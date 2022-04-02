// Generated from /project/evm4ccf/repo/cloak-compiler/cloak/solidity_parser/Solidity.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SolidityParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, T__84=85, T__85=86, T__86=87, 
		T__87=88, T__88=89, T__89=90, T__90=91, T__91=92, T__92=93, T__93=94, 
		T__94=95, T__95=96, T__96=97, T__97=98, T__98=99, T__99=100, T__100=101, 
		Uint=102, Int=103, Byte=104, Fixed=105, Ufixed=106, MeKeyword=107, AllKeyword=108, 
		TeeKeyword=109, VersionLiteral=110, BooleanLiteral=111, DecimalNumber=112, 
		HexNumber=113, ReservedKeyword=114, AnonymousKeyword=115, BreakKeyword=116, 
		ConstantKeyword=117, ContinueKeyword=118, ExternalKeyword=119, IndexedKeyword=120, 
		InternalKeyword=121, Payable=122, PrivateKeyword=123, PublicKeyword=124, 
		PureKeyword=125, ViewKeyword=126, MemoryKeyword=127, StorageKeyword=128, 
		CalldataKeyword=129, FinalKeyword=130, Identifier=131, StringLiteral=132, 
		HexString=133, WS=134, COMMENT=135, LINE_COMMENT=136, NumberUnit=137, 
		YulEVMBuiltin=138;
	public static final int
		RULE_sourceUnit = 0, RULE_sba = 1, RULE_pragmaDirective = 2, RULE_version = 3, 
		RULE_versionOperator = 4, RULE_versionConstraint = 5, RULE_importDirective = 6, 
		RULE_importAliases = 7, RULE_path = 8, RULE_symbolAliases = 9, RULE_contractDefinition = 10, 
		RULE_interfaceDefinition = 11, RULE_libraryDefinition = 12, RULE_inheritanceSpecifierList = 13, 
		RULE_inheritanceSpecifier = 14, RULE_contractBodyElement = 15, RULE_namedArgument = 16, 
		RULE_callArgumentList = 17, RULE_identifierPath = 18, RULE_stateVariableDeclaration = 19, 
		RULE_constructorDefinition = 20, RULE_functionDefinition = 21, RULE_modifierDefinition = 22, 
		RULE_fallbackFunctionDefinition = 23, RULE_receiveFunctionDefinition = 24, 
		RULE_structDefinition = 25, RULE_structMember = 26, RULE_returnParameters = 27, 
		RULE_modifierInvocation = 28, RULE_visibility = 29, RULE_stateMutability = 30, 
		RULE_overrideSpecifier = 31, RULE_parameterList = 32, RULE_parameter = 33, 
		RULE_enumValue = 34, RULE_enumDefinition = 35, RULE_userDefinedValueTypeDefinition = 36, 
		RULE_constantVariableDeclaration = 37, RULE_eventParameter = 38, RULE_eventDefinition = 39, 
		RULE_errorParameter = 40, RULE_errorDefinition = 41, RULE_usingDirective = 42, 
		RULE_variableDeclaration = 43, RULE_typeName = 44, RULE_functionTypeName = 45, 
		RULE_userDefinedTypeName = 46, RULE_mapping = 47, RULE_dataLocation = 48, 
		RULE_block = 49, RULE_statement = 50, RULE_expressionStatement = 51, RULE_ifStatement = 52, 
		RULE_whileStatement = 53, RULE_simpleStatement = 54, RULE_forStatement = 55, 
		RULE_doWhileStatement = 56, RULE_continueStatement = 57, RULE_breakStatement = 58, 
		RULE_tryStatement = 59, RULE_catchClause = 60, RULE_returnStatement = 61, 
		RULE_emitStatement = 62, RULE_revertStatement = 63, RULE_assemblyStatement = 64, 
		RULE_variableDeclarationStatement = 65, RULE_tupleVariableDeclarationStatement = 66, 
		RULE_elementaryTypeName = 67, RULE_expression = 68, RULE_tupleExpression = 69, 
		RULE_elementaryTypeNameExpression = 70, RULE_annotatedTypeName = 71, RULE_yulStatement = 72, 
		RULE_yulBlock = 73, RULE_yulVariableDeclaration = 74, RULE_yulAssignment = 75, 
		RULE_yulIfStatement = 76, RULE_yulForStatement = 77, RULE_yulSwitchCase = 78, 
		RULE_yulSwitchStatement = 79, RULE_yulFunctionDefinition = 80, RULE_yulPath = 81, 
		RULE_yulFunctionCall = 82, RULE_yulLiteral = 83, RULE_yulExpression = 84, 
		RULE_identifier = 85;
	private static String[] makeRuleNames() {
		return new String[] {
			"sourceUnit", "sba", "pragmaDirective", "version", "versionOperator", 
			"versionConstraint", "importDirective", "importAliases", "path", "symbolAliases", 
			"contractDefinition", "interfaceDefinition", "libraryDefinition", "inheritanceSpecifierList", 
			"inheritanceSpecifier", "contractBodyElement", "namedArgument", "callArgumentList", 
			"identifierPath", "stateVariableDeclaration", "constructorDefinition", 
			"functionDefinition", "modifierDefinition", "fallbackFunctionDefinition", 
			"receiveFunctionDefinition", "structDefinition", "structMember", "returnParameters", 
			"modifierInvocation", "visibility", "stateMutability", "overrideSpecifier", 
			"parameterList", "parameter", "enumValue", "enumDefinition", "userDefinedValueTypeDefinition", 
			"constantVariableDeclaration", "eventParameter", "eventDefinition", "errorParameter", 
			"errorDefinition", "usingDirective", "variableDeclaration", "typeName", 
			"functionTypeName", "userDefinedTypeName", "mapping", "dataLocation", 
			"block", "statement", "expressionStatement", "ifStatement", "whileStatement", 
			"simpleStatement", "forStatement", "doWhileStatement", "continueStatement", 
			"breakStatement", "tryStatement", "catchClause", "returnStatement", "emitStatement", 
			"revertStatement", "assemblyStatement", "variableDeclarationStatement", 
			"tupleVariableDeclarationStatement", "elementaryTypeName", "expression", 
			"tupleExpression", "elementaryTypeNameExpression", "annotatedTypeName", 
			"yulStatement", "yulBlock", "yulVariableDeclaration", "yulAssignment", 
			"yulIfStatement", "yulForStatement", "yulSwitchCase", "yulSwitchStatement", 
			"yulFunctionDefinition", "yulPath", "yulFunctionCall", "yulLiteral", 
			"yulExpression", "identifier"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'SOL'", "'pragma'", "'cloak'", "'solidity'", "';'", "'^'", "'~'", 
			"'>='", "'>'", "'<'", "'<='", "'='", "'import'", "'as'", "'from'", "'*'", 
			"'{'", "','", "'}'", "'contract'", "'interface'", "'library'", "'is'", 
			"':'", "'('", "')'", "'.'", "'immutable'", "'constructor'", "'function'", 
			"'fallback'", "'receive'", "'virtual'", "'modifier'", "'struct'", "'returns'", 
			"'override'", "'enum'", "'type'", "'event'", "'error'", "'using'", "'for'", 
			"'['", "']'", "'mapping'", "'!'", "'=>'", "'if'", "'else'", "'while'", 
			"'do'", "'try'", "'catch'", "'return'", "'emit'", "'revert'", "'assembly'", 
			"'\"evmasm\"'", "'address'", "'address payable'", "'bool'", "'var'", 
			"'string'", "'bytes'", "'byte'", "'++'", "'--'", "'+'", "'-'", "'**'", 
			"'/'", "'%'", "'<<'", "'>>'", "'&'", "'|'", "'=='", "'!='", "'&&'", "'||'", 
			"'?'", "'|='", "'^='", "'&='", "'<<='", "'>>='", "'+='", "'-='", "'*='", 
			"'/='", "'%='", "'new'", "'@'", "'leave'", "'let'", "':='", "'case'", 
			"'switch'", "'default'", "'->'", null, null, null, null, null, "'me'", 
			"'all'", "'tee'", null, null, null, null, null, "'anonymous'", "'break'", 
			"'constant'", "'continue'", "'external'", "'indexed'", "'internal'", 
			"'payable'", "'private'", "'public'", "'pure'", "'view'", "'memory'", 
			"'storage'", "'calldata'", "'final'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "Uint", "Int", "Byte", "Fixed", "Ufixed", 
			"MeKeyword", "AllKeyword", "TeeKeyword", "VersionLiteral", "BooleanLiteral", 
			"DecimalNumber", "HexNumber", "ReservedKeyword", "AnonymousKeyword", 
			"BreakKeyword", "ConstantKeyword", "ContinueKeyword", "ExternalKeyword", 
			"IndexedKeyword", "InternalKeyword", "Payable", "PrivateKeyword", "PublicKeyword", 
			"PureKeyword", "ViewKeyword", "MemoryKeyword", "StorageKeyword", "CalldataKeyword", 
			"FinalKeyword", "Identifier", "StringLiteral", "HexString", "WS", "COMMENT", 
			"LINE_COMMENT", "NumberUnit", "YulEVMBuiltin"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Solidity.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SolidityParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class SourceUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(SolidityParser.EOF, 0); }
		public List<PragmaDirectiveContext> pragmaDirective() {
			return getRuleContexts(PragmaDirectiveContext.class);
		}
		public PragmaDirectiveContext pragmaDirective(int i) {
			return getRuleContext(PragmaDirectiveContext.class,i);
		}
		public List<ImportDirectiveContext> importDirective() {
			return getRuleContexts(ImportDirectiveContext.class);
		}
		public ImportDirectiveContext importDirective(int i) {
			return getRuleContext(ImportDirectiveContext.class,i);
		}
		public List<ContractDefinitionContext> contractDefinition() {
			return getRuleContexts(ContractDefinitionContext.class);
		}
		public ContractDefinitionContext contractDefinition(int i) {
			return getRuleContext(ContractDefinitionContext.class,i);
		}
		public List<InterfaceDefinitionContext> interfaceDefinition() {
			return getRuleContexts(InterfaceDefinitionContext.class);
		}
		public InterfaceDefinitionContext interfaceDefinition(int i) {
			return getRuleContext(InterfaceDefinitionContext.class,i);
		}
		public List<LibraryDefinitionContext> libraryDefinition() {
			return getRuleContexts(LibraryDefinitionContext.class);
		}
		public LibraryDefinitionContext libraryDefinition(int i) {
			return getRuleContext(LibraryDefinitionContext.class,i);
		}
		public List<FunctionDefinitionContext> functionDefinition() {
			return getRuleContexts(FunctionDefinitionContext.class);
		}
		public FunctionDefinitionContext functionDefinition(int i) {
			return getRuleContext(FunctionDefinitionContext.class,i);
		}
		public List<ConstantVariableDeclarationContext> constantVariableDeclaration() {
			return getRuleContexts(ConstantVariableDeclarationContext.class);
		}
		public ConstantVariableDeclarationContext constantVariableDeclaration(int i) {
			return getRuleContext(ConstantVariableDeclarationContext.class,i);
		}
		public List<StructDefinitionContext> structDefinition() {
			return getRuleContexts(StructDefinitionContext.class);
		}
		public StructDefinitionContext structDefinition(int i) {
			return getRuleContext(StructDefinitionContext.class,i);
		}
		public List<EnumDefinitionContext> enumDefinition() {
			return getRuleContexts(EnumDefinitionContext.class);
		}
		public EnumDefinitionContext enumDefinition(int i) {
			return getRuleContext(EnumDefinitionContext.class,i);
		}
		public List<UserDefinedValueTypeDefinitionContext> userDefinedValueTypeDefinition() {
			return getRuleContexts(UserDefinedValueTypeDefinitionContext.class);
		}
		public UserDefinedValueTypeDefinitionContext userDefinedValueTypeDefinition(int i) {
			return getRuleContext(UserDefinedValueTypeDefinitionContext.class,i);
		}
		public List<ErrorDefinitionContext> errorDefinition() {
			return getRuleContexts(ErrorDefinitionContext.class);
		}
		public ErrorDefinitionContext errorDefinition(int i) {
			return getRuleContext(ErrorDefinitionContext.class,i);
		}
		public SbaContext sba() {
			return getRuleContext(SbaContext.class,0);
		}
		public SourceUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sourceUnit; }
	}

	public final SourceUnitContext sourceUnit() throws RecognitionException {
		SourceUnitContext _localctx = new SourceUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_sourceUnit);
		int _la;
		try {
			setState(192);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EOF:
			case T__1:
			case T__12:
			case T__19:
			case T__20:
			case T__21:
			case T__29:
			case T__34:
			case T__37:
			case T__38:
			case T__40:
			case T__45:
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__12) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__29) | (1L << T__34) | (1L << T__37) | (1L << T__38) | (1L << T__40) | (1L << T__45) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (T__63 - 64)) | (1L << (T__64 - 64)) | (1L << (T__65 - 64)) | (1L << (Uint - 64)) | (1L << (Int - 64)) | (1L << (Byte - 64)) | (1L << (Fixed - 64)) | (1L << (Ufixed - 64)))) != 0) || _la==Identifier) {
					{
					setState(183);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
					case 1:
						{
						setState(172);
						pragmaDirective();
						}
						break;
					case 2:
						{
						setState(173);
						importDirective();
						}
						break;
					case 3:
						{
						setState(174);
						contractDefinition();
						}
						break;
					case 4:
						{
						setState(175);
						interfaceDefinition();
						}
						break;
					case 5:
						{
						setState(176);
						libraryDefinition();
						}
						break;
					case 6:
						{
						setState(177);
						functionDefinition();
						}
						break;
					case 7:
						{
						setState(178);
						constantVariableDeclaration();
						}
						break;
					case 8:
						{
						setState(179);
						structDefinition();
						}
						break;
					case 9:
						{
						setState(180);
						enumDefinition();
						}
						break;
					case 10:
						{
						setState(181);
						userDefinedValueTypeDefinition();
						}
						break;
					case 11:
						{
						setState(182);
						errorDefinition();
						}
						break;
					}
					}
					setState(187);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(188);
				match(EOF);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(189);
				sba();
				setState(190);
				match(EOF);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SbaContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(SolidityParser.EOF, 0); }
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ContractBodyElementContext contractBodyElement() {
			return getRuleContext(ContractBodyElementContext.class,0);
		}
		public SbaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sba; }
	}

	public final SbaContext sba() throws RecognitionException {
		SbaContext _localctx = new SbaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sba);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(T__0);
			setState(199);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(195);
				typeName(0);
				}
				break;
			case 2:
				{
				setState(196);
				expression(0);
				}
				break;
			case 3:
				{
				setState(197);
				statement();
				}
				break;
			case 4:
				{
				setState(198);
				contractBodyElement();
				}
				break;
			}
			setState(201);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PragmaDirectiveContext extends ParserRuleContext {
		public Token name;
		public VersionContext ver;
		public VersionContext version() {
			return getRuleContext(VersionContext.class,0);
		}
		public PragmaDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragmaDirective; }
	}

	public final PragmaDirectiveContext pragmaDirective() throws RecognitionException {
		PragmaDirectiveContext _localctx = new PragmaDirectiveContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_pragmaDirective);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(T__1);
			setState(204);
			((PragmaDirectiveContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__2 || _la==T__3) ) {
				((PragmaDirectiveContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(205);
			((PragmaDirectiveContext)_localctx).ver = version();
			setState(206);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VersionContext extends ParserRuleContext {
		public List<VersionConstraintContext> versionConstraint() {
			return getRuleContexts(VersionConstraintContext.class);
		}
		public VersionConstraintContext versionConstraint(int i) {
			return getRuleContext(VersionConstraintContext.class,i);
		}
		public VersionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_version; }
	}

	public final VersionContext version() throws RecognitionException {
		VersionContext _localctx = new VersionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_version);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			versionConstraint();
			setState(210);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11))) != 0) || _la==VersionLiteral) {
				{
				setState(209);
				versionConstraint();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VersionOperatorContext extends ParserRuleContext {
		public VersionOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_versionOperator; }
	}

	public final VersionOperatorContext versionOperator() throws RecognitionException {
		VersionOperatorContext _localctx = new VersionOperatorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_versionOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VersionConstraintContext extends ParserRuleContext {
		public TerminalNode VersionLiteral() { return getToken(SolidityParser.VersionLiteral, 0); }
		public VersionOperatorContext versionOperator() {
			return getRuleContext(VersionOperatorContext.class,0);
		}
		public VersionConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_versionConstraint; }
	}

	public final VersionConstraintContext versionConstraint() throws RecognitionException {
		VersionConstraintContext _localctx = new VersionConstraintContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_versionConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11))) != 0)) {
				{
				setState(214);
				versionOperator();
				}
			}

			setState(217);
			match(VersionLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDirectiveContext extends ParserRuleContext {
		public PathContext import_path;
		public IdentifierContext unitAlias;
		public SymbolAliasesContext symbolAliases() {
			return getRuleContext(SymbolAliasesContext.class,0);
		}
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ImportDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDirective; }
	}

	public final ImportDirectiveContext importDirective() throws RecognitionException {
		ImportDirectiveContext _localctx = new ImportDirectiveContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_importDirective);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			match(T__12);
			setState(235);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case StringLiteral:
				{
				{
				setState(220);
				((ImportDirectiveContext)_localctx).import_path = path();
				setState(223);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(221);
					match(T__13);
					setState(222);
					((ImportDirectiveContext)_localctx).unitAlias = identifier();
					}
				}

				}
				}
				break;
			case T__16:
				{
				{
				setState(225);
				symbolAliases();
				setState(226);
				match(T__14);
				setState(227);
				((ImportDirectiveContext)_localctx).import_path = path();
				}
				}
				break;
			case T__15:
				{
				{
				setState(229);
				match(T__15);
				setState(230);
				match(T__13);
				setState(231);
				((ImportDirectiveContext)_localctx).unitAlias = identifier();
				setState(232);
				match(T__14);
				setState(233);
				((ImportDirectiveContext)_localctx).import_path = path();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(237);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportAliasesContext extends ParserRuleContext {
		public IdentifierContext symbol;
		public IdentifierContext alias;
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public ImportAliasesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importAliases; }
	}

	public final ImportAliasesContext importAliases() throws RecognitionException {
		ImportAliasesContext _localctx = new ImportAliasesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_importAliases);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			((ImportAliasesContext)_localctx).symbol = identifier();
			setState(242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(240);
				match(T__13);
				setState(241);
				((ImportAliasesContext)_localctx).alias = identifier();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathContext extends ParserRuleContext {
		public TerminalNode StringLiteral() { return getToken(SolidityParser.StringLiteral, 0); }
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_path);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(StringLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SymbolAliasesContext extends ParserRuleContext {
		public ImportAliasesContext importAliases;
		public List<ImportAliasesContext> aliases = new ArrayList<ImportAliasesContext>();
		public List<ImportAliasesContext> importAliases() {
			return getRuleContexts(ImportAliasesContext.class);
		}
		public ImportAliasesContext importAliases(int i) {
			return getRuleContext(ImportAliasesContext.class,i);
		}
		public SymbolAliasesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbolAliases; }
	}

	public final SymbolAliasesContext symbolAliases() throws RecognitionException {
		SymbolAliasesContext _localctx = new SymbolAliasesContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_symbolAliases);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(T__16);
			setState(247);
			((SymbolAliasesContext)_localctx).importAliases = importAliases();
			((SymbolAliasesContext)_localctx).aliases.add(((SymbolAliasesContext)_localctx).importAliases);
			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(248);
				match(T__17);
				setState(249);
				((SymbolAliasesContext)_localctx).importAliases = importAliases();
				((SymbolAliasesContext)_localctx).aliases.add(((SymbolAliasesContext)_localctx).importAliases);
				}
				}
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(255);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContractDefinitionContext extends ParserRuleContext {
		public IdentifierContext idf;
		public ContractBodyElementContext contractBodyElement;
		public List<ContractBodyElementContext> parts = new ArrayList<ContractBodyElementContext>();
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public InheritanceSpecifierListContext inheritanceSpecifierList() {
			return getRuleContext(InheritanceSpecifierListContext.class,0);
		}
		public List<ContractBodyElementContext> contractBodyElement() {
			return getRuleContexts(ContractBodyElementContext.class);
		}
		public ContractBodyElementContext contractBodyElement(int i) {
			return getRuleContext(ContractBodyElementContext.class,i);
		}
		public ContractDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_contractDefinition; }
	}

	public final ContractDefinitionContext contractDefinition() throws RecognitionException {
		ContractDefinitionContext _localctx = new ContractDefinitionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_contractDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(257);
			match(T__19);
			}
			setState(258);
			((ContractDefinitionContext)_localctx).idf = identifier();
			setState(260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__22) {
				{
				setState(259);
				inheritanceSpecifierList();
				}
			}

			setState(262);
			match(T__16);
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 29)) & ~0x3f) == 0 && ((1L << (_la - 29)) & ((1L << (T__28 - 29)) | (1L << (T__29 - 29)) | (1L << (T__30 - 29)) | (1L << (T__31 - 29)) | (1L << (T__33 - 29)) | (1L << (T__34 - 29)) | (1L << (T__37 - 29)) | (1L << (T__38 - 29)) | (1L << (T__39 - 29)) | (1L << (T__40 - 29)) | (1L << (T__41 - 29)) | (1L << (T__45 - 29)) | (1L << (T__59 - 29)) | (1L << (T__60 - 29)) | (1L << (T__61 - 29)) | (1L << (T__62 - 29)) | (1L << (T__63 - 29)) | (1L << (T__64 - 29)) | (1L << (T__65 - 29)))) != 0) || ((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Uint - 102)) | (1L << (Int - 102)) | (1L << (Byte - 102)) | (1L << (Fixed - 102)) | (1L << (Ufixed - 102)) | (1L << (FinalKeyword - 102)) | (1L << (Identifier - 102)))) != 0)) {
				{
				{
				setState(263);
				((ContractDefinitionContext)_localctx).contractBodyElement = contractBodyElement();
				((ContractDefinitionContext)_localctx).parts.add(((ContractDefinitionContext)_localctx).contractBodyElement);
				}
				}
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(269);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public InheritanceSpecifierListContext inheritanceSpecifierList() {
			return getRuleContext(InheritanceSpecifierListContext.class,0);
		}
		public List<ContractBodyElementContext> contractBodyElement() {
			return getRuleContexts(ContractBodyElementContext.class);
		}
		public ContractBodyElementContext contractBodyElement(int i) {
			return getRuleContext(ContractBodyElementContext.class,i);
		}
		public InterfaceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceDefinition; }
	}

	public final InterfaceDefinitionContext interfaceDefinition() throws RecognitionException {
		InterfaceDefinitionContext _localctx = new InterfaceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_interfaceDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(T__20);
			setState(272);
			((InterfaceDefinitionContext)_localctx).name = identifier();
			setState(274);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__22) {
				{
				setState(273);
				inheritanceSpecifierList();
				}
			}

			setState(276);
			match(T__16);
			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 29)) & ~0x3f) == 0 && ((1L << (_la - 29)) & ((1L << (T__28 - 29)) | (1L << (T__29 - 29)) | (1L << (T__30 - 29)) | (1L << (T__31 - 29)) | (1L << (T__33 - 29)) | (1L << (T__34 - 29)) | (1L << (T__37 - 29)) | (1L << (T__38 - 29)) | (1L << (T__39 - 29)) | (1L << (T__40 - 29)) | (1L << (T__41 - 29)) | (1L << (T__45 - 29)) | (1L << (T__59 - 29)) | (1L << (T__60 - 29)) | (1L << (T__61 - 29)) | (1L << (T__62 - 29)) | (1L << (T__63 - 29)) | (1L << (T__64 - 29)) | (1L << (T__65 - 29)))) != 0) || ((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Uint - 102)) | (1L << (Int - 102)) | (1L << (Byte - 102)) | (1L << (Fixed - 102)) | (1L << (Ufixed - 102)) | (1L << (FinalKeyword - 102)) | (1L << (Identifier - 102)))) != 0)) {
				{
				{
				setState(277);
				contractBodyElement();
				}
				}
				setState(282);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(283);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LibraryDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<ContractBodyElementContext> contractBodyElement() {
			return getRuleContexts(ContractBodyElementContext.class);
		}
		public ContractBodyElementContext contractBodyElement(int i) {
			return getRuleContext(ContractBodyElementContext.class,i);
		}
		public LibraryDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_libraryDefinition; }
	}

	public final LibraryDefinitionContext libraryDefinition() throws RecognitionException {
		LibraryDefinitionContext _localctx = new LibraryDefinitionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_libraryDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(T__21);
			setState(286);
			((LibraryDefinitionContext)_localctx).name = identifier();
			setState(287);
			match(T__16);
			setState(291);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 29)) & ~0x3f) == 0 && ((1L << (_la - 29)) & ((1L << (T__28 - 29)) | (1L << (T__29 - 29)) | (1L << (T__30 - 29)) | (1L << (T__31 - 29)) | (1L << (T__33 - 29)) | (1L << (T__34 - 29)) | (1L << (T__37 - 29)) | (1L << (T__38 - 29)) | (1L << (T__39 - 29)) | (1L << (T__40 - 29)) | (1L << (T__41 - 29)) | (1L << (T__45 - 29)) | (1L << (T__59 - 29)) | (1L << (T__60 - 29)) | (1L << (T__61 - 29)) | (1L << (T__62 - 29)) | (1L << (T__63 - 29)) | (1L << (T__64 - 29)) | (1L << (T__65 - 29)))) != 0) || ((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Uint - 102)) | (1L << (Int - 102)) | (1L << (Byte - 102)) | (1L << (Fixed - 102)) | (1L << (Ufixed - 102)) | (1L << (FinalKeyword - 102)) | (1L << (Identifier - 102)))) != 0)) {
				{
				{
				setState(288);
				contractBodyElement();
				}
				}
				setState(293);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(294);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InheritanceSpecifierListContext extends ParserRuleContext {
		public InheritanceSpecifierContext inheritanceSpecifier;
		public List<InheritanceSpecifierContext> inheritanceSpecifiers = new ArrayList<InheritanceSpecifierContext>();
		public List<InheritanceSpecifierContext> inheritanceSpecifier() {
			return getRuleContexts(InheritanceSpecifierContext.class);
		}
		public InheritanceSpecifierContext inheritanceSpecifier(int i) {
			return getRuleContext(InheritanceSpecifierContext.class,i);
		}
		public InheritanceSpecifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inheritanceSpecifierList; }
	}

	public final InheritanceSpecifierListContext inheritanceSpecifierList() throws RecognitionException {
		InheritanceSpecifierListContext _localctx = new InheritanceSpecifierListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_inheritanceSpecifierList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			match(T__22);
			setState(297);
			((InheritanceSpecifierListContext)_localctx).inheritanceSpecifier = inheritanceSpecifier();
			((InheritanceSpecifierListContext)_localctx).inheritanceSpecifiers.add(((InheritanceSpecifierListContext)_localctx).inheritanceSpecifier);
			setState(302);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(298);
				match(T__17);
				setState(299);
				((InheritanceSpecifierListContext)_localctx).inheritanceSpecifier = inheritanceSpecifier();
				((InheritanceSpecifierListContext)_localctx).inheritanceSpecifiers.add(((InheritanceSpecifierListContext)_localctx).inheritanceSpecifier);
				}
				}
				setState(304);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InheritanceSpecifierContext extends ParserRuleContext {
		public IdentifierPathContext name;
		public CallArgumentListContext arguments;
		public IdentifierPathContext identifierPath() {
			return getRuleContext(IdentifierPathContext.class,0);
		}
		public CallArgumentListContext callArgumentList() {
			return getRuleContext(CallArgumentListContext.class,0);
		}
		public InheritanceSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inheritanceSpecifier; }
	}

	public final InheritanceSpecifierContext inheritanceSpecifier() throws RecognitionException {
		InheritanceSpecifierContext _localctx = new InheritanceSpecifierContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_inheritanceSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			((InheritanceSpecifierContext)_localctx).name = identifierPath();
			setState(307);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(306);
				((InheritanceSpecifierContext)_localctx).arguments = callArgumentList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContractBodyElementContext extends ParserRuleContext {
		public ConstructorDefinitionContext constructorDefinition() {
			return getRuleContext(ConstructorDefinitionContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public ModifierDefinitionContext modifierDefinition() {
			return getRuleContext(ModifierDefinitionContext.class,0);
		}
		public FallbackFunctionDefinitionContext fallbackFunctionDefinition() {
			return getRuleContext(FallbackFunctionDefinitionContext.class,0);
		}
		public ReceiveFunctionDefinitionContext receiveFunctionDefinition() {
			return getRuleContext(ReceiveFunctionDefinitionContext.class,0);
		}
		public StructDefinitionContext structDefinition() {
			return getRuleContext(StructDefinitionContext.class,0);
		}
		public EnumDefinitionContext enumDefinition() {
			return getRuleContext(EnumDefinitionContext.class,0);
		}
		public UserDefinedValueTypeDefinitionContext userDefinedValueTypeDefinition() {
			return getRuleContext(UserDefinedValueTypeDefinitionContext.class,0);
		}
		public StateVariableDeclarationContext stateVariableDeclaration() {
			return getRuleContext(StateVariableDeclarationContext.class,0);
		}
		public EventDefinitionContext eventDefinition() {
			return getRuleContext(EventDefinitionContext.class,0);
		}
		public ErrorDefinitionContext errorDefinition() {
			return getRuleContext(ErrorDefinitionContext.class,0);
		}
		public UsingDirectiveContext usingDirective() {
			return getRuleContext(UsingDirectiveContext.class,0);
		}
		public ContractBodyElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_contractBodyElement; }
	}

	public final ContractBodyElementContext contractBodyElement() throws RecognitionException {
		ContractBodyElementContext _localctx = new ContractBodyElementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_contractBodyElement);
		try {
			setState(321);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(309);
				constructorDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(310);
				functionDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(311);
				modifierDefinition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(312);
				fallbackFunctionDefinition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(313);
				receiveFunctionDefinition();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(314);
				structDefinition();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(315);
				enumDefinition();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(316);
				userDefinedValueTypeDefinition();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(317);
				stateVariableDeclaration();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(318);
				eventDefinition();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(319);
				errorDefinition();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(320);
				usingDirective();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedArgumentContext extends ParserRuleContext {
		public IdentifierContext name;
		public ExpressionContext value;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NamedArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedArgument; }
	}

	public final NamedArgumentContext namedArgument() throws RecognitionException {
		NamedArgumentContext _localctx = new NamedArgumentContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_namedArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(323);
			((NamedArgumentContext)_localctx).name = identifier();
			setState(324);
			match(T__23);
			setState(325);
			((NamedArgumentContext)_localctx).value = expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<NamedArgumentContext> namedArgument() {
			return getRuleContexts(NamedArgumentContext.class);
		}
		public NamedArgumentContext namedArgument(int i) {
			return getRuleContext(NamedArgumentContext.class,i);
		}
		public CallArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callArgumentList; }
	}

	public final CallArgumentListContext callArgumentList() throws RecognitionException {
		CallArgumentListContext _localctx = new CallArgumentListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_callArgumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(T__24);
			setState(350);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__24:
			case T__25:
			case T__38:
			case T__43:
			case T__46:
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case T__66:
			case T__67:
			case T__68:
			case T__69:
			case T__92:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
			case MeKeyword:
			case AllKeyword:
			case TeeKeyword:
			case BooleanLiteral:
			case DecimalNumber:
			case HexNumber:
			case Payable:
			case Identifier:
			case StringLiteral:
				{
				setState(336);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
					{
					setState(328);
					expression(0);
					setState(333);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__17) {
						{
						{
						setState(329);
						match(T__17);
						setState(330);
						expression(0);
						}
						}
						setState(335);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				}
				break;
			case T__16:
				{
				setState(338);
				match(T__16);
				setState(347);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(339);
					namedArgument();
					setState(344);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__17) {
						{
						{
						setState(340);
						match(T__17);
						setState(341);
						namedArgument();
						}
						}
						setState(346);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(349);
				match(T__18);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(352);
			match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierPathContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public IdentifierPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierPath; }
	}

	public final IdentifierPathContext identifierPath() throws RecognitionException {
		IdentifierPathContext _localctx = new IdentifierPathContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_identifierPath);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(354);
			identifier();
			setState(359);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__26) {
				{
				{
				setState(355);
				match(T__26);
				setState(356);
				identifier();
				}
				}
				setState(361);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StateVariableDeclarationContext extends ParserRuleContext {
		public Token s130;
		public List<Token> keywords = new ArrayList<Token>();
		public AnnotatedTypeNameContext annotated_type;
		public Token s124;
		public Token s123;
		public Token s121;
		public Token s117;
		public Token s28;
		public IdentifierContext idf;
		public ExpressionContext expr;
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<OverrideSpecifierContext> overrideSpecifier() {
			return getRuleContexts(OverrideSpecifierContext.class);
		}
		public OverrideSpecifierContext overrideSpecifier(int i) {
			return getRuleContext(OverrideSpecifierContext.class,i);
		}
		public List<TerminalNode> FinalKeyword() { return getTokens(SolidityParser.FinalKeyword); }
		public TerminalNode FinalKeyword(int i) {
			return getToken(SolidityParser.FinalKeyword, i);
		}
		public List<TerminalNode> PublicKeyword() { return getTokens(SolidityParser.PublicKeyword); }
		public TerminalNode PublicKeyword(int i) {
			return getToken(SolidityParser.PublicKeyword, i);
		}
		public List<TerminalNode> PrivateKeyword() { return getTokens(SolidityParser.PrivateKeyword); }
		public TerminalNode PrivateKeyword(int i) {
			return getToken(SolidityParser.PrivateKeyword, i);
		}
		public List<TerminalNode> InternalKeyword() { return getTokens(SolidityParser.InternalKeyword); }
		public TerminalNode InternalKeyword(int i) {
			return getToken(SolidityParser.InternalKeyword, i);
		}
		public List<TerminalNode> ConstantKeyword() { return getTokens(SolidityParser.ConstantKeyword); }
		public TerminalNode ConstantKeyword(int i) {
			return getToken(SolidityParser.ConstantKeyword, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StateVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stateVariableDeclaration; }
	}

	public final StateVariableDeclarationContext stateVariableDeclaration() throws RecognitionException {
		StateVariableDeclarationContext _localctx = new StateVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_stateVariableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FinalKeyword) {
				{
				{
				setState(362);
				((StateVariableDeclarationContext)_localctx).s130 = match(FinalKeyword);
				((StateVariableDeclarationContext)_localctx).keywords.add(((StateVariableDeclarationContext)_localctx).s130);
				}
				}
				setState(367);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(368);
			((StateVariableDeclarationContext)_localctx).annotated_type = annotatedTypeName();
			setState(377);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__27 || _la==T__36 || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & ((1L << (ConstantKeyword - 117)) | (1L << (InternalKeyword - 117)) | (1L << (PrivateKeyword - 117)) | (1L << (PublicKeyword - 117)))) != 0)) {
				{
				setState(375);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PublicKeyword:
					{
					setState(369);
					((StateVariableDeclarationContext)_localctx).s124 = match(PublicKeyword);
					((StateVariableDeclarationContext)_localctx).keywords.add(((StateVariableDeclarationContext)_localctx).s124);
					}
					break;
				case PrivateKeyword:
					{
					setState(370);
					((StateVariableDeclarationContext)_localctx).s123 = match(PrivateKeyword);
					((StateVariableDeclarationContext)_localctx).keywords.add(((StateVariableDeclarationContext)_localctx).s123);
					}
					break;
				case InternalKeyword:
					{
					setState(371);
					((StateVariableDeclarationContext)_localctx).s121 = match(InternalKeyword);
					((StateVariableDeclarationContext)_localctx).keywords.add(((StateVariableDeclarationContext)_localctx).s121);
					}
					break;
				case ConstantKeyword:
					{
					setState(372);
					((StateVariableDeclarationContext)_localctx).s117 = match(ConstantKeyword);
					((StateVariableDeclarationContext)_localctx).keywords.add(((StateVariableDeclarationContext)_localctx).s117);
					}
					break;
				case T__36:
					{
					setState(373);
					overrideSpecifier();
					}
					break;
				case T__27:
					{
					setState(374);
					((StateVariableDeclarationContext)_localctx).s28 = match(T__27);
					((StateVariableDeclarationContext)_localctx).keywords.add(((StateVariableDeclarationContext)_localctx).s28);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(379);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(380);
			((StateVariableDeclarationContext)_localctx).idf = identifier();
			setState(383);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(381);
				match(T__11);
				setState(382);
				((StateVariableDeclarationContext)_localctx).expr = expression(0);
				}
			}

			setState(385);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDefinitionContext extends ParserRuleContext {
		public ParameterListContext parameters;
		public Token s122;
		public List<Token> modifiers = new ArrayList<Token>();
		public Token s121;
		public Token s124;
		public BlockContext body;
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<ModifierInvocationContext> modifierInvocation() {
			return getRuleContexts(ModifierInvocationContext.class);
		}
		public ModifierInvocationContext modifierInvocation(int i) {
			return getRuleContext(ModifierInvocationContext.class,i);
		}
		public List<TerminalNode> Payable() { return getTokens(SolidityParser.Payable); }
		public TerminalNode Payable(int i) {
			return getToken(SolidityParser.Payable, i);
		}
		public List<TerminalNode> InternalKeyword() { return getTokens(SolidityParser.InternalKeyword); }
		public TerminalNode InternalKeyword(int i) {
			return getToken(SolidityParser.InternalKeyword, i);
		}
		public List<TerminalNode> PublicKeyword() { return getTokens(SolidityParser.PublicKeyword); }
		public TerminalNode PublicKeyword(int i) {
			return getToken(SolidityParser.PublicKeyword, i);
		}
		public ConstructorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDefinition; }
	}

	public final ConstructorDefinitionContext constructorDefinition() throws RecognitionException {
		ConstructorDefinitionContext _localctx = new ConstructorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_constructorDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			match(T__28);
			setState(388);
			((ConstructorDefinitionContext)_localctx).parameters = parameterList();
			setState(395);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (InternalKeyword - 121)) | (1L << (Payable - 121)) | (1L << (PublicKeyword - 121)) | (1L << (Identifier - 121)))) != 0)) {
				{
				setState(393);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Identifier:
					{
					setState(389);
					modifierInvocation();
					}
					break;
				case Payable:
					{
					setState(390);
					((ConstructorDefinitionContext)_localctx).s122 = match(Payable);
					((ConstructorDefinitionContext)_localctx).modifiers.add(((ConstructorDefinitionContext)_localctx).s122);
					}
					break;
				case InternalKeyword:
					{
					setState(391);
					((ConstructorDefinitionContext)_localctx).s121 = match(InternalKeyword);
					((ConstructorDefinitionContext)_localctx).modifiers.add(((ConstructorDefinitionContext)_localctx).s121);
					}
					break;
				case PublicKeyword:
					{
					setState(392);
					((ConstructorDefinitionContext)_localctx).s124 = match(PublicKeyword);
					((ConstructorDefinitionContext)_localctx).modifiers.add(((ConstructorDefinitionContext)_localctx).s124);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(397);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(398);
			((ConstructorDefinitionContext)_localctx).body = block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public ParameterListContext parameters;
		public Token s33;
		public List<Token> modifiers = new ArrayList<Token>();
		public ReturnParametersContext return_parameters;
		public BlockContext body;
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<VisibilityContext> visibility() {
			return getRuleContexts(VisibilityContext.class);
		}
		public VisibilityContext visibility(int i) {
			return getRuleContext(VisibilityContext.class,i);
		}
		public List<StateMutabilityContext> stateMutability() {
			return getRuleContexts(StateMutabilityContext.class);
		}
		public StateMutabilityContext stateMutability(int i) {
			return getRuleContext(StateMutabilityContext.class,i);
		}
		public List<ModifierInvocationContext> modifierInvocation() {
			return getRuleContexts(ModifierInvocationContext.class);
		}
		public ModifierInvocationContext modifierInvocation(int i) {
			return getRuleContext(ModifierInvocationContext.class,i);
		}
		public List<OverrideSpecifierContext> overrideSpecifier() {
			return getRuleContexts(OverrideSpecifierContext.class);
		}
		public OverrideSpecifierContext overrideSpecifier(int i) {
			return getRuleContext(OverrideSpecifierContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ReturnParametersContext returnParameters() {
			return getRuleContext(ReturnParametersContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(400);
			match(T__29);
			setState(404);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(401);
				identifier();
				}
				break;
			case T__30:
				{
				setState(402);
				match(T__30);
				}
				break;
			case T__31:
				{
				setState(403);
				match(T__31);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(406);
			((FunctionDefinitionContext)_localctx).parameters = parameterList();
			setState(414);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__32 || _la==T__36 || ((((_la - 119)) & ~0x3f) == 0 && ((1L << (_la - 119)) & ((1L << (ExternalKeyword - 119)) | (1L << (InternalKeyword - 119)) | (1L << (Payable - 119)) | (1L << (PrivateKeyword - 119)) | (1L << (PublicKeyword - 119)) | (1L << (PureKeyword - 119)) | (1L << (ViewKeyword - 119)) | (1L << (Identifier - 119)))) != 0)) {
				{
				setState(412);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ExternalKeyword:
				case InternalKeyword:
				case PrivateKeyword:
				case PublicKeyword:
					{
					setState(407);
					visibility();
					}
					break;
				case Payable:
				case PureKeyword:
				case ViewKeyword:
					{
					setState(408);
					stateMutability();
					}
					break;
				case Identifier:
					{
					setState(409);
					modifierInvocation();
					}
					break;
				case T__32:
					{
					setState(410);
					((FunctionDefinitionContext)_localctx).s33 = match(T__32);
					((FunctionDefinitionContext)_localctx).modifiers.add(((FunctionDefinitionContext)_localctx).s33);
					}
					break;
				case T__36:
					{
					setState(411);
					overrideSpecifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(416);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(418);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__35) {
				{
				setState(417);
				((FunctionDefinitionContext)_localctx).return_parameters = returnParameters();
				}
			}

			setState(422);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				{
				setState(420);
				match(T__4);
				}
				break;
			case T__16:
				{
				setState(421);
				((FunctionDefinitionContext)_localctx).body = block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifierDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public ParameterListContext parameters;
		public Token virtual;
		public BlockContext body;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public List<OverrideSpecifierContext> overrideSpecifier() {
			return getRuleContexts(OverrideSpecifierContext.class);
		}
		public OverrideSpecifierContext overrideSpecifier(int i) {
			return getRuleContext(OverrideSpecifierContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ModifierDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifierDefinition; }
	}

	public final ModifierDefinitionContext modifierDefinition() throws RecognitionException {
		ModifierDefinitionContext _localctx = new ModifierDefinitionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_modifierDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(424);
			match(T__33);
			setState(425);
			((ModifierDefinitionContext)_localctx).name = identifier();
			setState(426);
			((ModifierDefinitionContext)_localctx).parameters = parameterList();
			setState(431);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__32 || _la==T__36) {
				{
				setState(429);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__32:
					{
					setState(427);
					((ModifierDefinitionContext)_localctx).virtual = match(T__32);
					}
					break;
				case T__36:
					{
					setState(428);
					overrideSpecifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(433);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(436);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				{
				setState(434);
				match(T__4);
				}
				break;
			case T__16:
				{
				setState(435);
				((ModifierDefinitionContext)_localctx).body = block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FallbackFunctionDefinitionContext extends ParserRuleContext {
		public ParameterListContext parameters;
		public Token s119;
		public List<Token> modifiers = new ArrayList<Token>();
		public Token s33;
		public ReturnParametersContext return_parameters;
		public BlockContext body;
		public List<StateMutabilityContext> stateMutability() {
			return getRuleContexts(StateMutabilityContext.class);
		}
		public StateMutabilityContext stateMutability(int i) {
			return getRuleContext(StateMutabilityContext.class,i);
		}
		public List<ModifierInvocationContext> modifierInvocation() {
			return getRuleContexts(ModifierInvocationContext.class);
		}
		public ModifierInvocationContext modifierInvocation(int i) {
			return getRuleContext(ModifierInvocationContext.class,i);
		}
		public List<OverrideSpecifierContext> overrideSpecifier() {
			return getRuleContexts(OverrideSpecifierContext.class);
		}
		public OverrideSpecifierContext overrideSpecifier(int i) {
			return getRuleContext(OverrideSpecifierContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public List<TerminalNode> ExternalKeyword() { return getTokens(SolidityParser.ExternalKeyword); }
		public TerminalNode ExternalKeyword(int i) {
			return getToken(SolidityParser.ExternalKeyword, i);
		}
		public ReturnParametersContext returnParameters() {
			return getRuleContext(ReturnParametersContext.class,0);
		}
		public FallbackFunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fallbackFunctionDefinition; }
	}

	public final FallbackFunctionDefinitionContext fallbackFunctionDefinition() throws RecognitionException {
		FallbackFunctionDefinitionContext _localctx = new FallbackFunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_fallbackFunctionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(438);
			match(T__30);
			setState(440);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(439);
				((FallbackFunctionDefinitionContext)_localctx).parameters = parameterList();
				}
			}

			setState(449);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__32 || _la==T__36 || ((((_la - 119)) & ~0x3f) == 0 && ((1L << (_la - 119)) & ((1L << (ExternalKeyword - 119)) | (1L << (Payable - 119)) | (1L << (PureKeyword - 119)) | (1L << (ViewKeyword - 119)) | (1L << (Identifier - 119)))) != 0)) {
				{
				setState(447);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ExternalKeyword:
					{
					setState(442);
					((FallbackFunctionDefinitionContext)_localctx).s119 = match(ExternalKeyword);
					((FallbackFunctionDefinitionContext)_localctx).modifiers.add(((FallbackFunctionDefinitionContext)_localctx).s119);
					}
					break;
				case Payable:
				case PureKeyword:
				case ViewKeyword:
					{
					setState(443);
					stateMutability();
					}
					break;
				case Identifier:
					{
					setState(444);
					modifierInvocation();
					}
					break;
				case T__32:
					{
					setState(445);
					((FallbackFunctionDefinitionContext)_localctx).s33 = match(T__32);
					((FallbackFunctionDefinitionContext)_localctx).modifiers.add(((FallbackFunctionDefinitionContext)_localctx).s33);
					}
					break;
				case T__36:
					{
					setState(446);
					overrideSpecifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(451);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(453);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__35) {
				{
				setState(452);
				((FallbackFunctionDefinitionContext)_localctx).return_parameters = returnParameters();
				}
			}

			setState(457);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				{
				setState(455);
				match(T__4);
				}
				break;
			case T__16:
				{
				setState(456);
				((FallbackFunctionDefinitionContext)_localctx).body = block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReceiveFunctionDefinitionContext extends ParserRuleContext {
		public Token s119;
		public List<Token> modifiers = new ArrayList<Token>();
		public Token s122;
		public Token s33;
		public BlockContext body;
		public List<ModifierInvocationContext> modifierInvocation() {
			return getRuleContexts(ModifierInvocationContext.class);
		}
		public ModifierInvocationContext modifierInvocation(int i) {
			return getRuleContext(ModifierInvocationContext.class,i);
		}
		public List<OverrideSpecifierContext> overrideSpecifier() {
			return getRuleContexts(OverrideSpecifierContext.class);
		}
		public OverrideSpecifierContext overrideSpecifier(int i) {
			return getRuleContext(OverrideSpecifierContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> ExternalKeyword() { return getTokens(SolidityParser.ExternalKeyword); }
		public TerminalNode ExternalKeyword(int i) {
			return getToken(SolidityParser.ExternalKeyword, i);
		}
		public List<TerminalNode> Payable() { return getTokens(SolidityParser.Payable); }
		public TerminalNode Payable(int i) {
			return getToken(SolidityParser.Payable, i);
		}
		public ReceiveFunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_receiveFunctionDefinition; }
	}

	public final ReceiveFunctionDefinitionContext receiveFunctionDefinition() throws RecognitionException {
		ReceiveFunctionDefinitionContext _localctx = new ReceiveFunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_receiveFunctionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(459);
			match(T__31);
			setState(460);
			match(T__24);
			setState(461);
			match(T__25);
			setState(469);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__32 || _la==T__36 || ((((_la - 119)) & ~0x3f) == 0 && ((1L << (_la - 119)) & ((1L << (ExternalKeyword - 119)) | (1L << (Payable - 119)) | (1L << (Identifier - 119)))) != 0)) {
				{
				setState(467);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ExternalKeyword:
					{
					setState(462);
					((ReceiveFunctionDefinitionContext)_localctx).s119 = match(ExternalKeyword);
					((ReceiveFunctionDefinitionContext)_localctx).modifiers.add(((ReceiveFunctionDefinitionContext)_localctx).s119);
					}
					break;
				case Payable:
					{
					setState(463);
					((ReceiveFunctionDefinitionContext)_localctx).s122 = match(Payable);
					((ReceiveFunctionDefinitionContext)_localctx).modifiers.add(((ReceiveFunctionDefinitionContext)_localctx).s122);
					}
					break;
				case Identifier:
					{
					setState(464);
					modifierInvocation();
					}
					break;
				case T__32:
					{
					setState(465);
					((ReceiveFunctionDefinitionContext)_localctx).s33 = match(T__32);
					((ReceiveFunctionDefinitionContext)_localctx).modifiers.add(((ReceiveFunctionDefinitionContext)_localctx).s33);
					}
					break;
				case T__36:
					{
					setState(466);
					overrideSpecifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(471);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(474);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				{
				setState(472);
				match(T__4);
				}
				break;
			case T__16:
				{
				setState(473);
				((ReceiveFunctionDefinitionContext)_localctx).body = block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<StructMemberContext> structMember() {
			return getRuleContexts(StructMemberContext.class);
		}
		public StructMemberContext structMember(int i) {
			return getRuleContext(StructMemberContext.class,i);
		}
		public StructDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDefinition; }
	}

	public final StructDefinitionContext structDefinition() throws RecognitionException {
		StructDefinitionContext _localctx = new StructDefinitionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_structDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(476);
			match(T__34);
			setState(477);
			((StructDefinitionContext)_localctx).name = identifier();
			setState(478);
			match(T__16);
			setState(480); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(479);
				structMember();
				}
				}
				setState(482); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 30)) & ~0x3f) == 0 && ((1L << (_la - 30)) & ((1L << (T__29 - 30)) | (1L << (T__45 - 30)) | (1L << (T__59 - 30)) | (1L << (T__60 - 30)) | (1L << (T__61 - 30)) | (1L << (T__62 - 30)) | (1L << (T__63 - 30)) | (1L << (T__64 - 30)) | (1L << (T__65 - 30)))) != 0) || ((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Uint - 102)) | (1L << (Int - 102)) | (1L << (Byte - 102)) | (1L << (Fixed - 102)) | (1L << (Ufixed - 102)) | (1L << (Identifier - 102)))) != 0) );
			setState(484);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructMemberContext extends ParserRuleContext {
		public IdentifierContext name;
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public StructMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structMember; }
	}

	public final StructMemberContext structMember() throws RecognitionException {
		StructMemberContext _localctx = new StructMemberContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_structMember);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(486);
			typeName(0);
			setState(487);
			((StructMemberContext)_localctx).name = identifier();
			setState(488);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnParametersContext extends ParserRuleContext {
		public ParameterListContext return_parameters;
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public ReturnParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnParameters; }
	}

	public final ReturnParametersContext returnParameters() throws RecognitionException {
		ReturnParametersContext _localctx = new ReturnParametersContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_returnParameters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(490);
			match(T__35);
			setState(491);
			((ReturnParametersContext)_localctx).return_parameters = parameterList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifierInvocationContext extends ParserRuleContext {
		public IdentifierPathContext identifierPath() {
			return getRuleContext(IdentifierPathContext.class,0);
		}
		public CallArgumentListContext callArgumentList() {
			return getRuleContext(CallArgumentListContext.class,0);
		}
		public ModifierInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifierInvocation; }
	}

	public final ModifierInvocationContext modifierInvocation() throws RecognitionException {
		ModifierInvocationContext _localctx = new ModifierInvocationContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_modifierInvocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(493);
			identifierPath();
			setState(495);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(494);
				callArgumentList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VisibilityContext extends ParserRuleContext {
		public TerminalNode InternalKeyword() { return getToken(SolidityParser.InternalKeyword, 0); }
		public TerminalNode ExternalKeyword() { return getToken(SolidityParser.ExternalKeyword, 0); }
		public TerminalNode PrivateKeyword() { return getToken(SolidityParser.PrivateKeyword, 0); }
		public TerminalNode PublicKeyword() { return getToken(SolidityParser.PublicKeyword, 0); }
		public VisibilityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_visibility; }
	}

	public final VisibilityContext visibility() throws RecognitionException {
		VisibilityContext _localctx = new VisibilityContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_visibility);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(497);
			_la = _input.LA(1);
			if ( !(((((_la - 119)) & ~0x3f) == 0 && ((1L << (_la - 119)) & ((1L << (ExternalKeyword - 119)) | (1L << (InternalKeyword - 119)) | (1L << (PrivateKeyword - 119)) | (1L << (PublicKeyword - 119)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StateMutabilityContext extends ParserRuleContext {
		public TerminalNode PureKeyword() { return getToken(SolidityParser.PureKeyword, 0); }
		public TerminalNode ViewKeyword() { return getToken(SolidityParser.ViewKeyword, 0); }
		public TerminalNode Payable() { return getToken(SolidityParser.Payable, 0); }
		public StateMutabilityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stateMutability; }
	}

	public final StateMutabilityContext stateMutability() throws RecognitionException {
		StateMutabilityContext _localctx = new StateMutabilityContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_stateMutability);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(499);
			_la = _input.LA(1);
			if ( !(((((_la - 122)) & ~0x3f) == 0 && ((1L << (_la - 122)) & ((1L << (Payable - 122)) | (1L << (PureKeyword - 122)) | (1L << (ViewKeyword - 122)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OverrideSpecifierContext extends ParserRuleContext {
		public IdentifierPathContext identifierPath;
		public List<IdentifierPathContext> overrides = new ArrayList<IdentifierPathContext>();
		public List<IdentifierPathContext> identifierPath() {
			return getRuleContexts(IdentifierPathContext.class);
		}
		public IdentifierPathContext identifierPath(int i) {
			return getRuleContext(IdentifierPathContext.class,i);
		}
		public OverrideSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_overrideSpecifier; }
	}

	public final OverrideSpecifierContext overrideSpecifier() throws RecognitionException {
		OverrideSpecifierContext _localctx = new OverrideSpecifierContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_overrideSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(501);
			match(T__36);
			setState(513);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(502);
				match(T__24);
				setState(503);
				((OverrideSpecifierContext)_localctx).identifierPath = identifierPath();
				((OverrideSpecifierContext)_localctx).overrides.add(((OverrideSpecifierContext)_localctx).identifierPath);
				setState(508);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(504);
					match(T__17);
					setState(505);
					((OverrideSpecifierContext)_localctx).identifierPath = identifierPath();
					((OverrideSpecifierContext)_localctx).overrides.add(((OverrideSpecifierContext)_localctx).identifierPath);
					}
					}
					setState(510);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(511);
				match(T__25);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public ParameterContext parameter;
		public List<ParameterContext> params = new ArrayList<ParameterContext>();
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(515);
			match(T__24);
			setState(524);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 30)) & ~0x3f) == 0 && ((1L << (_la - 30)) & ((1L << (T__29 - 30)) | (1L << (T__45 - 30)) | (1L << (T__59 - 30)) | (1L << (T__60 - 30)) | (1L << (T__61 - 30)) | (1L << (T__62 - 30)) | (1L << (T__63 - 30)) | (1L << (T__64 - 30)) | (1L << (T__65 - 30)))) != 0) || ((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Uint - 102)) | (1L << (Int - 102)) | (1L << (Byte - 102)) | (1L << (Fixed - 102)) | (1L << (Ufixed - 102)) | (1L << (FinalKeyword - 102)) | (1L << (Identifier - 102)))) != 0)) {
				{
				setState(516);
				((ParameterListContext)_localctx).parameter = parameter();
				((ParameterListContext)_localctx).params.add(((ParameterListContext)_localctx).parameter);
				setState(521);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(517);
					match(T__17);
					setState(518);
					((ParameterListContext)_localctx).parameter = parameter();
					((ParameterListContext)_localctx).params.add(((ParameterListContext)_localctx).parameter);
					}
					}
					setState(523);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(526);
			match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public Token FinalKeyword;
		public List<Token> keywords = new ArrayList<Token>();
		public AnnotatedTypeNameContext annotated_type;
		public DataLocationContext storage_location;
		public IdentifierContext idf;
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public TerminalNode FinalKeyword() { return getToken(SolidityParser.FinalKeyword, 0); }
		public DataLocationContext dataLocation() {
			return getRuleContext(DataLocationContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(529);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FinalKeyword) {
				{
				setState(528);
				((ParameterContext)_localctx).FinalKeyword = match(FinalKeyword);
				((ParameterContext)_localctx).keywords.add(((ParameterContext)_localctx).FinalKeyword);
				}
			}

			setState(531);
			((ParameterContext)_localctx).annotated_type = annotatedTypeName();
			setState(533);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 127)) & ~0x3f) == 0 && ((1L << (_la - 127)) & ((1L << (MemoryKeyword - 127)) | (1L << (StorageKeyword - 127)) | (1L << (CalldataKeyword - 127)))) != 0)) {
				{
				setState(532);
				((ParameterContext)_localctx).storage_location = dataLocation();
				}
			}

			setState(536);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(535);
				((ParameterContext)_localctx).idf = identifier();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumValueContext extends ParserRuleContext {
		public IdentifierContext idf;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EnumValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumValue; }
	}

	public final EnumValueContext enumValue() throws RecognitionException {
		EnumValueContext _localctx = new EnumValueContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_enumValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(538);
			((EnumValueContext)_localctx).idf = identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumDefinitionContext extends ParserRuleContext {
		public IdentifierContext idf;
		public EnumValueContext enumValue;
		public List<EnumValueContext> values = new ArrayList<EnumValueContext>();
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<EnumValueContext> enumValue() {
			return getRuleContexts(EnumValueContext.class);
		}
		public EnumValueContext enumValue(int i) {
			return getRuleContext(EnumValueContext.class,i);
		}
		public EnumDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDefinition; }
	}

	public final EnumDefinitionContext enumDefinition() throws RecognitionException {
		EnumDefinitionContext _localctx = new EnumDefinitionContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_enumDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(540);
			match(T__37);
			setState(541);
			((EnumDefinitionContext)_localctx).idf = identifier();
			setState(542);
			match(T__16);
			setState(544);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(543);
				((EnumDefinitionContext)_localctx).enumValue = enumValue();
				((EnumDefinitionContext)_localctx).values.add(((EnumDefinitionContext)_localctx).enumValue);
				}
			}

			setState(550);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(546);
				match(T__17);
				setState(547);
				((EnumDefinitionContext)_localctx).enumValue = enumValue();
				((EnumDefinitionContext)_localctx).values.add(((EnumDefinitionContext)_localctx).enumValue);
				}
				}
				setState(552);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(553);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UserDefinedValueTypeDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public ElementaryTypeNameContext elementaryTypeName() {
			return getRuleContext(ElementaryTypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public UserDefinedValueTypeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_userDefinedValueTypeDefinition; }
	}

	public final UserDefinedValueTypeDefinitionContext userDefinedValueTypeDefinition() throws RecognitionException {
		UserDefinedValueTypeDefinitionContext _localctx = new UserDefinedValueTypeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_userDefinedValueTypeDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(555);
			match(T__38);
			setState(556);
			((UserDefinedValueTypeDefinitionContext)_localctx).name = identifier();
			setState(557);
			match(T__22);
			setState(558);
			elementaryTypeName();
			setState(559);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantVariableDeclarationContext extends ParserRuleContext {
		public AnnotatedTypeNameContext annotated_type;
		public IdentifierContext idf;
		public ExpressionContext expr;
		public TerminalNode ConstantKeyword() { return getToken(SolidityParser.ConstantKeyword, 0); }
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConstantVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantVariableDeclaration; }
	}

	public final ConstantVariableDeclarationContext constantVariableDeclaration() throws RecognitionException {
		ConstantVariableDeclarationContext _localctx = new ConstantVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_constantVariableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(561);
			((ConstantVariableDeclarationContext)_localctx).annotated_type = annotatedTypeName();
			setState(562);
			match(ConstantKeyword);
			setState(563);
			((ConstantVariableDeclarationContext)_localctx).idf = identifier();
			setState(564);
			match(T__11);
			setState(565);
			((ConstantVariableDeclarationContext)_localctx).expr = expression(0);
			setState(566);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EventParameterContext extends ParserRuleContext {
		public IdentifierContext name;
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public TerminalNode IndexedKeyword() { return getToken(SolidityParser.IndexedKeyword, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EventParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eventParameter; }
	}

	public final EventParameterContext eventParameter() throws RecognitionException {
		EventParameterContext _localctx = new EventParameterContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_eventParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(568);
			annotatedTypeName();
			setState(570);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IndexedKeyword) {
				{
				setState(569);
				match(IndexedKeyword);
				}
			}

			setState(573);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(572);
				((EventParameterContext)_localctx).name = identifier();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EventDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public EventParameterContext eventParameter;
		public List<EventParameterContext> parameters = new ArrayList<EventParameterContext>();
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AnonymousKeyword() { return getToken(SolidityParser.AnonymousKeyword, 0); }
		public List<EventParameterContext> eventParameter() {
			return getRuleContexts(EventParameterContext.class);
		}
		public EventParameterContext eventParameter(int i) {
			return getRuleContext(EventParameterContext.class,i);
		}
		public EventDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eventDefinition; }
	}

	public final EventDefinitionContext eventDefinition() throws RecognitionException {
		EventDefinitionContext _localctx = new EventDefinitionContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_eventDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(575);
			match(T__39);
			setState(576);
			((EventDefinitionContext)_localctx).name = identifier();
			setState(577);
			match(T__24);
			setState(586);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 30)) & ~0x3f) == 0 && ((1L << (_la - 30)) & ((1L << (T__29 - 30)) | (1L << (T__45 - 30)) | (1L << (T__59 - 30)) | (1L << (T__60 - 30)) | (1L << (T__61 - 30)) | (1L << (T__62 - 30)) | (1L << (T__63 - 30)) | (1L << (T__64 - 30)) | (1L << (T__65 - 30)))) != 0) || ((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Uint - 102)) | (1L << (Int - 102)) | (1L << (Byte - 102)) | (1L << (Fixed - 102)) | (1L << (Ufixed - 102)) | (1L << (Identifier - 102)))) != 0)) {
				{
				setState(578);
				((EventDefinitionContext)_localctx).eventParameter = eventParameter();
				((EventDefinitionContext)_localctx).parameters.add(((EventDefinitionContext)_localctx).eventParameter);
				setState(583);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(579);
					match(T__17);
					setState(580);
					((EventDefinitionContext)_localctx).eventParameter = eventParameter();
					((EventDefinitionContext)_localctx).parameters.add(((EventDefinitionContext)_localctx).eventParameter);
					}
					}
					setState(585);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(588);
			match(T__25);
			setState(590);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AnonymousKeyword) {
				{
				setState(589);
				match(AnonymousKeyword);
				}
			}

			setState(592);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ErrorParameterContext extends ParserRuleContext {
		public AnnotatedTypeNameContext typ;
		public IdentifierContext name;
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ErrorParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_errorParameter; }
	}

	public final ErrorParameterContext errorParameter() throws RecognitionException {
		ErrorParameterContext _localctx = new ErrorParameterContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_errorParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(594);
			((ErrorParameterContext)_localctx).typ = annotatedTypeName();
			setState(596);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(595);
				((ErrorParameterContext)_localctx).name = identifier();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ErrorDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public ErrorParameterContext errorParameter;
		public List<ErrorParameterContext> parameters = new ArrayList<ErrorParameterContext>();
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<ErrorParameterContext> errorParameter() {
			return getRuleContexts(ErrorParameterContext.class);
		}
		public ErrorParameterContext errorParameter(int i) {
			return getRuleContext(ErrorParameterContext.class,i);
		}
		public ErrorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_errorDefinition; }
	}

	public final ErrorDefinitionContext errorDefinition() throws RecognitionException {
		ErrorDefinitionContext _localctx = new ErrorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_errorDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(598);
			match(T__40);
			setState(599);
			((ErrorDefinitionContext)_localctx).name = identifier();
			setState(600);
			match(T__24);
			setState(609);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 30)) & ~0x3f) == 0 && ((1L << (_la - 30)) & ((1L << (T__29 - 30)) | (1L << (T__45 - 30)) | (1L << (T__59 - 30)) | (1L << (T__60 - 30)) | (1L << (T__61 - 30)) | (1L << (T__62 - 30)) | (1L << (T__63 - 30)) | (1L << (T__64 - 30)) | (1L << (T__65 - 30)))) != 0) || ((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Uint - 102)) | (1L << (Int - 102)) | (1L << (Byte - 102)) | (1L << (Fixed - 102)) | (1L << (Ufixed - 102)) | (1L << (Identifier - 102)))) != 0)) {
				{
				setState(601);
				((ErrorDefinitionContext)_localctx).errorParameter = errorParameter();
				((ErrorDefinitionContext)_localctx).parameters.add(((ErrorDefinitionContext)_localctx).errorParameter);
				setState(606);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(602);
					match(T__17);
					setState(603);
					((ErrorDefinitionContext)_localctx).errorParameter = errorParameter();
					((ErrorDefinitionContext)_localctx).parameters.add(((ErrorDefinitionContext)_localctx).errorParameter);
					}
					}
					setState(608);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(611);
			match(T__25);
			setState(612);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UsingDirectiveContext extends ParserRuleContext {
		public IdentifierPathContext identifierPath() {
			return getRuleContext(IdentifierPathContext.class,0);
		}
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public UsingDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_usingDirective; }
	}

	public final UsingDirectiveContext usingDirective() throws RecognitionException {
		UsingDirectiveContext _localctx = new UsingDirectiveContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_usingDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(614);
			match(T__41);
			setState(615);
			identifierPath();
			setState(616);
			match(T__42);
			setState(619);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
				{
				setState(617);
				match(T__15);
				}
				break;
			case T__29:
			case T__45:
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
			case Identifier:
				{
				setState(618);
				typeName(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(621);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public Token FinalKeyword;
		public List<Token> keywords = new ArrayList<Token>();
		public AnnotatedTypeNameContext annotated_type;
		public DataLocationContext storage_location;
		public IdentifierContext idf;
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode FinalKeyword() { return getToken(SolidityParser.FinalKeyword, 0); }
		public DataLocationContext dataLocation() {
			return getRuleContext(DataLocationContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(624);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FinalKeyword) {
				{
				setState(623);
				((VariableDeclarationContext)_localctx).FinalKeyword = match(FinalKeyword);
				((VariableDeclarationContext)_localctx).keywords.add(((VariableDeclarationContext)_localctx).FinalKeyword);
				}
			}

			setState(626);
			((VariableDeclarationContext)_localctx).annotated_type = annotatedTypeName();
			setState(628);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 127)) & ~0x3f) == 0 && ((1L << (_la - 127)) & ((1L << (MemoryKeyword - 127)) | (1L << (StorageKeyword - 127)) | (1L << (CalldataKeyword - 127)))) != 0)) {
				{
				setState(627);
				((VariableDeclarationContext)_localctx).storage_location = dataLocation();
				}
			}

			setState(630);
			((VariableDeclarationContext)_localctx).idf = identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeNameContext extends ParserRuleContext {
		public TypeNameContext value_type;
		public ExpressionContext expr;
		public ElementaryTypeNameContext elementaryTypeName() {
			return getRuleContext(ElementaryTypeNameContext.class,0);
		}
		public FunctionTypeNameContext functionTypeName() {
			return getRuleContext(FunctionTypeNameContext.class,0);
		}
		public UserDefinedTypeNameContext userDefinedTypeName() {
			return getRuleContext(UserDefinedTypeNameContext.class,0);
		}
		public MappingContext mapping() {
			return getRuleContext(MappingContext.class,0);
		}
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
	}

	public final TypeNameContext typeName() throws RecognitionException {
		return typeName(0);
	}

	private TypeNameContext typeName(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeNameContext _localctx = new TypeNameContext(_ctx, _parentState);
		TypeNameContext _prevctx = _localctx;
		int _startState = 88;
		enterRecursionRule(_localctx, 88, RULE_typeName, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(637);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
				{
				setState(633);
				elementaryTypeName();
				}
				break;
			case T__29:
				{
				setState(634);
				functionTypeName();
				}
				break;
			case Identifier:
				{
				setState(635);
				userDefinedTypeName();
				}
				break;
			case T__45:
				{
				setState(636);
				mapping();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(647);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypeNameContext(_parentctx, _parentState);
					_localctx.value_type = _prevctx;
					_localctx.value_type = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_typeName);
					setState(639);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(640);
					match(T__43);
					setState(642);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
						{
						setState(641);
						((TypeNameContext)_localctx).expr = expression(0);
						}
					}

					setState(644);
					match(T__44);
					}
					} 
				}
				setState(649);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FunctionTypeNameContext extends ParserRuleContext {
		public ParameterListContext parameters;
		public ReturnParametersContext return_parameters;
		public List<VisibilityContext> visibility() {
			return getRuleContexts(VisibilityContext.class);
		}
		public VisibilityContext visibility(int i) {
			return getRuleContext(VisibilityContext.class,i);
		}
		public List<StateMutabilityContext> stateMutability() {
			return getRuleContexts(StateMutabilityContext.class);
		}
		public StateMutabilityContext stateMutability(int i) {
			return getRuleContext(StateMutabilityContext.class,i);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public ReturnParametersContext returnParameters() {
			return getRuleContext(ReturnParametersContext.class,0);
		}
		public FunctionTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionTypeName; }
	}

	public final FunctionTypeNameContext functionTypeName() throws RecognitionException {
		FunctionTypeNameContext _localctx = new FunctionTypeNameContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_functionTypeName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(650);
			match(T__29);
			setState(652);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				{
				setState(651);
				((FunctionTypeNameContext)_localctx).parameters = parameterList();
				}
				break;
			}
			setState(658);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(656);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case ExternalKeyword:
					case InternalKeyword:
					case PrivateKeyword:
					case PublicKeyword:
						{
						setState(654);
						visibility();
						}
						break;
					case Payable:
					case PureKeyword:
					case ViewKeyword:
						{
						setState(655);
						stateMutability();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(660);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
			}
			setState(662);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(661);
				((FunctionTypeNameContext)_localctx).return_parameters = returnParameters();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UserDefinedTypeNameContext extends ParserRuleContext {
		public IdentifierContext identifier;
		public List<IdentifierContext> names = new ArrayList<IdentifierContext>();
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public UserDefinedTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_userDefinedTypeName; }
	}

	public final UserDefinedTypeNameContext userDefinedTypeName() throws RecognitionException {
		UserDefinedTypeNameContext _localctx = new UserDefinedTypeNameContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_userDefinedTypeName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(664);
			((UserDefinedTypeNameContext)_localctx).identifier = identifier();
			((UserDefinedTypeNameContext)_localctx).names.add(((UserDefinedTypeNameContext)_localctx).identifier);
			setState(669);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,75,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(665);
					match(T__26);
					setState(666);
					((UserDefinedTypeNameContext)_localctx).identifier = identifier();
					((UserDefinedTypeNameContext)_localctx).names.add(((UserDefinedTypeNameContext)_localctx).identifier);
					}
					} 
				}
				setState(671);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,75,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MappingContext extends ParserRuleContext {
		public ElementaryTypeNameContext key_type;
		public IdentifierContext key_label;
		public AnnotatedTypeNameContext value_type;
		public ElementaryTypeNameContext elementaryTypeName() {
			return getRuleContext(ElementaryTypeNameContext.class,0);
		}
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public MappingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapping; }
	}

	public final MappingContext mapping() throws RecognitionException {
		MappingContext _localctx = new MappingContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_mapping);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(672);
			match(T__45);
			setState(673);
			match(T__24);
			setState(674);
			((MappingContext)_localctx).key_type = elementaryTypeName();
			setState(677);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__46) {
				{
				setState(675);
				match(T__46);
				setState(676);
				((MappingContext)_localctx).key_label = identifier();
				}
			}

			setState(679);
			match(T__47);
			setState(680);
			((MappingContext)_localctx).value_type = annotatedTypeName();
			setState(681);
			match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataLocationContext extends ParserRuleContext {
		public TerminalNode MemoryKeyword() { return getToken(SolidityParser.MemoryKeyword, 0); }
		public TerminalNode StorageKeyword() { return getToken(SolidityParser.StorageKeyword, 0); }
		public TerminalNode CalldataKeyword() { return getToken(SolidityParser.CalldataKeyword, 0); }
		public DataLocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataLocation; }
	}

	public final DataLocationContext dataLocation() throws RecognitionException {
		DataLocationContext _localctx = new DataLocationContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_dataLocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(683);
			_la = _input.LA(1);
			if ( !(((((_la - 127)) & ~0x3f) == 0 && ((1L << (_la - 127)) & ((1L << (MemoryKeyword - 127)) | (1L << (StorageKeyword - 127)) | (1L << (CalldataKeyword - 127)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public StatementContext statement;
		public List<StatementContext> statements = new ArrayList<StatementContext>();
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(685);
			match(T__16);
			setState(689);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__16 - 7)) | (1L << (T__24 - 7)) | (1L << (T__29 - 7)) | (1L << (T__38 - 7)) | (1L << (T__42 - 7)) | (1L << (T__43 - 7)) | (1L << (T__45 - 7)) | (1L << (T__46 - 7)) | (1L << (T__48 - 7)) | (1L << (T__50 - 7)) | (1L << (T__51 - 7)) | (1L << (T__52 - 7)) | (1L << (T__54 - 7)) | (1L << (T__55 - 7)) | (1L << (T__56 - 7)) | (1L << (T__57 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (BreakKeyword - 93)) | (1L << (ContinueKeyword - 93)) | (1L << (Payable - 93)) | (1L << (FinalKeyword - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
				{
				{
				setState(686);
				((BlockContext)_localctx).statement = statement();
				((BlockContext)_localctx).statements.add(((BlockContext)_localctx).statement);
				}
				}
				setState(691);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(692);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SimpleStatementContext simpleStatement() {
			return getRuleContext(SimpleStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public TryStatementContext tryStatement() {
			return getRuleContext(TryStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public EmitStatementContext emitStatement() {
			return getRuleContext(EmitStatementContext.class,0);
		}
		public RevertStatementContext revertStatement() {
			return getRuleContext(RevertStatementContext.class,0);
		}
		public AssemblyStatementContext assemblyStatement() {
			return getRuleContext(AssemblyStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_statement);
		try {
			setState(707);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(694);
				block();
				}
				break;
			case T__6:
			case T__24:
			case T__29:
			case T__38:
			case T__43:
			case T__45:
			case T__46:
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case T__66:
			case T__67:
			case T__68:
			case T__69:
			case T__92:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
			case MeKeyword:
			case AllKeyword:
			case TeeKeyword:
			case BooleanLiteral:
			case DecimalNumber:
			case HexNumber:
			case Payable:
			case FinalKeyword:
			case Identifier:
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(695);
				simpleStatement();
				}
				break;
			case T__48:
				enterOuterAlt(_localctx, 3);
				{
				setState(696);
				ifStatement();
				}
				break;
			case T__42:
				enterOuterAlt(_localctx, 4);
				{
				setState(697);
				forStatement();
				}
				break;
			case T__50:
				enterOuterAlt(_localctx, 5);
				{
				setState(698);
				whileStatement();
				}
				break;
			case T__51:
				enterOuterAlt(_localctx, 6);
				{
				setState(699);
				doWhileStatement();
				}
				break;
			case ContinueKeyword:
				enterOuterAlt(_localctx, 7);
				{
				setState(700);
				continueStatement();
				}
				break;
			case BreakKeyword:
				enterOuterAlt(_localctx, 8);
				{
				setState(701);
				breakStatement();
				}
				break;
			case T__52:
				enterOuterAlt(_localctx, 9);
				{
				setState(702);
				tryStatement();
				}
				break;
			case T__54:
				enterOuterAlt(_localctx, 10);
				{
				setState(703);
				returnStatement();
				}
				break;
			case T__55:
				enterOuterAlt(_localctx, 11);
				{
				setState(704);
				emitStatement();
				}
				break;
			case T__56:
				enterOuterAlt(_localctx, 12);
				{
				setState(705);
				revertStatement();
				}
				break;
			case T__57:
				enterOuterAlt(_localctx, 13);
				{
				setState(706);
				assemblyStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(709);
			((ExpressionStatementContext)_localctx).expr = expression(0);
			setState(710);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public ExpressionContext condition;
		public StatementContext then_branch;
		public StatementContext else_branch;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(712);
			match(T__48);
			setState(713);
			match(T__24);
			setState(714);
			((IfStatementContext)_localctx).condition = expression(0);
			setState(715);
			match(T__25);
			setState(716);
			((IfStatementContext)_localctx).then_branch = statement();
			setState(719);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
			case 1:
				{
				setState(717);
				match(T__49);
				setState(718);
				((IfStatementContext)_localctx).else_branch = statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public ExpressionContext condition;
		public StatementContext body;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(721);
			match(T__50);
			setState(722);
			match(T__24);
			setState(723);
			((WhileStatementContext)_localctx).condition = expression(0);
			setState(724);
			match(T__25);
			setState(725);
			((WhileStatementContext)_localctx).body = statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleStatementContext extends ParserRuleContext {
		public VariableDeclarationStatementContext variableDeclarationStatement() {
			return getRuleContext(VariableDeclarationStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public TupleVariableDeclarationStatementContext tupleVariableDeclarationStatement() {
			return getRuleContext(TupleVariableDeclarationStatementContext.class,0);
		}
		public SimpleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStatement; }
	}

	public final SimpleStatementContext simpleStatement() throws RecognitionException {
		SimpleStatementContext _localctx = new SimpleStatementContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_simpleStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(730);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,80,_ctx) ) {
			case 1:
				{
				setState(727);
				variableDeclarationStatement();
				}
				break;
			case 2:
				{
				setState(728);
				expressionStatement();
				}
				break;
			case 3:
				{
				setState(729);
				tupleVariableDeclarationStatement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public SimpleStatementContext init;
		public ExpressionContext condition;
		public ExpressionContext update;
		public StatementContext body;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public SimpleStatementContext simpleStatement() {
			return getRuleContext(SimpleStatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(732);
			match(T__42);
			setState(733);
			match(T__24);
			setState(736);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__24:
			case T__29:
			case T__38:
			case T__43:
			case T__45:
			case T__46:
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case T__66:
			case T__67:
			case T__68:
			case T__69:
			case T__92:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
			case MeKeyword:
			case AllKeyword:
			case TeeKeyword:
			case BooleanLiteral:
			case DecimalNumber:
			case HexNumber:
			case Payable:
			case FinalKeyword:
			case Identifier:
			case StringLiteral:
				{
				setState(734);
				((ForStatementContext)_localctx).init = simpleStatement();
				}
				break;
			case T__4:
				{
				setState(735);
				match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(739);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
				{
				setState(738);
				((ForStatementContext)_localctx).condition = expression(0);
				}
			}

			setState(741);
			match(T__4);
			setState(743);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
				{
				setState(742);
				((ForStatementContext)_localctx).update = expression(0);
				}
			}

			setState(745);
			match(T__25);
			setState(746);
			((ForStatementContext)_localctx).body = statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoWhileStatementContext extends ParserRuleContext {
		public StatementContext body;
		public ExpressionContext condition;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(748);
			match(T__51);
			setState(749);
			((DoWhileStatementContext)_localctx).body = statement();
			setState(750);
			match(T__50);
			setState(751);
			match(T__24);
			setState(752);
			((DoWhileStatementContext)_localctx).condition = expression(0);
			setState(753);
			match(T__25);
			setState(754);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode ContinueKeyword() { return getToken(SolidityParser.ContinueKeyword, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(756);
			match(ContinueKeyword);
			setState(757);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BreakKeyword() { return getToken(SolidityParser.BreakKeyword, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(759);
			match(BreakKeyword);
			setState(760);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TryStatementContext extends ParserRuleContext {
		public ParameterListContext return_parameters;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<CatchClauseContext> catchClause() {
			return getRuleContexts(CatchClauseContext.class);
		}
		public CatchClauseContext catchClause(int i) {
			return getRuleContext(CatchClauseContext.class,i);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public TryStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryStatement; }
	}

	public final TryStatementContext tryStatement() throws RecognitionException {
		TryStatementContext _localctx = new TryStatementContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(762);
			match(T__52);
			setState(763);
			expression(0);
			setState(769);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__35) {
				{
				setState(764);
				match(T__35);
				setState(765);
				match(T__24);
				setState(766);
				((TryStatementContext)_localctx).return_parameters = parameterList();
				setState(767);
				match(T__25);
				}
			}

			setState(771);
			block();
			setState(773); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(772);
				catchClause();
				}
				}
				setState(775); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__53 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CatchClauseContext extends ParserRuleContext {
		public ParameterListContext arguments;
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public CatchClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchClause; }
	}

	public final CatchClauseContext catchClause() throws RecognitionException {
		CatchClauseContext _localctx = new CatchClauseContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_catchClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(777);
			match(T__53);
			setState(779);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(778);
				identifier();
				}
			}

			setState(782);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(781);
				((CatchClauseContext)_localctx).arguments = parameterList();
				}
			}

			setState(784);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(786);
			match(T__54);
			setState(788);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
				{
				setState(787);
				((ReturnStatementContext)_localctx).expr = expression(0);
				}
			}

			setState(790);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmitStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CallArgumentListContext callArgumentList() {
			return getRuleContext(CallArgumentListContext.class,0);
		}
		public EmitStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emitStatement; }
	}

	public final EmitStatementContext emitStatement() throws RecognitionException {
		EmitStatementContext _localctx = new EmitStatementContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_emitStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(792);
			match(T__55);
			setState(793);
			expression(0);
			setState(794);
			callArgumentList();
			setState(795);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RevertStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CallArgumentListContext callArgumentList() {
			return getRuleContext(CallArgumentListContext.class,0);
		}
		public RevertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_revertStatement; }
	}

	public final RevertStatementContext revertStatement() throws RecognitionException {
		RevertStatementContext _localctx = new RevertStatementContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_revertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(797);
			match(T__56);
			setState(798);
			expression(0);
			setState(799);
			callArgumentList();
			setState(800);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssemblyStatementContext extends ParserRuleContext {
		public List<YulStatementContext> yulStatement() {
			return getRuleContexts(YulStatementContext.class);
		}
		public YulStatementContext yulStatement(int i) {
			return getRuleContext(YulStatementContext.class,i);
		}
		public AssemblyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblyStatement; }
	}

	public final AssemblyStatementContext assemblyStatement() throws RecognitionException {
		AssemblyStatementContext _localctx = new AssemblyStatementContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_assemblyStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(802);
			match(T__57);
			setState(804);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__58) {
				{
				setState(803);
				match(T__58);
				}
			}

			setState(806);
			match(T__16);
			setState(810);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__29) | (1L << T__42) | (1L << T__48))) != 0) || ((((_la - 95)) & ~0x3f) == 0 && ((1L << (_la - 95)) & ((1L << (T__94 - 95)) | (1L << (T__95 - 95)) | (1L << (T__98 - 95)) | (1L << (BreakKeyword - 95)) | (1L << (ContinueKeyword - 95)) | (1L << (Identifier - 95)) | (1L << (YulEVMBuiltin - 95)))) != 0)) {
				{
				{
				setState(807);
				yulStatement();
				}
				}
				setState(812);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(813);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationStatementContext extends ParserRuleContext {
		public VariableDeclarationContext variable_declaration;
		public ExpressionContext expr;
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarationStatement; }
	}

	public final VariableDeclarationStatementContext variableDeclarationStatement() throws RecognitionException {
		VariableDeclarationStatementContext _localctx = new VariableDeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_variableDeclarationStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(815);
			((VariableDeclarationStatementContext)_localctx).variable_declaration = variableDeclaration();
			setState(818);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(816);
				match(T__11);
				setState(817);
				((VariableDeclarationStatementContext)_localctx).expr = expression(0);
				}
			}

			setState(820);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TupleVariableDeclarationStatementContext extends ParserRuleContext {
		public List<VariableDeclarationContext> variableDeclaration() {
			return getRuleContexts(VariableDeclarationContext.class);
		}
		public VariableDeclarationContext variableDeclaration(int i) {
			return getRuleContext(VariableDeclarationContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TupleVariableDeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleVariableDeclarationStatement; }
	}

	public final TupleVariableDeclarationStatementContext tupleVariableDeclarationStatement() throws RecognitionException {
		TupleVariableDeclarationStatementContext _localctx = new TupleVariableDeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_tupleVariableDeclarationStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(822);
			match(T__24);
			setState(826);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(823);
				match(T__17);
				}
				}
				setState(828);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(829);
			variableDeclaration();
			setState(836);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(830);
				match(T__17);
				setState(832);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 30)) & ~0x3f) == 0 && ((1L << (_la - 30)) & ((1L << (T__29 - 30)) | (1L << (T__45 - 30)) | (1L << (T__59 - 30)) | (1L << (T__60 - 30)) | (1L << (T__61 - 30)) | (1L << (T__62 - 30)) | (1L << (T__63 - 30)) | (1L << (T__64 - 30)) | (1L << (T__65 - 30)))) != 0) || ((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Uint - 102)) | (1L << (Int - 102)) | (1L << (Byte - 102)) | (1L << (Fixed - 102)) | (1L << (Ufixed - 102)) | (1L << (FinalKeyword - 102)) | (1L << (Identifier - 102)))) != 0)) {
					{
					setState(831);
					variableDeclaration();
					}
				}

				}
				}
				setState(838);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(839);
			match(T__25);
			setState(840);
			match(T__11);
			setState(841);
			expression(0);
			setState(842);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementaryTypeNameContext extends ParserRuleContext {
		public Token name;
		public TerminalNode Int() { return getToken(SolidityParser.Int, 0); }
		public TerminalNode Uint() { return getToken(SolidityParser.Uint, 0); }
		public TerminalNode Byte() { return getToken(SolidityParser.Byte, 0); }
		public TerminalNode Fixed() { return getToken(SolidityParser.Fixed, 0); }
		public TerminalNode Ufixed() { return getToken(SolidityParser.Ufixed, 0); }
		public ElementaryTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementaryTypeName; }
	}

	public final ElementaryTypeNameContext elementaryTypeName() throws RecognitionException {
		ElementaryTypeNameContext _localctx = new ElementaryTypeNameContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_elementaryTypeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(844);
			((ElementaryTypeNameContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !(((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (T__59 - 60)) | (1L << (T__60 - 60)) | (1L << (T__61 - 60)) | (1L << (T__62 - 60)) | (1L << (T__63 - 60)) | (1L << (T__64 - 60)) | (1L << (T__65 - 60)) | (1L << (Uint - 60)) | (1L << (Int - 60)) | (1L << (Byte - 60)) | (1L << (Fixed - 60)) | (1L << (Ufixed - 60)))) != 0)) ) {
				((ElementaryTypeNameContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AndExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AndExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PrimaryExpressionContext extends ExpressionContext {
		public ElementaryTypeNameContext elementaryTypeName() {
			return getRuleContext(ElementaryTypeNameContext.class,0);
		}
		public PrimaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitwiseOrExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BitwiseOrExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class AllExprContext extends ExpressionContext {
		public TerminalNode AllKeyword() { return getToken(SolidityParser.AllKeyword, 0); }
		public AllExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class IteExprContext extends ExpressionContext {
		public ExpressionContext cond;
		public ExpressionContext then_expr;
		public ExpressionContext else_expr;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IteExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class RangeIndexExprContext extends ExpressionContext {
		public ExpressionContext arr;
		public ExpressionContext start;
		public ExpressionContext end;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public RangeIndexExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PowExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public PowExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class StringLiteralExprContext extends ExpressionContext {
		public TerminalNode StringLiteral() { return getToken(SolidityParser.StringLiteral, 0); }
		public StringLiteralExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class InlineArrayExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public InlineArrayExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PlusMinusExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public PlusMinusExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class CompExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CompExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class IndexExprContext extends ExpressionContext {
		public ExpressionContext arr;
		public ExpressionContext index;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IndexExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PayableConversionContext extends ExpressionContext {
		public TerminalNode Payable() { return getToken(SolidityParser.Payable, 0); }
		public CallArgumentListContext callArgumentList() {
			return getRuleContext(CallArgumentListContext.class,0);
		}
		public PayableConversionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class SignExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SignExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NumberLiteralExprContext extends ExpressionContext {
		public TerminalNode DecimalNumber() { return getToken(SolidityParser.DecimalNumber, 0); }
		public TerminalNode HexNumber() { return getToken(SolidityParser.HexNumber, 0); }
		public TerminalNode NumberUnit() { return getToken(SolidityParser.NumberUnit, 0); }
		public NumberLiteralExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitwiseNotExprContext extends ExpressionContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BitwiseNotExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class IdentifierExprContext extends ExpressionContext {
		public IdentifierContext idf;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public IdentifierExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BooleanLiteralExprContext extends ExpressionContext {
		public TerminalNode BooleanLiteral() { return getToken(SolidityParser.BooleanLiteral, 0); }
		public BooleanLiteralExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class MeExprContext extends ExpressionContext {
		public TerminalNode MeKeyword() { return getToken(SolidityParser.MeKeyword, 0); }
		public MeExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NotExprContext extends ExpressionContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class TeeExprContext extends ExpressionContext {
		public TerminalNode TeeKeyword() { return getToken(SolidityParser.TeeKeyword, 0); }
		public TeeExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitShiftExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BitShiftExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitwiseAndExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BitwiseAndExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class MultDivModExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MultDivModExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class AssignmentExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AssignmentExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class TupleExprContext extends ExpressionContext {
		public TupleExpressionContext expr;
		public TupleExpressionContext tupleExpression() {
			return getRuleContext(TupleExpressionContext.class,0);
		}
		public TupleExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class OrExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OrExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class FunctionCallExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CallArgumentListContext callArgumentList() {
			return getRuleContext(CallArgumentListContext.class,0);
		}
		public FunctionCallExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class FunctionCallOptionsContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<NamedArgumentContext> namedArgument() {
			return getRuleContexts(NamedArgumentContext.class);
		}
		public NamedArgumentContext namedArgument(int i) {
			return getRuleContext(NamedArgumentContext.class,i);
		}
		public FunctionCallOptionsContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class EqExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public EqExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NewExprContext extends ExpressionContext {
		public TypeNameContext target_type;
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public NewExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PostCrementExprContext extends ExpressionContext {
		public ExpressionContext expr;
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PostCrementExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitwiseXorExprContext extends ExpressionContext {
		public ExpressionContext lhs;
		public Token op;
		public ExpressionContext rhs;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BitwiseXorExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class MemberAccessExprContext extends ExpressionContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public MemberAccessExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PreCrementExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PreCrementExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class MetaTypeContext extends ExpressionContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public MetaTypeContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 136;
		enterRecursionRule(_localctx, 136, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(887);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MeKeyword:
				{
				_localctx = new MeExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(847);
				match(MeKeyword);
				}
				break;
			case AllKeyword:
				{
				_localctx = new AllExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(848);
				match(AllKeyword);
				}
				break;
			case TeeKeyword:
				{
				_localctx = new TeeExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(849);
				match(TeeKeyword);
				}
				break;
			case Payable:
				{
				_localctx = new PayableConversionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(850);
				match(Payable);
				setState(851);
				callArgumentList();
				}
				break;
			case T__38:
				{
				_localctx = new MetaTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(852);
				match(T__38);
				setState(853);
				match(T__24);
				setState(854);
				typeName(0);
				setState(855);
				match(T__25);
				}
				break;
			case T__66:
			case T__67:
				{
				_localctx = new PreCrementExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(857);
				((PreCrementExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__66 || _la==T__67) ) {
					((PreCrementExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(858);
				((PreCrementExprContext)_localctx).expr = expression(25);
				}
				break;
			case T__68:
			case T__69:
				{
				_localctx = new SignExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(859);
				((SignExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__68 || _la==T__69) ) {
					((SignExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(860);
				((SignExprContext)_localctx).expr = expression(24);
				}
				break;
			case T__46:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(861);
				match(T__46);
				setState(862);
				((NotExprContext)_localctx).expr = expression(23);
				}
				break;
			case T__6:
				{
				_localctx = new BitwiseNotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(863);
				match(T__6);
				setState(864);
				((BitwiseNotExprContext)_localctx).expr = expression(22);
				}
				break;
			case T__92:
				{
				_localctx = new NewExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(865);
				match(T__92);
				setState(866);
				((NewExprContext)_localctx).target_type = typeName(0);
				}
				break;
			case T__24:
				{
				_localctx = new TupleExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(867);
				((TupleExprContext)_localctx).expr = tupleExpression();
				}
				break;
			case T__43:
				{
				_localctx = new InlineArrayExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(868);
				match(T__43);
				{
				setState(869);
				expression(0);
				setState(874);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(870);
					match(T__17);
					setState(871);
					expression(0);
					}
					}
					setState(876);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				setState(877);
				match(T__44);
				}
				break;
			case Identifier:
				{
				_localctx = new IdentifierExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(879);
				((IdentifierExprContext)_localctx).idf = identifier();
				}
				break;
			case BooleanLiteral:
				{
				_localctx = new BooleanLiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(880);
				match(BooleanLiteral);
				}
				break;
			case DecimalNumber:
			case HexNumber:
				{
				_localctx = new NumberLiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(881);
				_la = _input.LA(1);
				if ( !(_la==DecimalNumber || _la==HexNumber) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(883);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,96,_ctx) ) {
				case 1:
					{
					setState(882);
					match(NumberUnit);
					}
					break;
				}
				}
				break;
			case StringLiteral:
				{
				_localctx = new StringLiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(885);
				match(StringLiteral);
				}
				break;
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
				{
				_localctx = new PrimaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(886);
				elementaryTypeName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(972);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(970);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,104,_ctx) ) {
					case 1:
						{
						_localctx = new PowExprContext(new ExpressionContext(_parentctx, _parentState));
						((PowExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(889);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(890);
						((PowExprContext)_localctx).op = match(T__70);
						setState(891);
						((PowExprContext)_localctx).rhs = expression(21);
						}
						break;
					case 2:
						{
						_localctx = new MultDivModExprContext(new ExpressionContext(_parentctx, _parentState));
						((MultDivModExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(892);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(893);
						((MultDivModExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 16)) & ~0x3f) == 0 && ((1L << (_la - 16)) & ((1L << (T__15 - 16)) | (1L << (T__71 - 16)) | (1L << (T__72 - 16)))) != 0)) ) {
							((MultDivModExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(894);
						((MultDivModExprContext)_localctx).rhs = expression(21);
						}
						break;
					case 3:
						{
						_localctx = new PlusMinusExprContext(new ExpressionContext(_parentctx, _parentState));
						((PlusMinusExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(895);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(896);
						((PlusMinusExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__68 || _la==T__69) ) {
							((PlusMinusExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(897);
						((PlusMinusExprContext)_localctx).rhs = expression(20);
						}
						break;
					case 4:
						{
						_localctx = new BitShiftExprContext(new ExpressionContext(_parentctx, _parentState));
						((BitShiftExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(898);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(899);
						((BitShiftExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__73 || _la==T__74) ) {
							((BitShiftExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(900);
						((BitShiftExprContext)_localctx).rhs = expression(19);
						}
						break;
					case 5:
						{
						_localctx = new BitwiseAndExprContext(new ExpressionContext(_parentctx, _parentState));
						((BitwiseAndExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(901);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(902);
						((BitwiseAndExprContext)_localctx).op = match(T__75);
						setState(903);
						((BitwiseAndExprContext)_localctx).rhs = expression(18);
						}
						break;
					case 6:
						{
						_localctx = new BitwiseXorExprContext(new ExpressionContext(_parentctx, _parentState));
						((BitwiseXorExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(904);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(905);
						((BitwiseXorExprContext)_localctx).op = match(T__5);
						setState(906);
						((BitwiseXorExprContext)_localctx).rhs = expression(17);
						}
						break;
					case 7:
						{
						_localctx = new BitwiseOrExprContext(new ExpressionContext(_parentctx, _parentState));
						((BitwiseOrExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(907);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(908);
						((BitwiseOrExprContext)_localctx).op = match(T__76);
						setState(909);
						((BitwiseOrExprContext)_localctx).rhs = expression(16);
						}
						break;
					case 8:
						{
						_localctx = new CompExprContext(new ExpressionContext(_parentctx, _parentState));
						((CompExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(910);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(911);
						((CompExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10))) != 0)) ) {
							((CompExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(912);
						((CompExprContext)_localctx).rhs = expression(15);
						}
						break;
					case 9:
						{
						_localctx = new EqExprContext(new ExpressionContext(_parentctx, _parentState));
						((EqExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(913);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(914);
						((EqExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__77 || _la==T__78) ) {
							((EqExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(915);
						((EqExprContext)_localctx).rhs = expression(14);
						}
						break;
					case 10:
						{
						_localctx = new AndExprContext(new ExpressionContext(_parentctx, _parentState));
						((AndExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(916);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(917);
						((AndExprContext)_localctx).op = match(T__79);
						setState(918);
						((AndExprContext)_localctx).rhs = expression(13);
						}
						break;
					case 11:
						{
						_localctx = new OrExprContext(new ExpressionContext(_parentctx, _parentState));
						((OrExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(919);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(920);
						((OrExprContext)_localctx).op = match(T__80);
						setState(921);
						((OrExprContext)_localctx).rhs = expression(12);
						}
						break;
					case 12:
						{
						_localctx = new IteExprContext(new ExpressionContext(_parentctx, _parentState));
						((IteExprContext)_localctx).cond = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(922);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(923);
						match(T__81);
						setState(924);
						((IteExprContext)_localctx).then_expr = expression(0);
						setState(925);
						match(T__23);
						setState(926);
						((IteExprContext)_localctx).else_expr = expression(10);
						}
						break;
					case 13:
						{
						_localctx = new AssignmentExprContext(new ExpressionContext(_parentctx, _parentState));
						((AssignmentExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(928);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(929);
						((AssignmentExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__11 || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (T__82 - 83)) | (1L << (T__83 - 83)) | (1L << (T__84 - 83)) | (1L << (T__85 - 83)) | (1L << (T__86 - 83)) | (1L << (T__87 - 83)) | (1L << (T__88 - 83)) | (1L << (T__89 - 83)) | (1L << (T__90 - 83)) | (1L << (T__91 - 83)))) != 0)) ) {
							((AssignmentExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(930);
						((AssignmentExprContext)_localctx).rhs = expression(10);
						}
						break;
					case 14:
						{
						_localctx = new IndexExprContext(new ExpressionContext(_parentctx, _parentState));
						((IndexExprContext)_localctx).arr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(931);
						if (!(precpred(_ctx, 33))) throw new FailedPredicateException(this, "precpred(_ctx, 33)");
						setState(932);
						match(T__43);
						setState(934);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
							{
							setState(933);
							((IndexExprContext)_localctx).index = expression(0);
							}
						}

						setState(936);
						match(T__44);
						}
						break;
					case 15:
						{
						_localctx = new RangeIndexExprContext(new ExpressionContext(_parentctx, _parentState));
						((RangeIndexExprContext)_localctx).arr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(937);
						if (!(precpred(_ctx, 32))) throw new FailedPredicateException(this, "precpred(_ctx, 32)");
						setState(938);
						match(T__43);
						setState(940);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
							{
							setState(939);
							((RangeIndexExprContext)_localctx).start = expression(0);
							}
						}

						setState(942);
						match(T__23);
						setState(944);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
							{
							setState(943);
							((RangeIndexExprContext)_localctx).end = expression(0);
							}
						}

						setState(946);
						match(T__44);
						}
						break;
					case 16:
						{
						_localctx = new MemberAccessExprContext(new ExpressionContext(_parentctx, _parentState));
						((MemberAccessExprContext)_localctx).expr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(947);
						if (!(precpred(_ctx, 31))) throw new FailedPredicateException(this, "precpred(_ctx, 31)");
						setState(948);
						match(T__26);
						setState(951);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
						case Identifier:
							{
							setState(949);
							identifier();
							}
							break;
						case T__59:
							{
							setState(950);
							match(T__59);
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						}
						break;
					case 17:
						{
						_localctx = new FunctionCallOptionsContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(953);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(954);
						match(T__16);
						setState(963);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==Identifier) {
							{
							setState(955);
							namedArgument();
							setState(960);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==T__17) {
								{
								{
								setState(956);
								match(T__17);
								setState(957);
								namedArgument();
								}
								}
								setState(962);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(965);
						match(T__18);
						}
						break;
					case 18:
						{
						_localctx = new FunctionCallExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(966);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(967);
						callArgumentList();
						}
						break;
					case 19:
						{
						_localctx = new PostCrementExprContext(new ExpressionContext(_parentctx, _parentState));
						((PostCrementExprContext)_localctx).expr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(968);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(969);
						((PostCrementExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__66 || _la==T__67) ) {
							((PostCrementExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(974);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TupleExpressionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TupleExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleExpression; }
	}

	public final TupleExpressionContext tupleExpression() throws RecognitionException {
		TupleExpressionContext _localctx = new TupleExpressionContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_tupleExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(975);
			match(T__24);
			{
			setState(977);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
				{
				setState(976);
				expression(0);
				}
			}

			setState(985);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(979);
				match(T__17);
				setState(981);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__6 - 7)) | (1L << (T__24 - 7)) | (1L << (T__38 - 7)) | (1L << (T__43 - 7)) | (1L << (T__46 - 7)) | (1L << (T__59 - 7)) | (1L << (T__60 - 7)) | (1L << (T__61 - 7)) | (1L << (T__62 - 7)) | (1L << (T__63 - 7)) | (1L << (T__64 - 7)) | (1L << (T__65 - 7)) | (1L << (T__66 - 7)) | (1L << (T__67 - 7)) | (1L << (T__68 - 7)) | (1L << (T__69 - 7)))) != 0) || ((((_la - 93)) & ~0x3f) == 0 && ((1L << (_la - 93)) & ((1L << (T__92 - 93)) | (1L << (Uint - 93)) | (1L << (Int - 93)) | (1L << (Byte - 93)) | (1L << (Fixed - 93)) | (1L << (Ufixed - 93)) | (1L << (MeKeyword - 93)) | (1L << (AllKeyword - 93)) | (1L << (TeeKeyword - 93)) | (1L << (BooleanLiteral - 93)) | (1L << (DecimalNumber - 93)) | (1L << (HexNumber - 93)) | (1L << (Payable - 93)) | (1L << (Identifier - 93)) | (1L << (StringLiteral - 93)))) != 0)) {
					{
					setState(980);
					expression(0);
					}
				}

				}
				}
				setState(987);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(988);
			match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementaryTypeNameExpressionContext extends ParserRuleContext {
		public ElementaryTypeNameContext elementaryTypeName() {
			return getRuleContext(ElementaryTypeNameContext.class,0);
		}
		public ElementaryTypeNameExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementaryTypeNameExpression; }
	}

	public final ElementaryTypeNameExpressionContext elementaryTypeNameExpression() throws RecognitionException {
		ElementaryTypeNameExpressionContext _localctx = new ElementaryTypeNameExpressionContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_elementaryTypeNameExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(990);
			elementaryTypeName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotatedTypeNameContext extends ParserRuleContext {
		public TypeNameContext type_name;
		public ExpressionContext privacy_annotation;
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AnnotatedTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotatedTypeName; }
	}

	public final AnnotatedTypeNameContext annotatedTypeName() throws RecognitionException {
		AnnotatedTypeNameContext _localctx = new AnnotatedTypeNameContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_annotatedTypeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(992);
			((AnnotatedTypeNameContext)_localctx).type_name = typeName(0);
			setState(995);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__93) {
				{
				setState(993);
				match(T__93);
				setState(994);
				((AnnotatedTypeNameContext)_localctx).privacy_annotation = expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulStatementContext extends ParserRuleContext {
		public YulBlockContext yulBlock() {
			return getRuleContext(YulBlockContext.class,0);
		}
		public YulVariableDeclarationContext yulVariableDeclaration() {
			return getRuleContext(YulVariableDeclarationContext.class,0);
		}
		public YulAssignmentContext yulAssignment() {
			return getRuleContext(YulAssignmentContext.class,0);
		}
		public YulFunctionCallContext yulFunctionCall() {
			return getRuleContext(YulFunctionCallContext.class,0);
		}
		public YulIfStatementContext yulIfStatement() {
			return getRuleContext(YulIfStatementContext.class,0);
		}
		public YulForStatementContext yulForStatement() {
			return getRuleContext(YulForStatementContext.class,0);
		}
		public YulSwitchStatementContext yulSwitchStatement() {
			return getRuleContext(YulSwitchStatementContext.class,0);
		}
		public TerminalNode BreakKeyword() { return getToken(SolidityParser.BreakKeyword, 0); }
		public TerminalNode ContinueKeyword() { return getToken(SolidityParser.ContinueKeyword, 0); }
		public YulFunctionDefinitionContext yulFunctionDefinition() {
			return getRuleContext(YulFunctionDefinitionContext.class,0);
		}
		public YulStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulStatement; }
	}

	public final YulStatementContext yulStatement() throws RecognitionException {
		YulStatementContext _localctx = new YulStatementContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_yulStatement);
		try {
			setState(1008);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(997);
				yulBlock();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(998);
				yulVariableDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(999);
				yulAssignment();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1000);
				yulFunctionCall();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1001);
				yulIfStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1002);
				yulForStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1003);
				yulSwitchStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1004);
				match(T__94);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1005);
				match(BreakKeyword);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1006);
				match(ContinueKeyword);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1007);
				yulFunctionDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulBlockContext extends ParserRuleContext {
		public List<YulStatementContext> yulStatement() {
			return getRuleContexts(YulStatementContext.class);
		}
		public YulStatementContext yulStatement(int i) {
			return getRuleContext(YulStatementContext.class,i);
		}
		public YulBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulBlock; }
	}

	public final YulBlockContext yulBlock() throws RecognitionException {
		YulBlockContext _localctx = new YulBlockContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_yulBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1010);
			match(T__16);
			setState(1014);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__29) | (1L << T__42) | (1L << T__48))) != 0) || ((((_la - 95)) & ~0x3f) == 0 && ((1L << (_la - 95)) & ((1L << (T__94 - 95)) | (1L << (T__95 - 95)) | (1L << (T__98 - 95)) | (1L << (BreakKeyword - 95)) | (1L << (ContinueKeyword - 95)) | (1L << (Identifier - 95)) | (1L << (YulEVMBuiltin - 95)))) != 0)) {
				{
				{
				setState(1011);
				yulStatement();
				}
				}
				setState(1016);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1017);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulVariableDeclarationContext extends ParserRuleContext {
		public Token Identifier;
		public List<Token> variables = new ArrayList<Token>();
		public List<TerminalNode> Identifier() { return getTokens(SolidityParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(SolidityParser.Identifier, i);
		}
		public YulExpressionContext yulExpression() {
			return getRuleContext(YulExpressionContext.class,0);
		}
		public YulFunctionCallContext yulFunctionCall() {
			return getRuleContext(YulFunctionCallContext.class,0);
		}
		public YulVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulVariableDeclaration; }
	}

	public final YulVariableDeclarationContext yulVariableDeclaration() throws RecognitionException {
		YulVariableDeclarationContext _localctx = new YulVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_yulVariableDeclaration);
		int _la;
		try {
			setState(1038);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,115,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(1019);
				match(T__95);
				setState(1020);
				((YulVariableDeclarationContext)_localctx).Identifier = match(Identifier);
				((YulVariableDeclarationContext)_localctx).variables.add(((YulVariableDeclarationContext)_localctx).Identifier);
				setState(1023);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__96) {
					{
					setState(1021);
					match(T__96);
					setState(1022);
					yulExpression();
					}
				}

				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(1025);
				match(T__95);
				setState(1026);
				((YulVariableDeclarationContext)_localctx).Identifier = match(Identifier);
				((YulVariableDeclarationContext)_localctx).variables.add(((YulVariableDeclarationContext)_localctx).Identifier);
				setState(1031);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(1027);
					match(T__17);
					setState(1028);
					((YulVariableDeclarationContext)_localctx).Identifier = match(Identifier);
					((YulVariableDeclarationContext)_localctx).variables.add(((YulVariableDeclarationContext)_localctx).Identifier);
					}
					}
					setState(1033);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1036);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__96) {
					{
					setState(1034);
					match(T__96);
					setState(1035);
					yulFunctionCall();
					}
				}

				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulAssignmentContext extends ParserRuleContext {
		public List<YulPathContext> yulPath() {
			return getRuleContexts(YulPathContext.class);
		}
		public YulPathContext yulPath(int i) {
			return getRuleContext(YulPathContext.class,i);
		}
		public YulExpressionContext yulExpression() {
			return getRuleContext(YulExpressionContext.class,0);
		}
		public YulFunctionCallContext yulFunctionCall() {
			return getRuleContext(YulFunctionCallContext.class,0);
		}
		public YulAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulAssignment; }
	}

	public final YulAssignmentContext yulAssignment() throws RecognitionException {
		YulAssignmentContext _localctx = new YulAssignmentContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_yulAssignment);
		int _la;
		try {
			setState(1054);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,117,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1040);
				yulPath();
				setState(1041);
				match(T__96);
				setState(1042);
				yulExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(1044);
				yulPath();
				setState(1047); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1045);
					match(T__17);
					setState(1046);
					yulPath();
					}
					}
					setState(1049); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__17 );
				}
				setState(1051);
				match(T__96);
				setState(1052);
				yulFunctionCall();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulIfStatementContext extends ParserRuleContext {
		public YulExpressionContext cond;
		public YulBlockContext body;
		public YulExpressionContext yulExpression() {
			return getRuleContext(YulExpressionContext.class,0);
		}
		public YulBlockContext yulBlock() {
			return getRuleContext(YulBlockContext.class,0);
		}
		public YulIfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulIfStatement; }
	}

	public final YulIfStatementContext yulIfStatement() throws RecognitionException {
		YulIfStatementContext _localctx = new YulIfStatementContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_yulIfStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1056);
			match(T__48);
			setState(1057);
			((YulIfStatementContext)_localctx).cond = yulExpression();
			setState(1058);
			((YulIfStatementContext)_localctx).body = yulBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulForStatementContext extends ParserRuleContext {
		public YulBlockContext init;
		public YulExpressionContext cond;
		public YulBlockContext post;
		public YulBlockContext body;
		public List<YulBlockContext> yulBlock() {
			return getRuleContexts(YulBlockContext.class);
		}
		public YulBlockContext yulBlock(int i) {
			return getRuleContext(YulBlockContext.class,i);
		}
		public YulExpressionContext yulExpression() {
			return getRuleContext(YulExpressionContext.class,0);
		}
		public YulForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulForStatement; }
	}

	public final YulForStatementContext yulForStatement() throws RecognitionException {
		YulForStatementContext _localctx = new YulForStatementContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_yulForStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1060);
			match(T__42);
			setState(1061);
			((YulForStatementContext)_localctx).init = yulBlock();
			setState(1062);
			((YulForStatementContext)_localctx).cond = yulExpression();
			setState(1063);
			((YulForStatementContext)_localctx).post = yulBlock();
			setState(1064);
			((YulForStatementContext)_localctx).body = yulBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulSwitchCaseContext extends ParserRuleContext {
		public YulLiteralContext yulLiteral() {
			return getRuleContext(YulLiteralContext.class,0);
		}
		public YulBlockContext yulBlock() {
			return getRuleContext(YulBlockContext.class,0);
		}
		public YulSwitchCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulSwitchCase; }
	}

	public final YulSwitchCaseContext yulSwitchCase() throws RecognitionException {
		YulSwitchCaseContext _localctx = new YulSwitchCaseContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_yulSwitchCase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1066);
			match(T__97);
			setState(1067);
			yulLiteral();
			setState(1068);
			yulBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulSwitchStatementContext extends ParserRuleContext {
		public YulExpressionContext yulExpression() {
			return getRuleContext(YulExpressionContext.class,0);
		}
		public YulBlockContext yulBlock() {
			return getRuleContext(YulBlockContext.class,0);
		}
		public List<YulSwitchCaseContext> yulSwitchCase() {
			return getRuleContexts(YulSwitchCaseContext.class);
		}
		public YulSwitchCaseContext yulSwitchCase(int i) {
			return getRuleContext(YulSwitchCaseContext.class,i);
		}
		public YulSwitchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulSwitchStatement; }
	}

	public final YulSwitchStatementContext yulSwitchStatement() throws RecognitionException {
		YulSwitchStatementContext _localctx = new YulSwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_yulSwitchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1070);
			match(T__98);
			setState(1071);
			yulExpression();
			setState(1083);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__97:
				{
				{
				setState(1073); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1072);
					yulSwitchCase();
					}
					}
					setState(1075); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__97 );
				setState(1079);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__99) {
					{
					setState(1077);
					match(T__99);
					setState(1078);
					yulBlock();
					}
				}

				}
				}
				break;
			case T__99:
				{
				{
				setState(1081);
				match(T__99);
				setState(1082);
				yulBlock();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulFunctionDefinitionContext extends ParserRuleContext {
		public Token Identifier;
		public List<Token> arguments = new ArrayList<Token>();
		public YulBlockContext body;
		public List<TerminalNode> Identifier() { return getTokens(SolidityParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(SolidityParser.Identifier, i);
		}
		public YulBlockContext yulBlock() {
			return getRuleContext(YulBlockContext.class,0);
		}
		public YulFunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulFunctionDefinition; }
	}

	public final YulFunctionDefinitionContext yulFunctionDefinition() throws RecognitionException {
		YulFunctionDefinitionContext _localctx = new YulFunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_yulFunctionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1085);
			match(T__29);
			setState(1086);
			match(Identifier);
			setState(1087);
			match(T__24);
			setState(1096);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1088);
				((YulFunctionDefinitionContext)_localctx).Identifier = match(Identifier);
				((YulFunctionDefinitionContext)_localctx).arguments.add(((YulFunctionDefinitionContext)_localctx).Identifier);
				setState(1093);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(1089);
					match(T__17);
					setState(1090);
					((YulFunctionDefinitionContext)_localctx).Identifier = match(Identifier);
					((YulFunctionDefinitionContext)_localctx).arguments.add(((YulFunctionDefinitionContext)_localctx).Identifier);
					}
					}
					setState(1095);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1098);
			match(T__25);
			setState(1108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__100) {
				{
				setState(1099);
				match(T__100);
				setState(1100);
				match(Identifier);
				setState(1105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(1101);
					match(T__17);
					setState(1102);
					match(Identifier);
					}
					}
					setState(1107);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1110);
			((YulFunctionDefinitionContext)_localctx).body = yulBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulPathContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(SolidityParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(SolidityParser.Identifier, i);
		}
		public YulPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulPath; }
	}

	public final YulPathContext yulPath() throws RecognitionException {
		YulPathContext _localctx = new YulPathContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_yulPath);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1112);
			match(Identifier);
			setState(1117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__26) {
				{
				{
				setState(1113);
				match(T__26);
				setState(1114);
				match(Identifier);
				}
				}
				setState(1119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulFunctionCallContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(SolidityParser.Identifier, 0); }
		public TerminalNode YulEVMBuiltin() { return getToken(SolidityParser.YulEVMBuiltin, 0); }
		public List<YulExpressionContext> yulExpression() {
			return getRuleContexts(YulExpressionContext.class);
		}
		public YulExpressionContext yulExpression(int i) {
			return getRuleContext(YulExpressionContext.class,i);
		}
		public YulFunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulFunctionCall; }
	}

	public final YulFunctionCallContext yulFunctionCall() throws RecognitionException {
		YulFunctionCallContext _localctx = new YulFunctionCallContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_yulFunctionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1120);
			_la = _input.LA(1);
			if ( !(_la==Identifier || _la==YulEVMBuiltin) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(1121);
			match(T__24);
			setState(1130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 111)) & ~0x3f) == 0 && ((1L << (_la - 111)) & ((1L << (BooleanLiteral - 111)) | (1L << (DecimalNumber - 111)) | (1L << (HexNumber - 111)) | (1L << (Identifier - 111)) | (1L << (StringLiteral - 111)) | (1L << (HexString - 111)) | (1L << (YulEVMBuiltin - 111)))) != 0)) {
				{
				setState(1122);
				yulExpression();
				setState(1127);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(1123);
					match(T__17);
					setState(1124);
					yulExpression();
					}
					}
					setState(1129);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1132);
			match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulLiteralContext extends ParserRuleContext {
		public TerminalNode DecimalNumber() { return getToken(SolidityParser.DecimalNumber, 0); }
		public TerminalNode StringLiteral() { return getToken(SolidityParser.StringLiteral, 0); }
		public TerminalNode HexNumber() { return getToken(SolidityParser.HexNumber, 0); }
		public TerminalNode BooleanLiteral() { return getToken(SolidityParser.BooleanLiteral, 0); }
		public TerminalNode HexString() { return getToken(SolidityParser.HexString, 0); }
		public YulLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulLiteral; }
	}

	public final YulLiteralContext yulLiteral() throws RecognitionException {
		YulLiteralContext _localctx = new YulLiteralContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_yulLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1134);
			_la = _input.LA(1);
			if ( !(((((_la - 111)) & ~0x3f) == 0 && ((1L << (_la - 111)) & ((1L << (BooleanLiteral - 111)) | (1L << (DecimalNumber - 111)) | (1L << (HexNumber - 111)) | (1L << (StringLiteral - 111)) | (1L << (HexString - 111)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YulExpressionContext extends ParserRuleContext {
		public YulPathContext yulPath() {
			return getRuleContext(YulPathContext.class,0);
		}
		public YulFunctionCallContext yulFunctionCall() {
			return getRuleContext(YulFunctionCallContext.class,0);
		}
		public YulLiteralContext yulLiteral() {
			return getRuleContext(YulLiteralContext.class,0);
		}
		public YulExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yulExpression; }
	}

	public final YulExpressionContext yulExpression() throws RecognitionException {
		YulExpressionContext _localctx = new YulExpressionContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_yulExpression);
		try {
			setState(1139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,128,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1136);
				yulPath();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1137);
				yulFunctionCall();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1138);
				yulLiteral();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public Token name;
		public TerminalNode Identifier() { return getToken(SolidityParser.Identifier, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(1141);
			((IdentifierContext)_localctx).name = match(Identifier);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 44:
			return typeName_sempred((TypeNameContext)_localctx, predIndex);
		case 68:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean typeName_sempred(TypeNameContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 21);
		case 2:
			return precpred(_ctx, 20);
		case 3:
			return precpred(_ctx, 19);
		case 4:
			return precpred(_ctx, 18);
		case 5:
			return precpred(_ctx, 17);
		case 6:
			return precpred(_ctx, 16);
		case 7:
			return precpred(_ctx, 15);
		case 8:
			return precpred(_ctx, 14);
		case 9:
			return precpred(_ctx, 13);
		case 10:
			return precpred(_ctx, 12);
		case 11:
			return precpred(_ctx, 11);
		case 12:
			return precpred(_ctx, 10);
		case 13:
			return precpred(_ctx, 9);
		case 14:
			return precpred(_ctx, 33);
		case 15:
			return precpred(_ctx, 32);
		case 16:
			return precpred(_ctx, 31);
		case 17:
			return precpred(_ctx, 30);
		case 18:
			return precpred(_ctx, 29);
		case 19:
			return precpred(_ctx, 26);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u008c\u047a\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2\u00ba"+
		"\n\2\f\2\16\2\u00bd\13\2\3\2\3\2\3\2\3\2\5\2\u00c3\n\2\3\3\3\3\3\3\3\3"+
		"\3\3\5\3\u00ca\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\5\5\u00d5\n\5\3"+
		"\6\3\6\3\7\5\7\u00da\n\7\3\7\3\7\3\b\3\b\3\b\3\b\5\b\u00e2\n\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00ee\n\b\3\b\3\b\3\t\3\t\3\t\5\t"+
		"\u00f5\n\t\3\n\3\n\3\13\3\13\3\13\3\13\7\13\u00fd\n\13\f\13\16\13\u0100"+
		"\13\13\3\13\3\13\3\f\3\f\3\f\5\f\u0107\n\f\3\f\3\f\7\f\u010b\n\f\f\f\16"+
		"\f\u010e\13\f\3\f\3\f\3\r\3\r\3\r\5\r\u0115\n\r\3\r\3\r\7\r\u0119\n\r"+
		"\f\r\16\r\u011c\13\r\3\r\3\r\3\16\3\16\3\16\3\16\7\16\u0124\n\16\f\16"+
		"\16\16\u0127\13\16\3\16\3\16\3\17\3\17\3\17\3\17\7\17\u012f\n\17\f\17"+
		"\16\17\u0132\13\17\3\20\3\20\5\20\u0136\n\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u0144\n\21\3\22\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\7\23\u014e\n\23\f\23\16\23\u0151\13\23\5\23\u0153"+
		"\n\23\3\23\3\23\3\23\3\23\7\23\u0159\n\23\f\23\16\23\u015c\13\23\5\23"+
		"\u015e\n\23\3\23\5\23\u0161\n\23\3\23\3\23\3\24\3\24\3\24\7\24\u0168\n"+
		"\24\f\24\16\24\u016b\13\24\3\25\7\25\u016e\n\25\f\25\16\25\u0171\13\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\7\25\u017a\n\25\f\25\16\25\u017d\13"+
		"\25\3\25\3\25\3\25\5\25\u0182\n\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\7\26\u018c\n\26\f\26\16\26\u018f\13\26\3\26\3\26\3\27\3\27\3\27"+
		"\3\27\5\27\u0197\n\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u019f\n\27\f"+
		"\27\16\27\u01a2\13\27\3\27\5\27\u01a5\n\27\3\27\3\27\5\27\u01a9\n\27\3"+
		"\30\3\30\3\30\3\30\3\30\7\30\u01b0\n\30\f\30\16\30\u01b3\13\30\3\30\3"+
		"\30\5\30\u01b7\n\30\3\31\3\31\5\31\u01bb\n\31\3\31\3\31\3\31\3\31\3\31"+
		"\7\31\u01c2\n\31\f\31\16\31\u01c5\13\31\3\31\5\31\u01c8\n\31\3\31\3\31"+
		"\5\31\u01cc\n\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\7\32\u01d6\n"+
		"\32\f\32\16\32\u01d9\13\32\3\32\3\32\5\32\u01dd\n\32\3\33\3\33\3\33\3"+
		"\33\6\33\u01e3\n\33\r\33\16\33\u01e4\3\33\3\33\3\34\3\34\3\34\3\34\3\35"+
		"\3\35\3\35\3\36\3\36\5\36\u01f2\n\36\3\37\3\37\3 \3 \3!\3!\3!\3!\3!\7"+
		"!\u01fd\n!\f!\16!\u0200\13!\3!\3!\5!\u0204\n!\3\"\3\"\3\"\3\"\7\"\u020a"+
		"\n\"\f\"\16\"\u020d\13\"\5\"\u020f\n\"\3\"\3\"\3#\5#\u0214\n#\3#\3#\5"+
		"#\u0218\n#\3#\5#\u021b\n#\3$\3$\3%\3%\3%\3%\5%\u0223\n%\3%\3%\7%\u0227"+
		"\n%\f%\16%\u022a\13%\3%\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3(\3(\5(\u023d\n(\3(\5(\u0240\n(\3)\3)\3)\3)\3)\3)\7)\u0248\n)\f)\16"+
		")\u024b\13)\5)\u024d\n)\3)\3)\5)\u0251\n)\3)\3)\3*\3*\5*\u0257\n*\3+\3"+
		"+\3+\3+\3+\3+\7+\u025f\n+\f+\16+\u0262\13+\5+\u0264\n+\3+\3+\3+\3,\3,"+
		"\3,\3,\3,\5,\u026e\n,\3,\3,\3-\5-\u0273\n-\3-\3-\5-\u0277\n-\3-\3-\3."+
		"\3.\3.\3.\3.\5.\u0280\n.\3.\3.\3.\5.\u0285\n.\3.\7.\u0288\n.\f.\16.\u028b"+
		"\13.\3/\3/\5/\u028f\n/\3/\3/\7/\u0293\n/\f/\16/\u0296\13/\3/\5/\u0299"+
		"\n/\3\60\3\60\3\60\7\60\u029e\n\60\f\60\16\60\u02a1\13\60\3\61\3\61\3"+
		"\61\3\61\3\61\5\61\u02a8\n\61\3\61\3\61\3\61\3\61\3\62\3\62\3\63\3\63"+
		"\7\63\u02b2\n\63\f\63\16\63\u02b5\13\63\3\63\3\63\3\64\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\5\64\u02c6\n\64\3\65\3\65"+
		"\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\5\66\u02d2\n\66\3\67\3\67\3\67"+
		"\3\67\3\67\3\67\38\38\38\58\u02dd\n8\39\39\39\39\59\u02e3\n9\39\59\u02e6"+
		"\n9\39\39\59\u02ea\n9\39\39\39\3:\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\3<\3<"+
		"\3<\3=\3=\3=\3=\3=\3=\3=\5=\u0304\n=\3=\3=\6=\u0308\n=\r=\16=\u0309\3"+
		">\3>\5>\u030e\n>\3>\5>\u0311\n>\3>\3>\3?\3?\5?\u0317\n?\3?\3?\3@\3@\3"+
		"@\3@\3@\3A\3A\3A\3A\3A\3B\3B\5B\u0327\nB\3B\3B\7B\u032b\nB\fB\16B\u032e"+
		"\13B\3B\3B\3C\3C\3C\5C\u0335\nC\3C\3C\3D\3D\7D\u033b\nD\fD\16D\u033e\13"+
		"D\3D\3D\3D\5D\u0343\nD\7D\u0345\nD\fD\16D\u0348\13D\3D\3D\3D\3D\3D\3E"+
		"\3E\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F"+
		"\3F\3F\3F\3F\7F\u036b\nF\fF\16F\u036e\13F\3F\3F\3F\3F\3F\3F\5F\u0376\n"+
		"F\3F\3F\5F\u037a\nF\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3"+
		"F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3"+
		"F\3F\3F\3F\3F\3F\5F\u03a9\nF\3F\3F\3F\3F\5F\u03af\nF\3F\3F\5F\u03b3\n"+
		"F\3F\3F\3F\3F\3F\5F\u03ba\nF\3F\3F\3F\3F\3F\7F\u03c1\nF\fF\16F\u03c4\13"+
		"F\5F\u03c6\nF\3F\3F\3F\3F\3F\7F\u03cd\nF\fF\16F\u03d0\13F\3G\3G\5G\u03d4"+
		"\nG\3G\3G\5G\u03d8\nG\7G\u03da\nG\fG\16G\u03dd\13G\3G\3G\3H\3H\3I\3I\3"+
		"I\5I\u03e6\nI\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\5J\u03f3\nJ\3K\3K\7K\u03f7"+
		"\nK\fK\16K\u03fa\13K\3K\3K\3L\3L\3L\3L\5L\u0402\nL\3L\3L\3L\3L\7L\u0408"+
		"\nL\fL\16L\u040b\13L\3L\3L\5L\u040f\nL\5L\u0411\nL\3M\3M\3M\3M\3M\3M\3"+
		"M\6M\u041a\nM\rM\16M\u041b\3M\3M\3M\5M\u0421\nM\3N\3N\3N\3N\3O\3O\3O\3"+
		"O\3O\3O\3P\3P\3P\3P\3Q\3Q\3Q\6Q\u0434\nQ\rQ\16Q\u0435\3Q\3Q\5Q\u043a\n"+
		"Q\3Q\3Q\5Q\u043e\nQ\3R\3R\3R\3R\3R\3R\7R\u0446\nR\fR\16R\u0449\13R\5R"+
		"\u044b\nR\3R\3R\3R\3R\3R\7R\u0452\nR\fR\16R\u0455\13R\5R\u0457\nR\3R\3"+
		"R\3S\3S\3S\7S\u045e\nS\fS\16S\u0461\13S\3T\3T\3T\3T\3T\7T\u0468\nT\fT"+
		"\16T\u046b\13T\5T\u046d\nT\3T\3T\3U\3U\3V\3V\3V\5V\u0476\nV\3W\3W\3W\2"+
		"\4Z\u008aX\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66"+
		"8:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a"+
		"\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2"+
		"\u00a4\u00a6\u00a8\u00aa\u00ac\2\22\3\2\5\6\3\2\b\16\5\2yy{{}~\4\2||\177"+
		"\u0080\3\2\u0081\u0083\4\2>Dhl\3\2EF\3\2GH\3\2rs\4\2\22\22JK\3\2LM\3\2"+
		"\n\r\3\2PQ\4\2\16\16U^\4\2\u0085\u0085\u008c\u008c\4\2qs\u0086\u0087\2"+
		"\u0502\2\u00c2\3\2\2\2\4\u00c4\3\2\2\2\6\u00cd\3\2\2\2\b\u00d2\3\2\2\2"+
		"\n\u00d6\3\2\2\2\f\u00d9\3\2\2\2\16\u00dd\3\2\2\2\20\u00f1\3\2\2\2\22"+
		"\u00f6\3\2\2\2\24\u00f8\3\2\2\2\26\u0103\3\2\2\2\30\u0111\3\2\2\2\32\u011f"+
		"\3\2\2\2\34\u012a\3\2\2\2\36\u0133\3\2\2\2 \u0143\3\2\2\2\"\u0145\3\2"+
		"\2\2$\u0149\3\2\2\2&\u0164\3\2\2\2(\u016f\3\2\2\2*\u0185\3\2\2\2,\u0192"+
		"\3\2\2\2.\u01aa\3\2\2\2\60\u01b8\3\2\2\2\62\u01cd\3\2\2\2\64\u01de\3\2"+
		"\2\2\66\u01e8\3\2\2\28\u01ec\3\2\2\2:\u01ef\3\2\2\2<\u01f3\3\2\2\2>\u01f5"+
		"\3\2\2\2@\u01f7\3\2\2\2B\u0205\3\2\2\2D\u0213\3\2\2\2F\u021c\3\2\2\2H"+
		"\u021e\3\2\2\2J\u022d\3\2\2\2L\u0233\3\2\2\2N\u023a\3\2\2\2P\u0241\3\2"+
		"\2\2R\u0254\3\2\2\2T\u0258\3\2\2\2V\u0268\3\2\2\2X\u0272\3\2\2\2Z\u027f"+
		"\3\2\2\2\\\u028c\3\2\2\2^\u029a\3\2\2\2`\u02a2\3\2\2\2b\u02ad\3\2\2\2"+
		"d\u02af\3\2\2\2f\u02c5\3\2\2\2h\u02c7\3\2\2\2j\u02ca\3\2\2\2l\u02d3\3"+
		"\2\2\2n\u02dc\3\2\2\2p\u02de\3\2\2\2r\u02ee\3\2\2\2t\u02f6\3\2\2\2v\u02f9"+
		"\3\2\2\2x\u02fc\3\2\2\2z\u030b\3\2\2\2|\u0314\3\2\2\2~\u031a\3\2\2\2\u0080"+
		"\u031f\3\2\2\2\u0082\u0324\3\2\2\2\u0084\u0331\3\2\2\2\u0086\u0338\3\2"+
		"\2\2\u0088\u034e\3\2\2\2\u008a\u0379\3\2\2\2\u008c\u03d1\3\2\2\2\u008e"+
		"\u03e0\3\2\2\2\u0090\u03e2\3\2\2\2\u0092\u03f2\3\2\2\2\u0094\u03f4\3\2"+
		"\2\2\u0096\u0410\3\2\2\2\u0098\u0420\3\2\2\2\u009a\u0422\3\2\2\2\u009c"+
		"\u0426\3\2\2\2\u009e\u042c\3\2\2\2\u00a0\u0430\3\2\2\2\u00a2\u043f\3\2"+
		"\2\2\u00a4\u045a\3\2\2\2\u00a6\u0462\3\2\2\2\u00a8\u0470\3\2\2\2\u00aa"+
		"\u0475\3\2\2\2\u00ac\u0477\3\2\2\2\u00ae\u00ba\5\6\4\2\u00af\u00ba\5\16"+
		"\b\2\u00b0\u00ba\5\26\f\2\u00b1\u00ba\5\30\r\2\u00b2\u00ba\5\32\16\2\u00b3"+
		"\u00ba\5,\27\2\u00b4\u00ba\5L\'\2\u00b5\u00ba\5\64\33\2\u00b6\u00ba\5"+
		"H%\2\u00b7\u00ba\5J&\2\u00b8\u00ba\5T+\2\u00b9\u00ae\3\2\2\2\u00b9\u00af"+
		"\3\2\2\2\u00b9\u00b0\3\2\2\2\u00b9\u00b1\3\2\2\2\u00b9\u00b2\3\2\2\2\u00b9"+
		"\u00b3\3\2\2\2\u00b9\u00b4\3\2\2\2\u00b9\u00b5\3\2\2\2\u00b9\u00b6\3\2"+
		"\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00b8\3\2\2\2\u00ba\u00bd\3\2\2\2\u00bb"+
		"\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00be\3\2\2\2\u00bd\u00bb\3\2"+
		"\2\2\u00be\u00c3\7\2\2\3\u00bf\u00c0\5\4\3\2\u00c0\u00c1\7\2\2\3\u00c1"+
		"\u00c3\3\2\2\2\u00c2\u00bb\3\2\2\2\u00c2\u00bf\3\2\2\2\u00c3\3\3\2\2\2"+
		"\u00c4\u00c9\7\3\2\2\u00c5\u00ca\5Z.\2\u00c6\u00ca\5\u008aF\2\u00c7\u00ca"+
		"\5f\64\2\u00c8\u00ca\5 \21\2\u00c9\u00c5\3\2\2\2\u00c9\u00c6\3\2\2\2\u00c9"+
		"\u00c7\3\2\2\2\u00c9\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\7\2"+
		"\2\3\u00cc\5\3\2\2\2\u00cd\u00ce\7\4\2\2\u00ce\u00cf\t\2\2\2\u00cf\u00d0"+
		"\5\b\5\2\u00d0\u00d1\7\7\2\2\u00d1\7\3\2\2\2\u00d2\u00d4\5\f\7\2\u00d3"+
		"\u00d5\5\f\7\2\u00d4\u00d3\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\t\3\2\2\2"+
		"\u00d6\u00d7\t\3\2\2\u00d7\13\3\2\2\2\u00d8\u00da\5\n\6\2\u00d9\u00d8"+
		"\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dc\7p\2\2\u00dc"+
		"\r\3\2\2\2\u00dd\u00ed\7\17\2\2\u00de\u00e1\5\22\n\2\u00df\u00e0\7\20"+
		"\2\2\u00e0\u00e2\5\u00acW\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2"+
		"\u00ee\3\2\2\2\u00e3\u00e4\5\24\13\2\u00e4\u00e5\7\21\2\2\u00e5\u00e6"+
		"\5\22\n\2\u00e6\u00ee\3\2\2\2\u00e7\u00e8\7\22\2\2\u00e8\u00e9\7\20\2"+
		"\2\u00e9\u00ea\5\u00acW\2\u00ea\u00eb\7\21\2\2\u00eb\u00ec\5\22\n\2\u00ec"+
		"\u00ee\3\2\2\2\u00ed\u00de\3\2\2\2\u00ed\u00e3\3\2\2\2\u00ed\u00e7\3\2"+
		"\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\7\7\2\2\u00f0\17\3\2\2\2\u00f1\u00f4"+
		"\5\u00acW\2\u00f2\u00f3\7\20\2\2\u00f3\u00f5\5\u00acW\2\u00f4\u00f2\3"+
		"\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\21\3\2\2\2\u00f6\u00f7\7\u0086\2\2\u00f7"+
		"\23\3\2\2\2\u00f8\u00f9\7\23\2\2\u00f9\u00fe\5\20\t\2\u00fa\u00fb\7\24"+
		"\2\2\u00fb\u00fd\5\20\t\2\u00fc\u00fa\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe"+
		"\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0101\3\2\2\2\u0100\u00fe\3\2"+
		"\2\2\u0101\u0102\7\25\2\2\u0102\25\3\2\2\2\u0103\u0104\7\26\2\2\u0104"+
		"\u0106\5\u00acW\2\u0105\u0107\5\34\17\2\u0106\u0105\3\2\2\2\u0106\u0107"+
		"\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u010c\7\23\2\2\u0109\u010b\5 \21\2"+
		"\u010a\u0109\3\2\2\2\u010b\u010e\3\2\2\2\u010c\u010a\3\2\2\2\u010c\u010d"+
		"\3\2\2\2\u010d\u010f\3\2\2\2\u010e\u010c\3\2\2\2\u010f\u0110\7\25\2\2"+
		"\u0110\27\3\2\2\2\u0111\u0112\7\27\2\2\u0112\u0114\5\u00acW\2\u0113\u0115"+
		"\5\34\17\2\u0114\u0113\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0116\3\2\2\2"+
		"\u0116\u011a\7\23\2\2\u0117\u0119\5 \21\2\u0118\u0117\3\2\2\2\u0119\u011c"+
		"\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011d\3\2\2\2\u011c"+
		"\u011a\3\2\2\2\u011d\u011e\7\25\2\2\u011e\31\3\2\2\2\u011f\u0120\7\30"+
		"\2\2\u0120\u0121\5\u00acW\2\u0121\u0125\7\23\2\2\u0122\u0124\5 \21\2\u0123"+
		"\u0122\3\2\2\2\u0124\u0127\3\2\2\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2"+
		"\2\2\u0126\u0128\3\2\2\2\u0127\u0125\3\2\2\2\u0128\u0129\7\25\2\2\u0129"+
		"\33\3\2\2\2\u012a\u012b\7\31\2\2\u012b\u0130\5\36\20\2\u012c\u012d\7\24"+
		"\2\2\u012d\u012f\5\36\20\2\u012e\u012c\3\2\2\2\u012f\u0132\3\2\2\2\u0130"+
		"\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131\35\3\2\2\2\u0132\u0130\3\2\2"+
		"\2\u0133\u0135\5&\24\2\u0134\u0136\5$\23\2\u0135\u0134\3\2\2\2\u0135\u0136"+
		"\3\2\2\2\u0136\37\3\2\2\2\u0137\u0144\5*\26\2\u0138\u0144\5,\27\2\u0139"+
		"\u0144\5.\30\2\u013a\u0144\5\60\31\2\u013b\u0144\5\62\32\2\u013c\u0144"+
		"\5\64\33\2\u013d\u0144\5H%\2\u013e\u0144\5J&\2\u013f\u0144\5(\25\2\u0140"+
		"\u0144\5P)\2\u0141\u0144\5T+\2\u0142\u0144\5V,\2\u0143\u0137\3\2\2\2\u0143"+
		"\u0138\3\2\2\2\u0143\u0139\3\2\2\2\u0143\u013a\3\2\2\2\u0143\u013b\3\2"+
		"\2\2\u0143\u013c\3\2\2\2\u0143\u013d\3\2\2\2\u0143\u013e\3\2\2\2\u0143"+
		"\u013f\3\2\2\2\u0143\u0140\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0142\3\2"+
		"\2\2\u0144!\3\2\2\2\u0145\u0146\5\u00acW\2\u0146\u0147\7\32\2\2\u0147"+
		"\u0148\5\u008aF\2\u0148#\3\2\2\2\u0149\u0160\7\33\2\2\u014a\u014f\5\u008a"+
		"F\2\u014b\u014c\7\24\2\2\u014c\u014e\5\u008aF\2\u014d\u014b\3\2\2\2\u014e"+
		"\u0151\3\2\2\2\u014f\u014d\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u0153\3\2"+
		"\2\2\u0151\u014f\3\2\2\2\u0152\u014a\3\2\2\2\u0152\u0153\3\2\2\2\u0153"+
		"\u0161\3\2\2\2\u0154\u015d\7\23\2\2\u0155\u015a\5\"\22\2\u0156\u0157\7"+
		"\24\2\2\u0157\u0159\5\"\22\2\u0158\u0156\3\2\2\2\u0159\u015c\3\2\2\2\u015a"+
		"\u0158\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u015e\3\2\2\2\u015c\u015a\3\2"+
		"\2\2\u015d\u0155\3\2\2\2\u015d\u015e\3\2\2\2\u015e\u015f\3\2\2\2\u015f"+
		"\u0161\7\25\2\2\u0160\u0152\3\2\2\2\u0160\u0154\3\2\2\2\u0161\u0162\3"+
		"\2\2\2\u0162\u0163\7\34\2\2\u0163%\3\2\2\2\u0164\u0169\5\u00acW\2\u0165"+
		"\u0166\7\35\2\2\u0166\u0168\5\u00acW\2\u0167\u0165\3\2\2\2\u0168\u016b"+
		"\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a\3\2\2\2\u016a\'\3\2\2\2\u016b"+
		"\u0169\3\2\2\2\u016c\u016e\7\u0084\2\2\u016d\u016c\3\2\2\2\u016e\u0171"+
		"\3\2\2\2\u016f\u016d\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u0172\3\2\2\2\u0171"+
		"\u016f\3\2\2\2\u0172\u017b\5\u0090I\2\u0173\u017a\7~\2\2\u0174\u017a\7"+
		"}\2\2\u0175\u017a\7{\2\2\u0176\u017a\7w\2\2\u0177\u017a\5@!\2\u0178\u017a"+
		"\7\36\2\2\u0179\u0173\3\2\2\2\u0179\u0174\3\2\2\2\u0179\u0175\3\2\2\2"+
		"\u0179\u0176\3\2\2\2\u0179\u0177\3\2\2\2\u0179\u0178\3\2\2\2\u017a\u017d"+
		"\3\2\2\2\u017b\u0179\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017e\3\2\2\2\u017d"+
		"\u017b\3\2\2\2\u017e\u0181\5\u00acW\2\u017f\u0180\7\16\2\2\u0180\u0182"+
		"\5\u008aF\2\u0181\u017f\3\2\2\2\u0181\u0182\3\2\2\2\u0182\u0183\3\2\2"+
		"\2\u0183\u0184\7\7\2\2\u0184)\3\2\2\2\u0185\u0186\7\37\2\2\u0186\u018d"+
		"\5B\"\2\u0187\u018c\5:\36\2\u0188\u018c\7|\2\2\u0189\u018c\7{\2\2\u018a"+
		"\u018c\7~\2\2\u018b\u0187\3\2\2\2\u018b\u0188\3\2\2\2\u018b\u0189\3\2"+
		"\2\2\u018b\u018a\3\2\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018e\u0190\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0191\5d"+
		"\63\2\u0191+\3\2\2\2\u0192\u0196\7 \2\2\u0193\u0197\5\u00acW\2\u0194\u0197"+
		"\7!\2\2\u0195\u0197\7\"\2\2\u0196\u0193\3\2\2\2\u0196\u0194\3\2\2\2\u0196"+
		"\u0195\3\2\2\2\u0197\u0198\3\2\2\2\u0198\u01a0\5B\"\2\u0199\u019f\5<\37"+
		"\2\u019a\u019f\5> \2\u019b\u019f\5:\36\2\u019c\u019f\7#\2\2\u019d\u019f"+
		"\5@!\2\u019e\u0199\3\2\2\2\u019e\u019a\3\2\2\2\u019e\u019b\3\2\2\2\u019e"+
		"\u019c\3\2\2\2\u019e\u019d\3\2\2\2\u019f\u01a2\3\2\2\2\u01a0\u019e\3\2"+
		"\2\2\u01a0\u01a1\3\2\2\2\u01a1\u01a4\3\2\2\2\u01a2\u01a0\3\2\2\2\u01a3"+
		"\u01a5\58\35\2\u01a4\u01a3\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5\u01a8\3\2"+
		"\2\2\u01a6\u01a9\7\7\2\2\u01a7\u01a9\5d\63\2\u01a8\u01a6\3\2\2\2\u01a8"+
		"\u01a7\3\2\2\2\u01a9-\3\2\2\2\u01aa\u01ab\7$\2\2\u01ab\u01ac\5\u00acW"+
		"\2\u01ac\u01b1\5B\"\2\u01ad\u01b0\7#\2\2\u01ae\u01b0\5@!\2\u01af\u01ad"+
		"\3\2\2\2\u01af\u01ae\3\2\2\2\u01b0\u01b3\3\2\2\2\u01b1\u01af\3\2\2\2\u01b1"+
		"\u01b2\3\2\2\2\u01b2\u01b6\3\2\2\2\u01b3\u01b1\3\2\2\2\u01b4\u01b7\7\7"+
		"\2\2\u01b5\u01b7\5d\63\2\u01b6\u01b4\3\2\2\2\u01b6\u01b5\3\2\2\2\u01b7"+
		"/\3\2\2\2\u01b8\u01ba\7!\2\2\u01b9\u01bb\5B\"\2\u01ba\u01b9\3\2\2\2\u01ba"+
		"\u01bb\3\2\2\2\u01bb\u01c3\3\2\2\2\u01bc\u01c2\7y\2\2\u01bd\u01c2\5> "+
		"\2\u01be\u01c2\5:\36\2\u01bf\u01c2\7#\2\2\u01c0\u01c2\5@!\2\u01c1\u01bc"+
		"\3\2\2\2\u01c1\u01bd\3\2\2\2\u01c1\u01be\3\2\2\2\u01c1\u01bf\3\2\2\2\u01c1"+
		"\u01c0\3\2\2\2\u01c2\u01c5\3\2\2\2\u01c3\u01c1\3\2\2\2\u01c3\u01c4\3\2"+
		"\2\2\u01c4\u01c7\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c6\u01c8\58\35\2\u01c7"+
		"\u01c6\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01cb\3\2\2\2\u01c9\u01cc\7\7"+
		"\2\2\u01ca\u01cc\5d\63\2\u01cb\u01c9\3\2\2\2\u01cb\u01ca\3\2\2\2\u01cc"+
		"\61\3\2\2\2\u01cd\u01ce\7\"\2\2\u01ce\u01cf\7\33\2\2\u01cf\u01d7\7\34"+
		"\2\2\u01d0\u01d6\7y\2\2\u01d1\u01d6\7|\2\2\u01d2\u01d6\5:\36\2\u01d3\u01d6"+
		"\7#\2\2\u01d4\u01d6\5@!\2\u01d5\u01d0\3\2\2\2\u01d5\u01d1\3\2\2\2\u01d5"+
		"\u01d2\3\2\2\2\u01d5\u01d3\3\2\2\2\u01d5\u01d4\3\2\2\2\u01d6\u01d9\3\2"+
		"\2\2\u01d7\u01d5\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01dc\3\2\2\2\u01d9"+
		"\u01d7\3\2\2\2\u01da\u01dd\7\7\2\2\u01db\u01dd\5d\63\2\u01dc\u01da\3\2"+
		"\2\2\u01dc\u01db\3\2\2\2\u01dd\63\3\2\2\2\u01de\u01df\7%\2\2\u01df\u01e0"+
		"\5\u00acW\2\u01e0\u01e2\7\23\2\2\u01e1\u01e3\5\66\34\2\u01e2\u01e1\3\2"+
		"\2\2\u01e3\u01e4\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e5"+
		"\u01e6\3\2\2\2\u01e6\u01e7\7\25\2\2\u01e7\65\3\2\2\2\u01e8\u01e9\5Z.\2"+
		"\u01e9\u01ea\5\u00acW\2\u01ea\u01eb\7\7\2\2\u01eb\67\3\2\2\2\u01ec\u01ed"+
		"\7&\2\2\u01ed\u01ee\5B\"\2\u01ee9\3\2\2\2\u01ef\u01f1\5&\24\2\u01f0\u01f2"+
		"\5$\23\2\u01f1\u01f0\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2;\3\2\2\2\u01f3"+
		"\u01f4\t\4\2\2\u01f4=\3\2\2\2\u01f5\u01f6\t\5\2\2\u01f6?\3\2\2\2\u01f7"+
		"\u0203\7\'\2\2\u01f8\u01f9\7\33\2\2\u01f9\u01fe\5&\24\2\u01fa\u01fb\7"+
		"\24\2\2\u01fb\u01fd\5&\24\2\u01fc\u01fa\3\2\2\2\u01fd\u0200\3\2\2\2\u01fe"+
		"\u01fc\3\2\2\2\u01fe\u01ff\3\2\2\2\u01ff\u0201\3\2\2\2\u0200\u01fe\3\2"+
		"\2\2\u0201\u0202\7\34\2\2\u0202\u0204\3\2\2\2\u0203\u01f8\3\2\2\2\u0203"+
		"\u0204\3\2\2\2\u0204A\3\2\2\2\u0205\u020e\7\33\2\2\u0206\u020b\5D#\2\u0207"+
		"\u0208\7\24\2\2\u0208\u020a\5D#\2\u0209\u0207\3\2\2\2\u020a\u020d\3\2"+
		"\2\2\u020b\u0209\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u020f\3\2\2\2\u020d"+
		"\u020b\3\2\2\2\u020e\u0206\3\2\2\2\u020e\u020f\3\2\2\2\u020f\u0210\3\2"+
		"\2\2\u0210\u0211\7\34\2\2\u0211C\3\2\2\2\u0212\u0214\7\u0084\2\2\u0213"+
		"\u0212\3\2\2\2\u0213\u0214\3\2\2\2\u0214\u0215\3\2\2\2\u0215\u0217\5\u0090"+
		"I\2\u0216\u0218\5b\62\2\u0217\u0216\3\2\2\2\u0217\u0218\3\2\2\2\u0218"+
		"\u021a\3\2\2\2\u0219\u021b\5\u00acW\2\u021a\u0219\3\2\2\2\u021a\u021b"+
		"\3\2\2\2\u021bE\3\2\2\2\u021c\u021d\5\u00acW\2\u021dG\3\2\2\2\u021e\u021f"+
		"\7(\2\2\u021f\u0220\5\u00acW\2\u0220\u0222\7\23\2\2\u0221\u0223\5F$\2"+
		"\u0222\u0221\3\2\2\2\u0222\u0223\3\2\2\2\u0223\u0228\3\2\2\2\u0224\u0225"+
		"\7\24\2\2\u0225\u0227\5F$\2\u0226\u0224\3\2\2\2\u0227\u022a\3\2\2\2\u0228"+
		"\u0226\3\2\2\2\u0228\u0229\3\2\2\2\u0229\u022b\3\2\2\2\u022a\u0228\3\2"+
		"\2\2\u022b\u022c\7\25\2\2\u022cI\3\2\2\2\u022d\u022e\7)\2\2\u022e\u022f"+
		"\5\u00acW\2\u022f\u0230\7\31\2\2\u0230\u0231\5\u0088E\2\u0231\u0232\7"+
		"\7\2\2\u0232K\3\2\2\2\u0233\u0234\5\u0090I\2\u0234\u0235\7w\2\2\u0235"+
		"\u0236\5\u00acW\2\u0236\u0237\7\16\2\2\u0237\u0238\5\u008aF\2\u0238\u0239"+
		"\7\7\2\2\u0239M\3\2\2\2\u023a\u023c\5\u0090I\2\u023b\u023d\7z\2\2\u023c"+
		"\u023b\3\2\2\2\u023c\u023d\3\2\2\2\u023d\u023f\3\2\2\2\u023e\u0240\5\u00ac"+
		"W\2\u023f\u023e\3\2\2\2\u023f\u0240\3\2\2\2\u0240O\3\2\2\2\u0241\u0242"+
		"\7*\2\2\u0242\u0243\5\u00acW\2\u0243\u024c\7\33\2\2\u0244\u0249\5N(\2"+
		"\u0245\u0246\7\24\2\2\u0246\u0248\5N(\2\u0247\u0245\3\2\2\2\u0248\u024b"+
		"\3\2\2\2\u0249\u0247\3\2\2\2\u0249\u024a\3\2\2\2\u024a\u024d\3\2\2\2\u024b"+
		"\u0249\3\2\2\2\u024c\u0244\3\2\2\2\u024c\u024d\3\2\2\2\u024d\u024e\3\2"+
		"\2\2\u024e\u0250\7\34\2\2\u024f\u0251\7u\2\2\u0250\u024f\3\2\2\2\u0250"+
		"\u0251\3\2\2\2\u0251\u0252\3\2\2\2\u0252\u0253\7\7\2\2\u0253Q\3\2\2\2"+
		"\u0254\u0256\5\u0090I\2\u0255\u0257\5\u00acW\2\u0256\u0255\3\2\2\2\u0256"+
		"\u0257\3\2\2\2\u0257S\3\2\2\2\u0258\u0259\7+\2\2\u0259\u025a\5\u00acW"+
		"\2\u025a\u0263\7\33\2\2\u025b\u0260\5R*\2\u025c\u025d\7\24\2\2\u025d\u025f"+
		"\5R*\2\u025e\u025c\3\2\2\2\u025f\u0262\3\2\2\2\u0260\u025e\3\2\2\2\u0260"+
		"\u0261\3\2\2\2\u0261\u0264\3\2\2\2\u0262\u0260\3\2\2\2\u0263\u025b\3\2"+
		"\2\2\u0263\u0264\3\2\2\2\u0264\u0265\3\2\2\2\u0265\u0266\7\34\2\2\u0266"+
		"\u0267\7\7\2\2\u0267U\3\2\2\2\u0268\u0269\7,\2\2\u0269\u026a\5&\24\2\u026a"+
		"\u026d\7-\2\2\u026b\u026e\7\22\2\2\u026c\u026e\5Z.\2\u026d\u026b\3\2\2"+
		"\2\u026d\u026c\3\2\2\2\u026e\u026f\3\2\2\2\u026f\u0270\7\7\2\2\u0270W"+
		"\3\2\2\2\u0271\u0273\7\u0084\2\2\u0272\u0271\3\2\2\2\u0272\u0273\3\2\2"+
		"\2\u0273\u0274\3\2\2\2\u0274\u0276\5\u0090I\2\u0275\u0277\5b\62\2\u0276"+
		"\u0275\3\2\2\2\u0276\u0277\3\2\2\2\u0277\u0278\3\2\2\2\u0278\u0279\5\u00ac"+
		"W\2\u0279Y\3\2\2\2\u027a\u027b\b.\1\2\u027b\u0280\5\u0088E\2\u027c\u0280"+
		"\5\\/\2\u027d\u0280\5^\60\2\u027e\u0280\5`\61\2\u027f\u027a\3\2\2\2\u027f"+
		"\u027c\3\2\2\2\u027f\u027d\3\2\2\2\u027f\u027e\3\2\2\2\u0280\u0289\3\2"+
		"\2\2\u0281\u0282\f\4\2\2\u0282\u0284\7.\2\2\u0283\u0285\5\u008aF\2\u0284"+
		"\u0283\3\2\2\2\u0284\u0285\3\2\2\2\u0285\u0286\3\2\2\2\u0286\u0288\7/"+
		"\2\2\u0287\u0281\3\2\2\2\u0288\u028b\3\2\2\2\u0289\u0287\3\2\2\2\u0289"+
		"\u028a\3\2\2\2\u028a[\3\2\2\2\u028b\u0289\3\2\2\2\u028c\u028e\7 \2\2\u028d"+
		"\u028f\5B\"\2\u028e\u028d\3\2\2\2\u028e\u028f\3\2\2\2\u028f\u0294\3\2"+
		"\2\2\u0290\u0293\5<\37\2\u0291\u0293\5> \2\u0292\u0290\3\2\2\2\u0292\u0291"+
		"\3\2\2\2\u0293\u0296\3\2\2\2\u0294\u0292\3\2\2\2\u0294\u0295\3\2\2\2\u0295"+
		"\u0298\3\2\2\2\u0296\u0294\3\2\2\2\u0297\u0299\58\35\2\u0298\u0297\3\2"+
		"\2\2\u0298\u0299\3\2\2\2\u0299]\3\2\2\2\u029a\u029f\5\u00acW\2\u029b\u029c"+
		"\7\35\2\2\u029c\u029e\5\u00acW\2\u029d\u029b\3\2\2\2\u029e\u02a1\3\2\2"+
		"\2\u029f\u029d\3\2\2\2\u029f\u02a0\3\2\2\2\u02a0_\3\2\2\2\u02a1\u029f"+
		"\3\2\2\2\u02a2\u02a3\7\60\2\2\u02a3\u02a4\7\33\2\2\u02a4\u02a7\5\u0088"+
		"E\2\u02a5\u02a6\7\61\2\2\u02a6\u02a8\5\u00acW\2\u02a7\u02a5\3\2\2\2\u02a7"+
		"\u02a8\3\2\2\2\u02a8\u02a9\3\2\2\2\u02a9\u02aa\7\62\2\2\u02aa\u02ab\5"+
		"\u0090I\2\u02ab\u02ac\7\34\2\2\u02aca\3\2\2\2\u02ad\u02ae\t\6\2\2\u02ae"+
		"c\3\2\2\2\u02af\u02b3\7\23\2\2\u02b0\u02b2\5f\64\2\u02b1\u02b0\3\2\2\2"+
		"\u02b2\u02b5\3\2\2\2\u02b3\u02b1\3\2\2\2\u02b3\u02b4\3\2\2\2\u02b4\u02b6"+
		"\3\2\2\2\u02b5\u02b3\3\2\2\2\u02b6\u02b7\7\25\2\2\u02b7e\3\2\2\2\u02b8"+
		"\u02c6\5d\63\2\u02b9\u02c6\5n8\2\u02ba\u02c6\5j\66\2\u02bb\u02c6\5p9\2"+
		"\u02bc\u02c6\5l\67\2\u02bd\u02c6\5r:\2\u02be\u02c6\5t;\2\u02bf\u02c6\5"+
		"v<\2\u02c0\u02c6\5x=\2\u02c1\u02c6\5|?\2\u02c2\u02c6\5~@\2\u02c3\u02c6"+
		"\5\u0080A\2\u02c4\u02c6\5\u0082B\2\u02c5\u02b8\3\2\2\2\u02c5\u02b9\3\2"+
		"\2\2\u02c5\u02ba\3\2\2\2\u02c5\u02bb\3\2\2\2\u02c5\u02bc\3\2\2\2\u02c5"+
		"\u02bd\3\2\2\2\u02c5\u02be\3\2\2\2\u02c5\u02bf\3\2\2\2\u02c5\u02c0\3\2"+
		"\2\2\u02c5\u02c1\3\2\2\2\u02c5\u02c2\3\2\2\2\u02c5\u02c3\3\2\2\2\u02c5"+
		"\u02c4\3\2\2\2\u02c6g\3\2\2\2\u02c7\u02c8\5\u008aF\2\u02c8\u02c9\7\7\2"+
		"\2\u02c9i\3\2\2\2\u02ca\u02cb\7\63\2\2\u02cb\u02cc\7\33\2\2\u02cc\u02cd"+
		"\5\u008aF\2\u02cd\u02ce\7\34\2\2\u02ce\u02d1\5f\64\2\u02cf\u02d0\7\64"+
		"\2\2\u02d0\u02d2\5f\64\2\u02d1\u02cf\3\2\2\2\u02d1\u02d2\3\2\2\2\u02d2"+
		"k\3\2\2\2\u02d3\u02d4\7\65\2\2\u02d4\u02d5\7\33\2\2\u02d5\u02d6\5\u008a"+
		"F\2\u02d6\u02d7\7\34\2\2\u02d7\u02d8\5f\64\2\u02d8m\3\2\2\2\u02d9\u02dd"+
		"\5\u0084C\2\u02da\u02dd\5h\65\2\u02db\u02dd\5\u0086D\2\u02dc\u02d9\3\2"+
		"\2\2\u02dc\u02da\3\2\2\2\u02dc\u02db\3\2\2\2\u02ddo\3\2\2\2\u02de\u02df"+
		"\7-\2\2\u02df\u02e2\7\33\2\2\u02e0\u02e3\5n8\2\u02e1\u02e3\7\7\2\2\u02e2"+
		"\u02e0\3\2\2\2\u02e2\u02e1\3\2\2\2\u02e3\u02e5\3\2\2\2\u02e4\u02e6\5\u008a"+
		"F\2\u02e5\u02e4\3\2\2\2\u02e5\u02e6\3\2\2\2\u02e6\u02e7\3\2\2\2\u02e7"+
		"\u02e9\7\7\2\2\u02e8\u02ea\5\u008aF\2\u02e9\u02e8\3\2\2\2\u02e9\u02ea"+
		"\3\2\2\2\u02ea\u02eb\3\2\2\2\u02eb\u02ec\7\34\2\2\u02ec\u02ed\5f\64\2"+
		"\u02edq\3\2\2\2\u02ee\u02ef\7\66\2\2\u02ef\u02f0\5f\64\2\u02f0\u02f1\7"+
		"\65\2\2\u02f1\u02f2\7\33\2\2\u02f2\u02f3\5\u008aF\2\u02f3\u02f4\7\34\2"+
		"\2\u02f4\u02f5\7\7\2\2\u02f5s\3\2\2\2\u02f6\u02f7\7x\2\2\u02f7\u02f8\7"+
		"\7\2\2\u02f8u\3\2\2\2\u02f9\u02fa\7v\2\2\u02fa\u02fb\7\7\2\2\u02fbw\3"+
		"\2\2\2\u02fc\u02fd\7\67\2\2\u02fd\u0303\5\u008aF\2\u02fe\u02ff\7&\2\2"+
		"\u02ff\u0300\7\33\2\2\u0300\u0301\5B\"\2\u0301\u0302\7\34\2\2\u0302\u0304"+
		"\3\2\2\2\u0303\u02fe\3\2\2\2\u0303\u0304\3\2\2\2\u0304\u0305\3\2\2\2\u0305"+
		"\u0307\5d\63\2\u0306\u0308\5z>\2\u0307\u0306\3\2\2\2\u0308\u0309\3\2\2"+
		"\2\u0309\u0307\3\2\2\2\u0309\u030a\3\2\2\2\u030ay\3\2\2\2\u030b\u030d"+
		"\78\2\2\u030c\u030e\5\u00acW\2\u030d\u030c\3\2\2\2\u030d\u030e\3\2\2\2"+
		"\u030e\u0310\3\2\2\2\u030f\u0311\5B\"\2\u0310\u030f\3\2\2\2\u0310\u0311"+
		"\3\2\2\2\u0311\u0312\3\2\2\2\u0312\u0313\5d\63\2\u0313{\3\2\2\2\u0314"+
		"\u0316\79\2\2\u0315\u0317\5\u008aF\2\u0316\u0315\3\2\2\2\u0316\u0317\3"+
		"\2\2\2\u0317\u0318\3\2\2\2\u0318\u0319\7\7\2\2\u0319}\3\2\2\2\u031a\u031b"+
		"\7:\2\2\u031b\u031c\5\u008aF\2\u031c\u031d\5$\23\2\u031d\u031e\7\7\2\2"+
		"\u031e\177\3\2\2\2\u031f\u0320\7;\2\2\u0320\u0321\5\u008aF\2\u0321\u0322"+
		"\5$\23\2\u0322\u0323\7\7\2\2\u0323\u0081\3\2\2\2\u0324\u0326\7<\2\2\u0325"+
		"\u0327\7=\2\2\u0326\u0325\3\2\2\2\u0326\u0327\3\2\2\2\u0327\u0328\3\2"+
		"\2\2\u0328\u032c\7\23\2\2\u0329\u032b\5\u0092J\2\u032a\u0329\3\2\2\2\u032b"+
		"\u032e\3\2\2\2\u032c\u032a\3\2\2\2\u032c\u032d\3\2\2\2\u032d\u032f\3\2"+
		"\2\2\u032e\u032c\3\2\2\2\u032f\u0330\7\25\2\2\u0330\u0083\3\2\2\2\u0331"+
		"\u0334\5X-\2\u0332\u0333\7\16\2\2\u0333\u0335\5\u008aF\2\u0334\u0332\3"+
		"\2\2\2\u0334\u0335\3\2\2\2\u0335\u0336\3\2\2\2\u0336\u0337\7\7\2\2\u0337"+
		"\u0085\3\2\2\2\u0338\u033c\7\33\2\2\u0339\u033b\7\24\2\2\u033a\u0339\3"+
		"\2\2\2\u033b\u033e\3\2\2\2\u033c\u033a\3\2\2\2\u033c\u033d\3\2\2\2\u033d"+
		"\u033f\3\2\2\2\u033e\u033c\3\2\2\2\u033f\u0346\5X-\2\u0340\u0342\7\24"+
		"\2\2\u0341\u0343\5X-\2\u0342\u0341\3\2\2\2\u0342\u0343\3\2\2\2\u0343\u0345"+
		"\3\2\2\2\u0344\u0340\3\2\2\2\u0345\u0348\3\2\2\2\u0346\u0344\3\2\2\2\u0346"+
		"\u0347\3\2\2\2\u0347\u0349\3\2\2\2\u0348\u0346\3\2\2\2\u0349\u034a\7\34"+
		"\2\2\u034a\u034b\7\16\2\2\u034b\u034c\5\u008aF\2\u034c\u034d\7\7\2\2\u034d"+
		"\u0087\3\2\2\2\u034e\u034f\t\7\2\2\u034f\u0089\3\2\2\2\u0350\u0351\bF"+
		"\1\2\u0351\u037a\7m\2\2\u0352\u037a\7n\2\2\u0353\u037a\7o\2\2\u0354\u0355"+
		"\7|\2\2\u0355\u037a\5$\23\2\u0356\u0357\7)\2\2\u0357\u0358\7\33\2\2\u0358"+
		"\u0359\5Z.\2\u0359\u035a\7\34\2\2\u035a\u037a\3\2\2\2\u035b\u035c\t\b"+
		"\2\2\u035c\u037a\5\u008aF\33\u035d\u035e\t\t\2\2\u035e\u037a\5\u008aF"+
		"\32\u035f\u0360\7\61\2\2\u0360\u037a\5\u008aF\31\u0361\u0362\7\t\2\2\u0362"+
		"\u037a\5\u008aF\30\u0363\u0364\7_\2\2\u0364\u037a\5Z.\2\u0365\u037a\5"+
		"\u008cG\2\u0366\u0367\7.\2\2\u0367\u036c\5\u008aF\2\u0368\u0369\7\24\2"+
		"\2\u0369\u036b\5\u008aF\2\u036a\u0368\3\2\2\2\u036b\u036e\3\2\2\2\u036c"+
		"\u036a\3\2\2\2\u036c\u036d\3\2\2\2\u036d\u036f\3\2\2\2\u036e\u036c\3\2"+
		"\2\2\u036f\u0370\7/\2\2\u0370\u037a\3\2\2\2\u0371\u037a\5\u00acW\2\u0372"+
		"\u037a\7q\2\2\u0373\u0375\t\n\2\2\u0374\u0376\7\u008b\2\2\u0375\u0374"+
		"\3\2\2\2\u0375\u0376\3\2\2\2\u0376\u037a\3\2\2\2\u0377\u037a\7\u0086\2"+
		"\2\u0378\u037a\5\u0088E\2\u0379\u0350\3\2\2\2\u0379\u0352\3\2\2\2\u0379"+
		"\u0353\3\2\2\2\u0379\u0354\3\2\2\2\u0379\u0356\3\2\2\2\u0379\u035b\3\2"+
		"\2\2\u0379\u035d\3\2\2\2\u0379\u035f\3\2\2\2\u0379\u0361\3\2\2\2\u0379"+
		"\u0363\3\2\2\2\u0379\u0365\3\2\2\2\u0379\u0366\3\2\2\2\u0379\u0371\3\2"+
		"\2\2\u0379\u0372\3\2\2\2\u0379\u0373\3\2\2\2\u0379\u0377\3\2\2\2\u0379"+
		"\u0378\3\2\2\2\u037a\u03ce\3\2\2\2\u037b\u037c\f\27\2\2\u037c\u037d\7"+
		"I\2\2\u037d\u03cd\5\u008aF\27\u037e\u037f\f\26\2\2\u037f\u0380\t\13\2"+
		"\2\u0380\u03cd\5\u008aF\27\u0381\u0382\f\25\2\2\u0382\u0383\t\t\2\2\u0383"+
		"\u03cd\5\u008aF\26\u0384\u0385\f\24\2\2\u0385\u0386\t\f\2\2\u0386\u03cd"+
		"\5\u008aF\25\u0387\u0388\f\23\2\2\u0388\u0389\7N\2\2\u0389\u03cd\5\u008a"+
		"F\24\u038a\u038b\f\22\2\2\u038b\u038c\7\b\2\2\u038c\u03cd\5\u008aF\23"+
		"\u038d\u038e\f\21\2\2\u038e\u038f\7O\2\2\u038f\u03cd\5\u008aF\22\u0390"+
		"\u0391\f\20\2\2\u0391\u0392\t\r\2\2\u0392\u03cd\5\u008aF\21\u0393\u0394"+
		"\f\17\2\2\u0394\u0395\t\16\2\2\u0395\u03cd\5\u008aF\20\u0396\u0397\f\16"+
		"\2\2\u0397\u0398\7R\2\2\u0398\u03cd\5\u008aF\17\u0399\u039a\f\r\2\2\u039a"+
		"\u039b\7S\2\2\u039b\u03cd\5\u008aF\16\u039c\u039d\f\f\2\2\u039d\u039e"+
		"\7T\2\2\u039e\u039f\5\u008aF\2\u039f\u03a0\7\32\2\2\u03a0\u03a1\5\u008a"+
		"F\f\u03a1\u03cd\3\2\2\2\u03a2\u03a3\f\13\2\2\u03a3\u03a4\t\17\2\2\u03a4"+
		"\u03cd\5\u008aF\f\u03a5\u03a6\f#\2\2\u03a6\u03a8\7.\2\2\u03a7\u03a9\5"+
		"\u008aF\2\u03a8\u03a7\3\2\2\2\u03a8\u03a9\3\2\2\2\u03a9\u03aa\3\2\2\2"+
		"\u03aa\u03cd\7/\2\2\u03ab\u03ac\f\"\2\2\u03ac\u03ae\7.\2\2\u03ad\u03af"+
		"\5\u008aF\2\u03ae\u03ad\3\2\2\2\u03ae\u03af\3\2\2\2\u03af\u03b0\3\2\2"+
		"\2\u03b0\u03b2\7\32\2\2\u03b1\u03b3\5\u008aF\2\u03b2\u03b1\3\2\2\2\u03b2"+
		"\u03b3\3\2\2\2\u03b3\u03b4\3\2\2\2\u03b4\u03cd\7/\2\2\u03b5\u03b6\f!\2"+
		"\2\u03b6\u03b9\7\35\2\2\u03b7\u03ba\5\u00acW\2\u03b8\u03ba\7>\2\2\u03b9"+
		"\u03b7\3\2\2\2\u03b9\u03b8\3\2\2\2\u03ba\u03cd\3\2\2\2\u03bb\u03bc\f "+
		"\2\2\u03bc\u03c5\7\23\2\2\u03bd\u03c2\5\"\22\2\u03be\u03bf\7\24\2\2\u03bf"+
		"\u03c1\5\"\22\2\u03c0\u03be\3\2\2\2\u03c1\u03c4\3\2\2\2\u03c2\u03c0\3"+
		"\2\2\2\u03c2\u03c3\3\2\2\2\u03c3\u03c6\3\2\2\2\u03c4\u03c2\3\2\2\2\u03c5"+
		"\u03bd\3\2\2\2\u03c5\u03c6\3\2\2\2\u03c6\u03c7\3\2\2\2\u03c7\u03cd\7\25"+
		"\2\2\u03c8\u03c9\f\37\2\2\u03c9\u03cd\5$\23\2\u03ca\u03cb\f\34\2\2\u03cb"+
		"\u03cd\t\b\2\2\u03cc\u037b\3\2\2\2\u03cc\u037e\3\2\2\2\u03cc\u0381\3\2"+
		"\2\2\u03cc\u0384\3\2\2\2\u03cc\u0387\3\2\2\2\u03cc\u038a\3\2\2\2\u03cc"+
		"\u038d\3\2\2\2\u03cc\u0390\3\2\2\2\u03cc\u0393\3\2\2\2\u03cc\u0396\3\2"+
		"\2\2\u03cc\u0399\3\2\2\2\u03cc\u039c\3\2\2\2\u03cc\u03a2\3\2\2\2\u03cc"+
		"\u03a5\3\2\2\2\u03cc\u03ab\3\2\2\2\u03cc\u03b5\3\2\2\2\u03cc\u03bb\3\2"+
		"\2\2\u03cc\u03c8\3\2\2\2\u03cc\u03ca\3\2\2\2\u03cd\u03d0\3\2\2\2\u03ce"+
		"\u03cc\3\2\2\2\u03ce\u03cf\3\2\2\2\u03cf\u008b\3\2\2\2\u03d0\u03ce\3\2"+
		"\2\2\u03d1\u03d3\7\33\2\2\u03d2\u03d4\5\u008aF\2\u03d3\u03d2\3\2\2\2\u03d3"+
		"\u03d4\3\2\2\2\u03d4\u03db\3\2\2\2\u03d5\u03d7\7\24\2\2\u03d6\u03d8\5"+
		"\u008aF\2\u03d7\u03d6\3\2\2\2\u03d7\u03d8\3\2\2\2\u03d8\u03da\3\2\2\2"+
		"\u03d9\u03d5\3\2\2\2\u03da\u03dd\3\2\2\2\u03db\u03d9\3\2\2\2\u03db\u03dc"+
		"\3\2\2\2\u03dc\u03de\3\2\2\2\u03dd\u03db\3\2\2\2\u03de\u03df\7\34\2\2"+
		"\u03df\u008d\3\2\2\2\u03e0\u03e1\5\u0088E\2\u03e1\u008f\3\2\2\2\u03e2"+
		"\u03e5\5Z.\2\u03e3\u03e4\7`\2\2\u03e4\u03e6\5\u008aF\2\u03e5\u03e3\3\2"+
		"\2\2\u03e5\u03e6\3\2\2\2\u03e6\u0091\3\2\2\2\u03e7\u03f3\5\u0094K\2\u03e8"+
		"\u03f3\5\u0096L\2\u03e9\u03f3\5\u0098M\2\u03ea\u03f3\5\u00a6T\2\u03eb"+
		"\u03f3\5\u009aN\2\u03ec\u03f3\5\u009cO\2\u03ed\u03f3\5\u00a0Q\2\u03ee"+
		"\u03f3\7a\2\2\u03ef\u03f3\7v\2\2\u03f0\u03f3\7x\2\2\u03f1\u03f3\5\u00a2"+
		"R\2\u03f2\u03e7\3\2\2\2\u03f2\u03e8\3\2\2\2\u03f2\u03e9\3\2\2\2\u03f2"+
		"\u03ea\3\2\2\2\u03f2\u03eb\3\2\2\2\u03f2\u03ec\3\2\2\2\u03f2\u03ed\3\2"+
		"\2\2\u03f2\u03ee\3\2\2\2\u03f2\u03ef\3\2\2\2\u03f2\u03f0\3\2\2\2\u03f2"+
		"\u03f1\3\2\2\2\u03f3\u0093\3\2\2\2\u03f4\u03f8\7\23\2\2\u03f5\u03f7\5"+
		"\u0092J\2\u03f6\u03f5\3\2\2\2\u03f7\u03fa\3\2\2\2\u03f8\u03f6\3\2\2\2"+
		"\u03f8\u03f9\3\2\2\2\u03f9\u03fb\3\2\2\2\u03fa\u03f8\3\2\2\2\u03fb\u03fc"+
		"\7\25\2\2\u03fc\u0095\3\2\2\2\u03fd\u03fe\7b\2\2\u03fe\u0401\7\u0085\2"+
		"\2\u03ff\u0400\7c\2\2\u0400\u0402\5\u00aaV\2\u0401\u03ff\3\2\2\2\u0401"+
		"\u0402\3\2\2\2\u0402\u0411\3\2\2\2\u0403\u0404\7b\2\2\u0404\u0409\7\u0085"+
		"\2\2\u0405\u0406\7\24\2\2\u0406\u0408\7\u0085\2\2\u0407\u0405\3\2\2\2"+
		"\u0408\u040b\3\2\2\2\u0409\u0407\3\2\2\2\u0409\u040a\3\2\2\2\u040a\u040e"+
		"\3\2\2\2\u040b\u0409\3\2\2\2\u040c\u040d\7c\2\2\u040d\u040f\5\u00a6T\2"+
		"\u040e\u040c\3\2\2\2\u040e\u040f\3\2\2\2\u040f\u0411\3\2\2\2\u0410\u03fd"+
		"\3\2\2\2\u0410\u0403\3\2\2\2\u0411\u0097\3\2\2\2\u0412\u0413\5\u00a4S"+
		"\2\u0413\u0414\7c\2\2\u0414\u0415\5\u00aaV\2\u0415\u0421\3\2\2\2\u0416"+
		"\u0419\5\u00a4S\2\u0417\u0418\7\24\2\2\u0418\u041a\5\u00a4S\2\u0419\u0417"+
		"\3\2\2\2\u041a\u041b\3\2\2\2\u041b\u0419\3\2\2\2\u041b\u041c\3\2\2\2\u041c"+
		"\u041d\3\2\2\2\u041d\u041e\7c\2\2\u041e\u041f\5\u00a6T\2\u041f\u0421\3"+
		"\2\2\2\u0420\u0412\3\2\2\2\u0420\u0416\3\2\2\2\u0421\u0099\3\2\2\2\u0422"+
		"\u0423\7\63\2\2\u0423\u0424\5\u00aaV\2\u0424\u0425\5\u0094K\2\u0425\u009b"+
		"\3\2\2\2\u0426\u0427\7-\2\2\u0427\u0428\5\u0094K\2\u0428\u0429\5\u00aa"+
		"V\2\u0429\u042a\5\u0094K\2\u042a\u042b\5\u0094K\2\u042b\u009d\3\2\2\2"+
		"\u042c\u042d\7d\2\2\u042d\u042e\5\u00a8U\2\u042e\u042f\5\u0094K\2\u042f"+
		"\u009f\3\2\2\2\u0430\u0431\7e\2\2\u0431\u043d\5\u00aaV\2\u0432\u0434\5"+
		"\u009eP\2\u0433\u0432\3\2\2\2\u0434\u0435\3\2\2\2\u0435\u0433\3\2\2\2"+
		"\u0435\u0436\3\2\2\2\u0436\u0439\3\2\2\2\u0437\u0438\7f\2\2\u0438\u043a"+
		"\5\u0094K\2\u0439\u0437\3\2\2\2\u0439\u043a\3\2\2\2\u043a\u043e\3\2\2"+
		"\2\u043b\u043c\7f\2\2\u043c\u043e\5\u0094K\2\u043d\u0433\3\2\2\2\u043d"+
		"\u043b\3\2\2\2\u043e\u00a1\3\2\2\2\u043f\u0440\7 \2\2\u0440\u0441\7\u0085"+
		"\2\2\u0441\u044a\7\33\2\2\u0442\u0447\7\u0085\2\2\u0443\u0444\7\24\2\2"+
		"\u0444\u0446\7\u0085\2\2\u0445\u0443\3\2\2\2\u0446\u0449\3\2\2\2\u0447"+
		"\u0445\3\2\2\2\u0447\u0448\3\2\2\2\u0448\u044b\3\2\2\2\u0449\u0447\3\2"+
		"\2\2\u044a\u0442\3\2\2\2\u044a\u044b\3\2\2\2\u044b\u044c\3\2\2\2\u044c"+
		"\u0456\7\34\2\2\u044d\u044e\7g\2\2\u044e\u0453\7\u0085\2\2\u044f\u0450"+
		"\7\24\2\2\u0450\u0452\7\u0085\2\2\u0451\u044f\3\2\2\2\u0452\u0455\3\2"+
		"\2\2\u0453\u0451\3\2\2\2\u0453\u0454\3\2\2\2\u0454\u0457\3\2\2\2\u0455"+
		"\u0453\3\2\2\2\u0456\u044d\3\2\2\2\u0456\u0457\3\2\2\2\u0457\u0458\3\2"+
		"\2\2\u0458\u0459\5\u0094K\2\u0459\u00a3\3\2\2\2\u045a\u045f\7\u0085\2"+
		"\2\u045b\u045c\7\35\2\2\u045c\u045e\7\u0085\2\2\u045d\u045b\3\2\2\2\u045e"+
		"\u0461\3\2\2\2\u045f\u045d\3\2\2\2\u045f\u0460\3\2\2\2\u0460\u00a5\3\2"+
		"\2\2\u0461\u045f\3\2\2\2\u0462\u0463\t\20\2\2\u0463\u046c\7\33\2\2\u0464"+
		"\u0469\5\u00aaV\2\u0465\u0466\7\24\2\2\u0466\u0468\5\u00aaV\2\u0467\u0465"+
		"\3\2\2\2\u0468\u046b\3\2\2\2\u0469\u0467\3\2\2\2\u0469\u046a\3\2\2\2\u046a"+
		"\u046d\3\2\2\2\u046b\u0469\3\2\2\2\u046c\u0464\3\2\2\2\u046c\u046d\3\2"+
		"\2\2\u046d\u046e\3\2\2\2\u046e\u046f\7\34\2\2\u046f\u00a7\3\2\2\2\u0470"+
		"\u0471\t\21\2\2\u0471\u00a9\3\2\2\2\u0472\u0476\5\u00a4S\2\u0473\u0476"+
		"\5\u00a6T\2\u0474\u0476\5\u00a8U\2\u0475\u0472\3\2\2\2\u0475\u0473\3\2"+
		"\2\2\u0475\u0474\3\2\2\2\u0476\u00ab\3\2\2\2\u0477\u0478\7\u0085\2\2\u0478"+
		"\u00ad\3\2\2\2\u0083\u00b9\u00bb\u00c2\u00c9\u00d4\u00d9\u00e1\u00ed\u00f4"+
		"\u00fe\u0106\u010c\u0114\u011a\u0125\u0130\u0135\u0143\u014f\u0152\u015a"+
		"\u015d\u0160\u0169\u016f\u0179\u017b\u0181\u018b\u018d\u0196\u019e\u01a0"+
		"\u01a4\u01a8\u01af\u01b1\u01b6\u01ba\u01c1\u01c3\u01c7\u01cb\u01d5\u01d7"+
		"\u01dc\u01e4\u01f1\u01fe\u0203\u020b\u020e\u0213\u0217\u021a\u0222\u0228"+
		"\u023c\u023f\u0249\u024c\u0250\u0256\u0260\u0263\u026d\u0272\u0276\u027f"+
		"\u0284\u0289\u028e\u0292\u0294\u0298\u029f\u02a7\u02b3\u02c5\u02d1\u02dc"+
		"\u02e2\u02e5\u02e9\u0303\u0309\u030d\u0310\u0316\u0326\u032c\u0334\u033c"+
		"\u0342\u0346\u036c\u0375\u0379\u03a8\u03ae\u03b2\u03b9\u03c2\u03c5\u03cc"+
		"\u03ce\u03d3\u03d7\u03db\u03e5\u03f2\u03f8\u0401\u0409\u040e\u0410\u041b"+
		"\u0420\u0435\u0439\u043d\u0447\u044a\u0453\u0456\u045f\u0469\u046c\u0475";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}