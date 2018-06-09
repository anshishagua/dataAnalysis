package com.anshishagua.service;

import com.anshishagua.exceptions.UnableToJoinException;
import com.anshishagua.object.Index;
import com.anshishagua.object.PrimaryKey;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.TableRelation;
import com.anshishagua.object.Tag;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.sql.Condition;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.utils.CollectionUtils;
import com.anshishagua.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/4
 * Time: 下午10:54
 */

@Service
public class BasicSQLService {
    @Autowired
    private TagSQLGenerateService tagSQLGenerateService;
    @Autowired
    private IndexSQLGenerateService indexSQLGenerateService;

    public static class TreeNode {
        private int group;
        private String value;
        private TreeNode parent;
        private List<TreeNode> children;

        public TreeNode(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }

        public TreeNode(int group, String value, TreeNode parent) {
            this.group = group;
            this.value = value;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public void addChild(TreeNode child) {
            Objects.requireNonNull(child);

            this.children.add(child);
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public static TreeNode find(TreeNode node) {
            while (node.parent != null) {
                node = node.parent;
            }

            return node;
        }

        public static void union(TreeNode a, TreeNode b) {
            b.group = find(a).group;
            b.parent = a;
            a.children.add(b);
        }

        public static Set<String> getTableNames(TreeNode root) {
            Set<String> tableNames = new HashSet<>();

            getTableNames(root, tableNames);

            return tableNames;
        }

        private static void getTableNames(TreeNode root, Set<String> tableNames) {
            if (root == null) {
                return;
            }

            tableNames.add(root.value);

            for (TreeNode child : root.children) {
                getTableNames(child, tableNames);
            }
        }
    }

    @Autowired
    private TableRelationService tableRelationService;

    public String generateTempTableName() {
        return "tmp_table_" + UUID.randomUUID().toString().replace("-", "");
    }

    public PrimaryKey getPrimaryKey(Table table) {
        Objects.requireNonNull(table);

        List<TableColumn> primaryKeys = table.getPrimaryKeys();

        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.setTableName(table.getName());
        primaryKey.setKeyColumns(primaryKeys);

        return primaryKey;
    }

    public String createTemporaryTable(String tableName, List<TableColumn> columns) {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS ").append(StringUtils.backQuote(tableName));

        builder.append(" (");

        builder.append(columns.stream().map(column -> String.format("%s %s", StringUtils.backQuote(column.getName()), column.getDataType().getValue())).collect(Collectors.joining(", ")));

        builder.append(")");

        return builder.toString();
    }

    public String createTableSQL(Table table) {
        Objects.requireNonNull(table);

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS ").append(StringUtils.backQuote(table.getName()));

        builder.append(" (");

        builder.append(table.getColumns().stream().map(column -> String.format("%s %s", StringUtils.backQuote(column.getName()), column.getDataType().getValue())).collect(Collectors.joining(", ")));

        builder.append(")");

        builder.append(" ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n'");

        return builder.toString();
    }

    public String createTagSQL(Tag tag) {
        return tagSQLGenerateService.generateTagTableCreateSQL(tag);
    }

    public String createIndexSQL(Index index) {
        return indexSQLGenerateService.createIndexCreateSQL(index);
    }

    public String dropTable(String tableName) {
        return String.format("DROP TABLE IF EXISTS %s", StringUtils.backQuote(tableName));
    }

    public SQLGenerateResult generateTableCreateSQL(Table table) {
        Objects.requireNonNull(table);

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(StringUtils.backQuote(table.getName())).append("(");

        builder.append(table.getColumns().stream().map(column -> String.format("%s %s", StringUtils.backQuote(column.getName()), column.getDataType().getValue())).collect(Collectors.joining(", ")));

        builder.append(")");

        SQLGenerateResult result = new SQLGenerateResult();
        result.setSuccess(true);
        List<String> executeSQLs = new ArrayList<>();
        executeSQLs.add(builder.toString());

        result.setExecuteSQLs(executeSQLs);

        return result;
    }

    //tableNames中的所有表和targetTableName建立JOIN关系,先考虑所有表都和targetTableName直接关联
    /*
                          join
                         /     \
                       join     c
                      /    \
                     a      b

               a join b join c join d
     */
    public Join buildJoinClauses(Set<String> tableNames, String targetTableName) {
        Join join = null;

        tableNames.remove(targetTableName);

        for (String tableName : tableNames) {
            List<TableRelation> tableRelations = tableRelationService.getByTable(targetTableName, tableName);

            TableRelation relation = tableRelations.get(0);

            Node left = null;

            if (join == null) {
                left = new com.anshishagua.parser.nodes.sql.Table(targetTableName);
            } else {
                left = join;
            }

            Node right = new com.anshishagua.parser.nodes.sql.Table(tableName);
            Condition joinCondition = new Equal(new Column(relation.getLeftTable().getName(), relation.getLeftColumn().getName()),
                    new Column(relation.getRightTable().getName(), relation.getRightColumn().getName()));

            join = new Join(left, right, relation.getJoinType(), joinCondition);
        }

        return join;
    }

    public Join buildJoinClauses(TreeNode root) {
        Objects.requireNonNull(root);

        Join join = null;
        Node left = null;
        Node right = null;

        Queue<TreeNode> queue = new LinkedList<>();

        for (TreeNode child : root.getChildren()) {
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode parent = node.getParent();

            List<TableRelation> tableRelations = tableRelationService.getByTable(parent.getValue(), node.getValue());
            TableRelation relation = tableRelations.get(0);

            if (join == null) {
                left = new com.anshishagua.parser.nodes.sql.Table(root.getValue());
            } else {
                left = join;
            }

            right = new com.anshishagua.parser.nodes.sql.Table(node.value);
            Condition joinCondition = new Equal(new Column(relation.getLeftTable().getName(), relation.getLeftColumn().getName()),
                    new Column(relation.getRightTable().getName(), relation.getRightColumn().getName()));

            join = new Join(left, right, relation.getJoinType(), joinCondition);

            for (TreeNode child : node.getChildren()) {
                queue.add(child);
            }
        }

        return join;
    }

    /**
     *
     * 把模型按照模型之间的关系分成几棵树,每棵树上的模型之间是可以关联的,
     * 组与组之间的模型是没有关联关系的,使用并集合实现,两个模型如果可以关联,
     * 则左模型作为父亲,右模型作为左模型的儿子节点,最后如果只剩下一颗树
     * 则所有的模型是可关联的,否则是不可关联的
     *
     * @param tableNames
     * @return
     */
    public Join buildJoinClauses(Set<String> tableNames) throws UnableToJoinException {
        List<String> list = new ArrayList<>(tableNames);
        int size = list.size();

        TreeNode [] nodes = new TreeNode[size];

        for (int i = 0; i < list.size(); ++i) {
            nodes[i] = new TreeNode(i, list.get(i), null);
        }

        for (int i = 0; i < list.size(); ++i) {
            for (int j = 0; j < list.size(); ++j) {
                if (i == j) {
                    continue;
                }

                String a = list.get(i);
                String b = list.get(j);

                List<TableRelation> relations = tableRelationService.getByTable(a, b);

                TableRelation relation = relations.isEmpty() ? null : relations.get(0);

                if (relation != null) {
                    if (TreeNode.find(nodes[i]) != TreeNode.find(nodes[j])) {
                        TreeNode.union(nodes[i], nodes[j]);
                    }
                }
                else {
                    relations = tableRelationService.getByTable(a, b);

                    relation = relations.isEmpty() ? null : relations.get(0);

                    if (relation != null) {
                        if (TreeNode.find(nodes[j]) != TreeNode.find(nodes[i])) {
                            TreeNode.union(nodes[j], nodes[i]);
                        }
                    }
                }
            }
        }

        List<TreeNode> trees = new ArrayList<>();

        for (int i = 0; i < nodes.length; ++i) {
            if (nodes[i].parent == null) {
                trees.add(nodes[i]);
            }
        }

        if (CollectionUtils.hasMultiplyElement(trees)) {
            List<Set<String>> sets = new ArrayList<>(trees.size());

            for (TreeNode root : trees) {
                sets.add(TreeNode.getTableNames(root));
            }

            String errorMessage = String.format("维度和度量涉及的表分成以下几组,组内表间有连接关系,组间的表没有连接关系:%s", sets);

            throw new UnableToJoinException(errorMessage);
        }

        TreeNode root = trees.get(0);

        return buildJoinClauses(root);
    }

    private TreeNode buildTreeFromAllPath(List<List<String>> paths) {
        Objects.requireNonNull(paths);

        String rootTableName = paths.get(0).get(0);

        TreeNode root = new TreeNode(rootTableName);
        Map<String, TreeNode> treeNodeMap = new HashMap<>();
        treeNodeMap.put(rootTableName, root);

        for (List<String> path : paths) {
            TreeNode parent = root;

            for (int i = 1; i < path.size(); ++i) {
                String tableName = path.get(i);

                TreeNode current = null;

                if (!treeNodeMap.containsKey(tableName)) {
                    current = new TreeNode(tableName);
                    treeNodeMap.put(tableName, current);

                    parent.addChild(current);
                    current.setParent(parent);
                } else {
                    current = treeNodeMap.get(tableName);
                }

                parent = current;
            }
        }

        return root;
    }

    public Join buildJoinClauses2(Set<String> tableNames) throws UnableToJoinException {
        Objects.requireNonNull(tableNames);

        List<String> list = new ArrayList<>(tableNames);

        for (int i = 0; i < list.size(); ++i) {
            List<List<String>> allPath = new ArrayList<>();

            for (int j = 0; j < list.size(); ++j) {
                if (i == j) {
                    continue;
                }

                String leftTable = list.get(i);
                String rightTable = list.get(j);

                List<List<String>> paths = tableRelationService.findRelationPaths(leftTable, rightTable);

                if (paths.isEmpty() || paths.size() > 1) {
                    continue;
                } else {
                    allPath.add(paths.get(0));
                }
            }

            if (allPath.size() == tableNames.size() - 1) {
                TreeNode root = buildTreeFromAllPath(allPath);

                return buildJoinClauses(root);
            }
        }

        throw new UnableToJoinException();
    }
}