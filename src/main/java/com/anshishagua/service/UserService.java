package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.UserMapper;
import com.anshishagua.object.User;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/29
 * Time: 下午5:01
 */

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getByUserId(long userId) {
        return userMapper.getById(userId);
    }

    public User getByUserName(String username) {
        if (Strings.isNullOrEmpty(username)) {
            return null;
        }

        return userMapper.getByUserName(username);
    }

    public void addNewUser(User user) {
        Objects.requireNonNull(user);

        userMapper.insert(user);
    }

    public void updatePassword(User user) {
        Objects.requireNonNull(user);

        userMapper.updatePassword(user);
    }
}