package com.seekon.system.auth.client.view.user;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.model.User;

/**
 * 此处主要向Page界面广播
 * 
 * @author mdrtwell
 * 
 */
public class UserTreeSelectionBroadcaster implements TreeSelectionListener {

  private JTree tree;

  @Override
  public void valueChanged(TreeSelectionEvent e) {

    tree = (JTree) e.getSource();
    TreePath[] tps = tree.getSelectionPaths();

    if (tps == null) {
      broadcaseNoneSelected();
      return;
    }

    if (tps.length == 1) {
      TreePath tp = tps[0];

      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tp
        .getLastPathComponent();
      Object userObject = treeNode.getUserObject();

      if (tree.getModel().getRoot() == treeNode) {
        broadcastRootSelected(userObject);
        return;
      }

      if (userObject instanceof User) {
        broadcastUserSelected(userObject);
        return;
      } else {
        broadcastUserCategorySelected(userObject);
      }
    }

    if (tps.length > 1) {
      List userObjects = new ArrayList();
      Class clazz = ((DefaultMutableTreeNode) tps[1].getLastPathComponent())
        .getUserObject().getClass();
      for (int i = 0; i < tps.length; i++) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tps[i]
          .getLastPathComponent();
        Object userObject = node.getUserObject();
        if (userObject.getClass() == clazz) {
          userObjects.add(userObject);
        } else {
          broadcaseNoneSelected();
          return;
        }
      }
      if (clazz == User.class) {
        broadcastUsersSelected(userObjects);
        return;
      }
    }

  }

  private void broadcastUsersSelected(List userObjects) {
    ObserverUtil.notifyEvent(new Event(userObjects,
      AuthorityEventType.USERS_SELECTED), tree);
  }

  private void broadcastUserSelected(Object userObject) {
    ObserverUtil.notifyEvent(new Event(userObject,
      AuthorityEventType.NEW_USER_SELECTED), tree);
  }

  private void broadcastUserCategorySelected(Object userObject) {
    ObserverUtil.notifyEvent(new Event(userObject,
      AuthorityEventType.USER_CATEGORY_SELECTED), tree);
  }

  private void broadcastRootSelected(Object userObject) {
    ObserverUtil.notifyEvent(new Event(userObject,
      AuthorityEventType.USER_ROOT_SELECTED), tree);
  }

  private void broadcaseNoneSelected() {
    ObserverUtil.notifyEvent(new Event("", AuthorityEventType.NONE_USER_SELECTIED),
      tree);
  }

}
