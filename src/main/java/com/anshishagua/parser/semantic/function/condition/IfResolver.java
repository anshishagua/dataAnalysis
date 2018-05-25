package com.anshishagua.parser.semantic.function.condition;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.condition.If;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午11:28
 */

public class IfResolver implements TypeResolver<If> {
    @Override
    public void resolve(If node) throws SemanticException {
        Node condition = node.getChild(0);

        if (!condition.getResultType().isBoolean()) {
            throw new SemanticException(String.format("[%s: type:%s] is not boolean", condition, condition.getResultType()));
        }

        BasicType leftType = node.getChild(1).getResultType();
        BasicType rightType = node.getChild(2).getResultType();

        if (leftType.isBoolean()) {
            if (!rightType.isBoolean()) {
                throw new SemanticException(String.format("[%s: type:%s] is not boolean", node.getChild(2), rightType));
            }

            node.setResultType(BasicType.Boolean);
        } else if (leftType.isNumeric()) {
            if (!rightType.isNumeric()) {
                throw new SemanticException(String.format("[%s: type:%s] is not numeric", node.getChild(2), rightType));
            }

            node.setResultType(leftType.getPriority() > rightType.getPriority() ? leftType : rightType);
        } else if (leftType.isTimestamp()) {
            if (!rightType.isTimestamp()) {
                throw new SemanticException(String.format("[%s: type:%s] is not timestamp", node.getChild(2), rightType));
            }

            node.setResultType(BasicType.Timestamp);
        } else if (leftType.isDate()) {
            if (!rightType.isDate()) {
                throw new SemanticException(String.format("[%s: type:%s] is not date", node.getChild(2), rightType));
            }

            node.setResultType(BasicType.Date);
        } else if (leftType.isString()) {
            if (!rightType.isString()) {
                throw new SemanticException(String.format("[%s: type:%s] is not string", node.getChild(2), rightType));
            }

            node.setResultType(BasicType.String);
        }
    }
}