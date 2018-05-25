package com.anshishagua.parser.semantic.function.date;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.date.Hour;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:10
 */

public class HourResolver implements TypeResolver<Hour> {
    @Override
    public void resolve(Hour node) throws SemanticException {
        node.setResultType(BasicType.Integer);
    }
}