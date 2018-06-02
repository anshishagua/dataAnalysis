package com.anshishagua.controller;

import com.anshishagua.object.Result;
import com.anshishagua.object.User;
import com.anshishagua.service.UserService;
import com.anshishagua.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: lixiao
 * Date: 2018/5/30
 * Time: 上午12:30
 */

@Controller
public class RegisterController {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        User user = userService.getByUserName(username);

        if (user != null) {
            return Result.error("用户" + username + "已存在");
        }

        user = new User();
        user.setUsername(username);
        password = MD5Utils.encrypt(password);
        user.setPassword(password);

        userService.addNewUser(user);

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (Exception ex) {
            LOG.error("Failed to login:", ex);

            return Result.error(ex.getMessage());
        }

        return Result.ok();
    }
}