package com.anshishagua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:19
 */

@Service
public class SQLExecuteService {
    @Autowired
    private HiveService hiveService;

    public void execute(List<String> sqls) throws SQLException {
        hiveService.execute(sqls);
    }

    public void execute(String sql) throws SQLException {
        hiveService.execute(sql);
    }
}