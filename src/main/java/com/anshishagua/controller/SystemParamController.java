package com.anshishagua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: lixiao
 * Date: 2018/5/19
 * Time: 下午5:32
 */

@Controller
@RequestMapping("/systemParam")
public class SystemParamController {
    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("systemParam/index");

        return modelAndView;
    }
}
