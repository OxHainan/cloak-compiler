"""
This module provides functionality to transform a zkay AST into an equivalent public solidity AST + proof circuits
"""

import web3
import json
from typing import Dict, Optional, List, Tuple

from cloak.compiler.privacy.circuit_generation.circuit_helper import CircuitHelper
from cloak.compiler.privacy.library_contracts import bn128_scalar_field
from cloak.compiler.privacy.transformation.internal_call_transformer import transform_internal_calls, compute_transitive_circuit_io_sizes
from cloak.cloak_ast.visitor.transformer_visitor import AstTransformerVisitor
from cloak.compiler.privacy.transformation.zkp_transformer import ZkpVarDeclTransformer, ZkpExpressionTransformer, ZkpCircuitTransformer, \
    ZkpStatementTransformer
from cloak.compiler.privacy.transformation.tee_transformer import TeeVarDeclTransformer, TeeExpressionTransformer, \
    TeeStatementTransformer
from cloak.config import cfg
from cloak.cloak_ast.ast import AddressTypeName, Expression, ConstructorOrFunctionDefinition, IdentifierExpr, Mapping, TeeExpr, VariableDeclaration, \
    AnnotatedTypeName, \
    StateVariableDeclaration, Identifier, ExpressionStatement, SourceUnit, ReturnStatement, AST, \
    Comment, NumberLiteralExpr, StructDefinition, Array, FunctionCallExpr, StructTypeName, PrimitiveCastExpr, TypeName, \
    ContractTypeName, BlankLine, Block, RequireStatement, NewExpr, ContractDefinition, TupleExpr, PrivacyLabelExpr, \
    Parameter, \
    VariableDeclarationStatement, StatementList, CipherText, ArrayLiteralExpr, MeExpr
from cloak.cloak_ast.pointers.parent_setter import set_parents
from cloak.cloak_ast.pointers.symbol_table import link_identifiers
from cloak.cloak_ast.visitor.deep_copy import deep_copy
from cloak.cloak_ast.global_defs import GlobalDefs
from cloak.type_check.privacy_policy import PrivacyPolicyEncoder

