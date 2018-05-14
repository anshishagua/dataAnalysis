package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.mybatis.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:41
 */

@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    public Task getById(long id) {
        return taskMapper.getById(id);
    }

    public Task getByObjectId(long id) {
        return taskMapper.getByObjectId(id);
    }

    public void addNewTask(Task task) {
        taskMapper.addNewTask(task);
    }
}