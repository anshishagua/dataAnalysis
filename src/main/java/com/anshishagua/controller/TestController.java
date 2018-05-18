package com.anshishagua.controller;

import com.anshishagua.mybatis.mapper.TableColumnMapper;
import com.anshishagua.mybatis.mapper.TableMapper;
import com.anshishagua.object.DataType;
import com.anshishagua.object.ParseResult;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.TableColumn;
import com.anshishagua.service.DataTypeService;
import com.anshishagua.service.SystemParameterService;
import com.anshishagua.service.TableRelationService;
import com.anshishagua.service.TableService;
import com.anshishagua.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:29
 */

@RestController
public class TestController {
    @Autowired
    private DataTypeService dataTypeService;
    @Autowired
    private TableColumnMapper tableColumnMapper;
    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private TableService tableService;
    @Autowired
    private TableRelationService tableRelationService;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/test")
    @ResponseBody
    public DataType test() {
        System.out.println(dataTypeService.getTypeById(1));

        TableColumn tableColumn = tableColumnMapper.getById(1);

        System.out.println(tableColumn);

        System.out.println(tableService.getById(1));

        System.out.println(tableRelationService.getById(1));

        System.out.println(systemParameterService.getById(1));

        System.out.println(systemParameterService.getByName("JAVA_HOME"));

        System.out.println(tableRelationService.getByTable("student", "course"));

        System.out.println(tableRelationService.getByTableColumn("student.id", "course.id"));

        return dataTypeService.getTypeById(1);
    }

    @RequestMapping("/parse")
    @ResponseBody
    public ParseResult parse(@RequestParam("expression") String expression,
                             @RequestParam("targetTableId") long targetTableId) {
        return tagService.parseFilterCondition(expression, targetTableId);
    }
}