package com.anshishagua.object;

import java.time.LocalDateTime;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午5:22
 */

public class SystemParameter {
    private long id;
    private String name;
    private String value;
    private long typeId;
    private DataType dataType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
    public String toString() {
        return "SystemParameter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", dataType=" + dataType +
                ", createTime=" + createTime +
                ", lastUpdated=" + lastUpdated +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}