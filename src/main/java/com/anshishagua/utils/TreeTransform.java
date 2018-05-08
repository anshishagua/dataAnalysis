package com.anshishagua.utils;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.bool.And;
import com.anshishagua.parser.nodes.comparision.GreaterThan;
import com.anshishagua.parser.nodes.primitive.BooleanValue;
import com.anshishagua.parser.nodes.primitive.DoubleValue;
import com.anshishagua.parser.nodes.primitive.IntegerValue;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/4
 * Time: 下午4:42
 */

public class TreeTransform {
    public static Node replace(Node original, Node replaced) {
        Objects.requireNonNull(original);
        Objects.requireNonNull(replaced);

        Node parent = original.getParent();

        Node root = null;

        if (parent != null) {
            int index = original.getIndex();

            parent.getChildren().set(index, replaced);
            replaced.setParent(parent);

            while (parent != null) {
                root = parent;
                parent = parent.getParent();
            }
        } else {
            //original is root

            root = replaced;
        }

        return root;
    }

    public static void main(String [] args) {
        Node a = new IntegerValue(1);
        Node b = new DoubleValue(2);

        Node and = new And(new GreaterThan(a, b), new BooleanValue(true));

        Node replace = new IntegerValue(33);

        Node root = TreeTransform.replace(a, replace);

        System.out.println(root);
    }
}