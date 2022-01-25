import web3
import json
import base64
from typing import Dict, Optional, List, Tuple

from cloak.cloak_ast.visitor.transformer_visitor import AstTransformerVisitor
from cloak.cloak_ast.visitor.function_visitor import FunctionVisitor
from cloak.cloak_ast.visitor.visitor import AstVisitor
from cloak.errors.exceptions import CloakCompilerError
from cloak.compiler.privacy.transformation.tee_transformer import TeeVarDeclTransformer
from cloak.config import cfg
from cloak.cloak_ast.ast import AddressTypeName, Expression, ConstructorOrFunctionDefinition, FunctionPrivacyType, IdentifierExpr, Mapping, TeeExpr, VariableDeclaration, \
    AnnotatedTypeName, UintTypeName, \
    StateVariableDeclaration, Identifier, ExpressionStatement, SourceUnit, ReturnStatement, AST, \
    Comment, NumberLiteralExpr, StructDefinition, Array, FunctionCallExpr, StructTypeName, PrimitiveCastExpr, TypeName, \
    ContractTypeName, BlankLine, Block, RequireStatement, NewExpr, ContractDefinition, TupleExpr, PrivacyLabelExpr, \
    Parameter, ForStatement, IfStatement, IndexExpr, \
    VariableDeclarationStatement, StatementList, ArrayLiteralExpr, MeExpr, StringLiteralExpr, LocationExpr, AssignmentStatement, IdentifierDeclaration
from cloak.cloak_ast.pointers.parent_setter import set_parents
from cloak.cloak_ast.pointers.symbol_table import link_identifiers
from cloak.cloak_ast.global_defs import GlobalDefs
from cloak.policy.privacy_policy import PrivacyPolicyEncoder
from cloak.cloak_ast import ast as ast_module
from cloak.cloak_ast.build_ast import SOL


def transform_ast(ast: AST, put_enable: bool) -> AST:
    """
    Convert cloak to solidity AST + proof circuits

    :param ast: cloak AST
    :return: solidity AST and dictionary which maps all function definitions which require verification
             to the corresponding circuit helper instance.
    """

    # collect state names
    snc = StateNameCollector()
    snc.visit(ast)

    # collect function names
    fnc = FunctionNameCollector(snc.getPrivacyRelatedFunctions())
    fnc.visit(ast)

    # generate call graph
    cgg = CallGraphGenerator(list(fnc.getFunctionNames()), fnc.getPrivacyRelatedFunctions(), snc.getStates(), snc.getPrivacyRelatedStates())
    cgg.visit(ast)
    cgg.markPrivacyIteratively()

    # mark privacy-related function
    prm = PrivacyRelatedMarker(cgg.privacy_related_funciton_set)
    prm.visit(ast)

    # transformer
    ct = CloakTransformer(put_enable)
    new_ast = ct.visit(ast)

    # restore all parent pointers and identifier targets
    # XYZ set_parents(new_ast)
    # XYZ link_identifiers(new_ast)
    return new_ast


class StateNameCollector(AstVisitor):
    def __init__(self):
        super().__init__()
        self.privacy_related_states_set = set()
        self.privacy_related_functions_set = set()
        self.states_set = set()

    def visitStateVariableDeclaration(self, ast: StateVariableDeclaration):
        self.states_set.add(ast.idf.name)
        if self.isPrivacyRelated(str(ast.annotated_type)):
            self.privacy_related_states_set.add(ast.idf.name)

    def visitIdentifierDeclaration(self, ast: IdentifierDeclaration):
        if self.isPrivacyRelated(str(ast.annotated_type)):
            self.privacy_related_functions_set.add(ast.get_related_function().idf.name)

    def isPrivacyRelated(self, name: str):
        return name.count('@') != name.count('@all')
    
    def getStates(self):
        return self.states_set

    def getPrivacyRelatedStates(self):
        return self.privacy_related_states_set

    def getPrivacyRelatedFunctions(self):
        return self.privacy_related_functions_set

