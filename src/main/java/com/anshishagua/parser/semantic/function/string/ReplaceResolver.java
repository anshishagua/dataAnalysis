package com.anshishagua.parser.semantic.function.string;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.string.Replace;
import com.anshishagua.parser.semantic.TypeResolver;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午2:26
 */

public class ReplaceResolver implements TypeResolver<Replace> {
    @Override
    public void resolve(Replace node) throws SemanticException {
        List<Node> children = node.getChildren();

        for (Node child : children) {
            if (!child.getResultType().isString()) {
                throw new SemanticException(String.format("%s: %s is not %s type", node, child, BasicType.String));
            }
        }

        node.setResultType(BasicType.String);
    }
}