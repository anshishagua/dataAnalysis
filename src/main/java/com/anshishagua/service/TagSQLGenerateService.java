package com.anshishagua.service;

import com.anshishagua.object.ParseResult;
import com.anshishagua.object.PrimaryKey;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.TableRelation;
import com.anshishagua.object.Tag;
import com.anshishagua.object.TagValue;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.bool.And;
import com.anshishagua.parser.nodes.bool.In;
import com.anshishagua.parser.nodes.bool.NotIn;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.function.aggregation.AggregationNode;
import com.anshishagua.parser.nodes.function.string.Concat;
import com.anshishagua.parser.nodes.primitive.LongValue;
import com.anshishagua.parser.nodes.primitive.StringValue;
import com.anshishagua.parser.nodes.sql.Alias;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.sql.Condition;
import com.anshishagua.parser.nodes.sql.Insert;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.parser.nodes.sql.JoinType;
import com.anshishagua.parser.nodes.sql.Query;
import com.anshishagua.utils.CollectionUtils;
import com.anshishagua.utils.NodeUtils;
import com.anshishagua.utils.StringUtils;
import com.anshishagua.utils.TreeTransform;
import com.anshishagua.service.BasicSQLService.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 上午11:41
 */

@Service
public class TagSQLGenerateService {
    private static final Logger LOG = LoggerFactory.getLogger(TagSQLGenerateService.class);

    public static final String TAG_TABLE_NAME_PREFIX = "tag_";

    @Autowired
    private TagService tagService;
    @Autowired
    private BasicSQLService basicSQLService;
    @Autowired
    private TableService tableService;
    @Autowired
    private TableRelationService tableRelationService;

    public String generateTagTableName(long tagId) {
        return String.format("%s%d", TAG_TABLE_NAME_PREFIX, tagId);
    }

    public String generateTagTableCreateSQL(Tag tag) {
        Objects.requireNonNull(tag);

        Table table = tableService.getById(tag.getTableId());
        PrimaryKey primaryKey = basicSQLService.getPrimaryKey(table);

        String tagTableName = generateTagTableName(tag.getId());

        return String.format("CREATE TABLE IF NOT EXISTS %s (`id` %s, `tag_value_id` %s, `tag_value_value` %s)",
                tagTableName,
                primaryKey.getDataType().getValue(),
                "BIGINT",
                "STRING");
    }

    //将聚合操作转化为子查询
    //student.age >= 20 && student.age <= 30 && (avg(scores.score) >= 70 AND avg(scores.score) <= 90)
    //avg(scores.score) ==>
    //(select avg(scores.score) from student s inner join scores on s.id = scores.student_id where s.id = student.id group by s.id)
    private Query aggregationToSubQuery(AggregationNode node, Table targetTable) {
        Query query = new Query();

        List<TableColumn> primaryKeys = targetTable.getPrimaryKeys();

        PrimaryKey primaryKey = basicSQLService.getPrimaryKey(targetTable);

        if (primaryKey.isCombined()) {
            Concat concat = primaryKey.toConcatNode();

            query.select(new Alias(concat, "id"));
        } else {
            query.select(new Column(targetTable.getName(), primaryKeys.get(0).getName()));
        }

        query.select(node);

        Set<String> tableNames = NodeUtils.getTableNames(node);

        //只涉及一个表并且就是打标签表,无需做join
        if (CollectionUtils.hasSingleElement(tableNames) && tableNames.iterator().next().equals(targetTable.getName())) {
            query.from(new com.anshishagua.parser.nodes.sql.Table(targetTable.getName()));

            targetTable.getPrimaryKeys().forEach(tableColumn -> {
                query.groupBy(new Column(targetTable.getName(), tableColumn.getName()));
            });

            return query;
        }

        //涉及多个表join
        TreeNode root = null;
        try {
            root = buildJoinTree(targetTable.getName(), tableNames);
        } catch (Exception ex) {
            LOG.error("Failed to join trees", ex);
        }
        Join join = basicSQLService.buildJoinClauses(root);

        query.join(join);

        for (TableColumn column : primaryKeys) {
            query.groupBy(new Column(targetTable.getName(), column.getName()));
        }

        return query;
    }

