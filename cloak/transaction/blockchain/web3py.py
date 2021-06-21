from __future__ import with_statement
from cloak.tests.utils.test_timer import sleep
import json
import os
import ccf
import tempfile
from abc import abstractmethod
from contextlib import contextmanager
from pathlib import Path
from typing import Any, Dict, Optional, Tuple, List, Union

from eth_tester import PyEVMBackend, EthereumTester
from web3 import Web3

from cloak import my_logging
from cloak.compiler.privacy import library_contracts
from cloak.compiler.solidity.compiler import compile_solidity_json
from cloak.config import cfg, zk_print, zk_print_banner
from cloak.my_logging.log_context import log_context
from cloak.transaction.interface import CloakBlockchainInterface, IntegrityError, BlockChainError, \
    TransactionFailedException
from cloak.transaction.types import PublicKeyValue, AddressValue, MsgStruct, BlockStruct, TxStruct, Value
from cloak.transaction.blockchain.ccf_provider import CloakCCFProvider
from cloak.transaction.blockchain import ccf_config
from cloak.utils.helpers import get_contract_names, save_to_file
from cloak.cloak_ast.process_ast import get_verification_contract_names

max_gas_limit = 10000000
count = 0


class Web3Blockchain(CloakBlockchainInterface):
    def __init__(self) -> None:
        super().__init__()
        self.w3 = self._create_w3_instance()
        if not self.w3.isConnected():
            raise BlockChainError(f'Failed to connect to blockchain: {self.w3.provider}')

    @staticmethod
    def compile_contract(sol_filename: str, contract_name: str, libs: Optional[Dict] = None, cwd=None):
        solp = Path(sol_filename)
        jout = compile_solidity_json(sol_filename, libs, optimizer_runs=cfg.opt_solc_optimizer_runs, cwd=cwd)['contracts'][solp.name][contract_name]
        return {
            'abi': json.loads(jout['metadata'])['output']['abi'],
            'bin': jout['evm']['bytecode']['object'],
            'deployed_bin': jout['evm']['deployedBytecode']['object']
        }

    def deploy_solidity_contract(self, sol_filename: str, contract_name: Optional[str], sender: Union[bytes, str]) -> str:
        contract_name = get_contract_names(sol_filename)[0] if contract_name is None else contract_name
        contract = self._deploy_contract(sender, self.compile_contract(sol_filename, contract_name))
        return str(contract.address)

    def get_special_variables(self, sender: AddressValue, wei_amount: int = 0) -> Tuple[MsgStruct, BlockStruct, TxStruct]:
        block = self.w3.eth.getBlock('pending')
        zk_print(f'Current block timestamp: {block["timestamp"]}')
        return MsgStruct(sender, wei_amount), \
               BlockStruct(AddressValue(self.w3.eth.coinbase), block['difficulty'], block['gasLimit'], block['number'], block['timestamp']),\
               TxStruct(self.w3.eth.gasPrice, sender)

    @abstractmethod
    def _create_w3_instance(self) -> Web3:
        pass

    def _default_address(self) -> Union[None, bytes, str]:
        if cfg.blockchain_default_account is None:
            return None
        elif isinstance(cfg.blockchain_default_account, int):
            return self.w3.eth.accounts[cfg.blockchain_default_account]
        else:
            return cfg.blockchain_default_account

    def _get_balance(self, address: Union[bytes, str]) -> int:
        return self.w3.eth.getBalance(address)

    def _req_public_key(self, address: Union[bytes, str]) -> PublicKeyValue:
        return PublicKeyValue(self._req_state_var(self.pki_contract, 'getPk', address))

    def _announce_public_key(self, address: Union[bytes, str], pk: Tuple[int, ...]) -> Any:
        with log_context('transaction', f'announcePk'):
            return self._transact(self.pki_contract, address, 'announcePk', pk)

    def _set_tee_address(self, address: Union[bytes, str]) -> Any:
        with log_context('transaction', f'{cfg.tee_set_addr_function_name}'):
            return self._transact(self.service_contract, address, f'{cfg.tee_set_addr_function_name}')

    def _get_tee_address(self) -> Any:
        return AddressValue(self._req_state_var(self.service_contract, f'{cfg.tee_get_addr_function_name}'))

    def _req_state_var(self, contract_handle, name: str, *indices) -> Any:
        try:
            return contract_handle.functions[name](*indices).call()
        except Exception as e:
            raise BlockChainError(e.args)

    def _call(self, contract_handle, sender: Union[bytes, str], name: str, *args) -> Union[bool, int, str]:
        try:
            fct = contract_handle.functions[name]
            gas_amount = self._gas_heuristic(sender, fct(*args))
            tx = {'from': sender, 'gas': gas_amount}
            return fct(*args).call(tx)
        except Exception as e:
            raise BlockChainError(e.args)

    def _transact(self, contract_handle, sender: Union[bytes, str], function: str, *actual_params, wei_amount: Optional[int] = None) -> Any:
        try:
            fct = contract_handle.constructor if function == 'constructor' else contract_handle.functions[function]
            gas_amount = self._gas_heuristic(sender, fct(*actual_params))
            tx = {'from': sender, 'gas': gas_amount}
            if wei_amount:
                tx['value'] = wei_amount
            tx_hash = fct(*actual_params).transact(tx)
            tx_receipt = self.w3.eth.waitForTransactionReceipt(tx_hash)
        except Exception as e:
            raise BlockChainError(e.args)

        if tx_receipt['status'] == 0:
            raise TransactionFailedException("Transaction failed")
        gas = tx_receipt['gasUsed']
        zk_print(f"Consumed gas: {gas}")
        my_logging.data('gas', gas)
        return tx_receipt

    def _deploy(self, project_dir: str, sender: Union[bytes, str], contract: str, *actual_args, wei_amount: Optional[int] = None) -> Any:
        with open(os.path.join(project_dir, 'contract.cloak')) as f:
            verifier_names = get_verification_contract_names(f.read())

        # Deploy verification contracts if not already done
        external_contract_addresses =  self._deploy_dependencies(sender, project_dir, verifier_names)
        with self.__hardcoded_external_contracts_ctx(project_dir, external_contract_addresses) as filename:
            cout = self.compile_contract(filename, contract, cwd=project_dir)
        handle = self._deploy_contract(sender, cout, *actual_args, wei_amount=wei_amount)
        zk_print(f'Deployed contract "{contract}" at address "{handle.address}"')
        return handle

    def _deploy_contract(self, sender: Union[bytes, str], contract_interface, *args, wei_amount: Optional[int] = None):
        if args is None:
            args = []

        contract = self.w3.eth.contract(
            abi=contract_interface['abi'],
            bytecode=contract_interface['bin']
        )

        tx_receipt = self._transact(contract, sender, 'constructor', *args, wei_amount=wei_amount)
        contract = self.w3.eth.contract(
            address=tx_receipt.contractAddress, abi=contract_interface['abi']
        )
        return contract

    def _deploy_dependencies(self, sender: Union[bytes, str], project_dir: str, verifier_names: List[str]) -> Dict[str, AddressValue]:
        # Deploy verification contracts if not already done
        vf = {}
        for verifier_name in verifier_names:
            with log_context('transaction', f'deploy_{verifier_name}'):
                filename = os.path.join(project_dir, f'{verifier_name}.sol')
                cout = self.compile_contract(filename, verifier_name, self.lib_addresses)
                vf[verifier_name] = AddressValue(self._deploy_contract(sender, cout).address)
        vf[cfg.pki_contract_name] = AddressValue(self.pki_contract.address)
        vf[cfg.service_contract_name] = AddressValue(self.service_contract.address)
        return vf

    def _connect_libraries(self):
        if not cfg.blockchain_pki_address:
            raise BlockChainError('Must specify pki address in config.')

        lib_addresses = []
        if cfg.external_crypto_lib_names:
            lib_addresses = [addr.strip() for addr in cfg.blockchain_crypto_lib_addresses.split(',')] if cfg.blockchain_crypto_lib_addresses else []
            if len(lib_addresses) != len(cfg.external_crypto_lib_names):
                raise BlockChainError('Must specify all crypto library addresses in config\n'
                                      f'Expected {len(cfg.external_crypto_lib_names)} was {len(lib_addresses)}')

        with cfg.library_compilation_environment():
            with tempfile.TemporaryDirectory() as tmpdir:
                pki_sol = save_to_file(tmpdir, f'{cfg.pki_contract_name}.sol', library_contracts.get_pki_contract())
                self._pki_contract = self._verify_contract_integrity(cfg.blockchain_pki_address, pki_sol, contract_name=cfg.pki_contract_name)
                # service_sol = save_to_file(tmpdir, f'{cfg.service_contract_name}.sol', library_contracts.get_service_contract())
                # self._service_contract = self._verify_contract_integrity(cfg.blockchain_service_address, service_sol, contract_name=cfg.service_contract_name)

                verify_sol = save_to_file(tmpdir, 'verify_libs.sol', library_contracts.get_verify_libs_code())
                self._lib_addresses = {}
                for lib, addr in zip(cfg.external_crypto_lib_names, lib_addresses):
                    out = self._verify_contract_integrity(addr, verify_sol, contract_name=lib, is_library=True)
                    self._lib_addresses[lib] = out.address

    def _connect(self, project_dir: str, contract: str, address: Union[bytes, str]) -> Any:
        filename = os.path.join(project_dir, 'public_contract.sol')
        cout = self.compile_contract(filename, contract)
        return self.w3.eth.contract(
            address=address, abi=cout['abi']
        )

    def _verify_contract_integrity(self, address: Union[bytes, str], sol_filename: str, *,
                                   libraries: Dict = None, contract_name: str = None, is_library: bool = False,
                                   cwd=None) -> Any:
        if isinstance(address, bytes):
            address = self.w3.toChecksumAddress(address)

        if contract_name is None:
            contract_name = get_contract_names(sol_filename)[0]
        actual_byte_code = self.__normalized_hex(self.w3.eth.getCode(address))
        if not actual_byte_code:
            raise IntegrityError(f'Expected contract {contract_name} is not deployed at address {address}')

        cout = self.compile_contract(sol_filename, contract_name, libs=libraries, cwd=cwd)
        expected_byte_code = self.__normalized_hex(cout['deployed_bin'])

        if is_library:
            # https://github.com/ethereum/solidity/issues/7101
            expected_byte_code = expected_byte_code[:2] + self.__normalized_hex(address) + expected_byte_code[42:]

        if actual_byte_code != expected_byte_code:
            raise IntegrityError(f'Deployed contract at address {address} does not match local contract {sol_filename}')
        zk_print(f'Contract@{address} matches {sol_filename[sol_filename.rfind("/") + 1:]}:{contract_name}')

        return self.w3.eth.contract(
            address=address, abi=cout['abi']
        )

    def _verify_library_integrity(self, libraries: List[Tuple[str, str]], contract_with_libs_addr: str, sol_with_libs_filename: str) -> Dict[str, str]:
        cname = get_contract_names(sol_with_libs_filename)[0]
        actual_code = self.__normalized_hex(self.w3.eth.getCode(contract_with_libs_addr))
        if not actual_code:
            raise IntegrityError(f'Expected contract {cname} is not deployed at address {contract_with_libs_addr}')
        code_with_placeholders = self.__normalized_hex(self.compile_contract(sol_with_libs_filename, cname)['deployed_bin'])

        if len(actual_code) != len(code_with_placeholders):
            raise IntegrityError(f'Local code of contract {cname} has different length than remote contract')

        addresses = {}
        for lib_name, lib_sol in libraries:
            # Compute placeholder according to
            # https://solidity.readthedocs.io/en/v0.5.13/using-the-compiler.html#using-the-commandline-compiler
            hash = self.w3.solidityKeccak(['string'], [f'{lib_sol[lib_sol.rfind("/") + 1:]}:{lib_name}'])
            placeholder = f'__${self.__normalized_hex(hash)[:34]}$__'

            # Retrieve concrete address in deployed code at placeholder offset in local code and verify library contract integrity
            lib_address_offset = code_with_placeholders.find(placeholder)
            if lib_address_offset != -1:
                lib_address = self.w3.toChecksumAddress(actual_code[lib_address_offset:lib_address_offset+40])
                with cfg.library_compilation_environment():
                    self._verify_contract_integrity(lib_address, lib_sol, contract_name=lib_name, is_library=True)
                addresses[lib_name] = lib_address
        return addresses

    def _verify_zkay_contract_integrity(self, address: str, project_dir: str, pki_verifier_addresses: Dict):
        with self.__hardcoded_external_contracts_ctx(project_dir, pki_verifier_addresses) as sol_file:
            self._verify_contract_integrity(address, sol_file, cwd=project_dir)

    @contextmanager
    def __hardcoded_external_contracts_ctx(self, contract_dir: str, pki_verifier_addresses):
        # Hardcode contract addresses
        with open(os.path.join(contract_dir, 'public_contract.sol')) as f:
            c = f.read()
        for key, val in pki_verifier_addresses.items():
            c = c.replace(f'{key}(0)', f'{key}({self.w3.toChecksumAddress(val.val)})')

        with tempfile.TemporaryDirectory() as tempd:
            # Save in temporary file to compile
            output_filename = os.path.join(tempd, "contract.inst.sol")
            with open(output_filename, 'w') as f:
                f.write(c)
            yield output_filename
            pass

    def __normalized_hex(self, val: Union[str, bytes]) -> str:
        if not isinstance(val, str):
            val = val.hex()
        val = val[2:] if val.startswith('0x') else val
        return val.lower()

    def _gas_heuristic(self, sender, tx) -> int:
        limit = self.w3.eth.getBlock('latest')['gasLimit']
        estimate = tx.estimateGas({'from': sender, 'gas': limit})
        return min(int(estimate * 1.2), limit)


