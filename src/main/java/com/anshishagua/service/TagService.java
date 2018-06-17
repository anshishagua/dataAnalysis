package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.constants.IndexType;
import com.anshishagua.constants.ObjectType;
import com.anshishagua.constants.TagType;
import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.mybatis.mapper.TagMapper;
import com.anshishagua.mybatis.mapper.TagValueMapper;
import com.anshishagua.object.CronExpressionConstants;
import com.anshishagua.object.ObjectReference;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.SystemParameter;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableRelation;
import com.anshishagua.object.Tag;
import com.anshishagua.constants.TaskType;
import com.anshishagua.object.TagValue;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private TagSQLGenerateService sqlGenerateService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private TableRelationService tableRelationService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskDependencyService taskDependencyService;
    @Autowired
    private ObjectReferenceService objectReferenceService;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagValueMapper tagValueMapper;

    public Tag getById(long tagId) {
        Tag tag = tagMapper.getById(tagId);

        if (tag != null) {
            tag.setTable(tableService.getById(tag.getTableId()));
            tag.setTagValues(tagValueMapper.getByTagId(tag.getId()));
        }

        return tag;
    }

    public Tag getByName(String name) {
        Tag tag = tagMapper.getByName(name);

        if (tag != null) {
            tag.setTable(tableService.getById(tag.getTableId()));
            tag.setTagValues(tagValueMapper.getByTagId(tag.getId()));
        }

        return tag;
    }

    public List<Tag> getByNameLike(String name) {
        Objects.requireNonNull(name);

        List<Tag> tags = tagMapper.getByNameLike(name);

        for (Tag tag : tags) {
            tag.setTable(tableService.getById(tag.getTableId()));
            tag.setTagValues(tagValueMapper.getByTagId(tag.getId()));
        }

        return tags;
    }

    private ParseResult parse(String expression, ParseResult.ParseType parseType, long targetTableId) {
        ExpressionParser parser = new ExpressionParser(expression);

        try {
            parser.parse();
        } catch (Exception ex) {
            return ParseResult.error(parseType, expression, ex.getMessage());
        }

        SemanticAnalyzer analyzer = new SemanticAnalyzer(parser.getAstTree(), parseType);
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

        Table mainTable = tableService.getById(targetTableId);

        tables.remove(mainTable);

        try {
            sqlGenerateService.buildJoinTree(mainTable.getName(), tables.stream().map(it -> it.getName()).collect(Collectors.toSet()));
        } catch (Exception ex) {
            return ParseResult.error(parseType, expression, ex.getMessage());
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
        parseResult.setAstTree(analyzer.getAstTree());
        parseResult.setColumns(analyzer.getColumns());
        parseResult.setTables(analyzer.getTables());
        parseResult.setSystemParameters(analyzer.getSystemParameters());
        parseResult.setParseType(parseType);
        parseResult.setAggregationNodes(analyzer.getAggregationNodes());

        return parseResult;
    }

    public ParseResult parseFilterCondition(String expression, long targetTableId) {
        ParseResult.ParseType parseType = ParseResult.ParseType.TAG_FILTER_CONDITION;

        if (Strings.isNullOrEmpty(expression)) {
            return ParseResult.ok(parseType, expression == null ? "" : expression);
        }

        return parse(expression, parseType, targetTableId);
    }

    public ParseResult parseComputeCondition(String expression, long targetTableId) {
        ParseResult.ParseType parseType = ParseResult.ParseType.TAG_COMPUTE_CONDITION;

        if (Strings.isNullOrEmpty(expression)) {
            return ParseResult.error(parseType, expression, "Tag compute condition could not be empty");
        }

        return parse(expression, parseType, targetTableId);
    }

    public void updateTag(Tag tag) {

    }

    public void addTag(Tag tag) {
        tagMapper.insert(tag);

        for (TagValue tagValue : tag.getTagValues()) {
            tagValue.setTagId(tag.getId());
            tagValueMapper.insert(tagValue);
        }

        SQLGenerateResult sqlGenerateResult = tag.getTagType() == TagType.USER_DEFINED ? SQLGenerateResult.ok() : sqlGenerateService.generate(tag);
        tag.setSqlGenerateResult(sqlGenerateResult);

        tagService.updateSQLGenerateResult(tag);

        if (tag.getTagType() == TagType.USER_DEFINED) {
            return;
        }

        Task task = new Task();
        task.setCreateTime(LocalDateTime.now());
        task.setLastUpdated(LocalDateTime.now());
        task.setObjectId(tag.getId());
        task.setTaskType(TaskType.TAG);
        task.setCronExpression(CronExpressionConstants.EVERY_DAY_AT_ONE_AM);
        task.setDescription(String.format("Tag[%d:%s] task", tag.getId(), tag.getName()));
        task.setResources(sqlGenerateResult.getDataSourceTables().size());

        taskService.addNewTask(task);

        Set<String> tableNames = sqlGenerateResult.getDataSourceTables();

        List<ObjectReference> objectReferences = new ArrayList<>();

        for (String systemParam : sqlGenerateResult.getSystemParameters()) {
            SystemParameter systemParameter = systemParameterService.getByName(systemParam);

            ObjectReference reference = new ObjectReference();
            reference.setObjectId(tag.getId());
            reference.setObjectName(tag.getName());
            reference.setObjectType(ObjectType.TAG);
            reference.setRefObjectId(systemParameter.getId());
            reference.setRefObjectType(ObjectType.SYSTEM_PARAM);
            reference.setRefObjectName(systemParam);

            objectReferences.add(reference);
        }

        for (long relationId : sqlGenerateResult.getTableRelationIds()) {
            TableRelation relation = tableRelationService.getById(relationId);

            ObjectReference reference = new ObjectReference();
            reference.setObjectId(tag.getId());
            reference.setObjectName(tag.getName());
            reference.setObjectType(ObjectType.TAG);
            reference.setRefObjectId(relation.getId());
            reference.setRefObjectType(ObjectType.TABLE_RELATION);
            reference.setRefObjectName(relation.getLeftTable().getName() + "-" + relation.getRightTable().getName());

            objectReferences.add(reference);
        }

        List<Task> dependentTasks = new ArrayList<>();

        for (String tableName : tableNames) {
            Table table = tableService.getByName(tableName);

            ObjectReference reference = new ObjectReference();
            reference.setObjectId(tag.getId());
            reference.setObjectName(tag.getName());
            reference.setObjectType(ObjectType.TAG);
            reference.setRefObjectId(table.getId());
            reference.setRefObjectType(ObjectType.TABLE);
            reference.setRefObjectName(table.getName());

            objectReferences.add(reference);

            Task dependentTask = taskService.getByTaskTypeAndObjectId(TaskType.DATA_LOAD, table.getId());

            dependentTasks.add(dependentTask);
        }

        taskDependencyService.add(task, dependentTasks);
        objectReferenceService.addObjectReferences(objectReferences);
    }

    public void updateSQLGenerateResult(Tag tag) {
        tagMapper.updateSQLGenerateResult(tag);
    }

    public List<Tag> getAll() {
        List<Tag> tags = tagMapper.list();

        for (Tag tag : tags) {
            tag.setTable(tableService.getById(tag.getTableId()));
            tag.setTagValues(tagValueMapper.getByTagId(tag.getId()));
        }

        return tags;
    }
}