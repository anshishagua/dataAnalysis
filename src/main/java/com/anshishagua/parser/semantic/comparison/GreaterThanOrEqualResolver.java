package com.anshishagua.parser.semantic.comparison;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.comparision.GreaterThanOrEqual;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/4/21
 * Time: 下午12:47
 */

public class GreaterThanOrEqualResolver implements TypeResolver<GreaterThanOrEqual> {
    @Override
    public void resolve(GreaterThanOrEqual node) throws SemanticException {
        Node left = node.getChild(0);
        Node right = node.getChild(1);

        if (!BasicType.theSameBasicType(left.getResultType(), right.getResultType())) {
            throw new SemanticException(String.format("%s could not compare with %s using %s", left, right, ">="));
        }

        node.setResultType(BasicType.Boolean);
    }
}