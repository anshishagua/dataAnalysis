package com.anshishagua.parser.semantic.function.date;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.date.Week;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:08
 */

public class WeekResolver implements TypeResolver<Week> {
    @Override
    public void resolve(Week node) throws SemanticException {
        Node child = node.getChild(0);

        if (!child.getResultType().isDate()) {
            throw new SemanticException(String.format("%s: %s is not %s type", node, child, BasicType.Date));
        }

        node.setResultType(BasicType.Integer);
    }
}