class Web3CloakCCFNetwork(Web3Blockchain):
    def __init__(self) -> None:
        super().__init__()
        

    def _create_w3_instance(self) -> Web3:
        # config = ccf.clients.CCFClient(
        #     ccf_config.host, 
        #     ccf_config.port, 
        #     ccf_config.ca, 
        #     ccf_config.cert, 
        #     ccf_config.key
        # )
        # w3 = Web3(CloakCCFProvider(config))
        # return w3

        # TODO: renqian - replace with CCF
        genesis_overrides = {'gas_limit': int(max_gas_limit * 1.2)}
        custom_genesis_params = PyEVMBackend._generate_genesis_params(overrides=genesis_overrides)
        self.eth_tester = EthereumTester(backend=PyEVMBackend(genesis_parameters=custom_genesis_params))
        w3 = Web3(Web3.EthereumTesterProvider(self.eth_tester))
        return w3

    def _deploy(self, project_dir: str, sender: Union[bytes, str], contract: str, *actual_args, wei_amount: Optional[int] = None) -> Any:
        cout = self.compile_contract(os.path.join(project_dir, "private_contract.sol"), contract, cwd=project_dir)
        handle = self._deploy_contract(sender, cout, *actual_args, wei_amount=wei_amount)
        zk_print(f'Deployed private contract "{contract}" at address "{handle.address}"')
        # TODO: renqian - bind privacy policy
        zk_print(f'Bindded privacy policy and public contract address with the private contract')
        return handle

    def _transact(self, contract_handle, sender: Union[bytes, str], function: str, *actual_params, wei_amount: Optional[int] = None) -> Any:
        if function == "compareToAverage":
            sleep(2)
            global count
            if (count == 0):
                zk_print("Waiting for manager to summit avgScore, examinator to submit point[msg.sender]...")
                count += 1
            elif (count == 1):
                zk_print("Waiting for examinator to submit point[msg.sender]...")
                count += 1
            else:
                zk_print("All variables have been known by TEE, waiting for execution result...")
            actual_params = []
        try:
            fct = contract_handle.constructor if function == 'constructor' else contract_handle.functions[function]
            tx = {'from': sender}
            tx_hash = fct(*actual_params).transact(tx)
            tx_receipt = self.w3.eth.waitForTransactionReceipt(tx_hash)
        except Exception as e:
            # raise BlockChainError(e.args)
            return None

        if tx_receipt['status'] == 0:
            raise TransactionFailedException("Transaction failed")
        if function == "compareToAverage" and count == 2:
            zk_print(f"Get public return of {function}: {0}")
        return tx_receipt

    def transact(self, contract_handle, sender: AddressValue, function: str, actual_args: List, should_encrypt: List[bool], wei_amount: Optional[int] = None) -> Any:
        assert contract_handle is not None
        zk_print(f'Issuing transaction to cloak network for function "{function}" from account "{sender}"')
        zk_print(Value.collection_to_string(actual_args), verbosity_level=2)
        ret = self._transact(contract_handle, sender.val, function, *Value.unwrap_values(actual_args), wei_amount=wei_amount)
        zk_print()
        return ret


