package com.anshishagua.controller;

import com.anshishagua.compute.Task;
import com.anshishagua.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/15
 * Time: 下午11:08
 */

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        List<Task> tasks = taskService.getAllTasks();

        modelAndView.addObject("tasks", tasks);

        modelAndView.setViewName("task/index");

        return modelAndView;
    }
}
