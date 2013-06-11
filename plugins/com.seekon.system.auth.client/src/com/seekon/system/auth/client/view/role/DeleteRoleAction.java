package com.seekon.system.auth.client.view.role;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.model.Role;

public class DeleteRoleAction extends AbstractTreeAction {

  private static final long serialVersionUID = -6048402618727444969L;

  private List<Role> roles = new ArrayList<Role>();

  public DeleteRoleAction(JTree tree) {
    super(tree);
    putValue(Action.NAME, "删除角色");
    setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);
    roles.clear();
    boolean enable = paths != null && paths.size() > 0;
    for (TreePath path : paths) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path
        .getLastPathComponent();
      Object userObject = treeNode.getUserObject();
      if (userObject instanceof Role) {
        enable = true;
        Role role = (Role) userObject;
        roles.add(role);
      } else {
        enable = false;
        roles.clear();
        break;
      }
    }
    setEnabled(enable);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (JOptionPane.showConfirmDialog(tree, "确认删除选中角色？", "请确认",
      JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
      UIUtilities.asyncInvoke(new DefaultInvokeHandler<Object>() {
        @Override
        public Object execute() throws Exception {
          ServiceFacade.getRoleService().deleteRoles(roles);
          return null;
        }

        @Override
        public void success(Object result) {
          ObserverUtil.notifyEvent(
            new Event(roles, AuthorityEventType.ROLES_DELETED), tree);
          JOptionPane.showMessageDialog(tree, "删除成功！");
        }
      });

    }
  }

}
