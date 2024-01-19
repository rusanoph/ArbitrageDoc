// Generated from c:/Users/007/Desktop/Workspace/ArbitrageWorkspace/pdfStatisticsSpringApp/arbitrage_statistics/src/main/java/ru/idr/grammar/FindContent.g4 by ANTLR 4.13.1
package ru.idr.grammar.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class FindContentLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, COMPANY_PREFIX=3, LDAQ=4, RDAQ=5, CYRILIC_LETTER=6, LATIN_LETTER=7, 
		TEXT=8, WS=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "COMPANY_PREFIX", "LDAQ", "RDAQ", "CYRILIC_LETTER", "LATIN_LETTER", 
			"TEXT", "WS"
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


	public FindContentLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FindContent.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\t<\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0003\u0002%\b\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0004\u00072\b\u0007\u000b\u0007\f\u0007"+
		"3\u0001\b\u0004\b7\b\b\u000b\b\f\b8\u0001\b\u0001\b\u0000\u0000\t\u0001"+
		"\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007"+
		"\u000f\b\u0011\t\u0001\u0000\u0003\u0002\u0000AZaz\u0003\u0000()\u00ab"+
		"\u00ab\u00bb\u00bb\u0003\u0000\t\n\r\r  C\u0000\u0001\u0001\u0000\u0000"+
		"\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000"+
		"\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000"+
		"\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000"+
		"\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0001"+
		"\u0013\u0001\u0000\u0000\u0000\u0003\u0015\u0001\u0000\u0000\u0000\u0005"+
		"$\u0001\u0000\u0000\u0000\u0007&\u0001\u0000\u0000\u0000\t(\u0001\u0000"+
		"\u0000\u0000\u000b*\u0001\u0000\u0000\u0000\r,\u0001\u0000\u0000\u0000"+
		"\u000f1\u0001\u0000\u0000\u0000\u00116\u0001\u0000\u0000\u0000\u0013\u0014"+
		"\u0005(\u0000\u0000\u0014\u0002\u0001\u0000\u0000\u0000\u0015\u0016\u0005"+
		")\u0000\u0000\u0016\u0004\u0001\u0000\u0000\u0000\u0017\u0018\u0005\u041e"+
		"\u0000\u0000\u0018\u0019\u0005\u041e\u0000\u0000\u0019%\u0005\u041e\u0000"+
		"\u0000\u001a\u001b\u0005\u0410\u0000\u0000\u001b%\u0005\u041e\u0000\u0000"+
		"\u001c\u001d\u0005\u041f\u0000\u0000\u001d\u001e\u0005\u0410\u0000\u0000"+
		"\u001e%\u0005\u041e\u0000\u0000\u001f \u0005\u0418\u0000\u0000 %\u0005"+
		"\u041f\u0000\u0000!\"\u0005\u0417\u0000\u0000\"#\u0005\u0410\u0000\u0000"+
		"#%\u0005\u041e\u0000\u0000$\u0017\u0001\u0000\u0000\u0000$\u001a\u0001"+
		"\u0000\u0000\u0000$\u001c\u0001\u0000\u0000\u0000$\u001f\u0001\u0000\u0000"+
		"\u0000$!\u0001\u0000\u0000\u0000%\u0006\u0001\u0000\u0000\u0000&\'\u0005"+
		"\u00ab\u0000\u0000\'\b\u0001\u0000\u0000\u0000()\u0005\u00bb\u0000\u0000"+
		")\n\u0001\u0000\u0000\u0000*+\u0002\u0400\u052f\u0000+\f\u0001\u0000\u0000"+
		"\u0000,-\u0007\u0000\u0000\u0000-\u000e\u0001\u0000\u0000\u0000.2\u0003"+
		"\u000b\u0005\u0000/2\u0003\r\u0006\u000002\b\u0001\u0000\u00001.\u0001"+
		"\u0000\u0000\u00001/\u0001\u0000\u0000\u000010\u0001\u0000\u0000\u0000"+
		"23\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000"+
		"\u00004\u0010\u0001\u0000\u0000\u000057\u0007\u0002\u0000\u000065\u0001"+
		"\u0000\u0000\u000078\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u0000"+
		"89\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:;\u0006\b\u0000\u0000"+
		";\u0012\u0001\u0000\u0000\u0000\u0005\u0000$138\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}