// Generated from /Users/lixiao/code/expressionParser/src/main/resources/ExpressionRule.g4 by ANTLR 4.7
package com.anshishagua.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionRuleParser}.
 */
public interface ExpressionRuleListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(ExpressionRuleParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(ExpressionRuleParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterCompare(ExpressionRuleParser.CompareContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitCompare(ExpressionRuleParser.CompareContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#numeric}.
	 * @param ctx the parse tree
	 */
	void enterNumeric(ExpressionRuleParser.NumericContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#numeric}.
	 * @param ctx the parse tree
	 */
	void exitNumeric(ExpressionRuleParser.NumericContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#sign}.
	 * @param ctx the parse tree
	 */
	void enterSign(ExpressionRuleParser.SignContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#sign}.
	 * @param ctx the parse tree
	 */
	void exitSign(ExpressionRuleParser.SignContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#decimal}.
	 * @param ctx the parse tree
	 */
	void enterDecimal(ExpressionRuleParser.DecimalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#decimal}.
	 * @param ctx the parse tree
	 */
	void exitDecimal(ExpressionRuleParser.DecimalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(ExpressionRuleParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(ExpressionRuleParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#stringContent}.
	 * @param ctx the parse tree
	 */
	void enterStringContent(ExpressionRuleParser.StringContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#stringContent}.
	 * @param ctx the parse tree
	 */
	void exitStringContent(ExpressionRuleParser.StringContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#intNumber}.
	 * @param ctx the parse tree
	 */
	void enterIntNumber(ExpressionRuleParser.IntNumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#intNumber}.
	 * @param ctx the parse tree
	 */
	void exitIntNumber(ExpressionRuleParser.IntNumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#longNumber}.
	 * @param ctx the parse tree
	 */
	void enterLongNumber(ExpressionRuleParser.LongNumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#longNumber}.
	 * @param ctx the parse tree
	 */
	void exitLongNumber(ExpressionRuleParser.LongNumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(ExpressionRuleParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(ExpressionRuleParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(ExpressionRuleParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(ExpressionRuleParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ExpressionRuleParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ExpressionRuleParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#andCondition}.
	 * @param ctx the parse tree
	 */
	void enterAndCondition(ExpressionRuleParser.AndConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#andCondition}.
	 * @param ctx the parse tree
	 */
	void exitAndCondition(ExpressionRuleParser.AndConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#conditionRightHandSide}.
	 * @param ctx the parse tree
	 */
	void enterConditionRightHandSide(ExpressionRuleParser.ConditionRightHandSideContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#conditionRightHandSide}.
	 * @param ctx the parse tree
	 */
	void exitConditionRightHandSide(ExpressionRuleParser.ConditionRightHandSideContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ExpressionRuleParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ExpressionRuleParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(ExpressionRuleParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(ExpressionRuleParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#columnRef}.
	 * @param ctx the parse tree
	 */
	void enterColumnRef(ExpressionRuleParser.ColumnRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#columnRef}.
	 * @param ctx the parse tree
	 */
	void exitColumnRef(ExpressionRuleParser.ColumnRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#operand}.
	 * @param ctx the parse tree
	 */
	void enterOperand(ExpressionRuleParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#operand}.
	 * @param ctx the parse tree
	 */
	void exitOperand(ExpressionRuleParser.OperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#summand}.
	 * @param ctx the parse tree
	 */
	void enterSummand(ExpressionRuleParser.SummandContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#summand}.
	 * @param ctx the parse tree
	 */
	void exitSummand(ExpressionRuleParser.SummandContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#plusMinus}.
	 * @param ctx the parse tree
	 */
	void enterPlusMinus(ExpressionRuleParser.PlusMinusContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#plusMinus}.
	 * @param ctx the parse tree
	 */
	void exitPlusMinus(ExpressionRuleParser.PlusMinusContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(ExpressionRuleParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(ExpressionRuleParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#mulDivMod}.
	 * @param ctx the parse tree
	 */
	void enterMulDivMod(ExpressionRuleParser.MulDivModContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#mulDivMod}.
	 * @param ctx the parse tree
	 */
	void exitMulDivMod(ExpressionRuleParser.MulDivModContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(ExpressionRuleParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(ExpressionRuleParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(ExpressionRuleParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(ExpressionRuleParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionRuleParser#caseWhen}.
	 * @param ctx the parse tree
	 */
	void enterCaseWhen(ExpressionRuleParser.CaseWhenContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionRuleParser#caseWhen}.
	 * @param ctx the parse tree
	 */
	void exitCaseWhen(ExpressionRuleParser.CaseWhenContext ctx);
}