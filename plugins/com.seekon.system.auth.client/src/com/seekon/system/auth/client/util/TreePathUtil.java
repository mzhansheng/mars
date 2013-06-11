package com.seekon.system.auth.client.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class TreePathUtil {

  public static TreePath getTreePath(TreeNode[] parentNodes, TreeNode treeNode) {
    List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
    if (parentNodes != null) {
      treeNodeList.addAll(java.util.Arrays.asList(parentNodes));
    }
    treeNodeList.add(treeNode);
    return new TreePath(treeNodeList.toArray());
  }
}
