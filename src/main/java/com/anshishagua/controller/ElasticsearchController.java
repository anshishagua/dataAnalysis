package com.anshishagua.controller;

import com.anshishagua.service.ElasticsearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/9
 * Time: 下午5:07
 */

@RestController
@RequestMapping("/es")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    static class Person {
        private long id;
        private String name;
        private int age;

        public Person() {

        }

        public Person(long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @RequestMapping("/search")
    @ResponseBody
    public List<String> search() {
        String indexName = "cust_base_info";
        String indexType = "20180424";

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(QueryBuilders.matchPhraseQuery("_tags", "女性客群"));
        queryBuilder.must(QueryBuilders.matchPhraseQuery("_tags", "有车一族"));
        queryBuilder.must(QueryBuilders.matchPhraseQuery("_tags", "豪华车客群"));
        queryBuilder.must(QueryBuilders.matchPhraseQuery("_tags", "90后客群"));
        queryBuilder.must(QueryBuilders.matchPhraseQuery("_tags", "未婚客群"));
        queryBuilder.must(QueryBuilders.matchQuery("name_cn", "白椒"));

        List<SearchHit> searchHits = null;

        try {
            searchHits = elasticsearchService.search(indexName, indexType, 0, 1000, queryBuilder, null);
        } catch (Exception ex) {
            return new ArrayList<>();
        }

        System.out.println("Count:" + searchHits.size());

        List<String> result = new ArrayList<>();

        for (SearchHit searchHit : searchHits) {
            result.add(searchHit.getSourceAsString());
        }

        return result;
    }
}
