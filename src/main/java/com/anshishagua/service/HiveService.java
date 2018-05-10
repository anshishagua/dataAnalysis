package com.anshishagua.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:40
 */

@Service
public class HiveService {
    private static final Logger LOG = LoggerFactory.getLogger(HiveService.class);

    @Value("${hive.dataSource.driverClassName}")
    private String driverClassName;

    @Value("${hive.dataSource.url}")
    private String jdbcUrl;

    @Value("${hive.dataSource.username}")
    private String username;

    @Value("${hive.dataSource.password}")
    private String password;

    private DataSource hiveDataSource;

    private void initDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        HikariDataSource dataSource = new HikariDataSource(config);
        dataSource.setReadOnly(false);

        hiveDataSource = dataSource;
    }

    public void execute(String sql) throws SQLException {
        if (hiveDataSource == null) {
            initDataSource();
        }

        Connection connection = hiveDataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);

        LOG.info("Execute {}", sql);

        statement.execute(sql);
    }

    public void execute(List<String> sqls) throws SQLException {
        if (hiveDataSource == null) {
            initDataSource();
        }

        Connection connection = hiveDataSource.getConnection();

        for (String sql : sqls) {
            PreparedStatement statement = connection.prepareStatement(sql);

            LOG.info("Execute {}", sql);

            statement.execute(sql);
        }
    }
}