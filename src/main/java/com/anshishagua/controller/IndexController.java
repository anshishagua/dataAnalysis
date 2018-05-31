package com.anshishagua.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anshishagua.object.DataType;
import com.anshishagua.object.Index;
import com.anshishagua.object.IndexDimension;
import com.anshishagua.object.IndexMetric;
import com.anshishagua.object.IndexType;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.Result;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.service.BasicSQLService;
import com.anshishagua.service.DataTypeService;
import com.anshishagua.service.HiveService;
import com.anshishagua.service.IndexSQLGenerateService;
import com.anshishagua.service.IndexService;
import com.anshishagua.service.MetaDataService;
import com.anshishagua.service.NameValidateService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:06
 */

@Controller
@RequestMapping("/index")
public class IndexController {
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;
    @Autowired
    private IndexSQLGenerateService indexSQLGenerateService;
    @Autowired
    private MetaDataService metaDataService;
    @Autowired
    private NameValidateService nameValidateService;
    @Autowired
    private BasicSQLService basicSQLService;
    @Autowired
    private HiveService hiveService;
    @Autowired
    private DataTypeService dataTypeService;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index/index");

        modelAndView.addObject("indices", indexService.getAll());
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
    public ParseResult parseDimension(@RequestParam("expression") String expression,
                                      @RequestParam("indexType") String indexTypeString) {
        return indexService.parseDimension(expression, IndexType.parseByValue(indexTypeString));
    }

    @RequestMapping("/parseMetric")
    @ResponseBody
    public ParseResult parseMetric(@RequestParam("expression") String expression,
                                   @RequestParam("indexType") String indexTypeString) {
        return indexService.parseMetric(expression, IndexType.parseByValue(indexTypeString));
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
                      @RequestParam("indexType") String indexTypeString,
                      @RequestParam("description") String description,
                      @RequestParam("dimensions") String dimensionsString,
                      @RequestParam("metrics") String metricsString) {
        if (Strings.isNullOrEmpty(indexName)) {
            return Result.error("指标名为空");
        }

        IndexType indexType = IndexType.parseByValue(indexTypeString);

        if (!nameValidateService.isValidIndexName(indexName)) {
            return Result.error(String.format("指标名%s不合法"));
        }

        if (indexService.getByName(indexName) != null) {
            return Result.error(String.format("指标%s已存在", indexName));
        }

        Index index = new Index();
        index.setName(indexName);
        index.setDescription(description);

        index.setIndexType(indexType);
        index.setCreateTime(LocalDateTime.now());
        index.setLastUpdated(LocalDateTime.now());

        List<IndexDimension> indexDimensions = new ArrayList<>();

        JSONArray jsonArray = JSON.parseArray(dimensionsString);

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String dimensionName = jsonObject.getString("name");
            String expression = jsonObject.getString("expression");
            String dimensionDescription = jsonObject.getString("description");

            ParseResult parseResult = indexService.parseDimension(expression, indexType);

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
            DataType dataType = parseResult.getResultType().toDataType();
            dataType = dataTypeService.getTypeByValue(dataType.getValue());

            if (dataType == null) {
                return Result.error("Could not find type:" + dataType.getValue());
            }

            dimension.setTypeId(dataType.getId());

            indexDimensions.add(dimension);
        }

        List<IndexMetric> indexMetrics = new ArrayList<>();

        jsonArray = JSON.parseArray(metricsString);

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String metricName = jsonObject.getString("name");
            String expression = jsonObject.getString("expression");
            String metricDescription = jsonObject.getString("description");

            ParseResult parseResult = indexService.parseMetric(expression, indexType);

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

            DataType dataType = parseResult.getResultType().toDataType();
            dataType = dataTypeService.getTypeByValue(dataType.getValue());

            if (dataType == null) {
                return Result.error("Could not find type:" + dataType.getValue());
            }

            metric.setTypeId(dataType.getId());

            indexMetrics.add(metric);
        }

        index.setDimensions(indexDimensions);
        index.setMetrics(indexMetrics);

        SQLGenerateResult sqlGenerateResult = indexSQLGenerateService.generate(index);

        if (!sqlGenerateResult.isSuccess()) {
            return Result.error(sqlGenerateResult.getErrorMessage());
        }

        indexService.addIndex(index);

        String sql = basicSQLService.createIndexSQL(index);

        try {
            hiveService.execute(sql);
        } catch (SQLException ex) {
            LOG.error("Failed to create index table {}", index.getId(), ex);

            return Result.error("创建指标hive表失败:" + ex.toString());
        }

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

    @RequestMapping("/data")
    public ModelAndView data(@RequestParam("id") long indexId) {
        ModelAndView modelAndView = new ModelAndView("index/data");

        Index index = indexService.getById(indexId);

        modelAndView.addObject("indexId", indexId);
        modelAndView.addObject("indexName", index.getName());

        List<String> tableHeaders = new ArrayList<>();
        tableHeaders.addAll(index.getDimensions().stream().map(dimension -> dimension.getName()).collect(Collectors.toList()));
        tableHeaders.addAll(index.getMetrics().stream().map(metric -> metric.getName()).collect(Collectors.toList()));

        String sql = "SELECT * FROM index_" + indexId;

        List<List<String>> result = new ArrayList<>();

        ResultSet resultSet = null;
        try {
           resultSet = hiveService.executeQuery(sql);

           int columns = tableHeaders.size();

           while (resultSet.next()) {
                List<String> list = new ArrayList<>();

                for (int i = 1; i <= columns; ++i) {
                    list.add(resultSet.getString(i));
                }

                result.add(list);
           }
        } catch (SQLException ex) {
            LOG.error("Failed to execute sql {}", sql, ex);
        }

        modelAndView.addObject("tableHeaders", tableHeaders);
        modelAndView.addObject("result", result);

        return  modelAndView;
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response, @RequestParam("indexId") long indexId) {
        Index index = indexService.getById(indexId);

        String fileName = String.format("index_%d.csv", indexId);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        Charset charset = StandardCharsets.UTF_8;

        if (index == null) {
            try {
                response.getOutputStream().write(String.format("指标%d不存在\n", indexId).getBytes(charset));
                response.getOutputStream().close();
            } catch (IOException ex) {
                LOG.error("", ex);
            }

            return;
        }

        List<String> headers = new ArrayList<>();
        headers.addAll(index.getDimensions().stream().map(dimension -> dimension.getName()).collect(Collectors.toList()));
        headers.addAll(index.getMetrics().stream().map(metric -> metric.getName()).collect(Collectors.toList()));

        String sql = "SELECT * FROM index_" + indexId;

        List<List<String>> result = new ArrayList<>();

        ResultSet resultSet = null;
        try {
            String header = headers.stream().collect(Collectors.joining(",")) + "\n";
            response.getOutputStream().write(header.getBytes(charset));

            resultSet = hiveService.executeQuery(sql);

            int columns = headers.size();

            while (resultSet.next()) {
                List<String> list = new ArrayList<>();

                for (int i = 1; i <= columns; ++i) {
                    list.add(resultSet.getString(i));
                }

                String line = list.stream().collect(Collectors.joining(",")) + "\n";

                response.getOutputStream().write(line.getBytes(charset));
            }

            response.getOutputStream().close();
        } catch (SQLException ex) {
            LOG.error("Failed to execute sql {}", sql, ex);
        } catch (IOException ex) {
            LOG.error("Failed to write to output stream", ex);
        }
    }
}