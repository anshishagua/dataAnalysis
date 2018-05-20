package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.mybatis.mapper.TableMapper;
import com.anshishagua.object.CronExpressionConstants;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.TaskType;
import com.anshishagua.utils.AssertUtils;
import org.apache.logging.log4j.core.util.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午3:45
 */

@Service
public class TableService {
    private static final Logger LOG = LoggerFactory.getLogger(TableService.class);

    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TableColumnService tableColumnService;
    @Autowired
    private BasicSQLService basicSQLService;
    @Autowired
    private HiveService hiveService;

    public List<String> tableNameLike(String tableName) {
        return tableMapper.tableNameLike(tableName);
    }

    public Table getById(long id) {
        Table table = tableMapper.getById(id);

        if (table != null) {
            List<TableColumn> columns = tableColumnService.getTableColumns(id);
            table.setColumns(columns);
            table.setPrimaryKeys(columns.stream().filter(it -> it.isPrimaryKey()).collect(Collectors.toList()));
        }

        return table;
    }

    public List<Table> getAllTables() {
        List<Table> tables = tableMapper.list();

        for (Table table : tables) {
            List<TableColumn> columns = tableColumnService.getTableColumns(table.getId());
            table.setColumns(columns);
            table.setPrimaryKeys(columns.stream().filter(it -> it.isPrimaryKey()).collect(Collectors.toList()));
        }

        return tables;
    }

    public Table getByName(String name) {
        Table table = tableMapper.getByName(name);

        if (table != null) {
            List<TableColumn> columns = tableColumnService.getTableColumns(table.getId());
            table.setColumns(columns);
            table.setPrimaryKeys(columns.stream().filter(it -> it.isPrimaryKey()).collect(Collectors.toList()));
        }

        return table;
    }

    public void addNewTable(Table table) {
        Objects.requireNonNull(table);
        AssertUtils.collectionNotEmpty(table.getColumns());
        tableMapper.insert(table);

        for (TableColumn tableColumn : table.getColumns()) {
            tableColumn.setTableId(table.getId());

            tableColumnService.addColumn(tableColumn);
        }

        Task task = new Task();
        task.setCreateTime(LocalDateTime.now());
        task.setLastUpdated(LocalDateTime.now());
        task.setObjectId(table.getId());
        task.setTaskType(TaskType.DATA_LOAD);
        task.setCronExpression(CronExpressionConstants.EVERY_DAY_AT_ONE_AM);
        task.setDescription("aaaa");

        taskService.addNewTask(task);
    }
}