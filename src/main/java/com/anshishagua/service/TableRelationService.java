package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.TableColumnMapper;
import com.anshishagua.mybatis.mapper.TableMapper;
import com.anshishagua.mybatis.mapper.TableRelationMapper;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.TableRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午4:26
 */

@Service
public class TableRelationService {
    @Autowired
    private TableRelationMapper tableRelationMapper;
    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private TableColumnMapper tableColumnMapper;

    public TableRelation getById(long id) {
        TableRelation relation = tableRelationMapper.getById(id);

        if (relation != null) {
            TableColumn column = tableColumnMapper.getById(relation.getLeftColumnId());

            relation.setLeftColumn(column);

            if (column != null) {
                relation.setLeftTable(tableMapper.getById(column.getTableId()));
            }

            column = tableColumnMapper.getById(relation.getRightColumnId());

            relation.setRightColumn(column);

            if (column != null) {
                relation.setRightTable(tableMapper.getById(column.getTableId()));
            }
        }

        return relation;
    }

    public List<TableRelation> getByTable(String leftTableName, String rightTableName) {
        List<TableRelation> relations = tableRelationMapper.getByTable(leftTableName, rightTableName);

        for (TableRelation relation : relations) {
            TableColumn column = tableColumnMapper.getById(relation.getLeftColumnId());

            relation.setLeftColumn(column);

            if (column != null) {
                relation.setLeftTable(tableMapper.getById(column.getTableId()));
            }

            column = tableColumnMapper.getById(relation.getRightColumnId());

            relation.setRightColumn(column);

            if (column != null) {
                relation.setRightTable(tableMapper.getById(column.getTableId()));
            }
        }

        return relations;
    }

    public TableRelation getByTableColumn(String leftColumn, String rightColumn) {
        TableRelation relation = tableRelationMapper.getByTableColumn(leftColumn.split("\\.")[0],
                leftColumn.split("\\.")[1], rightColumn.split("\\.")[0], rightColumn.split("\\.")[1]);

        if (relation == null) {
            return relation;
        }

        TableColumn column = tableColumnMapper.getById(relation.getLeftColumnId());

        relation.setLeftColumn(column);

        if (column != null) {
            relation.setLeftTable(tableMapper.getById(column.getTableId()));
        }

        column = tableColumnMapper.getById(relation.getRightColumnId());

        relation.setRightColumn(column);

        if (column != null) {
            relation.setRightTable(tableMapper.getById(column.getTableId()));
        }

        return relation;
    }
}