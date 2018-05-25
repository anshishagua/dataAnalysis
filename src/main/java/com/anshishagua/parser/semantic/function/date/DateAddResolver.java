package com.anshishagua.parser.semantic.function.date;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.date.DateAdd;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:04
 */

public class DateAddResolver implements TypeResolver<DateAdd> {
    @Override
    public void resolve(DateAdd node) throws SemanticException {
        Node child = node.getChild(0);

        if (!child.getResultType().isDate()) {
            throw new SemanticException(String.format("%s: %s is not %s type", node, child, BasicType.Date));
        }

        child = node.getChild(1);

        if (!(child.getResultType() == BasicType.Integer) && !(child.getResultType() == BasicType.Long)) {
            throw new SemanticException(String.format("%s: %s is not int or long type", node, child));
        }

        node.setResultType(BasicType.Date);
    }
}