package com.anshishagua.parser.nodes.function.conversion;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:48
 */

public class ToLong extends UnaryFunctionNode {
    public ToLong(List<Node> children) {
        super("to_long", children);
    }

    @Override
    public String toSQL() {
        return String.format("cast(%s as long)", getChild(0).toSQL());
    }
}