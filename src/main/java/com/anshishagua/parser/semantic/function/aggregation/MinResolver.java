package com.anshishagua.parser.semantic.function.aggregation;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.aggregation.Min;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午10:53
 */

public class MinResolver implements TypeResolver<Min> {
    @Override
    public void resolve(Min node) throws SemanticException {
        BasicType resultType = node.getChild(0).getResultType();

        if (resultType.isBoolean()) {
            throw new SemanticException(String.format("%s: type boolean could not be used in function min", node.getChild(0)));
        }

        node.setResultType(resultType);
    }
}