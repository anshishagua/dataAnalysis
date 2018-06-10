package com.anshishagua.controller;

import com.anshishagua.object.Result;
import com.anshishagua.object.Table;
import com.anshishagua.object.TableRelation;
import com.anshishagua.parser.nodes.sql.JoinType;
import com.anshishagua.service.TableColumnService;
import com.anshishagua.service.TableRelationService;
import com.anshishagua.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/13
 * Time: 下午1:56
 */

@Controller
@RequestMapping("/tableRelation")
public class TableRelationController {
    @Autowired
    private TableService tableService;
    @Autowired
    private TableRelationService tableRelationService;
    @Autowired
    private TableColumnService tableColumnService;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        List<Table> tables = tableService.getAllTables();

        List<JoinType> joinTypes = Arrays.asList(JoinType.values());

        modelAndView.addObject("tables", tables);
        modelAndView.addObject("joinTypes", joinTypes);

        modelAndView.setViewName("tableRelation/index");

        return modelAndView;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@RequestParam("leftTable") long leftTableId,
                      @RequestParam("rightTable") long rightTableId,
                      @RequestParam("leftColumn") long leftColumnId,
                      @RequestParam("rightColumn") long rightColumnId,
                      @RequestParam("joinType") String joinTypeString,
                      @RequestParam("description") String description) {
        TableRelation tableRelation = new TableRelation();
        tableRelation.setLeftColumnId(leftColumnId);
        tableRelation.setRightColumnId(rightColumnId);
        tableRelation.setJoinType(JoinType.parse(joinTypeString));
        tableRelation.setDescription(description);
        tableRelation.setCreateTime(LocalDateTime.now());
        tableRelation.setLastUpdated(LocalDateTime.now());

        tableRelationService.addRelation(tableRelation);

        return Result.ok();
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();

        List<TableRelation> relations = tableRelationService.getAllRelations();

        modelAndView.addObject("relations", relations);
        modelAndView.setViewName("tableRelation/list");

        return modelAndView;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("id") long id) {
        TableRelation relation = tableRelationService.getById(id);

        ModelAndView modelAndView = new ModelAndView("tableRelation/detail");

        modelAndView.addObject("relation", relation);

        return modelAndView;
    }
}
