package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.KafkaDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User: lixiao
 * Date: 2018/7/8
 * Time: 上午11:43
 */

@Mapper
public interface KafkaDataSourceMapper {
    KafkaDataSource getById(@Param("id") long id);
    KafkaDataSource getByTableId(@Param("tableId") long tableId);
    void insert(KafkaDataSource kafkaSource);
}