package com.anshishagua.mybatis.typehandler;

import com.anshishagua.constants.TaskType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午9:52
 */

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({TaskType.class})
public class TaskTypeHandler extends BaseTypeHandler<TaskType> {
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TaskType taskType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, taskType == null ? null : taskType.toString());
    }

    public TaskType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);

        return TaskType.parseByValue(string);
    }

    public TaskType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);

        return TaskType.parseByValue(string);
    }

    public TaskType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);

        return TaskType.parseByValue(string);
    }
}