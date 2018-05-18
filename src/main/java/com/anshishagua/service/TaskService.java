package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.mybatis.mapper.TaskMapper;
import com.anshishagua.object.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Task getByTaskTypeAndObjectId(TaskType taskType, long objectId) {
        return taskMapper.getByTaskTypeAndObjectId(taskType.getValue(), objectId);
    }

    public Task getByObjectId(long id) {
        return taskMapper.getByObjectId(id);
    }

    public void addNewTask(Task task) {
        taskMapper.addNewTask(task);
    }

    public List<Task> getAllTasks() {
        return taskMapper.list();
    }
}