// Copyright 2016-2019 Federico Bond <federicobond@gmail.com>
// Licensed under the MIT license. See LICENSE file in the project root for details.

// Original source: https://github.com/solidityj/solidity-antlr4/blob/master/Solidity.g4
// changes are marked with REMOVED or CHANGED
//
// - removed language features:
//   - imports (importDeclaration, importDirective)
//      -> https://solidity.readthedocs.io/en/v0.4.24/layout-of-source-files.html#importing-other-source-files
//   - interfaces, libraries
//   - inheritance (inheritanceSpecifier)
//      -> https://solidity.readthedocs.io/en/v0.4.24/contracts.html#inheritance
//   - using for (usingForDeclaration)
//      -> https://solidity.readthedocs.io/en/v0.4.24/contracts.html#using-for
//   - function modifiers / modifier definitions (modifierDefinition, modifierInvocation)
//      -> https://solidity.readthedocs.io/en/v0.4.24/common-patterns.html#restricting-access
//   - events (eventDefinition,eventParameterList,eventParameter)
//      -> https://solidity.readthedocs.io/en/v0.4.21/contracts.html#events
//   - struct (structDefinition)
// - moved stateVariableAccessModifiers to separate rule
// - user defined type names (userDefinedTypeName)
// - function type name (functionTypeName, functionTypeParameterList, functionTypeParameter), needed for higher-order functions



grammar Solidity;

/**
 * On top level, Solidity allows pragmas, import directives, and
 * definitions of contracts, interfaces, libraries, structs, enums and constants.
 */
sourceUnit: (
    pragmaDirective
    | importDirective
    | contractDefinition
    | interfaceDefinition
    | libraryDefinition
    | functionDefinition
    // | constantVariableDeclaration
    // | structDefinition
    // | enumDefinition
    // | userDefinedValueTypeDefinition
    // | errorDefinition
)* EOF
    | sba EOF;

sba: 'SOL' (expression | statement | contractBodyElement ) EOF;

pragmaDirective
  : 'pragma' name=('cloak' | 'solidity') ver=version ';' ;

version
  : versionConstraint versionConstraint? ;

versionOperator
  : '^' | '~' | '>=' | '>' | '<' | '<=' | '=' ;

versionConstraint
  : versionOperator? VersionLiteral ;

importDirective:
    'import' (
        (import_path=path ('as' unitAlias=identifier)?)
        | (symbolAliases 'from' import_path=path)
        | ('*' 'as' unitAlias=identifier 'from' import_path=path)
    ) ';';
//@doc: inline
//@doc:name aliases
importAliases: symbol=identifier ('as' alias=identifier)?;
/**
 * Path of a file to be imported.
 */
path: StringLiteral;
/**
 * List of aliases for symbols to be imported.
 */
symbolAliases: '{' aliases+=importAliases (',' aliases+=importAliases)* '}';

// REMOVED: interface, library, inheritance
contractDefinition
  : ( 'contract' ) idf=identifier
    '{' parts+=contractBodyElement* '}' ;

/**
 * Top-level definition of an interface.
 */
interfaceDefinition:
    'interface' name=identifier
    inheritanceSpecifierList?
    '{' contractBodyElement* '}';

/**
 * Top-level definition of a library.
 */
libraryDefinition: 'library' name=identifier '{' contractBodyElement* '}';

//@doc:inline
inheritanceSpecifierList:
    'is' inheritanceSpecifiers+=inheritanceSpecifier
    (',' inheritanceSpecifiers+=inheritanceSpecifier)*?;
/**
 * Inheritance specifier for contracts and interfaces.
 * Can optionally supply base constructor arguments.
 */
inheritanceSpecifier: name=identifierPath arguments=callArgumentList?;

/**
 * Declarations that can be used in contracts, interfaces and libraries.
 *
 * Note that interfaces and libraries may not contain constructors, interfaces may not contain state variables
 * and libraries may not contain fallback, receive functions nor non-constant state variables.
 */
contractBodyElement:
    constructorDefinition
    | functionDefinition
    | modifierDefinition
    // TODO | fallbackFunctionDefinition
    // TODO | receiveFunctionDefinition
    // TODO | structDefinition
    | enumDefinition
    // TODO | userDefinedValueTypeDefinition
    | stateVariableDeclaration
    // TODO | eventDefinition
    // TODO | errorDefinition
    // TODO | usingDirective
    ;

//@doc:inline
namedArgument: name=identifier ':' value=expression;
/**
 * Arguments when calling a function or a similar callable object.
 * The arguments are either given as comma separated list or as map of named arguments.
 */
callArgumentList: '(' ((expression (',' expression)*)? | '{' (namedArgument (',' namedArgument)*)? '}') ')';

