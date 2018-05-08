package com.anshishagua.utils;

import com.anshishagua.parser.nodes.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午3:33
 */

public class TreeNodeWalker {
    public static void preOrder(Node root, Consumer<Node> consumer) {
        Objects.requireNonNull(consumer);

        if (root == null) {
            return;
        }

        consumer.accept(root);

        for (Node child : root.getChildren()) {
            preOrder(child, consumer);
        }
    }

    public static void postOrder(Node root, Consumer<Node> consumer) {
        Objects.requireNonNull(consumer);

        if (root == null) {
            return;
        }

        for (Node child : root.getChildren()) {
            postOrder(child, consumer);
        }

        consumer.accept(root);
    }

    public static void leafToRootOrder(Node root, Consumer<Node> consumer) {
        Objects.requireNonNull(consumer);

        Queue<Node> queue = new LinkedList<>();

        queue.offer(root);

        List<Node> nodes = new ArrayList<>();

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            nodes.add(node);

            for (Node child : node.getChildren()) {
                queue.add(child);
            }
        }

        Collections.reverse(nodes);

        for (Node node : nodes) {
            consumer.accept(node);
        }
    }
}