package com.anshishagua.parser.semantic.bool;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.FunctionNode;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/4/23
 * Time: 下午11:41
 */

public class FunctionResolver implements TypeResolver<FunctionNode> {
    @Override
    public void resolve(FunctionNode node) throws SemanticException {
        String name = node.getName();

        node.setResultType(BasicType.Boolean);
    }
}
