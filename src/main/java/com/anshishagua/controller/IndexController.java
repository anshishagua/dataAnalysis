package com.anshishagua.controller;

import com.anshishagua.object.Index;
import com.anshishagua.object.IndexDimension;
import com.anshishagua.object.IndexMetric;
import com.anshishagua.object.IndexType;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.service.IndexSQLGenerateService;
import com.anshishagua.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index/index");

        modelAndView.addObject("name", "benben");

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
}