/**
 * Qualified name.
 */
identifierPath: identifier ('.' identifier)*;

// CHANGED: typeName -> annotatedTypeName
// REMOVED (only allow default):
// - PublicKeyword
// - InternalKeyword
// - PrivateKeyword
//
// state variable modifiers:
// - public: all can access (default for functions)
// - internal: only this contract and contracts deriving from it can access (default for state variables)
// - private: can be accessed only from this contract
// - constant: constant at compile-time: https://solidity.readthedocs.io/en/v0.4.24/contracts.html#constant-state-variables
stateVariableDeclaration
  : ( keywords+=FinalKeyword )* annotated_type=annotatedTypeName
    ( keywords+=ConstantKeyword )*
    idf=identifier ('=' expr=expression)? ';' ;

// 
// The declaration of a constant variable.
// 
constantVariableDeclaration:
    annotated_type=annotatedTypeName 'constant' idf=identifier '=' expr=expression ';';

/**
 * Definition of a constructor.
 * Must always supply an implementation.
 * Note that specifying internal or public visibility is deprecated.
 */
constructorDefinition:
    'constructor' parameters=parameterList
    (modifierInvocation | modifiers+='payable' | modifiers+='internal' | modifiers+='public')*
    body=block;

/**
 * The definition of contract, library and interface functions.
 * Depending on the context in which the function is defined, further restrictions may apply,
 * e.g. functions in interfaces have to be unimplemented, i.e. may not contain a body block.
 */
functionDefinition:
    'function' (identifier | 'fallback' | 'receive')
    parameters=parameterList
    (visibility | stateMutability | modifierInvocation | virtual='virtual' | overrideSpecifier)*
    return_parameters=returnParameters?
    (';' | body=block)
    ;

/**
 * The definition of a modifier.
 * Note that within the body block of a modifier, the underscore cannot be used as identifier,
 * but is used as placeholder statement for the body of a function to which the modifier is applied.
 */
modifierDefinition:
    'modifier' name=identifier
    parameters=parameterList
    ( virtual='virtual' | overrideSpecifier)*
    (';' | body=block);

returnParameters
: 'returns' return_parameters=parameterList ;

/**
 * Call to a modifier. If the modifier takes no arguments, the argument list can be skipped entirely
 * (including opening and closing parentheses).
 */
modifierInvocation: identifierPath callArgumentList?;
/**
 * Visibility for functions and function types.
 */
visibility: 'internal' | 'external' | 'private' | 'public';
/**
 * State mutability for function types.
 * The default mutability 'non-payable' is assumed if no mutability is specified.
 */
stateMutability: 'pure' | 'view' | 'payable';
/**
 * An override specifier used for functions, modifiers or state variables.
 * In cases where there are ambiguous declarations in several base contracts being overridden,
 * a complete list of base contracts has to be given.
 */
overrideSpecifier: 'override' ('(' overrides+=identifierPath (',' overrides+=identifierPath)* ')')?;


parameterList
  : '(' ( params+=parameter (',' params+=parameter)* )? ')' ;

// identifier is optional because parameters can be used to specify the return value
// CHANGED:
// - typeName -> annotatedTypeName
parameter
  : (keywords+=FinalKeyword)? annotated_type=annotatedTypeName storage_location=dataLocation? idf=identifier? ;

enumValue
  : idf=identifier ;

enumDefinition
  : 'enum' idf=identifier '{' values+=enumValue? (',' values+=enumValue)* '}' ;

variableDeclaration
  : (keywords+=FinalKeyword)? annotated_type=annotatedTypeName storage_location=dataLocation? idf=identifier ;

// REMOVED:
// special types:
// - address payable: Same as address, but with the additional members transfer and send
// - arrays: allows fixed size (T[k]) and dynamic size (T[])
typeName
  : elementaryTypeName
  | userDefinedTypeName
  | value_type=typeName '[' expr=expression? ']'
  | mapping
  ;

userDefinedTypeName
  : names+=identifier ( '.' names+=identifier )* ;

mapping
  : 'mapping' '(' key_type=elementaryTypeName ( '!' key_label=identifier )? '=>' value_type=annotatedTypeName ')' ;

// REMOVED (only allow default)
// storage location
// - memory: Not persisting (default for function parameters)
// - storage: Where the state variables are held. (default for local variables, forced for state variables)
// - calldata: non-modifiable, non-persistent area for function arguments (forced for function parameters of external
//   functions)
dataLocation: MemoryKeyword | StorageKeyword | CalldataKeyword;

block
  : '{' statements+=statement* '}' ;

// REMOVED:
// - inlineAssemblyStatement
// - throwStatement
// - emitStatement
statement
  : ifStatement
  | whileStatement
  | forStatement
  | block
  | doWhileStatement
  | continueStatement
  | breakStatement
  | returnStatement
  | simpleStatement ;

