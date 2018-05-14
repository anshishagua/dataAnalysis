package com.anshishagua.mybatis.typehandler;

import com.alibaba.fastjson.JSON;
import com.anshishagua.object.SQLGenerateResult;
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
 * Time: 上午10:57
 */

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({SQLGenerateResult.class})
public class SQLGenerateResultHandler extends BaseTypeHandler<SQLGenerateResult> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SQLGenerateResult result, JdbcType jdbcType) throws SQLException {
        if (result == null) {
            ps.setString(i, null);
            return;
        }

        ps.setString(i, JSON.toJSONString(result));
    }

    @Override
    public SQLGenerateResult getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);

        return value == null ? null : JSON.parseObject(value, SQLGenerateResult.class);
    }

    @Override
    public SQLGenerateResult getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);

        return value == null ? null : JSON.parseObject(value, SQLGenerateResult.class);
    }

    @Override
    public SQLGenerateResult getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);

        return value == null ? null : JSON.parseObject(value, SQLGenerateResult.class);
    }
}