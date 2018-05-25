package com.anshishagua.parser.semantic.function.aggregation;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.aggregation.Max;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午10:52
 */

public class MaxResolver implements TypeResolver<Max> {
    @Override
    public void resolve(Max node) throws SemanticException {
        BasicType resultType = node.getChild(0).getResultType();

        if (resultType.isBoolean()) {
            throw new SemanticException(String.format("%s: type boolean could not be used in function max", node.getChild(0)));
        }

        node.setResultType(resultType);
    }
}