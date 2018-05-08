package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.TableColumn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:50
 */

@Mapper
public interface TableColumnMapper {
    TableColumn getById(long id);
    List<TableColumn> getByTableId(long id);
}