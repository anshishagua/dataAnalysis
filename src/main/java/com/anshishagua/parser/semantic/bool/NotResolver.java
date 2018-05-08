package com.anshishagua.parser.semantic.bool;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.bool.Not;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/4/21
 * Time: 下午12:56
 */

public class NotResolver implements TypeResolver<Not> {
    @Override
    public void resolve(Not node) throws SemanticException {
        Node child = node.getChild(0);

        if (!child.getResultType().isBoolean()) {
            throw new SemanticException(String.format("%s: %s is not %s type", node, child, BasicType.Boolean));
        }

        node.setResultType(BasicType.Boolean);
    }
}