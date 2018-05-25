package com.anshishagua.parser.semantic.function.string;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.string.Trim;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午11:19
 */

public class TrimResolver implements TypeResolver<Trim> {
    @Override
    public void resolve(Trim node) throws SemanticException {
        Node child = node.getChild(0);

        if (!child.getResultType().isString()) {
            throw new SemanticException(String.format("%s: %s is not %s type", node, child, BasicType.String));
        }

        node.setResultType(BasicType.String);
    }
}