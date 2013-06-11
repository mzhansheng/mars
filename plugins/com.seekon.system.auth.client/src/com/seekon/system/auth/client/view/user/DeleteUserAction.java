package com.seekon.system.auth.client.view.user;

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
import com.seekon.system.auth.model.User;

public class DeleteUserAction extends AbstractTreeAction {

  private static final long serialVersionUID = 3867211348961440253L;

  private List<User> users = new ArrayList<User>();

  public DeleteUserAction(JTree tree) {
    super(tree);
    putValue(Action.NAME, "删除用户");
    setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);
    users.clear();
    boolean enable = paths != null && paths.size() > 0;
    for (TreePath path : paths) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path
        .getLastPathComponent();
      Object userObject = treeNode.getUserObject();
      if (userObject instanceof User) {
        enable = true;
        User user = (User) userObject;
        users.add(user);
      } else {
        enable = false;
        users.clear();
        break;
      }
    }
    setEnabled(enable);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (JOptionPane.showConfirmDialog(tree, "确认删除选中用户？", "请确认",
      JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
      //System.out.println("delete user :" + users);
      UIUtilities.asyncInvoke(new DefaultInvokeHandler<Object>() {

        @Override
        public Object execute() throws Exception {
          ServiceFacade.getUserService().deleteUsers(users);
          return null;
        }

        @Override
        public void success(Object result) {
          ObserverUtil.notifyEvent(
            new Event(users, AuthorityEventType.USERS_DELETED), tree);
          JOptionPane.showMessageDialog(tree, "删除成功！");
        }
      });

    }
  }

}