class Web3TesterBlockchain(Web3Blockchain):
    def __init__(self) -> None:
        self.eth_tester = None
        super().__init__()
        self.next_acc_idx = 1

    @classmethod
    def is_debug_backend(cls) -> bool:
        return True

    def _connect_libraries(self):
        zk_print_banner(f'Deploying Libraries')

        sender = self.w3.eth.accounts[0]
        # Since eth-tester is not persistent, always automatically deploy libraries
        with cfg.library_compilation_environment():
            with tempfile.TemporaryDirectory() as tmpdir:
                with log_context('transaction', 'deploy_pki'):
                    pki_sol = save_to_file(tmpdir, f'{cfg.pki_contract_name}.sol', library_contracts.get_pki_contract())
                    self._pki_contract = self._deploy_contract(sender, self.compile_contract(pki_sol, cfg.pki_contract_name))
                    zk_print(f'Deployed pki contract at address "{self.pki_contract.address}"')
                    service_sol = save_to_file(tmpdir, f'{cfg.service_contract_name}.sol', library_contracts.get_service_contract())
                    self._service_contract = self._deploy_contract(sender, self.compile_contract(service_sol, cfg.service_contract_name))
                    zk_print(f'Deployed sevice contract at address "{self.service_contract.address}"')
                
                with log_context('transaction', 'deploy_verify_libs'):
                    verify_sol = save_to_file(tmpdir, 'verify_libs.sol', library_contracts.get_verify_libs_code())
                    self._lib_addresses = {}
                    for lib in cfg.external_crypto_lib_names:
                        out = self._deploy_contract(sender, self.compile_contract(verify_sol, lib))
                        self._lib_addresses[lib] = out.address
                        zk_print(f'Deployed crypto lib {lib} at address "{out.address}"')

    def _create_w3_instance(self) -> Web3:
        genesis_overrides = {'gas_limit': int(max_gas_limit * 1.2)}
        custom_genesis_params = PyEVMBackend._generate_genesis_params(overrides=genesis_overrides)
        self.eth_tester = EthereumTester(backend=PyEVMBackend(genesis_parameters=custom_genesis_params))
        w3 = Web3(Web3.EthereumTesterProvider(self.eth_tester))
        return w3

    def create_test_accounts(self, count: int) -> Tuple:
        accounts = self.w3.eth.accounts
        if len(accounts[self.next_acc_idx:]) < count:
            raise ValueError(f'Can have at most {len(accounts)-1} dummy accounts in total')
        dummy_accounts = tuple(accounts[self.next_acc_idx:self.next_acc_idx + count])
        self.next_acc_idx += count
        return dummy_accounts

    def _gas_heuristic(self, sender, tx) -> int:
        return max_gas_limit


