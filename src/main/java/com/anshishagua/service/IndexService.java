package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.mybatis.mapper.IndexDimensionMapper;
import com.anshishagua.mybatis.mapper.IndexMapper;
import com.anshishagua.mybatis.mapper.IndexMetricMapper;
import com.anshishagua.object.CronExpressionConstants;
import com.anshishagua.object.DataType;
import com.anshishagua.object.Index;
import com.anshishagua.object.IndexDimension;
import com.anshishagua.object.IndexMetric;
import com.anshishagua.object.IndexType;
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
    private DataTypeService dataTypeService;

    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private IndexDimensionMapper indexDimensionMapper;
    @Autowired
    private IndexMetricMapper indexMetricMapper;

    public List<Index> getAll() {
        List<Index> indices = indexMapper.list();

        for (Index index : indices) {
            index.setDimensions(indexDimensionMapper.getByIndexId(index.getId()));
            index.setMetrics(indexMetricMapper.getByIndexId(index.getId()));
        }

        return indices;
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
        Index index = null;

        if (name.startsWith("index_")) {
            index = indexMapper.getById(Long.parseLong(name.substring(name.indexOf("_") + 1)));
        } else {
            index = indexMapper.getByName(name);
        }

        if (index != null) {
            List<IndexDimension> dimensions = indexDimensionMapper.getByIndexId(index.getId());

            for (IndexDimension dimension : dimensions) {
                DataType dataType = dataTypeService.getTypeById(dimension.getTypeId());

                if (dataType != null) {
                    dimension.setDataType(dataType.toBasicType());
                }
            }

            index.setDimensions(dimensions);

            List<IndexMetric> metrics = indexMetricMapper.getByIndexId(index.getId());

            for (IndexMetric metric : metrics) {
                DataType dataType = dataTypeService.getTypeById(metric.getTypeId());

                if (dataType != null) {
                    metric.setDataType(dataType.toBasicType());
                }
            }

            index.setMetrics(metrics);
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

        SemanticAnalyzer analyzer = new SemanticAnalyzer(parser.getAstTree(), parseType);
        analyzer.setTableService(tableService);
        analyzer.setSystemParameterService(systemParameterService);
        analyzer.setIndexService(this);

        try {
            analyzer.analyze();
        } catch (SemanticException ex) {
            return ParseResult.error(parseType, expression, ex.getMessage());
        }

        ParseResult parseResult = ParseResult.ok(parseType, expression);
        parseResult.setAstTree(analyzer.getAstTree());
        parseResult.setColumns(analyzer.getColumns());
        parseResult.setTables(analyzer.getTables());
        parseResult.setIndices(analyzer.getIndices());
        parseResult.setIndexDimensions(analyzer.getIndexDimensions());
        parseResult.setIndexMetrics(analyzer.getIndexMetrics());
        parseResult.setSystemParameters(analyzer.getSystemParameters());
        parseResult.setParseType(parseType);
        parseResult.setAggregationNodes(analyzer.getAggregationNodes());
        parseResult.setResultType(analyzer.getAstTree().getResultType());

        return parseResult;
    }

    public ParseResult parseDimension(String expression, IndexType indexType) {
        ParseResult parseResult = indexType == IndexType.BASIC ?
                parse(ParseType.BASE_INDEX_DIMENSION, expression) : parse(ParseType.DERIVED_INDEX_DIMENSION, expression);

        if (!parseResult.isSuccess()) {
            return parseResult;
        }

        Node root = parseResult.getAstTree();

        if (root.getResultType().isDecimal()) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标维度类型不能为浮点型数值");
        }

        if (indexType == IndexType.BASIC && parseResult.getTables().isEmpty()) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标维度没有引用任何表");
        }

        if (!parseResult.getAggregationNodes().isEmpty()) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标维度不能包含聚合");
        }

        if (indexType == IndexType.DERIVED) {
            if (parseResult.getIndices().isEmpty()) {
                return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "派生指标没有引用基础指标");
            } else if (parseResult.getIndices().size() > 1) {
                return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "派生指标引用了多个基础指标");
            }

            if (!parseResult.getTables().isEmpty()) {
                return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "派生指标引用了基础表");
            }
        }

        return parseResult;
    }

    public ParseResult parseMetric(String expression, IndexType indexType) {
        ParseResult parseResult = indexType == IndexType.BASIC ?
                parse(ParseType.BASE_INDEX_METRIC, expression) : parse(ParseType.DERIVED_INDEX_METRIC, expression);

        if (!parseResult.isSuccess()) {
            return parseResult;
        }

        if (indexType == IndexType.BASIC && parseResult.getTables().isEmpty()) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标度量没有引用任何表");
        }

        Node root = parseResult.getAstTree();

        if (!(root instanceof AggregationNode)) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标度量顶层一定是聚合");
        }

        if (CollectionUtils.hasMultiplyElement(parseResult.getAggregationNodes())) {
            return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "指标度量不能包含多个聚合");
        }

        if (indexType == IndexType.DERIVED) {
            if (parseResult.getIndices().isEmpty()) {
                return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "派生指标没有引用基础指标");
            } else if (parseResult.getIndices().size() > 1) {
                return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "派生指标引用了多个基础指标");
            }

            if (!parseResult.getTables().isEmpty()) {
                return ParseResult.error(ParseType.BASE_INDEX_DIMENSION, expression, "派生指标引用了基础表");
            }
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
            dimension.setIndexId(index.getId());
            indexDimensionMapper.insert(dimension);
        }

        for (IndexMetric metric : index.getMetrics()) {
            metric.setIndexId(index.getId());
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
        task.setDescription(String.format("Index[%d:%s] task", index.getId(), index.getName()));
        task.setResources(sqlGenerateResult.getDataSourceTables().size());

        taskService.addNewTask(task);

        Set<String> tableNames = sqlGenerateResult.getDataSourceTables();
        List<Task> dependentTasks = new ArrayList<>();
        TaskType taskType = index.getIndexType() == IndexType.BASIC ? TaskType.DATA_LOAD : TaskType.INDEX;

        for (String tableName : tableNames) {
            long objectId = -1;

            if (index.getIndexType() == IndexType.BASIC) {
                Table table = tableService.getByName(tableName);

                objectId = table.getId();
            } else {
                Index basicIndex = getByName(tableName);

                objectId = basicIndex.getId();
            }

            Task dependentTask = taskService.getByTaskTypeAndObjectId(taskType, objectId);

            dependentTasks.add(dependentTask);
        }

        taskDependencyService.add(task, dependentTasks);
    }
}