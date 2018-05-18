package com.anshishagua.controller;

import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskExecution;
import com.anshishagua.object.TaskType;
import com.anshishagua.service.TaskExecutionService;
import com.anshishagua.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/15
 * Time: 下午11:10
 */

@Controller
@RequestMapping("/taskExecution")
public class TaskExecutionController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskExecutionService taskExecutionService;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("taskExecution/index");

        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();

        List<TaskExecution> taskExecutions = taskExecutionService.getAll();

        modelAndView.addObject("taskExecutions", taskExecutions);
        modelAndView.setViewName("taskExecution/list");

        return modelAndView;
    }

    @RequestMapping("/execute")
    public ModelAndView execute(@RequestParam("type") String type,
                                @RequestParam("objectId") long objectId) {
        TaskType taskType = TaskType.parseByValue(type);
        Task task = null;

        if (taskType == TaskType.INDEX) {
            task = taskService.getByTaskTypeAndObjectId(TaskType.INDEX, objectId);
        } else if (taskType == TaskType.TAG) {
            task = taskService.getByTaskTypeAndObjectId(TaskType.TAG, objectId);
        }

        LocalDate today = LocalDate.now();

        taskExecutionService.executeTask(task, today.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("taskExecutions", taskExecutionService.getAll());
        modelAndView.setViewName("taskExecution/list");

        return modelAndView;
    }
}
