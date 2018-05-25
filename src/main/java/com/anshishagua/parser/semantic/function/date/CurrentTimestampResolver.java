package com.anshishagua.parser.semantic.function.date;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.date.CurrentTimestamp;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:02
 */

public class CurrentTimestampResolver implements TypeResolver<CurrentTimestamp> {
    @Override
    public void resolve(CurrentTimestamp node) throws SemanticException {
        node.setResultType(BasicType.Timestamp);
    }
}