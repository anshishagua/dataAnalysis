package com.anshishagua.parser.grammar;

import com.anshishagua.antlr4.ExpressionRuleLexer;
import com.anshishagua.antlr4.ExpressionRuleParser;
import com.anshishagua.parser.ExpressionVisitor;
import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.utils.TreeNodeWalker;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午3:23
 */

public class ExpressionParser {
    String expression;
    Node root;

    public ExpressionParser(String expression) {
        Objects.requireNonNull(expression);

        this.expression = expression;
    }

    public void parse() throws RecognitionException {
        CharStream inputStream = CharStreams.fromString(expression);
        ExpressionRuleLexer lexer = new ExpressionRuleLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ExpressionRuleParser parser = new ExpressionRuleParser(tokenStream);

        ParseTree parseTree = parser.start();
        ExpressionVisitor visitor = new ExpressionVisitor();

        Node root = visitor.visit(parseTree);

        this.root = root;

        //init parent info
        TreeNodeWalker.preOrder(root, (Node node) -> {
            node.getChildren().forEach(it -> {
                ((AbstractNode) it).setParent(node);
            });
        });
    }

    public Node getAstTree() {
        return root;
    }
}