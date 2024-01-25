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
		T__0=1, T__1=2, T__2=3, LAW_PREFIX=4, COMPANY_PREFIX=5, MONEYSUM=6, RUBSUM=7, 
		KOPSUM=8, DATE=9, LDAQ=10, RDAQ=11, INT=12, DIGIT=13, CYRILIC_LETTER=14, 
		LATIN_LETTER=15, WORD=16, WS_SKIP=17;
	public static final int
		RULE_start = 0, RULE_lexem = 1, RULE_token = 2, RULE_bracketedText = 3, 
		RULE_law = 4, RULE_companyName = 5, RULE_moneysum = 6, RULE_date = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "lexem", "token", "bracketedText", "law", "companyName", "moneysum", 
			"date"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'\"'", null, null, null, null, null, null, "'\\u00AB'", 
			"'\\u00BB'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "LAW_PREFIX", "COMPANY_PREFIX", "MONEYSUM", "RUBSUM", 
			"KOPSUM", "DATE", "LDAQ", "RDAQ", "INT", "DIGIT", "CYRILIC_LETTER", "LATIN_LETTER", 
			"WORD", "WS_SKIP"
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
		public TerminalNode EOF() { return getToken(FindContentParser.EOF, 0); }
		public List<LexemContext> lexem() {
			return getRuleContexts(LexemContext.class);
		}
		public LexemContext lexem(int i) {
			return getRuleContext(LexemContext.class,i);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(17); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(16);
				lexem();
				}
				}
				setState(19); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 67178L) != 0) );
			setState(21);
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

	@SuppressWarnings("CheckReturnValue")
	public static class LexemContext extends ParserRuleContext {
		public TokenContext token() {
			return getRuleContext(TokenContext.class,0);
		}
		public TerminalNode WORD() { return getToken(FindContentParser.WORD, 0); }
		public LexemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterLexem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitLexem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitLexem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexemContext lexem() throws RecognitionException {
		LexemContext _localctx = new LexemContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_lexem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__2:
			case COMPANY_PREFIX:
			case MONEYSUM:
			case DATE:
			case LDAQ:
				{
				setState(23);
				token();
				}
				break;
			case WORD:
				{
				setState(24);
				match(WORD);
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

	@SuppressWarnings("CheckReturnValue")
	public static class TokenContext extends ParserRuleContext {
		public CompanyNameContext companyName() {
			return getRuleContext(CompanyNameContext.class,0);
		}
		public LawContext law() {
			return getRuleContext(LawContext.class,0);
		}
		public MoneysumContext moneysum() {
			return getRuleContext(MoneysumContext.class,0);
		}
		public DateContext date() {
			return getRuleContext(DateContext.class,0);
		}
		public BracketedTextContext bracketedText() {
			return getRuleContext(BracketedTextContext.class,0);
		}
		public TokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_token; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterToken(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitToken(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitToken(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TokenContext token() throws RecognitionException {
		TokenContext _localctx = new TokenContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_token);
		try {
			setState(32);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(27);
				companyName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(28);
				law();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(29);
				moneysum();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(30);
				date();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(31);
				bracketedText();
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
	public static class BracketedTextContext extends ParserRuleContext {
		public List<LexemContext> lexem() {
			return getRuleContexts(LexemContext.class);
		}
		public LexemContext lexem(int i) {
			return getRuleContext(LexemContext.class,i);
		}
		public BracketedTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bracketedText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterBracketedText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitBracketedText(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitBracketedText(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BracketedTextContext bracketedText() throws RecognitionException {
		BracketedTextContext _localctx = new BracketedTextContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_bracketedText);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(T__0);
			setState(36); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(35);
				lexem();
				}
				}
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 67178L) != 0) );
			setState(40);
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
	public static class LawContext extends ParserRuleContext {
		public TerminalNode RDAQ() { return getToken(FindContentParser.RDAQ, 0); }
		public List<TerminalNode> LDAQ() { return getTokens(FindContentParser.LDAQ); }
		public TerminalNode LDAQ(int i) {
			return getToken(FindContentParser.LDAQ, i);
		}
		public List<TerminalNode> LAW_PREFIX() { return getTokens(FindContentParser.LAW_PREFIX); }
		public TerminalNode LAW_PREFIX(int i) {
			return getToken(FindContentParser.LAW_PREFIX, i);
		}
		public List<LexemContext> lexem() {
			return getRuleContexts(LexemContext.class);
		}
		public LexemContext lexem(int i) {
			return getRuleContext(LexemContext.class,i);
		}
		public LawContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_law; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterLaw(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitLaw(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitLaw(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LawContext law() throws RecognitionException {
		LawContext _localctx = new LawContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_law);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(49); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(42);
				match(LDAQ);
				setState(43);
				match(LAW_PREFIX);
				setState(45); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(44);
						lexem();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(47); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				}
				setState(51); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LDAQ );
			setState(53);
			match(RDAQ);
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
		public TerminalNode RDAQ() { return getToken(FindContentParser.RDAQ, 0); }
		public TerminalNode COMPANY_PREFIX() { return getToken(FindContentParser.COMPANY_PREFIX, 0); }
		public List<TerminalNode> LDAQ() { return getTokens(FindContentParser.LDAQ); }
		public TerminalNode LDAQ(int i) {
			return getToken(FindContentParser.LDAQ, i);
		}
		public List<LexemContext> lexem() {
			return getRuleContexts(LexemContext.class);
		}
		public LexemContext lexem(int i) {
			return getRuleContext(LexemContext.class,i);
		}
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
		enterRule(_localctx, 10, RULE_companyName);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMPANY_PREFIX) {
				{
				setState(55);
				match(COMPANY_PREFIX);
				}
			}

			setState(64); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(58);
					_la = _input.LA(1);
					if ( !(_la==T__2 || _la==LDAQ) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(60); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(59);
							lexem();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(62); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(66); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(68);
			_la = _input.LA(1);
			if ( !(_la==T__2 || _la==RDAQ) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class MoneysumContext extends ParserRuleContext {
		public TerminalNode MONEYSUM() { return getToken(FindContentParser.MONEYSUM, 0); }
		public MoneysumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_moneysum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterMoneysum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitMoneysum(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitMoneysum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MoneysumContext moneysum() throws RecognitionException {
		MoneysumContext _localctx = new MoneysumContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_moneysum);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(MONEYSUM);
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
	public static class DateContext extends ParserRuleContext {
		public TerminalNode DATE() { return getToken(FindContentParser.DATE, 0); }
		public DateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).enterDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FindContentListener ) ((FindContentListener)listener).exitDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FindContentVisitor ) return ((FindContentVisitor<? extends T>)visitor).visitDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateContext date() throws RecognitionException {
		DateContext _localctx = new DateContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_date);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(DATE);
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
		"\u0004\u0001\u0011K\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001"+
		"\u0000\u0004\u0000\u0012\b\u0000\u000b\u0000\f\u0000\u0013\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001\u001a\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002!\b\u0002"+
		"\u0001\u0003\u0001\u0003\u0004\u0003%\b\u0003\u000b\u0003\f\u0003&\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0004\u0004.\b"+
		"\u0004\u000b\u0004\f\u0004/\u0004\u00042\b\u0004\u000b\u0004\f\u00043"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0003\u00059\b\u0005\u0001\u0005"+
		"\u0001\u0005\u0004\u0005=\b\u0005\u000b\u0005\f\u0005>\u0004\u0005A\b"+
		"\u0005\u000b\u0005\f\u0005B\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0000\u0000\b\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0000\u0002\u0002\u0000\u0003\u0003\n\n\u0002\u0000"+
		"\u0003\u0003\u000b\u000bN\u0000\u0011\u0001\u0000\u0000\u0000\u0002\u0019"+
		"\u0001\u0000\u0000\u0000\u0004 \u0001\u0000\u0000\u0000\u0006\"\u0001"+
		"\u0000\u0000\u0000\b1\u0001\u0000\u0000\u0000\n8\u0001\u0000\u0000\u0000"+
		"\fF\u0001\u0000\u0000\u0000\u000eH\u0001\u0000\u0000\u0000\u0010\u0012"+
		"\u0003\u0002\u0001\u0000\u0011\u0010\u0001\u0000\u0000\u0000\u0012\u0013"+
		"\u0001\u0000\u0000\u0000\u0013\u0011\u0001\u0000\u0000\u0000\u0013\u0014"+
		"\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0016"+
		"\u0005\u0000\u0000\u0001\u0016\u0001\u0001\u0000\u0000\u0000\u0017\u001a"+
		"\u0003\u0004\u0002\u0000\u0018\u001a\u0005\u0010\u0000\u0000\u0019\u0017"+
		"\u0001\u0000\u0000\u0000\u0019\u0018\u0001\u0000\u0000\u0000\u001a\u0003"+
		"\u0001\u0000\u0000\u0000\u001b!\u0003\n\u0005\u0000\u001c!\u0003\b\u0004"+
		"\u0000\u001d!\u0003\f\u0006\u0000\u001e!\u0003\u000e\u0007\u0000\u001f"+
		"!\u0003\u0006\u0003\u0000 \u001b\u0001\u0000\u0000\u0000 \u001c\u0001"+
		"\u0000\u0000\u0000 \u001d\u0001\u0000\u0000\u0000 \u001e\u0001\u0000\u0000"+
		"\u0000 \u001f\u0001\u0000\u0000\u0000!\u0005\u0001\u0000\u0000\u0000\""+
		"$\u0005\u0001\u0000\u0000#%\u0003\u0002\u0001\u0000$#\u0001\u0000\u0000"+
		"\u0000%&\u0001\u0000\u0000\u0000&$\u0001\u0000\u0000\u0000&\'\u0001\u0000"+
		"\u0000\u0000\'(\u0001\u0000\u0000\u0000()\u0005\u0002\u0000\u0000)\u0007"+
		"\u0001\u0000\u0000\u0000*+\u0005\n\u0000\u0000+-\u0005\u0004\u0000\u0000"+
		",.\u0003\u0002\u0001\u0000-,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000"+
		"\u0000/-\u0001\u0000\u0000\u0000/0\u0001\u0000\u0000\u000002\u0001\u0000"+
		"\u0000\u00001*\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000031\u0001"+
		"\u0000\u0000\u000034\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u0000"+
		"56\u0005\u000b\u0000\u00006\t\u0001\u0000\u0000\u000079\u0005\u0005\u0000"+
		"\u000087\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u00009@\u0001\u0000"+
		"\u0000\u0000:<\u0007\u0000\u0000\u0000;=\u0003\u0002\u0001\u0000<;\u0001"+
		"\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000"+
		">?\u0001\u0000\u0000\u0000?A\u0001\u0000\u0000\u0000@:\u0001\u0000\u0000"+
		"\u0000AB\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000BC\u0001\u0000"+
		"\u0000\u0000CD\u0001\u0000\u0000\u0000DE\u0007\u0001\u0000\u0000E\u000b"+
		"\u0001\u0000\u0000\u0000FG\u0005\u0006\u0000\u0000G\r\u0001\u0000\u0000"+
		"\u0000HI\u0005\t\u0000\u0000I\u000f\u0001\u0000\u0000\u0000\t\u0013\u0019"+
		" &/38>B";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}