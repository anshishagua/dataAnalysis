package com.anshishagua.service;

import com.anshishagua.exceptions.UnableToJoinException;
import com.anshishagua.object.Index;
import com.anshishagua.object.IndexDimension;
import com.anshishagua.object.IndexMetric;
import com.anshishagua.constants.IndexType;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.Tag;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.sql.Condition;
import com.anshishagua.parser.nodes.sql.Insert;
import com.anshishagua.parser.nodes.sql.Join;
import com.anshishagua.parser.nodes.sql.JoinType;
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
    @Autowired
    private TagSQLGenerateService tagSQLGenerateService;
    @Autowired
    private TableService tableService;

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
        Set<String> indexTableNames = new HashSet<>();
        Set<String> systemParameters = new HashSet<>();

        for (IndexDimension dimension : dimensions) {
            ParseResult parseResult = indexService.parseDimension(dimension.getExpression(), index.getIndexType());

            if (!parseResult.isSuccess()) {
                return SQLGenerateResult.error(parseResult.getErrorMessage());
            }

            dimensionParseResults.add(parseResult);

            dimension.setDataType(parseResult.getResultType());

            if (index.getIndexType() == IndexType.DERIVED) {
                indexTableNames.addAll(parseResult.getIndices().stream().map(it -> generateIndexTableName(it)).collect(Collectors.toList()));
            } else {
                tableNames.addAll(parseResult.getTables().stream().map(table -> table.getName()).collect(Collectors.toList()));
            }

            systemParameters.addAll(parseResult.getSystemParameters().stream().map(it -> it.getName()).collect(Collectors.toSet()));
        }

        for (IndexMetric metric : metrics) {
            ParseResult parseResult = indexService.parseMetric(metric.getExpression(), index.getIndexType());

            if (!parseResult.isSuccess()) {
                return SQLGenerateResult.error(parseResult.getErrorMessage());
            }

            metricParseResults.add(parseResult);

            metric.setDataType(parseResult.getResultType());

            if (index.getIndexType() == IndexType.DERIVED) {
                indexTableNames.addAll(parseResult.getIndices().stream().map(it -> generateIndexTableName(it)).collect(Collectors.toList()));
            } else {
                tableNames.addAll(parseResult.getTables().stream().map(table -> table.getName()).collect(Collectors.toList()));
            }

            systemParameters.addAll(parseResult.getSystemParameters().stream().map(it -> it.getName()).collect(Collectors.toSet()));
        }

        String indexTableName = generateIndexTableName(index);
        result.addExecuteSQL(createIndexCreateSQL(index));
        Set<String> set = new HashSet<>();
        set.add(indexTableName);
        result.setTargetTables(set);

        Set<Tag> tags = new HashSet<>();

        Query query = new Query();

        for (ParseResult dimensionParseResult : dimensionParseResults) {
            //isTag
            if (!dimensionParseResult.getTags().isEmpty()) {
                Tag tag = dimensionParseResult.getTags().iterator().next();
                tags.add(tag);
                query.select(new Column(tagSQLGenerateService.generateTagTableName(tag.getId()), "tag_value_value"));
                query.groupBy(new Column(tagSQLGenerateService.generateTagTableName(tag.getId()), "tag_value_value"));
            } else {
                query.select(dimensionParseResult.getAstTree());
                query.groupBy(dimensionParseResult.getAstTree());
            }
        }

        for (ParseResult metricParseResult : metricParseResults) {
            query.select(metricParseResult.getAstTree());
        }

        if (index.getIndexType() == IndexType.BASIC) {
            //只有一个表,只用from
            if (CollectionUtils.hasSingleElement(tableNames)) {
                String tableName = tableNames.iterator().next();

                query.from(new Table(tableName));
            } else {
                Join join = null;

                try {
                    join = basicSQLService.buildJoinClauses2(tableNames);

                    result.addDataSourceTables(join.getJoinTables());
                    result.addTableRelationIds(join.getTableRelationIds());
                } catch (UnableToJoinException ex) {
                    return SQLGenerateResult.error(ex.getMessage());
                }

                for (Tag tag : tags) {
                    com.anshishagua.object.Table table = tableService.getById(tag.getTableId());
                    TableColumn primaryKey = table.getPrimaryKeys().get(0);

                    String tagTableName = tagSQLGenerateService.generateTagTableName(tag.getId());
                    String tagTableColumnName = "id";

                    Condition joinCondition = new Equal(new Column(tagTableName, tagTableColumnName),
                            new Column(table.getName(), primaryKey.getName()));

                    join = new Join(join, new Table(tagTableName), JoinType.LEFT_JOIN, joinCondition);
                }

                query.join(join);
            }

            result.addDataSourceTables(tableNames);
        } else {
            String tableName = indexTableNames.iterator().next();

            query.from(new Table(tableName));

            result.addDataSourceTables(indexTableNames);
        }

        result.addSystemParameters(systemParameters);

        result.setTagTables(tags.stream().map(it -> tagSQLGenerateService.generateTagTableName(it.getId())).collect(Collectors.toSet()));

        Insert insert = new Insert(indexTableName, query, true);

        result.addExecuteSQL(insert.toString());

        return result;
    }
}