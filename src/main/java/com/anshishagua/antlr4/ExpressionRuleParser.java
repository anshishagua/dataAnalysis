// Generated from /Users/lixiao/code/expressionParser/src/main/resources/ExpressionRule.g4 by ANTLR 4.7
package com.anshishagua.antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionRuleParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Boolean=1, Equal=2, NotEqual=3, LessThan=4, LessThanOrEqual=5, GreaterThan=6, 
		GreaterThanOrEqual=7, Dot=8, Exp=9, Case=10, When=11, Then=12, Else=13, 
		End=14, Digit=15, And=16, Or=17, Not=18, Multiply=19, Divide=20, Mod=21, 
		Plus=22, Minus=23, In=24, Is=25, Null=26, LeftParen=27, RightParen=28, 
		Comma=29, Between=30, Like=31, SINGLE_QUOTE=32, ID=33, WS=34;
	public static final int
		RULE_start = 0, RULE_compare = 1, RULE_numeric = 2, RULE_sign = 3, RULE_decimal = 4, 
		RULE_string = 5, RULE_stringContent = 6, RULE_intNumber = 7, RULE_longNumber = 8, 
		RULE_number = 9, RULE_condition = 10, RULE_expression = 11, RULE_andCondition = 12, 
		RULE_conditionRightHandSide = 13, RULE_value = 14, RULE_id = 15, RULE_columnRef = 16, 
		RULE_operand = 17, RULE_summand = 18, RULE_plusMinus = 19, RULE_factor = 20, 
		RULE_mulDivMod = 21, RULE_term = 22, RULE_function = 23, RULE_caseWhen = 24;
	public static final String[] ruleNames = {
		"start", "compare", "numeric", "sign", "decimal", "string", "stringContent", 
		"intNumber", "longNumber", "number", "condition", "expression", "andCondition", 
		"conditionRightHandSide", "value", "id", "columnRef", "operand", "summand", 
		"plusMinus", "factor", "mulDivMod", "term", "function", "caseWhen"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, "'='", null, "'<'", "'<='", "'>'", "'>='", "'.'", null, "'CASE'", 
		"'WHEN'", "'THEN'", "'ELSE'", "'END'", null, null, null, "'NOT'", "'*'", 
		"'/'", "'%'", "'+'", "'-'", "'In'", "'IS'", "'NULL'", "'('", "')'", "','", 
		"'BETWEEN'", "'LIKE'", "'''"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "Boolean", "Equal", "NotEqual", "LessThan", "LessThanOrEqual", "GreaterThan", 
		"GreaterThanOrEqual", "Dot", "Exp", "Case", "When", "Then", "Else", "End", 
		"Digit", "And", "Or", "Not", "Multiply", "Divide", "Mod", "Plus", "Minus", 
		"In", "Is", "Null", "LeftParen", "RightParen", "Comma", "Between", "Like", 
		"SINGLE_QUOTE", "ID", "WS"
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

	@Override
	public String getGrammarFileName() { return "ExpressionRule.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExpressionRuleParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ExpressionRuleParser.EOF, 0); }
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			expression();
			setState(51);
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

	public static class CompareContext extends ParserRuleContext {
		public TerminalNode Equal() { return getToken(ExpressionRuleParser.Equal, 0); }
		public TerminalNode NotEqual() { return getToken(ExpressionRuleParser.NotEqual, 0); }
		public TerminalNode LessThanOrEqual() { return getToken(ExpressionRuleParser.LessThanOrEqual, 0); }
		public TerminalNode LessThan() { return getToken(ExpressionRuleParser.LessThan, 0); }
		public TerminalNode GreaterThan() { return getToken(ExpressionRuleParser.GreaterThan, 0); }
		public TerminalNode GreaterThanOrEqual() { return getToken(ExpressionRuleParser.GreaterThanOrEqual, 0); }
		public CompareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitCompare(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitCompare(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompareContext compare() throws RecognitionException {
		CompareContext _localctx = new CompareContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_compare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Equal) | (1L << NotEqual) | (1L << LessThan) | (1L << LessThanOrEqual) | (1L << GreaterThan) | (1L << GreaterThanOrEqual))) != 0)) ) {
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

	public static class NumericContext extends ParserRuleContext {
		public DecimalContext decimal() {
			return getRuleContext(DecimalContext.class,0);
		}
		public IntNumberContext intNumber() {
			return getRuleContext(IntNumberContext.class,0);
		}
		public LongNumberContext longNumber() {
			return getRuleContext(LongNumberContext.class,0);
		}
		public NumericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numeric; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitNumeric(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitNumeric(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericContext numeric() throws RecognitionException {
		NumericContext _localctx = new NumericContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_numeric);
		try {
			setState(58);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				decimal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				intNumber();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(57);
				longNumber();
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

	public static class SignContext extends ParserRuleContext {
		public SignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterSign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitSign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitSign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignContext sign() throws RecognitionException {
		SignContext _localctx = new SignContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_sign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			_la = _input.LA(1);
			if ( !(_la==Plus || _la==Minus) ) {
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

	public static class DecimalContext extends ParserRuleContext {
		public TerminalNode Dot() { return getToken(ExpressionRuleParser.Dot, 0); }
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public SignContext sign() {
			return getRuleContext(SignContext.class,0);
		}
		public DecimalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decimal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterDecimal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitDecimal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitDecimal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecimalContext decimal() throws RecognitionException {
		DecimalContext _localctx = new DecimalContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_decimal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Plus || _la==Minus) {
				{
				setState(62);
				sign();
				}
			}

			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Dot:
				{
				setState(65);
				match(Dot);
				setState(66);
				number();
				}
				break;
			case Digit:
				{
				setState(67);
				number();
				setState(68);
				match(Dot);
				setState(69);
				number();
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

	public static class StringContext extends ParserRuleContext {
		public List<TerminalNode> SINGLE_QUOTE() { return getTokens(ExpressionRuleParser.SINGLE_QUOTE); }
		public TerminalNode SINGLE_QUOTE(int i) {
			return getToken(ExpressionRuleParser.SINGLE_QUOTE, i);
		}
		public StringContentContext stringContent() {
			return getRuleContext(StringContentContext.class,0);
		}
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(SINGLE_QUOTE);
			setState(74);
			stringContent();
			setState(75);
			match(SINGLE_QUOTE);
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

	public static class StringContentContext extends ParserRuleContext {
		public StringContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterStringContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitStringContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitStringContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContentContext stringContent() throws RecognitionException {
		StringContentContext _localctx = new StringContentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_stringContent);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(77);
					matchWildcard();
					}
					} 
				}
				setState(82);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
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

	public static class IntNumberContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public SignContext sign() {
			return getRuleContext(SignContext.class,0);
		}
		public IntNumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intNumber; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterIntNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitIntNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitIntNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntNumberContext intNumber() throws RecognitionException {
		IntNumberContext _localctx = new IntNumberContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_intNumber);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Plus || _la==Minus) {
				{
				setState(83);
				sign();
				}
			}

			setState(86);
			number();
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

	public static class LongNumberContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public SignContext sign() {
			return getRuleContext(SignContext.class,0);
		}
		public LongNumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_longNumber; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterLongNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitLongNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitLongNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LongNumberContext longNumber() throws RecognitionException {
		LongNumberContext _localctx = new LongNumberContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_longNumber);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Plus || _la==Minus) {
				{
				setState(88);
				sign();
				}
			}

			setState(91);
			number();
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

	public static class NumberContext extends ParserRuleContext {
		public List<TerminalNode> Digit() { return getTokens(ExpressionRuleParser.Digit); }
		public TerminalNode Digit(int i) {
			return getToken(ExpressionRuleParser.Digit, i);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_number);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(94); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(93);
					match(Digit);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(96); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class ConditionContext extends ParserRuleContext {
		public OperandContext operand() {
			return getRuleContext(OperandContext.class,0);
		}
		public ConditionRightHandSideContext conditionRightHandSide() {
			return getRuleContext(ConditionRightHandSideContext.class,0);
		}
		public TerminalNode Not() { return getToken(ExpressionRuleParser.Not, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_condition);
		int _la;
		try {
			setState(104);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Boolean:
			case Dot:
			case Case:
			case Digit:
			case Plus:
			case Minus:
			case Null:
			case LeftParen:
			case SINGLE_QUOTE:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
				operand();
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Boolean) | (1L << Equal) | (1L << NotEqual) | (1L << LessThan) | (1L << LessThanOrEqual) | (1L << GreaterThan) | (1L << GreaterThanOrEqual) | (1L << Dot) | (1L << Case) | (1L << Digit) | (1L << Not) | (1L << Plus) | (1L << Minus) | (1L << In) | (1L << Is) | (1L << Null) | (1L << LeftParen) | (1L << Between) | (1L << Like) | (1L << SINGLE_QUOTE) | (1L << ID))) != 0)) {
					{
					setState(99);
					conditionRightHandSide();
					}
				}

				}
				break;
			case Not:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				match(Not);
				setState(103);
				condition();
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

	public static class ExpressionContext extends ParserRuleContext {
		public List<AndConditionContext> andCondition() {
			return getRuleContexts(AndConditionContext.class);
		}
		public AndConditionContext andCondition(int i) {
			return getRuleContext(AndConditionContext.class,i);
		}
		public List<TerminalNode> Or() { return getTokens(ExpressionRuleParser.Or); }
		public TerminalNode Or(int i) {
			return getToken(ExpressionRuleParser.Or, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			andCondition();
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Or) {
				{
				{
				setState(107);
				match(Or);
				setState(108);
				andCondition();
				}
				}
				setState(113);
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

	public static class AndConditionContext extends ParserRuleContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public List<TerminalNode> And() { return getTokens(ExpressionRuleParser.And); }
		public TerminalNode And(int i) {
			return getToken(ExpressionRuleParser.And, i);
		}
		public AndConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterAndCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitAndCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitAndCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndConditionContext andCondition() throws RecognitionException {
		AndConditionContext _localctx = new AndConditionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_andCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			condition();
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==And) {
				{
				{
				setState(115);
				match(And);
				setState(116);
				condition();
				}
				}
				setState(121);
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

	public static class ConditionRightHandSideContext extends ParserRuleContext {
		public CompareContext compare() {
			return getRuleContext(CompareContext.class,0);
		}
		public List<OperandContext> operand() {
			return getRuleContexts(OperandContext.class);
		}
		public OperandContext operand(int i) {
			return getRuleContext(OperandContext.class,i);
		}
		public TerminalNode Is() { return getToken(ExpressionRuleParser.Is, 0); }
		public TerminalNode Null() { return getToken(ExpressionRuleParser.Null, 0); }
		public TerminalNode Not() { return getToken(ExpressionRuleParser.Not, 0); }
		public TerminalNode In() { return getToken(ExpressionRuleParser.In, 0); }
		public TerminalNode LeftParen() { return getToken(ExpressionRuleParser.LeftParen, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RightParen() { return getToken(ExpressionRuleParser.RightParen, 0); }
		public List<TerminalNode> Comma() { return getTokens(ExpressionRuleParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(ExpressionRuleParser.Comma, i);
		}
		public TerminalNode Between() { return getToken(ExpressionRuleParser.Between, 0); }
		public TerminalNode And() { return getToken(ExpressionRuleParser.And, 0); }
		public TerminalNode Like() { return getToken(ExpressionRuleParser.Like, 0); }
		public ConditionRightHandSideContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionRightHandSide; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterConditionRightHandSide(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitConditionRightHandSide(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitConditionRightHandSide(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionRightHandSideContext conditionRightHandSide() throws RecognitionException {
		ConditionRightHandSideContext _localctx = new ConditionRightHandSideContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_conditionRightHandSide);
		int _la;
		try {
			setState(154);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Equal:
			case NotEqual:
			case LessThan:
			case LessThanOrEqual:
			case GreaterThan:
			case GreaterThanOrEqual:
				enterOuterAlt(_localctx, 1);
				{
				setState(122);
				compare();
				setState(123);
				operand();
				}
				break;
			case Is:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				match(Is);
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Not) {
					{
					setState(126);
					match(Not);
					}
				}

				setState(129);
				match(Null);
				}
				break;
			case In:
				enterOuterAlt(_localctx, 3);
				{
				setState(130);
				match(In);
				setState(131);
				match(LeftParen);
				setState(132);
				expression();
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Comma) {
					{
					{
					setState(133);
					match(Comma);
					setState(134);
					expression();
					}
					}
					setState(139);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(140);
				match(RightParen);
				}
				break;
			case Between:
				enterOuterAlt(_localctx, 4);
				{
				setState(142);
				match(Between);
				setState(143);
				operand();
				setState(144);
				match(And);
				setState(145);
				operand();
				}
				break;
			case Boolean:
			case Dot:
			case Case:
			case Digit:
			case Not:
			case Plus:
			case Minus:
			case Null:
			case LeftParen:
			case Like:
			case SINGLE_QUOTE:
			case ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Not) {
					{
					setState(147);
					match(Not);
					}
				}

				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Like) {
					{
					setState(150);
					match(Like);
					}
				}

				setState(153);
				operand();
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

	public static class ValueContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public NumericContext numeric() {
			return getRuleContext(NumericContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ColumnRefContext columnRef() {
			return getRuleContext(ColumnRefContext.class,0);
		}
		public TerminalNode Boolean() { return getToken(ExpressionRuleParser.Boolean, 0); }
		public TerminalNode Null() { return getToken(ExpressionRuleParser.Null, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_value);
		try {
			setState(162);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(156);
				string();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(157);
				numeric();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(158);
				id();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(159);
				columnRef();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(160);
				match(Boolean);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(161);
				match(Null);
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

	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ExpressionRuleParser.ID, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(ID);
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

	public static class ColumnRefContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ExpressionRuleParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ExpressionRuleParser.ID, i);
		}
		public TerminalNode Dot() { return getToken(ExpressionRuleParser.Dot, 0); }
		public ColumnRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterColumnRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitColumnRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitColumnRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnRefContext columnRef() throws RecognitionException {
		ColumnRefContext _localctx = new ColumnRefContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_columnRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(ID);
			setState(167);
			match(Dot);
			setState(168);
			match(ID);
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

	public static class OperandContext extends ParserRuleContext {
		public SummandContext summand() {
			return getRuleContext(SummandContext.class,0);
		}
		public OperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterOperand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitOperand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitOperand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperandContext operand() throws RecognitionException {
		OperandContext _localctx = new OperandContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_operand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			summand();
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

	public static class SummandContext extends ParserRuleContext {
		public List<FactorContext> factor() {
			return getRuleContexts(FactorContext.class);
		}
		public FactorContext factor(int i) {
			return getRuleContext(FactorContext.class,i);
		}
		public List<PlusMinusContext> plusMinus() {
			return getRuleContexts(PlusMinusContext.class);
		}
		public PlusMinusContext plusMinus(int i) {
			return getRuleContext(PlusMinusContext.class,i);
		}
		public SummandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_summand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterSummand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitSummand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitSummand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SummandContext summand() throws RecognitionException {
		SummandContext _localctx = new SummandContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_summand);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			factor();
			setState(178);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(173);
					plusMinus();
					setState(174);
					factor();
					}
					} 
				}
				setState(180);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
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

	public static class PlusMinusContext extends ParserRuleContext {
		public TerminalNode Plus() { return getToken(ExpressionRuleParser.Plus, 0); }
		public TerminalNode Minus() { return getToken(ExpressionRuleParser.Minus, 0); }
		public PlusMinusContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_plusMinus; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterPlusMinus(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitPlusMinus(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitPlusMinus(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PlusMinusContext plusMinus() throws RecognitionException {
		PlusMinusContext _localctx = new PlusMinusContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_plusMinus);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			_la = _input.LA(1);
			if ( !(_la==Plus || _la==Minus) ) {
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

	public static class FactorContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<MulDivModContext> mulDivMod() {
			return getRuleContexts(MulDivModContext.class);
		}
		public MulDivModContext mulDivMod(int i) {
			return getRuleContext(MulDivModContext.class,i);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitFactor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_factor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			term();
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Multiply) | (1L << Divide) | (1L << Mod))) != 0)) {
				{
				{
				setState(184);
				mulDivMod();
				setState(185);
				term();
				}
				}
				setState(191);
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

	public static class MulDivModContext extends ParserRuleContext {
		public TerminalNode Multiply() { return getToken(ExpressionRuleParser.Multiply, 0); }
		public TerminalNode Divide() { return getToken(ExpressionRuleParser.Divide, 0); }
		public TerminalNode Mod() { return getToken(ExpressionRuleParser.Mod, 0); }
		public MulDivModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mulDivMod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterMulDivMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitMulDivMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitMulDivMod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MulDivModContext mulDivMod() throws RecognitionException {
		MulDivModContext _localctx = new MulDivModContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_mulDivMod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Multiply) | (1L << Divide) | (1L << Mod))) != 0)) ) {
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

	public static class TermContext extends ParserRuleContext {
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode Plus() { return getToken(ExpressionRuleParser.Plus, 0); }
		public TerminalNode Minus() { return getToken(ExpressionRuleParser.Minus, 0); }
		public TerminalNode LeftParen() { return getToken(ExpressionRuleParser.LeftParen, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(ExpressionRuleParser.RightParen, 0); }
		public CaseWhenContext caseWhen() {
			return getRuleContext(CaseWhenContext.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_term);
		int _la;
		try {
			setState(203);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(194);
				function();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(195);
				value();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(196);
				_la = _input.LA(1);
				if ( !(_la==Plus || _la==Minus) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(197);
				term();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(198);
				match(LeftParen);
				setState(199);
				expression();
				setState(200);
				match(RightParen);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(202);
				caseWhen();
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

	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ExpressionRuleParser.ID, 0); }
		public TerminalNode LeftParen() { return getToken(ExpressionRuleParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(ExpressionRuleParser.RightParen, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(ExpressionRuleParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(ExpressionRuleParser.Comma, i);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(ID);
			setState(206);
			match(LeftParen);
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Boolean) | (1L << Dot) | (1L << Case) | (1L << Digit) | (1L << Not) | (1L << Plus) | (1L << Minus) | (1L << Null) | (1L << LeftParen) | (1L << SINGLE_QUOTE) | (1L << ID))) != 0)) {
				{
				setState(207);
				expression();
				setState(212);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Comma) {
					{
					{
					setState(208);
					match(Comma);
					setState(209);
					expression();
					}
					}
					setState(214);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(217);
			match(RightParen);
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

	public static class CaseWhenContext extends ParserRuleContext {
		public TerminalNode Case() { return getToken(ExpressionRuleParser.Case, 0); }
		public TerminalNode Else() { return getToken(ExpressionRuleParser.Else, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode End() { return getToken(ExpressionRuleParser.End, 0); }
		public List<TerminalNode> When() { return getTokens(ExpressionRuleParser.When); }
		public TerminalNode When(int i) {
			return getToken(ExpressionRuleParser.When, i);
		}
		public List<TerminalNode> Then() { return getTokens(ExpressionRuleParser.Then); }
		public TerminalNode Then(int i) {
			return getToken(ExpressionRuleParser.Then, i);
		}
		public CaseWhenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseWhen; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).enterCaseWhen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionRuleListener ) ((ExpressionRuleListener)listener).exitCaseWhen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionRuleVisitor ) return ((ExpressionRuleVisitor<? extends T>)visitor).visitCaseWhen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseWhenContext caseWhen() throws RecognitionException {
		CaseWhenContext _localctx = new CaseWhenContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_caseWhen);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			match(Case);
			setState(225); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(220);
				match(When);
				setState(221);
				expression();
				setState(222);
				match(Then);
				setState(223);
				expression();
				}
				}
				setState(227); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==When );
			setState(229);
			match(Else);
			setState(230);
			expression();
			setState(231);
			match(End);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3$\u00ec\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\5\4=\n\4\3\5\3\5\3\6\5\6B\n"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6J\n\6\3\7\3\7\3\7\3\7\3\b\7\bQ\n\b\f\b\16"+
		"\bT\13\b\3\t\5\tW\n\t\3\t\3\t\3\n\5\n\\\n\n\3\n\3\n\3\13\6\13a\n\13\r"+
		"\13\16\13b\3\f\3\f\5\fg\n\f\3\f\3\f\5\fk\n\f\3\r\3\r\3\r\7\rp\n\r\f\r"+
		"\16\rs\13\r\3\16\3\16\3\16\7\16x\n\16\f\16\16\16{\13\16\3\17\3\17\3\17"+
		"\3\17\3\17\5\17\u0082\n\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u008a\n"+
		"\17\f\17\16\17\u008d\13\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17"+
		"\u0097\n\17\3\17\5\17\u009a\n\17\3\17\5\17\u009d\n\17\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\5\20\u00a5\n\20\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\7\24\u00b3\n\24\f\24\16\24\u00b6\13\24\3\25\3\25"+
		"\3\26\3\26\3\26\3\26\7\26\u00be\n\26\f\26\16\26\u00c1\13\26\3\27\3\27"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u00ce\n\30\3\31\3\31"+
		"\3\31\3\31\3\31\7\31\u00d5\n\31\f\31\16\31\u00d8\13\31\5\31\u00da\n\31"+
		"\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\6\32\u00e4\n\32\r\32\16\32\u00e5"+
		"\3\32\3\32\3\32\3\32\3\32\2\2\33\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\62\2\5\3\2\4\t\3\2\30\31\3\2\25\27\2\u00f4\2\64\3\2\2\2\4"+
		"\67\3\2\2\2\6<\3\2\2\2\b>\3\2\2\2\nA\3\2\2\2\fK\3\2\2\2\16R\3\2\2\2\20"+
		"V\3\2\2\2\22[\3\2\2\2\24`\3\2\2\2\26j\3\2\2\2\30l\3\2\2\2\32t\3\2\2\2"+
		"\34\u009c\3\2\2\2\36\u00a4\3\2\2\2 \u00a6\3\2\2\2\"\u00a8\3\2\2\2$\u00ac"+
		"\3\2\2\2&\u00ae\3\2\2\2(\u00b7\3\2\2\2*\u00b9\3\2\2\2,\u00c2\3\2\2\2."+
		"\u00cd\3\2\2\2\60\u00cf\3\2\2\2\62\u00dd\3\2\2\2\64\65\5\30\r\2\65\66"+
		"\7\2\2\3\66\3\3\2\2\2\678\t\2\2\28\5\3\2\2\29=\5\n\6\2:=\5\20\t\2;=\5"+
		"\22\n\2<9\3\2\2\2<:\3\2\2\2<;\3\2\2\2=\7\3\2\2\2>?\t\3\2\2?\t\3\2\2\2"+
		"@B\5\b\5\2A@\3\2\2\2AB\3\2\2\2BI\3\2\2\2CD\7\n\2\2DJ\5\24\13\2EF\5\24"+
		"\13\2FG\7\n\2\2GH\5\24\13\2HJ\3\2\2\2IC\3\2\2\2IE\3\2\2\2J\13\3\2\2\2"+
		"KL\7\"\2\2LM\5\16\b\2MN\7\"\2\2N\r\3\2\2\2OQ\13\2\2\2PO\3\2\2\2QT\3\2"+
		"\2\2RP\3\2\2\2RS\3\2\2\2S\17\3\2\2\2TR\3\2\2\2UW\5\b\5\2VU\3\2\2\2VW\3"+
		"\2\2\2WX\3\2\2\2XY\5\24\13\2Y\21\3\2\2\2Z\\\5\b\5\2[Z\3\2\2\2[\\\3\2\2"+
		"\2\\]\3\2\2\2]^\5\24\13\2^\23\3\2\2\2_a\7\21\2\2`_\3\2\2\2ab\3\2\2\2b"+
		"`\3\2\2\2bc\3\2\2\2c\25\3\2\2\2df\5$\23\2eg\5\34\17\2fe\3\2\2\2fg\3\2"+
		"\2\2gk\3\2\2\2hi\7\24\2\2ik\5\26\f\2jd\3\2\2\2jh\3\2\2\2k\27\3\2\2\2l"+
		"q\5\32\16\2mn\7\23\2\2np\5\32\16\2om\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2"+
		"\2\2r\31\3\2\2\2sq\3\2\2\2ty\5\26\f\2uv\7\22\2\2vx\5\26\f\2wu\3\2\2\2"+
		"x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z\33\3\2\2\2{y\3\2\2\2|}\5\4\3\2}~\5$\23"+
		"\2~\u009d\3\2\2\2\177\u0081\7\33\2\2\u0080\u0082\7\24\2\2\u0081\u0080"+
		"\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u009d\7\34\2\2"+
		"\u0084\u0085\7\32\2\2\u0085\u0086\7\35\2\2\u0086\u008b\5\30\r\2\u0087"+
		"\u0088\7\37\2\2\u0088\u008a\5\30\r\2\u0089\u0087\3\2\2\2\u008a\u008d\3"+
		"\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e\3\2\2\2\u008d"+
		"\u008b\3\2\2\2\u008e\u008f\7\36\2\2\u008f\u009d\3\2\2\2\u0090\u0091\7"+
		" \2\2\u0091\u0092\5$\23\2\u0092\u0093\7\22\2\2\u0093\u0094\5$\23\2\u0094"+
		"\u009d\3\2\2\2\u0095\u0097\7\24\2\2\u0096\u0095\3\2\2\2\u0096\u0097\3"+
		"\2\2\2\u0097\u0099\3\2\2\2\u0098\u009a\7!\2\2\u0099\u0098\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\5$\23\2\u009c|\3\2\2\2"+
		"\u009c\177\3\2\2\2\u009c\u0084\3\2\2\2\u009c\u0090\3\2\2\2\u009c\u0096"+
		"\3\2\2\2\u009d\35\3\2\2\2\u009e\u00a5\5\f\7\2\u009f\u00a5\5\6\4\2\u00a0"+
		"\u00a5\5 \21\2\u00a1\u00a5\5\"\22\2\u00a2\u00a5\7\3\2\2\u00a3\u00a5\7"+
		"\34\2\2\u00a4\u009e\3\2\2\2\u00a4\u009f\3\2\2\2\u00a4\u00a0\3\2\2\2\u00a4"+
		"\u00a1\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a3\3\2\2\2\u00a5\37\3\2\2"+
		"\2\u00a6\u00a7\7#\2\2\u00a7!\3\2\2\2\u00a8\u00a9\7#\2\2\u00a9\u00aa\7"+
		"\n\2\2\u00aa\u00ab\7#\2\2\u00ab#\3\2\2\2\u00ac\u00ad\5&\24\2\u00ad%\3"+
		"\2\2\2\u00ae\u00b4\5*\26\2\u00af\u00b0\5(\25\2\u00b0\u00b1\5*\26\2\u00b1"+
		"\u00b3\3\2\2\2\u00b2\u00af\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4\u00b2\3\2"+
		"\2\2\u00b4\u00b5\3\2\2\2\u00b5\'\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b8"+
		"\t\3\2\2\u00b8)\3\2\2\2\u00b9\u00bf\5.\30\2\u00ba\u00bb\5,\27\2\u00bb"+
		"\u00bc\5.\30\2\u00bc\u00be\3\2\2\2\u00bd\u00ba\3\2\2\2\u00be\u00c1\3\2"+
		"\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0+\3\2\2\2\u00c1\u00bf"+
		"\3\2\2\2\u00c2\u00c3\t\4\2\2\u00c3-\3\2\2\2\u00c4\u00ce\5\60\31\2\u00c5"+
		"\u00ce\5\36\20\2\u00c6\u00c7\t\3\2\2\u00c7\u00ce\5.\30\2\u00c8\u00c9\7"+
		"\35\2\2\u00c9\u00ca\5\30\r\2\u00ca\u00cb\7\36\2\2\u00cb\u00ce\3\2\2\2"+
		"\u00cc\u00ce\5\62\32\2\u00cd\u00c4\3\2\2\2\u00cd\u00c5\3\2\2\2\u00cd\u00c6"+
		"\3\2\2\2\u00cd\u00c8\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce/\3\2\2\2\u00cf"+
		"\u00d0\7#\2\2\u00d0\u00d9\7\35\2\2\u00d1\u00d6\5\30\r\2\u00d2\u00d3\7"+
		"\37\2\2\u00d3\u00d5\5\30\r\2\u00d4\u00d2\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6"+
		"\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00da\3\2\2\2\u00d8\u00d6\3\2"+
		"\2\2\u00d9\u00d1\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00db\3\2\2\2\u00db"+
		"\u00dc\7\36\2\2\u00dc\61\3\2\2\2\u00dd\u00e3\7\f\2\2\u00de\u00df\7\r\2"+
		"\2\u00df\u00e0\5\30\r\2\u00e0\u00e1\7\16\2\2\u00e1\u00e2\5\30\r\2\u00e2"+
		"\u00e4\3\2\2\2\u00e3\u00de\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e3\3\2"+
		"\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\7\17\2\2\u00e8"+
		"\u00e9\5\30\r\2\u00e9\u00ea\7\20\2\2\u00ea\63\3\2\2\2\31<AIRV[bfjqy\u0081"+
		"\u008b\u0096\u0099\u009c\u00a4\u00b4\u00bf\u00cd\u00d6\u00d9\u00e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}