package com.anshishagua.object;

import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午4:59
 */

public class ParseResult {
    public static enum ParseType {
        TAG_FILTER_CONDITION,
        TAG_COMPUTE_CONDITION,
        BASE_INDEX_DIMENSION,
        BASE_INDEX_METRIC,
        DERIVED_INDEX_DIMENSION,
        DERIVED_INDEX_METRIC,
        UNKNOWN
    }

    private String originalExpression;
    private boolean success;
    private String errorMessage;
    private Exception exception;
    @JsonIgnore
    private Node astTree;
    private BasicType resultType;
    private Set<TableColumn> columns = new HashSet<>();
    private Set<Table> tables = new HashSet<>();
    private Set<Index> indices = new HashSet<>();
    private Set<IndexDimension> indexDimensions = new HashSet<>();
    private Set<IndexMetric> indexMetrics = new HashSet<>();
    private Set<SystemParameter> systemParameters = new HashSet<>();
    private ParseType parseType = ParseType.UNKNOWN;
    @JsonIgnore
    private List<Node> aggregationNodes = new ArrayList<>();

    public static ParseResult ok(ParseType parseType, String originalExpression) {
        Objects.requireNonNull(originalExpression);
        Objects.requireNonNull(parseType);

        ParseResult result = new ParseResult();
        result.originalExpression = originalExpression;
        result.parseType = parseType;
        result.success = true;
        result.errorMessage = "";

        return result;
    }

    public static ParseResult error(ParseType parseType, String originalExpression, String errorMessage) {
        Objects.requireNonNull(originalExpression);
        Objects.requireNonNull(parseType);
        Objects.requireNonNull(errorMessage);

        ParseResult result = new ParseResult();
        result.originalExpression = originalExpression;
        result.errorMessage = errorMessage;
        result.success = false;
        result.parseType = parseType;

        return result;
    }

    public ParseResult() {

    }

    public String getOriginalExpression() {
        return originalExpression;
    }

    public void setOriginalExpression(String originalExpression) {
        this.originalExpression = originalExpression;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Node getAstTree() {
        return astTree;
    }

    public void setAstTree(Node astTree) {
        this.astTree = astTree;
    }

    public BasicType getResultType() {
        return resultType;
    }

    public void setResultType(BasicType resultType) {
        this.resultType = resultType;
    }

    public Set<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(Set<TableColumn> columns) {
        this.columns = columns;
    }

    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }

    public Set<Index> getIndices() {
        return indices;
    }

    public void setIndices(Set<Index> indices) {
        this.indices = indices;
    }

    public Set<IndexDimension> getIndexDimensions() {
        return indexDimensions;
    }

    public void setIndexDimensions(Set<IndexDimension> indexDimensions) {
        this.indexDimensions = indexDimensions;
    }

    public Set<IndexMetric> getIndexMetrics() {
        return indexMetrics;
    }

    public void setIndexMetrics(Set<IndexMetric> indexMetrics) {
        this.indexMetrics = indexMetrics;
    }

    public Set<SystemParameter> getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(Set<SystemParameter> systemParameters) {
        this.systemParameters = systemParameters;
    }

    public ParseType getParseType() {
        return parseType;
    }

    public void setParseType(ParseType parseType) {
        this.parseType = parseType;
    }

    public void setAggregationNodes(List<Node> aggregationNodes) {
        this.aggregationNodes = aggregationNodes;
    }

    public List<Node> getAggregationNodes() {
        return aggregationNodes;
    }
}