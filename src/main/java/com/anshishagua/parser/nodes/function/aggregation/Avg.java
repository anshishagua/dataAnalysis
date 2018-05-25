package com.anshishagua.parser.nodes.function.aggregation;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.Arrays;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 下午11:10
 */

public class Avg extends UnaryFunctionNode implements AggregationNode {
    public Avg(Node child) {
        super("avg", Arrays.asList(new Node[] {child}));
    }

    public Avg(List<Node> children) {
        super("avg", children);
    }
}