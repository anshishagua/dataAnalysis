package com.anshishagua.controller;

import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Tag;
import com.anshishagua.service.TagSQLGenerateService;
import com.anshishagua.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: lixiao
 * Date: 2018/5/8
 * Time: 下午4:38
 */

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagSQLGenerateService sqlGenerateService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/generate")
    @ResponseBody
    public SQLGenerateResult generate(@RequestParam("id") long id) {
        Tag tag = tagService.getById(id);

        if (tag == null) {
            return SQLGenerateResult.error("Tag " + id + " not found");
        }

        SQLGenerateResult result = sqlGenerateService.generate(tag);

        return result;
    }
}
