package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.TableMapper;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午3:45
 */

@Service
public class TableService {
    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private TableColumnService tableColumnService;

    public Table getById(long id) {
        Table table = tableMapper.getById(id);

        if (table != null) {
            List<TableColumn> columns = tableColumnService.getTableColumns(id);
            table.setColumns(columns);
            table.setPrimaryKeys(columns.stream().filter(it -> it.isPrimaryKey()).collect(Collectors.toList()));
        }

        return table;
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
}