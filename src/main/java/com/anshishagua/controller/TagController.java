package com.anshishagua.controller;

import com.anshishagua.object.ParseResult;
import com.anshishagua.object.Result;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.Tag;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.parser.nodes.sql.JoinType;
import com.anshishagua.parser.nodes.sql.Query;
import com.anshishagua.service.BasicSQLService;
import com.anshishagua.service.HiveService;
import com.anshishagua.service.MetaDataService;
import com.anshishagua.service.NameValidateService;
import com.anshishagua.service.SQLExecuteService;
import com.anshishagua.service.TableService;
import com.anshishagua.service.TagSQLGenerateService;
import com.anshishagua.service.TagService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/8
 * Time: 下午4:38
 */

@RestController
@RequestMapping("/tag")
public class TagController {
    private static final Logger LOG = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagSQLGenerateService sqlGenerateService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SQLExecuteService sqlExecuteService;
    @Autowired
    private TableService tableService;
    @Autowired
    private MetaDataService metaDataService;
    @Autowired
    private NameValidateService nameValidateService;
    @Autowired
    private BasicSQLService basicSQLService;
    @Autowired
    private HiveService hiveService;

    @RequestMapping("/generate")
    @ResponseBody
    public SQLGenerateResult generate(@RequestParam("id") long id) {
        Tag tag = tagService.getById(id);

        if (tag == null) {
            return SQLGenerateResult.error("Tag " + id + " not found");
        }

        SQLGenerateResult result = sqlGenerateService.generate(tag);

        if (result.isSuccess()) {
            try {
                sqlExecuteService.execute(result.getExecuteSQLs());
            } catch (SQLException ex) {
                LOG.error("Failed to execute", ex);
            }
        }

        return result;
    }

    @RequestMapping("/check")
    @ResponseBody
    public Result check(@RequestParam("tagName") String tagName) {
        Tag tag = tagService.getByName(tagName);

        if (tag != null) {
            return new Result(false, String.format("标签%s已存在", tagName));
        }

        return Result.ok();
    }

    @RequestMapping("/insert")
    public void insert() {
        Tag tag = new Tag();

        tag.setCreateTime(LocalDateTime.now());
        tag.setLastUpdated(LocalDateTime.now());
        tag.setDescription("sssss");
        tag.setTableId(2);
        tag.setName("测试标签");
        tag.setFilterCondition("student.name > 'A' AND student.name < 'B'");
        tag.setComputeCondition("student.age > 10 AND student.age < 30 AND count(course.name) > 30");

        SQLGenerateResult result = sqlGenerateService.generate(tag);

        tag.setSqlGenerateResult(result);

        tagService.addTag(tag);
    }

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        List<Table> tables = tableService.getAllTables();

        modelAndView.addObject("bools", metaDataService.getBoolOperators());
        modelAndView.addObject("compares", metaDataService.getCompareOperators());
        modelAndView.addObject("operators", metaDataService.getOperators());
        modelAndView.addObject("functions", metaDataService.getFunctionNames());
        modelAndView.addObject("tableColumns", metaDataService.getTableColumns());
        modelAndView.addObject("tables", tables);
        modelAndView.setViewName("tag/index");

        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("tag/list");

        List<Tag> tags = tagService.getAll();

        modelAndView.addObject("tags", tags);

        return modelAndView;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@RequestParam("tagName") String tagName,
               @RequestParam("description") String description,
               @RequestParam("targetTableId") long targetTableId,
               @RequestParam("filterCondition") String filterCondition,
               @RequestParam("computeCondition") String computeCondition) {
        if (Strings.isNullOrEmpty(tagName)) {
            return Result.error("标签名为空");
        }

        if (!nameValidateService.isValidTagName(tagName)) {
            return Result.error(String.format("标签名%s不合法:", tagName));
        }

        if (tagService.getByName(tagName) != null) {
            return Result.error(String.format("标签%s已存在", tagName));
        }

        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setDescription(description);

        Table table = tableService.getById(targetTableId);

        if (table == null) {
            return Result.error(String.format("贴标表id:%d不存在", targetTableId));
        }

        tag.setTableId(targetTableId);

        ParseResult parseResult = tagService.parseFilterCondition(filterCondition, targetTableId);

        if (!parseResult.isSuccess()) {
            return Result.error(String.format("过滤条件%s错误:%s", filterCondition, parseResult.getErrorMessage()));
        }

        parseResult = tagService.parseComputeCondition(computeCondition, targetTableId);

        if (!parseResult.isSuccess()) {
            return Result.error(String.format("规则条件%s错误:%s", computeCondition, parseResult.getErrorMessage()));
        }

        tag.setFilterCondition(filterCondition);
        tag.setComputeCondition(computeCondition);
        tag.setCreateTime(LocalDateTime.now());
        tag.setLastUpdated(LocalDateTime.now());

        SQLGenerateResult result = sqlGenerateService.generate(tag);

        if (!result.isSuccess()) {
            return Result.error(String.format("标签生成SQL失败:%s", result.getErrorMessage()));
        }

        tag.setSqlGenerateResult(result);

        tagService.addTag(tag);

        String sql = basicSQLService.createTagSQL(tag);

        try {
            hiveService.execute(sql);
        } catch (SQLException ex) {
            LOG.error("Failed to create tag table {}", tag.getId(), ex);

            return Result.error("创建标签hive表失败:" + ex.toString());
        }

        return Result.ok();
    }

    @RequestMapping("/data")
    public ModelAndView data(@RequestParam("id") long tagId) {
        ModelAndView modelAndView = new ModelAndView("tag/data");

        Tag tag = tagService.getById(tagId);

        modelAndView.addObject("tagName", tag == null ? "" : tag.getName());

        ResultSet resultSet = null;
        Table table = tableService.getById(tag.getTableId());
        String tableName = table.getName();
        List<String> columnNames = table.getColumns().stream().map(it -> it.getName()).collect(Collectors.toList());

        Query query = new Query();

        for (String columnName : columnNames) {
            query.select(new Column(tableName, columnName));
        }

        com.anshishagua.parser.nodes.sql.Table left = new com.anshishagua.parser.nodes.sql.Table("tag_" + tagId);
        com.anshishagua.parser.nodes.sql.Table right = new com.anshishagua.parser.nodes.sql.Table(tableName);

        query.join(new Join(left, right, JoinType.INNER_JOIN, new Equal(new Column("tag_" + tagId, "id"), new Column(tableName, table.getPrimaryKeys().get(0).getName()))));

        String sql = query.toSQL();
        List<List<String>> data = new ArrayList<>();

        try {
            resultSet = hiveService.executeQuery(sql);

            while (resultSet.next()) {
                List<String> list = new ArrayList<>();

                for (String columnName : columnNames) {
                    list.add(resultSet.getString(columnName));
                }

                data.add(list);
            }
        } catch (SQLException ex) {
            LOG.error("Failed to execute sql {}", sql, ex);
        }

        modelAndView.addObject("columns", columnNames);
        modelAndView.addObject("data", data);

        return modelAndView;
    }
}