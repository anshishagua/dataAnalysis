package com.anshishagua.service;

import com.anshishagua.exceptions.UnableToJoinException;
import com.anshishagua.object.Index;
import com.anshishagua.object.IndexDimension;
import com.anshishagua.object.IndexMetric;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.parser.nodes.sql.Insert;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.parser.nodes.sql.Query;
import com.anshishagua.parser.nodes.sql.Table;
import com.anshishagua.utils.CollectionUtils;
import com.anshishagua.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 上午11:42
 */

@Service
public class IndexSQLGenerateService {
    @Autowired
    private IndexService indexService;
    @Autowired
    private BasicSQLService basicSQLService;

    public String generateIndexTableName(Index index) {
        Objects.requireNonNull(index);

        return String.format("index_%d", index.getId());
    }

    public String createIndexCreateSQL(Index index) {
        Objects.requireNonNull(index);

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS ");
        String indexTableName = generateIndexTableName(index);
        builder.append(StringUtils.backQuote(indexTableName));

        builder.append("(");

        List<String> columns = new ArrayList<>(index.getDimensions().size() + index.getMetrics().size());

        for (IndexDimension dimension : index.getDimensions()) {
            columns.add(String.format("`dimension_%d` %s", dimension.getId(), dimension.getDataType().toDataType().getValue()));
        }

        for (IndexMetric metric : index.getMetrics()) {
            columns.add(String.format("`metric_%d` %s", metric.getId(), metric.getDataType().toDataType().getValue()));
        }

        builder.append(columns.stream().collect(Collectors.joining(", ")));

        builder.append(")");

        return builder.toString();
    }

    public SQLGenerateResult generate(Index index) {
        Objects.requireNonNull(index);

        SQLGenerateResult result = SQLGenerateResult.ok();

        List<IndexDimension> dimensions = index.getDimensions();
        List<IndexMetric> metrics = index.getMetrics();

        List<ParseResult> dimensionParseResults = new ArrayList<>(dimensions.size());
        List<ParseResult> metricParseResults = new ArrayList<>(metrics.size());

        Set<String> tableNames = new HashSet<>();

        for (IndexDimension dimension : dimensions) {
            ParseResult parseResult = indexService.parseDimension(dimension.getExpression(), index.getIndexType());

            if (!parseResult.isSuccess()) {
                return SQLGenerateResult.error(parseResult.getErrorMessage());
            }

            dimensionParseResults.add(parseResult);

            dimension.setDataType(parseResult.getResultType());
            tableNames.addAll(parseResult.getTables().stream().map(table -> table.getName()).collect(Collectors.toList()));
        }

        for (IndexMetric metric : metrics) {
            ParseResult parseResult = indexService.parseMetric(metric.getExpression(), index.getIndexType());

            if (!parseResult.isSuccess()) {
                return SQLGenerateResult.error(parseResult.getErrorMessage());
            }

            metricParseResults.add(parseResult);

            metric.setDataType(parseResult.getResultType());
            tableNames.addAll(parseResult.getTables().stream().map(table -> table.getName()).collect(Collectors.toList()));
        }

        String indexTableName = generateIndexTableName(index);
        result.addExecuteSQL(createIndexCreateSQL(index));
        Set<String> set = new HashSet<>();
        set.add(indexTableName);
        result.setTargetTables(set);

        Query query = new Query();

        for (ParseResult dimensionParseResult : dimensionParseResults) {
            query.select(dimensionParseResult.getAstTree());
            query.groupBy(dimensionParseResult.getAstTree());
        }

        for (ParseResult metricParseResult : metricParseResults) {
            query.select(metricParseResult.getAstTree());
        }

        //只有一个表,只用from
        if (CollectionUtils.hasSingleElement(tableNames)) {
            String tableName = tableNames.iterator().next();

            query.from(new Table(tableName));
        } else {
            Join join = null;

            try {
                join = basicSQLService.buildJoinClauses(tableNames);
            } catch (UnableToJoinException ex) {
                return SQLGenerateResult.error(ex.getMessage());
            }

            query.join(join);
        }

        result.setDataSourceTables(tableNames);
        Insert insert = new Insert(indexTableName, query, true);

        result.addExecuteSQL(insert.toString());

        return result;
    }
}