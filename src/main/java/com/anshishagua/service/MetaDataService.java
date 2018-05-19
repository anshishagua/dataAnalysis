package com.anshishagua.service;

import com.anshishagua.object.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/19
 * Time: 上午11:05
 */

@Service
public class MetaDataService {
    @Autowired
    private TableService tableService;

    public List<String> getCompareOperators() {
        return Arrays.asList(">", ">=", "<", "<=", "=", "!=", "LIKE", "NOT LIKE");
    }

    public List<String> getBoolOperators() {
        return Arrays.asList("AND", "OR", "NOT");
    }

    public List<String> getOperators() {
        return Arrays.asList("+", "-", "*", "/");
    }

    public List<String> getFunctionNames() {
        return Arrays.asList("sum", "count", "avg", "max", "min");
    }

    public Map<String, List<String>> getTableColumns() {
        Map<String, List<String>> result = new LinkedHashMap<>();

        List<Table> tables = tableService.getAllTables();

        for (Table table : tables) {
            String tableName = table.getName();
            List<String> columns = table.getColumns().stream().map(column -> column.getName()).collect(Collectors.toList());

            result.put(tableName, columns);
        }

        return result;
    }
}
