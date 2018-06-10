package com.anshishagua.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.PrimaryKey;
import com.anshishagua.object.Result;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.Tag;
import com.anshishagua.object.TagValue;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.parser.nodes.sql.JoinType;
import com.anshishagua.parser.nodes.sql.Query;
import com.anshishagua.service.BasicSQLService;
import com.anshishagua.service.HiveService;
import com.anshishagua.service.MetaDataService;
import com.anshishagua.service.NameValidateService;
import com.anshishagua.service.ObjectReferenceService;
import com.anshishagua.service.SQLExecuteService;
import com.anshishagua.service.SystemParameterService;
import com.anshishagua.service.TableService;
import com.anshishagua.service.TagSQLGenerateService;
import com.anshishagua.service.TagService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/8
 * Time: 下午4:38
 */

@Controller
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
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private ObjectReferenceService objectReferenceService;

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

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        List<Table> tables = tableService.getAllTables();

        modelAndView.addObject("systemParams", systemParameterService.getAll());
        modelAndView.addObject("bools", metaDataService.getBoolOperators());
        modelAndView.addObject("compares", metaDataService.getCompareOperators());
        modelAndView.addObject("operators", metaDataService.getOperators());
        modelAndView.addObject("functions", metaDataService.getFunctionNames());
        modelAndView.addObject("tableColumns", metaDataService.getTableColumns());
        modelAndView.addObject("tables", tables);
        modelAndView.setViewName("tag/index");

        return modelAndView;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id") long id) {
        Tag tag = tagService.getById(id);

        if (tag == null) {
            return Result.error(String.format("Tag %d not found", id));
        }

        return Result.ok();
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("tag/edit");

        Tag tag = tagService.getById(id);

        modelAndView.addObject("tag", tag);

        return modelAndView;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("id") long tagId) {
        ModelAndView modelAndView = new ModelAndView("tag/detail");

        Tag tag = tagService.getById(tagId);

        modelAndView.addObject("tag", tag);
        modelAndView.addObject("objectReferences", objectReferenceService.getByObjectId(tagId));
        modelAndView.addObject("refObjects", objectReferenceService.getByRefObjectId(tagId));

        return modelAndView;
    }

    @RequestMapping("/tagValues")
    public ModelAndView tagValues(@RequestParam("tagId") long tagId) {
        ModelAndView modelAndView = new ModelAndView("tag/tagValues");

        Tag tag = tagService.getById(tagId);

        List<TagValue> tagValues = new ArrayList<>();

        if (tag != null) {
            tagValues = tag.getTagValues();
        }

        modelAndView.addObject("tagValues", tagValues);

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
                      @RequestParam("targetTableId") long targetTableId,
                      @RequestParam(value = "description", required = false) String description,
                      @RequestParam("tagValues") String tagValuesString) {
        if (Strings.isNullOrEmpty(tagName)) {
            return Result.error("标签名为空");
        }

        if (!nameValidateService.isValidTagName(tagName)) {
            return Result.error(String.format("标签名%s不合法:", tagName));
        }

        if (tagService.getByName(tagName) != null) {
            return Result.error(String.format("标签%s已存在", tagName));
        }

        List<TagValue> tagValues = new ArrayList<>();

        JSONArray jsonArray = JSON.parseArray(tagValuesString);

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            TagValue tagValue = new TagValue();
            tagValue.setOrder(i);
            tagValue.setValue(jsonObject.getString("value"));
            tagValue.setFilterCondition(jsonObject.getString("filterCondition"));
            tagValue.setComputeCondition(jsonObject.getString("computeCondition"));
            tagValue.setDescription(jsonObject.getString("description"));

            tagValues.add(tagValue);
        }

        if (tagValues.isEmpty()) {
            return Result.error("标签值列表为空");
        }

        Set<String> set = new HashSet<>();

        for (TagValue tagValue : tagValues) {
            if (Strings.isNullOrEmpty(tagValue.getValue())) {
                return Result.error("标签值为空");
            } else if (set.contains(tagValue.getValue())) {
                return Result.error("已包含标签值" + tagValue.getValue());
            } else {
                set.add(tagValue.getValue());
            }

            ParseResult parseResult = tagService.parseFilterCondition(tagValue.getFilterCondition(), targetTableId);

            if (!parseResult.isSuccess()) {
                return Result.error(String.format("过滤条件%s错误:%s", tagValue.getFilterCondition(), parseResult.getErrorMessage()));
            }

            parseResult = tagService.parseComputeCondition(tagValue.getComputeCondition(), targetTableId);

            if (!parseResult.isSuccess()) {
                return Result.error(String.format("规则条件%s错误:%s", tagValue.getComputeCondition(), parseResult.getErrorMessage()));
            }

        }

        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setDescription(description);

        Table table = tableService.getById(targetTableId);

        if (table == null) {
            return Result.error(String.format("贴标表id:%d不存在", targetTableId));
        }

        tag.setTableId(targetTableId);
        tag.setTagValues(tagValues);
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

    private String tagQuerySQL(Tag tag) {
        Objects.requireNonNull(tag);

        Table table = tableService.getById(tag.getTableId());
        List<TableColumn> primaryKeys = table.getPrimaryKeys();
        PrimaryKey primaryKey = basicSQLService.getPrimaryKey(table);
        String tableName = table.getName();
        String tagTableName = String.format("tag_%d", tag.getId());
        List<String> columnNames = new ArrayList<>();

        List<String> tagColumns = Arrays.asList("tag_value_id", "tag_value_value");
        columnNames.addAll(table.getColumns().stream().map(it -> it.getName()).collect(Collectors.toList()));

        Query query = new Query();

        for (String tagColumn: tagColumns) {
            query.select(new Column(tagTableName, tagColumn));
        }

        for (String columnName : columnNames) {
            query.select(new Column(tableName, columnName));
        }

        com.anshishagua.parser.nodes.sql.Table left = new com.anshishagua.parser.nodes.sql.Table(tagTableName);
        com.anshishagua.parser.nodes.sql.Table right = new com.anshishagua.parser.nodes.sql.Table(tableName);

        if (primaryKey.isCombined()) {
            query.join(new Join(left, right, JoinType.INNER_JOIN, new Equal(new Column(tagTableName, "id"), primaryKey.toConcatNode())));
        } else {
            query.join(new Join(left, right, JoinType.INNER_JOIN, new Equal(new Column(tagTableName, "id"), new Column(tableName, table.getPrimaryKeys().get(0).getName()))));
        }

        String sql = query.toSQL();

        return sql;
    }

    private List<String> columnNames(ResultSet resultSet) throws SQLException {
        Objects.requireNonNull(resultSet);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        List<String> columnNames = new ArrayList<>(columnCount);

        for (int i = 1; i <= columnCount; ++i) {
            columnNames.add(metaData.getColumnName(i));
        }

        return columnNames;
    }

    @RequestMapping("/data")
    public ModelAndView data(@RequestParam("id") long tagId) {
        ModelAndView modelAndView = new ModelAndView("tag/data");

        Tag tag = tagService.getById(tagId);

        modelAndView.addObject("tagId", tagId);
        modelAndView.addObject("tagName", tag == null ? "" : tag.getName());

        ResultSet resultSet = null;

        List<List<String>> data = new ArrayList<>();

        String sql = tagQuerySQL(tag);
        List<String> columnNames = null;

        try {
            resultSet = hiveService.executeQuery(sql);

            columnNames = columnNames(resultSet);

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

    @RequestMapping("/value")
    public String tagValue() {
        return "tag/tagValue";
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response, @RequestParam("tagId") long tagId) {
        Tag tag = tagService.getById(tagId);

        String fileName = String.format("tag_%d.csv", tagId);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        Charset charset = StandardCharsets.UTF_8;

        if (tag == null) {
            try {
                response.getOutputStream().write(String.format("标签%d不存在\n", tagId).getBytes(charset));
                response.getOutputStream().close();
            } catch (IOException ex) {
                LOG.error("", ex);
            }

            return;
        }

        String sql = tagQuerySQL(tag);
        List<String> columnNames = null;

        ResultSet resultSet = null;

        try {
            resultSet = hiveService.executeQuery(sql);

            columnNames = columnNames(resultSet);

            String header = columnNames.stream().collect(Collectors.joining(",")) + "\n";

            response.getOutputStream().write(header.getBytes(charset));

            while (resultSet.next()) {
                List<String> list = new ArrayList<>();

                for (String columnName : columnNames) {
                    list.add(resultSet.getString(columnName));
                }

                String line = list.stream().collect(Collectors.joining(",")) + "\n";

                response.getOutputStream().write(line.getBytes(charset));
            }

            response.getOutputStream().close();
        } catch (SQLException ex) {
            LOG.error("Failed to execute sql {}", sql, ex);
        } catch (IOException ex) {
            LOG.error("Failed to write data", ex);
        }
    }
}