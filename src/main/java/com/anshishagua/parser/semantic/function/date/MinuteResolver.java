package com.anshishagua.parser.semantic.function.date;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.date.Minute;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:11
 */

public class MinuteResolver implements TypeResolver<Minute> {
    @Override
    public void resolve(Minute node) throws SemanticException {
        node.setResultType(BasicType.Integer);
    }
}