package com.anshishagua.parser.semantic.function.date;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.date.Month;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:07
 */

public class MonthResolver implements TypeResolver<Month> {
    @Override
    public void resolve(Month node) throws SemanticException {
        Node child = node.getChild(0);

        if (!child.getResultType().isDate()) {
            throw new SemanticException(String.format("%s: %s is not %s type", node, child, BasicType.Date));
        }

        node.setResultType(BasicType.Integer);
    }
}