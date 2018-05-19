package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.SystemParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午5:21
 */

@Mapper
public interface SystemParameterMapper {
    SystemParameter getById(long id);
    SystemParameter getByName(String name);
    List<SystemParameter> list();
    void insert(SystemParameter systemParameter);
}