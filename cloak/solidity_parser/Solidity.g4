// Copyright 2016-2019 Federico Bond <federicobond@gmail.com>
// Licensed under the MIT license. See LICENSE file in the project root for details.

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
    | constantVariableDeclaration
    | structDefinition
    | enumDefinition
    | userDefinedValueTypeDefinition
    | errorDefinition
)* EOF
    | sba EOF;

sba: 'SOL' (typeName | expression | statement | contractBodyElement) EOF;

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

contractDefinition
  : ( 'contract' ) idf=identifier
    ( 'is' inheritanceSpecifier (',' inheritanceSpecifier )* )?
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
    | fallbackFunctionDefinition
    | receiveFunctionDefinition
    | structDefinition
    | enumDefinition
    | userDefinedValueTypeDefinition
    | stateVariableDeclaration
    | eventDefinition
    | errorDefinition
    | usingDirective
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

/**
 * The declaration of a state variable.
 */
stateVariableDeclaration:
    (keywords+='final')* annotated_type=annotatedTypeName
    ( keywords+='public'
    | keywords+='private'
    | keywords+='internal'
    | keywords+='constant'
    | overrideSpecifier
    | keywords+='immutable')*
    idf=identifier
    ('=' expr=expression)?
    ';';

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
    (visibility | stateMutability | modifierInvocation | modifiers+='virtual' | overrideSpecifier)*
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

/**
 * Definition of the special fallback function.
 */
fallbackFunctionDefinition:
    'fallback' parameters=parameterList?
    (modifiers+='external' | stateMutability | modifierInvocation | modifiers+='virtual' | overrideSpecifier)*
    return_parameters=returnParameters?
    (';' | body=block);

/**
 * Definition of the special receive function.
 */
receiveFunctionDefinition:
    'receive' '(' ')'
    (modifiers+='external' | modifiers+='payable' | modifierInvocation | modifiers+='virtual' | overrideSpecifier)*
    (';' | body=block);

/**
 * Definition of a struct. Can occur at top-level within a source unit or within a contract, library or interface.
 */
structDefinition: 'struct' name=identifier '{' structMember+ '}';
/**
 * The declaration of a named struct member.
 */
structMember: typeName name=identifier ';';

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


parameterList: '(' ( params+=parameter (',' params+=parameter)* )? ')' ;

parameter
  : (keywords+=FinalKeyword)? annotated_type=annotatedTypeName storage_location=dataLocation? idf=identifier? ;

enumValue
  : idf=identifier ;

enumDefinition
  : 'enum' idf=identifier '{' values+=enumValue? (',' values+=enumValue)* '}' ;

/**
 * Definition of a user defined value type. Can occur at top-level within a source unit or within a contract, library or interface.
 */
userDefinedValueTypeDefinition:
    'type' name=identifier 'is' elementaryTypeName ';';

/**
 * The declaration of a constant variable.
 */ 
constantVariableDeclaration:
    annotated_type=annotatedTypeName 'constant' idf=identifier '=' expr=expression ';';

/**
 * Parameter of an event.
 */
eventParameter: annotatedTypeName 'indexed'? name=identifier?;
/**
 * Definition of an event. Can occur in contracts, libraries or interfaces.
 */
eventDefinition:
    'event' name=identifier
    '(' (parameters+=eventParameter (',' parameters+=eventParameter)*)? ')'
    'anonymous'?
    ';';

/**
 * Parameter of an error.
 */
errorParameter: typ=annotatedTypeName name=identifier?;
/**
 * Definition of an error.
 */
errorDefinition:
    'error' name=identifier
    '(' (parameters+=errorParameter (',' parameters+=errorParameter)*)? ')'
    ';';

/**
 * Using directive to bind library functions to types.
 * Can occur within contracts and libraries.
 */
usingDirective: 'using' identifierPath 'for' ('*' | typeName) ';';

variableDeclaration
  : (keywords+=FinalKeyword)? annotated_type=annotatedTypeName storage_location=dataLocation? idf=identifier ;

typeName
  : elementaryTypeName
  | functionTypeName
  | userDefinedTypeName
  | value_type=typeName '[' expr=expression? ']'
  | mapping
  ;

functionTypeName:
    'function'
    parameters=parameterList?
    (visibility | stateMutability)*
    return_parameters=returnParameters?
    ;

userDefinedTypeName
  : names+=identifier ( '.' names+=identifier )* ;

