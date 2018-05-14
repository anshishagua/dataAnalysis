package com.anshishagua.controller;

import com.anshishagua.object.ParseResult;
import com.anshishagua.object.Result;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Table;
import com.anshishagua.object.Tag;
import com.anshishagua.service.SQLExecuteService;
import com.anshishagua.service.TableService;
import com.anshishagua.service.TagSQLGenerateService;
import com.anshishagua.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/8
 * Time: 下午4:38
 */

@RestController
@RequestMapping("/tag")
public class TagController {
    private static final Logger LOG = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagSQLGenerateService sqlGenerateService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SQLExecuteService sqlExecuteService;
    @Autowired
    private TableService tableService;

    @RequestMapping("/generate")
    @ResponseBody
    public SQLGenerateResult generate(@RequestParam("id") long id) {
        Tag tag = tagService.getById(id);

        if (tag == null) {
            return SQLGenerateResult.error("Tag " + id + " not found");
        }

        SQLGenerateResult result = sqlGenerateService.generate(tag);

        if (result.isSuccess()) {
            try {
                sqlExecuteService.execute(result.getExecuteSQLs());
            } catch (SQLException ex) {
                LOG.error("Failed to execute", ex);
            }
        }

        return result;
    }

    @RequestMapping("/check")
    @ResponseBody
    public Result check(@RequestParam("tagName") String tagName) {
        Tag tag = tagService.getByName(tagName);

        if (tag != null) {
            return new Result(false, String.format("标签%s已存在", tagName));
        }

        return Result.ok();
    }

    @RequestMapping("/insert")
    public void insert() {
        Tag tag = new Tag();

        tag.setCreateTime(LocalDateTime.now());
        tag.setLastUpdated(LocalDateTime.now());
        tag.setDescription("sssss");
        tag.setTableId(2);
        tag.setName("测试标签");
        tag.setFilterCondition("student.name > 'A' AND student.name < 'B'");
        tag.setComputeCondition("student.age > 10 AND student.age < 30 AND count(course.name) > 30");

        SQLGenerateResult result = sqlGenerateService.generate(tag);

        tag.setSqlGenerateResult(result);

        tagService.addTag(tag);
    }

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        List<Table> tables = tableService.getAllTables();

        modelAndView.addObject("tables", tables);
        modelAndView.setViewName("tag/index");

        return modelAndView;
    }

    @RequestMapping("/add")
    @ResponseBody
    Result add(@RequestParam("tagName") String tagName,
               @RequestParam("description") String description,
               @RequestParam("targetTableId") long targetTableId,
               @RequestParam("filterCondition") String filterCondition,
               @RequestParam("computeCondition") String computeCondition) {

        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setDescription(description);

        Table table = tableService.getById(targetTableId);

        if (table == null) {
            return Result.error(String.format("贴标表id:%d不存在", targetTableId));
        }

        tag.setTableId(targetTableId);

        ParseResult parseResult = tagService.parseFilterCondition(filterCondition);

        if (!parseResult.isSuccess()) {
            return Result.error(String.format("过滤条件%s错误:%s", filterCondition, parseResult.getErrorMessage()));
        }

        parseResult = tagService.parseComputeCondition(computeCondition);

        if (!parseResult.isSuccess()) {
            return Result.error(String.format("规则条件%s错误:%s", computeCondition, parseResult.getErrorMessage()));
        }

        tag.setFilterCondition(filterCondition);
        tag.setComputeCondition(computeCondition);
        tag.setCreateTime(LocalDateTime.now());
        tag.setLastUpdated(LocalDateTime.now());

        SQLGenerateResult result = sqlGenerateService.generate(tag);

        if (!result.isSuccess()) {
            return Result.error(String.format("标签生成SQL失败:%s", result.getErrorMessage()));
        }

        tag.setSqlGenerateResult(result);

        tagService.addTag(tag);

        tag.setSqlGenerateResult(sqlGenerateService.generate(tag));

        tagService.updateSQLGenerateResult(tag);

        return Result.ok();
    }
}
