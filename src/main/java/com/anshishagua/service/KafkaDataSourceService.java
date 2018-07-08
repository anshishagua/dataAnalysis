package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.KafkaDataSourceMapper;
import com.anshishagua.object.KafkaDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: lixiao
 * Date: 2018/7/8
 * Time: 下午1:33
 */

@Service
public class KafkaDataSourceService {
    @Autowired
    private KafkaDataSourceMapper mapper;

    public KafkaDataSource getByTableId(long tableId) {
        return mapper.getByTableId(tableId);
    }

    public KafkaDataSource getById(long id) {
        return mapper.getById(id);
    }

    public void insert(KafkaDataSource kafkaDataSource) {
        mapper.insert(kafkaDataSource);
    }
}