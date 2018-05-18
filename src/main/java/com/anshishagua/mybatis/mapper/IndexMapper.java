package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.Index;
import com.anshishagua.object.SQLGenerateResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:00
 */

@Mapper
public interface IndexMapper {
    List<Index> list();
    Index getById(long id);
    Index getByName(String name);
    void insert(Index index);
    void updateSQLGenerateResult(Index index);
}