package com.anshishagua.mybatis.typehandler;

import com.anshishagua.constants.TagType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: lixiao
 * Date: 2018/6/13
 * Time: 上午10:42
 */

public class TagTypeHandler extends BaseTypeHandler<TagType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TagType tagType, JdbcType jdbcType) throws SQLException {
        ps.setString(i, tagType.getValue());
    }

    @Override
    public TagType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return TagType.parseByValue(rs.getString(columnName));
    }

    @Override
    public TagType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return TagType.parseByValue(rs.getString(columnIndex));
    }

    @Override
    public TagType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return TagType.parseByValue(cs.getString(columnIndex));
    }
}