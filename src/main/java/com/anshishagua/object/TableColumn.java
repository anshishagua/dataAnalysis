package com.anshishagua.object;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:48
 */

public class TableColumn {
    private long id;
    private long tableId;
    private long typeId;
    private DataType dataType;
    private boolean isPrimaryKey;
    private boolean nullable;
    private String name;
    private String alias;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdated;
    private String description;
    private boolean deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TableColumn && ((TableColumn) obj).getId() == id;
    }

    @Override
    public String toString() {
        return "TableColumn{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", dataType=" + dataType +
                ", isPrimaryKey=" + isPrimaryKey +
                ", nullable=" + nullable +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", createTime=" + createTime +
                ", lastUpdated=" + lastUpdated +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}