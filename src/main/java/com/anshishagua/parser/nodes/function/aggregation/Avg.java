package com.anshishagua.parser.nodes.function.aggregation;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.FunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 下午11:10
 */

public class Avg extends FunctionNode implements AggregationNode {
    public Avg(List<Node> children) {
        super("avg", children);
    }
}