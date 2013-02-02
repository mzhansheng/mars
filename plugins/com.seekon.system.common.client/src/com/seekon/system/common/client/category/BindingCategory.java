package com.seekon.system.common.client.category;

import java.util.List;

import com.jgoodies.binding.beans.Model;
import com.seekon.system.common.model.Category;

public class BindingCategory extends Model {

  private static final long serialVersionUID = -6529772460277082376L;

  public static final String PROPERTYNAME_NAME = "displayName";

  public static final String PROPERTYNAME_ID = "id";

  private Category target;

  public BindingCategory(Category target) {
    super();
    this.target = target;
  }

  public String getId() {
    return target.getId();
  }

  public void setId(String categoryId) {
    String old = target.getId();
    target.setId(categoryId);
    firePropertyChange(PROPERTYNAME_ID, old, categoryId);
  }

  public String getDisplayName() {
    return target.getDisplayName();
  }

  public void setDisplayName(String categoryName) {
    String old = target.getDisplayName();
    target.setDisplayName(categoryName);
    firePropertyChange(PROPERTYNAME_NAME, old, categoryName);
  }

  public Category getCategory() {
    return this.target;
  }

  
  public List<Category> getChildren() {
    return target.getChildren();
  }

  public void setChildren(List<Category> children) {
    target.setChildren(children);
  }

  public void addChild(Category child) {
    target.addChild(child);
  }

  public Category getParent() {
    return target.getParent();
  }

  public void setParent(Category parent) {
    target.setParent(parent);
  }

  public int hashCode() {
    return target.hashCode();
  }

  public boolean equals(Object obj) {
    return target.equals(obj);
  }

  @Override
  public String toString() {
    return target.getDisplayName();
  }
}
