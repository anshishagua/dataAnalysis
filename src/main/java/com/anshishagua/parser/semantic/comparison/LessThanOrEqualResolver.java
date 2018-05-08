package com.anshishagua.parser.semantic.comparison;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.comparision.LessThanOrEqual;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/4/21
 * Time: 下午12:48
 */

public class LessThanOrEqualResolver implements TypeResolver<LessThanOrEqual> {
    @Override
    public void resolve(LessThanOrEqual node) throws SemanticException {
        Node left = node.getChild(0);
        Node right = node.getChild(1);

        if (!BasicType.theSameBasicType(left.getResultType(), right.getResultType())) {
            throw new SemanticException(String.format("%s could not compare with %s using %s", left, right, "<="));
        }

        node.setResultType(BasicType.Boolean);
    }
}