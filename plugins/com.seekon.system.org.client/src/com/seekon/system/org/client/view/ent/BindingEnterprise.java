package com.seekon.system.org.client.view.ent;

import java.util.Date;
import java.util.List;

import com.jgoodies.binding.beans.Model;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Enterprise;

public class BindingEnterprise extends Model {

  private static final long serialVersionUID = 881683390226917933L;

  public static final String PROPERTYNAME_CODE = "code";

  public static final String PROPERTYNAME_NAME = "name";

  public static final String PROPERTYNAME_ENT_TYPE = "entType";

  public static final String PROPERTYNAME_ADDRESS = "address";

  public static final String PROPERTYNAME_POST_CODE = "postCode";

  public static final String PROPERTYNAME_YEAR = "year";

  private Enterprise target;

  public BindingEnterprise(Enterprise target) {
    super();
    this.target = target;
  }

  public String getEntType() {
    return target.getEntType();
  }

  public String getId() {
    return target.getId();
  }

  public void setEntType(String entType) {
    String old = this.target.getEntType();
    target.setEntType(entType);
    firePropertyChange(PROPERTYNAME_ENT_TYPE, old, entType);
  }

  public void setId(String id) {
    target.setId(id);
  }

  public String getCode() {
    return target.getCode();
  }

  public String getAddress() {
    return target.getAddress();
  }

  public void setCode(String code) {
    String old = this.target.getCode();
    target.setCode(code);
    firePropertyChange(PROPERTYNAME_CODE, old, code);
  }

  public void setAddress(String address) {
    String old = this.target.getAddress();
    target.setAddress(address);
    firePropertyChange(PROPERTYNAME_ADDRESS, old, address);
  }

  public String getName() {
    return target.getName();
  }

  public String getPostCode() {
    return target.getPostCode();
  }

  public void setName(String name) {
    String old = this.target.getName();
    target.setName(name);
    firePropertyChange(PROPERTYNAME_NAME, old, name);
  }

  public void setPostCode(String postCode) {
    String old = this.target.getPostCode();
    target.setPostCode(postCode);
    firePropertyChange(PROPERTYNAME_POST_CODE, old, postCode);
  }

  public int getYear() {
    return target.getYear();
  }

  public void setYear(int year) {
    int old = this.target.getYear();
    target.setYear(year);
    firePropertyChange(PROPERTYNAME_YEAR, old, year);
  }

  public Enterprise getParent() {
    return target.getParent();
  }

  public void setType(String type) {
    target.setType(type);
  }

  public void setParent(Enterprise parent) {
    target.setParent(parent);
  }

  public String getType() {
    return target.getType();
  }

  public List<AbstractOrgItem> getChildOrgItemList() {
    return target.getChildOrgItemList();
  }

  public String getCreator() {
    return target.getCreator();
  }

  public void setCreator(String creator) {
    target.setCreator(creator);
  }

  public void setChildOrgItemList(List<AbstractOrgItem> childOrgItemList) {
    target.setChildOrgItemList(childOrgItemList);
  }

  public Date getCreateTime() {
    return target.getCreateTime();
  }

  public void setCreateTime(Date createTime) {
    target.setCreateTime(createTime);
  }

  public AbstractOrgItem getChild(int index) {
    return target.getChild(index);
  }

  public String getLastModifier() {
    return target.getLastModifier();
  }

  public void setLastModifier(String lastModifier) {
    target.setLastModifier(lastModifier);
  }

  public int getChildCount() {
    return target.getChildCount();
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

  public Enterprise getEnterprise() {
    return this.target;
  }

  public Object clone() throws CloneNotSupportedException {
    BindingEnterprise clone = (BindingEnterprise) super.clone();
    return clone;
  }

}
