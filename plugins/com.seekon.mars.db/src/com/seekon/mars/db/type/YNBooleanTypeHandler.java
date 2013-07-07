package com.seekon.mars.db.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class YNBooleanTypeHandler extends BaseTypeHandler<Boolean> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter,
    JdbcType jdbcType) throws SQLException {
    if (parameter) {
      ps.setString(i, "Y");
    } else {
      ps.setString(i, "N");
    }
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, String columnName)
    throws SQLException {
    return checkResult(rs.getString(columnName));
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, int columnIndex)
    throws SQLException {
    return checkResult(rs.getString(columnIndex));
  }

  @Override
  public Boolean getNullableResult(CallableStatement cs, int columnIndex)
    throws SQLException {
    return checkResult(cs.getString(columnIndex));
  }

  private boolean checkResult(String result) {
    return result != null && "Y".equalsIgnoreCase(result) ? true : false;
  }
}
