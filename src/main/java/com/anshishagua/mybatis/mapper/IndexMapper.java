package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.Index;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:00
 */

@Mapper
public interface IndexMapper {
    Index getById(long id);
    Index getByName(String name);
    void insert(Index index);
}