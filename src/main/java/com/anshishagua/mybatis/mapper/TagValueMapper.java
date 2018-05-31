package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.TagValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/31
 * Time: 下午4:18
 */

@Mapper
public interface TagValueMapper {
    void insert(TagValue tagValue);
    TagValue getById(long id);
    List<TagValue> getByTagId(long tagId);
}