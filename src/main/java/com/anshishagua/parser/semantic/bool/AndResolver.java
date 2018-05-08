package com.anshishagua.parser.semantic.bool;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.bool.And;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/4/21
 * Time: 下午12:53
 */

public class AndResolver implements TypeResolver<And> {
    @Override
    public void resolve(And node) throws SemanticException {
        Node left = node.getChild(0);
        Node right = node.getChild(1);

        if (!left.getResultType().isBoolean()) {
            throw new SemanticException(String.format("%s: %s is not %s type", node, left, BasicType.Boolean));
        }

        if (!right.getResultType().isBoolean()) {
            throw new SemanticException(String.format("%s: %s is not %s type", node, right, BasicType.Boolean));
        }

        node.setResultType(BasicType.Boolean);
    }
}