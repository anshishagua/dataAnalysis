package com.anshishagua.mybatis.typehandler;

import com.anshishagua.object.IndexType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午11:02
 */

public class IndexTypeHandler extends BaseTypeHandler<IndexType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IndexType indexType, JdbcType jdbcType)
            throws SQLException {

        ps.setString(i, indexType.getValue());
    }

    @Override
    public IndexType getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return IndexType.parseByValue(rs.getString(columnName));
    }

    @Override
    public IndexType getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return IndexType.parseByValue(rs.getString(columnIndex));
    }

    @Override
    public IndexType getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return IndexType.parseByValue(cs.getString(columnIndex));
    }
}