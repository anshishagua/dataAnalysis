package com.anshishagua.object;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/7/8
 * Time: 下午2:59
 */

public class TableInfo {
    private String name;
    private List<TableField> fields;

    public TableInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableField> getFields() {
        return fields;
    }

    public void setFields(List<TableField> fields) {
        this.fields = fields;
    }
}