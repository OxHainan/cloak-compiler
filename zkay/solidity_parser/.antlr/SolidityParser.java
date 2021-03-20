// Generated from /cloak-compiler/zkay/solidity_parser/Solidity.g4 by ANTLR 4.8
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
		T__66=67, T__67=68, T__68=69, Uint=70, Int=71, Byte=72, Fixed=73, Ufixed=74, 
		MeKeyword=75, AllKeyword=76, TeeKeyword=77, VersionLiteral=78, BooleanLiteral=79, 
		DecimalNumber=80, HexNumber=81, ReservedKeyword=82, AnonymousKeyword=83, 
		BreakKeyword=84, ConstantKeyword=85, ContinueKeyword=86, ExternalKeyword=87, 
		IndexedKeyword=88, InternalKeyword=89, PayableKeyword=90, PrivateKeyword=91, 
		PublicKeyword=92, PureKeyword=93, ViewKeyword=94, FinalKeyword=95, Identifier=96, 
		StringLiteral=97, WS=98, COMMENT=99, LINE_COMMENT=100;
	public static final int
		RULE_sourceUnit = 0, RULE_pragmaDirective = 1, RULE_pragma = 2, RULE_version = 3, 
		RULE_versionOperator = 4, RULE_versionConstraint = 5, RULE_contractDefinition = 6, 
		RULE_contractPart = 7, RULE_stateVariableDeclaration = 8, RULE_constructorDefinition = 9, 
		RULE_functionDefinition = 10, RULE_returnParameters = 11, RULE_modifierList = 12, 
		RULE_modifier = 13, RULE_parameterList = 14, RULE_parameter = 15, RULE_enumValue = 16, 
		RULE_enumDefinition = 17, RULE_variableDeclaration = 18, RULE_typeName = 19, 
		RULE_userDefinedTypeName = 20, RULE_mapping = 21, RULE_stateMutability = 22, 
		RULE_block = 23, RULE_statement = 24, RULE_expressionStatement = 25, RULE_ifStatement = 26, 
		RULE_whileStatement = 27, RULE_simpleStatement = 28, RULE_forStatement = 29, 
		RULE_doWhileStatement = 30, RULE_continueStatement = 31, RULE_breakStatement = 32, 
		RULE_returnStatement = 33, RULE_variableDeclarationStatement = 34, RULE_elementaryTypeName = 35, 
		RULE_expression = 36, RULE_functionCallArguments = 37, RULE_tupleExpression = 38, 
		RULE_elementaryTypeNameExpression = 39, RULE_numberLiteral = 40, RULE_annotatedTypeName = 41, 
		RULE_identifier = 42;
	private static String[] makeRuleNames() {
		return new String[] {
			"sourceUnit", "pragmaDirective", "pragma", "version", "versionOperator", 
			"versionConstraint", "contractDefinition", "contractPart", "stateVariableDeclaration", 
			"constructorDefinition", "functionDefinition", "returnParameters", "modifierList", 
			"modifier", "parameterList", "parameter", "enumValue", "enumDefinition", 
			"variableDeclaration", "typeName", "userDefinedTypeName", "mapping", 
			"stateMutability", "block", "statement", "expressionStatement", "ifStatement", 
			"whileStatement", "simpleStatement", "forStatement", "doWhileStatement", 
			"continueStatement", "breakStatement", "returnStatement", "variableDeclarationStatement", 
			"elementaryTypeName", "expression", "functionCallArguments", "tupleExpression", 
			"elementaryTypeNameExpression", "numberLiteral", "annotatedTypeName", 
			"identifier"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'pragma'", "';'", "'cloak'", "'solidity'", "'^'", "'~'", "'>='", 
			"'>'", "'<'", "'<='", "'='", "'contract'", "'{'", "'}'", "'constructor'", 
			"'function'", "'returns'", "'('", "','", "')'", "'enum'", "'.'", "'mapping'", 
			"'!'", "'=>'", "'if'", "'else'", "'while'", "'for'", "'do'", "'return'", 
			"'address'", "'address payable'", "'bool'", "'var'", "'string'", "'bytes'", 
			"'byte'", "'++'", "'--'", "'['", "']'", "'+'", "'-'", "'**'", "'*'", 
			"'/'", "'%'", "'<<'", "'>>'", "'&'", "'|'", "'=='", "'!='", "'&&'", "'||'", 
			"'?'", "':'", "'|='", "'^='", "'&='", "'<<='", "'>>='", "'+='", "'-='", 
			"'*='", "'/='", "'%='", "'@'", null, null, null, null, null, "'me'", 
			"'all'", "'tee'", null, null, null, null, null, "'anonymous'", "'break'", 
			"'constant'", "'continue'", "'external'", "'indexed'", "'internal'", 
			"'payable'", "'private'", "'public'", "'pure'", "'view'", "'final'"
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
			null, null, null, null, null, null, null, null, null, null, "Uint", "Int", 
			"Byte", "Fixed", "Ufixed", "MeKeyword", "AllKeyword", "TeeKeyword", "VersionLiteral", 
			"BooleanLiteral", "DecimalNumber", "HexNumber", "ReservedKeyword", "AnonymousKeyword", 
			"BreakKeyword", "ConstantKeyword", "ContinueKeyword", "ExternalKeyword", 
			"IndexedKeyword", "InternalKeyword", "PayableKeyword", "PrivateKeyword", 
			"PublicKeyword", "PureKeyword", "ViewKeyword", "FinalKeyword", "Identifier", 
			"StringLiteral", "WS", "COMMENT", "LINE_COMMENT"
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
		public PragmaDirectiveContext pragma_directive;
		public ContractDefinitionContext contractDefinition;
		public List<ContractDefinitionContext> contracts = new ArrayList<ContractDefinitionContext>();
		public TerminalNode EOF() { return getToken(SolidityParser.EOF, 0); }
		public PragmaDirectiveContext pragmaDirective() {
			return getRuleContext(PragmaDirectiveContext.class,0);
		}
		public List<ContractDefinitionContext> contractDefinition() {
			return getRuleContexts(ContractDefinitionContext.class);
		}
		public ContractDefinitionContext contractDefinition(int i) {
			return getRuleContext(ContractDefinitionContext.class,i);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			((SourceUnitContext)_localctx).pragma_directive = pragmaDirective();
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(87);
				((SourceUnitContext)_localctx).contractDefinition = contractDefinition();
				((SourceUnitContext)_localctx).contracts.add(((SourceUnitContext)_localctx).contractDefinition);
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(93);
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
		public PragmaContext pragma() {
			return getRuleContext(PragmaContext.class,0);
		}
		public PragmaDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragmaDirective; }
	}

	public final PragmaDirectiveContext pragmaDirective() throws RecognitionException {
		PragmaDirectiveContext _localctx = new PragmaDirectiveContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_pragmaDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__0);
			setState(96);
			pragma();
			setState(97);
			match(T__1);
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

	public static class PragmaContext extends ParserRuleContext {
		public PragmaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragma; }
	 
		public PragmaContext() { }
		public void copyFrom(PragmaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VersionPragmaContext extends PragmaContext {
		public Token name;
		public VersionContext ver;
		public VersionContext version() {
			return getRuleContext(VersionContext.class,0);
		}
		public VersionPragmaContext(PragmaContext ctx) { copyFrom(ctx); }
	}

	public final PragmaContext pragma() throws RecognitionException {
		PragmaContext _localctx = new PragmaContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_pragma);
		int _la;
		try {
			_localctx = new VersionPragmaContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(99);
			((VersionPragmaContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__2 || _la==T__3) ) {
				((VersionPragmaContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(100);
			((VersionPragmaContext)_localctx).ver = version();
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
			setState(102);
			versionConstraint();
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10))) != 0) || _la==VersionLiteral) {
				{
				setState(103);
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
			setState(106);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10))) != 0)) ) {
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
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10))) != 0)) {
				{
				setState(108);
				versionOperator();
				}
			}

			setState(111);
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

	public static class ContractDefinitionContext extends ParserRuleContext {
		public IdentifierContext idf;
		public ContractPartContext contractPart;
		public List<ContractPartContext> parts = new ArrayList<ContractPartContext>();
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<ContractPartContext> contractPart() {
			return getRuleContexts(ContractPartContext.class);
		}
		public ContractPartContext contractPart(int i) {
			return getRuleContext(ContractPartContext.class,i);
		}
		public ContractDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_contractDefinition; }
	}

	public final ContractDefinitionContext contractDefinition() throws RecognitionException {
		ContractDefinitionContext _localctx = new ContractDefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_contractDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(113);
			match(T__11);
			}
			setState(114);
			((ContractDefinitionContext)_localctx).idf = identifier();
			setState(115);
			match(T__12);
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__15) | (1L << T__20) | (1L << T__22) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (FinalKeyword - 70)) | (1L << (Identifier - 70)))) != 0)) {
				{
				{
				setState(116);
				((ContractDefinitionContext)_localctx).contractPart = contractPart();
				((ContractDefinitionContext)_localctx).parts.add(((ContractDefinitionContext)_localctx).contractPart);
				}
				}
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(122);
			match(T__13);
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

	public static class ContractPartContext extends ParserRuleContext {
		public StateVariableDeclarationContext stateVariableDeclaration() {
			return getRuleContext(StateVariableDeclarationContext.class,0);
		}
		public ConstructorDefinitionContext constructorDefinition() {
			return getRuleContext(ConstructorDefinitionContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public EnumDefinitionContext enumDefinition() {
			return getRuleContext(EnumDefinitionContext.class,0);
		}
		public ContractPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_contractPart; }
	}

	public final ContractPartContext contractPart() throws RecognitionException {
		ContractPartContext _localctx = new ContractPartContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_contractPart);
		try {
			setState(128);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__22:
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
			case FinalKeyword:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				stateVariableDeclaration();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				constructorDefinition();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				functionDefinition();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 4);
				{
				setState(127);
				enumDefinition();
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

	public static class StateVariableDeclarationContext extends ParserRuleContext {
		public Token FinalKeyword;
		public List<Token> keywords = new ArrayList<Token>();
		public AnnotatedTypeNameContext annotated_type;
		public Token ConstantKeyword;
		public IdentifierContext idf;
		public ExpressionContext expr;
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<TerminalNode> FinalKeyword() { return getTokens(SolidityParser.FinalKeyword); }
		public TerminalNode FinalKeyword(int i) {
			return getToken(SolidityParser.FinalKeyword, i);
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
		enterRule(_localctx, 16, RULE_stateVariableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FinalKeyword) {
				{
				{
				setState(130);
				((StateVariableDeclarationContext)_localctx).FinalKeyword = match(FinalKeyword);
				((StateVariableDeclarationContext)_localctx).keywords.add(((StateVariableDeclarationContext)_localctx).FinalKeyword);
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(136);
			((StateVariableDeclarationContext)_localctx).annotated_type = annotatedTypeName();
			setState(140);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ConstantKeyword) {
				{
				{
				setState(137);
				((StateVariableDeclarationContext)_localctx).ConstantKeyword = match(ConstantKeyword);
				((StateVariableDeclarationContext)_localctx).keywords.add(((StateVariableDeclarationContext)_localctx).ConstantKeyword);
				}
				}
				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(143);
			((StateVariableDeclarationContext)_localctx).idf = identifier();
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(144);
				match(T__10);
				setState(145);
				((StateVariableDeclarationContext)_localctx).expr = expression(0);
				}
			}

			setState(148);
			match(T__1);
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
		public ModifierListContext modifiers;
		public BlockContext body;
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public ModifierListContext modifierList() {
			return getRuleContext(ModifierListContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstructorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDefinition; }
	}

	public final ConstructorDefinitionContext constructorDefinition() throws RecognitionException {
		ConstructorDefinitionContext _localctx = new ConstructorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_constructorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			match(T__14);
			setState(151);
			((ConstructorDefinitionContext)_localctx).parameters = parameterList();
			setState(152);
			((ConstructorDefinitionContext)_localctx).modifiers = modifierList();
			setState(153);
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
		public IdentifierContext idf;
		public ParameterListContext parameters;
		public ModifierListContext modifiers;
		public ReturnParametersContext return_parameters;
		public BlockContext body;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public ModifierListContext modifierList() {
			return getRuleContext(ModifierListContext.class,0);
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
		enterRule(_localctx, 20, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(T__15);
			setState(156);
			((FunctionDefinitionContext)_localctx).idf = identifier();
			setState(157);
			((FunctionDefinitionContext)_localctx).parameters = parameterList();
			setState(158);
			((FunctionDefinitionContext)_localctx).modifiers = modifierList();
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__16) {
				{
				setState(159);
				((FunctionDefinitionContext)_localctx).return_parameters = returnParameters();
				}
			}

			setState(162);
			((FunctionDefinitionContext)_localctx).body = block();
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
		enterRule(_localctx, 22, RULE_returnParameters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(T__16);
			setState(165);
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

	public static class ModifierListContext extends ParserRuleContext {
		public ModifierContext modifier;
		public List<ModifierContext> modifiers = new ArrayList<ModifierContext>();
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public ModifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifierList; }
	}

	public final ModifierListContext modifierList() throws RecognitionException {
		ModifierListContext _localctx = new ModifierListContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_modifierList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 89)) & ~0x3f) == 0 && ((1L << (_la - 89)) & ((1L << (InternalKeyword - 89)) | (1L << (PayableKeyword - 89)) | (1L << (PrivateKeyword - 89)) | (1L << (PublicKeyword - 89)) | (1L << (PureKeyword - 89)) | (1L << (ViewKeyword - 89)))) != 0)) {
				{
				{
				setState(167);
				((ModifierListContext)_localctx).modifier = modifier();
				((ModifierListContext)_localctx).modifiers.add(((ModifierListContext)_localctx).modifier);
				}
				}
				setState(172);
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

	public static class ModifierContext extends ParserRuleContext {
		public StateMutabilityContext stateMutability() {
			return getRuleContext(StateMutabilityContext.class,0);
		}
		public TerminalNode PublicKeyword() { return getToken(SolidityParser.PublicKeyword, 0); }
		public TerminalNode InternalKeyword() { return getToken(SolidityParser.InternalKeyword, 0); }
		public TerminalNode PrivateKeyword() { return getToken(SolidityParser.PrivateKeyword, 0); }
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_modifier);
		try {
			setState(177);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PayableKeyword:
			case PureKeyword:
			case ViewKeyword:
				enterOuterAlt(_localctx, 1);
				{
				setState(173);
				stateMutability();
				}
				break;
			case PublicKeyword:
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				match(PublicKeyword);
				}
				break;
			case InternalKeyword:
				enterOuterAlt(_localctx, 3);
				{
				setState(175);
				match(InternalKeyword);
				}
				break;
			case PrivateKeyword:
				enterOuterAlt(_localctx, 4);
				{
				setState(176);
				match(PrivateKeyword);
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
		enterRule(_localctx, 28, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(T__17);
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (FinalKeyword - 70)) | (1L << (Identifier - 70)))) != 0)) {
				{
				setState(180);
				((ParameterListContext)_localctx).parameter = parameter();
				((ParameterListContext)_localctx).params.add(((ParameterListContext)_localctx).parameter);
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__18) {
					{
					{
					setState(181);
					match(T__18);
					setState(182);
					((ParameterListContext)_localctx).parameter = parameter();
					((ParameterListContext)_localctx).params.add(((ParameterListContext)_localctx).parameter);
					}
					}
					setState(187);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(190);
			match(T__19);
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
		public IdentifierContext idf;
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public TerminalNode FinalKeyword() { return getToken(SolidityParser.FinalKeyword, 0); }
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
		enterRule(_localctx, 30, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FinalKeyword) {
				{
				setState(192);
				((ParameterContext)_localctx).FinalKeyword = match(FinalKeyword);
				((ParameterContext)_localctx).keywords.add(((ParameterContext)_localctx).FinalKeyword);
				}
			}

			setState(195);
			((ParameterContext)_localctx).annotated_type = annotatedTypeName();
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(196);
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
		enterRule(_localctx, 32, RULE_enumValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
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
		enterRule(_localctx, 34, RULE_enumDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(T__20);
			setState(202);
			((EnumDefinitionContext)_localctx).idf = identifier();
			setState(203);
			match(T__12);
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(204);
				((EnumDefinitionContext)_localctx).enumValue = enumValue();
				((EnumDefinitionContext)_localctx).values.add(((EnumDefinitionContext)_localctx).enumValue);
				}
			}

			setState(211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(207);
				match(T__18);
				setState(208);
				((EnumDefinitionContext)_localctx).enumValue = enumValue();
				((EnumDefinitionContext)_localctx).values.add(((EnumDefinitionContext)_localctx).enumValue);
				}
				}
				setState(213);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(214);
			match(T__13);
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
		public IdentifierContext idf;
		public AnnotatedTypeNameContext annotatedTypeName() {
			return getRuleContext(AnnotatedTypeNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode FinalKeyword() { return getToken(SolidityParser.FinalKeyword, 0); }
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FinalKeyword) {
				{
				setState(216);
				((VariableDeclarationContext)_localctx).FinalKeyword = match(FinalKeyword);
				((VariableDeclarationContext)_localctx).keywords.add(((VariableDeclarationContext)_localctx).FinalKeyword);
				}
			}

			setState(219);
			((VariableDeclarationContext)_localctx).annotated_type = annotatedTypeName();
			setState(220);
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
		public ElementaryTypeNameContext elementaryTypeName() {
			return getRuleContext(ElementaryTypeNameContext.class,0);
		}
		public UserDefinedTypeNameContext userDefinedTypeName() {
			return getRuleContext(UserDefinedTypeNameContext.class,0);
		}
		public MappingContext mapping() {
			return getRuleContext(MappingContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_typeName);
		try {
			setState(225);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
			case Uint:
			case Int:
			case Byte:
			case Fixed:
			case Ufixed:
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				elementaryTypeName();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
				userDefinedTypeName();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 3);
				{
				setState(224);
				mapping();
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
		enterRule(_localctx, 40, RULE_userDefinedTypeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			((UserDefinedTypeNameContext)_localctx).identifier = identifier();
			((UserDefinedTypeNameContext)_localctx).names.add(((UserDefinedTypeNameContext)_localctx).identifier);
			setState(232);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(228);
				match(T__21);
				setState(229);
				((UserDefinedTypeNameContext)_localctx).identifier = identifier();
				((UserDefinedTypeNameContext)_localctx).names.add(((UserDefinedTypeNameContext)_localctx).identifier);
				}
				}
				setState(234);
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
		enterRule(_localctx, 42, RULE_mapping);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(T__22);
			setState(236);
			match(T__17);
			setState(237);
			((MappingContext)_localctx).key_type = elementaryTypeName();
			setState(240);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__23) {
				{
				setState(238);
				match(T__23);
				setState(239);
				((MappingContext)_localctx).key_label = identifier();
				}
			}

			setState(242);
			match(T__24);
			setState(243);
			((MappingContext)_localctx).value_type = annotatedTypeName();
			setState(244);
			match(T__19);
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
		public TerminalNode PayableKeyword() { return getToken(SolidityParser.PayableKeyword, 0); }
		public TerminalNode PureKeyword() { return getToken(SolidityParser.PureKeyword, 0); }
		public TerminalNode ViewKeyword() { return getToken(SolidityParser.ViewKeyword, 0); }
		public StateMutabilityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stateMutability; }
	}

	public final StateMutabilityContext stateMutability() throws RecognitionException {
		StateMutabilityContext _localctx = new StateMutabilityContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_stateMutability);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			_la = _input.LA(1);
			if ( !(((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & ((1L << (PayableKeyword - 90)) | (1L << (PureKeyword - 90)) | (1L << (ViewKeyword - 90)))) != 0)) ) {
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
		enterRule(_localctx, 46, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			match(T__12);
			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__12) | (1L << T__17) | (1L << T__22) | (1L << T__23) | (1L << T__25) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__42) | (1L << T__43))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (MeKeyword - 70)) | (1L << (AllKeyword - 70)) | (1L << (TeeKeyword - 70)) | (1L << (BooleanLiteral - 70)) | (1L << (DecimalNumber - 70)) | (1L << (HexNumber - 70)) | (1L << (BreakKeyword - 70)) | (1L << (ContinueKeyword - 70)) | (1L << (FinalKeyword - 70)) | (1L << (Identifier - 70)) | (1L << (StringLiteral - 70)))) != 0)) {
				{
				{
				setState(249);
				((BlockContext)_localctx).statement = statement();
				((BlockContext)_localctx).statements.add(((BlockContext)_localctx).statement);
				}
				}
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(255);
			match(T__13);
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
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
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
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public SimpleStatementContext simpleStatement() {
			return getRuleContext(SimpleStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_statement);
		try {
			setState(266);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__25:
				enterOuterAlt(_localctx, 1);
				{
				setState(257);
				ifStatement();
				}
				break;
			case T__27:
				enterOuterAlt(_localctx, 2);
				{
				setState(258);
				whileStatement();
				}
				break;
			case T__28:
				enterOuterAlt(_localctx, 3);
				{
				setState(259);
				forStatement();
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 4);
				{
				setState(260);
				block();
				}
				break;
			case T__29:
				enterOuterAlt(_localctx, 5);
				{
				setState(261);
				doWhileStatement();
				}
				break;
			case ContinueKeyword:
				enterOuterAlt(_localctx, 6);
				{
				setState(262);
				continueStatement();
				}
				break;
			case BreakKeyword:
				enterOuterAlt(_localctx, 7);
				{
				setState(263);
				breakStatement();
				}
				break;
			case T__30:
				enterOuterAlt(_localctx, 8);
				{
				setState(264);
				returnStatement();
				}
				break;
			case T__5:
			case T__17:
			case T__22:
			case T__23:
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
			case T__39:
			case T__42:
			case T__43:
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
			case FinalKeyword:
			case Identifier:
			case StringLiteral:
				enterOuterAlt(_localctx, 9);
				{
				setState(265);
				simpleStatement();
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
		enterRule(_localctx, 50, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			((ExpressionStatementContext)_localctx).expr = expression(0);
			setState(269);
			match(T__1);
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
		enterRule(_localctx, 52, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(T__25);
			setState(272);
			match(T__17);
			setState(273);
			((IfStatementContext)_localctx).condition = expression(0);
			setState(274);
			match(T__19);
			setState(275);
			((IfStatementContext)_localctx).then_branch = statement();
			setState(278);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(276);
				match(T__26);
				setState(277);
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
		enterRule(_localctx, 54, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(T__27);
			setState(281);
			match(T__17);
			setState(282);
			((WhileStatementContext)_localctx).condition = expression(0);
			setState(283);
			match(T__19);
			setState(284);
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
		public SimpleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStatement; }
	}

	public final SimpleStatementContext simpleStatement() throws RecognitionException {
		SimpleStatementContext _localctx = new SimpleStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_simpleStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(286);
				variableDeclarationStatement();
				}
				break;
			case 2:
				{
				setState(287);
				expressionStatement();
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
		enterRule(_localctx, 58, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(T__28);
			setState(291);
			match(T__17);
			setState(294);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
			case T__17:
			case T__22:
			case T__23:
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
			case T__39:
			case T__42:
			case T__43:
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
			case FinalKeyword:
			case Identifier:
			case StringLiteral:
				{
				setState(292);
				((ForStatementContext)_localctx).init = simpleStatement();
				}
				break;
			case T__1:
				{
				setState(293);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(297);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__17) | (1L << T__23) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__42) | (1L << T__43))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (MeKeyword - 70)) | (1L << (AllKeyword - 70)) | (1L << (TeeKeyword - 70)) | (1L << (BooleanLiteral - 70)) | (1L << (DecimalNumber - 70)) | (1L << (HexNumber - 70)) | (1L << (Identifier - 70)) | (1L << (StringLiteral - 70)))) != 0)) {
				{
				setState(296);
				((ForStatementContext)_localctx).condition = expression(0);
				}
			}

			setState(299);
			match(T__1);
			setState(301);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__17) | (1L << T__23) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__42) | (1L << T__43))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (MeKeyword - 70)) | (1L << (AllKeyword - 70)) | (1L << (TeeKeyword - 70)) | (1L << (BooleanLiteral - 70)) | (1L << (DecimalNumber - 70)) | (1L << (HexNumber - 70)) | (1L << (Identifier - 70)) | (1L << (StringLiteral - 70)))) != 0)) {
				{
				setState(300);
				((ForStatementContext)_localctx).update = expression(0);
				}
			}

			setState(303);
			match(T__19);
			setState(304);
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
		enterRule(_localctx, 60, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			match(T__29);
			setState(307);
			((DoWhileStatementContext)_localctx).body = statement();
			setState(308);
			match(T__27);
			setState(309);
			match(T__17);
			setState(310);
			((DoWhileStatementContext)_localctx).condition = expression(0);
			setState(311);
			match(T__19);
			setState(312);
			match(T__1);
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
		enterRule(_localctx, 62, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			match(ContinueKeyword);
			setState(315);
			match(T__1);
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
		enterRule(_localctx, 64, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			match(BreakKeyword);
			setState(318);
			match(T__1);
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
		enterRule(_localctx, 66, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			match(T__30);
			setState(322);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__17) | (1L << T__23) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__42) | (1L << T__43))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (MeKeyword - 70)) | (1L << (AllKeyword - 70)) | (1L << (TeeKeyword - 70)) | (1L << (BooleanLiteral - 70)) | (1L << (DecimalNumber - 70)) | (1L << (HexNumber - 70)) | (1L << (Identifier - 70)) | (1L << (StringLiteral - 70)))) != 0)) {
				{
				setState(321);
				((ReturnStatementContext)_localctx).expr = expression(0);
				}
			}

			setState(324);
			match(T__1);
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
		enterRule(_localctx, 68, RULE_variableDeclarationStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			((VariableDeclarationStatementContext)_localctx).variable_declaration = variableDeclaration();
			setState(329);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(327);
				match(T__10);
				setState(328);
				((VariableDeclarationStatementContext)_localctx).expr = expression(0);
				}
			}

			setState(331);
			match(T__1);
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
		enterRule(_localctx, 70, RULE_elementaryTypeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			((ElementaryTypeNameContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !(((((_la - 32)) & ~0x3f) == 0 && ((1L << (_la - 32)) & ((1L << (T__31 - 32)) | (1L << (T__32 - 32)) | (1L << (T__33 - 32)) | (1L << (T__34 - 32)) | (1L << (T__35 - 32)) | (1L << (T__36 - 32)) | (1L << (T__37 - 32)) | (1L << (Uint - 32)) | (1L << (Int - 32)) | (1L << (Byte - 32)) | (1L << (Fixed - 32)) | (1L << (Ufixed - 32)))) != 0)) ) {
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
	public static class ParenthesisExprContext extends ExpressionContext {
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenthesisExprContext(ExpressionContext ctx) { copyFrom(ctx); }
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
	public static class SignExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SignExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NumberLiteralExprContext extends ExpressionContext {
		public NumberLiteralContext numberLiteral() {
			return getRuleContext(NumberLiteralContext.class,0);
		}
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
		public ExpressionContext func;
		public FunctionCallArgumentsContext args;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionCallArgumentsContext functionCallArguments() {
			return getRuleContext(FunctionCallArgumentsContext.class,0);
		}
		public FunctionCallExprContext(ExpressionContext ctx) { copyFrom(ctx); }
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
	public static class PostCrementExprContext extends ExpressionContext {
		public ExpressionContext expr;
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PostCrementExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PrimitiveCastExprContext extends ExpressionContext {
		public ElementaryTypeNameContext elem_type;
		public ExpressionContext expr;
		public ElementaryTypeNameContext elementaryTypeName() {
			return getRuleContext(ElementaryTypeNameContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrimitiveCastExprContext(ExpressionContext ctx) { copyFrom(ctx); }
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
		public IdentifierContext member;
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

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				_localctx = new MeExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(336);
				match(MeKeyword);
				}
				break;
			case 2:
				{
				_localctx = new AllExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(337);
				match(AllKeyword);
				}
				break;
			case 3:
				{
				_localctx = new TeeExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(338);
				match(TeeKeyword);
				}
				break;
			case 4:
				{
				_localctx = new PrimitiveCastExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(339);
				((PrimitiveCastExprContext)_localctx).elem_type = elementaryTypeName();
				setState(340);
				match(T__17);
				setState(341);
				((PrimitiveCastExprContext)_localctx).expr = expression(0);
				setState(342);
				match(T__19);
				}
				break;
			case 5:
				{
				_localctx = new ParenthesisExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(344);
				match(T__17);
				setState(345);
				((ParenthesisExprContext)_localctx).expr = expression(0);
				setState(346);
				match(T__19);
				}
				break;
			case 6:
				{
				_localctx = new PreCrementExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(348);
				((PreCrementExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__38 || _la==T__39) ) {
					((PreCrementExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(349);
				((PreCrementExprContext)_localctx).expr = expression(22);
				}
				break;
			case 7:
				{
				_localctx = new SignExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(350);
				((SignExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__42 || _la==T__43) ) {
					((SignExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(351);
				((SignExprContext)_localctx).expr = expression(21);
				}
				break;
			case 8:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(352);
				match(T__23);
				setState(353);
				((NotExprContext)_localctx).expr = expression(20);
				}
				break;
			case 9:
				{
				_localctx = new BitwiseNotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(354);
				match(T__5);
				setState(355);
				((BitwiseNotExprContext)_localctx).expr = expression(19);
				}
				break;
			case 10:
				{
				_localctx = new BooleanLiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(356);
				match(BooleanLiteral);
				}
				break;
			case 11:
				{
				_localctx = new NumberLiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(357);
				numberLiteral();
				}
				break;
			case 12:
				{
				_localctx = new StringLiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(358);
				match(StringLiteral);
				}
				break;
			case 13:
				{
				_localctx = new TupleExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(359);
				((TupleExprContext)_localctx).expr = tupleExpression();
				}
				break;
			case 14:
				{
				_localctx = new IdentifierExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(360);
				((IdentifierExprContext)_localctx).idf = identifier();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(422);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(420);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new PowExprContext(new ExpressionContext(_parentctx, _parentState));
						((PowExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(363);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(364);
						((PowExprContext)_localctx).op = match(T__44);
						setState(365);
						((PowExprContext)_localctx).rhs = expression(19);
						}
						break;
					case 2:
						{
						_localctx = new MultDivModExprContext(new ExpressionContext(_parentctx, _parentState));
						((MultDivModExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(366);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(367);
						((MultDivModExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__45) | (1L << T__46) | (1L << T__47))) != 0)) ) {
							((MultDivModExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(368);
						((MultDivModExprContext)_localctx).rhs = expression(18);
						}
						break;
					case 3:
						{
						_localctx = new PlusMinusExprContext(new ExpressionContext(_parentctx, _parentState));
						((PlusMinusExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(369);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(370);
						((PlusMinusExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__42 || _la==T__43) ) {
							((PlusMinusExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(371);
						((PlusMinusExprContext)_localctx).rhs = expression(17);
						}
						break;
					case 4:
						{
						_localctx = new BitShiftExprContext(new ExpressionContext(_parentctx, _parentState));
						((BitShiftExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(372);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(373);
						((BitShiftExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__48 || _la==T__49) ) {
							((BitShiftExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(374);
						((BitShiftExprContext)_localctx).rhs = expression(16);
						}
						break;
					case 5:
						{
						_localctx = new BitwiseAndExprContext(new ExpressionContext(_parentctx, _parentState));
						((BitwiseAndExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(375);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(376);
						((BitwiseAndExprContext)_localctx).op = match(T__50);
						setState(377);
						((BitwiseAndExprContext)_localctx).rhs = expression(15);
						}
						break;
					case 6:
						{
						_localctx = new BitwiseXorExprContext(new ExpressionContext(_parentctx, _parentState));
						((BitwiseXorExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(378);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(379);
						((BitwiseXorExprContext)_localctx).op = match(T__4);
						setState(380);
						((BitwiseXorExprContext)_localctx).rhs = expression(14);
						}
						break;
					case 7:
						{
						_localctx = new BitwiseOrExprContext(new ExpressionContext(_parentctx, _parentState));
						((BitwiseOrExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(381);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(382);
						((BitwiseOrExprContext)_localctx).op = match(T__51);
						setState(383);
						((BitwiseOrExprContext)_localctx).rhs = expression(13);
						}
						break;
					case 8:
						{
						_localctx = new CompExprContext(new ExpressionContext(_parentctx, _parentState));
						((CompExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(384);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(385);
						((CompExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9))) != 0)) ) {
							((CompExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(386);
						((CompExprContext)_localctx).rhs = expression(12);
						}
						break;
					case 9:
						{
						_localctx = new EqExprContext(new ExpressionContext(_parentctx, _parentState));
						((EqExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(387);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(388);
						((EqExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__52 || _la==T__53) ) {
							((EqExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(389);
						((EqExprContext)_localctx).rhs = expression(11);
						}
						break;
					case 10:
						{
						_localctx = new AndExprContext(new ExpressionContext(_parentctx, _parentState));
						((AndExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(390);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(391);
						((AndExprContext)_localctx).op = match(T__54);
						setState(392);
						((AndExprContext)_localctx).rhs = expression(10);
						}
						break;
					case 11:
						{
						_localctx = new OrExprContext(new ExpressionContext(_parentctx, _parentState));
						((OrExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(393);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(394);
						((OrExprContext)_localctx).op = match(T__55);
						setState(395);
						((OrExprContext)_localctx).rhs = expression(9);
						}
						break;
					case 12:
						{
						_localctx = new IteExprContext(new ExpressionContext(_parentctx, _parentState));
						((IteExprContext)_localctx).cond = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(396);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(397);
						match(T__56);
						setState(398);
						((IteExprContext)_localctx).then_expr = expression(0);
						setState(399);
						match(T__57);
						setState(400);
						((IteExprContext)_localctx).else_expr = expression(8);
						}
						break;
					case 13:
						{
						_localctx = new AssignmentExprContext(new ExpressionContext(_parentctx, _parentState));
						((AssignmentExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(402);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(403);
						((AssignmentExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (T__10 - 11)) | (1L << (T__58 - 11)) | (1L << (T__59 - 11)) | (1L << (T__60 - 11)) | (1L << (T__61 - 11)) | (1L << (T__62 - 11)) | (1L << (T__63 - 11)) | (1L << (T__64 - 11)) | (1L << (T__65 - 11)) | (1L << (T__66 - 11)) | (1L << (T__67 - 11)))) != 0)) ) {
							((AssignmentExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(404);
						((AssignmentExprContext)_localctx).rhs = expression(7);
						}
						break;
					case 14:
						{
						_localctx = new PostCrementExprContext(new ExpressionContext(_parentctx, _parentState));
						((PostCrementExprContext)_localctx).expr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(405);
						if (!(precpred(_ctx, 28))) throw new FailedPredicateException(this, "precpred(_ctx, 28)");
						setState(406);
						((PostCrementExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__38 || _la==T__39) ) {
							((PostCrementExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 15:
						{
						_localctx = new IndexExprContext(new ExpressionContext(_parentctx, _parentState));
						((IndexExprContext)_localctx).arr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(407);
						if (!(precpred(_ctx, 27))) throw new FailedPredicateException(this, "precpred(_ctx, 27)");
						setState(408);
						match(T__40);
						setState(409);
						((IndexExprContext)_localctx).index = expression(0);
						setState(410);
						match(T__41);
						}
						break;
					case 16:
						{
						_localctx = new FunctionCallExprContext(new ExpressionContext(_parentctx, _parentState));
						((FunctionCallExprContext)_localctx).func = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(412);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(413);
						match(T__17);
						setState(414);
						((FunctionCallExprContext)_localctx).args = functionCallArguments();
						setState(415);
						match(T__19);
						}
						break;
					case 17:
						{
						_localctx = new MemberAccessExprContext(new ExpressionContext(_parentctx, _parentState));
						((MemberAccessExprContext)_localctx).expr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(417);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(418);
						match(T__21);
						setState(419);
						((MemberAccessExprContext)_localctx).member = identifier();
						}
						break;
					}
					} 
				}
				setState(424);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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

	public static class FunctionCallArgumentsContext extends ParserRuleContext {
		public ExpressionContext expression;
		public List<ExpressionContext> exprs = new ArrayList<ExpressionContext>();
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FunctionCallArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCallArguments; }
	}

	public final FunctionCallArgumentsContext functionCallArguments() throws RecognitionException {
		FunctionCallArgumentsContext _localctx = new FunctionCallArgumentsContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_functionCallArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(433);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__17) | (1L << T__23) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__42) | (1L << T__43))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (MeKeyword - 70)) | (1L << (AllKeyword - 70)) | (1L << (TeeKeyword - 70)) | (1L << (BooleanLiteral - 70)) | (1L << (DecimalNumber - 70)) | (1L << (HexNumber - 70)) | (1L << (Identifier - 70)) | (1L << (StringLiteral - 70)))) != 0)) {
				{
				setState(425);
				((FunctionCallArgumentsContext)_localctx).expression = expression(0);
				((FunctionCallArgumentsContext)_localctx).exprs.add(((FunctionCallArgumentsContext)_localctx).expression);
				setState(430);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__18) {
					{
					{
					setState(426);
					match(T__18);
					setState(427);
					((FunctionCallArgumentsContext)_localctx).expression = expression(0);
					((FunctionCallArgumentsContext)_localctx).exprs.add(((FunctionCallArgumentsContext)_localctx).expression);
					}
					}
					setState(432);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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
		enterRule(_localctx, 76, RULE_tupleExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(435);
			match(T__17);
			{
			setState(437);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__17) | (1L << T__23) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__42) | (1L << T__43))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (MeKeyword - 70)) | (1L << (AllKeyword - 70)) | (1L << (TeeKeyword - 70)) | (1L << (BooleanLiteral - 70)) | (1L << (DecimalNumber - 70)) | (1L << (HexNumber - 70)) | (1L << (Identifier - 70)) | (1L << (StringLiteral - 70)))) != 0)) {
				{
				setState(436);
				expression(0);
				}
			}

			setState(445);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(439);
				match(T__18);
				setState(441);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__17) | (1L << T__23) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__42) | (1L << T__43))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Uint - 70)) | (1L << (Int - 70)) | (1L << (Byte - 70)) | (1L << (Fixed - 70)) | (1L << (Ufixed - 70)) | (1L << (MeKeyword - 70)) | (1L << (AllKeyword - 70)) | (1L << (TeeKeyword - 70)) | (1L << (BooleanLiteral - 70)) | (1L << (DecimalNumber - 70)) | (1L << (HexNumber - 70)) | (1L << (Identifier - 70)) | (1L << (StringLiteral - 70)))) != 0)) {
					{
					setState(440);
					expression(0);
					}
				}

				}
				}
				setState(447);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(448);
			match(T__19);
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
		enterRule(_localctx, 78, RULE_elementaryTypeNameExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(450);
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

	public static class NumberLiteralContext extends ParserRuleContext {
		public TerminalNode DecimalNumber() { return getToken(SolidityParser.DecimalNumber, 0); }
		public TerminalNode HexNumber() { return getToken(SolidityParser.HexNumber, 0); }
		public NumberLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numberLiteral; }
	}

	public final NumberLiteralContext numberLiteral() throws RecognitionException {
		NumberLiteralContext _localctx = new NumberLiteralContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_numberLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(452);
			_la = _input.LA(1);
			if ( !(_la==DecimalNumber || _la==HexNumber) ) {
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
		enterRule(_localctx, 82, RULE_annotatedTypeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(454);
			((AnnotatedTypeNameContext)_localctx).type_name = typeName();
			setState(457);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__68) {
				{
				setState(455);
				match(T__68);
				setState(456);
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
		enterRule(_localctx, 84, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(459);
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
		case 36:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 18);
		case 1:
			return precpred(_ctx, 17);
		case 2:
			return precpred(_ctx, 16);
		case 3:
			return precpred(_ctx, 15);
		case 4:
			return precpred(_ctx, 14);
		case 5:
			return precpred(_ctx, 13);
		case 6:
			return precpred(_ctx, 12);
		case 7:
			return precpred(_ctx, 11);
		case 8:
			return precpred(_ctx, 10);
		case 9:
			return precpred(_ctx, 9);
		case 10:
			return precpred(_ctx, 8);
		case 11:
			return precpred(_ctx, 7);
		case 12:
			return precpred(_ctx, 6);
		case 13:
			return precpred(_ctx, 28);
		case 14:
			return precpred(_ctx, 27);
		case 15:
			return precpred(_ctx, 25);
		case 16:
			return precpred(_ctx, 24);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3f\u01d0\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\3\2\3\2\7\2[\n\2\f\2\16\2^\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\5\3\5\5\5k\n\5\3\6\3\6\3\7\5\7p\n\7\3\7\3\7\3\b\3\b\3\b\3\b\7\bx"+
		"\n\b\f\b\16\b{\13\b\3\b\3\b\3\t\3\t\3\t\3\t\5\t\u0083\n\t\3\n\7\n\u0086"+
		"\n\n\f\n\16\n\u0089\13\n\3\n\3\n\7\n\u008d\n\n\f\n\16\n\u0090\13\n\3\n"+
		"\3\n\3\n\5\n\u0095\n\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\f\5\f\u00a3\n\f\3\f\3\f\3\r\3\r\3\r\3\16\7\16\u00ab\n\16\f\16\16"+
		"\16\u00ae\13\16\3\17\3\17\3\17\3\17\5\17\u00b4\n\17\3\20\3\20\3\20\3\20"+
		"\7\20\u00ba\n\20\f\20\16\20\u00bd\13\20\5\20\u00bf\n\20\3\20\3\20\3\21"+
		"\5\21\u00c4\n\21\3\21\3\21\5\21\u00c8\n\21\3\22\3\22\3\23\3\23\3\23\3"+
		"\23\5\23\u00d0\n\23\3\23\3\23\7\23\u00d4\n\23\f\23\16\23\u00d7\13\23\3"+
		"\23\3\23\3\24\5\24\u00dc\n\24\3\24\3\24\3\24\3\25\3\25\3\25\5\25\u00e4"+
		"\n\25\3\26\3\26\3\26\7\26\u00e9\n\26\f\26\16\26\u00ec\13\26\3\27\3\27"+
		"\3\27\3\27\3\27\5\27\u00f3\n\27\3\27\3\27\3\27\3\27\3\30\3\30\3\31\3\31"+
		"\7\31\u00fd\n\31\f\31\16\31\u0100\13\31\3\31\3\31\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\5\32\u010d\n\32\3\33\3\33\3\33\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\5\34\u0119\n\34\3\35\3\35\3\35\3\35\3\35\3\35\3\36"+
		"\3\36\5\36\u0123\n\36\3\37\3\37\3\37\3\37\5\37\u0129\n\37\3\37\5\37\u012c"+
		"\n\37\3\37\3\37\5\37\u0130\n\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3"+
		" \3!\3!\3!\3\"\3\"\3\"\3#\3#\5#\u0145\n#\3#\3#\3$\3$\3$\5$\u014c\n$\3"+
		"$\3$\3%\3%\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\5&\u016c\n&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\7&\u01a7"+
		"\n&\f&\16&\u01aa\13&\3\'\3\'\3\'\7\'\u01af\n\'\f\'\16\'\u01b2\13\'\5\'"+
		"\u01b4\n\'\3(\3(\5(\u01b8\n(\3(\3(\5(\u01bc\n(\7(\u01be\n(\f(\16(\u01c1"+
		"\13(\3(\3(\3)\3)\3*\3*\3+\3+\3+\5+\u01cc\n+\3,\3,\3,\2\3J-\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTV\2\16"+
		"\3\2\5\6\3\2\7\r\4\2\\\\_`\4\2\"(HL\3\2)*\3\2-.\3\2\60\62\3\2\63\64\3"+
		"\2\t\f\3\2\678\4\2\r\r=F\3\2RS\2\u01f2\2X\3\2\2\2\4a\3\2\2\2\6e\3\2\2"+
		"\2\bh\3\2\2\2\nl\3\2\2\2\fo\3\2\2\2\16s\3\2\2\2\20\u0082\3\2\2\2\22\u0087"+
		"\3\2\2\2\24\u0098\3\2\2\2\26\u009d\3\2\2\2\30\u00a6\3\2\2\2\32\u00ac\3"+
		"\2\2\2\34\u00b3\3\2\2\2\36\u00b5\3\2\2\2 \u00c3\3\2\2\2\"\u00c9\3\2\2"+
		"\2$\u00cb\3\2\2\2&\u00db\3\2\2\2(\u00e3\3\2\2\2*\u00e5\3\2\2\2,\u00ed"+
		"\3\2\2\2.\u00f8\3\2\2\2\60\u00fa\3\2\2\2\62\u010c\3\2\2\2\64\u010e\3\2"+
		"\2\2\66\u0111\3\2\2\28\u011a\3\2\2\2:\u0122\3\2\2\2<\u0124\3\2\2\2>\u0134"+
		"\3\2\2\2@\u013c\3\2\2\2B\u013f\3\2\2\2D\u0142\3\2\2\2F\u0148\3\2\2\2H"+
		"\u014f\3\2\2\2J\u016b\3\2\2\2L\u01b3\3\2\2\2N\u01b5\3\2\2\2P\u01c4\3\2"+
		"\2\2R\u01c6\3\2\2\2T\u01c8\3\2\2\2V\u01cd\3\2\2\2X\\\5\4\3\2Y[\5\16\b"+
		"\2ZY\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_`\7"+
		"\2\2\3`\3\3\2\2\2ab\7\3\2\2bc\5\6\4\2cd\7\4\2\2d\5\3\2\2\2ef\t\2\2\2f"+
		"g\5\b\5\2g\7\3\2\2\2hj\5\f\7\2ik\5\f\7\2ji\3\2\2\2jk\3\2\2\2k\t\3\2\2"+
		"\2lm\t\3\2\2m\13\3\2\2\2np\5\n\6\2on\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\7P"+
		"\2\2r\r\3\2\2\2st\7\16\2\2tu\5V,\2uy\7\17\2\2vx\5\20\t\2wv\3\2\2\2x{\3"+
		"\2\2\2yw\3\2\2\2yz\3\2\2\2z|\3\2\2\2{y\3\2\2\2|}\7\20\2\2}\17\3\2\2\2"+
		"~\u0083\5\22\n\2\177\u0083\5\24\13\2\u0080\u0083\5\26\f\2\u0081\u0083"+
		"\5$\23\2\u0082~\3\2\2\2\u0082\177\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0081"+
		"\3\2\2\2\u0083\21\3\2\2\2\u0084\u0086\7a\2\2\u0085\u0084\3\2\2\2\u0086"+
		"\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a\3\2"+
		"\2\2\u0089\u0087\3\2\2\2\u008a\u008e\5T+\2\u008b\u008d\7W\2\2\u008c\u008b"+
		"\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f"+
		"\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0094\5V,\2\u0092\u0093\7\r\2"+
		"\2\u0093\u0095\5J&\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096"+
		"\3\2\2\2\u0096\u0097\7\4\2\2\u0097\23\3\2\2\2\u0098\u0099\7\21\2\2\u0099"+
		"\u009a\5\36\20\2\u009a\u009b\5\32\16\2\u009b\u009c\5\60\31\2\u009c\25"+
		"\3\2\2\2\u009d\u009e\7\22\2\2\u009e\u009f\5V,\2\u009f\u00a0\5\36\20\2"+
		"\u00a0\u00a2\5\32\16\2\u00a1\u00a3\5\30\r\2\u00a2\u00a1\3\2\2\2\u00a2"+
		"\u00a3\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5\5\60\31\2\u00a5\27\3\2"+
		"\2\2\u00a6\u00a7\7\23\2\2\u00a7\u00a8\5\36\20\2\u00a8\31\3\2\2\2\u00a9"+
		"\u00ab\5\34\17\2\u00aa\u00a9\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac\u00aa\3"+
		"\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\33\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af"+
		"\u00b4\5.\30\2\u00b0\u00b4\7^\2\2\u00b1\u00b4\7[\2\2\u00b2\u00b4\7]\2"+
		"\2\u00b3\u00af\3\2\2\2\u00b3\u00b0\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b2"+
		"\3\2\2\2\u00b4\35\3\2\2\2\u00b5\u00be\7\24\2\2\u00b6\u00bb\5 \21\2\u00b7"+
		"\u00b8\7\25\2\2\u00b8\u00ba\5 \21\2\u00b9\u00b7\3\2\2\2\u00ba\u00bd\3"+
		"\2\2\2\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bf\3\2\2\2\u00bd"+
		"\u00bb\3\2\2\2\u00be\u00b6\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c0\3\2"+
		"\2\2\u00c0\u00c1\7\26\2\2\u00c1\37\3\2\2\2\u00c2\u00c4\7a\2\2\u00c3\u00c2"+
		"\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c7\5T+\2\u00c6"+
		"\u00c8\5V,\2\u00c7\u00c6\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8!\3\2\2\2\u00c9"+
		"\u00ca\5V,\2\u00ca#\3\2\2\2\u00cb\u00cc\7\27\2\2\u00cc\u00cd\5V,\2\u00cd"+
		"\u00cf\7\17\2\2\u00ce\u00d0\5\"\22\2\u00cf\u00ce\3\2\2\2\u00cf\u00d0\3"+
		"\2\2\2\u00d0\u00d5\3\2\2\2\u00d1\u00d2\7\25\2\2\u00d2\u00d4\5\"\22\2\u00d3"+
		"\u00d1\3\2\2\2\u00d4\u00d7\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d5\u00d6\3\2"+
		"\2\2\u00d6\u00d8\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d8\u00d9\7\20\2\2\u00d9"+
		"%\3\2\2\2\u00da\u00dc\7a\2\2\u00db\u00da\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc"+
		"\u00dd\3\2\2\2\u00dd\u00de\5T+\2\u00de\u00df\5V,\2\u00df\'\3\2\2\2\u00e0"+
		"\u00e4\5H%\2\u00e1\u00e4\5*\26\2\u00e2\u00e4\5,\27\2\u00e3\u00e0\3\2\2"+
		"\2\u00e3\u00e1\3\2\2\2\u00e3\u00e2\3\2\2\2\u00e4)\3\2\2\2\u00e5\u00ea"+
		"\5V,\2\u00e6\u00e7\7\30\2\2\u00e7\u00e9\5V,\2\u00e8\u00e6\3\2\2\2\u00e9"+
		"\u00ec\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb+\3\2\2\2"+
		"\u00ec\u00ea\3\2\2\2\u00ed\u00ee\7\31\2\2\u00ee\u00ef\7\24\2\2\u00ef\u00f2"+
		"\5H%\2\u00f0\u00f1\7\32\2\2\u00f1\u00f3\5V,\2\u00f2\u00f0\3\2\2\2\u00f2"+
		"\u00f3\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f5\7\33\2\2\u00f5\u00f6\5"+
		"T+\2\u00f6\u00f7\7\26\2\2\u00f7-\3\2\2\2\u00f8\u00f9\t\4\2\2\u00f9/\3"+
		"\2\2\2\u00fa\u00fe\7\17\2\2\u00fb\u00fd\5\62\32\2\u00fc\u00fb\3\2\2\2"+
		"\u00fd\u0100\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0101"+
		"\3\2\2\2\u0100\u00fe\3\2\2\2\u0101\u0102\7\20\2\2\u0102\61\3\2\2\2\u0103"+
		"\u010d\5\66\34\2\u0104\u010d\58\35\2\u0105\u010d\5<\37\2\u0106\u010d\5"+
		"\60\31\2\u0107\u010d\5> \2\u0108\u010d\5@!\2\u0109\u010d\5B\"\2\u010a"+
		"\u010d\5D#\2\u010b\u010d\5:\36\2\u010c\u0103\3\2\2\2\u010c\u0104\3\2\2"+
		"\2\u010c\u0105\3\2\2\2\u010c\u0106\3\2\2\2\u010c\u0107\3\2\2\2\u010c\u0108"+
		"\3\2\2\2\u010c\u0109\3\2\2\2\u010c\u010a\3\2\2\2\u010c\u010b\3\2\2\2\u010d"+
		"\63\3\2\2\2\u010e\u010f\5J&\2\u010f\u0110\7\4\2\2\u0110\65\3\2\2\2\u0111"+
		"\u0112\7\34\2\2\u0112\u0113\7\24\2\2\u0113\u0114\5J&\2\u0114\u0115\7\26"+
		"\2\2\u0115\u0118\5\62\32\2\u0116\u0117\7\35\2\2\u0117\u0119\5\62\32\2"+
		"\u0118\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119\67\3\2\2\2\u011a\u011b"+
		"\7\36\2\2\u011b\u011c\7\24\2\2\u011c\u011d\5J&\2\u011d\u011e\7\26\2\2"+
		"\u011e\u011f\5\62\32\2\u011f9\3\2\2\2\u0120\u0123\5F$\2\u0121\u0123\5"+
		"\64\33\2\u0122\u0120\3\2\2\2\u0122\u0121\3\2\2\2\u0123;\3\2\2\2\u0124"+
		"\u0125\7\37\2\2\u0125\u0128\7\24\2\2\u0126\u0129\5:\36\2\u0127\u0129\7"+
		"\4\2\2\u0128\u0126\3\2\2\2\u0128\u0127\3\2\2\2\u0129\u012b\3\2\2\2\u012a"+
		"\u012c\5J&\2\u012b\u012a\3\2\2\2\u012b\u012c\3\2\2\2\u012c\u012d\3\2\2"+
		"\2\u012d\u012f\7\4\2\2\u012e\u0130\5J&\2\u012f\u012e\3\2\2\2\u012f\u0130"+
		"\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u0132\7\26\2\2\u0132\u0133\5\62\32"+
		"\2\u0133=\3\2\2\2\u0134\u0135\7 \2\2\u0135\u0136\5\62\32\2\u0136\u0137"+
		"\7\36\2\2\u0137\u0138\7\24\2\2\u0138\u0139\5J&\2\u0139\u013a\7\26\2\2"+
		"\u013a\u013b\7\4\2\2\u013b?\3\2\2\2\u013c\u013d\7X\2\2\u013d\u013e\7\4"+
		"\2\2\u013eA\3\2\2\2\u013f\u0140\7V\2\2\u0140\u0141\7\4\2\2\u0141C\3\2"+
		"\2\2\u0142\u0144\7!\2\2\u0143\u0145\5J&\2\u0144\u0143\3\2\2\2\u0144\u0145"+
		"\3\2\2\2\u0145\u0146\3\2\2\2\u0146\u0147\7\4\2\2\u0147E\3\2\2\2\u0148"+
		"\u014b\5&\24\2\u0149\u014a\7\r\2\2\u014a\u014c\5J&\2\u014b\u0149\3\2\2"+
		"\2\u014b\u014c\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014e\7\4\2\2\u014eG"+
		"\3\2\2\2\u014f\u0150\t\5\2\2\u0150I\3\2\2\2\u0151\u0152\b&\1\2\u0152\u016c"+
		"\7M\2\2\u0153\u016c\7N\2\2\u0154\u016c\7O\2\2\u0155\u0156\5H%\2\u0156"+
		"\u0157\7\24\2\2\u0157\u0158\5J&\2\u0158\u0159\7\26\2\2\u0159\u016c\3\2"+
		"\2\2\u015a\u015b\7\24\2\2\u015b\u015c\5J&\2\u015c\u015d\7\26\2\2\u015d"+
		"\u016c\3\2\2\2\u015e\u015f\t\6\2\2\u015f\u016c\5J&\30\u0160\u0161\t\7"+
		"\2\2\u0161\u016c\5J&\27\u0162\u0163\7\32\2\2\u0163\u016c\5J&\26\u0164"+
		"\u0165\7\b\2\2\u0165\u016c\5J&\25\u0166\u016c\7Q\2\2\u0167\u016c\5R*\2"+
		"\u0168\u016c\7c\2\2\u0169\u016c\5N(\2\u016a\u016c\5V,\2\u016b\u0151\3"+
		"\2\2\2\u016b\u0153\3\2\2\2\u016b\u0154\3\2\2\2\u016b\u0155\3\2\2\2\u016b"+
		"\u015a\3\2\2\2\u016b\u015e\3\2\2\2\u016b\u0160\3\2\2\2\u016b\u0162\3\2"+
		"\2\2\u016b\u0164\3\2\2\2\u016b\u0166\3\2\2\2\u016b\u0167\3\2\2\2\u016b"+
		"\u0168\3\2\2\2\u016b\u0169\3\2\2\2\u016b\u016a\3\2\2\2\u016c\u01a8\3\2"+
		"\2\2\u016d\u016e\f\24\2\2\u016e\u016f\7/\2\2\u016f\u01a7\5J&\25\u0170"+
		"\u0171\f\23\2\2\u0171\u0172\t\b\2\2\u0172\u01a7\5J&\24\u0173\u0174\f\22"+
		"\2\2\u0174\u0175\t\7\2\2\u0175\u01a7\5J&\23\u0176\u0177\f\21\2\2\u0177"+
		"\u0178\t\t\2\2\u0178\u01a7\5J&\22\u0179\u017a\f\20\2\2\u017a\u017b\7\65"+
		"\2\2\u017b\u01a7\5J&\21\u017c\u017d\f\17\2\2\u017d\u017e\7\7\2\2\u017e"+
		"\u01a7\5J&\20\u017f\u0180\f\16\2\2\u0180\u0181\7\66\2\2\u0181\u01a7\5"+
		"J&\17\u0182\u0183\f\r\2\2\u0183\u0184\t\n\2\2\u0184\u01a7\5J&\16\u0185"+
		"\u0186\f\f\2\2\u0186\u0187\t\13\2\2\u0187\u01a7\5J&\r\u0188\u0189\f\13"+
		"\2\2\u0189\u018a\79\2\2\u018a\u01a7\5J&\f\u018b\u018c\f\n\2\2\u018c\u018d"+
		"\7:\2\2\u018d\u01a7\5J&\13\u018e\u018f\f\t\2\2\u018f\u0190\7;\2\2\u0190"+
		"\u0191\5J&\2\u0191\u0192\7<\2\2\u0192\u0193\5J&\n\u0193\u01a7\3\2\2\2"+
		"\u0194\u0195\f\b\2\2\u0195\u0196\t\f\2\2\u0196\u01a7\5J&\t\u0197\u0198"+
		"\f\36\2\2\u0198\u01a7\t\6\2\2\u0199\u019a\f\35\2\2\u019a\u019b\7+\2\2"+
		"\u019b\u019c\5J&\2\u019c\u019d\7,\2\2\u019d\u01a7\3\2\2\2\u019e\u019f"+
		"\f\33\2\2\u019f\u01a0\7\24\2\2\u01a0\u01a1\5L\'\2\u01a1\u01a2\7\26\2\2"+
		"\u01a2\u01a7\3\2\2\2\u01a3\u01a4\f\32\2\2\u01a4\u01a5\7\30\2\2\u01a5\u01a7"+
		"\5V,\2\u01a6\u016d\3\2\2\2\u01a6\u0170\3\2\2\2\u01a6\u0173\3\2\2\2\u01a6"+
		"\u0176\3\2\2\2\u01a6\u0179\3\2\2\2\u01a6\u017c\3\2\2\2\u01a6\u017f\3\2"+
		"\2\2\u01a6\u0182\3\2\2\2\u01a6\u0185\3\2\2\2\u01a6\u0188\3\2\2\2\u01a6"+
		"\u018b\3\2\2\2\u01a6\u018e\3\2\2\2\u01a6\u0194\3\2\2\2\u01a6\u0197\3\2"+
		"\2\2\u01a6\u0199\3\2\2\2\u01a6\u019e\3\2\2\2\u01a6\u01a3\3\2\2\2\u01a7"+
		"\u01aa\3\2\2\2\u01a8\u01a6\3\2\2\2\u01a8\u01a9\3\2\2\2\u01a9K\3\2\2\2"+
		"\u01aa\u01a8\3\2\2\2\u01ab\u01b0\5J&\2\u01ac\u01ad\7\25\2\2\u01ad\u01af"+
		"\5J&\2\u01ae\u01ac\3\2\2\2\u01af\u01b2\3\2\2\2\u01b0\u01ae\3\2\2\2\u01b0"+
		"\u01b1\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b3\u01ab\3\2"+
		"\2\2\u01b3\u01b4\3\2\2\2\u01b4M\3\2\2\2\u01b5\u01b7\7\24\2\2\u01b6\u01b8"+
		"\5J&\2\u01b7\u01b6\3\2\2\2\u01b7\u01b8\3\2\2\2\u01b8\u01bf\3\2\2\2\u01b9"+
		"\u01bb\7\25\2\2\u01ba\u01bc\5J&\2\u01bb\u01ba\3\2\2\2\u01bb\u01bc\3\2"+
		"\2\2\u01bc\u01be\3\2\2\2\u01bd\u01b9\3\2\2\2\u01be\u01c1\3\2\2\2\u01bf"+
		"\u01bd\3\2\2\2\u01bf\u01c0\3\2\2\2\u01c0\u01c2\3\2\2\2\u01c1\u01bf\3\2"+
		"\2\2\u01c2\u01c3\7\26\2\2\u01c3O\3\2\2\2\u01c4\u01c5\5H%\2\u01c5Q\3\2"+
		"\2\2\u01c6\u01c7\t\r\2\2\u01c7S\3\2\2\2\u01c8\u01cb\5(\25\2\u01c9\u01ca"+
		"\7G\2\2\u01ca\u01cc\5J&\2\u01cb\u01c9\3\2\2\2\u01cb\u01cc\3\2\2\2\u01cc"+
		"U\3\2\2\2\u01cd\u01ce\7b\2\2\u01ceW\3\2\2\2)\\joy\u0082\u0087\u008e\u0094"+
		"\u00a2\u00ac\u00b3\u00bb\u00be\u00c3\u00c7\u00cf\u00d5\u00db\u00e3\u00ea"+
		"\u00f2\u00fe\u010c\u0118\u0122\u0128\u012b\u012f\u0144\u014b\u016b\u01a6"+
		"\u01a8\u01b0\u01b3\u01b7\u01bb\u01bf\u01cb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}