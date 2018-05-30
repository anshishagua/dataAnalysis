package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.UserRoleMapper;
import com.anshishagua.object.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: lixiao
 * Date: 2018/5/29
 * Time: 下午5:03
 */

@Service
public class UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public UserRole getByRoleId(long id) {
        return userRoleMapper.getById(id);
    }

    public UserRole getByUserId(long userId) {
        return userRoleMapper.getByUserId(userId);
    }
}