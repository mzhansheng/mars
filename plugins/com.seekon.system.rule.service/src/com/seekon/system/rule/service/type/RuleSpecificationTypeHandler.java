package com.seekon.system.rule.service.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.seekon.mars.rule.engine.specification.IRuleSpecification;
import com.seekon.system.common.util.XMLObjectHandler;

@MappedTypes(IRuleSpecification.class)
public class RuleSpecificationTypeHandler implements TypeHandler<IRuleSpecification> {

  @Override
  public IRuleSpecification getResult(ResultSet arg0, String arg1)
    throws SQLException {
    return getResult(arg0.getBytes(arg1));
  }

  @Override
  public IRuleSpecification getResult(ResultSet arg0, int arg1) throws SQLException {
    return getResult(arg0.getBytes(arg1));
  }

  @Override
  public IRuleSpecification getResult(CallableStatement arg0, int arg1)
    throws SQLException {
    return getResult(arg0.getBytes(arg1));
  }

  private IRuleSpecification getResult(byte[] is) {
    if (is == null || is.length == 0) {
      return null;
    }
    return XMLObjectHandler.readObject(IRuleSpecification.class, is);
  }

  @Override
  public void setParameter(PreparedStatement arg0, int arg1,
    IRuleSpecification arg2, JdbcType arg3) throws SQLException {
    if (arg2 == null) {
      arg0.setBytes(arg1, null);
    } else {
      arg0.setBytes(arg1, XMLObjectHandler.writeObject(arg2));
    }
  }

}
