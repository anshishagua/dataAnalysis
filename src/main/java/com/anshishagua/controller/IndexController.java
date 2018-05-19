package com.anshishagua.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anshishagua.object.Index;
import com.anshishagua.object.IndexDimension;
import com.anshishagua.object.IndexMetric;
import com.anshishagua.object.IndexType;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.Result;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.service.IndexSQLGenerateService;
import com.anshishagua.service.IndexService;
import com.anshishagua.service.MetaDataService;
import com.anshishagua.service.NameValidateService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:06
 */

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private IndexSQLGenerateService indexSQLGenerateService;
    @Autowired
    private MetaDataService metaDataService;
    @Autowired
    private NameValidateService nameValidateService;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index/index");

        modelAndView.addObject("bools", metaDataService.getBoolOperators());
        modelAndView.addObject("compares", metaDataService.getCompareOperators());
        modelAndView.addObject("operators", metaDataService.getOperators());
        modelAndView.addObject("functions", metaDataService.getFunctionNames());
        modelAndView.addObject("tableColumns", metaDataService.getTableColumns());

        return modelAndView;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Index getById(@RequestParam("id") long id) {
        return indexService.getById(id);
    }

    @RequestMapping("/parseDimension")
    @ResponseBody
    public ParseResult parseDimension(@RequestParam("expression") String expression) {
        return indexService.parseDimension(expression);
    }

    @RequestMapping("/parseMetric")
    @ResponseBody
    public ParseResult parseMetric(@RequestParam("expression") String expression) {
        return indexService.parseMetric(expression);
    }

    @RequestMapping("/generate")
    @ResponseBody
    public SQLGenerateResult generate(@RequestParam("id") long id) {
        Index index = indexService.getById(id);

        if (index == null) {
            return SQLGenerateResult.error("Index " + id + " not found");
        }

        return indexSQLGenerateService.generate(index);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@RequestParam("indexName") String indexName,
                      @RequestParam("indexType") String indexType,
                      @RequestParam("description") String description,
                      @RequestParam("dimensions") String dimensionsString,
                      @RequestParam("metrics") String metricsString) {
        if (Strings.isNullOrEmpty(indexName)) {
            return Result.error("指标名为空");
        }

        if (!nameValidateService.isValidIndexName(indexName)) {
            return Result.error(String.format("指标名%s不合法"));
        }

        if (indexService.getByName(indexName) != null) {
            return Result.error(String.format("指标%s已存在", indexName));
        }

        Index index = new Index();
        index.setName(indexName);
        index.setDescription(description);
        index.setIndexType(IndexType.parseByValue(indexType));
        index.setCreateTime(LocalDateTime.now());
        index.setLastUpdated(LocalDateTime.now());

        List<IndexDimension> indexDimensions = new ArrayList<>();

        JSONArray jsonArray = JSON.parseArray(dimensionsString);

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String dimensionName = jsonObject.getString("name");
            String expression = jsonObject.getString("expression");
            String dimensionDescription = jsonObject.getString("description");

            ParseResult parseResult = indexService.parseDimension(expression);

            if (!parseResult.isSuccess()) {
                return Result.error(parseResult.getErrorMessage());
            }

            IndexDimension dimension = new IndexDimension();
            dimension.setName(dimensionName);
            dimension.setExpression(expression);
            dimension.setDescription(dimensionDescription);
            dimension.setOrder(i);
            dimension.setDataType(parseResult.getResultType());
            dimension.setCreateTime(LocalDateTime.now());
            dimension.setLastUpdated(LocalDateTime.now());

            indexDimensions.add(dimension);
        }

        List<IndexMetric> indexMetrics = new ArrayList<>();

        jsonArray = JSON.parseArray(metricsString);

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String metricName = jsonObject.getString("name");
            String expression = jsonObject.getString("expression");
            String metricDescription = jsonObject.getString("description");

            ParseResult parseResult = indexService.parseMetric(expression);

            if (!parseResult.isSuccess()) {
                return Result.error(parseResult.getErrorMessage());
            }

            IndexMetric metric = new IndexMetric();
            metric.setName(metricName);
            metric.setExpression(expression);
            metric.setDescription(metricDescription);
            metric.setOrder(i);
            metric.setDataType(parseResult.getResultType());
            metric.setCreateTime(LocalDateTime.now());
            metric.setLastUpdated(LocalDateTime.now());

            indexMetrics.add(metric);
        }

        index.setDimensions(indexDimensions);
        index.setMetrics(indexMetrics);

        SQLGenerateResult sqlGenerateResult = indexSQLGenerateService.generate(index);

        if (!sqlGenerateResult.isSuccess()) {
            return Result.error(sqlGenerateResult.getErrorMessage());
        }

        indexService.addIndex(index);

        return Result.ok();
    }

    @RequestMapping("/insert")
    public void insert() {
        Index index = new Index();

        index.setIndexType(IndexType.BASIC);
        index.setDescription("test index");
        index.setName("测试指标");
        index.setCreateTime(LocalDateTime.now());
        index.setLastUpdated(LocalDateTime.now());

        List<IndexDimension> dimensions = new ArrayList<>();

        IndexDimension dimension = new IndexDimension();

        dimension.setName("student_id");
        dimension.setOrder(1);
        dimension.setCreateTime(LocalDateTime.now());
        dimension.setLastUpdated(LocalDateTime.now());
        dimension.setExpression("student.id");

        dimensions.add(dimension);

        List<IndexMetric> metrics = new ArrayList<>();

        IndexMetric metric = new IndexMetric();

        metric.setName("countaaaa");
        metric.setCreateTime(LocalDateTime.now());
        metric.setLastUpdated(LocalDateTime.now());
        metric.setExpression("count(course.id)");
        metric.setOrder(1);

        metrics.add(metric);

        index.setDimensions(dimensions);
        index.setMetrics(metrics);

        indexService.addIndex(index);
    }

    @RequestMapping("/dimension")
    public ModelAndView dimension() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index/dimension");

        return modelAndView;
    }

    @RequestMapping("/metric")
    public ModelAndView metric() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index/metric");

        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();

        List<Index> indices = indexService.getAll();

        modelAndView.addObject("indices", indices);
        modelAndView.setViewName("index/list");

        return modelAndView;
    }
}