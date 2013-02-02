package com.seekon.system.auth.service.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.model.ResourceWrapper;
import com.seekon.system.common.util.XMLObjectHandler;

@MappedTypes(ResourceWrapper.class)
public class ResourceTypeHandler implements TypeHandler<ResourceWrapper> {

  @Override
  public ResourceWrapper getResult(ResultSet rs, String columnName)
    throws SQLException {
    return this.getResult(rs.getBytes(columnName));
  }

  @Override
  public ResourceWrapper getResult(ResultSet rs, int columnIndex)
    throws SQLException {
    return this.getResult(rs.getBytes(columnIndex));
  }

  @Override
  public ResourceWrapper getResult(CallableStatement cs, int columnIndex)
    throws SQLException {
    return this.getResult(cs.getBytes(columnIndex));
  }

  private ResourceWrapper getResult(byte[] is) {
    if (is == null || is.length == 0) {
      return null;
    }
    ResourceWrapper resourceWrapper = XMLObjectHandler.readObject(
      ResourceWrapper.class, is);
    rebuildResourceChildren(resourceWrapper.getResource());
    return resourceWrapper;
  }

  private void rebuildResourceChildren(AbstractResource resource) {
    if (resource instanceof Resource) {
      List<AbstractResource> children = ((Resource) resource).getChildren();
      for (AbstractResource child : children) {
        child.setParent(resource);
        rebuildResourceChildren(child);
      }
    }
  }

  @Override
  public void setParameter(PreparedStatement pst, int columnIndex,
    ResourceWrapper resource, JdbcType jdbcType) throws SQLException {
    try {
      pst.setBytes(columnIndex, XMLObjectHandler.writeObject(resource));
    } catch (Throwable e) {
      e.printStackTrace();
    } finally {

    }
  }

}