expressionStatement
  : expr=expression ';' ;

ifStatement
  : 'if' '(' condition=expression ')' then_branch=statement ( 'else' else_branch=statement )? ;

whileStatement
  : 'while' '(' condition=expression ')' body=statement ;

simpleStatement
  : ( variableDeclarationStatement | expressionStatement | tupleVariableDeclarationStatement) ;

forStatement
  : 'for' '(' ( init=simpleStatement | ';' ) condition=expression? ';' update=expression? ')' body=statement ;

doWhileStatement
  : 'do' body=statement 'while' '(' condition=expression ')' ';' ;

continueStatement
  : 'continue' ';' ;

breakStatement
  : 'break' ';' ;


returnStatement
  : 'return' expr=expression? ';' ;

// REMOVED:
// - 'var' identifierList
// - '(' variableDeclarationList ')'
variableDeclarationStatement
  : variable_declaration=variableDeclaration ( '=' expr=expression )? ';'
  ;

tupleVariableDeclarationStatement:
    '(' (',')* variableDeclaration (',' (variableDeclaration)?)* ')' '=' expression ';'
    ;

// REMOVED: identifierList

elementaryTypeName
  : name=('address' | 'address payable' | 'bool' | Int | Uint | // Supported types
          'var' | 'string' | 'bytes' | 'byte' | Byte | Fixed | Ufixed )  ; // Unsupported types

Uint
  : 'uint' | 'uint8' | 'uint16' | 'uint24' | 'uint32' | 'uint40' | 'uint48' | 'uint56' | 'uint64' | 'uint72' | 'uint80' | 'uint88' | 'uint96' | 'uint104' | 'uint112' | 'uint120' | 'uint128' | 'uint136' | 'uint144' | 'uint152' | 'uint160' | 'uint168' | 'uint176' | 'uint184' | 'uint192' | 'uint200' | 'uint208' | 'uint216' | 'uint224' | 'uint232' | 'uint240' | 'uint248' | 'uint256' ;

Int
  : 'int' | 'int8' | 'int16' | 'int24' | 'int32' | 'int40' | 'int48' | 'int56' | 'int64' | 'int72' | 'int80' | 'int88' | 'int96' | 'int104' | 'int112' | 'int120' | 'int128' | 'int136' | 'int144' | 'int152' | 'int160' | 'int168' | 'int176' | 'int184' | 'int192' | 'int200' | 'int208' | 'int216' | 'int224' | 'int232' | 'int240' | 'int248' | 'int256' ;

Byte
   : 'bytes1' | 'bytes2' | 'bytes3' | 'bytes4' | 'bytes5' | 'bytes6' | 'bytes7' | 'bytes8' | 'bytes9' | 'bytes10' | 'bytes11' | 'bytes12' | 'bytes13' | 'bytes14' | 'bytes15' | 'bytes16' | 'bytes17' | 'bytes18' | 'bytes19' | 'bytes20' | 'bytes21' | 'bytes22' | 'bytes23' | 'bytes24' | 'bytes25' | 'bytes26' | 'bytes27' | 'bytes28' | 'bytes29' | 'bytes30' | 'bytes31' | 'bytes32' ;

Fixed
  : 'fixed' | ( 'fixed' [0-9]+ 'x' [0-9]+ ) ;

Ufixed
  : 'ufixed' | ( 'ufixed' [0-9]+ 'x' [0-9]+ ) ;

