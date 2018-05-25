package com.anshishagua.parser.semantic.function.string;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.string.Concat;
import com.anshishagua.parser.semantic.TypeResolver;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 上午11:58
 */

public class ConcatResolver implements TypeResolver<Concat> {
    @Override
    public void resolve(Concat node) throws SemanticException {
        List<Node> children = node.getChildren();

        for (Node child : children) {
            if (!child.getResultType().isString()) {
                throw new SemanticException(String.format("%s: %s is not %s type", node, child, BasicType.String));
            }
        }

        node.setResultType(BasicType.String);
    }
}