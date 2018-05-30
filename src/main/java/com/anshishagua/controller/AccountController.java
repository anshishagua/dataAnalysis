package com.anshishagua.controller;

import com.anshishagua.object.Result;
import com.anshishagua.object.User;
import com.anshishagua.service.UserService;
import com.anshishagua.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: lixiao
 * Date: 2018/5/30
 * Time: 上午10:35
 */

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String index(Model model) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();

        User user = userService.getByUserName(username);

        model.addAttribute("user", user);

        return "account/index";
    }

    @GetMapping("/modifyPassword")
    public String modifyPassword() {
        return "account/modifyPassword";
    }

    @PostMapping("/modifyPassword")
    @ResponseBody
    public Result modifyPassword(@RequestParam("username") String username,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword) {
        User user = userService.getByUserName(username);

        if (user == null) {
            return Result.error(String.format("用户%s不存在", username));
        }

        oldPassword = MD5Utils.encrypt(oldPassword);

        if (!user.getPassword().equals(oldPassword)) {
            return Result.error(String.format("旧密码错误"));
        }

        newPassword = MD5Utils.encrypt(newPassword);
        user.setPassword(newPassword);

        userService.updatePassword(user);

        return Result.ok();
    }
}