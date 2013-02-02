package com.seekon.system.common.client.category;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.spi.CategoryService;

public class DeleteCategoryAction extends AbstractTreeAction {

  private static final long serialVersionUID = 6303566702908120313L;

  private List<Category> categories = new ArrayList<Category>();

  private CategoryService categoryService;

  public DeleteCategoryAction(JTree tree, CategoryService categoryService) {
    super(tree);
    this.categoryService = categoryService;

    this.putValue(Action.NAME, "删除分类");
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
        if (((Category) userObject).getParent() == null) {
          categories.clear();
          this.setEnabled(false);
        } else {
          this.setEnabled(true);
          categories.add((Category) userObject);
        }
      } else {
        categories.clear();
        this.setEnabled(false);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (JOptionPane.showConfirmDialog(tree, "确认删除选中的分类及下级分类等信息？", "请确认",
      JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
      UIUtilities.asyncInvoke(new DefaultInvokeHandler<Object>() {

        @Override
        public Object execute() throws Exception {
          categoryService.delelteCategoryList(categories);
          return null;
        }

        @Override
        public void success(Object result) {
          ObserverUtil.notifyEvent(new Event(categories,
            CategoryEventType.CATEGORY_DELETED), tree);
          JOptionPane.showMessageDialog(tree, "删除成功！");
        }
      });
    }
  }

}