mapping
  : 'mapping' '(' key_type=elementaryTypeName ( '!' key_label=identifier )? '=>' value_type=annotatedTypeName ')' ;

dataLocation: MemoryKeyword | StorageKeyword | CalldataKeyword;

block
  : '{' statements+=statement* '}' ;

statement:
    block
    | simpleStatement
    | ifStatement
    | forStatement
    | whileStatement
    | doWhileStatement
    | continueStatement
    | breakStatement
    | tryStatement
    | returnStatement
    | emitStatement
    | revertStatement
    | assemblyStatement
    ;

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

/**
 * A try statement. The contained expression needs to be an external function call or a contract creation.
 */
tryStatement: 'try' expression ('returns' '(' return_parameters=parameterList ')')? block catchClause+;
/**
 * The catch clause of a try statement.
 */
catchClause: 'catch' identifier? arguments=parameterList? block;

returnStatement: 'return' expr=expression? ';' ;

/**
 * An emit statement. The contained expression needs to refer to an event.
 */
emitStatement: 'emit' expression callArgumentList ';';
/**
 * A revert statement. The contained expression needs to refer to an error.
 */
revertStatement: 'revert' expression callArgumentList ';';
/**
 * An inline assembly block.
 * The contents of an inline assembly block use a separate scanner/lexer, i.e. the set of keywords and
 * allowed identifiers is different inside an inline assembly block.
 */
assemblyStatement: 'assembly' '"evmasm"'? '{' yulStatement* '}';

variableDeclarationStatement
  : variable_declaration=variableDeclaration ( '=' expr=expression )? ';'
  ;

tupleVariableDeclarationStatement:
    '(' (',')* variableDeclaration (',' (variableDeclaration)?)* ')' '=' expression ';'
    ;

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

/**
 * Complex expression.
 * Can be an index access, an index range access, a member access, a function call (with optional function call options),
 * a type conversion, an unary or binary expression, a comparison or assignment, a ternary expression,
 * a new-expression (i.e. a contract creation or the allocation of a dynamic memory array),
 * a tuple, an inline array or a primary expression (i.e. an identifier, literal or type name).
 */
expression:
    MeKeyword # MeExpr
    | AllKeyword # AllExpr
    | TeeKeyword # TeeExpr
    | arr=expression '[' index=expression? ']' # IndexExpr
    | arr=expression '[' start=expression? ':' end=expression? ']' # RangeIndexExpr
    | expr=expression '.' (identifier | 'address') # MemberAccessExpr
    | expression '{' (namedArgument (',' namedArgument)*)? '}' # FunctionCallOptions
    | expression callArgumentList # FunctionCallExpr
    | Payable callArgumentList # PayableConversion
    | 'type' '(' typeName ')' # MetaType
    | expr=expression op=('++' | '--') # PostCrementExpr
    | op=('++' | '--') expr=expression # PreCrementExpr
    | op=('+' | '-') expr=expression # SignExpr
    | '!' expr=expression # NotExpr
    | '~' expr=expression # BitwiseNotExpr
    |<assoc=right> lhs=expression op='**' rhs=expression # PowExpr
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
    |<assoc=right> cond=expression '?' then_expr=expression ':' else_expr=expression # IteExpr
    | lhs=expression op=('=' | '|=' | '^=' | '&=' | '<<=' | '>>=' | '+=' | '-=' | '*=' | '/=' | '%=') rhs=expression # AssignmentExpr
    | 'new' target_type=typeName # NewExpr
    | expr=tupleExpression # TupleExpr
    | '[' (expression ( ',' expression)* ) ']' # InlineArrayExpr
    | idf=identifier # IdentifierExpr
    | BooleanLiteral # BooleanLiteralExpr
    | (DecimalNumber | HexNumber) NumberUnit? # NumberLiteralExpr
    | StringLiteral # StringLiteralExpr
    | elementaryTypeName # PrimaryExpression
    ;

tupleExpression: '(' ( expression? ( ',' expression? )* ) ')' ;

elementaryTypeNameExpression
  : elementaryTypeName ;

MeKeyword : 'me' ;
AllKeyword : 'all' ;
TeeKeyword : 'tee' ;

annotatedTypeName:
    type_name=typeName ('@' privacy_annotation=expression)? ;

/**
 * A Yul statement within an inline assembly block.
 * continue and break statements are only valid within for loops.
 * leave statements are only valid within function bodies.
 */
