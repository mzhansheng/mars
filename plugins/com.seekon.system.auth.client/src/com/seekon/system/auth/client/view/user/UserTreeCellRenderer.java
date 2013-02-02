package com.seekon.system.auth.client.view.user;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.seekon.system.auth.model.User;

public class UserTreeCellRenderer extends DefaultTreeCellRenderer {

  private static final long serialVersionUID = -9106374092436052243L;

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value,
    boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

    JLabel treeCellRendererComponent = (JLabel) super.getTreeCellRendererComponent(
      tree, value, selected, expanded, leaf, row, hasFocus);

    // Use user name as user tree's label
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
    Object userObject = node.getUserObject();
    if (userObject instanceof User) {
      User user = (User) userObject;
      treeCellRendererComponent.setText(user.getUserName());

      treeCellRendererComponent.setIcon(new ImageIcon(this.getClass().getResource(
        "/com/seekon/system/auth/client/resource/icon/user/user.png")));
    }

    return treeCellRendererComponent;
  }

}
