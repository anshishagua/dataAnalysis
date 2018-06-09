// Generated from /Users/lixiao/code/dataAnalysis/src/main/resources/ExpressionRule.g4 by ANTLR 4.7
package com.anshishagua.antlr4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionRuleLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Boolean=3, Equal=4, NotEqual=5, LessThan=6, LessThanOrEqual=7, 
		GreaterThan=8, GreaterThanOrEqual=9, Dot=10, Exp=11, STRING=12, Case=13, 
		When=14, Then=15, Else=16, End=17, Digit=18, And=19, Or=20, Not=21, Multiply=22, 
		Divide=23, Mod=24, Plus=25, Minus=26, In=27, Is=28, Null=29, LeftParen=30, 
		RightParen=31, Comma=32, Between=33, Like=34, ID=35, WS=36;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "Boolean", "Equal", "NotEqual", "LessThan", "LessThanOrEqual", 
		"GreaterThan", "GreaterThanOrEqual", "Dot", "Exp", "STRING", "Case", "When", 
		"Then", "Else", "End", "Digit", "And", "Or", "Not", "Multiply", "Divide", 
		"Mod", "Plus", "Minus", "In", "Is", "Null", "LeftParen", "RightParen", 
		"Comma", "Between", "Like", "ID", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'${'", "'}'", null, "'='", null, "'<'", "'<='", "'>'", "'>='", 
		"'.'", null, null, "'CASE'", "'WHEN'", "'THEN'", "'ELSE'", "'END'", null, 
		null, null, "'NOT'", "'*'", "'/'", "'%'", "'+'", "'-'", "'In'", "'IS'", 
		"'NULL'", "'('", "')'", "','", "'BETWEEN'", "'LIKE'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "Boolean", "Equal", "NotEqual", "LessThan", "LessThanOrEqual", 
		"GreaterThan", "GreaterThanOrEqual", "Dot", "Exp", "STRING", "Case", "When", 
		"Then", "Else", "End", "Digit", "And", "Or", "Not", "Multiply", "Divide", 
		"Mod", "Plus", "Minus", "In", "Is", "Null", "LeftParen", "RightParen", 
		"Comma", "Between", "Like", "ID", "WS"
	};
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


	public ExpressionRuleLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ExpressionRule.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2&\u00da\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\5\4Z\n\4\3\5\3\5\3\6\3\6\3\6\3\6\5\6b\n\6\3\7\3\7\3\b"+
		"\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\7\rt\n\r\f\r\16"+
		"\rw\13\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\24\3\24\3\24\3\24\3\24\5\24\u009a\n\24\3\25\3\25\3\25\3\25\5\25"+
		"\u00a0\n\25\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32"+
		"\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\37"+
		"\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3"+
		"$\6$\u00d0\n$\r$\16$\u00d1\3%\6%\u00d5\n%\r%\16%\u00d6\3%\3%\2\2&\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#E$G%I&\3\2\b\4\2GGgg\3\2))\3\2\62;\6\2C\\aac|\u4e02\u9fa7\7\2\62"+
		";C\\aac|\u4e02\u9fa7\5\2\13\f\17\17\"\"\2\u00e0\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3"+
		"\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\3K\3\2\2"+
		"\2\5N\3\2\2\2\7Y\3\2\2\2\t[\3\2\2\2\13a\3\2\2\2\rc\3\2\2\2\17e\3\2\2\2"+
		"\21h\3\2\2\2\23j\3\2\2\2\25m\3\2\2\2\27o\3\2\2\2\31q\3\2\2\2\33z\3\2\2"+
		"\2\35\177\3\2\2\2\37\u0084\3\2\2\2!\u0089\3\2\2\2#\u008e\3\2\2\2%\u0092"+
		"\3\2\2\2\'\u0099\3\2\2\2)\u009f\3\2\2\2+\u00a1\3\2\2\2-\u00a5\3\2\2\2"+
		"/\u00a7\3\2\2\2\61\u00a9\3\2\2\2\63\u00ab\3\2\2\2\65\u00ad\3\2\2\2\67"+
		"\u00af\3\2\2\29\u00b2\3\2\2\2;\u00b5\3\2\2\2=\u00ba\3\2\2\2?\u00bc\3\2"+
		"\2\2A\u00be\3\2\2\2C\u00c0\3\2\2\2E\u00c8\3\2\2\2G\u00cd\3\2\2\2I\u00d4"+
		"\3\2\2\2KL\7&\2\2LM\7}\2\2M\4\3\2\2\2NO\7\177\2\2O\6\3\2\2\2PQ\7V\2\2"+
		"QR\7T\2\2RS\7W\2\2SZ\7G\2\2TU\7H\2\2UV\7C\2\2VW\7N\2\2WX\7U\2\2XZ\7G\2"+
		"\2YP\3\2\2\2YT\3\2\2\2Z\b\3\2\2\2[\\\7?\2\2\\\n\3\2\2\2]^\7>\2\2^b\7@"+
		"\2\2_`\7#\2\2`b\7?\2\2a]\3\2\2\2a_\3\2\2\2b\f\3\2\2\2cd\7>\2\2d\16\3\2"+
		"\2\2ef\7>\2\2fg\7?\2\2g\20\3\2\2\2hi\7@\2\2i\22\3\2\2\2jk\7@\2\2kl\7?"+
		"\2\2l\24\3\2\2\2mn\7\60\2\2n\26\3\2\2\2op\t\2\2\2p\30\3\2\2\2qu\7)\2\2"+
		"rt\n\3\2\2sr\3\2\2\2tw\3\2\2\2us\3\2\2\2uv\3\2\2\2vx\3\2\2\2wu\3\2\2\2"+
		"xy\7)\2\2y\32\3\2\2\2z{\7E\2\2{|\7C\2\2|}\7U\2\2}~\7G\2\2~\34\3\2\2\2"+
		"\177\u0080\7Y\2\2\u0080\u0081\7J\2\2\u0081\u0082\7G\2\2\u0082\u0083\7"+
		"P\2\2\u0083\36\3\2\2\2\u0084\u0085\7V\2\2\u0085\u0086\7J\2\2\u0086\u0087"+
		"\7G\2\2\u0087\u0088\7P\2\2\u0088 \3\2\2\2\u0089\u008a\7G\2\2\u008a\u008b"+
		"\7N\2\2\u008b\u008c\7U\2\2\u008c\u008d\7G\2\2\u008d\"\3\2\2\2\u008e\u008f"+
		"\7G\2\2\u008f\u0090\7P\2\2\u0090\u0091\7F\2\2\u0091$\3\2\2\2\u0092\u0093"+
		"\t\4\2\2\u0093&\3\2\2\2\u0094\u0095\7C\2\2\u0095\u0096\7P\2\2\u0096\u009a"+
		"\7F\2\2\u0097\u0098\7(\2\2\u0098\u009a\7(\2\2\u0099\u0094\3\2\2\2\u0099"+
		"\u0097\3\2\2\2\u009a(\3\2\2\2\u009b\u009c\7Q\2\2\u009c\u00a0\7T\2\2\u009d"+
		"\u009e\7~\2\2\u009e\u00a0\7~\2\2\u009f\u009b\3\2\2\2\u009f\u009d\3\2\2"+
		"\2\u00a0*\3\2\2\2\u00a1\u00a2\7P\2\2\u00a2\u00a3\7Q\2\2\u00a3\u00a4\7"+
		"V\2\2\u00a4,\3\2\2\2\u00a5\u00a6\7,\2\2\u00a6.\3\2\2\2\u00a7\u00a8\7\61"+
		"\2\2\u00a8\60\3\2\2\2\u00a9\u00aa\7\'\2\2\u00aa\62\3\2\2\2\u00ab\u00ac"+
		"\7-\2\2\u00ac\64\3\2\2\2\u00ad\u00ae\7/\2\2\u00ae\66\3\2\2\2\u00af\u00b0"+
		"\7K\2\2\u00b0\u00b1\7p\2\2\u00b18\3\2\2\2\u00b2\u00b3\7K\2\2\u00b3\u00b4"+
		"\7U\2\2\u00b4:\3\2\2\2\u00b5\u00b6\7P\2\2\u00b6\u00b7\7W\2\2\u00b7\u00b8"+
		"\7N\2\2\u00b8\u00b9\7N\2\2\u00b9<\3\2\2\2\u00ba\u00bb\7*\2\2\u00bb>\3"+
		"\2\2\2\u00bc\u00bd\7+\2\2\u00bd@\3\2\2\2\u00be\u00bf\7.\2\2\u00bfB\3\2"+
		"\2\2\u00c0\u00c1\7D\2\2\u00c1\u00c2\7G\2\2\u00c2\u00c3\7V\2\2\u00c3\u00c4"+
		"\7Y\2\2\u00c4\u00c5\7G\2\2\u00c5\u00c6\7G\2\2\u00c6\u00c7\7P\2\2\u00c7"+
		"D\3\2\2\2\u00c8\u00c9\7N\2\2\u00c9\u00ca\7K\2\2\u00ca\u00cb\7M\2\2\u00cb"+
		"\u00cc\7G\2\2\u00ccF\3\2\2\2\u00cd\u00cf\t\5\2\2\u00ce\u00d0\t\6\2\2\u00cf"+
		"\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d2\3\2"+
		"\2\2\u00d2H\3\2\2\2\u00d3\u00d5\t\7\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00d6"+
		"\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8"+
		"\u00d9\b%\2\2\u00d9J\3\2\2\2\n\2Yau\u0099\u009f\u00d1\u00d6\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}