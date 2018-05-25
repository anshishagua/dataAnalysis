package com.anshishagua.parser.semantic.function.string;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.string.LowerCase;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午2:25
 */

public class LowerCaseResolver implements TypeResolver<LowerCase> {
    @Override
    public void resolve(LowerCase node) throws SemanticException {
        Node child = node.getChild(0);

        if (!child.getResultType().isString()) {
            throw new SemanticException(String.format("%s: %s is not %s type", node, child, BasicType.String));
        }

        node.setResultType(BasicType.String);
    }
}