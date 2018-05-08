package com.anshishagua.parser.semantic;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.nodes.conditional.CaseWhen;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 上午11:21
 */

public class CaseWhenResolver implements TypeResolver<CaseWhen> {
    @Override
    public void resolve(CaseWhen node) throws SemanticException {
        node.setResultType(node.getElseNode().getResultType());
    }
}