package com.anshishagua.parser.semantic.function.condition;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.comparision.IsNull;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午11:52
 */

public class IsNullResolver implements TypeResolver<IsNull> {
    @Override
    public void resolve(IsNull node) throws SemanticException {
        node.setResultType(BasicType.Boolean);
    }
}