package com.seekon.system.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.service.mapper.RoleMapper;
import com.seekon.system.auth.spi.RoleService;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.service.AbstractCategoryService;
import com.seekon.system.rule.model.Rule;

public class RoleServiceImpl extends AbstractCategoryService implements RoleService {

  @Inject
  RoleMapper roleMapper;

  @Override
  public Category getCategory() {
    return getCategoryById("sys.auth.role");
  }

  @Override
  @Transactional
  public void deleteCategory(Category category) {
    super.deleteCategory(category);
    deleteRoleListByCategory(category);
  }

  @Transactional
  private void deleteRoleListByCategory(Category category) {
    roleMapper.deleteRoleResourceByCategory(category);
    roleMapper.deleteRoleRuleByCategory(category);
    roleMapper.deleteUserRoleByCategory(category);
    roleMapper.deleteRoleBasicInfoByCategory(category);
  }

  @Override
  public List<Role> getRoleList() {
    return roleMapper.getRoleList();
  }

  @Override
  @Transactional
  public Role addRole(Role role) {
    addRoleBasicInfo(role);
    addRoleResource(role);
    this.addRoleRule(role);
    return role;
  }

  @Transactional
  private void addRoleBasicInfo(Role role) {
    Category rolecCategory = role.getCategory();
    if (rolecCategory.getId() == null) {
      role.setCategory(this.addCategory(rolecCategory));
    }
    role.setId(UUID.randomUUID().toString());
    roleMapper.addRoleBasicInfo(role);
  }

  @Override
  @Transactional
  public Role addRoleResource(Role role) {
    roleMapper.addRoleResource(role);
    return role;
  }

  @Override
  @Transactional
  public void updateRole(Role role) {
    roleMapper.updateRoleBasicInfo(role);
    if (role.getResource() != null) {
      roleMapper.updateRoleResource(role);
    }
  }

  @Override
  @Transactional
  public void updateRoleResource(Role role) {
    roleMapper.updateRoleResource(role);
  }

  @Override
  @Transactional
  public void updateRoleRuleList(Role role) {
    roleMapper.deleteRoleRuleList(role);
    this.addRoleRule(role);
  }

  @Transactional
  private void addRoleRule(Role role) {
    List<Rule> ruleList = role.getDatascopeRuleList();
    for (Rule rule : ruleList) {
      Map<String, String> roleRule = new HashMap<String, String>();
      roleRule.put("id", UUID.randomUUID().toString());
      roleRule.put("roleId", role.getId());
      roleRule.put("ruleId", rule.getId());
      roleMapper.addRoleRule(roleRule);
    }
  }

  @Override
  @Transactional
  public void deleteRoles(List<Role> roles) {
    for (Role role : roles) {
      roleMapper.deleteRoleBasicInfo(role);
      roleMapper.deleteRoleResource(role);
      roleMapper.deleteUserRole(role);
      roleMapper.deleteRoleRuleList(role);
    }
  }

}
