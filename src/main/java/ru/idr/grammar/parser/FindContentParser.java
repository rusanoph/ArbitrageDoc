// Generated from c:/Users/007/Desktop/Workspace/ArbitrageWorkspace/pdfStatisticsSpringApp/arbitrage_statistics/src/main/java/ru/idr/grammar/FindContent.g4 by ANTLR 4.13.1
package ru.idr.grammar.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class FindContentParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, COMPANY_PREFIX=3, LDAQ=4, RDAQ=5, CYRILIC_LETTER=6, LATIN_LETTER=7, 
		TEXT=8, WS=9;
	public static final int
		RULE_start = 0, RULE_companyName = 1, RULE_interestingPart = 2, RULE_content = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "companyName", "interestingPart", "content"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", null, "'\\u00AB'", "'\\u00BB'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "COMPANY_PREFIX", "LDAQ", "RDAQ", "CYRILIC_LETTER", 
			"LATIN_LETTER", "TEXT", "WS"
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
	public String getGrammarFileName() { return "FindContent.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FindContentParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public List<ContentContext> content() {
			return getRuleContexts(ContentContext.class);
		}
		public ContentContext content(int i) {
			return getRuleContext(ContentContext.class,i);
		}
		public List<TerminalNode> TEXT() { return getTokens(FindContentParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(FindContentParser.TEXT, i);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		int _la;
		try {
			setState(20);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(15); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(9);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
					case 1:
						{
						setState(8);
						match(TEXT);
						}
						break;
					}
					setState(11);
					content();
					setState(13);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						setState(12);
						match(TEXT);
						}
						break;
					}
					}
					}
					setState(17); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 282L) != 0) );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(19);
				match(TEXT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class CompanyNameContext extends ParserRuleContext {
		public TerminalNode LDAQ() { return getToken(FindContentParser.LDAQ, 0); }
		public TerminalNode COMPANY_PREFIX() { return getToken(FindContentParser.COMPANY_PREFIX, 0); }
		public List<InterestingPartContext> interestingPart() {
			return getRuleContexts(InterestingPartContext.class);
		}
		public InterestingPartContext interestingPart(int i) {
			return getRuleContext(InterestingPartContext.class,i);
		}
		public List<TerminalNode> TEXT() { return getTokens(FindContentParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(FindContentParser.TEXT, i);
		}
		public TerminalNode RDAQ() { return getToken(FindContentParser.RDAQ, 0); }
		public CompanyNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_companyName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterCompanyName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitCompanyName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitCompanyName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompanyNameContext companyName() throws RecognitionException {
		CompanyNameContext _localctx = new CompanyNameContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_companyName);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMPANY_PREFIX) {
				{
				setState(22);
				match(COMPANY_PREFIX);
				}
			}

			setState(25);
			match(LDAQ);
			setState(28); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(28);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__0:
						{
						setState(26);
						interestingPart();
						}
						break;
					case TEXT:
						{
						setState(27);
						match(TEXT);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(30); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RDAQ) {
				{
				setState(32);
				match(RDAQ);
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

	@SuppressWarnings("CheckReturnValue")
	public static class InterestingPartContext extends ParserRuleContext {
		public List<CompanyNameContext> companyName() {
			return getRuleContexts(CompanyNameContext.class);
		}
		public CompanyNameContext companyName(int i) {
			return getRuleContext(CompanyNameContext.class,i);
		}
		public List<TerminalNode> TEXT() { return getTokens(FindContentParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(FindContentParser.TEXT, i);
		}
		public InterestingPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interestingPart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterInterestingPart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitInterestingPart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitInterestingPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterestingPartContext interestingPart() throws RecognitionException {
		InterestingPartContext _localctx = new InterestingPartContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_interestingPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			match(T__0);
			setState(38); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(38);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case COMPANY_PREFIX:
				case LDAQ:
					{
					setState(36);
					companyName();
					}
					break;
				case TEXT:
					{
					setState(37);
					match(TEXT);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(40); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 280L) != 0) );
			setState(42);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ContentContext extends ParserRuleContext {
		public CompanyNameContext companyName() {
			return getRuleContext(CompanyNameContext.class,0);
		}
		public InterestingPartContext interestingPart() {
			return getRuleContext(InterestingPartContext.class,0);
		}
		public TerminalNode TEXT() { return getToken(FindContentParser.TEXT, 0); }
		public ContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContentContext content() throws RecognitionException {
		ContentContext _localctx = new ContentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_content);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case COMPANY_PREFIX:
			case LDAQ:
				{
				setState(44);
				companyName();
				}
				break;
			case T__0:
				{
				setState(45);
				interestingPart();
				}
				break;
			case TEXT:
				{
				setState(46);
				match(TEXT);
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

	public static final String _serializedATN =
		"\u0004\u0001\t2\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0003\u0000\n\b"+
		"\u0000\u0001\u0000\u0001\u0000\u0003\u0000\u000e\b\u0000\u0004\u0000\u0010"+
		"\b\u0000\u000b\u0000\f\u0000\u0011\u0001\u0000\u0003\u0000\u0015\b\u0000"+
		"\u0001\u0001\u0003\u0001\u0018\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0004\u0001\u001d\b\u0001\u000b\u0001\f\u0001\u001e\u0001\u0001\u0003"+
		"\u0001\"\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002\'\b\u0002"+
		"\u000b\u0002\f\u0002(\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u00030\b\u0003\u0001\u0003\u0000\u0000\u0004\u0000"+
		"\u0002\u0004\u0006\u0000\u00009\u0000\u0014\u0001\u0000\u0000\u0000\u0002"+
		"\u0017\u0001\u0000\u0000\u0000\u0004#\u0001\u0000\u0000\u0000\u0006/\u0001"+
		"\u0000\u0000\u0000\b\n\u0005\b\u0000\u0000\t\b\u0001\u0000\u0000\u0000"+
		"\t\n\u0001\u0000\u0000\u0000\n\u000b\u0001\u0000\u0000\u0000\u000b\r\u0003"+
		"\u0006\u0003\u0000\f\u000e\u0005\b\u0000\u0000\r\f\u0001\u0000\u0000\u0000"+
		"\r\u000e\u0001\u0000\u0000\u0000\u000e\u0010\u0001\u0000\u0000\u0000\u000f"+
		"\t\u0001\u0000\u0000\u0000\u0010\u0011\u0001\u0000\u0000\u0000\u0011\u000f"+
		"\u0001\u0000\u0000\u0000\u0011\u0012\u0001\u0000\u0000\u0000\u0012\u0015"+
		"\u0001\u0000\u0000\u0000\u0013\u0015\u0005\b\u0000\u0000\u0014\u000f\u0001"+
		"\u0000\u0000\u0000\u0014\u0013\u0001\u0000\u0000\u0000\u0015\u0001\u0001"+
		"\u0000\u0000\u0000\u0016\u0018\u0005\u0003\u0000\u0000\u0017\u0016\u0001"+
		"\u0000\u0000\u0000\u0017\u0018\u0001\u0000\u0000\u0000\u0018\u0019\u0001"+
		"\u0000\u0000\u0000\u0019\u001c\u0005\u0004\u0000\u0000\u001a\u001d\u0003"+
		"\u0004\u0002\u0000\u001b\u001d\u0005\b\u0000\u0000\u001c\u001a\u0001\u0000"+
		"\u0000\u0000\u001c\u001b\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000"+
		"\u0000\u0000\u001e\u001c\u0001\u0000\u0000\u0000\u001e\u001f\u0001\u0000"+
		"\u0000\u0000\u001f!\u0001\u0000\u0000\u0000 \"\u0005\u0005\u0000\u0000"+
		"! \u0001\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000\"\u0003\u0001\u0000"+
		"\u0000\u0000#&\u0005\u0001\u0000\u0000$\'\u0003\u0002\u0001\u0000%\'\u0005"+
		"\b\u0000\u0000&$\u0001\u0000\u0000\u0000&%\u0001\u0000\u0000\u0000\'("+
		"\u0001\u0000\u0000\u0000(&\u0001\u0000\u0000\u0000()\u0001\u0000\u0000"+
		"\u0000)*\u0001\u0000\u0000\u0000*+\u0005\u0002\u0000\u0000+\u0005\u0001"+
		"\u0000\u0000\u0000,0\u0003\u0002\u0001\u0000-0\u0003\u0004\u0002\u0000"+
		".0\u0005\b\u0000\u0000/,\u0001\u0000\u0000\u0000/-\u0001\u0000\u0000\u0000"+
		"/.\u0001\u0000\u0000\u00000\u0007\u0001\u0000\u0000\u0000\u000b\t\r\u0011"+
		"\u0014\u0017\u001c\u001e!&(/";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}