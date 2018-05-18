package com.anshishagua.object;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午4:50
 */

public class Tag {
    private static final long NO_ID = -1;

    private long id;
    private String name;
    private long groupId;
    private long tableId;
    private Table table;
    private String filterCondition;
    private String computeCondition;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;
    private boolean deleted;
    private String description;
    private SQLGenerateResult sqlGenerateResult;

    public Tag() {
        id = NO_ID;
    }

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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getComputeCondition() {
        return computeCondition;
    }

    public void setComputeCondition(String computeCondition) {
        this.computeCondition = computeCondition;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SQLGenerateResult getSqlGenerateResult() {
        return sqlGenerateResult;
    }

    public void setSqlGenerateResult(SQLGenerateResult sqlGenerateResult) {
        this.sqlGenerateResult = sqlGenerateResult;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tableId=" + tableId +
                ", filterCondition='" + filterCondition + '\'' +
                ", computeCondition='" + computeCondition + '\'' +
                ", createTime=" + createTime +
                ", lastUpdated=" + lastUpdated +
                ", deleted=" + deleted +
                ", description='" + description + '\'' +
                '}';
    }
}