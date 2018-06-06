package com.anshishagua.controller;

import com.anshishagua.constants.SearchType;
import com.anshishagua.object.Index;
import com.anshishagua.object.Table;
import com.anshishagua.object.Tag;
import com.anshishagua.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/6/1
 * Time: 上午11:56
 */

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("searchTypes", Arrays.asList(SearchType.values()));

        return "search/index";
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam("searchType") String searchTypeString,
                               @RequestParam("query") String query) {
        ModelAndView modelAndView = new ModelAndView("search/result");

        SearchType searchType = SearchType.valueOf(searchTypeString);

        List<Table> tables = new ArrayList<>();
        List<Index> indices = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        switch (searchType) {
            case TAG:
                tags = searchService.searchByTag(query);
                break;
            case INDEX:
                indices = searchService.searchByIndex(query);
                break;
            case TABLE:
                tables = searchService.searchByTable(query);
                break;
            default:
                tags = searchService.searchByTag(query);
                indices = searchService.searchByIndex(query);
                tables = searchService.searchByTable(query);
                break;
        }

        modelAndView.addObject("tables", tables);
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("indices", indices);
        modelAndView.addObject("searchType", searchType);

        return modelAndView;
    }
}