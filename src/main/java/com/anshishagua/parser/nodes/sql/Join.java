package com.anshishagua.parser.nodes.sql;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.comparision.Equal;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/5/4
 * Time: 下午11:35
 */

public class Join extends AbstractNode<Void> {
    private Node left;
    private Node right;
    private JoinType joinType;
    private Condition joinCondition;
    private Set<String> joinTables = new HashSet<>();

    public Join(Node left, Node right, JoinType joinType, Condition joinCondition) {
        super(left, right);

        this.left = left;
        this.right = right;
        this.joinType = joinType;
        this.joinCondition = joinCondition;

        if (left instanceof Table) {
            joinTables.add(((Table) left).getTableName());
        } else if (left instanceof Join) {
            joinTables.addAll(((Join) left).getJoinTables());
        }

        if (right instanceof Table) {
            joinTables.add(((Table) right).getTableName());
        } else if (right instanceof Join) {
            joinTables.addAll(((Join) right).getJoinTables());
        }
    }

    public Set<String> getJoinTables() {
        return joinTables;
    }

    public Condition getJoinCondition() {
        return joinCondition;
    }

    /*                         join
                          join     d
                    join       c
                  a     b

                        a inner join b on b.id = a.id inner join c on c.id = b.id

     */


    @Override
    public String toString() {
        return String.format("%s %s %s ON (%s)", left.toString(), joinType.toSQL(), right.toString(), joinCondition.toString());
    }

    public static void main(String [] args) {
        Join join = new Join(new Table("a"), new Table("b"), JoinType.INNER_JOIN, new Equal(new Column("a", "id"), new Column("b", "id")));

        join = new Join(join, new Table("c"), JoinType.INNER_JOIN, new Equal(new Column("c", "id"), new Column("b", "id")));

        System.out.println(join);
    }
}