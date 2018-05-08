package com.anshishagua.parser.semantic.function.aggregation;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.aggregation.Count;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 下午11:23
 */

public class CountResolver implements TypeResolver<Count> {
    @Override
    public void resolve(Count node) throws SemanticException {
        node.setResultType(BasicType.Long);
    }
}