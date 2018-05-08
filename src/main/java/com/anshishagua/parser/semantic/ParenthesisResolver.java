package com.anshishagua.parser.semantic;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.nodes.priority.Parenthesis;

/**
 * User: lixiao
 * Date: 2018/4/21
 * Time: 下午12:34
 */

public class ParenthesisResolver implements TypeResolver<Parenthesis> {
    @Override
    public void resolve(Parenthesis node) throws SemanticException {
        node.setResultType(node.getChild(0).getResultType());
    }
}