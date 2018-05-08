package com.anshishagua.service;

import com.anshishagua.object.TableColumn;
import com.anshishagua.object.TableRelation;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.sql.Condition;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.parser.nodes.sql.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/5/4
 * Time: 下午10:54
 */

@Service
public class BasicSQLService {
    @Autowired
    private TableRelationService tableRelationService;

    public Node concatPrimaryKeys(List<TableColumn> primaryKeys) {
        return null;
    }

    //tableNames中的所有表和targetTableName建立JOIN关系,先考虑所有表都和targetTableName直接关联

    /*
                       join
                  join      c
                 a    b

               a join b join c join d


     */

    public Join buildJoinClauses(Set<String> tableNames, String targetTableName) {
        Join join = null;

        tableNames.remove(targetTableName);

        for (String tableName : tableNames) {
            List<TableRelation> tableRelations = tableRelationService.getByTable(targetTableName, tableName);

            TableRelation relation = tableRelations.get(0);

            Node left = null;

            if (join == null) {
                left = new Table(targetTableName);
            } else {
                left = join;
            }

            Node right = new Table(tableName);
            Condition joinCondition = new Equal(new Column(relation.getLeftTable().getName(), relation.getLeftColumn().getName()),
                    new Column(relation.getRightTable().getName(), relation.getRightColumn().getName()));

            join = new Join(left, right, relation.getJoinType(), joinCondition);
        }

        return join;
    }
}
