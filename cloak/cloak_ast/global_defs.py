# BUILTIN SPECIAL TYPE DEFINITIONS
from cloak.cloak_ast.ast import AnnotatedTypeName, AssignmentStatement, FunctionTypeName, IdentifierDeclaration, Parameter, Identifier, RequireStatement, StructDefinition, \
    VariableDeclaration, TypeName, StateVariableDeclaration, IdentifierExpr, StructTypeName, Block, ConstructorOrFunctionDefinition
from cloak.cloak_ast.pointers.parent_setter import set_parents
from cloak.config import cfg
array_length_member = VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('length'))
array_push_member = VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('push'))


class GlobalDefs:
    # gasleft: ConstructorOrFunctionDefinition = ConstructorOrFunctionDefinition (
    #     idf=Identifier('gasleft'),
    #     parameters=[],
    #     modifiers=[],
    #     return_parameters=[Parameter([], annotated_type=AnnotatedTypeName.uint_all(), idf=Identifier(''))],
    #     body=Block([])
    # )
    # gasleft.idf.parent = gasleft

    set_code_hash: ConstructorOrFunctionDefinition = ConstructorOrFunctionDefinition(
        idf=Identifier('setCodeHash'),
        parameters=[Parameter([], annotated_type=AnnotatedTypeName.uint_all(), idf=Identifier('newCodeHash'))],
        modifiers=['external'],
        return_parameters=[],
        body=Block([
            RequireStatement(IdentifierExpr('msg').dot('sender').as_type(AnnotatedTypeName.address_all()).binop('==', IdentifierExpr('owner').as_type(AnnotatedTypeName.address_all()))),
            AssignmentStatement(IdentifierExpr(cfg.tee_code_hash_name), IdentifierExpr('setCodeHash'))
        ])
    )
    set_parents(set_code_hash)

    set_policy: ConstructorOrFunctionDefinition = ConstructorOrFunctionDefinition(
        idf=Identifier('setPolicy'),
        parameters=[Parameter([], annotated_type=AnnotatedTypeName.uint_all(), idf=Identifier('newPolicy'))],
        modifiers=['external'],
        return_parameters=[],
        body=Block([
            RequireStatement(IdentifierExpr('msg').dot('sender').as_type(AnnotatedTypeName.address_all()).binop('==', IdentifierExpr('owner').as_type(AnnotatedTypeName.address_all()))),
            AssignmentStatement(IdentifierExpr(cfg.tee_policy_hash_name), IdentifierExpr('newPolicy'))
        ])
    )
    set_parents(set_policy)

    address_struct: StructDefinition = StructDefinition(
        Identifier('<address>'), [
            VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('balance'))
        ]
    )
    set_parents(address_struct)

    address_payable_struct: StructDefinition = StructDefinition(
        Identifier('<address_payable>'), [
            VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('balance')),
            ConstructorOrFunctionDefinition(Identifier('send'), [Parameter([], AnnotatedTypeName.uint_all(), Identifier(''))], ['public'],
                                            [Parameter([], AnnotatedTypeName.bool_all(), Identifier(''))], Block([])),
            ConstructorOrFunctionDefinition(Identifier('transfer'), [Parameter([], AnnotatedTypeName.uint_all(), Identifier(''))], ['public'],
                                            [], Block([])),
        ]
    )
    address_payable_struct.members[1].can_be_private = False
    address_payable_struct.members[2].can_be_private = False
    set_parents(address_payable_struct)

    msg_struct: StructDefinition = StructDefinition(
        Identifier('<msg>'), [
            VariableDeclaration([], AnnotatedTypeName(TypeName.address_payable_type()), Identifier('sender')),
            VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('value')),
        ]
    )
    set_parents(msg_struct)

    block_struct: StructDefinition = StructDefinition(
        Identifier('<block>'), [
            VariableDeclaration([], AnnotatedTypeName(TypeName.address_payable_type()), Identifier('coinbase')),
            VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('difficulty')),
            VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('gaslimit')),
            VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('number')),
            VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('timestamp')),
        ]
    )
    set_parents(block_struct)

    tx_struct: StructDefinition = StructDefinition(
        Identifier('<tx>'), [
            VariableDeclaration([], AnnotatedTypeName.uint_all(), Identifier('gasprice')),
            VariableDeclaration([], AnnotatedTypeName(TypeName.address_payable_type()), Identifier('origin')),
        ]
    )
    set_parents(tx_struct)


class GlobalVars:
    msg: StateVariableDeclaration = StateVariableDeclaration(
        AnnotatedTypeName.all(StructTypeName([GlobalDefs.msg_struct.idf], GlobalDefs.msg_struct)), [],
        Identifier('msg'), None
    )
    msg.idf.parent = msg

    block: StateVariableDeclaration = StateVariableDeclaration(
        AnnotatedTypeName.all(StructTypeName([GlobalDefs.block_struct.idf], GlobalDefs.block_struct)), [],
        Identifier('block'), None
    )
    block.idf.parent = block

    tx: StateVariableDeclaration = StateVariableDeclaration(
        AnnotatedTypeName.all(StructTypeName([GlobalDefs.tx_struct.idf], GlobalDefs.tx_struct)), [],
        Identifier('tx'), None
    )
    tx.idf.parent = tx

    now: StateVariableDeclaration = StateVariableDeclaration(
        AnnotatedTypeName.uint_all(), [],
        Identifier('now'), None
    )
    now.idf.parent = now

    address: StateVariableDeclaration = StateVariableDeclaration(
        AnnotatedTypeName.uint_all(), [],
        Identifier('address'), None
    )
    address.idf.parent = address

    uint256: StateVariableDeclaration = StateVariableDeclaration(
        AnnotatedTypeName.uint_all(), [],
        Identifier('uint256'), None
    )
    uint256.idf.parent = uint256