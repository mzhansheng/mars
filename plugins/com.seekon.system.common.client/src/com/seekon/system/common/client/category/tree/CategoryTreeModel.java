package com.seekon.system.common.client.category.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.seekon.system.common.model.Category;

public class CategoryTreeModel extends DefaultTreeModel {

  private static final long serialVersionUID = -4412450989951372485L;

  public CategoryTreeModel(Category root) {
    super(new DefaultMutableTreeNode(root));
  }

  @Override
  public Object getChild(Object parent, int index) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parent;
    DefaultMutableTreeNode node = null;
    try {
      node = (DefaultMutableTreeNode) parentNode.getChildAt(index);
    } catch (Exception e) {
    }
    if (node == null) {
      node = new DefaultMutableTreeNode(
        ((Category) parentNode.getUserObject()).getChild(index));
      parentNode.add(node);
    }
    return node;
  }

  @Override
  public int getChildCount(Object parent) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parent;
    return ((Category) parentNode.getUserObject()).getChildCount();
  }

  @Override
  public boolean isLeaf(Object node) {
    return getChildCount(node) == 0;
  }

}
