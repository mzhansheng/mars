package com.seekon.system.auth.client.view.role;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.seekon.system.auth.model.Role;

public class RoleTreeCellRenderer extends DefaultTreeCellRenderer {

  private static final long serialVersionUID = -6981031947928276981L;

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value,
    boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

    JLabel treeCellRendererComponent = (JLabel) super.getTreeCellRendererComponent(
      tree, value, selected, expanded, leaf, row, hasFocus);

    // Use user name as user tree's label
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
    Object userObject = node.getUserObject();
    if (userObject instanceof Role) {
      Role role = (Role) userObject;
      treeCellRendererComponent.setText(role.getRoleName());

      treeCellRendererComponent.setIcon(new ImageIcon(this.getClass().getResource(
        "/com/seekon/system/auth/client/resource/icon/role/role.png")));
    }

    return treeCellRendererComponent;
  }

}
