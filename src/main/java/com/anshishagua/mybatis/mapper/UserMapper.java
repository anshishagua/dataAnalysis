package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: lixiao
 * Date: 2018/5/29
 * Time: 下午4:53
 */

@Mapper
public interface UserMapper {
    User getById(long id);
    User getByUserName(String username);
    void insert(User user);
}