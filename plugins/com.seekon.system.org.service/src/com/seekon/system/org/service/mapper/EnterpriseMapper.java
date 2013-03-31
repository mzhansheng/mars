package com.seekon.system.org.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seekon.system.auth.Action;
import com.seekon.system.auth.Datascope;
import com.seekon.system.common.ControlDataUpdater;
import com.seekon.system.org.model.Enterprise;

public interface EnterpriseMapper {

  @Datascope(actions = { @Action(id = "func.watch", resourceId = "system.org") })
  public List<Enterprise> getEnterpriseListByParentId(@Param("id")
  String parentId);
    
  @ControlDataUpdater(tableName = "m_ele_enterprise", keyField = "enterprise.id")
  public void addEnterprise(@Param("enterprise")
  Enterprise enterprise);

  @ControlDataUpdater(tableName = "m_ele_enterprise", keyField = "enterprise.id")
  public void updateEnterprise(@Param("enterprise")
  Enterprise enterprise);

  public void deleteEnterprise(@Param("enterprise")
  Enterprise enterprise);
}
