package com.anshishagua.parser.semantic.function.condition;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.comparision.Like;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午11:56
 */

public class LikeResolver implements TypeResolver<Like> {
    @Override
    public void resolve(Like node) throws SemanticException {
        for (int i = 0; i < 2; ++i) {
            if (!node.getChild(i).getResultType().isString()) {
                throw new SemanticException(String.format("%s: type:%s is not string", node.getChild(i), node.getChild(i).getResultType()));
            }
        }

        node.setResultType(BasicType.Boolean);
    }
}