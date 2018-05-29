package com.anshishagua.parser.nodes.function.date;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.NoArgumentFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午2:52
 */

public class CurrentTimestamp extends NoArgumentFunctionNode {
    public CurrentTimestamp(List<Node> children) {
        super("current_timestamp", children);
    }
}