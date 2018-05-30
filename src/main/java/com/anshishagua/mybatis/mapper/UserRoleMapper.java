package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: lixiao
 * Date: 2018/5/29
 * Time: 下午4:54
 */

@Mapper
public interface UserRoleMapper {
    UserRole getById(long id);
    UserRole getByUserId(long userId);
}