package com.seekon.system.common.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.seekon.system.common.util.XMLObjectHandler;

public class OptionTypeHandler implements TypeHandler<Object>{

  @Override
  public Object getResult(ResultSet rs, String columnName)
    throws SQLException {
    return this.getResult(rs.getBytes(columnName));
  }

  @Override
  public Object getResult(ResultSet rs, int columnIndex)
    throws SQLException {
    return this.getResult(rs.getBytes(columnIndex));
  }

  @Override
  public Object getResult(CallableStatement cs, int columnIndex)
    throws SQLException {
    return this.getResult(cs.getBytes(columnIndex));
  }

  private Object getResult(byte[] is) {
    if (is == null || is.length == 0) {
      return null;
    }
    Object Object = XMLObjectHandler.readObject(
      Object.class, is);
    return Object;
  }

  @Override
  public void setParameter(PreparedStatement pst, int columnIndex,
    Object resource, JdbcType jdbcType) throws SQLException {
    try {
      pst.setBytes(columnIndex, XMLObjectHandler.writeObject(resource));
    } catch (Throwable e) {
      e.printStackTrace();
    } finally {

    }
  }

}
