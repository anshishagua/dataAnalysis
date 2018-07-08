package com.anshishagua.mybatis.typehandler;

import com.anshishagua.constants.DataType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: lixiao
 * Date: 2018/7/5
 * Time: 下午4:31
 */

public class DataTypeHandler extends BaseTypeHandler<DataType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DataType modelType, JdbcType jdbcType) throws SQLException {
        ps.setString(1, modelType.getValue());
    }

    @Override
    public DataType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return DataType.parseByValue(rs.getString(columnName));
    }

    @Override
    public DataType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return DataType.parseByValue(rs.getString(columnIndex));
    }

    @Override
    public DataType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return DataType.parseByValue(cs.getString(columnIndex));
    }
}