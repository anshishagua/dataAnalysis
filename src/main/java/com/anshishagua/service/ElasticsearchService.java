package com.anshishagua.service;

import com.alibaba.fastjson.JSON;
import com.anshishagua.object.IndexTypeMappings;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 上午10:02
 */

@Service
public class ElasticsearchService {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchService.class);

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    @Value("${elasticsearch.cluster.servers}")
    private String servers;

    @Value("${elasticsearch.cluster.server.port}")
    private int port;

    private TransportClient transportClient;

    //@PostConstruct
    private void init() {
        Settings settings = Settings.builder().put("cluster.name", clusterName)
                .put("transport.type","netty3")
                .put("http.type", "netty3")
                .build();

        transportClient = new PreBuiltTransportClient(settings);

        try {
            for (String server : servers.split(",")) {
                transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(server.trim()), port));
            }
        } catch (UnknownHostException ex) {
            LOG.error("Failed to start init elasticsearch transport client", ex);

            throw new RuntimeException(ex);
        }
    }

    public void createIndex(String indexName) {
        CreateIndexResponse response = transportClient.admin().indices().prepareCreate(indexName).get();

        if (!response.isAcknowledged()) {
            LOG.error("Failed to create index {}", indexName);
        }
    }

    public boolean createMapping(IndexTypeMappings indexTypeMappings) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject().startObject(indexTypeMappings.getIndexType());
        builder.startObject("properties");

        for (IndexTypeMappings.IndexTypeMapping indexTypeMapping : indexTypeMappings.getMappings()) {
            builder.startObject(indexTypeMapping.getName());

            builder.field(IndexTypeMappings.PROPERTY_FIELD_TYPE, indexTypeMapping.getType().toString().toLowerCase());
            builder.field(IndexTypeMappings.PROPERTY_FIELD_STORE, indexTypeMapping.isStore() ? "yes" : "no");
            builder.field(IndexTypeMappings.PROPERTY_FIELD_INDEX, indexTypeMapping.getAnalyzer());

            builder.endObject();
        }

        builder.endObject().endObject().endObject();

        PutMappingRequest request = Requests.putMappingRequest(indexTypeMappings.getIndexName()).type(indexTypeMappings.getIndexType()).source(builder);
        PutMappingResponse response = null;
        try {
            response = transportClient.admin().indices().putMapping(request).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response.isAcknowledged();
    }

    public boolean index(String indexName, String indexType, Map<String, ?> map) {
        Objects.requireNonNull(map);

        IndexResponse indexResponse = transportClient.prepareIndex(indexName, indexType).setSource(map).get();

        return indexResponse.status() == RestStatus.OK;
    }

    public boolean index(String indexName, String indexType, List<?> list) {
        Objects.requireNonNull(list);

        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();

        for (Object entity : list) {
            bulkRequest.add(transportClient.prepareIndex(indexName, indexType).setSource(JSON.toJSONString(entity)));
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();

        if (bulkResponse.hasFailures()) {
            LOG.warn("Failed to bulk insert:", bulkResponse.buildFailureMessage());
        }

        return !bulkResponse.hasFailures();
    }

    public List<SearchHit> search(String indexName, String indexType, QueryBuilder queryBuilder) throws Exception {
        return search(indexName, indexType, 0, Integer.MAX_VALUE, queryBuilder, null);
    }

    public List<SearchHit> search(String indexName, String indexType, int from, int size, QueryBuilder queryBuilder, SortBuilder sortBuilder) throws Exception {
        SearchRequestBuilder requestBuilder = transportClient.prepareSearch(indexName).setTypes(indexType).setFrom(from).setSize(size);

        if (queryBuilder != null) {
            requestBuilder.setQuery(queryBuilder);
        }

        if (sortBuilder != null) {
            requestBuilder.addSort(sortBuilder);
        }

        List<SearchHit> result = new ArrayList<>();

        SearchResponse response = requestBuilder.execute().get();

        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            result.add(hit);
        }

        return result;
    }

    public <T> List<T> toList(List<SearchHit> searchHits, Class<T> clazz) {
        List<T> result = new ArrayList<>();

        for (SearchHit searchHit : searchHits) {
            T object = JSON.parseObject(searchHit.getSourceAsString(), clazz);

            result.add(object);
        }

        return result;
    }
}