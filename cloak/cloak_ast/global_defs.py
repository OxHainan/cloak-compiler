# BUILTIN SPECIAL TYPE DEFINITIONS
from cloak.cloak_ast.ast import AnnotatedTypeName, AssignmentStatement, FunctionTypeName, IdentifierDeclaration, Parameter, Identifier, RequireStatement, StructDefinition, \
    VariableDeclaration, TypeName, StateVariableDeclaration, IdentifierExpr, StructTypeName, Block, ConstructorOrFunctionDefinition, \
    Array, UintTypeName, ElementaryTypeName
from cloak.cloak_ast.pointers.parent_setter import set_parents
from cloak.config import cfg
from cloak.errors.exceptions import CloakCompilerError
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

    address: ConstructorOrFunctionDefinition = ConstructorOrFunctionDefinition(
        idf=Identifier('address'),
        parameters=[Parameter([], annotated_type=AnnotatedTypeName.uint_all(), idf=Identifier('naddr'))],
        modifiers=['internal'],
        return_parameters=[Parameter([], annotated_type=AnnotatedTypeName.address_all(), idf=Identifier('addr'))],
        body=Block([])   
    )
    address.idf.parent = address

    uint256: ConstructorOrFunctionDefinition = ConstructorOrFunctionDefinition(
        idf=Identifier('uint256'),
        parameters=[Parameter([], annotated_type=AnnotatedTypeName.address_all(), idf=Identifier('nuint'))],
        modifiers=['internal'],
        return_parameters=[Parameter([], annotated_type=AnnotatedTypeName.uint_all(), idf=Identifier('uint'))],
        body=Block([])   
    )
    uint256.idf.parent = uint256

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

    # TODO: add other functions and add variable length parameters
    abi_struct: StructDefinition = StructDefinition(
        Identifier('<abi>'), [
            ConstructorOrFunctionDefinition(
                Identifier('encode'),
                [Parameter([], AnnotatedTypeName(Array(AnnotatedTypeName(UintTypeName()))), Identifier("dummy"))],
                ['public'],
                [Parameter([], AnnotatedTypeName(ElementaryTypeName("bytes32")), Identifier('dummy'))],
                Block([])
            ),
        ]
    )
    set_parents(abi_struct)

    keccak256: ConstructorOrFunctionDefinition = ConstructorOrFunctionDefinition(
        idf=Identifier('keccak256'),
        parameters=[Parameter([], AnnotatedTypeName(ElementaryTypeName("bytes")), idf=Identifier('dummy'))],
        modifiers=['public'],
        return_parameters=[Parameter([], AnnotatedTypeName(ElementaryTypeName("bytes32")), idf=Identifier('dummy'))],
        body=Block([])
    )
    keccak256.idf.parent = keccak256

    # TODO: add revert function that has parameter
    revert: ConstructorOrFunctionDefinition = ConstructorOrFunctionDefinition(
        idf=Identifier('revert'),
        parameters=[],
        modifiers=['public'],
        return_parameters=[],
        body=Block([])
    )
    revert.idf.parent = revert

    require: ConstructorOrFunctionDefinition = ConstructorOrFunctionDefinition(
        idf=Identifier('require'),
        parameters=[Parameter([], AnnotatedTypeName(TypeName.bool_type()), idf=Identifier('condition'))],
        modifiers=['public'],
        return_parameters=[],
        body=Block([])
    )
    require.idf.parent = require


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

    abi: StateVariableDeclaration = StateVariableDeclaration(
        AnnotatedTypeName.all(StructTypeName([GlobalDefs.abi_struct.idf], GlobalDefs.abi_struct)), [],
        Identifier('abi'), None
    )
    abi.idf.parent = abi

def get_array_builtin_function(name: str, arr: Array) -> ConstructorOrFunctionDefinition:
    val_type = arr.value_type
    if name == 'push':
        return ConstructorOrFunctionDefinition(Identifier(name), [Parameter([], AnnotatedTypeName(val_type), Identifier(''))], [], [], Block([]))
    if name == 'pop':
        return ConstructorOrFunctionDefinition(Identifier(name), [], [], [Parameter([], AnnotatedTypeName(val_type), Identifier(''))], Block([]))
    raise CloakCompilerError(f"unsupported array functin: {name}")
