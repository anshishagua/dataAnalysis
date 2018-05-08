package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.SystemParameter;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午5:21
 */

@Mapper
public interface SystemParameterMapper {
    SystemParameter getById(long id);
    SystemParameter getByName(String name);
}