package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.SystemParameterMapper;
import com.anshishagua.object.SystemParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午5:29
 */

@Service
public class SystemParameterService {
    @Autowired
    private SystemParameterMapper systemParameterMapper;
    @Autowired
    private DataTypeService dataTypeService;

    public SystemParameter getById(long id) {
        SystemParameter systemParameter = systemParameterMapper.getById(id);

        if (systemParameter != null) {
            systemParameter.setDataType(dataTypeService.getTypeById(systemParameter.getTypeId()));
        }

        return systemParameter;
    }

    public SystemParameter getByName(String name) {
        SystemParameter systemParameter = systemParameterMapper.getByName(name);

        if (systemParameter != null) {
            systemParameter.setDataType(dataTypeService.getTypeById(systemParameter.getTypeId()));
        }

        return systemParameter;
    }

    public List<SystemParameter> getAll() {
        List<SystemParameter> result = systemParameterMapper.list();

        for (SystemParameter systemParameter : result) {
            systemParameter.setDataType(dataTypeService.getTypeById(systemParameter.getTypeId()));
        }

        return result;
    }

    public void add(SystemParameter systemParameter) {
        Objects.requireNonNull(systemParameter);

        systemParameterMapper.insert(systemParameter);
    }
}