package com.seekon.system.auth.client.view.role.resource;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.seekon.system.auth.model.ResourceWrapper;

public class AuthResourceTreeModel extends DefaultTreeModel {

  private static final long serialVersionUID = -6458337688909450908L;

  public AuthResourceTreeModel(ResourceWrapper resource) {
    super(new AuthResourceTreeNode(resource));
  }

  @Override
  public Object getChild(Object parent, int index) {
    DefaultMutableTreeNode node = null;
    try {
      node = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) parent)
        .getChildAt(index);
    } catch (Exception e) {
    }
    if (node == null) {
      ResourceWrapper resource = (ResourceWrapper) ((DefaultMutableTreeNode) parent)
        .getUserObject();
      if (resource == null) {
        return null;
      }
      node = new AuthResourceTreeNode(resource.getChild(index));
      ((DefaultMutableTreeNode) parent).add(node);
    }
    return node;
  }

  @Override
  public int getChildCount(Object parent) {
    ResourceWrapper resource = (ResourceWrapper) ((DefaultMutableTreeNode) parent)
      .getUserObject();
    if (resource == null) {
      return 0;
    }
    return resource.getChildCount();
  }

  @Override
  public boolean isLeaf(Object node) {
    return getChildCount(node) == 0;
  }
}
