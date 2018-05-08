package com.anshishagua.parser.semantic.function.aggregation;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.aggregation.Sum;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/4/23
 * Time: 下午11:47
 */

public class SumResolver implements TypeResolver<Sum> {
    @Override
    public void resolve(Sum node) throws SemanticException {
        if (!node.getChild(0).getResultType().isNumeric()) {
            throw new SemanticException(String.format("%s not numeric", node.getChild(0)));
        }

        node.setResultType(BasicType.Double);
    }
}