class FunctionNameCollector(FunctionVisitor):
    def __init__(self, privacy_related_functions_set: set):
        super().__init__()
        self.function_names = set()
        self.privacy_related_funciton_set = privacy_related_functions_set

    def visitConstructorOrFunctionDefinition(self, ast: ConstructorOrFunctionDefinition):
        self.function_names.add(ast.name)
        for param in ast.parameters:
            if self.isPrivacyRelated(str(param.annotated_type)):
                self.privacy_related_funciton_set.add(ast.name)
                break

    def isPrivacyRelated(self, name: str):
        return name.count('@') != name.count('@all')

    def getFunctionNames(self):
        return self.function_names

    def getPrivacyRelatedFunctions(self):
        return self.privacy_related_funciton_set
class CallGraphGenerator(FunctionVisitor):
    def __init__(self, function_names: list, privacy_related_functions_set: set, states_set: set, privacy_related_states_set: set):
        super().__init__()
        self.function_names = function_names
        self.call_graph = [[False for i in range(len(function_names))] for i in range(len(function_names))]
        self.privacy_related_funciton_set = privacy_related_functions_set
        self.states_set = states_set
        self.privacy_related_states_set = privacy_related_states_set
    
    def visitAST(self, ast: AST):
        for child in ast.children():
            self.visit(child)

    def functionName2Index(self, function_name: str):
        return self.function_names.index(function_name)

    def functionIndex2Name(self, index: int):
        return self.function_names[index]

    def visitFunctionCallExpr(self, ast: FunctionCallExpr):
        self.visitAST(ast)
        if isinstance(ast.func, LocationExpr):
            if ast.func.target.idf.name in self.function_names:
                self.call_graph[self.functionName2Index(ast.get_related_function().idf.name)][self.functionName2Index(ast.func.target.idf.name)] = True

    def visitIdentifier(self, ast: Identifier):
        if str(ast) in self.states_set and str(ast) in self.privacy_related_states_set:
            self.privacy_related_funciton_set.add(ast.get_related_function().idf.name)

    def markPrivacyIteratively(self):
        privacy_related_funciton_num = len(self.privacy_related_funciton_set)
        while True:
            for function_name in self.function_names:
                if function_name in self.privacy_related_funciton_set:
                    for i in range(len(self.function_names)):
                        if self.call_graph[i][self.functionName2Index(function_name)]:
                            self.privacy_related_funciton_set.add(self.functionIndex2Name(i))
            if privacy_related_funciton_num == len(self.privacy_related_funciton_set):
                # Convergence
                break
            else:
                privacy_related_funciton_num = len(self.privacy_related_funciton_set)

class PrivacyRelatedMarker(FunctionVisitor):
    def __init__(self, privacy_related_funciton_set: set):
        super().__init__()
        self.privacy_related_funciton_set = privacy_related_funciton_set

    def visitConstructorOrFunctionDefinition(self, ast: ConstructorOrFunctionDefinition):
        if ast.name in self.privacy_related_funciton_set:
            ast.is_privacy_related_function = True

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

    def __init__(self, put_enable):
        self.put_enable = put_enable
        super().__init__()

    @staticmethod
    def import_contract(cname: str, su: SourceUnit):
        """
        Import contract 'vname' into the given source unit.

        :param cname: contract name (.sol filename stem must match contract type name)
        :param su: [SIDE EFFECT] source unit where contract should be imported
        """
        import_filename = f'./{cname}.sol'
        su.extra_head_parts.append(ast_module.ImportDirective(import_filename))
