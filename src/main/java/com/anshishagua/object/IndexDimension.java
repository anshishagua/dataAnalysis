package com.anshishagua.object;

import com.anshishagua.parser.BasicType;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:15
 */

public class IndexDimension {
    private long id;
    private long indexId;
    private int order;
    private String name;
    private String expression;
    private long typeId;
    private BasicType dataType;
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

    public long getIndexId() {
        return indexId;
    }

    public void setIndexId(long indexId) {
        this.indexId = indexId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public BasicType getDataType() {
        return dataType;
    }

    public void setDataType(BasicType dataType) {
        this.dataType = dataType;
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
    public boolean equals(Object obj) {
        return obj instanceof IndexDimension && ((IndexDimension) obj).getId() == id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}