package com.anshishagua.object;

import com.anshishagua.constants.DataType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:46
 */

public class Table {
    private long id;
    private String name;
    private String alias;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdated;
    private String description;
    private boolean deleted;
    private List<TableColumn> columns;
    private List<TableColumn> primaryKeys;
    private DataType dataType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }

    public List<TableColumn> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<TableColumn> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public TableColumn getColumn(String columnName) {
        List<TableColumn> result = columns.stream().filter(it -> it.getName().equals(columnName)).collect(Collectors.toList());

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Table && ((Table) obj).getId() == id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", createTime=" + createTime +
                ", lastUpdated=" + lastUpdated +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ", columns=" + columns +
                '}';
    }
}
