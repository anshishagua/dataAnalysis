package com.anshishagua.mybatis.mapper;

import com.anshishagua.compute.Task;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:41
 */

@Mapper
public interface TaskMapper {
    Task getById(long id);
    Task getByObjectId(long id);
    void addNewTask(Task task);
}