package com.seekon.system.auth.client.view.role.basicinfo;

import java.util.Date;

import com.jgoodies.binding.beans.Model;
import com.seekon.system.auth.model.Role;
import com.seekon.system.common.model.Category;

public class BindingRole extends Model {

  private static final long serialVersionUID = -5976664401689959079L;

  public static final String PROPERTYNAME_ROLENAME = "roleName";

  public static final String PROPERTYNAME_ROLEID = "id";

  public static final String PROPERTYNAME_ROLECODE = "roleCode";

  private Role role;

  public BindingRole(Role target) {
    this.role = target;
  }

  public void setCategory(Category category) {
    this.role.setCategory(category);
  }

  public Category getCategory() {
    return role.getCategory();
  }

  public String getRoleId() {
    return role.getId();
  }

  public void setRoleId(String roleId) {
    String old = role.getId();
    role.setId(roleId);
    firePropertyChange(PROPERTYNAME_ROLEID, old, roleId);
  }

  public String getRoleCode() {
    return role.getRoleCode();
  }

  public void setRoleCode(String roleCode) {
    String oldValue = role.getRoleCode();
    role.setRoleCode(roleCode);
    firePropertyChange(PROPERTYNAME_ROLECODE, oldValue, roleCode);
  }

  public String getRoleName() {
    return role.getRoleName();
  }

  public void setRoleName(String roleName) {
    String old = role.getRoleName();
    role.setRoleName(roleName);
    firePropertyChange(PROPERTYNAME_ROLENAME, old, roleName);
  }

  public boolean getEnabled() {
    return role.getEnabled();
  }

  public void setEnabled(boolean enabled) {
    role.setEnabled(enabled);
  }

  public String getCreator() {
    return role.getCreator();
  }

  public void setCreator(String createUser) {
    role.setCreator(createUser);
  }

  public Date getCreateTime() {
    return role.getCreateTime();
  }

  public void setCreateTime(Date createDate) {
    role.setCreateTime(createDate);
  }

  public String getLastModifier() {
    return role.getLastModifier();
  }

  public void setLastModifier(String updateUser) {
    role.setLastModifier(updateUser);
  }

  public Date getLastModifyTime() {
    return role.getLastModifyTime();
  }

  public void setLastModifyTime(Date updateDate) {
    role.setLastModifyTime(updateDate);
  }

  public Role getRole() {
    return this.role;
  }

  public String toString() {
    return getRoleName();
  }
}
