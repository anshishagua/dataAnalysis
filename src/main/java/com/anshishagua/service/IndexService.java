package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.mybatis.mapper.IndexDimensionMapper;
import com.anshishagua.mybatis.mapper.IndexMapper;
import com.anshishagua.mybatis.mapper.IndexMetricMapper;
import com.anshishagua.object.CronExpressionConstants;
import com.anshishagua.object.Index;
import com.anshishagua.object.IndexDimension;
import com.anshishagua.object.IndexMetric;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.ParseResult.ParseType;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TaskType;
import com.anshishagua.parser.grammar.ExpressionParser;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.aggregation.AggregationNode;
import com.anshishagua.parser.semantic.SemanticAnalyzer;
import com.anshishagua.utils.AssertUtils;
import com.anshishagua.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午10:56
 */

@Service
public class IndexService {
    @Autowired
    private TableService tableService;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private IndexSQLGenerateService indexSQLGenerateService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskDependencyService taskDependencyService;

    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private IndexDimensionMapper indexDimensionMapper;
    @Autowired
    private IndexMetricMapper indexMetricMapper;

    public List<Index> getAll() {
        return indexMapper.list();
    }

    public Index getById(long id) {
        Index index = indexMapper.getById(id);

        if (index != null) {
            index.setDimensions(indexDimensionMapper.getByIndexId(index.getId()));
            index.setMetrics(indexMetricMapper.getByIndexId(index.getId()));
        }

        return index;
    }

    public Index getByName(String name) {
        Index index = indexMapper.getByName(name);

        if (index != null) {
            index.setDimensions(indexDimensionMapper.getByIndexId(index.getId()));
            index.setMetrics(indexMetricMapper.getByIndexId(index.getId()));
        }

        return index;
    }

    public ParseResult parse(ParseType parseType, String expression) {
        ExpressionParser parser = new ExpressionParser(expression);

        try {
            parser.parse();
        } catch (Exception ex) {
            return ParseResult.error(parseType, expression, ex.getMessage());
        }

        SemanticAnalyzer analyzer = new SemanticAnalyzer(parser.getAstTree());
        analyzer.setTableService(tableService);
        analyzer.setSystemParameterService(systemParameterService);

        try {
            analyzer.analyze();
        } catch (SemanticException ex) {
            return ParseResult.error(parseType, expression, ex.getMessage());
        }

        ParseResult parseResult = ParseResult.ok(parseType, expression);
        parseResult.setAstTree(parser.getAstTree());
        parseResult.setColumns(analyzer.getColumns());
        parseResult.setTables(analyzer.getTables());
        parseResult.setSystemParameters(analyzer.getSystemParameters());
        parseResult.setParseType(parseType);
        parseResult.setAggregationNodes(analyzer.getAggregationNodes());
        parseResult.setResultType(parser.getAstTree().getResultType());

        return parseResult;
    }

    public ParseResult parseDimension(String expression) {
        ParseResult parseResult = parse(ParseType.BASE_INDEX_DIMENSION, expression);

        if (!parseResult.isSuccess()) {
            return parseResult;
        }

        Node root = parseResult.getAstTree();

        if (root.getResultType().isDecimal()) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标维度类型不能为浮点型数值");
        }

        if (parseResult.getTables().isEmpty()) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标维度没有引用任何表");
        }

        if (!parseResult.getAggregationNodes().isEmpty()) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标维度不能包含聚合");
        }

        return parseResult;
    }

    public ParseResult parseMetric(String expression) {
        ParseResult parseResult = parse(ParseType.BASE_INDEX_METRIC, expression);

        if (!parseResult.isSuccess()) {
            return parseResult;
        }

        if (parseResult.getTables().isEmpty()) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标度量没有引用任何表");
        }

        Node root = parseResult.getAstTree();

        if (!(root instanceof AggregationNode)) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标度量顶层一定是聚合");
        }

        if (CollectionUtils.hasMultiplyElement(parseResult.getAggregationNodes())) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标度量不能包含多个聚合");
        }

        return parseResult;
    }

    public void addIndex(Index index) {
        Objects.requireNonNull(index);
        AssertUtils.collectionNotEmpty(index.getDimensions());
        AssertUtils.collectionNotEmpty(index.getMetrics());

        SQLGenerateResult sqlGenerateResult = indexSQLGenerateService.generate(index);
        index.setSqlGenerateResult(sqlGenerateResult);

        indexMapper.insert(index);

        for (IndexDimension dimension : index.getDimensions()) {
            indexDimensionMapper.insert(dimension);
        }

        for (IndexMetric metric : index.getMetrics()) {
            indexMetricMapper.insert(metric);
        }

        index.setSqlGenerateResult(indexSQLGenerateService.generate(index));

        indexMapper.updateSQLGenerateResult(index);

        Task task = new Task();
        task.setCreateTime(LocalDateTime.now());
        task.setLastUpdated(LocalDateTime.now());
        task.setObjectId(index.getId());
        task.setTaskType(TaskType.INDEX);
        task.setCronExpression(CronExpressionConstants.EVERY_DAY_AT_ONE_AM);
        task.setDescription("index compute");

        taskService.addNewTask(task);

        Set<String> tableNames = sqlGenerateResult.getDataSourceTables();
        List<Task> dependentTasks = new ArrayList<>();

        for (String tableName : tableNames) {
            Table table = tableService.getByName(tableName);

            Task dependentTask = taskService.getByTaskTypeAndObjectId(TaskType.DATA_LOAD, table.getId());

            dependentTasks.add(dependentTask);
        }

        taskDependencyService.add(task, dependentTasks);
    }
}