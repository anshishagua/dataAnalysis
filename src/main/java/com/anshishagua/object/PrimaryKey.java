package com.anshishagua.object;

import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.string.Concat;
import com.anshishagua.parser.nodes.primitive.StringValue;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/6/7
 * Time: 上午10:16
 */

public class PrimaryKey {
    private String tableName;
    private List<TableColumn> keyColumns;
    private List<String> combinedKeys;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setKeyColumns(List<TableColumn> keyColumns) {
        Objects.requireNonNull(keyColumns);

        this.keyColumns = keyColumns;
        this.combinedKeys = keyColumns.stream().map(it -> it.getName()).collect(Collectors.toList());
    }

    public String getCombinedKey() {
        return combinedKeys.stream().collect(Collectors.joining("_"));
    }

    public Concat toConcatNode() {
        List<Node> children = new ArrayList<>();

        Iterator<String> iterator = combinedKeys.iterator();

        while (iterator.hasNext()) {
            String columnName = iterator.next();

            children.add(new Column(tableName, columnName));

            if (iterator.hasNext()) {
                children.add(new StringValue("_"));
            }
        }

        Concat concat = new Concat(children);

        return concat;
    }

    public String getCombinedKeySQL() {
        StringBuilder builder = new StringBuilder("CONCAT(");

        Iterator<String> iterator = combinedKeys.iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();

            builder.append(StringUtils.backQuote(tableName));
            builder.append(".");
            builder.append(StringUtils.backQuote(key));

            if (iterator.hasNext()) {
                builder.append(", '_', ");
            }
        }

        builder.append(")");

        return builder.toString();
    }

    public boolean isCombined() {
        return combinedKeys.size() > 1;
    }

    public DataType getDataType() {
        if (isCombined()) {
            return BasicType.String.toDataType();
        }

        return keyColumns.get(0).getDataType();
    }
}