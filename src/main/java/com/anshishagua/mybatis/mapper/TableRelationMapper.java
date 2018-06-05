package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.TableRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午4:17
 */

@Mapper
public interface TableRelationMapper {
    TableRelation getById(long id);
    List<TableRelation> getByLeftTable(@Param("leftTableName") String leftTableName);
    List<TableRelation> getByTable(@Param("leftTableName") String leftTableName,
                                   @Param("rightTableName") String rightTableName);
    TableRelation getByTableColumn(@Param("leftTableName") String leftTableName,
                                   @Param("leftColumnName") String leftColumnName,
                                   @Param("rightTableName") String rightTableName,
                                   @Param("rightColumnName") String rightColumnName);
    void insert(TableRelation relation);
    List<TableRelation> list();
}