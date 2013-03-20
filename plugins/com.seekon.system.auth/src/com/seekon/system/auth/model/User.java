package com.seekon.system.auth.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.rule.model.Rule;

public class User implements DatascopeOwner, Serializable, Cloneable {

  private static final long serialVersionUID = -1692046901933845042L;

  private String id;

  private String userCode;

  private String userName;

  private String password;

  private String sex;

  private String mobile;

  private String phone;

  private String email;

  private String idCard;

  private Date birthday;

  private AbstractOrgItem belongedOrgItem;//所属机构

  private List<Role> roleList = new ArrayList<Role>();//拥有的角色列表

  private List<Rule> datascopeRuleList = new ArrayList<Rule>();//用户全局的数据权限，不区分角色

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public AbstractOrgItem getBelongedOrgItem() {
    return belongedOrgItem;
  }

  public void setBelongedOrgItem(AbstractOrgItem belongedOrgItem) {
    this.belongedOrgItem = belongedOrgItem;
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role role) {
    this.roleList.add(role);
  }

  public List<Rule> getDatascopeRuleList() {
    return datascopeRuleList;
  }

  public void setDatascopeRuleList(List<Rule> datascopeRuleList) {
    this.datascopeRuleList = datascopeRuleList;
  }

  public void addDatascopeRule(Rule datascopeRule) {
    this.datascopeRuleList.add(datascopeRule);
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
    User other = (User) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("{user_d:");
    sb.append(userCode);
    sb.append(",user_name:");
    sb.append(userName);
    sb.append("}");
    return sb.toString();
  }
}
