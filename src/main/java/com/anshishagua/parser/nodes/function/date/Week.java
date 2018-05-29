package com.anshishagua.parser.nodes.function.date;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午2:50
 */

public class Week extends UnaryFunctionNode {
    public Week(List<Node> children) {
        super("week", children);
    }

    @Override
    public String toSQL() {
        return String.format("weekofyear(%s)", getChild(0).toSQL());
    }
}