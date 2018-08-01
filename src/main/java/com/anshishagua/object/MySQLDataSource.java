package com.anshishagua.object;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * User: lixiao
 * Date: 2018/6/13
 * Time: 上午11:14
 */

public class MySQLDataSource {
    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private String jdbcUrl;
    private String username;
    private String password;
    private String sql;
    private boolean inited;
    private DataSource dataSource;

    public static String getJdbcDriverClassName() {
        return JDBC_DRIVER_CLASS_NAME;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    private void init() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(JDBC_DRIVER_CLASS_NAME);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setReadOnly(true);
        config.setAutoCommit(true);

        dataSource = new HikariDataSource();
    }

    public void validate() throws SQLException {
        init();
    }
}