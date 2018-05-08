package com.anshishagua.service;

import com.anshishagua.object.ParseResult;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.Tag;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.bool.In;
import com.anshishagua.parser.nodes.function.aggregation.AggregationNode;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.parser.nodes.sql.Query;
import com.anshishagua.utils.CollectionUtils;
import com.anshishagua.utils.NodeUtils;
import com.anshishagua.utils.TreeTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 上午11:41
 */

@Service
public class TagSQLGenerateService {
    @Autowired
    private TagService tagService;
    @Autowired
    private BasicSQLService basicSQLService;

    //将聚合操作转化为子查询
    //student.age >= 20 && student.age <= 30 && (avg(scores.score) >= 70 AND avg(scores.score) <= 90)
    //avg(scores.score) ==>
    //(select avg(scores.score) from student s inner join scores on s.id = scores.student_id where s.id = student.id group by s.id)
    private Query aggregationToSubQuery(AggregationNode node, Table targetTable) {
        Query query = new Query();

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
        Join join = basicSQLService.buildJoinClauses(tableNames, targetTable.getName());
        query.join(join);

        List<TableColumn> primaryKeys = targetTable.getPrimaryKeys();

        for (TableColumn primaryKey : primaryKeys) {
            query.groupBy(new Column(targetTable.getName(), primaryKey.getName()));
        }

        return query;
    }

    private Query buildQuery(Node root, Table targetTable, List<Node> aggregationNodes) {
        for (Node aggregationNode : aggregationNodes) {
            Query query = aggregationToSubQuery((AggregationNode) aggregationNode, targetTable);

            root = TreeTransform.replace(aggregationNode, query);
        }

        Query query = new Query();
        TableColumn primaryKey = targetTable.getPrimaryKeys().get(0);

        query.select(new Column(targetTable.getName(), primaryKey.getName()));

        query.from(new com.anshishagua.parser.nodes.sql.Table(targetTable.getName()));

        query.where(root);

        return query;
    }

    public SQLGenerateResult genereate(Tag tag) {
        Objects.requireNonNull(tag);

        SQLGenerateResult result = new SQLGenerateResult();

        String filterCondition = tag.getFilterCondition();
        String computeCondition = tag.getComputeCondition();

        ParseResult filterConditionResult = tagService.parseFilterCondition(filterCondition);

        if (!filterConditionResult.isSuccess()) {
            result.setSuccess(false);
            result.setErrorMessage(filterConditionResult.getErrorMessage());
        }

        ParseResult computeConditionResult = tagService.parseComputeCondition(computeCondition);

        if (!computeConditionResult.isSuccess()) {
            result.setSuccess(false);
            result.setErrorMessage(computeConditionResult.getErrorMessage());
        }

        Table targetTable = tag.getTable();
        TableColumn primaryKey = targetTable.getPrimaryKeys().get(0);

        Query filterQuery = null;

        //has filter condition
        if (filterConditionResult.getAstTree() != null) {
            filterQuery = buildQuery(filterConditionResult.getAstTree(), targetTable, filterConditionResult.getAggregationNodes());
        }

        Query computeQuery = buildQuery(computeConditionResult.getAstTree(), targetTable, computeConditionResult.getAggregationNodes());

        if (filterQuery != null) {
            computeQuery.and(new In(new Column(targetTable.getName(), primaryKey.getName()), filterQuery));
        }

        result.setSuccess(true);
        Set<String> dataSourceTables = new HashSet<>();
        dataSourceTables.add(targetTable.getName());
        dataSourceTables.addAll(filterConditionResult.getTables().stream().map(it -> it.getName()).collect(Collectors.toList()));
        dataSourceTables.addAll(computeConditionResult.getTables().stream().map(it -> it.getName()).collect(Collectors.toList()));

        result.setDataSourceTables(dataSourceTables);

        List<String> executeSQLs = new ArrayList<>();
        executeSQLs.add(computeQuery.toString());

        result.setExecuteSQLs(executeSQLs);

        return result;
    }
}