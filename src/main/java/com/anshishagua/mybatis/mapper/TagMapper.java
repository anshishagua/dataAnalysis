package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午4:51
 */

@Mapper
public interface TagMapper {
    Tag getById(long id);
    Tag getByName(String name);
    List<Tag> getByNameLike(@Param("name") String name);
    void insert(Tag tag);
    void update(Tag tag);
    void updateSQLGenerateResult(Tag tag);
    List<Tag> list();
}