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
import com.anshishagua.service.TableService;
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
    public ModelAndView add(@RequestParam("name") String name,
                            @RequestParam("alias") String alias,
                            @RequestParam("description") String description,
                            @RequestParam("columns") String columnString) {
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
            column.setPrimaryKey(jsonObject.getBoolean("isPrimaryKey"));
            column.setNullable(jsonObject.getBoolean("nullable"));
            column.setCreateTime(LocalDateTime.now());
            column.setLastUpdated(LocalDateTime.now());

            columns.add(column);
        }

        table.setColumns(columns);

        tableService.addNewTable(table);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("tableName", name);
        modelAndView.setViewName("table/result");

        return modelAndView;
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