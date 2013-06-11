package com.seekon.system.auth.client.view.user;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.system.auth.client.view.user.wizard.CreateUserWizard;
import com.seekon.system.auth.model.User;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Department;

public class CreateUserAction extends AbstractTreeAction {
  private static final long serialVersionUID = -8111003211906495967L;

  public CreateUserAction(JTree tree) {
    super(tree);
    putValue(Action.NAME, "创建用户...");
    setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);

    setEnabled(false);

    if (paths.isEmpty()) {
      setEnabled(false);
    } else if (paths.size() != 1) {
      setEnabled(false);
    } else {
      TreePath tp = paths.get(0);
      Object userObject = ((DefaultMutableTreeNode) tp.getLastPathComponent())
        .getUserObject();
      if (userObject instanceof Department) {
        this.setEnabled(true);
      }
      if (userObject instanceof User) {
        this.setEnabled(false);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    TreePath tp = paths.get(0);
    Object userObject = ((DefaultMutableTreeNode) tp.getLastPathComponent())
      .getUserObject();
    UserPage userManager = (UserPage) SwingUtilities.getAncestorOfClass(
      UserPage.class, tree);
    userManager.getUserRightContainer().callback(
      new CreateUserWizard((AbstractOrgItem) userObject));
  }

}
