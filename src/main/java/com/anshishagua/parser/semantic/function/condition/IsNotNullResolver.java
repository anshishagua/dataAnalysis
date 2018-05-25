package com.anshishagua.parser.semantic.function.condition;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.comparision.IsNotNull;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午11:53
 */

public class IsNotNullResolver implements TypeResolver<IsNotNull> {
    @Override
    public void resolve(IsNotNull node) throws SemanticException {
        node.setResultType(BasicType.Boolean);
    }
}