package com.anshishagua.mybatis.mapper;

import com.anshishagua.compute.TaskDependency;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:28
 */

@Mapper
public interface TaskDependencyMapper {
    TaskDependency getById(long id);
    List<TaskDependency> getAll();
    void insert(TaskDependency dependency);
}