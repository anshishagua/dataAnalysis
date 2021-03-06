package com.anshishagua.parser.nodes.function.aggregation;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/23
 * Time: 下午11:33
 */

public class Sum extends UnaryFunctionNode implements AggregationNode {
    public Sum(List<Node> children) {
        super("sum", children);
    }
}