// CHANGED: INLINED: primaryExpression
// REMOVED from primaryExpression:
// - HexLiteral
// - elementaryTypeNameExpression ('[' ']')? (for type casts)
// CHANGED from primaryExpression:
// - identifier ('[' ']')? -> identifier
// - added me and all
// REMOVED:
// - ('after' | 'delete') expression
expression
  : MeKeyword # MeExpr
  | AllKeyword # AllExpr
  | TeeKeyword # TeeExpr
  | expr=expression op=('++' | '--') # PostCrementExpr
  | arr=expression '[' index=expression? ']' # IndexExpr
  | elem_type=elementaryTypeName '(' expr=expression ')' # PrimitiveCastExpr
  | expression callArgumentList # FunctionCallExpr
  | expr=expression '.' member=identifier # MemberAccessExpr
  | op=('++' | '--') expr=expression # PreCrementExpr
  | op=('+' | '-') expr=expression # SignExpr
  | '!' expr=expression # NotExpr
  | '~' expr=expression # BitwiseNotExpr
  | lhs=expression op='**' rhs=expression # PowExpr
  | lhs=expression op=('*' | '/' | '%') rhs=expression # MultDivModExpr
  | lhs=expression op=('+' | '-') rhs=expression # PlusMinusExpr
  | lhs=expression op=('<<' | '>>') rhs=expression # BitShiftExpr
  | lhs=expression op='&' rhs=expression # BitwiseAndExpr
  | lhs=expression op='^' rhs=expression # BitwiseXorExpr
  | lhs=expression op='|' rhs=expression # BitwiseOrExpr
  | lhs=expression op=('<' | '>' | '<=' | '>=') rhs=expression # CompExpr
  | lhs=expression op=('==' | '!=') rhs=expression # EqExpr
  | lhs=expression op='&&' rhs=expression # AndExpr
  | lhs=expression op='||' rhs=expression # OrExpr
  | cond=expression '?' then_expr=expression ':' else_expr=expression # IteExpr
  | lhs=expression op=('=' | '|=' | '^=' | '&=' | '<<=' | '>>=' | '+=' | '-=' | '*=' | '/=' | '%=') rhs=expression # AssignmentExpr
  | BooleanLiteral # BooleanLiteralExpr
  | numberLiteral # NumberLiteralExpr
  | StringLiteral # StringLiteralExpr
  | expr=tupleExpression # TupleExpr
  | 'new' target_type=typeName # NewExpr
  | idf=identifier # IdentifierExpr
  // REMOVED: literal
  | ( elementaryTypeName ) # PrimaryExpression
  ;

tupleExpression
  : '(' ( expression? ( ',' expression? )* ) ')' ;

elementaryTypeNameExpression
  : elementaryTypeName ;

// REMOVED:
// - NumberUnit
// - HexNumber
numberLiteral
  : DecimalNumber | HexNumber ;

// CHANGED: ADDED RULES FOR PRIVACY ANNOTATIONS

MeKeyword : 'me' ;
AllKeyword : 'all' ;
TeeKeyword : 'tee' ;

annotatedTypeName
  : type_name=typeName ('@' privacy_annotation=expression)? ;

// REMOVED:
// - 'from'
// - 'calldata'
identifier
  : (name=Identifier) ;

VersionLiteral
  : [0-9]+ '.' [0-9]+ '.' [0-9]+ ;

BooleanLiteral
  : 'true' | 'false' ;

DecimalNumber
  : ( DecimalDigits ) ( [eE] DecimalDigits )? ; // NB: removed fractional literals since they are useless without fixed point type
  //: ([0-9]+ | ([0-9]* '.' [0-9]+) ) ( [eE] [0-9]+ )? ;

fragment
DecimalDigits
  : [0-9] ( '_'? [0-9] )* ;

HexNumber
  : '0' [xX] HexDigits ;

fragment
HexDigits
  : HexCharacter ( '_'? HexCharacter )* ;

fragment
HexCharacter
  : [0-9A-Fa-f] ;

// REMOVED:
// - final
ReservedKeyword
  : 'abstract'
  | 'after'
  | 'case'
  | 'catch'
  | 'default'
  | 'in'
  | 'inline'
  | 'let'
  | 'match'
  | 'null'
  | 'of'
  | 'relocatable'
  | 'static'
  | 'switch'
  | 'try'
  | 'type'
  | 'typeof' ;

AnonymousKeyword : 'anonymous' ;
BreakKeyword : 'break' ;
ConstantKeyword : 'constant' ;
ContinueKeyword : 'continue' ;
ExternalKeyword : 'external' ;
IndexedKeyword : 'indexed' ;
InternalKeyword : 'internal' ;
PayableKeyword : 'payable' ;
PrivateKeyword : 'private' ;
PublicKeyword : 'public' ;
PureKeyword : 'pure' ;
ViewKeyword : 'view' ;

MemoryKeyword: 'memory';
StorageKeyword: 'storage';
CalldataKeyword: 'calldata';

// ADDED
FinalKeyword : 'final' ;

Identifier
  : IdentifierStart IdentifierPart* ;

fragment
IdentifierStart
  : [a-zA-Z$_] ;

fragment
IdentifierPart
  : [a-zA-Z0-9$_] ;

StringLiteral
  : '"' DoubleQuotedStringCharacter* '"'
  | '\'' SingleQuotedStringCharacter* '\'' ;

fragment
DoubleQuotedStringCharacter
  : ~["\r\n\\] | ('\\' .) ;
fragment
SingleQuotedStringCharacter
  : ~['\r\n\\] | ('\\' .) ;

// CHANGED: switched WS to HIDDEN channel (allows preserving whitespaces)
WS
  : [ \t\r\n\u000C]+ -> channel(HIDDEN) ;

COMMENT
  : '/*' .*? '*/' -> channel(HIDDEN) ;

LINE_COMMENT
  : '//' ~[\r\n]* -> channel(HIDDEN) ;
