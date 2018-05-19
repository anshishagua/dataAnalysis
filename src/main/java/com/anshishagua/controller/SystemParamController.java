package com.anshishagua.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anshishagua.object.DataType;
import com.anshishagua.object.Result;
import com.anshishagua.object.SystemParameter;
import com.anshishagua.parser.BasicType;
import com.anshishagua.service.DataTypeService;
import com.anshishagua.service.NameValidateService;
import com.anshishagua.service.SystemParameterService;
import com.anshishagua.utils.TypeUtils;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * User: lixiao
 * Date: 2018/5/19
 * Time: 下午5:32
 */

@Controller
@RequestMapping("/systemParam")
public class SystemParamController {
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private DataTypeService dataTypeService;
    @Autowired
    private NameValidateService nameValidateService;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("parameters", systemParameterService.getAll());
        modelAndView.setViewName("systemParam/index");

        return modelAndView;
    }

    @RequestMapping("/newParam")
    public ModelAndView newParam() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("dataTypes", dataTypeService.getAll());
        modelAndView.setViewName("systemParam/record");

        return modelAndView;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@RequestParam("systemParams") String systemParams) {
        JSONArray jsonArray = JSON.parseArray(systemParams);

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String name = jsonObject.getString("name");
            String value = jsonObject.getString("value");
            long typeId = jsonObject.getLongValue("dataType");
            String description = jsonObject.getString("description");

            if (Strings.isNullOrEmpty(name)) {
                return Result.error("系统参数名字为空");
            }

            if (!nameValidateService.isValidSystemParamName(name)) {
                return Result.error(String.format("系统参数%s名字不合法", name));
            }

            if (Strings.isNullOrEmpty(value)) {
                return Result.error(String.format("系统参数%s值为空", name));
            }

            DataType dataType = dataTypeService.getTypeById(typeId);

            if (dataType == null) {
                return Result.error(String.format("类型%d不存在", typeId));
            }

            BasicType basicType = dataType.toBasicType();

            Result result = TypeUtils.checkValue(value, basicType);

            if (!result.isSuccess()) {
                return result;
            }

            if (systemParameterService.getByName(name) != null) {
                return Result.error(String.format("系统参数%s已存在", name));
            }

            SystemParameter systemParameter = new SystemParameter();
            systemParameter.setName(name);
            systemParameter.setTypeId(typeId);
            systemParameter.setDataType(dataType);
            systemParameter.setCreateTime(LocalDateTime.now());
            systemParameter.setLastUpdated(LocalDateTime.now());
            systemParameter.setDescription(description);
            systemParameter.setValue(value);

            systemParameterService.add(systemParameter);
        }

        return Result.ok();
    }
}