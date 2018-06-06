package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.ObjectReference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/6/6
 * Time: 下午3:13
 */

@Mapper
public interface ObjectReferenceMapper {
    void insert(ObjectReference objectReference);
    void insertBatch(@Param("list") List<ObjectReference> list);
    List<ObjectReference> getByObjectId(@Param("objectId") long objectId);
}