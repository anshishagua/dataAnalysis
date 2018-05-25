package com.anshishagua.parser.nodes.function.aggregation;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 下午11:22
 */

public class Count extends UnaryFunctionNode implements AggregationNode {
    public Count(List<Node> children) {
        super("count", children);
    }
}