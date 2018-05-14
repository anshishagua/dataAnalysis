package com.anshishagua.mybatis.typehandler;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午11:44
 */

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({List.class})
public class ExecuteSQLsHandler extends BaseTypeHandler<List> {
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List sqls, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, sqls == null ? null : JSON.toJSONString(sqls));
    }

    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);

        return string == null ? Collections.emptyList() : JSON.parseArray(string, String.class);
    }

    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);

        return string == null ? Collections.emptyList() : JSON.parseArray(string, String.class);
    }

    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);

        return string == null ? Collections.emptyList() : JSON.parseArray(string, String.class);
    }
}