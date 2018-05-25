package com.anshishagua.parser.semantic.function.date;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.date.Second;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:13
 */

public class SecondResolver implements TypeResolver<Second> {
    @Override
    public void resolve(Second node) throws SemanticException {
        node.setResultType(BasicType.Integer);
    }
}