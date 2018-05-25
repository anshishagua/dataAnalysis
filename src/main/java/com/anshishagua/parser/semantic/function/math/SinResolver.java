package com.anshishagua.parser.semantic.function.math;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.numeric.Sin;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 上午11:45
 */

public class SinResolver implements TypeResolver<Sin> {
    @Override
    public void resolve(Sin node) throws SemanticException {
        Node child = node.getChild(0);

        if (!child.getResultType().isNumeric()) {
            throw new SemanticException(child + " not numeric");
        }

        node.setResultType(BasicType.Double);
    }
}