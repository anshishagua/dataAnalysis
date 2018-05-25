package com.anshishagua.parser.nodes.function.date;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.BinaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午2:52
 */

public class DateDiff extends BinaryFunctionNode {
    public DateDiff(List<Node> children) {
        super("date_diff", children);
    }
}