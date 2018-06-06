package com.anshishagua.mybatis.typehandler;

import com.anshishagua.constants.ObjectType;
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
 * Date: 2018/6/6
 * Time: 下午3:11
 */

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({ObjectType.class})
public class ObjectTypeHandler extends BaseTypeHandler<ObjectType> {
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ObjectType objectType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, objectType == null ? null : objectType.getValue());
    }

    public ObjectType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);

        return ObjectType.parseByValue(string);
    }

    public ObjectType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);

        return ObjectType.parseByValue(string);
    }

    public ObjectType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);

        return ObjectType.parseByValue(string);
    }
}