import json
import math
import os
from contextlib import contextmanager
from typing import Dict, Any, ContextManager, List

from semantic_version import NpmSpec

from cloak.compiler.privacy.proving_scheme.meta import provingschemeparams
from cloak.config_user import UserConfig
from cloak.config_version import Versions
from cloak.transaction.crypto.meta import cryptoparams


def zk_print(*args, verbosity_level=1, **kwargs):
    if (verbosity_level <= cfg.verbosity) and not cfg.is_unit_test:
        print(*args, **kwargs)


def zk_print_banner(title: str):
    l = len(title) + 4
    zk_print(f'{"#"*l}\n# {title} #\n{"#"*l}\n')


class Config(UserConfig):
    def __init__(self):
        super().__init__()

        # Internal values

        self._options_with_effect_on_circuit_output = [
            'proving_scheme', 'snark_backend', 'crypto_backend',
            'opt_solc_optimizer_runs', 'opt_hash_threshold',
            'opt_eval_constexpr_in_circuit', 'opt_cache_circuit_inputs', 'opt_cache_circuit_outputs',
        ]

        self._is_unit_test = False
        self._concrete_solc_version = None

    def _load_cfg_file_if_exists(self, filename):
        if os.path.exists(filename):
            with open(filename) as conf:
                try:
                    self.override_defaults(json.load(conf))
                except ValueError as e:
                    raise ValueError(f'{e} (in file "{filename}")')

    def load_configuration_from_disk(self, local_cfg_file: str):
        # Load global configuration file
        global_config_dir = self._appdirs.site_config_dir
        global_cfg_file = os.path.join(global_config_dir, 'config.json')
        self._load_cfg_file_if_exists(global_cfg_file)

        # Load user configuration file
        user_config_dir = self._appdirs.user_config_dir
        user_cfg_file = os.path.join(user_config_dir, 'config.json')
        self._load_cfg_file_if_exists(user_cfg_file)

        # Load local configuration file
        self._load_cfg_file_if_exists(local_cfg_file)

    def override_defaults(self, overrides: Dict[str, Any]):
        for arg, val in overrides.items():
            if not hasattr(self, arg):
                raise ValueError(f'Tried to override non-existing config value {arg}')
            try:
                setattr(self, arg, val)
            except ValueError as e:
                raise ValueError(f'{e} (for entry "{arg}")')

    def export_compiler_settings(self) -> dict:
        out = {}
        for k in self._options_with_effect_on_circuit_output:
            out[k] = getattr(self, k)
        return out

    def import_compiler_settings(self, vals: dict):
        for k in vals:
            if k not in self._options_with_effect_on_circuit_output:
                raise KeyError(f'vals contains unknown option "{k}"')
            setattr(self, k, vals[k])

    @contextmanager
    def library_compilation_environment(self) -> ContextManager:
        """Use this fixed configuration compiling libraries to get reproducible output."""
        old_solc, old_opt_runs = self.solc_version, self.opt_solc_optimizer_runs
        self.override_solc(self.library_solc_version)
        self.opt_solc_optimizer_runs = 1000
        yield
        self.opt_solc_optimizer_runs = old_opt_runs
        self.override_solc(old_solc)

    @property
    def library_solc_version(self) -> str:
        # Note: Changing this version breaks compatibility with already deployed library contracts
        return Versions.ZKAY_LIBRARY_SOLC_VERSION

    @property
    def cloak_version(self) -> str:
        """cloak version number"""
        return Versions.CLOAK_VERSION

    @property
    def cloak_solc_version_compatibility(self) -> NpmSpec:
        """Target solidity language level for the current zkay version"""
        return Versions.CLOAK_SOLC_VERSION_COMPATIBILITY

    @property
    def solc_version(self) -> str:
        version = Versions.SOLC_VERSION
        assert version is not None and version != 'latest'
        return version

    @staticmethod
    def override_solc(new_version):
        Versions.set_solc_version(new_version)

    @property
    def key_bits(self) -> int:
        return cryptoparams[self.crypto_backend]['key_bits']

    @property
    def key_bytes(self) -> int:
        return self.key_bits // 8

    @property
    def rnd_bytes(self) -> int:
        return cryptoparams[self.crypto_backend]['rnd_bytes']

    @property
    def cipher_bytes_payload(self) -> int:
        return cryptoparams[self.crypto_backend]['cipher_payload_bytes']

    @property
    def cipher_bytes_meta(self) -> int:
        return cryptoparams[self.crypto_backend]['cipher_meta_bytes']

    def is_symmetric_cipher(self) -> bool:
        return cryptoparams[self.crypto_backend]['symmetric']

    @property
    def cipher_payload_len(self) -> int:
        return int(math.ceil(self.cipher_bytes_payload / self.cipher_chunk_size))

    @property
    def cipher_len(self) -> int:
        if self.is_symmetric_cipher():
            return self.cipher_payload_len + 1 # Additional uint to store sender address
        else:
            return self.cipher_payload_len

    @property
    def key_len(self) -> int:
        return 1 if self.is_symmetric_cipher() else int(math.ceil(self.key_bytes / self.cipher_chunk_size))

    @property
    def randomness_len(self) -> int:
        return 0 if self.is_symmetric_cipher() else int(math.ceil(self.rnd_bytes / self.rnd_chunk_size))

    @property
    def zk_proof_len(self) -> int:
        return provingschemeparams[self.proving_scheme]['proof_len']

    @property
    def tee_proof_len(self) -> int:
        return provingschemeparams['tee']['proof_len']

    @property
    def external_crypto_lib_names(self) -> List[str]:
        """Names of all solidity libraries in verify_libs.sol, which need to be linked against."""
        return provingschemeparams[self.proving_scheme]['external_sol_libs']

    def should_use_hash(self, circuit: 'CircuitHelper') -> bool:
        """
        This function determines whether input hashing is used for a particular circuit.

        :return: if true, all public circuit inputs are passed as private inputs into the circuit and only their combined hash-
                 value is passed as a public input. This makes verification constant-cost,
                 but increases offchain resource usage during key and proof generation.
        """

        pub_arg_size = circuit.trans_in_size + circuit.trans_out_size
        return pub_arg_size > self.opt_hash_threshold

    @property
    def zk_reserved_name_prefix(self) -> str:
        """
        Identifiers in user code must not start with this prefix.

        This is to ensure that user code does not interfere with the additional code generated by the zkay compiler.
        """
        return 'zk_'

    @property
    def tee_reserved_name_prefix(self) -> str:
        """
        Identifiers in user code must not start with this prefix.

        This is to ensure that user code does not interfere with the additional code generated by the zkay compiler.
        """
        return 'tee_'

    @property
    def reserved_conflict_resolution_suffix(self) -> str:
        """
        Identifiers in user code must not end with this suffix.

        This is used for resolving conflicts with python globals in the generated offchain simulation code.
        """
        return '_zalt'

    def get_zk_internal_name(self, fct) -> str:
        if fct.requires_verification_when_external:
            return f'_{self.zk_reserved_name_prefix}{fct.name}'
        else:
            return fct.name

    def get_tee_internal_name(self, fct) -> str:
        if fct.requires_verification_when_external:
            return f'_{self.tee_reserved_name_prefix}{fct.name}'
        else:
            return fct.name

    def get_zk_verification_contract_name(self, contract: str, fct: str):
        return f'{cfg.zk_reserved_name_prefix}Verify_{contract}_{fct}'

    def get_circuit_output_dir_name(self, verifier_name: str) -> str:
        """Return the output directory for an individual circuit"""
        return f'{verifier_name}_out'

    @staticmethod
    def get_contract_var_name(type_name: str) -> str:
        """
        Return an identifier referring to the address variable of verification contract of type 'type_name'

        :param type_name: name of the unqualified verification contract type
        :return: new identifier
        """
        return f'{type_name}_inst'

    @property
    def pki_contract_name(self) -> str:
        return f'CloakPublicKeyInfrastructure'

    @property
    def tee_service_contract_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}Verify_Service'

    @property
    def zk_out_name(self) -> str:
        return f'{self.zk_reserved_name_prefix}out'

    @property
    def zk_in_name(self) -> str:
        return f'{self.zk_reserved_name_prefix}in'

    @property
    def zk_proof_param_name(self) -> str:
        return f'{self.zk_reserved_name_prefix}proof'

    @property
    def return_zk_var_name(self) -> str:
        return f'{self.zk_reserved_name_prefix}ret'

    @property
    def zk_field_prime_var_name(self) -> str:
        return f'{self.zk_reserved_name_prefix}field_prime'

    @property
    def zk_prover_key_hash_name(self) -> str:
        return f'{self.zk_reserved_name_prefix}prover_key_hash'

    @property
    def zk_struct_prefix(self) -> str:
        return f'{self.zk_reserved_name_prefix}data'

    @property
    def zk_data_var_name(self) -> str:
        return f'{self.zk_struct_prefix}'

    @property
    def tee_out_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}out'

    @property
    def tee_read_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}read'

    @property
    def tee_mutate_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}mutate'

    @property
    def tee_proof_param_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}proof'

    @property
    def tee_code_hash_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}codeHash'

    @property
    def tee_policy_hash_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}policyHash'

    @property
    def tee_func_hash_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}funcHash'

    @property
    def tee_old_state_hash_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}oldStateHash'

    @property
    def tee_old_state_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}oldState'

    @property
    def return_tee_var_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}ret'

    @property
    def tee_field_prime_var_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}field_prime'

    @property
    def tee_prover_key_hash_name(self) -> str:
        return f'{self.tee_reserved_name_prefix}prover_key_hash'

    @property
    def tee_struct_prefix(self) -> str:
        return f'{self.tee_reserved_name_prefix}data'

    @property
    def tee_data_var_name(self) -> str:
        return f'{self.tee_struct_prefix}'

    @property
    def jsnark_circuit_classname(self) -> str:
        return 'ZkayCircuit'

    @property
    def zk_verification_function_name(self) -> str:
        return 'check_verify'

    @property
    def tee_verification_function_name(self) -> str:
        return 'verify'

    @property
    def tee_get_hash_function_name(self) -> str:
        return 'getHash'

    @property
    def cipher_chunk_size(self) -> int:
        return cryptoparams[self.crypto_backend]['cipher_chunk_size']

    @property
    def rnd_chunk_size(self) -> int:
        return cryptoparams[self.crypto_backend]['rnd_chunk_size']

    @property
    def is_unit_test(self) -> bool:
        return self._is_unit_test


cfg = Config()
Versions.set_solc_version('latest')
