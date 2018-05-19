package com.anshishagua.object;

import com.anshishagua.parser.BasicType;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:20
 */

public class DataType {
    private long id;
    private String value;
    private String name;
    private long parentId;
    private DataType parent;
    private String description;
    private boolean deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public DataType getParent() {
        return parent;
    }

    public void setParent(DataType parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public BasicType toBasicType() {
        if (value.equals("BOOLEAN")) {
            return BasicType.Boolean;
        }

        if (value.equals("INTEGER") || value.equals("INT")) {
            return BasicType.Integer;
        }

        if (value.equals("BIGINT")) {
            return BasicType.Long;
        }

        if (value.equals("FLOAT")) {
            return BasicType.Float;
        }

        if (value.equals("DOUBLE")) {
            return BasicType.Double;
        }

        if (value.equals("STRING") || value.equals("TEXT")) {
            return BasicType.String;
        }

        if (value.equals("DATE")) {
            return BasicType.Date;
        }

        return null;
    }

    @Override
    public String toString() {
        return "DataType{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", parent=" + parent +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
