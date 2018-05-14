package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.IndexDimension;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:50
 */

@Mapper
public interface IndexDimensionMapper {
    List<IndexDimension> getByIndexId(@Param("indexId") long indexId);
    void insert(IndexDimension dimension);
}