package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.DataType;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:19
 */

@Mapper
public interface DataTypeMapper {
    DataType getById(long id);
}