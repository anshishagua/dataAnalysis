// Generated from /Users/lixiao/code/dataAnalysis/src/main/resources/ExpressionRule.g4 by ANTLR 4.7
package com.anshishagua.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionRuleParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionRuleVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(ExpressionRuleParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare(ExpressionRuleParser.CompareContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#numeric}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeric(ExpressionRuleParser.NumericContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#sign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSign(ExpressionRuleParser.SignContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#decimal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal(ExpressionRuleParser.DecimalContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(ExpressionRuleParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#intNumber}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntNumber(ExpressionRuleParser.IntNumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#longNumber}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLongNumber(ExpressionRuleParser.LongNumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(ExpressionRuleParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(ExpressionRuleParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ExpressionRuleParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#andCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndCondition(ExpressionRuleParser.AndConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#conditionRightHandSide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionRightHandSide(ExpressionRuleParser.ConditionRightHandSideContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ExpressionRuleParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(ExpressionRuleParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(ExpressionRuleParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#columnRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnRef(ExpressionRuleParser.ColumnRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#operand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperand(ExpressionRuleParser.OperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#summand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSummand(ExpressionRuleParser.SummandContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#plusMinus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusMinus(ExpressionRuleParser.PlusMinusContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(ExpressionRuleParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#mulDivMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivMod(ExpressionRuleParser.MulDivModContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(ExpressionRuleParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(ExpressionRuleParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionRuleParser#caseWhen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseWhen(ExpressionRuleParser.CaseWhenContext ctx);
}