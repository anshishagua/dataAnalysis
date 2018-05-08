package com.anshishagua.mybatis.typehandler;

import com.anshishagua.parser.nodes.sql.JoinType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: lixiao
 * Date: 2018/5/2
 * Time: 下午4:18
 */

public class JoinTypeHandler extends BaseTypeHandler<JoinType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JoinType joinType, JdbcType jdbcType)
            throws SQLException {

        ps.setString(i, joinType.toSQL());
    }

    @Override
    public JoinType getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return JoinType.parse(rs.getString(columnName));
    }

    @Override
    public JoinType getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return JoinType.parse(rs.getString(columnIndex));
    }

    @Override
    public JoinType getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return JoinType.parse(cs.getString(columnIndex));
    }
}