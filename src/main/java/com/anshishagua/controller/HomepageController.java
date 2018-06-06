package com.anshishagua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: lixiao
 * Date: 2018/5/15
 * Time: 下午10:55
 */

@Controller
@RequestMapping("/")
public class HomepageController {
    @RequestMapping("/")
    public String index() {
        return "table/index";
    }
}
