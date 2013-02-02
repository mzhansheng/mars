package com.seekon.system.auth.client.view.role;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.system.auth.client.view.role.wizard.CreateRoleWizard;
import com.seekon.system.auth.model.Role;
import com.seekon.system.common.model.Category;

public class CreateRoleAction extends AbstractTreeAction {

  private static final long serialVersionUID = 5851859117526487608L;

  private Category category;

  public CreateRoleAction(JTree tree) {
    super(tree);
    putValue(Action.NAME, "创建角色...");
    setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);

    setEnabled(false);

    if (paths.isEmpty())
      setEnabled(false);
    else if (paths.size() != 1)
      setEnabled(false);
    else {
      TreePath tp = paths.get(0);
      if (tp.getLastPathComponent() == tree.getModel().getRoot()) {
        setEnabled(false);
      } else {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tp
          .getLastPathComponent();
        Object userObject = treeNode.getUserObject();
        if (userObject instanceof Category) {
          if ((treeNode.getChildCount() == 0)
            || (treeNode.getChildCount() > 0 && ((DefaultMutableTreeNode) treeNode
              .getChildAt(0)).getUserObject() instanceof Role)) {
            setEnabled(true);
            category = (Category) userObject;
          }
        } else {
          setEnabled(false);
        }
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    RolePage rolePage = (RolePage) SwingUtilities.getAncestorOfClass(RolePage.class,
      tree);
    rolePage.getRoleDisplayer().callback(new CreateRoleWizard(category));
  }

}
