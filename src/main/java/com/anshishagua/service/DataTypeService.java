package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.DataTypeMapper;
import com.anshishagua.object.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:33
 */

@Service
public class DataTypeService {
    @Autowired
    private DataTypeMapper dataTypeMapper;

    public DataType getTypeById(long id) {
        return dataTypeMapper.getById(id);
    }

    public DataType getTypeByValue(String value) {
        Objects.requireNonNull(value);

        return dataTypeMapper.getByValue(value);
    }

    public List<DataType> getAll() {
        return dataTypeMapper.list();
    }
}