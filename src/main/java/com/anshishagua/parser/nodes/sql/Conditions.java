package com.anshishagua.parser.nodes.sql;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.bool.And;
import com.anshishagua.parser.nodes.bool.Or;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.comparision.GreaterThan;
import com.anshishagua.parser.nodes.comparision.GreaterThanOrEqual;
import com.anshishagua.parser.nodes.comparision.LessThan;
import com.anshishagua.parser.nodes.comparision.LessThanOrEqual;
import com.anshishagua.parser.nodes.comparision.NotEqual;

/**
 * User: lixiao
 * Date: 2018/4/28
 * Time: 下午5:08
 */

public class Conditions {
    public Conditions() {

    }

    public static Condition and(Node left, Node right) {
        return new And(left, right);
    }

    public static Condition or(Node left, Node right) {
        return new Or(left, right);
    }

    public static Condition greaterThan(Node left, Node right) {
        return new GreaterThan(left, right);
    }

    public static Condition greaterThanOrEqual(Node left, Node right) {
        return new GreaterThanOrEqual(left, right);
    }

    public static Condition lessThan(Node left, Node right) {
        return new LessThan(left, right);
    }

    public static Condition lessThanOrEqual(Node left, Node right) {
        return new LessThanOrEqual(left, right);
    }

    public static Condition equal(Node left, Node right) {
        return new Equal(left, right);
    }

    public static Condition notEqual(Node left, Node right) {
        return new NotEqual(left, right);
    }
}
