package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.Table;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:38
 */

@Mapper
public interface TableMapper {
    Table getById(long id);
    Table getByName(String name);
}