package com.anshishagua.service;

import com.alibaba.fastjson.JSON;
import com.anshishagua.constants.SessionState;
import com.anshishagua.object.BatchResult;
import com.anshishagua.object.DataType;
import com.anshishagua.object.KafkaDataSource;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableColumn;
import com.anshishagua.object.TableField;
import com.anshishagua.object.TableInfo;
import com.anshishagua.object.Tag;
import com.anshishagua.object.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * User: lixiao
 * Date: 2018/7/5
 * Time: 下午5:11
 */

@Service
public class StreamTaskService {
    private static final Logger LOG = LoggerFactory.getLogger(StreamTaskService.class);

    @Autowired
    private KafkaDataSourceService kafkaDataSourceService;
    @Autowired
    private TaskExecutionService taskExecutionService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TableService tableService;
    @Autowired
    private DataTypeService dataTypeService;

    @Value("${livy.rest.url}")
    private String livyRestUrl;

    @Value("${spark.streaming.job.jar.path}")
    private String jarFilePath;

    @Value("${spark.streaming.job.main.class.name}")
    private String mainClassName;

    public void stop(long tagId) {

    }

    private String genAppName(long tagId) {
        return "app_spark_streaming_tag_" + tagId;
    }

    public TaskResult start(long tagId) {
        Tag tag = tagService.getById(tagId);

        if (tag == null) {
            return TaskResult.error("Tag " + tagId + " not found");
        }

        RestTemplate restTemplate = new RestTemplateBuilder().build();

        String url = livyRestUrl + "/batches/";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        KafkaDataSource kafkaDataSource = kafkaDataSourceService.getByTableId(tag.getTableId());
        SQLGenerateResult sqlGenerateResult = tag.getSqlGenerateResult();
        Table table = tableService.getById(tag.getTableId());

        List<String> sqls = sqlGenerateResult.getExecuteSQLs();

        TableInfo tableInfo = new TableInfo();
        tableInfo.setName(table.getName());

        List<TableField> tableFields = new ArrayList<>(table.getColumns().size());

        for (TableColumn tableColumn : table.getColumns()) {
            DataType dataType = dataTypeService.getTypeById(tableColumn.getTypeId());
            TableField tableField = new TableField(tableColumn.getName(), dataType.getValue());

            tableFields.add(tableField);
        }

        tableInfo.setFields(tableFields);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("kafkaDataSource", kafkaDataSource);
        paramMap.put("tableInfo", tableInfo);
        paramMap.put("sqls", sqls);
        paramMap.put("appName", genAppName(tagId));

        BatchResult batchResult = new BatchResult();

        Map<String, Object> map = new HashMap<>();
        map.put("file", jarFilePath);
        map.put("className", mainClassName);
        map.put("args", Arrays.asList(JSON.toJSONString(paramMap)));

        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(map), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        HttpStatus httpStatus = response.getStatusCode();

        if (!httpStatus.is2xxSuccessful()) {
            LOG.error("Failed to start job", response.getBody());

            return TaskResult.error(response.getBody());
        }

        batchResult = JSON.parseObject(response.getBody(), BatchResult.class);

        while (batchResult.getSessionState() == SessionState.STARTING && batchResult.getAppId() == null) {
            url = url + batchResult.getId();

            response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {

                }
            } else {
                batchResult = JSON.parseObject(response.getBody(), BatchResult.class);
            }
        }

        TaskResult taskResult = new TaskResult();
        taskResult.setProperty("batchResult", batchResult);

        return taskResult;
    }
}