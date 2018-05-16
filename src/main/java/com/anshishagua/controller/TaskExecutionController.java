package com.anshishagua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: lixiao
 * Date: 2018/5/15
 * Time: 下午11:10
 */

@Controller
@RequestMapping("/taskExecution")
public class TaskExecutionController {
    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/taskExecution/index");

        return modelAndView;
    }
}
