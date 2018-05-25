package com.anshishagua.parser.nodes.function.aggregation;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午10:51
 */

public class Min extends UnaryFunctionNode implements AggregationNode {
    public Min(List<Node> children) {
        super("min", children);
    }
}