package com.anshishagua.parser.semantic;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.conditional.CaseWhen;

import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 上午11:21
 */

public class CaseWhenResolver implements TypeResolver<CaseWhen> {
    @Override
    public void resolve(CaseWhen node) throws SemanticException {
        BasicType resultType = null;

        for (BasicType type : node.getThenNodes().stream().map(it -> it.getResultType()).collect(Collectors.toList())) {
            if (resultType == null) {
                resultType = type;
            } else if (type.getPriority() > resultType.getPriority()) {
                resultType = type;
            }
        }

        BasicType type = node.getElseNode().getResultType();

        if (type.getPriority() > resultType.getPriority()) {
            resultType = type;
        }

        node.setResultType(resultType);
    }
}