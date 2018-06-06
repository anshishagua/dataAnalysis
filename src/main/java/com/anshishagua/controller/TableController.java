package com.anshishagua.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anshishagua.object.Result;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.service.BasicSQLService;
import com.anshishagua.service.DataTypeService;
import com.anshishagua.service.HiveService;
import com.anshishagua.service.NameValidateService;
import com.anshishagua.service.TableService;
import com.google.common.base.Strings;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 下午2:50
 */

@Controller
@RequestMapping("/table")
public class TableController {
    private static final Logger LOG = LoggerFactory.getLogger(TableController.class);

    @Autowired
    private TableService tableService;
    @Autowired
    private DataTypeService dataTypeService;
    @Autowired
    private BasicSQLService basicSQLService;
    @Autowired
    private HiveService hiveService;
    @Autowired
    private NameValidateService nameValidateService;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("dataTypes", dataTypeService.getAll());

        modelAndView.setViewName("table/index");

        return modelAndView;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Table get(@Param("id") long id) {
        return tableService.getById(id);
    }

    @RequestMapping("/generate")
    @ResponseBody
    public SQLGenerateResult genCreateTable(@Param("id") long id) {
        Table table = tableService.getById(id);

        if (table == null) {
            return SQLGenerateResult.error("Table " + id + " not found");
        }

        return basicSQLService.generateTableCreateSQL(table);
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();

        List<Table> tables = tableService.getAllTables();

        modelAndView.addObject("tables", tables);
        modelAndView.setViewName("table/list");

        return modelAndView;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView();

        Table table = tableService.getById(id);

        List<TableColumn> columns = table.getColumns();
        modelAndView.addObject("table", table);
        modelAndView.addObject("columns", columns);

        modelAndView.setViewName("table/detail");

        return modelAndView;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id") long id) {
        Table table = tableService.getById(id);

        if (table == null) {
            return Result.error(String.format("Table %d not found", id));
        }

        return Result.ok();
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("index/edit");

        Table table = tableService.getById(id);

        modelAndView.addObject("table", table);

        return modelAndView;
    }

    @RequestMapping("/check")
    @ResponseBody
    public Result check(@RequestParam("tableName") String tableName) {
        Table table = tableService.getByName(tableName);

        if (table != null) {
            return new Result(false, String.format("表%s已存在", tableName));
        }

        return new Result(true, "");
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@RequestParam("name") String name,
                      @RequestParam("alias") String alias,
                      @RequestParam("description") String description,
                      @RequestParam("columns") String columnString) {
        if (Strings.isNullOrEmpty(name)) {
            return Result.error("表名为空");
        }

        if (!nameValidateService.isValidTableName(name)) {
            return Result.error("表名不合法");
        }

        Table table = new Table();
        table.setName(name);
        table.setAlias(alias);
        table.setDescription(description);
        table.setCreateTime(LocalDateTime.now());
        table.setLastUpdated(LocalDateTime.now());

        JSONArray jsonArray = JSON.parseArray(columnString);

        List<TableColumn> columns = new ArrayList<>(jsonArray.size());

        for (int i = 0; i < jsonArray.size(); ++i) {
            TableColumn column = new TableColumn();

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            column.setName(jsonObject.getString("name"));
            column.setAlias(jsonObject.getString("alias"));
            column.setTypeId(jsonObject.getLong("dataType"));
            column.setDataType(dataTypeService.getTypeById(column.getTypeId()));
            column.setPrimaryKey(jsonObject.getBoolean("isPrimaryKey"));
            column.setNullable(jsonObject.getBoolean("nullable"));
            column.setCreateTime(LocalDateTime.now());
            column.setLastUpdated(LocalDateTime.now());

            columns.add(column);
        }

        if (columns.isEmpty()) {
            return Result.error("表" + name + "列为空");
        }

        long count = columns.stream().filter(column -> column.isPrimaryKey()).count();

        if (count == 0) {
            return Result.error("没有选择主键");
        }

        if (count > 1) {
            return Result.error("只能有唯一主键");
        }

        count = columns.stream().filter(column -> column.isPrimaryKey() && column.isNullable()).count();

        if (count > 0) {
            return Result.error("主键不能为空");
        }

        table.setColumns(columns);

        tableService.addNewTable(table);

        String sql = basicSQLService.createTableSQL(table);

        LOG.info("Start to create hive table:{}", sql);

        try {
            hiveService.execute(sql);
        } catch (SQLException ex) {
            LOG.error("Failed to create table", ex);

            return Result.error(String.format("创建hive表%s失败:%s", table.getName(), ex.toString()));
        }

        return Result.ok();
    }

    @RequestMapping("/column")
    public ModelAndView column() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("dataTypes", dataTypeService.getAll());
        modelAndView.setViewName("table/column");

        return modelAndView;
    }

    @RequestMapping("/insert")
    public void insert() {
        Table table = new Table();
        table.setName("student");
        table.setAlias("学生");
        table.setCreateTime(LocalDateTime.now());
        table.setLastUpdated(LocalDateTime.now());
        table.setDescription("学生表");
        table.setDeleted(false);

        TableColumn id = new TableColumn();
        id.setName("id");
        id.setAlias("主键");
        id.setPrimaryKey(true);
        id.setCreateTime(LocalDateTime.now());
        id.setLastUpdated(LocalDateTime.now());
        id.setDescription("xxxx");
        id.setNullable(false);
        id.setTypeId(2);

        List<TableColumn> columns = new ArrayList<>();
        columns.add(id);

        TableColumn name = new TableColumn();
        name.setName("name");
        name.setAlias("姓名");
        name.setPrimaryKey(false);
        name.setCreateTime(LocalDateTime.now());
        name.setLastUpdated(LocalDateTime.now());
        name.setDescription("xxxxxxx");
        name.setNullable(false);
        name.setTypeId(3);
        columns.add(name);

        TableColumn age = new TableColumn();
        age.setName("age");
        age.setAlias("年龄");
        age.setPrimaryKey(false);
        age.setCreateTime(LocalDateTime.now());
        age.setLastUpdated(LocalDateTime.now());
        age.setDescription("xxxxxxxfffff");
        age.setNullable(false);
        age.setTypeId(1);
        columns.add(age);

        table.setColumns(columns);

        tableService.addNewTable(table);
    }

    @RequestMapping("/columns")
    ModelAndView columns(@RequestParam("tableId") long tableId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("table/columns");

        List<TableColumn> columns = new ArrayList<>();

        Table table = tableService.getById(tableId);

        if (table != null) {
            columns = table.getColumns();
        }

        modelAndView.addObject("columns", columns);

        return modelAndView;
    }

    @RequestMapping("/search")
    @ResponseBody
    public List<String> search(@RequestParam("term") String keyword) {
        return tableService.tableNameLike(keyword);
    }
}