package com.anshishagua.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午2:42
 */

@MappedJdbcTypes(JdbcType.TIMESTAMP)
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeHandler extends BaseTypeHandler<LocalDateTime> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime localDateTime, JdbcType jdbcType) throws SQLException {
        if (localDateTime == null) {
            ps.setTimestamp(i, null);
            return;
        }

        ps.setTimestamp(i, Timestamp.valueOf(localDateTime));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);

        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);

        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);

        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}