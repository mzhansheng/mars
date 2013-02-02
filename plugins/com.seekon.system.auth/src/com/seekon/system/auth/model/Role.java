package com.seekon.system.auth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.seekon.system.common.model.Category;
import com.seekon.system.rule.model.Rule;

public class Role implements DatascopeOwner, Serializable, Cloneable {

  private static final long serialVersionUID = 8286361435391868232L;

  private String id;

  private String roleCode;

  private String roleName;

  private boolean enabled;

  private String creator;

  private Date createTime;

  private String lastModifier;

  private Date lastModifyTime;

  private Category category;// 所属的角色分类

  private ResourceWrapper resource;// 所拥有的资源权限

  private List<Rule> datascopeRuleList = new ArrayList<Rule>();//此角色（功能组）中具有的数据权限

  public String getId() {
    return id;
  }

  public void setId(String roleId) {
    this.id = roleId;
  }

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String createUser) {
    this.creator = createUser;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createDate) {
    this.createTime = createDate;
  }

  public String getLastModifier() {
    return lastModifier;
  }

  public void setLastModifier(String updateUser) {
    this.lastModifier = updateUser;
  }

  public Date getLastModifyTime() {
    return lastModifyTime;
  }

  public void setLastModifyTime(Date updateDate) {
    this.lastModifyTime = updateDate;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public ResourceWrapper getResource() {
    return resource;
  }

  public void setResource(ResourceWrapper resource) {
    this.resource = resource;
  }

  public List<Rule> getDatascopeRuleList() {
    return datascopeRuleList;
  }

  public void setDatascopeRuleList(List<Rule> datascopeRuleList) {
    this.datascopeRuleList = datascopeRuleList;
  }

  public void addDatascopeRule(Rule datascope) {
    this.datascopeRuleList.add(datascope);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Role other = (Role) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("角色代码:" + roleCode + "\n");
    sb.append("角色名称:" + roleName + "\n");
    return sb.toString();
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Role role = (Role) super.clone();
    if (this.category != null) {
      role.setCategory((Category) this.category.clone());
    }
    return role;
  }
}
