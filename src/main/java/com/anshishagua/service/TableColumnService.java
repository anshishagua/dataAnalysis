package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.TableColumnMapper;
import com.anshishagua.object.TableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 上午10:32
 */

@Service
public class TableColumnService {
    @Autowired
    private TableColumnMapper tableColumnMapper;
    @Autowired
    private DataTypeService dataTypeService;

    public List<TableColumn> getTableColumns(long tableId) {
        List<TableColumn> columns = tableColumnMapper.getByTableId(tableId);

        for (TableColumn column : columns) {
            column.setDataType(dataTypeService.getTypeById(column.getTypeId()));
        }

        return columns;
    }

    public void addColumn(TableColumn column) {
        tableColumnMapper.addColumn(column);
    }
}