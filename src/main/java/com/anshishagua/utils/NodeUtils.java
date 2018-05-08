package com.anshishagua.utils;

import com.anshishagua.object.Table;
import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.bool.Or;
import com.anshishagua.parser.nodes.sql.Column;

import java.util.HashSet;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/4/28
 * Time: 上午10:04
 */

public class NodeUtils {
    public static Set<String> getTableNames(Node root) {
        Set<String> tables = new HashSet<>();

        getTableNames(root, tables);

        return tables;
    }

    private static void getTableNames(Node root, Set<String> set) {
        TreeNodeWalker.preOrder(root, node -> {
            if (node instanceof Column) {
                set.add(((Column) node).getTableName());
            }
        });
    }

    public static Set<String> getColumnNames(Node root) {
        Set<String> columnNames = new HashSet<>();

        getTableNames(root, columnNames);

        return columnNames;
    }

    private static void getColumnNames(Node root, Set<String> set) {
        TreeNodeWalker.preOrder(root, node -> {
            if (node instanceof Column) {
                set.add(((Column) node).getTableName() + "." + ((Column) node).getColumnName());
            }
        });
    }

    public static Node orToAnd(Node root) {
        if (root == null) {
            return root;
        }

        if (root instanceof Or) {
            Node newRoot = root.negate();

            AbstractNode parent = (AbstractNode) root.getParent();

            if (parent != null) {
                int index = root.getIndex();

                parent.setChild(index, newRoot);
            }

            return newRoot;
        }

        if (root.getChildren().isEmpty()) {
            return root;
        }

        for (int i = 0; i < root.getChildren().size(); ++i) {
            Node child = root.getChildren().get(i);

            Node newChild = child.negate();

            ((AbstractNode) newChild).setParent(root);
            ((AbstractNode) newChild).setIndex(i);

            root.getChildren().set(i, newChild);
        }

        return root;
    }
}
