package com.seekon.system.auth.service.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.seekon.system.auth.model.Role;
import com.seekon.system.common.ControlDataUpdater;
import com.seekon.system.common.model.Category;

public interface RoleMapper {

  public List<Role> getRoleList();

  public Role getRoleByRoleCode(@Param("roleCode")
  String roleCode);

  @ControlDataUpdater(tableName = "m_sys_role", keyField="role.id")
  public void addRoleBasicInfo(@Param("role")
  Role role);

  public void addRoleResource(@Param("role")
  Role role);

  @ControlDataUpdater(tableName = "m_sys_role", keyField="role.id")
  public void updateRoleBasicInfo(@Param("role")
  Role role);

  public void updateRoleResource(@Param("role")
  Role role);

  public void deleteRoleBasicInfo(@Param("role")
  Role role);

  public void deleteRoleResource(@Param("role")
  Role role);

  public void deleteUserRole(@Param("role")
  Role role);

  public void deleteRoleRuleList(@Param("role")
  Role role);

  public void addRoleRule(@Param("roleRule")
  Map<String, String> roleRule);

  public void deleteRoleBasicInfoByCategory(@Param("category")
  Category category);

  public void deleteRoleResourceByCategory(@Param("category")
  Category category);

  public void deleteUserRoleByCategory(@Param("category")
  Category category);

  public void deleteRoleRuleByCategory(@Param("category")
  Category category);

}
