import web3
import json
import base64
from typing import Dict, Optional, List, Tuple

from cloak.cloak_ast.visitor.transformer_visitor import AstTransformerVisitor
from cloak.compiler.privacy.transformation.tee_transformer import TeeVarDeclTransformer
from cloak.config import cfg
from cloak.cloak_ast.ast import AddressTypeName, Expression, ConstructorOrFunctionDefinition, FunctionPrivacyType, IdentifierExpr, Mapping, TeeExpr, VariableDeclaration, \
    AnnotatedTypeName, UintTypeName, \
    StateVariableDeclaration, Identifier, ExpressionStatement, SourceUnit, ReturnStatement, AST, \
    Comment, NumberLiteralExpr, StructDefinition, Array, FunctionCallExpr, StructTypeName, PrimitiveCastExpr, TypeName, \
    ContractTypeName, BlankLine, Block, RequireStatement, NewExpr, ContractDefinition, TupleExpr, PrivacyLabelExpr, \
    Parameter, ForStatement, IfStatement, IndexExpr, \
    VariableDeclarationStatement, StatementList, ArrayLiteralExpr, MeExpr, StringLiteralExpr, LocationExpr
from cloak.cloak_ast.pointers.parent_setter import set_parents
from cloak.cloak_ast.pointers.symbol_table import link_identifiers
from cloak.cloak_ast.visitor.deep_copy import deep_copy
from cloak.cloak_ast.global_defs import GlobalDefs
from cloak.policy.privacy_policy import PrivacyPolicyEncoder
from cloak.cloak_ast import ast as ast_module
from cloak.cloak_ast.build_ast import SOL


def transform_ast(ast: AST) -> AST:
    """
    Convert cloak to solidity AST + proof circuits

    :param ast: cloak AST
    :return: solidity AST and dictionary which maps all function definitions which require verification
             to the corresponding circuit helper instance.
    """
    ct = CloakTransformer()
    new_ast = ct.visit(ast)

    # restore all parent pointers and identifier targets
    # XYZ set_parents(new_ast)
    # XYZ link_identifiers(new_ast)
    return new_ast


