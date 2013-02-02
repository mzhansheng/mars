package com.seekon.system.org.client.view.dept;

import java.util.Date;

import com.jgoodies.binding.beans.Model;
import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;

public class BindingDepartment extends Model {

  private static final long serialVersionUID = 881683390226917933L;

  public static final String PROPERTYNAME_CODE = "code";

  public static final String PROPERTYNAME_NAME = "name";

  public static final String PROPERTYNAME_YEAR = "year";

  private Department target;

  public BindingDepartment(Department target) {
    super();
    this.target = target;
  }

  public void setId(String id) {
    target.setId(id);
  }

  public String getCode() {
    return target.getCode();
  }

  public void setCode(String code) {
    String old = this.target.getCode();
    target.setCode(code);
    firePropertyChange(PROPERTYNAME_CODE, old, code);
  }

  public String getName() {
    return target.getName();
  }

  public void setName(String name) {
    String old = this.target.getName();
    target.setName(name);
    firePropertyChange(PROPERTYNAME_NAME, old, name);
  }

  public int getYear() {
    return target.getYear();
  }

  public void setYear(int year) {
    int old = this.target.getYear();
    target.setYear(year);
    firePropertyChange(PROPERTYNAME_YEAR, old, year);
  }

  public void setType(String type) {
    target.setType(type);
  }

  public String getType() {
    return target.getType();
  }

  public String getCreator() {
    return target.getCreator();
  }

  public void setCreator(String creator) {
    target.setCreator(creator);
  }

  public Date getCreateTime() {
    return target.getCreateTime();
  }

  public void setCreateTime(Date createTime) {
    target.setCreateTime(createTime);
  }

  public String getLastModifier() {
    return target.getLastModifier();
  }

  public void setLastModifier(String lastModifier) {
    target.setLastModifier(lastModifier);
  }

  public Date getLastModifyTime() {
    return target.getLastModifyTime();
  }

  public void setLastModifyTime(Date lastModifyTime) {
    target.setLastModifyTime(lastModifyTime);
  }

  public int hashCode() {
    return target.hashCode();
  }

  public boolean equals(Object obj) {
    return target.equals(obj);
  }

  public String toString() {
    return target.toString();
  }

  public Department getDepartment() {
    return this.target;
  }

  public Enterprise getBelongedEnt() {
    return target.getBelongedEnt();
  }

  public void setBelongedEnt(Enterprise belongedEnt) {
    target.setBelongedEnt(belongedEnt);
  }

  public String getId() {
    return target.getId();
  }

  public Object clone() throws CloneNotSupportedException {
    BindingDepartment clone = (BindingDepartment) super.clone();
    return clone;
  }

}
