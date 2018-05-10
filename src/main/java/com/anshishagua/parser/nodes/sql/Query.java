package com.anshishagua.parser.nodes.sql;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.bool.And;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/28
 * Time: 下午4:44
 */

public class Query extends AbstractNode<Void> {
    private List<Node> selectFields = new ArrayList<>();
    private List<Node> fromTables = new ArrayList<>();
    private Join joinClause;
    private Node whereCondition;
    private List<Node> orderBys = new ArrayList<>();
    private List<Node> groupBys = new ArrayList<>();
    private Node havingCondition;

    public Query() {

    }

    public void select(Node node) {
        Objects.requireNonNull(node);

        selectFields.add(node);
    }

    public void from(String tableName) {
        Objects.requireNonNull(tableName);

        fromTables.add(new Table(tableName));
    }

    public void from(Node node) {
        Objects.requireNonNull(node);

        fromTables.add(node);
    }

    public void where(Node condition) {
        Objects.requireNonNull(condition);

        this.whereCondition = condition;
    }

    public void and(Node condition) {
        Objects.requireNonNull(condition);

        if (this.whereCondition == null) {
            this.whereCondition = condition;
        } else {
            this.whereCondition = new And(this.whereCondition, condition);
        }
    }

    public void join(Join join) {
        Objects.requireNonNull(join);

        this.joinClause = join;
    }

    public void groupBy(Node node) {
        Objects.requireNonNull(node);

        groupBys.add(node);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("SELECT ");

        Iterator<Node> iterator = selectFields.iterator();

        while (iterator.hasNext()) {
            builder.append(iterator.next());

            if (iterator.hasNext()) {
                builder.append(", ");
            }
            else {
                builder.append(" ");
            }
        }

        iterator = fromTables.iterator();

        if (iterator.hasNext() || joinClause != null) {
            builder.append("FROM ");
        }

        while (iterator.hasNext()) {
            builder.append(iterator.next());

            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }

        if (joinClause != null) {
            builder.append(joinClause.toString());
        }

        if (whereCondition != null) {
            builder.append(" WHERE ").append(whereCondition);
            builder.append(" ");
        }

        if (!groupBys.isEmpty()) {
            builder.append("GROUP BY ");

            iterator = groupBys.iterator();

            while (iterator.hasNext()) {
                builder.append(iterator.next());

                if (iterator.hasNext()) {
                    builder.append(", ");
                }
                else {
                    builder.append(" ");
                }
            }
        }

        if (!orderBys.isEmpty()) {
            builder.append("ORDER BY ");

            iterator = orderBys.iterator();

            while (iterator.hasNext()) {
                builder.append(iterator.next());

                if (iterator.hasNext()) {
                    builder.append(", ");
                }
                else {
                    builder.append(" ");
                }
            }
        }

        if (havingCondition != null) {
            builder.append("HAVING ").append(havingCondition);
            builder.append(" ");
        }

        return builder.toString().trim();
    }
}