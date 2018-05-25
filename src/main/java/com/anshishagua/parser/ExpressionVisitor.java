package com.anshishagua.parser;

import com.anshishagua.parser.nodes.comparision.Like;
import com.anshishagua.parser.nodes.function.FunctionNode;
import com.anshishagua.parser.nodes.function.FunctionRegistry;
import com.anshishagua.antlr4.ExpressionRuleBaseVisitor;
import com.anshishagua.antlr4.ExpressionRuleParser;
import com.anshishagua.parser.nodes.bool.And;
import com.anshishagua.parser.nodes.function.aggregation.Sum;
import com.anshishagua.parser.nodes.primitive.BooleanValue;
import com.anshishagua.parser.nodes.conditional.CaseWhen;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.arithmetic.Divide;
import com.anshishagua.parser.nodes.primitive.DoubleValue;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.comparision.GreaterThan;
import com.anshishagua.parser.nodes.comparision.GreaterThanOrEqual;
import com.anshishagua.parser.nodes.primitive.IntegerValue;
import com.anshishagua.parser.nodes.comparision.IsNotNull;
import com.anshishagua.parser.nodes.comparision.IsNull;
import com.anshishagua.parser.nodes.comparision.LessThan;
import com.anshishagua.parser.nodes.comparision.LessThanOrEqual;
import com.anshishagua.parser.nodes.primitive.LongValue;
import com.anshishagua.parser.nodes.arithmetic.Minus;
import com.anshishagua.parser.nodes.arithmetic.Mod;
import com.anshishagua.parser.nodes.arithmetic.Multiply;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.comparision.NotEqual;
import com.anshishagua.parser.nodes.comparision.NotLike;
import com.anshishagua.parser.nodes.bool.Not;
import com.anshishagua.parser.nodes.bool.Or;
import com.anshishagua.parser.nodes.primitive.Variable;
import com.anshishagua.parser.nodes.priority.Parenthesis;
import com.anshishagua.parser.nodes.arithmetic.Plus;
import com.anshishagua.parser.nodes.primitive.StringValue;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午5:58
 */

public class ExpressionVisitor extends ExpressionRuleBaseVisitor<Node> {
    @Override
    public Node visitStart(ExpressionRuleParser.StartContext context) {
        return visit(context.expression());
    }

    @Override
    public Node visitCompare(ExpressionRuleParser.CompareContext context) {
        return visitChildren(context);
    }

    @Override
    public Node visitNumeric(ExpressionRuleParser.NumericContext context) {
        return visitChildren(context);
    }

    @Override
    public Node visitSign(ExpressionRuleParser.SignContext context) {
        return visitChildren(context);
    }

    @Override
    public Node visitDecimal(ExpressionRuleParser.DecimalContext context) {
        StringBuilder builder = new StringBuilder();

        if (context.sign() != null && context.sign().getText().equals("-")) {
            builder.append("-");
        }

        if (context.number().size() == 1) {
            builder.append("0.").append(context.number(0).getText());
        } else {
            builder.append(context.number(0).getText()).append(".").append(context.number(1).getText());
        }

        return new DoubleValue(Double.parseDouble(builder.toString()));
    }

    @Override
    public Node visitString(ExpressionRuleParser.StringContext context) {
        String value = context.getText();

        return new StringValue(value.substring(1, value.length() - 1));
    }

    @Override
    public Node visitIntNumber(ExpressionRuleParser.IntNumberContext context) {
        String string = context.getText();

        if (context.sign() != null && context.sign().getText().equals("-")) {
            string = "-" + string;
        }

        return new IntegerValue(Integer.parseInt(string));
    }

    @Override
    public Node visitLongNumber(ExpressionRuleParser.LongNumberContext context) {
        String string = context.getText();

        if (context.sign() != null && context.sign().getText().equals("-")) {
            string = "-" + string;
        }

        return new LongValue(Long.parseLong(string));
    }

    @Override
    public Node visitNumber(ExpressionRuleParser.NumberContext context) {
        return visitChildren(context);
    }

