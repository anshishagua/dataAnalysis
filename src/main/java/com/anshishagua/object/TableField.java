package com.anshishagua.object;

/**
 * User: lixiao
 * Date: 2018/7/8
 * Time: 下午3:00
 */

public class TableField {
    private String name;
    private String type;

    public TableField() {

    }

    public TableField(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}