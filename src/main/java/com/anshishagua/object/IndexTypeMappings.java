package com.anshishagua.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/9
 * Time: 上午9:52
 */

public class IndexTypeMappings {
    public static final String PROPERTY_FIELD_NAME = "name";
    public static final String PROPERTY_FIELD_TYPE = "type";
    public static final String PROPERTY_FIELD_INDEX = "index";
    public static final String PROPERTY_FIELD_STORE = "store";

    private String indexName;
    private String indexType;

    public IndexTypeMappings(String indexName, String indexType) {
        Objects.requireNonNull(indexName);
        Objects.requireNonNull(indexType);

        this.indexName = indexName;
        this.indexType = indexType;
    }

    private List<IndexTypeMapping> mappings = new ArrayList<>();

    public String getIndexName() {
        return indexName;
    }

    public String getIndexType() {
        return indexType;
    }

    public static class IndexTypeMapping {
        private String name;
        private DataType type;
        private boolean store;
        private String analyzer;

        public IndexTypeMapping(String name, DataType type, boolean store, String analyzer) {
            Objects.requireNonNull(name);
            Objects.requireNonNull(type);
            Objects.requireNonNull(analyzer);

            this.name = name;
            this.type = type;
            this.store = store;
            this.analyzer = analyzer;
        }

        public String getName() {
            return name;
        }

        public DataType getType() {
            return type;
        }

        public boolean isStore() {
            return store;
        }

        public String getAnalyzer() {
            return analyzer;
        }
    }

    public void addIndexTypeMapping(String name, DataType type, boolean store, String analyzer) {
        mappings.add(new IndexTypeMapping(name, type, store, analyzer));
    }

    public List<IndexTypeMapping> getMappings() {
        return mappings;
    }
}