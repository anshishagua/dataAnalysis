package com.anshishagua.object;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午10:57
 */

public class Index {
    private long id;
    private String name;
    private IndexType indexType;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdated;
    private String description;
    private boolean deleted;
    private List<IndexDimension> dimensions;
    private List<IndexMetric> metrics;

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

    public IndexType getIndexType() {
        return indexType;
    }

    public void setIndexType(IndexType indexType) {
        this.indexType = indexType;
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

    public List<IndexDimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<IndexDimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<IndexMetric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<IndexMetric> metrics) {
        this.metrics = metrics;
    }
}