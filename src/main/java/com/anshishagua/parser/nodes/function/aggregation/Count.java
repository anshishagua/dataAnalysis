package com.anshishagua.parser.nodes.function.aggregation;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.FunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 下午11:22
 */

public class Count extends FunctionNode implements AggregationNode {
    public Count(List<Node> children) {
        super("count", children);
    }

    @Override
    public int requiredArgumentSize() {
        return 1;
    }
}