# 
    def add_extra_tail_parts(self, su: SourceUnit, c: ContractDefinition):
        # Add constant state variables for tee external contracts
        code_hash_hb = web3.Web3.keccak(su.private_contract_code.encode())
        policy_hash_hb = web3.Web3.keccak(su.generated_policy.encode())
        c.extra_tail_parts += [
            Comment("CloakService Variable"),
            SOL(f"address owner = msg.sender;"),
            SOL(f"uint constant teeCHash = {code_hash_hb.hex()};"),
            SOL(f"uint constant teePHash = {policy_hash_hb.hex()};"),
        ]

    def visitSourceUnit(self, ast: SourceUnit):
        #self.import_contract(cfg.service_contract_name, ast)

        for c in ast.contracts:
            self.transform_contract(ast, c)
            self.visitAST(ast)
        return ast

    def transform_contract(self, su: SourceUnit, c: ContractDefinition) -> ContractDefinition:
        """
        Transform an entire cloak contract into a public solidity contract.

        This:

        * transforms state variables, function bodies and signatures
        * import verification contracts
        * creates external wrapper functions for all public functions which require verification

        :param su: [SIDE EFFECTS] Source unit of which this contract is part of
        :param c: [SIDE EFFECTS] The contract to transform
        :return: The contract itself
        """
        self.add_extra_tail_parts(su, c)
        c = self.transform_tee_functions(su, c)

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

        """Transformer for state variable declarations and parameters"""
        var_decl_trafo = TeeVarDeclTransformer().visit_list(c.state_variable_declarations)
        # remove function except constructor
        if self.put_enable:
            # remove all privacy-related function except constructor
            is_privacy_related_function = lambda u: isinstance(u, ConstructorOrFunctionDefinition) and u.is_function and u.is_privacy_related_function
            c.units[:] = filter(lambda u: not is_privacy_related_function(u), c.units)
        else:
            # remove all origin function except constructor
            is_function = lambda u: isinstance(u, ConstructorOrFunctionDefinition) and u.is_function
            c.units[:] = filter(lambda u: not is_function(u), c.units)

        # append get_states and set_states
        c.extra_tail_parts += [self.get_states(su, c), self.set_states(su, c)]

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
            if state['is_constant']:
                continue
            state_type = states_types[state["name"]]
            if not state_type.is_mapping:
                basic_type += f"oldStates[{idx}] = abi.encode({i});\n"
                if state["owner"]["owner"] != "all" and is_cipher:
                    basic_type += f"oldStates[{idx+1}] = {state['name']}[0];\n"
                    basic_type += f"oldStates[{idx+2}] = {state['name']}[1];\n"
                    basic_type += f"oldStates[{idx+3}] = {state['name']}[2];\n"
                    basic_type += f"oldStates[{idx+4}] = {state['name']}[3];\n"
                    basic_type += f"oldStates[{idx+5}] = {state['name']}[4];\n"
                    idx += 6
                else:
                    basic_type += f"oldStates[{idx+1}] = abi.encode({state['name']});"
                    idx += 2
        return basic_type, idx

    @staticmethod
    def get_states_mapping_type(states: [Dict[str, any]], states_types: Dict[str, TypeName], idx: int, is_cipher = True) -> (str, int):
        mapping_type = ""
        for si, state in enumerate(states):
            if state['is_constant']:
                continue
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
                    for_body += f"oldStates[os_idx + {5 + mapping_depth} + i * {factor}] = {state['name']}{key_expr}[3];\n"
                    for_body += f"oldStates[os_idx + {6 + mapping_depth} + i * {factor}] = {state['name']}{key_expr}[4];\n"
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
                require(msg.sender == owner, 'msg.sender is not tee');
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
            if state['is_constant']:
                continue
            state_type = states_types[state["name"]]
            if not state_type.is_mapping:
                if state["owner"]["owner"] != "all" and is_cipher:
                    basic_type += f"{state['name']}[0] = data[{idx+1}] ;\n"
                    basic_type += f"{state['name']}[1] = data[{idx+2}] ;\n"
                    basic_type += f"{state['name']}[2] = data[{idx+3}] ;\n"
                    basic_type += f"{state['name']}[3] = data[{idx+4}] ;\n"
                    basic_type += f"{state['name']}[4] = data[{idx+5}] ;\n"
                    idx += 6
                else:
                    basic_type += f"{state['name']} = abi.decode(data[{idx+1}], ({state_type}));\n"
                    idx += 2
        return basic_type, idx


    @staticmethod
    def set_states_mapping_type(states: [Dict[str, any]], states_types: Dict[str, TypeName], idx: int, is_cipher = True) -> (str, int):
        mapping_type = ""
        for si, state in enumerate(states):
            if state['is_constant']:
                continue
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
                    for_body += f"{state['name']}{key_expr}[3] = data[data_idx +{5+mapping_depth} + i * {factor}];\n"
                    for_body += f"{state['name']}{key_expr}[4] = data[data_idx +{6+mapping_depth} + i * {factor}];\n"
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
