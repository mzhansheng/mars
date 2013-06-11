package com.seekon.smartclient.component.tree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public abstract class AbstractTreeAction extends AbstractAction implements
  TreeSelectionListener {

  protected JTree tree;

  protected List<TreePath> paths = new ArrayList<TreePath>();

  public AbstractTreeAction(JTree tree) {
    this.tree = tree;
    tree.addTreeSelectionListener(this);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    // Filter selected tree paths
    paths.clear();
    TreePath[] tps = tree.getSelectionPaths();
    if (tps != null) {
      for (TreePath path : tps) {
        if (tree.getSelectionModel().isPathSelected(path))
          paths.add(path);
      }
    }
    // System.out.println("当前选中的节点：" + paths);
  }
}
