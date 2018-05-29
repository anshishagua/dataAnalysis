package com.anshishagua.parser.nodes.function.conversion;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:32
 */

public class ToDouble extends UnaryFunctionNode {
    public ToDouble(List<Node> children) {
        super("to_double", children);
    }

    @Override
    public String toSQL() {
        return String.format("cast(%s as double)", getChild(0).toSQL());
    }
}