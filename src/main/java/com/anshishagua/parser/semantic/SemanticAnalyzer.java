package com.anshishagua.parser.semantic;

import com.anshishagua.object.Index;
import com.anshishagua.object.IndexDimension;
import com.anshishagua.object.IndexMetric;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.ParseResult.ParseType;
import com.anshishagua.parser.nodes.function.FunctionRegistry;
import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.object.SystemParameter;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.FunctionNode;
import com.anshishagua.parser.nodes.function.MultiFunctionNode;
import com.anshishagua.parser.nodes.function.aggregation.AggregationNode;
import com.anshishagua.parser.nodes.sql.Column;
import com.anshishagua.parser.nodes.primitive.Variable;
import com.anshishagua.service.IndexService;
import com.anshishagua.service.SystemParameterService;
import com.anshishagua.service.TableService;
import com.anshishagua.service.TagService;
import com.anshishagua.utils.CollectionUtils;
import com.anshishagua.utils.TreeNodeWalker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午3:29
 */

public class SemanticAnalyzer {
    private Node astTree;
    private SystemParameterService systemParameterService;
    private TableService tableService;
    private IndexService indexService;
    private TagService tagService;
    private ParseType parseType;
    private Set<Table> tables = new HashSet<>();
    private Set<TableColumn> columns = new HashSet<>();
    private Set<Index> indices = new HashSet<>();
    private Set<IndexDimension> indexDimensions = new HashSet<>();
    private Set<IndexMetric> indexMetrics = new HashSet<>();
    private Set<SystemParameter> systemParameters = new HashSet<>();
    private List<Node> aggregationNodes = new ArrayList<>();

