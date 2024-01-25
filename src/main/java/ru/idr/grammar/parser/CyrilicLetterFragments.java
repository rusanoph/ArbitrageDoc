// Generated from c:/Users/007/Desktop/Workspace/ArbitrageWorkspace/pdfStatisticsSpringApp/arbitrage_statistics/src/main/java/ru/idr/grammar/CyrilicLetterFragments.g4 by ANTLR 4.13.1
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
public class CyrilicLetterFragments extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", 
			"О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", 
			"Ь", "Э", "Ю", "Я"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
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


	public CyrilicLetterFragments(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CyrilicLetterFragments.g4"; }

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
		"\u0004\u0000\u0000\u0081\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d"+
		"\u0002\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017"+
		"\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0000\u0000"+
		" \u0001\u0000\u0003\u0000\u0005\u0000\u0007\u0000\t\u0000\u000b\u0000"+
		"\r\u0000\u000f\u0000\u0011\u0000\u0013\u0000\u0015\u0000\u0017\u0000\u0019"+
		"\u0000\u001b\u0000\u001d\u0000\u001f\u0000!\u0000#\u0000%\u0000\'\u0000"+
		")\u0000+\u0000-\u0000/\u00001\u00003\u00005\u00007\u00009\u0000;\u0000"+
		"=\u0000?\u0000\u0001\u0000 \u0002\u0000\u0410\u0410\u0430\u0430\u0002"+
		"\u0000\u0411\u0411\u0431\u0431\u0002\u0000\u0412\u0412\u0432\u0432\u0002"+
		"\u0000\u0413\u0413\u0433\u0433\u0002\u0000\u0414\u0414\u0434\u0434\u0002"+
		"\u0000\u0415\u0415\u0435\u0435\u0002\u0000\u0416\u0416\u0436\u0436\u0002"+
		"\u0000\u0417\u0417\u0437\u0437\u0002\u0000\u0418\u0418\u0438\u0438\u0002"+
		"\u0000\u0419\u0419\u0439\u0439\u0002\u0000\u041a\u041a\u043a\u043a\u0002"+
		"\u0000\u041b\u041b\u043b\u043b\u0002\u0000\u041c\u041c\u043c\u043c\u0002"+
		"\u0000\u041d\u041d\u043d\u043d\u0002\u0000\u041e\u041e\u043e\u043e\u0002"+
		"\u0000\u041f\u041f\u043f\u043f\u0002\u0000\u0420\u0420\u0440\u0440\u0002"+
		"\u0000\u0421\u0421\u0441\u0441\u0002\u0000\u0422\u0422\u0442\u0442\u0002"+
		"\u0000\u0423\u0423\u0443\u0443\u0002\u0000\u0424\u0424\u0444\u0444\u0002"+
		"\u0000\u0425\u0425\u0445\u0445\u0002\u0000\u0426\u0426\u0446\u0446\u0002"+
		"\u0000\u0427\u0427\u0447\u0447\u0002\u0000\u0428\u0428\u0448\u0448\u0002"+
		"\u0000\u0429\u0429\u0449\u0449\u0002\u0000\u042a\u042a\u044a\u044a\u0002"+
		"\u0000\u042b\u042b\u044b\u044b\u0002\u0000\u042c\u042c\u044c\u044c\u0002"+
		"\u0000\u042d\u042d\u044d\u044d\u0002\u0000\u042e\u042e\u044e\u044e\u0002"+
		"\u0000\u042f\u042f\u044f\u044f`\u0001A\u0001\u0000\u0000\u0000\u0003C"+
		"\u0001\u0000\u0000\u0000\u0005E\u0001\u0000\u0000\u0000\u0007G\u0001\u0000"+
		"\u0000\u0000\tI\u0001\u0000\u0000\u0000\u000bK\u0001\u0000\u0000\u0000"+
		"\rM\u0001\u0000\u0000\u0000\u000fO\u0001\u0000\u0000\u0000\u0011Q\u0001"+
		"\u0000\u0000\u0000\u0013S\u0001\u0000\u0000\u0000\u0015U\u0001\u0000\u0000"+
		"\u0000\u0017W\u0001\u0000\u0000\u0000\u0019Y\u0001\u0000\u0000\u0000\u001b"+
		"[\u0001\u0000\u0000\u0000\u001d]\u0001\u0000\u0000\u0000\u001f_\u0001"+
		"\u0000\u0000\u0000!a\u0001\u0000\u0000\u0000#c\u0001\u0000\u0000\u0000"+
		"%e\u0001\u0000\u0000\u0000\'g\u0001\u0000\u0000\u0000)i\u0001\u0000\u0000"+
		"\u0000+k\u0001\u0000\u0000\u0000-m\u0001\u0000\u0000\u0000/o\u0001\u0000"+
		"\u0000\u00001q\u0001\u0000\u0000\u00003s\u0001\u0000\u0000\u00005u\u0001"+
		"\u0000\u0000\u00007w\u0001\u0000\u0000\u00009y\u0001\u0000\u0000\u0000"+
		";{\u0001\u0000\u0000\u0000=}\u0001\u0000\u0000\u0000?\u007f\u0001\u0000"+
		"\u0000\u0000AB\u0007\u0000\u0000\u0000B\u0002\u0001\u0000\u0000\u0000"+
		"CD\u0007\u0001\u0000\u0000D\u0004\u0001\u0000\u0000\u0000EF\u0007\u0002"+
		"\u0000\u0000F\u0006\u0001\u0000\u0000\u0000GH\u0007\u0003\u0000\u0000"+
		"H\b\u0001\u0000\u0000\u0000IJ\u0007\u0004\u0000\u0000J\n\u0001\u0000\u0000"+
		"\u0000KL\u0007\u0005\u0000\u0000L\f\u0001\u0000\u0000\u0000MN\u0007\u0006"+
		"\u0000\u0000N\u000e\u0001\u0000\u0000\u0000OP\u0007\u0007\u0000\u0000"+
		"P\u0010\u0001\u0000\u0000\u0000QR\u0007\b\u0000\u0000R\u0012\u0001\u0000"+
		"\u0000\u0000ST\u0007\t\u0000\u0000T\u0014\u0001\u0000\u0000\u0000UV\u0007"+
		"\n\u0000\u0000V\u0016\u0001\u0000\u0000\u0000WX\u0007\u000b\u0000\u0000"+
		"X\u0018\u0001\u0000\u0000\u0000YZ\u0007\f\u0000\u0000Z\u001a\u0001\u0000"+
		"\u0000\u0000[\\\u0007\r\u0000\u0000\\\u001c\u0001\u0000\u0000\u0000]^"+
		"\u0007\u000e\u0000\u0000^\u001e\u0001\u0000\u0000\u0000_`\u0007\u000f"+
		"\u0000\u0000` \u0001\u0000\u0000\u0000ab\u0007\u0010\u0000\u0000b\"\u0001"+
		"\u0000\u0000\u0000cd\u0007\u0011\u0000\u0000d$\u0001\u0000\u0000\u0000"+
		"ef\u0007\u0012\u0000\u0000f&\u0001\u0000\u0000\u0000gh\u0007\u0013\u0000"+
		"\u0000h(\u0001\u0000\u0000\u0000ij\u0007\u0014\u0000\u0000j*\u0001\u0000"+
		"\u0000\u0000kl\u0007\u0015\u0000\u0000l,\u0001\u0000\u0000\u0000mn\u0007"+
		"\u0016\u0000\u0000n.\u0001\u0000\u0000\u0000op\u0007\u0017\u0000\u0000"+
		"p0\u0001\u0000\u0000\u0000qr\u0007\u0018\u0000\u0000r2\u0001\u0000\u0000"+
		"\u0000st\u0007\u0019\u0000\u0000t4\u0001\u0000\u0000\u0000uv\u0007\u001a"+
		"\u0000\u0000v6\u0001\u0000\u0000\u0000wx\u0007\u001b\u0000\u0000x8\u0001"+
		"\u0000\u0000\u0000yz\u0007\u001c\u0000\u0000z:\u0001\u0000\u0000\u0000"+
		"{|\u0007\u001d\u0000\u0000|<\u0001\u0000\u0000\u0000}~\u0007\u001e\u0000"+
		"\u0000~>\u0001\u0000\u0000\u0000\u007f\u0080\u0007\u001f\u0000\u0000\u0080"+
		"@\u0001\u0000\u0000\u0000\u0001\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}