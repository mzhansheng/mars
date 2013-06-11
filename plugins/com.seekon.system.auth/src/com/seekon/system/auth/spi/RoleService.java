package com.seekon.system.auth.spi;

import java.util.List;

import com.seekon.system.auth.model.Role;
import com.seekon.system.common.spi.CategoryService;

public interface RoleService extends CategoryService {

  List<Role> getRoleList();

  Role addRole(Role role);

  Role addRoleResource(Role role);

  void updateRole(Role role);

  void updateRoleResource(Role role);

  void updateRoleRuleList(Role role);

  void deleteRoles(List<Role> roles);

}
