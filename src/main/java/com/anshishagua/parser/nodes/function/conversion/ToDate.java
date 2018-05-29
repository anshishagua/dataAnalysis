package com.anshishagua.parser.nodes.function.conversion;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:32
 */

public class ToDate extends UnaryFunctionNode {
    public ToDate(List<Node> children) {
        super("to_date", children);
    }

    @Override
    public String toSQL() {
        return String.format("cast(%s as date)", getChild(0).toSQL());
    }
}