    @Override
    public Node visitCondition(ExpressionRuleParser.ConditionContext context) {
        if (context.Not() != null) {
            return new Not(visit(context.condition()));
        }

        Node node = visit(context.operand());

        if (context.conditionRightHandSide() == null) {
            return node;
        }

        if (context.conditionRightHandSide().compare() != null) {
            if (context.conditionRightHandSide().compare().GreaterThan() != null) {
                Node right = visit(context.conditionRightHandSide().operand(0));

                return new GreaterThan(node, right);
            } else if (context.conditionRightHandSide().compare().GreaterThanOrEqual() != null) {
                Node right = visit(context.conditionRightHandSide().operand(0));

                return new GreaterThanOrEqual(node, right);
            } else if (context.conditionRightHandSide().compare().LessThan() != null) {
                Node right = visit(context.conditionRightHandSide().operand(0));

                return new LessThan(node, right);
            } else if (context.conditionRightHandSide().compare().LessThanOrEqual() != null) {
                Node right = visit(context.conditionRightHandSide().operand(0));

                return new LessThanOrEqual(node, right);
            } else if (context.conditionRightHandSide().compare().Equal() != null) {
                Node right = visit(context.conditionRightHandSide().operand(0));

                return new Equal(node, right);
            } else if (context.conditionRightHandSide().compare().NotEqual() != null) {
                Node right = visit(context.conditionRightHandSide().operand(0));

                return new NotEqual(node, right);
            }
        }

        if (context.conditionRightHandSide().Is() != null && context.conditionRightHandSide().Null() != null) {
            if (context.conditionRightHandSide().Not() != null) {
                return new IsNotNull(node);
            } else {
                return new IsNull(node);
            }
        }

        if (context.conditionRightHandSide().Not() != null && context.conditionRightHandSide().Like() != null) {
            return new NotLike(node, visit(context.conditionRightHandSide().operand(0)));
        }

        if (context.conditionRightHandSide().Not() != null) {
            return new Not(visit(context.conditionRightHandSide().operand(0)));
        }

        if (context.conditionRightHandSide().Like() != null) {
            return new Like(node, visit(context.conditionRightHandSide().operand(0)));
        }

        return visitChildren(context);
    }

    @Override
    public Node visitExpression(ExpressionRuleParser.ExpressionContext context) {
        Node node = visit(context.andCondition(0));

        for (int i = 1; i < context.andCondition().size(); ++i) {
            Node right = visit(context.andCondition(i));

            node = new Or(node, right);
        }

        return node;
    }

    @Override
    public Node visitAndCondition(ExpressionRuleParser.AndConditionContext context) {
        Node node = visit(context.condition(0));

        for (int i = 1; i < context.condition().size(); ++i) {
            Node right = visit(context.condition(i));

            node = new And(node, right);
        }

        return node;
    }

    @Override
    public Node visitConditionRightHandSide(ExpressionRuleParser.ConditionRightHandSideContext context) {
        return visitChildren(context);
    }

    @Override
    public Node visitValue(ExpressionRuleParser.ValueContext context) {
        if (context.Boolean() != null) {
            return new BooleanValue(Boolean.valueOf(context.Boolean().getText()));
        }

        if (context.Null() != null) {
            return null;
        }

        if (context.id() != null) {
            return new Variable(context.id().getText());
        }

        return visitChildren(context);
    }

    @Override
    public Node visitColumnRef(ExpressionRuleParser.ColumnRefContext context) {
        return new Column(context.ID(0).getText(), context.ID(1).getText());
    }

    @Override
    public Node visitId(ExpressionRuleParser.IdContext context) {
        return visitChildren(context);
    }

    @Override
    public Node visitOperand(ExpressionRuleParser.OperandContext context) {
        return visitChildren(context);
    }

    @Override
    public Node visitSummand(ExpressionRuleParser.SummandContext context) {
        Node node = visit(context.factor(0));

        for (int i = 0; i < context.plusMinus().size(); ++i) {
            Node right = visit(context.factor(i + 1));

            if (context.plusMinus(i).Plus() != null) {
                node = new Plus(node, right);
            } else if (context.plusMinus(i).Minus() != null) {
                node = new Minus(node, right);
            }
        }

        return node;
    }

    @Override
    public Node visitFactor(ExpressionRuleParser.FactorContext context) {
        Node node = visit(context.term(0));

        for (int i = 0; i < context.mulDivMod().size(); ++i) {
            Node right = visit(context.term(i + 1));

            if (context.mulDivMod(i).Multiply() != null) {
                node = new Multiply(node, right);
            } else if (context.mulDivMod(i).Divide() != null) {
                node = new Divide(node, right);
            } else if (context.mulDivMod(i).Mod() != null) {
                node = new Mod(node, right);
            }
        }

        return node;
    }

    @Override
    public Node visitTerm(ExpressionRuleParser.TermContext context) {
        if (context.LeftParen() != null) {
            return new Parenthesis(visit(context.expression()));
        }

        if (context.Plus() != null) {
            return visit(context.term());
        }

        if (context.Minus() != null) {
            return new Multiply(new IntegerValue(-1), visit(context.term()));
        }

        return visitChildren(context);
    }

    @Override
    public Node visitFunction(ExpressionRuleParser.FunctionContext context) {
        String functionName = context.ID().getText();

        int size = context.expression().size();

        List<Node> children = new ArrayList<>(size);

        for (int i = 0; i < size; ++i) {
            children.add(visit(context.expression(i)));
        }

        return new FunctionNode(functionName, children);
    }

    @Override
    public Node visitCaseWhen(ExpressionRuleParser.CaseWhenContext context) {
        int size = context.expression().size();

        Node elseNode = visit(context.expression(size - 1));

        List<Node> whenNodes = new ArrayList<>(size / 2);
        List<Node> thenNodes = new ArrayList<>(size / 2);

        for (int i = 0; i < size - 1; i += 2) {
            Node whenNode = visit(context.expression(i));
            Node thenNode = visit(context.expression(i + 1));

            whenNodes.add(whenNode);
            thenNodes.add(thenNode);
        }

        return new CaseWhen(whenNodes, thenNodes, elseNode);
    }
}