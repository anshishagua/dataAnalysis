package com.anshishagua.controller;

import com.alibaba.fastjson.JSON;
import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskDependency;
import com.anshishagua.object.Index;
import com.anshishagua.object.Table;
import com.anshishagua.object.Tag;
import com.anshishagua.object.TaskType;
import com.anshishagua.service.IndexService;
import com.anshishagua.service.TableService;
import com.anshishagua.service.TagService;
import com.anshishagua.service.TaskDependencyService;
import com.anshishagua.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: lixiao
 * Date: 2018/5/18
 * Time: 上午12:02
 */

@Controller
@RequestMapping("/taskDependency")
public class TaskDependencyController {
    @Autowired
    private TaskDependencyService taskDependencyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TableService tableService;
    @Autowired
    private TagService tagService;
    @Autowired
    private IndexService indexService;

    private class Edge {
        private String source;
        private String target;

        public Edge(String source, String target) {
            this.source = source;
            this.target = target;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }
    }

    private class Vertex {
        private String name;
        private String color;
    }

    private String getVertexName(TaskType taskType, long objectId) {
        switch (taskType) {
            case DATA_LOAD:
                Table table = tableService.getById(objectId);
                return String.format("表:%s", table.getAlias());
            case INDEX:
                Index index = indexService.getById(objectId);
                return  String.format("指标:%s", index.getName());
            case TAG:
                Tag tag = tagService.getById(objectId);
                return String.format("标签:%s", tag.getName());
        }

        return null;
    }

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        List<TaskDependency> dependencies = taskDependencyService.getAllDependencies();

        Map<Long, String> vertices = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        for (TaskDependency dependency : dependencies) {
            long sourceId = dependency.getTaskId();
            long destId = dependency.getDependentTaskId();

            Task task = taskService.getById(sourceId);
            Task dependentTask = taskService.getById(destId);

            vertices.put(task.getId(), getVertexName(task.getTaskType(), task.getObjectId()));
            vertices.put(dependentTask.getId(), getVertexName(dependentTask.getTaskType(), dependentTask.getObjectId()));

            edges.add(new Edge(vertices.get(dependentTask.getId()), vertices.get(task.getId())));
        }

        List<Map<String, String>> list = new ArrayList<>();

        vertices.forEach((key, value) -> {
            Map<String, String> data = new HashMap<>();
            data.put("name", value);

            list.add(data);
        });

        List<Map<String, String>> categories = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "表");
        categories.add(map);
        map = new HashMap<>();
        map.put("name", "标签");
        categories.add(map);
        map = new HashMap<>();
        map.put("name", "指标");
        categories.add(map);

        modelAndView.addObject("nodeTypes", JSON.toJSONString(Arrays.asList("表", "标签", "指标")));
        modelAndView.addObject("categories", JSON.toJSONString(categories));
        modelAndView.addObject("vertices", JSON.toJSONString(list));
        modelAndView.addObject("links", JSON.toJSONString(edges));
        modelAndView.addObject("dependencies", dependencies);
        modelAndView.setViewName("taskDependency/index");

        return modelAndView;
    }
}