def transform_ast(ast: AST) -> Tuple[AST, Dict[ConstructorOrFunctionDefinition, CircuitHelper]]:
    """
    Convert cloak to solidity AST + proof circuits

    :param ast: cloak AST
    :return: solidity AST and dictionary which maps all function definitions which require verification
             to the corresponding circuit helper instance.
    """
    ct = CloakTransformer()
    new_ast = ct.visit(ast)

    # restore all parent pointers and identifier targets
    set_parents(new_ast)
    link_identifiers(new_ast)
    return new_ast, ct.circuits


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

    To support verification, the functions themselves also need additional transformations:

    In the original zkay paper, all the circuit out parameters + the proof are added as additional parameters for all functions which
    require verification.
    This makes it impossible to simply call another function, since every function expects its out arguments + a proof.

    Zkay 2.0 uses an improved design, with the goal of supporting function calls in an elegant way.
    It is based on the following observations:

    1) zk proof verification is only possible in functions which are called externally via a transaction,
       as it requires offchain simulation to generate a valid zero knowledge proof.
    2) public functions can be called externally (transaction) as well as internally (called from other function)
    3) private and internal functions can only be called internally
    4) public functions which have private arguments, but don't contain any private expressions in their body (e.g. because they only
       contain assignments, which are public operations as long as the owner does not change), only need verification if they are called
       externally (since then the parameters are user supplied and thus their encryption needs to be verified)
    5) The difference between an external and an internal function can be reduced to argument encryption verification +
       proof verification via verification contract invocation

    From 1) follows, that the externally called function must also handle the verification of all transitively called functions
    Observations 2), 4) and 5) suggest, that it is sensible to split each public function into two different parts:

     a) An internal function which has the original function body and arguments
     b) An external function which does argument verification, calls the internal function and finally and invokes the verification contract
        (=> "External Wrapper function")

    This way, calling a public function from within another function works exactly the same as calling a private/internal function,
    zkay simply has to reroute the call to the internal function.
    It also means, that no resources are wasted when calling a function such as mentioned in 4) from another function, since in that case
    the internal function does not require verification.

    What's left is how to deal with 1). Zkay 2.0 uses the following solution:

    * | If a function is purely public (no private arguments, no private expressions in itself or any transitively called functions)
      | => No change in signature and no additional transformations
    * If an internal function requires verification (+), 4 additional arguments are added to its signature:

          1) a variable length array where public circuit inputs generated by this function should be stored
          2) a start index which determines at which index this function should start storing circuit inputs into the in array
          3) a variable length array containing public circuit outputs required in this function
          4) a start index which determines where in the uint array the out values for the current function call are located

      * A struct definition is added to the contract definition, which includes entries for every circuit and input variable with correct types.
      * At the beginning of the internal function, a variable of that struct type is declared and all circuit output variables from the out array \
        parameter are deserialized into the struct.
      * Within the function body, all circuit inputs are stored into the struct and outputs are read from the struct.
      * At the end of the internal function, all circuit input variables in the struct are serialized into the in array parameter.

      When a function calls another function which requires verification, the start indices for in and out array are advanced such that
      they point to the correct sections and the in/out arrays + new start indices are added to the arguments of the call.
      If the called function does not require verification, it is simply called without any additional arguments.

      | (+) An internal function requires verification if it contains private expressions.
      |     *Note*: a function body can contain private variables (!= @all) without containing private expressions, \
                  since assignment of encrypted variables with the same owner is a public operation.

    * If a function is an external wrapper, 2 additional arguments are added to its signature:

          1) a variable length array containing public circuit outputs for the function itself and all transitively called functions
             If we have a call hierarchy like this::

                func f():
                    calls g(x) which calls h(x)
                    calls h(x)

             then the layout in the output array (same for the input array defined below) will be: f outs | g outs | h outs | h outs
             (i.e. the current functions circuit outputs come first, followed by those of the called functions in the function call order
             (according to AST traversal order))
          2) a zero knowledge proof

      * At the beginning of the external wrapper function a dynamic array is allocated which is large enough to store all circuit inputs
        from all transitively called functions.
      * Next all encrypted arguments are stored in the in array (since the circuit will verify the encryption)
      * Then the wrapper requests all statically known public keys (key for me or for a final address state variable), required by any
        of the transitively called functions, and also stores them in the in array.
      * The corresponding internal function is then called.
        If it requires verification, the newly allocated in array + the out array parameter + initial start indices
        (0 for out array, after last key for in array) are added as additional arguments.
      * Finally the verification contract is invoked to verify the proof (the in array was populated by the called functions themselves).
    """

    def __init__(self):
        super().__init__()


    @staticmethod
    def import_contract(cname: str, su: SourceUnit, corresponding_circuit: Optional[CircuitHelper] = None):
        """
        Import contract 'vname' into the given source unit.

        :param cname: contract name (.sol filename stem must match contract type name)
        :param su: [SIDE EFFECT] source unit where contract should be imported
        :param corresponding_circuit: [SIDE EFFECT] if contract is a verification contract, this should be the corresponding circuit helper
        """
        import_filename = f'./{cname}.sol'
        su.used_contracts.append(import_filename)

        if corresponding_circuit is not None:
            c_type = ContractTypeName([Identifier(cname)])
            corresponding_circuit.register_verification_contract_metadata(c_type, import_filename)

    @staticmethod
    def create_contract_variable(cname: str) -> StateVariableDeclaration:
        """Create a public constant state variable with which contract with name 'cname' can be accessed"""
        inst_idf = Identifier(cfg.get_contract_var_name(cname))
        c_type = ContractTypeName([Identifier(cname)])

        cast_0_to_c = PrimitiveCastExpr(c_type, NumberLiteralExpr(0))
        var_decl = StateVariableDeclaration(AnnotatedTypeName(c_type), ['public', 'constant'], inst_idf.clone(), cast_0_to_c)
        return var_decl

    def include_verification_contracts(self, su: SourceUnit, c: ContractDefinition) -> List[StateVariableDeclaration]:
        """
        Import all verification contracts for 'c' into 'su' and create state variable declarations for all of them + the pki contract.

        :param su: [SIDE EFFECT] source unit into which contracts should be imported
        :param c: contract for which verification contracts should be imported
        :return: list of all constant state variable declarations for the pki contract + all the verification contracts
        """
        c.contract_var_decls = [self.create_contract_variable(cfg.pki_contract_name)]
        c.contract_var_decls.append(self.create_contract_variable(cfg.tee_service_contract_name))
        
        for f in c.fcts_is_zkp:
            if f.requires_verification_when_external and f.has_side_effects:
                name = cfg.get_zk_verification_contract_name(c.idf.name, f.name)
                self.import_contract(name, su, self.circuits[f])
                c.contract_var_decls.append(self.create_contract_variable(name))

        return c.contract_var_decls

    @staticmethod
    def create_circuit_helper(fct: ConstructorOrFunctionDefinition, global_owners: List[PrivacyLabelExpr],
                              internal_circ: Optional[CircuitHelper] = None):
        """
        Create circuit helper for the given function.

        :param fct: function for which to create a circuit
        :param global_owners: list of all statically known privacy labels (me + final address state variables)
        :param internal_circ: the circuit of the internal function on which to base this circuit
                              (only used when creating the circuit of the external wrapper function)
        :return: new circuit helper
        """
        return CircuitHelper(fct, global_owners, ZkpExpressionTransformer, ZkpCircuitTransformer, internal_circ)

    def visitSourceUnit(self, ast: SourceUnit):
        self.import_contract(cfg.pki_contract_name, ast)
        self.import_contract(cfg.tee_service_contract_name, ast)

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
            fct.original_body = deep_copy(fct.body, with_types=True, with_analysis=True)
        
        c.fcts_is_zkp = [fct for fct in c.all_fcts if fct.is_zkp()]
        c.fcts_is_tee = [fct for fct in c.all_fcts if fct.is_tee()]

        # Split into functions which require verification and those which don't need a circuit helper
        self.circuits: Dict[ConstructorOrFunctionDefinition, CircuitHelper] = {}
        """Abstract circuits for all functions which require verification"""
        c.req_ext_zk_fcts, c.req_ext_tee_fcts = {}, {}
        c.new_fcts, c.new_constr = [], []
        for fct in c.all_fcts:
            assert isinstance(fct, ConstructorOrFunctionDefinition)
            if fct.requires_verification or fct.requires_verification_when_external:
                self.circuits[fct] = self.create_circuit_helper(fct, c.global_owners)

            if fct.requires_verification_when_external and fct.is_zkp():
                c.req_ext_zk_fcts[fct] = fct.parameters[:]
            elif fct.requires_verification_when_external and fct.is_tee():
                c.req_ext_tee_fcts[fct] = fct.parameters[:]
            elif fct.is_constructor:
                c.new_constr.append(fct)
            else:
                c.new_fcts.append(fct)

        self.include_verification_contracts(su, c)

        c.state_variable_declarations = [StateVariableDeclaration(AnnotatedTypeName.address_all(), ['public', 'constant'],
                                            Identifier(TeeExpr().name), TeeExpr())] \
                                    + c.state_variable_declarations

        c = self.transform_zkp_functions(su, c)
        c = self.transform_tee_functions(su, c)

        # new_fcts = [GlobalDefs.set_code_hash, GlobalDefs.set_policy] + new_fcts
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
        c.state_variable_declarations = var_decl_trafo.visit_list(c.state_variable_declarations)


        # Add constant state variables for external contracts
        code_hash_hb = web3.Web3.solidityKeccak(['bytes'], [c.code().encode()])
        print(code_hash_hb.hex())
        code_hash_decl = StateVariableDeclaration(AnnotatedTypeName.uint_all(), ['public', 'constant'],
                                                    Identifier(cfg.tee_code_hash_name),
                                                    NumberLiteralExpr(int(code_hash_hb.hex(), base=16)))
        policy_hash_hb = web3.Web3.solidityKeccak(['bytes'], [json.dumps(su.privacy_policy, cls=PrivacyPolicyEncoder, indent=2).encode()])
        print(policy_hash_hb.hex())
        policy_hash_decl = StateVariableDeclaration(AnnotatedTypeName.uint_all(), ['public', 'constant'],
                                                    Identifier(cfg.tee_policy_hash_name),
                                                    NumberLiteralExpr(int(policy_hash_hb.hex(), base=16)))

        c.state_variable_declarations = [code_hash_decl, policy_hash_decl, Comment()]\
                                        + Comment.comment_list('Helper Contracts', c.contract_var_decls)\
                                        + [Comment('User state variables')]\
                                        + c.state_variable_declarations

        # Transform function signatures
        for fct in c.fcts_is_tee:
            fct.parameters = var_decl_trafo.visit_list(fct.parameters)
        for fct in c.fcts_is_tee:
            fct.return_parameters = var_decl_trafo.visit_list(fct.return_parameters)
            fct.return_var_decls = var_decl_trafo.visit_list(fct.return_var_decls)

        # Transform bodies
        for fct in c.fcts_is_tee:
            # gen = self.circuits.get(fct, None)
            fct.body = TeeStatementTransformer().visit(fct.body)

        # Transform (internal) functions which require verification (add the necessary additional parameters and boilerplate code)

        # renqian TODO: delete internal calls
        # transform_internal_calls(c.fcts_is_tee, self.circuits)
        for fct in c.fcts_is_tee:
            self.create_tee_internal_verification_wrapper(fct)

        # Create external wrapper functions where necessary
        for f, params in c.req_ext_tee_fcts.items():
            ext_f, int_f = self.split_tee_into_external_and_internal_fct(f, params, c.global_owners)
            if ext_f.is_function:
                c.new_fcts.append(ext_f)
            else:
                c.new_constr.append(ext_f)
            c.new_fcts.append(int_f)
            
        return c

    def create_tee_internal_verification_wrapper(self, ast: ConstructorOrFunctionDefinition):
        """
        Add the necessary additional parameters and boiler plate code for verification support to the given function.

        :param ast: [SIDE EFFECT] Internal function which requires verification
        """
        stmts = []

        if cfg.is_symmetric_cipher() and 'pure' in ast.modifiers:
            # Symmetric trafo requires msg.sender access -> change from pure to view
            ast.modifiers = ['view' if mod == 'pure' else mod for mod in ast.modifiers]

        # Add additional params
        # renqian TODO: delete original paramaters
        # ast.parameters = []
        ast.add_param(Array(AnnotatedTypeName.uint_all()), cfg.tee_old_state_name)
        ast.add_param(Array(AnnotatedTypeName.uint_all()), cfg.tee_read_name)
        ast.add_param(AnnotatedTypeName.uint_all(), f'{cfg.tee_read_name}_start_idx')
        ast.add_param(Array(AnnotatedTypeName.uint_all()), cfg.tee_mutate_name)
        ast.add_param(AnnotatedTypeName.uint_all(), f'{cfg.tee_mutate_name}_start_idx')

        # # Verify that in/out parameters have correct size
        # out_start_idx, in_start_idx = IdentifierExpr(f'{cfg.tee_mutate_name}_start_idx'), IdentifierExpr(f'{cfg.tee_read_name}_start_idx')
        # out_var, in_var = IdentifierExpr(cfg.tee_mutate_name), IdentifierExpr(cfg.tee_read_name).as_type(Array(AnnotatedTypeName.uint_all()))
        # stmts.append(RequireStatement(out_start_idx.binop('+', NumberLiteralExpr(circuit.out_size_trans)).binop('<=', out_var.dot('length'))))
        # stmts.append(RequireStatement(in_start_idx.binop('+', NumberLiteralExpr(circuit.in_size_trans)).binop('<=', in_var.dot('length'))))

        # Declare return variable if necessary
        if ast.return_parameters:
            stmts += Comment.comment_list("Declare return variables", [VariableDeclarationStatement(vd) for vd in ast.return_var_decls])

        # # Deserialize out array (if any)
        # deserialize_stmts = []
        # offset = 0
        # for s in circuit.output_idfs:
        #     deserialize_stmts.append(s.deserialize(cfg.tee_mutate_name, out_start_idx, offset))
        #     if isinstance(s.t, CipherText) and cfg.is_symmetric_cipher():
        #         # Assign sender field to user-encrypted values if necessary
        #         sender_key = in_var.index(0)
        #         deserialize_stmts.append(s.get_loc_expr().index(cfg.cipher_payload_len).assign(sender_key))
        #     offset += s.t.size_in_uints
        # if deserialize_stmts:
        #     stmts.append(StatementList(Comment.comment_wrap_block("Deserialize output values", deserialize_stmts), excluded_from_simulation=True))

        # Include original transformed function body
        stmts += ast.body.statements

        # # Serialize in parameters to in array (if any)
        # serialize_stmts = []
        # offset = 0
        # for s in circuit.input_idfs:
        #     serialize_stmts += [s.serialize(cfg.tee_read_name, in_start_idx, offset)]
        #     offset += s.t.size_in_uints
        # if offset:
        #     stmts.append(Comment())
        #     stmts += Comment.comment_wrap_block('Serialize input values', serialize_stmts)

        # # Add return statement at the end if necessary
        # # (was previously replaced by assignment to return_var by ZkpStatementTransformer)
        # if circuit.has_return_var:
        #     stmts.append(ReturnStatement(TupleExpr([IdentifierExpr(vd.idf.clone()).override(target=vd) for vd in ast.return_var_decls])))

        ast.body.statements[:] = stmts

    def split_tee_into_external_and_internal_fct(self, f: ConstructorOrFunctionDefinition, original_params: List[Parameter],
                                             global_owners: List[PrivacyLabelExpr]) -> Tuple[ConstructorOrFunctionDefinition,
                                                                                             ConstructorOrFunctionDefinition]:
        """
        Take public function f and split it into an internal function and an external wrapper function.

        :param f: [SIDE EFFECT] function to split (at least requires_verification_if_external)
        :param original_params: list of transformed function parameters without additional parameters added due to transformation
        :param global_owners: list of static labels (me + final address state variable identifiers)
        :return: Tuple of newly created external and internal function definitions
        """
        assert f.requires_verification_when_external

        # Create new empty function with same parameters as original -> external wrapper
        if f.is_function:
            new_modifiers = ['external']
            original_params = [deep_copy(p, with_types=True).with_changed_storage('memory', 'calldata') for p in original_params]
        else:
            new_modifiers = ['public']
        if f.is_payable:
            new_modifiers.append('payable')

        requires_proof = True
        if not f.has_side_effects:
            requires_proof = False
            new_modifiers.append('view')
        new_f = ConstructorOrFunctionDefinition(f.idf, [], new_modifiers, f.return_parameters, Block([]))

        # Make original function internal
        f.idf = Identifier(cfg.get_tee_internal_name(f))
        f.modifiers = ['internal' if mod == 'public' else mod for mod in f.modifiers if mod != 'payable']
        f.requires_verification_when_external = False

        # # Create new circuit for external function
        # circuit = self.create_circuit_helper(new_f, global_owners, self.circuits[f])
        # if not f.requires_verification:
        #     del self.circuits[f]
        # self.circuits[new_f] = circuit

        # Set meta attributes and populate body
        new_f.requires_verification = True
        new_f.requires_verification_when_external = True
        new_f.called_functions = f.called_functions
        new_f.called_functions[f] = None
        new_f.body = self.create_tee_external_wrapper_body(f, original_params, requires_proof)

        # Add out and proof parameter to external wrapper
        storage_loc = 'calldata' if new_f.is_function else 'memory'
        new_f.add_param(Array(AnnotatedTypeName.uint_all()), Identifier(cfg.tee_read_name), storage_loc)
        new_f.add_param(Array(AnnotatedTypeName.uint_all()), Identifier(cfg.tee_mutate_name), storage_loc)

        if requires_proof:
            new_f.add_param(AnnotatedTypeName.tee_proof_type(), Identifier(cfg.tee_proof_param_name), storage_loc)

        return new_f, f

    @staticmethod
    def create_tee_external_wrapper_body(int_fct: ConstructorOrFunctionDefinition, original_params: List[Parameter],
                                        requires_proof: bool) -> Block:
        """
        Return Block with external wrapper function body.

        :param int_fct: corresponding internal function
        :param original_params: list of transformed function parameters without additional parameters added due to transformation
        :return: body with wrapper code
        """
        stmts = []

        # # Verify that out parameter has correct size
        # stmts += [RequireStatement(IdentifierExpr(cfg.tee_mutate_name).dot('length').binop('==', NumberLiteralExpr(ext_circuit.out_size_trans)))]

        # IdentifierExpr for array var holding serialized public circuit inputs
        read_arr_var = IdentifierExpr(cfg.tee_read_name).as_type(Array(AnnotatedTypeName.uint_all()))
        mutate_arr_var = IdentifierExpr(cfg.tee_mutate_name).as_type(Array(AnnotatedTypeName.uint_all()))

        # # Find index of me's public key in requested_global_keys
        # glob_me_key_index = -1
        # for idx, e in enumerate(ext_circuit.requested_global_keys):
        #     if isinstance(e, MeExpr):
        #         glob_me_key_index = idx
        #         break

        # # Request static public keys
        # offset = 0
        # key_req_stmts = []
        # if ext_circuit.requested_global_keys:
        #     # Ensure that me public key is stored starting at in[0]
        #     keys = [key for key in ext_circuit.requested_global_keys]
        #     if glob_me_key_index != -1:
        #         (keys[0], keys[glob_me_key_index]) = (keys[glob_me_key_index], keys[0])

        #     tmp_key_var = Identifier('_tmp_key')
        #     key_req_stmts.append(tmp_key_var.decl_var(AnnotatedTypeName.key_type()))
        #     for key_owner in keys:
        #         idf, assignment = ext_circuit.request_public_key(key_owner, ext_circuit.get_glob_key_name(key_owner))
        #         assignment.lhs = IdentifierExpr(tmp_key_var.clone())
        #         key_req_stmts.append(assignment)

        #         # Manually add to circuit inputs
        #         key_req_stmts.append(in_arr_var.slice(offset, cfg.key_len).assign(IdentifierExpr(tmp_key_var.clone()).slice(0, cfg.key_len)))
        #         offset += cfg.key_len
        #         assert offset == ext_circuit.in_size

        # # Check encrypted parameters
        # param_stmts = []
        # for p in original_params:
        #     """ * of T_e rule 8 """
        #     if p.annotated_type.is_cipher():
        #         assign_stmt = in_arr_var.slice(offset, cfg.cipher_payload_len).assign(IdentifierExpr(p.idf.clone()).slice(0, cfg.cipher_payload_len))
        #         ext_circuit.ensure_parameter_encryption(assign_stmt, p)

        #         # Manually add to circuit inputs
        #         param_stmts.append(assign_stmt)
        #         offset += cfg.cipher_payload_len

        # if cfg.is_symmetric_cipher():
        #     # Populate sender field of encrypted parameters
        #     copy_stmts = []
        #     for p in original_params:
        #         if p.annotated_type.is_cipher():
        #             sender_key = in_arr_var.index(0)
        #             idf = IdentifierExpr(p.idf.clone()).as_type(p.annotated_type.clone())
        #             lit = ArrayLiteralExpr([idf.clone().index(i) for i in range(cfg.cipher_payload_len)] + [sender_key])
        #             copy_stmts.append(VariableDeclarationStatement(VariableDeclaration([], p.annotated_type.clone(), p.idf.clone(), 'memory'), lit))
        #     if copy_stmts:
        #         param_stmts += [Comment(), Comment('Copy from calldata to memory and set sender field')] + copy_stmts

        #     assert glob_me_key_index != -1, "Symmetric cipher but did not request me key"

        # # Declare in array
        # new_in_array_expr = NewExpr(AnnotatedTypeName(TypeName.dyn_uint_array()), [NumberLiteralExpr(5)])
        # in_var_decl = in_arr_var.idf.decl_var(TypeName.dyn_uint_array(), new_in_array_expr)
        # stmts.append(in_var_decl)
        # stmts.append(Comment())

        stmts.append(Comment("Constant function hash"))
        func_hash_var = IdentifierExpr(cfg.tee_func_hash_name).as_type(AnnotatedTypeName(TypeName.uint_type()))
        func_hash_var_decl = func_hash_var.idf.decl_var(TypeName.uint_type(), NumberLiteralExpr(0x5B38Da6a701c568545dCfcB03FcB875f56beddC4))
        stmts.append(func_hash_var_decl)

        old_state_arr_var = IdentifierExpr(cfg.tee_old_state_name).as_type(Array(AnnotatedTypeName.uint_all()))
        old_state_var_decl = old_state_arr_var.idf.decl_var(TypeName.dyn_uint_array(), None)
        stmts.append(old_state_var_decl)
        stmts.append(Comment())

        # Initialize index of read, mutate and old state
        stmts.append(Comment("Initialize start index of read, mutate and old state"))
        read_start_idx = IdentifierExpr(f'{cfg.tee_read_name}_start_idx').as_type(AnnotatedTypeName(TypeName.uint_type()))
        read_start_idx_var_decl = read_start_idx.idf.decl_var(TypeName.uint_type(), NumberLiteralExpr(0))
        stmts.append(read_start_idx_var_decl)

        mutate_start_idx = IdentifierExpr(f'{cfg.tee_mutate_name}_start_idx').as_type(AnnotatedTypeName(TypeName.uint_type()))
        mutate_start_idx_var_decl = mutate_start_idx.idf.decl_var(TypeName.uint_type(), NumberLiteralExpr(0))
        stmts.append(mutate_start_idx_var_decl)
        stmts.append(Comment())

        # stmts += Comment.comment_wrap_block('Request static public keys', key_req_stmts)
        # stmts += Comment.comment_wrap_block('Backup private arguments for verification', param_stmts)

        # Call internal function
        # args = [IdentifierExpr(param.idf.clone()) for param in original_params]
        args = []
        args += [old_state_arr_var, read_arr_var.clone(), read_start_idx,
                    mutate_arr_var, mutate_start_idx]

        internal_call = FunctionCallExpr(IdentifierExpr(int_fct.idf.clone()).override(target=int_fct), args)
        # internal_call.sec_start_offset = 0


        if int_fct.return_parameters:
            stmts += Comment.comment_list("Declare return variables", [VariableDeclarationStatement(deep_copy(vd)) for vd in int_fct.return_var_decls])
            in_call = TupleExpr([IdentifierExpr(vd.idf.clone()) for vd in int_fct.return_var_decls]).assign(internal_call)
        else:
            in_call = ExpressionStatement(internal_call)
        stmts.append(Comment("Call internal function"))
        stmts.append(in_call)
        stmts.append(Comment())

        # Call verifier
        if requires_proof:
            verifier = IdentifierExpr(f'{cfg.tee_service_contract_name}_inst')

            get_hash_args = [old_state_arr_var]
            old_state_hash_var = IdentifierExpr(cfg.tee_old_state_hash_name).as_type(AnnotatedTypeName(TypeName.uint_type()))
            old_state_var_decl = old_state_hash_var.idf.decl_var(TypeName.uint_type(), verifier.call(cfg.tee_get_hash_function_name, get_hash_args))
            stmts.append(old_state_var_decl)

            verify_args = [IdentifierExpr(cfg.tee_proof_param_name), IdentifierExpr(cfg.tee_code_hash_name), IdentifierExpr(cfg.tee_policy_hash_name), IdentifierExpr(cfg.tee_func_hash_name), IdentifierExpr(cfg.tee_old_state_hash_name)]
            verify = RequireStatement(verifier.call(cfg.tee_verification_function_name, verify_args))
            stmts.append(StatementList([Comment('Verify tee proof of execution'), verify], excluded_from_simulation=True))

        # Add return statement at the end if necessary
        if int_fct.return_parameters:
            stmts.append(ReturnStatement(TupleExpr([IdentifierExpr(vd.idf.clone()) for vd in int_fct.return_var_decls])))

        return Block(stmts)










    def transform_zkp_functions(self, su: SourceUnit, c: ContractDefinition):
        """
        Transform the ZKP funtions into public functions and corresponding circuits

        This:

        * transforms state variables, function bodies and signatures
        * import verification contracts
        * adds zk_data structs for each function with verification \
          (to store circuit I/O, to bypass solidity stack limit and allow for easy assignment of array variables),
        * creates external wrapper functions for all public functions which require verification
        * adds circuit IO serialization/deserialization code from/to zk_data struct to all functions which require verification.

        :param su: [SIDE EFFECTS] Source unit of which this contract is part of
        :param c: [SIDE EFFECTS] The contract to transform
        :return: The contract itself
        """

        var_decl_trafo = ZkpVarDeclTransformer()
        """Transformer for state variable declarations and parameters"""

        # Transform types of normal state variables
        c.state_variable_declarations = var_decl_trafo.visit_list(c.state_variable_declarations)

        # Add constant state variables for external contracts and field prime
        field_prime_decl = StateVariableDeclaration(AnnotatedTypeName.uint_all(), ['public', 'constant'],
                                                    Identifier(cfg.zk_field_prime_var_name),
                                                    NumberLiteralExpr(bn128_scalar_field))

        c.state_variable_declarations = [field_prime_decl, Comment()]\
                                        + Comment.comment_list('Helper Contracts', c.contract_var_decls)\
                                        + [Comment('User state variables')]\
                                        + c.state_variable_declarations

        # Transform signatures
        for f in c.fcts_is_zkp:
            f.parameters = var_decl_trafo.visit_list(f.parameters)
        for f in c.function_definitions:
            f.return_parameters = var_decl_trafo.visit_list(f.return_parameters)
            f.return_var_decls = var_decl_trafo.visit_list(f.return_var_decls)

        # Transform bodies
        for fct in c.fcts_is_zkp:
            gen = self.circuits.get(fct, None)
            fct.body = ZkpStatementTransformer(gen).visit(fct.body)

        # Transform (internal) functions which require verification (add the necessary additional parameters and boilerplate code)
        compute_transitive_circuit_io_sizes(c.fcts_is_zkp, self.circuits)
        transform_internal_calls(c.fcts_is_zkp, self.circuits)
        for f in c.fcts_is_zkp:
            circuit = self.circuits[f]
            assert circuit.requires_verification()
            if circuit.requires_zk_data_struct():
                # Add zk data struct for f to contract
                zk_data_struct = StructDefinition(Identifier(circuit.zk_data_struct_name), [
                    VariableDeclaration([], AnnotatedTypeName(idf.t), idf.clone(), '')
                    for idf in circuit.output_idfs + circuit.input_idfs
                ])
                c.struct_definitions.append(zk_data_struct)
            self.create_zkp_internal_verification_wrapper(f)

        # Create external wrapper functions where necessary
        for f, params in c.req_ext_zk_fcts.items():
            ext_f, int_f = self.split_zkp_into_external_and_internal_fct(f, params, c.global_owners)
            if ext_f.is_function:
                c.new_fcts.append(ext_f)
            else:
                c.new_constr.append(ext_f)
            c.new_fcts.append(int_f)

        return c

    def create_zkp_internal_verification_wrapper(self, ast: ConstructorOrFunctionDefinition):
        """
        Add the necessary additional parameters and boiler plate code for verification support to the given function.

        :param ast: [SIDE EFFECT] Internal function which requires verification
        """
        circuit = self.circuits[ast]
        stmts = []

        if cfg.is_symmetric_cipher() and 'pure' in ast.modifiers:
            # Symmetric trafo requires msg.sender access -> change from pure to view
            ast.modifiers = ['view' if mod == 'pure' else mod for mod in ast.modifiers]

        # Add additional params
        ast.add_param(Array(AnnotatedTypeName.uint_all()), cfg.zk_in_name)
        ast.add_param(AnnotatedTypeName.uint_all(), f'{cfg.zk_in_name}_start_idx')
        ast.add_param(Array(AnnotatedTypeName.uint_all()), cfg.zk_out_name)
        ast.add_param(AnnotatedTypeName.uint_all(), f'{cfg.zk_out_name}_start_idx')

        # Verify that in/out parameters have correct size
        out_start_idx, in_start_idx = IdentifierExpr(f'{cfg.zk_out_name}_start_idx'), IdentifierExpr(f'{cfg.zk_in_name}_start_idx')
        out_var, in_var = IdentifierExpr(cfg.zk_out_name), IdentifierExpr(cfg.zk_in_name).as_type(Array(AnnotatedTypeName.uint_all()))
        stmts.append(RequireStatement(out_start_idx.binop('+', NumberLiteralExpr(circuit.out_size_trans)).binop('<=', out_var.dot('length'))))
        stmts.append(RequireStatement(in_start_idx.binop('+', NumberLiteralExpr(circuit.in_size_trans)).binop('<=', in_var.dot('length'))))

        # Declare zk_data struct var (if needed)
        if circuit.requires_zk_data_struct():
            zk_struct_type = StructTypeName([Identifier(circuit.zk_data_struct_name)])
            stmts += [Identifier(cfg.zk_data_var_name).decl_var(zk_struct_type), BlankLine()]

        # Declare return variable if necessary
        if ast.return_parameters:
            stmts += Comment.comment_list("Declare return variables", [VariableDeclarationStatement(vd) for vd in ast.return_var_decls])

        # Deserialize out array (if any)
        deserialize_stmts = []
        offset = 0
        for s in circuit.output_idfs:
            deserialize_stmts.append(s.deserialize(cfg.zk_out_name, out_start_idx, offset))
            if isinstance(s.t, CipherText) and cfg.is_symmetric_cipher():
                # Assign sender field to user-encrypted values if necessary
                sender_key = in_var.index(0)
                deserialize_stmts.append(s.get_loc_expr().index(cfg.cipher_payload_len).assign(sender_key))
            offset += s.t.size_in_uints
        if deserialize_stmts:
            stmts.append(StatementList(Comment.comment_wrap_block("Deserialize output values", deserialize_stmts), excluded_from_simulation=True))

        # Include original transformed function body
        stmts += ast.body.statements

        # Serialize in parameters to in array (if any)
        serialize_stmts = []
        offset = 0
        for s in circuit.input_idfs:
            serialize_stmts += [s.serialize(cfg.zk_in_name, in_start_idx, offset)]
            offset += s.t.size_in_uints
        if offset:
            stmts.append(Comment())
            stmts += Comment.comment_wrap_block('Serialize input values', serialize_stmts)

        # Add return statement at the end if necessary
        # (was previously replaced by assignment to return_var by ZkpStatementTransformer)
        if circuit.has_return_var:
            stmts.append(ReturnStatement(TupleExpr([IdentifierExpr(vd.idf.clone()).override(target=vd) for vd in ast.return_var_decls])))

        ast.body.statements[:] = stmts

    def split_zkp_into_external_and_internal_fct(self, f: ConstructorOrFunctionDefinition, original_params: List[Parameter],
                                             global_owners: List[PrivacyLabelExpr]) -> Tuple[ConstructorOrFunctionDefinition,
                                                                                             ConstructorOrFunctionDefinition]:
        """
        Take public function f and split it into an internal function and an external wrapper function.

        :param f: [SIDE EFFECT] function to split (at least requires_verification_if_external)
        :param original_params: list of transformed function parameters without additional parameters added due to transformation
        :param global_owners: list of static labels (me + final address state variable identifiers)
        :return: Tuple of newly created external and internal function definitions
        """
        assert f.requires_verification_when_external

        # Create new empty function with same parameters as original -> external wrapper
        if f.is_function:
            new_modifiers = ['external']
            original_params = [deep_copy(p, with_types=True).with_changed_storage('memory', 'calldata') for p in original_params]
        else:
            new_modifiers = ['public']
        if f.is_payable:
            new_modifiers.append('payable')

        requires_proof = True
        if not f.has_side_effects:
            requires_proof = False
            new_modifiers.append('view')
        new_f = ConstructorOrFunctionDefinition(f.idf, original_params, new_modifiers, f.return_parameters, Block([]))

        # Make original function internal
        f.idf = Identifier(cfg.get_zk_internal_name(f))
        f.modifiers = ['internal' if mod == 'public' else mod for mod in f.modifiers if mod != 'payable']
        f.requires_verification_when_external = False

        # Create new circuit for external function
        circuit = self.create_circuit_helper(new_f, global_owners, self.circuits[f])
        if not f.requires_verification:
            del self.circuits[f]
        self.circuits[new_f] = circuit

        # Set meta attributes and populate body
        new_f.requires_verification = True
        new_f.requires_verification_when_external = True
        new_f.called_functions = f.called_functions
        new_f.called_functions[f] = None
        new_f.body = self.create_zkp_external_wrapper_body(f, circuit, original_params, requires_proof)

        # Add out and proof parameter to external wrapper
        storage_loc = 'calldata' if new_f.is_function else 'memory'
        new_f.add_param(Array(AnnotatedTypeName.uint_all()), Identifier(cfg.zk_out_name), storage_loc)

        if requires_proof:
            new_f.add_param(AnnotatedTypeName.zk_proof_type(), Identifier(cfg.zk_proof_param_name), storage_loc)

        return new_f, f

    @staticmethod
    def create_zkp_external_wrapper_body(int_fct: ConstructorOrFunctionDefinition, ext_circuit: CircuitHelper,
                                     original_params: List[Parameter], requires_proof: bool) -> Block:
        """
        Return Block with external wrapper function body.

        :param int_fct: corresponding internal function
        :param ext_circuit: [SIDE EFFECT] circuit helper of the external wrapper function
        :param original_params: list of transformed function parameters without additional parameters added due to transformation
        :return: body with wrapper code
        """
        has_priv_args = any([p.annotated_type.is_cipher() for p in original_params])
        stmts = []

        if has_priv_args:
            ext_circuit._require_public_key_for_label_at(None, Expression.me_expr())
        if cfg.is_symmetric_cipher():
            # Make sure msg.sender's key pair is available in the circuit
            assert any(isinstance(k, (MeExpr, TeeExpr)) for k in ext_circuit.requested_global_keys) \
                   or has_priv_args, "requires verification => both sender keys required"
            stmts += ext_circuit.request_private_key()

        # Verify that out parameter has correct size
        stmts += [RequireStatement(IdentifierExpr(cfg.zk_out_name).dot('length').binop('==', NumberLiteralExpr(ext_circuit.out_size_trans)))]

        # IdentifierExpr for array var holding serialized public circuit inputs
        in_arr_var = IdentifierExpr(cfg.zk_in_name).as_type(Array(AnnotatedTypeName.uint_all()))

        # Find index of me's public key in requested_global_keys
        glob_me_key_index = -1
        for idx, e in enumerate(ext_circuit.requested_global_keys):
            if isinstance(e, MeExpr):
                glob_me_key_index = idx
                break
                

        # Request static public keys
        offset = 0
        key_req_stmts = []
        if ext_circuit.requested_global_keys:
            # Ensure that me public key is stored starting at in[0]
            keys = [key for key in ext_circuit.requested_global_keys]
            if glob_me_key_index != -1:
                (keys[0], keys[glob_me_key_index]) = (keys[glob_me_key_index], keys[0])

            tmp_key_var = Identifier('_tmp_key')
            key_req_stmts.append(tmp_key_var.decl_var(AnnotatedTypeName.key_type()))
            for key_owner in keys:
                idf, assignment = ext_circuit.request_public_key(key_owner, ext_circuit.get_glob_key_name(key_owner))
                assignment.lhs = IdentifierExpr(tmp_key_var.clone())
                key_req_stmts.append(assignment)

                # Manually add to circuit inputs
                key_req_stmts.append(in_arr_var.slice(offset, cfg.key_len).assign(IdentifierExpr(tmp_key_var.clone()).slice(0, cfg.key_len)))
                offset += cfg.key_len
                assert offset == ext_circuit.in_size

        # Check encrypted parameters
        param_stmts = []
        for p in original_params:
            """ * of T_e rule 8 """
            if p.annotated_type.is_cipher():
                assign_stmt = in_arr_var.slice(offset, cfg.cipher_payload_len).assign(IdentifierExpr(p.idf.clone()).slice(0, cfg.cipher_payload_len))
                ext_circuit.ensure_parameter_encryption(assign_stmt, p)

                # Manually add to circuit inputs
                param_stmts.append(assign_stmt)
                offset += cfg.cipher_payload_len

        if cfg.is_symmetric_cipher():
            # Populate sender field of encrypted parameters
            copy_stmts = []
            for p in original_params:
                if p.annotated_type.is_cipher():
                    sender_key = in_arr_var.index(0)
                    idf = IdentifierExpr(p.idf.clone()).as_type(p.annotated_type.clone())
                    lit = ArrayLiteralExpr([idf.clone().index(i) for i in range(cfg.cipher_payload_len)] + [sender_key])
                    copy_stmts.append(VariableDeclarationStatement(VariableDeclaration([], p.annotated_type.clone(), p.idf.clone(), 'memory'), lit))
            if copy_stmts:
                param_stmts += [Comment(), Comment('Copy from calldata to memory and set sender field')] + copy_stmts

            assert glob_me_key_index != -1, "Symmetric cipher but did not request me key"

        # Declare in array
        new_in_array_expr = NewExpr(AnnotatedTypeName(TypeName.dyn_uint_array()), [NumberLiteralExpr(ext_circuit.in_size_trans)])
        in_var_decl = in_arr_var.idf.decl_var(TypeName.dyn_uint_array(), new_in_array_expr)
        stmts.append(in_var_decl)
        stmts.append(Comment())
        stmts += Comment.comment_wrap_block('Request static public keys', key_req_stmts)
        stmts += Comment.comment_wrap_block('Backup private arguments for verification', param_stmts)

        # Call internal function
        args = [IdentifierExpr(param.idf.clone()) for param in original_params]
        internal_call = FunctionCallExpr(IdentifierExpr(int_fct.idf.clone()).override(target=int_fct), args)
        internal_call.sec_start_offset = ext_circuit.priv_in_size

        if int_fct.requires_verification:
            ext_circuit.call_function(internal_call)
            args += [in_arr_var.clone(), NumberLiteralExpr(ext_circuit.in_size),
                     IdentifierExpr(cfg.zk_out_name), NumberLiteralExpr(ext_circuit.out_size)]

        if int_fct.return_parameters:
            stmts += Comment.comment_list("Declare return variables", [VariableDeclarationStatement(deep_copy(vd)) for vd in int_fct.return_var_decls])
            in_call = TupleExpr([IdentifierExpr(vd.idf.clone()) for vd in int_fct.return_var_decls]).assign(internal_call)
        else:
            in_call = ExpressionStatement(internal_call)
        stmts.append(Comment("Call internal function"))
        stmts.append(in_call)
        stmts.append(Comment())

        # Call verifier
        if requires_proof:
            verifier = IdentifierExpr(cfg.get_contract_var_name(ext_circuit.verifier_contract_type.code()))
            verifier_args = [IdentifierExpr(cfg.zk_proof_param_name), IdentifierExpr(cfg.zk_in_name), IdentifierExpr(cfg.zk_out_name)]
            verify = ExpressionStatement(verifier.call(cfg.zk_verification_function_name, verifier_args))
            stmts.append(StatementList([Comment('Verify zk proof of execution'), verify], excluded_from_simulation=True))

        # Add return statement at the end if necessary
        if int_fct.return_parameters:
            stmts.append(ReturnStatement(TupleExpr([IdentifierExpr(vd.idf.clone()) for vd in int_fct.return_var_decls])))

        return Block(stmts)