    public TreeNode buildJoinTree(String targetTable, Set<String> joinTables) throws Exception {
        Objects.requireNonNull(targetTable);
        Objects.requireNonNull(joinTables);

        Map<String, TreeNode> map = new HashMap<>();
        TreeNode root = new TreeNode(targetTable);
        map.put(targetTable, root);

        for (String tableName : joinTables) {
            if (tableName.equals(targetTable)) {
                continue;
            }

            List<List<String>> paths = tableRelationService.findRelationPaths(targetTable, tableName);

            if (paths.isEmpty()) {
                throw new Exception(String.format("模型%s无法关联到贴标对象%s", tableName, targetTable));
            }

            if (paths.size() > 1) {
                throw new Exception(String.format("存在多条路径从模型%s关联到贴标对象%s", tableName, targetTable));
            }

            List<String> path = paths.get(0);

            for (int i = 1; i < path.size(); ++i) {
                TreeNode parent = map.get(path.get(i - 1));
                TreeNode child = map.get(path.get(i));

                if (child == null) {
                    child = new TreeNode(path.get(i));
                    map.put(path.get(i), child);
                }

                child.setParent(parent);
                parent.addChild(child);
            }
        }

        return root;
    }

    private Condition joinCondition(String leftTableName, String rightTableName, List<TableColumn> columns) {
        Objects.requireNonNull(leftTableName);
        Objects.requireNonNull(rightTableName);
        Objects.requireNonNull(columns);

        Node condition = null;

        for (TableColumn column : columns) {
            Node equal = new Equal(new Column(leftTableName, column.getName()), new Column(rightTableName, column.getName()));

            if (condition == null) {
                condition = equal;
            } else {
                condition = new And(condition, equal);
            }
        }

        return (Condition) condition;
    }

    private Query buildQuery(SQLGenerateResult sqlGenerateResult, Node root, Table targetTable, List<Node> aggregationNodes) {
        List<TableColumn> primaryKeys = targetTable.getPrimaryKeys();

        PrimaryKey primaryKey = basicSQLService.getPrimaryKey(targetTable);

        Set<String> tempTables = new HashSet<>();

        for (Node aggregationNode : aggregationNodes) {
            Query query = aggregationToSubQuery((AggregationNode) aggregationNode, targetTable);

            if (query.getJoinClause() != null) {
                sqlGenerateResult.addTableRelationIds(query.getJoinClause().getTableRelationIds());
            }

            String tempTableName = basicSQLService.generateTempTableName();
            tempTables.add(tempTableName);
            sqlGenerateResult.addTempTable(tempTableName);

            List<TableColumn> columns = new ArrayList<>();

            TableColumn column = new TableColumn();

            column.setName("id");
            column.setDataType(primaryKey.getDataType());
            columns.add(column);

            column = new TableColumn();
            column.setName("aggregation_result");
            column.setDataType(aggregationNode.getResultType().toDataType());
            columns.add(column);

            String sql = basicSQLService.createTemporaryTable(tempTableName, columns);

            sqlGenerateResult.addExecuteSQL(sql);

            Insert insert = new Insert(tempTableName, query, true);

            sqlGenerateResult.addExecuteSQL(insert.toString());

            TreeTransform.replace(aggregationNode, new Column(tempTableName, "aggregation_result"));
        }

        Query query = new Query();

        if (primaryKey.isCombined()) {
            query.select(new Alias(primaryKey.toConcatNode(), "id"));
        } else {
            query.select(new Column(targetTable.getName(), primaryKeys.get(0).getName()));
        }

        if (tempTables.isEmpty()) {
            query.from(new com.anshishagua.parser.nodes.sql.Table(targetTable.getName()));
        } else {
            Join join = null;

            for (String tempTableName : tempTables) {
                Condition joinCondition = primaryKey.isCombined() ?
                        new Equal(new Column(tempTableName, "id"), primaryKey.toConcatNode()) :
                        new Equal(new Column(tempTableName, "id"), new Column(targetTable.getName(), primaryKeys.get(0).getName()));

                if (join == null) {
                    join = new Join(new com.anshishagua.parser.nodes.sql.Table(targetTable.getName()),
                            new com.anshishagua.parser.nodes.sql.Table(tempTableName),
                            JoinType.INNER_JOIN, joinCondition);
                } else {
                    join = new Join(join, new com.anshishagua.parser.nodes.sql.Table(tempTableName), JoinType.INNER_JOIN, joinCondition);
                }
            }

            query.join(join);
        }

        query.where(root);

        return query;
    }

