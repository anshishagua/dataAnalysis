package com.anshishagua.mybatis.mapper;

import com.anshishagua.compute.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:41
 */

@Mapper
public interface TaskMapper {
    Task getById(long id);
    Task getByTaskTypeAndObjectId(@Param("taskType") String taskType, @Param("objectId") long objectId);
    Task getByObjectId(long id);
    void addNewTask(Task task);
    List<Task> list();
}