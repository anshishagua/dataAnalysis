package com.anshishagua.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:38
 */

@Configuration
public class HiveConfig {
    @Value("${hive.dataSource.driverClassName}")
    private String driverClassName;

    @Value("${hive.dataSource.url}")
    private String jdbcUrl;

    @Value("${hive.dataSource.username}")
    private String username;

    @Value("${hive.dataSource.password}")
    private String password;
}