class CloakTransformer(AstTransformerVisitor):
    """
    Transformer which transforms contract level AST elements (contract, function definitions, constructor definitions)

    Contract level transformations:

    * Import public key infrastructure contract and make it available as public constant state variable
    * | Import verification contracts for all functions which require verification and make them available as public constant state variables
      | Note: This transformations initializes those state variables with address 0, which is a placeholder. 0 is replaced with the \
              real address upon deployment.
    * Transform state variable declarations with owner != @all (replace type by cipher type)
    * For every function and constructor, the parameters and the body are transformed using the transformers defined in zkp_transformer.py
    """

    def __init__(self):
        super().__init__()

    @staticmethod
    def import_contract(cname: str, su: SourceUnit):
        """
        Import contract 'vname' into the given source unit.

        :param cname: contract name (.sol filename stem must match contract type name)
        :param su: [SIDE EFFECT] source unit where contract should be imported
        """
        import_filename = f'./{cname}.sol'
        su.import_directives.append(ast_module.ImportDirective(import_filename))

    @staticmethod
    def create_contract_variable(cname: str, default_value: Optional[str] = None) -> StateVariableDeclaration:
        """Create a public constant state variable with which contract with name 'cname' can be accessed"""
        source_text = None
        if default_value is None or default_value == "":
            default_value = 0
        else:
            source_text = default_value

        inst_idf = Identifier(cfg.get_contract_var_name(cname))
        c_type = ContractTypeName([Identifier(cname)])

        cast_0_to_c = PrimitiveCastExpr(c_type, NumberLiteralExpr(int(default_value, 16), source_text=source_text))
        var_decl = StateVariableDeclaration(AnnotatedTypeName(
            c_type), ['public', 'constant'], inst_idf.clone(), cast_0_to_c)
        return var_decl

    def include_verification_contracts(self, su: SourceUnit, c: ContractDefinition) -> List[StateVariableDeclaration]:
        """
        Import all verification contracts for 'c' into 'su' and create state variable declarations for all of them + the pki contract.

        :param su: [SIDE EFFECT] source unit into which contracts should be imported
        :param c: contract for which verification contracts should be imported
        :return: list of all constant state variable declarations for the pki contract + all the verification contracts
        """
        c.contract_var_decls = [
            self.create_contract_variable(cfg.service_contract_name, cfg.blockchain_service_address),
        ]

        return c.contract_var_decls

    def visitSourceUnit(self, ast: SourceUnit):
        self.import_contract(cfg.service_contract_name, ast)

        for c in ast.contracts:
            self.transform_contract(ast, c)

        return ast

    def transform_contract(self, su: SourceUnit, c: ContractDefinition) -> ContractDefinition:
        """
        Transform an entire cloak contract into a public solidity contract.

        This:

        * transforms state variables, function bodies and signatures
        * import verification contracts
        * adds data structs for each function with verification \
          (to store circuit I/O, to bypass solidity stack limit and allow for easy assignment of array variables),
        * creates external wrapper functions for all public functions which require verification
        * adds circuit IO serialization/deserialization code from/to zk_data struct to all functions which require verification.

        :param su: [SIDE EFFECTS] Source unit of which this contract is part of
        :param c: [SIDE EFFECTS] The contract to transform
        :return: The contract itself
        """

        # Get list of static owner labels for this contract
        c.global_owners = [Expression.me_expr(), Expression.tee_expr()]
        for var in c.state_variable_declarations:
            if isinstance(var, StateVariableDeclaration):
                if var.annotated_type.is_address() and (var.is_final or var.is_constant):
                    c.global_owners.append(var.idf)

        # Backup untransformed function bodies
        c.all_fcts = c.constructor_definitions + c.function_definitions
        for fct in c.all_fcts:
            fct.original_body = deep_copy(
                fct.body, with_types=True, with_analysis=True)

        c.fcts_is_tee = [fct for fct in c.all_fcts if fct.is_tee()]
        """Abstract circuits for all functions which require verification"""
        c.new_fcts, c.new_constr = [], []
        for fct in c.all_fcts:
            assert isinstance(fct, ConstructorOrFunctionDefinition)
            if fct.is_constructor:
                c.new_constr.append(fct)

        self.include_verification_contracts(su, c)

        c = self.transform_tee_functions(su, c)

        c.state_variable_declarations = [Comment('User state variables')]\
            + c.state_variable_declarations

        # Add constant state variables for tee external contracts
        code_hash_hb = web3.Web3.keccak(su.private_contract_code.encode())
        print(code_hash_hb.hex())
        code_hash_decl = StateVariableDeclaration(AnnotatedTypeName.uint_all(), ['public', 'constant'],
                                                  Identifier(
                                                      cfg.tee_code_hash_name),
                                                  NumberLiteralExpr(int(code_hash_hb.hex(), base=16)))
        policy_hash_hb = web3.Web3.keccak(json.dumps(su.privacy_policy, cls=PrivacyPolicyEncoder, indent=2).encode())
        print(policy_hash_hb.hex())
        policy_hash_decl = StateVariableDeclaration(AnnotatedTypeName.uint_all(), ['public', 'constant'],
                                                    Identifier(
                                                        cfg.tee_policy_hash_name),
                                                    NumberLiteralExpr(int(policy_hash_hb.hex(), base=16)))
        tee_addr_var = StateVariableDeclaration(AnnotatedTypeName.address_all(), ['public'], Identifier('tee'),
                                                IdentifierExpr(f'{cfg.service_contract_name}_inst').call(cfg.tee_get_addr_function_name, []))
        c.state_variable_declarations = [Comment('TEE helper variables'), code_hash_decl, policy_hash_decl, tee_addr_var, Comment()]\
            + c.state_variable_declarations


        # Add helper contracts
        c.state_variable_declarations = [Comment()]\
            + Comment.comment_list('Helper Contracts', c.contract_var_decls)\
            + c.state_variable_declarations

        c.constructor_definitions = c.new_constr
        c.function_definitions = [fct for fct in c.new_fcts if fct.body.statements]

        return c

    def transform_tee_functions(self, su: SourceUnit, c: ContractDefinition):
        """
        Transform the TEE funtions into public functions

        This:

        * transforms state variables, function bodies and signatures
        * import tee_Verify_Service contracts
        * adds tee_data structs for each function with verification \
          (to store tee proof, to bypass solidity stack limit and allow for easy assignment of array variables),
        * creates external wrapper functions for all public functions which require verification
        * adds tee proof serialization/deserialization code from/to tee_data struct to all functions which require verification.

        :param su: [SIDE EFFECTS] Source unit of which this contract is part of
        :param c: [SIDE EFFECTS] The contract to transform
        :return: The contract itself
        """

        var_decl_trafo = TeeVarDeclTransformer()
        """Transformer for state variable declarations and parameters"""

        # Transform types of normal state variables
        c.state_variable_declarations = var_decl_trafo.visit_list(
            c.state_variable_declarations)

        # append get_states and set_states
        self.append_get_states(su, c)
        self.append_set_states(su, c)

        return c

    @staticmethod
    def get_states(su: SourceUnit, c: ContractDefinition, is_cipher = True) -> ConstructorOrFunctionDefinition:
        states = su.privacy_policy.policy["states"]
        states_types = c.states_types()
        basic_type, idx = CloakTransformer.get_states_basic_type(states, states_types, is_cipher)
        mapping_type, _ = CloakTransformer.get_states_mapping_type(states, states_types, idx, is_cipher)
        return SOL(f"""
            function get_states(bytes[] memory read, uint return_len) public returns (bytes[] memory) {{
                bytes[] memory oldStates = new bytes[](return_len);
                {basic_type}
                uint read_idx = 0;
                uint os_idx = {idx};
                uint keys_count = 0;
                {mapping_type}
                return oldStates;
            }}
        """)

    @staticmethod
    def get_states_basic_type(states: [Dict[str, any]], states_types: Dict[str, TypeName], is_cipher = True) -> (str, int):
        basic_type = ""
        idx = 0
        for i, state in enumerate(states):
            state_type = states_types[state["name"]]
            if not state_type.is_mapping:
                basic_type += f"oldStates[{idx}] = abi.encode({i});\n"
                if state["owner"]["owner"] != "all" and is_cipher:
                    basic_type += f"oldStates[{idx+1}] = {state['name']}[0];\n"
                    basic_type += f"oldStates[{idx+2}] = {state['name']}[1];\n"
                    basic_type += f"oldStates[{idx+3}] = {state['name']}[2];\n"
                    idx += 4
                else:
                    basic_type += f"oldStates[{idx+1}] = abi.encode({state['name']});"
                    idx += 2
        return basic_type, idx

    @staticmethod
    def get_states_mapping_type(states: [Dict[str, any]], states_types: Dict[str, TypeName], idx: int, is_cipher = True) -> (str, int):
        mapping_type = ""
        for si, state in enumerate(states):
            state_type = states_types[state["name"]]
            if state_type.is_mapping:
                mapping_type += "oldStates[os_idx] = read[read_idx];\n"
                mapping_type += "oldStates[os_idx+1] = read[read_idx+1];\n"

                mapping_depth, mapping_keys, _ = state_type.split()
                factor = mapping_depth + (3 if state["owner"]["owner"] != "all" and is_cipher else 1)
                for_body = ""
                key_expr = ""
                for i in range(mapping_depth):
                    key_type = mapping_keys[i]
                    for_body += f"oldStates[os_idx + {2+i} + i * {factor}] = read[read_idx + {2+i} + i];\n"
                    key_expr += f"[abi.decode(read[read_idx + {2+i} + i], ({key_type}))]"
                if state["owner"]["owner"] != "all" and is_cipher:
                    for_body += f"oldStates[os_idx + {2 + mapping_depth} + i * {factor}] = {state['name']}{key_expr}[0];\n"
                    for_body += f"oldStates[os_idx + {3 + mapping_depth} + i * {factor}] = {state['name']}{key_expr}[1];\n"
                    for_body += f"oldStates[os_idx + {4 + mapping_depth} + i * {factor}] = {state['name']}{key_expr}[2];\n"
                else:
                    for_body += f"oldStates[os_idx + {2 + mapping_depth} + i * {factor}] = abi.encode({state['name']}{key_expr});\n"
                mapping_type += f"""
                    keys_count = abi.decode(read[read_idx + 1], (uint));
                    for (uint i = 0; i < keys_count; i = i + 1) {{
                        {for_body}
                    }}
                """

                if si != len(states) - 1:
                    mapping_type += f"os_idx = os_idx + 2 + keys_count * {factor};\n"
                    mapping_type += f"read_idx = read_idx + 2 + keys_count * {mapping_depth};\n"

        return mapping_type, idx


    @staticmethod
    def set_states(su: SourceUnit, c: ContractDefinition, is_cipher = True) -> ConstructorOrFunctionDefinition:
        states = su.privacy_policy.policy["states"]
        states_types = c.states_types()
        basic_type, idx = CloakTransformer.set_states_basic_type(states, states_types, is_cipher)
        mapping_type, _ = CloakTransformer.set_states_mapping_type(states, states_types, idx, is_cipher)
        guard = ""
        params = "bytes[] memory data"
        if is_cipher:
            guard = """
                require(msg.sender == tee, 'msg.sender is not tee');
                require(proof[0] == teeCHash, 'code hash error');
                require(proof[1] == teePHash, 'policy hash error');
                uint256 osHash = uint256(keccak256(abi.encode(get_states(read, old_states_len))));
                require(proof[2] == osHash, 'old states hash error');
            """
            params = "bytes[] memory read, uint old_states_len, bytes[] memory data, uint[] memory proof"
        res = SOL(f"""
        function set_states({params}) public {{
            {guard}
            {basic_type}
            uint data_idx = {idx};
            uint keys_count = 0;
            {mapping_type}
        }}
        """)
        return res


    @staticmethod
    def set_states_basic_type(states: [Dict[str, any]], states_types: Dict[str, TypeName], is_cipher = True) -> (str, int):
        basic_type = ""
        idx = 0
        for i, state in enumerate(states):
            state_type = states_types[state["name"]]
            if not state_type.is_mapping:
                if state["owner"]["owner"] != "all" and is_cipher:
                    basic_type += f"{state['name']}[0] = data[{idx+1}] ;\n"
                    basic_type += f"{state['name']}[1] = data[{idx+2}] ;\n"
                    basic_type += f"{state['name']}[2] = data[{idx+3}] ;\n"
                    idx += 4
                else:
                    basic_type += f"{state['name']} = abi.decode(data[{idx+1}], ({state_type}));\n"
                    idx += 2
        return basic_type, idx


    @staticmethod
    def set_states_mapping_type(states: [Dict[str, any]], states_types: Dict[str, TypeName], idx: int, is_cipher = True) -> (str, int):
        mapping_type = ""
        for si, state in enumerate(states):
            state_type = states_types[state["name"]]
            if state_type.is_mapping:
                mapping_depth, mapping_keys, value_type = state_type.split()
                factor = mapping_depth + (3 if state["owner"]["owner"] != "all" and is_cipher else 1)
                key_expr = ""
                for i in range(mapping_depth):
                    key_expr += f"[abi.decode(data[data_idx + {2+i} + i * {factor}], ({mapping_keys[i]}))]"
                for_body = ""
                if state["owner"]["owner"] != "all" and is_cipher:
                    for_body += f"{state['name']}{key_expr}[0] = data[data_idx +{2+mapping_depth} + i * {factor}];\n"
                    for_body += f"{state['name']}{key_expr}[1] = data[data_idx +{3+mapping_depth} + i * {factor}];\n"
                    for_body += f"{state['name']}{key_expr}[2] = data[data_idx +{4+mapping_depth} + i * {factor}];\n"
                else:
                    for_body += f"{state['name']}{key_expr} = abi.decode(data[data_idx + {2+mapping_depth} + i * {factor}], ({value_type}));\n"
                mapping_type += f"""
                    keys_count = abi.decode(data[data_idx + 1], (uint));
                    for (uint i = 0; i < keys_count; i = i + 1) {{
                        {for_body}
                    }}
                """

                if si != len(states) - 1:
                    mapping_type += f"data_idx = data_idx + 2 + keys_count * {factor};\n"

        return mapping_type, idx


    def append_get_states(self, su: SourceUnit, c: ContractDefinition, is_cipher = True):
        c.new_fcts.append(self.get_states(su, c))

    def append_set_states(self, su: SourceUnit, c: ContractDefinition, is_cipher = True):
        c.new_fcts.append(self.set_states(su, c))