    public SQLGenerateResult generate(Tag tag) {
        Objects.requireNonNull(tag);

        SQLGenerateResult result = new SQLGenerateResult();

        String createTableSQL = generateTagTableCreateSQL(tag);

        String tagTableName = generateTagTableName(tag.getId());
        result.addExecuteSQL(createTableSQL);

        List<TagValue> tagValues = tag.getTagValues();

        ParseResult filterConditionResult = null;
        ParseResult computeConditionResult = null;

        boolean insertOverwrite = true;

        Set<String> dataSourceTables = new HashSet<>();
        Set<String> systemParameters = new HashSet<>();
        Set<Long> tableRelationIds = new HashSet<>();

        Table targetTable = tableService.getById(tag.getTableId());
        List<TableColumn> primaryKeys = targetTable.getPrimaryKeys();
        PrimaryKey primaryKey = basicSQLService.getPrimaryKey(targetTable);

        dataSourceTables.add(targetTable.getName());

        boolean first = true;

        for (TagValue tagValue : tagValues) {
            filterConditionResult = tagService.parseFilterCondition(tagValue.getFilterCondition(), tag.getTableId());

            if (!filterConditionResult.isSuccess()) {
                result.setSuccess(false);
                result.setErrorMessage(filterConditionResult.getErrorMessage());

                return result;
            }

            computeConditionResult = tagService.parseComputeCondition(tagValue.getComputeCondition(), tag.getTableId());

            if (!computeConditionResult.isSuccess()) {
                result.setSuccess(false);
                result.setErrorMessage(computeConditionResult.getErrorMessage());

                return result;
            }

            Query filterQuery = null;

            //has filter condition
            if (filterConditionResult.getAstTree() != null) {
                filterQuery = buildQuery(result, filterConditionResult.getAstTree(), targetTable, filterConditionResult.getAggregationNodes());

                if (filterQuery.getJoinClause() != null) {
                    tableRelationIds.addAll(filterQuery.getJoinClause().getTableRelationIds());
                }
            }

            Query computeQuery = buildQuery(result, computeConditionResult.getAstTree(), targetTable, computeConditionResult.getAggregationNodes());

            computeQuery.select(new LongValue(tagValue.getId()));
            computeQuery.select(new StringValue(tagValue.getValue()));

            if (filterQuery != null) {
                if (primaryKey.isCombined()) {
                    computeQuery.and(new In(primaryKey.toConcatNode(), filterQuery));
                } else {
                    computeQuery.and(new In(new Column(targetTable.getName(), primaryKeys.get(0).getName()), filterQuery));
                }
            }

            if (first) {
                first = !first;
            } else {
                Query filterTagIdQuery = new Query();
                Column tagColumn = new Column(tagTableName, "id");
                filterTagIdQuery.select(tagColumn);
                filterTagIdQuery.from(tagTableName);

                if (primaryKey.isCombined()) {
                    computeQuery.and(new NotIn(primaryKey.toConcatNode(), filterTagIdQuery));
                } else {
                    computeQuery.and(new NotIn(new Column(targetTable.getName(), primaryKeys.get(0).getName()), filterTagIdQuery));
                }
            }

            if (computeQuery.getJoinClause() != null) {
                tableRelationIds.addAll(computeQuery.getJoinClause().getTableRelationIds());
            }

            Insert insert = new Insert();
            insert.setOverwrite(insertOverwrite);
            insert.setTableName(tagTableName);
            insert.setQuery(computeQuery);

            result.addExecuteSQL(insert.toString());

            insertOverwrite = false;

            dataSourceTables.addAll(filterConditionResult.getTables().stream().map(it -> it.getName()).collect(Collectors.toList()));
            dataSourceTables.addAll(computeConditionResult.getTables().stream().map(it -> it.getName()).collect(Collectors.toList()));
            systemParameters.addAll(filterConditionResult.getSystemParameters().stream().map(it -> it.getName()).collect(Collectors.toSet()));
            systemParameters.addAll(computeConditionResult.getSystemParameters().stream().map(it -> it.getName()).collect(Collectors.toSet()));
        }

        result.setSuccess(true);
        result.addDataSourceTables(dataSourceTables);
        result.addSystemParameters(systemParameters);
        result.addTableRelationIds(tableRelationIds);

        Set<String> targetTables = new HashSet<>(Arrays.asList(tagTableName));
        result.setTargetTables(targetTables);

        List<String> tempTableNames = result.getTempTables();

        for (String tempTableName : tempTableNames) {
            result.addExecuteSQL(basicSQLService.dropTable(tempTableName));
        }

        return result;
    }
}