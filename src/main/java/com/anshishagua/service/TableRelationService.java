package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.TableColumnMapper;
import com.anshishagua.mybatis.mapper.TableMapper;
import com.anshishagua.mybatis.mapper.TableRelationMapper;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.TableRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

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

    private void updateTableColumn(TableRelation relation) {
        Objects.requireNonNull(relation);

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

    public TableRelation getById(long id) {
        TableRelation relation = tableRelationMapper.getById(id);

        if (relation != null) {
            updateTableColumn(relation);
        }

        return relation;
    }

    public List<TableRelation> getAllRelations() {
        List<TableRelation> relations = tableRelationMapper.list();

        for (TableRelation relation : relations) {
           updateTableColumn(relation);
        }

        return relations;
    }

    public List<TableRelation> getByLeftTable(String leftTableName) {
        List<TableRelation> relations = tableRelationMapper.getByLeftTable(leftTableName);

        for (TableRelation relation : relations) {
            updateTableColumn(relation);
        }

        return relations;
    }

    public List<TableRelation> getByTable(String leftTableName, String rightTableName) {
        List<TableRelation> relations = tableRelationMapper.getByTable(leftTableName, rightTableName);

        for (TableRelation relation : relations) {
            updateTableColumn(relation);
        }

        return relations;
    }

    public TableRelation getByTableColumn(String leftColumn, String rightColumn) {
        TableRelation relation = tableRelationMapper.getByTableColumn(leftColumn.split("\\.")[0],
                leftColumn.split("\\.")[1], rightColumn.split("\\.")[0], rightColumn.split("\\.")[1]);

        if (relation == null) {
            return relation;
        }

        updateTableColumn(relation);

        return relation;
    }

    public void addRelation(TableRelation relation) {
        tableRelationMapper.insert(relation);
    }

    private void dfs(String source, String target, Set<String> visited, Stack<String> stack, List<List<String>> paths) {
        stack.push(source);
        visited.add(source);

        if (target.equals(source)) {
            Iterator<String> iterator = stack.iterator();

            List<String> path = new ArrayList<>();

            while (iterator.hasNext()) {
                path.add(iterator.next());
            }

            paths.add(path);

            visited.remove(source);
            stack.pop();

            return;
        }

        List<TableRelation> relations = getByLeftTable(source);

        for (TableRelation relation : relations) {
            Table table = relation.getRightTable();

            if (!visited.contains(table.getName())) {
                dfs(table.getName(), target, visited, stack, paths);
            }
        }

        visited.remove(source);
        stack.pop();
    }

    public List<List<String>> findRelationPaths(String leftTable, String rightTable) {
        Objects.requireNonNull(leftTable);
        Objects.requireNonNull(rightTable);

        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        List<List<String>> paths = new ArrayList<>();
        dfs(leftTable, rightTable, visited, stack, paths);

        return paths;
    }
}