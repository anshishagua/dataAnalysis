package com.anshishagua.controller;

import com.anshishagua.object.Index;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.service.IndexSQLGenerateService;
import com.anshishagua.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:06
 */

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private IndexSQLGenerateService indexSQLGenerateService;

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
}