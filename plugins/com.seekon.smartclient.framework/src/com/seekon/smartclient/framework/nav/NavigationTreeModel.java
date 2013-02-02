package com.seekon.smartclient.framework.nav;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Resource;
import com.seekon.smartclient.module.tree.DefaultResourceTreeModel;

public class NavigationTreeModel extends DefaultResourceTreeModel {

  private static final long serialVersionUID = -742081768787158990L;

  public NavigationTreeModel(TreeNode root) {
    super(root);
  }

  @Override
  public Object getChild(Object parent, int index) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parent;
    DefaultMutableTreeNode node = null;
    try{
      node = (DefaultMutableTreeNode) parentNode.getChildAt(index);
    }catch(Exception e){
    }
    if(node == null){
      Resource resource = (Resource) parentNode.getUserObject();
      AbstractResource child = getChildNotWithAction(resource, index);
      node = new DefaultMutableTreeNode(child);
      parentNode.add(node);
    }
    return node;
  }

  @Override
  public int getChildCount(Object parent) {
    Resource resource = (Resource) ((DefaultMutableTreeNode) parent).getUserObject();
    return getChildCountNotWithAction(resource);
  }

  private AbstractResource getChildNotWithAction(Resource resource, int index) {
    int count = -1;
    for (int i = 0; i < resource.getChildCount(); i++) {
      AbstractResource child = resource.getChild(i);
      if (child instanceof Resource) {
        count++;
      }
      if (index == count) {
        return child;
      }
    }
    return null;
  }

  private int getChildCountNotWithAction(Resource resource) {
    if(resource == null){
      return 0;
    }
    int count = 0;
    for (AbstractResource child : resource.getChildren()) {
      if (child instanceof Resource) {
        count++;
      }
    }
    return count;
  }
}
