package com.anshishagua.controller;

import com.anshishagua.constants.SearchType;
import com.anshishagua.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

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
        ModelAndView modelAndView = new ModelAndView();

        SearchType searchType = SearchType.valueOf(searchTypeString);

        switch (searchType) {
            case TAG:

        }

        return modelAndView;
    }
}