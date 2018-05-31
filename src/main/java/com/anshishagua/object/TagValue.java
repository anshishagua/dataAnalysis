package com.anshishagua.object;

import java.time.LocalDateTime;

/**
 * User: lixiao
 * Date: 2018/5/30
 * Time: 上午11:38
 */

public class TagValue {
    private long id;
    private long tagId;
    private Tag tag;
    private String value;
    private int order;
    private String filterCondition;
    private String computeCondition;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdated;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TagValue{" +
                "id=" + id +
                ", tagId=" + tagId +
                ", tag=" + tag +
                ", value='" + value + '\'' +
                ", order=" + order +
                ", filterCondition='" + filterCondition + '\'' +
                ", computeCondition='" + computeCondition + '\'' +
                ", createTime=" + createTime +
                ", lastUpdated=" + lastUpdated +
                ", description='" + description + '\'' +
                '}';
    }
}