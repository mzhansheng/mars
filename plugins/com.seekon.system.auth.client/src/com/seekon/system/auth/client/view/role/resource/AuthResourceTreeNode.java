package com.seekon.system.auth.client.view.role.resource;

import javax.swing.tree.DefaultMutableTreeNode;

import com.seekon.system.auth.model.ResourceWrapper;

public class AuthResourceTreeNode extends DefaultMutableTreeNode {

  private static final long serialVersionUID = 3742102074163005055L;

  public AuthResourceTreeNode() {
    super();
  }

  public AuthResourceTreeNode(Object userObject, boolean allowsChildren) {
    super(userObject, allowsChildren);
  }

  public AuthResourceTreeNode(Object userObject) {
    super(userObject);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof DefaultMutableTreeNode) {
      DefaultMutableTreeNode tmp = (DefaultMutableTreeNode) obj;
      if (this.getUserObject() != null) {
        return this.getUserObject().equals(tmp.getUserObject());
      }
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    if (this.getUserObject() != null) {
      return this.getUserObject().hashCode();
    }
    return super.hashCode();
  }

  @Override
  public String toString() {
    ResourceWrapper resource = (ResourceWrapper) this.getUserObject();
    if (resource != null) {
      return resource.getResource().getName();
    }
    return super.toString();
  }
}
