package com.anshishagua.mybatis.mapper;

import com.anshishagua.compute.TaskExecution;
import com.anshishagua.constants.TaskStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午9:40
 */

@Mapper
public interface TaskExecutionMapper {
    void insert(TaskExecution taskExecution);
    TaskExecution getByTaskIdAndDate(@Param("taskId") long taskId, @Param("executeDate") String executeDate);
    TaskExecution getById(long id);
    List<TaskExecution> getByStatus(TaskStatus taskStatus);
    List<TaskExecution> list();
    void update(TaskExecution taskExecution);
}