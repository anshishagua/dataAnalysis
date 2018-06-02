package com.anshishagua.controller;

import com.anshishagua.object.Result;
import com.anshishagua.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: lixiao
 * Date: 2018/5/29
 * Time: 下午5:22
 */

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("")
    public ModelAndView login() {
        return new ModelAndView("account/login");
    }

    @PostMapping("")
    @ResponseBody
    public Result doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.encrypt(password));
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