package com.seekon.system.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 系统中所用到的公共分类（分组）对象
 * 1、可对应物理表m_sys_category
 *
 */
public class Category implements Serializable, Cloneable {

  private static final long serialVersionUID = -1818191912321151L;

  private String id;

  private String displayName;

  private List<Category> children = new ArrayList<Category>();

  private Category parent;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public List<Category> getChildren() {
    return children;
  }

  public void setChildren(List<Category> children) {
    this.children = children;
  }

  public void addChild(Category child) {
    this.children.add(child);
  }

  public void removeChild(Category child) {
    this.children.remove(child);
  }

  public Category getChild(int index) {
    return this.children.get(index);
  }

  public int getChildCount() {
    return this.children.size();
  }

  public Category getParent() {
    return parent;
  }

  public void setParent(Category parent) {
    this.parent = parent;
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
    Category other = (Category) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return this.displayName;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
