package com.anshishagua.mybatis.typehandler;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午9:38
 */

import com.anshishagua.object.TaskStatus;
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
 * Date: 2018/3/5
 * Time: 下午4:48
 */

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({TaskStatus.class})
public class TaskStatusHandler extends BaseTypeHandler<TaskStatus> {
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TaskStatus TaskStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, TaskStatus == null ? null : TaskStatus.toString());
    }

    public TaskStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);

        return TaskStatus.parseByValue(string);
    }

    public TaskStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);

        return TaskStatus.parseByValue(string);
    }

    public TaskStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);

        return TaskStatus.parseByValue(string);
    }
}