package com.anshishagua.parser.semantic.function.conversion;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.conversion.ToBoolean;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:36
 */

public class ToBooleanResolver implements TypeResolver<ToBoolean> {
    @Override
    public void resolve(ToBoolean node) throws SemanticException {

        node.setResultType(BasicType.Boolean);
    }
}