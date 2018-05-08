package com.anshishagua.object;

import com.anshishagua.parser.nodes.sql.JoinType;

import java.time.LocalDateTime;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午4:15
 */

public class TableRelation {
    private long id;
    private JoinType joinType;
    private Table leftTable;
    private TableColumn leftColumn;
    private long leftColumnId;
    private long rightColumnId;
    private Table rightTable;
    private TableColumn rightColumn;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdated;
    private boolean deleted;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public Table getLeftTable() {
        return leftTable;
    }

    public void setLeftTable(Table leftTable) {
        this.leftTable = leftTable;
    }

    public TableColumn getLeftColumn() {
        return leftColumn;
    }

    public void setLeftColumn(TableColumn leftColumn) {
        this.leftColumn = leftColumn;
    }

    public long getLeftColumnId() {
        return leftColumnId;
    }

    public void setLeftColumnId(long leftColumnId) {
        this.leftColumnId = leftColumnId;
    }

    public long getRightColumnId() {
        return rightColumnId;
    }

    public void setRightColumnId(long rightColumnId) {
        this.rightColumnId = rightColumnId;
    }

    public Table getRightTable() {
        return rightTable;
    }

    public void setRightTable(Table rightTable) {
        this.rightTable = rightTable;
    }

    public TableColumn getRightColumn() {
        return rightColumn;
    }

    public void setRightColumn(TableColumn rightColumn) {
        this.rightColumn = rightColumn;
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

    @Override
    public String toString() {
        return "TableRelation{" +
                "id=" + id +
                ", joinType=" + joinType +
                ", leftTable=" + leftTable +
                ", leftColumn=" + leftColumn +
                ", leftColumnId=" + leftColumnId +
                ", rightColumnId=" + rightColumnId +
                ", rightTable=" + rightTable +
                ", rightColumn=" + rightColumn +
                ", createTime=" + createTime +
                ", lastUpdated=" + lastUpdated +
                ", deleted=" + deleted +
                '}';
    }
}