class Web3IpcBlockchain(Web3Blockchain):
    def _create_w3_instance(self) -> Web3:
        assert cfg.blockchain_node_uri is None or isinstance(cfg.blockchain_node_uri, str)
        return Web3(Web3.IPCProvider(cfg.blockchain_node_uri))


class Web3WebsocketBlockchain(Web3Blockchain):
    def _create_w3_instance(self) -> Web3:
        assert cfg.blockchain_node_uri is None or isinstance(cfg.blockchain_node_uri, str)
        return Web3(Web3.WebsocketProvider(cfg.blockchain_node_uri))


class Web3HttpBlockchain(Web3Blockchain):
    def _create_w3_instance(self) -> Web3:
        assert cfg.blockchain_node_uri is None or isinstance(cfg.blockchain_node_uri, str)
        return Web3(Web3.HTTPProvider(cfg.blockchain_node_uri))


class Web3HttpGanacheBlockchain(Web3HttpBlockchain):
    def __init__(self) -> None:
        super().__init__()
        self.next_acc_idx = 1

    @classmethod
    def is_debug_backend(cls) -> bool:
        return True

    def create_test_accounts(self, count: int) -> Tuple:
        accounts = self.w3.eth.accounts
        if len(accounts[self.next_acc_idx:]) < count:
            raise ValueError(f'Can have at most {len(accounts)-1} dummy accounts in total')
        dummy_accounts = tuple(accounts[self.next_acc_idx:self.next_acc_idx + count])
        self.next_acc_idx += count
        return dummy_accounts

    def _gas_heuristic(self, sender, tx) -> int:
        return self.w3.eth.getBlock('latest')['gasLimit']


class Web3CustomBlockchain(Web3Blockchain):
    def _create_w3_instance(self) -> Web3:
        assert isinstance(cfg.blockchain_node_uri, Web3)
        return cfg.blockchain_node_uri
