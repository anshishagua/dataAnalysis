package com.anshishagua.parser.semantic.arithmetic;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.arithmetic.Mod;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/4/21
 * Time: 下午12:36
 */

public class ModResolver implements TypeResolver<Mod> {
    @Override
    public void resolve(Mod node) throws SemanticException {
        Node left = node.getChild(0);
        Node right = node.getChild(1);

        if (!left.getResultType().isNumeric()) {
            throw new SemanticException(left + " not numeric");
        } else if (!right.getResultType().isNumeric()) {
            throw new SemanticException(right + " not numeric");
        }

        node.setResultType(left.getResultType().getPriority() <= right.getResultType().getPriority() ?
                right.getResultType() : left.getResultType());
    }
}