yulStatement:
    yulBlock
    | yulVariableDeclaration
    | yulAssignment
    | yulFunctionCall
    | yulIfStatement
    | yulForStatement
    | yulSwitchStatement
    | 'leave'
    | 'break'
    | 'continue'
    | yulFunctionDefinition;

yulBlock: '{' yulStatement* '}';

/**
 * The declaration of one or more Yul variables with optional initial value.
 * If multiple variables are declared, only a function call is a valid initial value.
 */
yulVariableDeclaration:
    ('let' variables+=Identifier (':=' yulExpression)?)
    | ('let' variables+=Identifier (',' variables+=Identifier)* (':=' yulFunctionCall)?);

/**
 * Any expression can be assigned to a single Yul variable, whereas
 * multi-assignments require a function call on the right-hand side.
 */
yulAssignment: yulPath ':=' yulExpression | (yulPath (',' yulPath)+) ':=' yulFunctionCall;

yulIfStatement: 'if' cond=yulExpression body=yulBlock;

yulForStatement: 'for' init=yulBlock cond=yulExpression post=yulBlock body=yulBlock;

//@doc:inline
yulSwitchCase: 'case' yulLiteral yulBlock;
/**
 * A Yul switch statement can consist of only a default-case (deprecated) or
 * one or more non-default cases optionally followed by a default-case.
 */
yulSwitchStatement:
    'switch' yulExpression
    (
        (yulSwitchCase+ ('default' yulBlock)?)
        | ('default' yulBlock)
    );

yulFunctionDefinition:
    'function' Identifier
    '(' (arguments+=Identifier (',' arguments+=Identifier)*)? ')'
    ('->' Identifier (',' Identifier)*)?
    body=yulBlock;

/**
 * While only identifiers without dots can be declared within inline assembly,
 * paths containing dots can refer to declarations outside the inline assembly block.
 */
yulPath: Identifier ('.' Identifier)*;
/**
 * A call to a function with return values can only occur as right-hand side of an assignment or
 * a variable declaration.
 */
yulFunctionCall: (Identifier | YulEVMBuiltin) '(' (yulExpression (',' yulExpression)*)? ')';
yulLiteral: DecimalNumber | StringLiteral | HexNumber | BooleanLiteral | HexString;
yulExpression: yulPath | yulFunctionCall | yulLiteral;

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
Payable : 'payable' ;
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

// Note that this will also be used for Yul hex string literals.
/**
 * Hex strings need to consist of an even number of hex digits that may be grouped using underscores.
 */
HexString: 'hex' (('"' EvenHexDigits? '"') | ('\'' EvenHexDigits? '\''));
//@doc:inline
fragment EvenHexDigits: HexCharacter HexCharacter ('_'? HexCharacter HexCharacter)*;

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

NumberUnit: 'wei' | 'gwei' | 'ether' | 'seconds' | 'minutes' | 'hours' | 'days' | 'weeks' | 'years';

/**
 * Builtin functions in the EVM Yul dialect.
 */
YulEVMBuiltin:
    'stop' | 'add' | 'sub' | 'mul' | 'div' | 'sdiv' | 'mod' | 'smod' | 'exp' | 'not'
    | 'lt' | 'gt' | 'slt' | 'sgt' | 'eq' | 'iszero' | 'and' | 'or' | 'xor' | 'byte'
    | 'shl' | 'shr' | 'sar' | 'addmod' | 'mulmod' | 'signextend' | 'keccak256'
    | 'pop' | 'mload' | 'mstore' | 'mstore8' | 'sload' | 'sstore' | 'msize' | 'gas'
    | 'address' | 'balance' | 'selfbalance' | 'caller' | 'callvalue' | 'calldataload'
    | 'calldatasize' | 'calldatacopy' | 'extcodesize' | 'extcodecopy' | 'returndatasize'
    | 'returndatacopy' | 'extcodehash' | 'create' | 'create2' | 'call' | 'callcode'
    | 'delegatecall' | 'staticcall' | 'return' | 'revert' | 'selfdestruct' | 'invalid'
    | 'log0' | 'log1' | 'log2' | 'log3' | 'log4' | 'chainid' | 'origin' | 'gasprice'
    | 'blockhash' | 'coinbase' | 'timestamp' | 'number' | 'difficulty' | 'gaslimit'
    | 'basefee';
