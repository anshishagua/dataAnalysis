package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.IndexMetric;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:51
 */

@Mapper
public interface IndexMetricMapper {
    List<IndexMetric> getByIndexId(@Param("indexId") long indexId);
    void insert(IndexMetric metric);
}