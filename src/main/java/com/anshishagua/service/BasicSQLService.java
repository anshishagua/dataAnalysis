package com.anshishagua.service;

import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.TableRelation;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.sql.Condition;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/4
 * Time: 下午10:54
 */

@Service
public class BasicSQLService {
    @Autowired
    private TableRelationService tableRelationService;

    public String generateTempTableName() {
        return "tmp_table_" + UUID.randomUUID().toString().replace("-", "");
    }

    public String createTemporaryTable(String tableName, List<TableColumn> columns) {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TEMPORARY TABLE IF NOT EXISTS ").append(StringUtils.backQuote(tableName));

        builder.append(" (");

        builder.append(columns.stream().map(column -> String.format("%s %s", StringUtils.backQuote(column.getName()), column.getDataType().getValue())).collect(Collectors.joining(", ")));

        builder.append(")");

        return builder.toString();
    }

    public String dropTable(String tableName) {
        return String.format("DROP TABLE IF EXISTS %s", StringUtils.backQuote(tableName));
    }

    public SQLGenerateResult generateTableCreateSQL(Table table) {
        Objects.requireNonNull(table);

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(StringUtils.backQuote(table.getName())).append("(");

        builder.append(table.getColumns().stream().map(column -> String.format("%s %s", StringUtils.backQuote(column.getName()), column.getDataType().getValue())).collect(Collectors.joining(", ")));

        builder.append(")");

        SQLGenerateResult result = new SQLGenerateResult();
        result.setSuccess(true);
        List<String> executeSQLs = new ArrayList<>();
        executeSQLs.add(builder.toString());

        result.setExecuteSQLs(executeSQLs);

        return result;
    }

    //tableNames中的所有表和targetTableName建立JOIN关系,先考虑所有表都和targetTableName直接关联
    /*
                          join
                         /     \
                       join     c
                      /    \
                     a      b

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
                left = new com.anshishagua.parser.nodes.sql.Table(targetTableName);
            } else {
                left = join;
            }

            Node right = new com.anshishagua.parser.nodes.sql.Table(tableName);
            Condition joinCondition = new Equal(new Column(relation.getLeftTable().getName(), relation.getLeftColumn().getName()),
                    new Column(relation.getRightTable().getName(), relation.getRightColumn().getName()));

            join = new Join(left, right, relation.getJoinType(), joinCondition);
        }

        return join;
    }
}