    public SemanticAnalyzer(Node astTree, ParseType parseType) {
        Objects.requireNonNull(astTree);
        Objects.requireNonNull(parseType);

        this.astTree = astTree;
        this.parseType = parseType;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }

    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }

    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public Set<Table> getTables() {
        return Collections.unmodifiableSet(tables);
    }

    public Set<Index> getIndices() {
        return Collections.unmodifiableSet(indices);
    }

    public Set<IndexDimension> getIndexDimensions() {
        return Collections.unmodifiableSet(indexDimensions);
    }

    public Set<IndexMetric> getIndexMetrics() {
        return Collections.unmodifiableSet(indexMetrics);
    }

    public Set<TableColumn> getColumns() {
        return Collections.unmodifiableSet(columns);
    }

    public Set<SystemParameter> getSystemParameters() {
        return Collections.unmodifiableSet(systemParameters);
    }

    public List<Node> getAggregationNodes() {
        return Collections.unmodifiableList(aggregationNodes);
    }

    private void analyzeVariable() throws SemanticException {
        Consumer<Node> consumer = (Node node) -> {
            if (node instanceof Variable) {
                String varName = ((Variable) node).getVarName();

                SystemParameter systemParameter = systemParameterService.getByName(varName);

                if (systemParameter == null) {
                    throw new SemanticException("Variable " + varName + " not found");
                }

                BasicType basicType = systemParameter.getDataType().toBasicType();

                if (basicType == null) {
                    throw new SemanticException("Variable " + varName + " not found");
                }

                systemParameters.add(systemParameter);

                node.setResultType(basicType);
            }
        };

        TreeNodeWalker.preOrder(astTree, consumer);
    }

    private void analyzeIndexDimension() throws SemanticException {
        Consumer<Node> consumer = (Node node) -> {
            if (node instanceof Column) {
                String indexName = ((Column) node).getTableName();
                String dimensionName = ((Column) node).getColumnName();

                Index index = indexService.getByName(indexName);

                if (index == null) {
                    throw new SemanticException("Index " + indexName + " not found");
                }

                List<IndexDimension> dimensions = index.getDimensions();

                List<IndexDimension> result  = dimensions.stream().filter(it -> it.getName().equals(dimensionName)).collect(Collectors.toList());

                IndexDimension dimension = CollectionUtils.hasSingleElement(result) ? result.get(0) : null;

                if (dimension == null) {
                    throw new SemanticException(String.format("Dimension %s.%s not found", indexName, dimensionName));
                }

                ((Column) node).setTableName("index_" + index.getId());
                ((Column) node).setColumnName("dimension_" + dimension.getId());

                indices.add(index);
                indexDimensions.add(dimension);

                node.setResultType(dimension.getDataType());
            }
        };

        TreeNodeWalker.preOrder(astTree, consumer);
    }

    private void analyzeIndexMetric() throws SemanticException {
        Consumer<Node> consumer = (Node node) -> {
            if (node instanceof Column) {
                String indexName = ((Column) node).getTableName();
                String metricName = ((Column) node).getColumnName();

                Index index = indexService.getByName(indexName);

                if (index == null) {
                    throw new SemanticException("Index " + indexName + " not found");
                }

                List<IndexMetric> metrics = index.getMetrics();

                List<IndexMetric> result  = metrics.stream().filter(it -> it.getName().equals(metricName)).collect(Collectors.toList());

                IndexMetric metric = CollectionUtils.hasSingleElement(result) ? result.get(0) : null;

                if (metric == null) {
                    throw new SemanticException(String.format("Metric %s.%s not found", indexName, metricName));
                }

                ((Column) node).setTableName("index_" + index.getId());
                ((Column) node).setColumnName("metric_" + metric.getId());

                indices.add(index);
                indexMetrics.add(metric);

                node.setResultType(metric.getDataType());
            }
        };

        TreeNodeWalker.preOrder(astTree, consumer);
    }

    private void analyzeTableColumn() throws SemanticException {
        Consumer<Node> consumer = (Node node) -> {
            if (node instanceof Column) {
                String tableName = ((Column) node).getTableName();
                String columnName = ((Column) node).getColumnName();

                Table table = tableService.getByName(tableName);

                if (table == null) {
                    throw new SemanticException("Table " + tableName + " not found");
                }

                TableColumn column = table.getColumn(columnName);

                if (column == null) {
                    throw new SemanticException(String.format("Column %s.%s not found", tableName, columnName));
                }

                tables.add(table);
                columns.add(column);

                BasicType basicType = column.getDataType().toBasicType();

                if (basicType == null) {
                    throw new SemanticException("Column " + tableName + "." + columnName + " not found");
                }

                node.setResultType(basicType);
            }
        };

        TreeNodeWalker.preOrder(astTree, consumer);
    }

    private void analyzeFunction() throws SemanticException {
        Consumer<Node> consumer = (Node node) -> {
            if (node instanceof FunctionNode) {
                FunctionNode functionNode = (FunctionNode) node;
                String functionName = functionNode.getName();

                if (!FunctionRegistry.contains(functionName)) {
                    throw new SemanticException("Function " + functionName + " not found");
                }

                Node newNode = FunctionRegistry.createNode(functionName, node.getChildren());
                Node parent = node.getParent();

                if (parent != null) {
                    newNode.setParent(node.getParent());
                    node.getParent().setChild(node.getIndex(), newNode);
                } else {
                    astTree = newNode;
                }

                node = newNode;
                int args = ((FunctionNode) node).requiredArgumentSize();

                if (!(node instanceof MultiFunctionNode) && (node.getChildren().size() != args)) {
                    throw new SemanticException(String.format("Function %s should have %d argument%s, actual: %d", functionName, args, args > 1 ? "s" : "", node.getChildren().size()));
                }
            }

            if (node instanceof AggregationNode) {
                aggregationNodes.add(node);
            }
        };

        TreeNodeWalker.leafToRootOrder(astTree, consumer);
    }

    private void analyzeType2(Node root) throws SemanticException {
        if (root == null) {
            return;
        }

        if (root.getResultType() != null) {
            return;
        }

        for (Node child : root.getChildren()) {
            TypeResolverRegistry.resolve(child);
        }

        TypeResolverRegistry.resolve(root);
    }

    private void analyzeType() throws SemanticException {
        Consumer<Node> consumer = (Node node) -> {
            if (node.getResultType() != null) {
                return;
            }

            TypeResolverRegistry.resolve(node);
        };

        TreeNodeWalker.leafToRootOrder(astTree, consumer);
    }

    public void analyze() throws SemanticException {
        analyzeVariable();

        if (parseType == ParseType.DERIVED_INDEX_DIMENSION) {
            analyzeIndexDimension();
        } else if (parseType == ParseType.DERIVED_INDEX_METRIC) {
            analyzeIndexMetric();
        } else {
            analyzeTableColumn();
        }
        analyzeFunction();
        analyzeType();
    }

    public Node getAstTree() {
        return astTree;
    }
}