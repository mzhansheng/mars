package com.seekon.system.org.model;

import java.util.ArrayList;
import java.util.List;

public class Enterprise extends AbstractOrgItem {

  private static final long serialVersionUID = -1490940396238785791L;

  private String entType;

  private String address;

  private String postCode;

  private Enterprise parent;

  private List<AbstractOrgItem> childOrgItemList = null;

  public Enterprise() {
    super();
    type = "004";
  }

  public String getEntType() {
    return entType;
  }

  public void setEntType(String entType) {
    this.entType = entType;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public Enterprise getParent() {
    return parent;
  }

  public void setParent(Enterprise parent) {
    this.parent = parent;
  }

  public List<AbstractOrgItem> getChildOrgItemList() {// //TODO:
    return childOrgItemList;
  }

  public void setChildOrgItemList(List<AbstractOrgItem> childOrgItemList) {
    this.childOrgItemList = childOrgItemList;
  }

  public void addChildOrgItem(AbstractOrgItem child) {
    if(this.childOrgItemList == null){
      this.childOrgItemList = new ArrayList<AbstractOrgItem>();
    }
    this.childOrgItemList.add(child);
    if(child instanceof Enterprise){
      ((Enterprise)child).setParent(this);
    }else if(child instanceof Department){
      ((Department)child).setBelongedEnt(this);
    }
  }

  public void removeChildOrgItem(AbstractOrgItem child){
    this.childOrgItemList.remove(child);
  }
  
  public AbstractOrgItem getChild(int index) {
    if (this.childOrgItemList == null) {
      return null;
    }
    return childOrgItemList.get(index);
  }

  public int getChildCount() {
    return this.childOrgItemList == null ? 0 : this.childOrgItemList.size();
  }
}
