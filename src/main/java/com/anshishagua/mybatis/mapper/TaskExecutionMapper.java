package com.anshishagua.mybatis.mapper;

import com.anshishagua.compute.TaskExecution;
import com.anshishagua.object.TaskStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午9:40
 */

@Mapper
public interface TaskExecutionMapper {
    TaskExecution getById(long id);
    List<TaskExecution> getByStatus(TaskStatus taskStatus);
}