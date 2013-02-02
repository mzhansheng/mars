package com.seekon.system.common.client.category;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.smartclient.component.viewedit.SwitcherPage;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.spi.CategoryService;

public class CreateCategoryAction extends AbstractTreeAction {

  private static final long serialVersionUID = -5110490738245283688L;

  private SwitcherPage switcherPage;

  private CategoryService categoryService;

  private Category parent;

  public CreateCategoryAction(JTree tree, SwitcherPage switcherPage,
    CategoryService categoryService) {
    super(tree);

    this.switcherPage = switcherPage;
    this.categoryService = categoryService;

    this.putValue(Action.NAME, "新建分类");
    this.setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);
    this.setEnabled(false);
    
    if (paths == null || paths.isEmpty()) {
      this.setEnabled(false);
    } else {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree
        .getSelectionPath().getLastPathComponent();
      Object userObject = treeNode.getUserObject();
      if (userObject instanceof Category) {
        if ((treeNode.getChildCount() == 0)
          || (treeNode.getChildCount() > 0 && ((DefaultMutableTreeNode) treeNode
            .getChildAt(0)).getUserObject() instanceof Category)) {
          this.setEnabled(true);
          parent = (Category) userObject;
        } else {
          this.setEnabled(false);
        }
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switcherPage
      .callback(new CreateCategoryPage(parent, categoryService));
  }

}
