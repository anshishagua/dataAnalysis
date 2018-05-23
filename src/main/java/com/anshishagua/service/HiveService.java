package com.anshishagua.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hive.jdbc.HivePreparedStatement;
import org.apache.hive.jdbc.HiveStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Value("${hive.hdfs.uri}")
    private String hiveHdfsUri;

    @Value(("${hive.dataSource.schemaName}"))
    private String database;

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

    public ResultSet executeQuery(String sql) throws SQLException {
        if (hiveDataSource == null) {
            initDataSource();
        }

        Connection connection = hiveDataSource.getConnection();

        Statement statement = connection.createStatement();

        LOG.info("Execute {}", sql);

        return statement.executeQuery(sql);
    }

    public void execute(String sql) throws SQLException {
        if (hiveDataSource == null) {
            initDataSource();
        }

        Connection connection = hiveDataSource.getConnection();

        Statement statement = connection.createStatement();

        LOG.info("Execute {}", sql);

        statement.execute(sql);
    }

    public long count(String sql) throws SQLException {
        if (hiveDataSource == null) {
            initDataSource();
        }

        Connection connection = hiveDataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);

        LOG.info("Execute {}", sql);

        ResultSet resultSet = statement.executeQuery(sql);

        long count = 0;

        while (resultSet.next()) {
            count = resultSet.getLong(1);
        }

        return count;
    }

    public void execute(List<String> sqls) throws SQLException {
        if (hiveDataSource == null) {
            initDataSource();
        }

        Connection connection = hiveDataSource.getConnection();

        Statement statement = connection.createStatement();

        for (String sql : sqls) {
            LOG.info("Execute {}", sql);

            statement.execute(sql);
        }
    }

    private DataSource getHiveDataSource() {
        if (hiveDataSource == null) {
            initDataSource();
        }

        return hiveDataSource;
    }

    //LOAD DATA LOCAL inpath '/tmp/deposit_details.txt' OVERWRITE INTO TABLE deposit_details PARTITION(`p_exchange_date`='2018LOAD DATA LOCAL inpath ''/tmp/deposit_details.txt'' OVERWRITE INTO TABLE deposit_details PARTITION(`p_exchange_date`=''20180329'');
    //0329');
    public void load(String localFile, String tableName) throws IOException {
        String hdfsFileName = tableName + ".txt";

        String path = String.format("%s/%s.db/%s/%s", hiveHdfsUri, database, tableName, hdfsFileName);

        Configuration configuration = new Configuration();

        FileSystem fileSystem = FileSystem.get(URI.create(path), configuration);

        Path src = new Path(localFile);
        Path dst = new Path(path);
        try {
            fileSystem.copyFromLocalFile(src, dst);
        } finally {
            fileSystem.close();
        }
    }
}