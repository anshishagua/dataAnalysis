package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.mybatis.mapper.TagMapper;
import com.anshishagua.object.CronExpressionConstants;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableRelation;
import com.anshishagua.object.Tag;
import com.anshishagua.object.TaskType;
import com.anshishagua.parser.grammar.ExpressionParser;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.aggregation.AggregationNode;
import com.anshishagua.parser.semantic.SemanticAnalyzer;
import com.anshishagua.utils.NodeUtils;
import com.anshishagua.utils.TreeNodeWalker;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午4:56
 */

@Service
public class TagService {
    private static final Logger LOG = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private TableService tableService;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private TableRelationService tableRelationService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TagMapper tagMapper;

    public Tag getById(long tagId) {
        Tag tag = tagMapper.getById(tagId);

        if (tag != null) {
            tag.setTable(tableService.getById(tag.getTableId()));
        }

        return tag;
    }

    public Tag getByName(String name) {
        Tag tag = tagMapper.getByName(name);

        if (tag != null) {
            tag.setTable(tableService.getById(tag.getTableId()));
        }

        return tag;
    }

    private ParseResult parse(String expression, ParseResult.ParseType parseType) {
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

        Node root = parser.getAstTree();

        //返回结果应该是Boolean类型
        if (!(root.getResultType().isBoolean())) {
            return ParseResult.error(parseType, expression, "Result type must be Boolean, actual:" + root.getResultType());
        }

        //需要引用列,不然表达式没意义
        if (analyzer.getColumns().isEmpty()) {
            return ParseResult.error(parseType, expression, "Expression does not reference table columns");
        }

        List<Table> tables = new ArrayList<>(analyzer.getTables());

        Table mainTable = tableService.getByName("student");

        tables.remove(mainTable);

        //检查是否可以关联到标签对象
        for (Table table : tables) {
            List<TableRelation> relations = tableRelationService.getByTable(mainTable.getName(), table.getName());

            if (relations.isEmpty()) {
                return ParseResult.error(parseType, expression, String.format("Table %s has no relation to table %s", table.getName(), mainTable.getName()));
            }
        }

        //检查聚合条件是否涉及到列字段
        List<ParseResult> parseResults = new ArrayList<>();
        TreeNodeWalker.preOrder(root, node -> {
            if (node instanceof AggregationNode) {
                Set<String> columnNames = NodeUtils.getColumnNames(node);

                if (columnNames.isEmpty()) {
                    parseResults.add(ParseResult.error(parseType, expression, String.format("Aggregation %s has no column reference", node)));
                }
            }
        });

        if (!parseResults.isEmpty()) {
            return parseResults.get(0);
        }

        ParseResult parseResult = ParseResult.ok(parseType, expression);
        parseResult.setAstTree(parser.getAstTree());
        parseResult.setColumns(analyzer.getColumns());
        parseResult.setTables(analyzer.getTables());
        parseResult.setSystemParameters(analyzer.getSystemParameters());
        parseResult.setParseType(parseType);
        parseResult.setAggregationNodes(analyzer.getAggregationNodes());

        return parseResult;
    }

    public ParseResult parseFilterCondition(String expression) {
        ParseResult.ParseType parseType = ParseResult.ParseType.TAG_FILTER_CONDITION;

        if (Strings.isNullOrEmpty(expression)) {
            return ParseResult.ok(parseType, expression == null ? "" : expression);
        }

        return parse(expression, parseType);
    }

    public ParseResult parseComputeCondition(String expression) {
        ParseResult.ParseType parseType = ParseResult.ParseType.TAG_COMPUTE_CONDITION;

        if (Strings.isNullOrEmpty(expression)) {
            return ParseResult.error(parseType, expression, "Tag compute condition could not be empty");
        }

        return parse(expression, parseType);
    }

    public void addTag(Tag tag) {
        tagMapper.insert(tag);

        Task task = new Task();
        task.setCreateTime(LocalDateTime.now());
        task.setLastUpdated(LocalDateTime.now());
        task.setObjectId(tag.getId());
        task.setTaskType(TaskType.TAG);
        task.setCronExpression(CronExpressionConstants.EVERY_DAY_AT_ONE_AM);
        task.setDescription("tag compute");

        taskService.addNewTask(task);
    }

    public void updateSQLGenerateResult(Tag tag) {
        tagMapper.updateSQLGenerateResult(tag);
    }
}