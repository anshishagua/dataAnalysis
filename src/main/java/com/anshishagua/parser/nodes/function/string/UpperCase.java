package com.anshishagua.parser.nodes.function.string;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 上午10:40
 */

public class UpperCase extends UnaryFunctionNode {
    public UpperCase(List<Node> children) {
        super("upper_case", children);
    }

    @Override
    public String toSQL() {
        return String.format("upper(%s)", getChild(0).toSQL());
    }
}