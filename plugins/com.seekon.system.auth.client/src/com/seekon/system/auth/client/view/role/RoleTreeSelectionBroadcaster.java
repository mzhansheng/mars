package com.seekon.system.auth.client.view.role;

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
import com.seekon.system.auth.model.Role;
import com.seekon.system.common.client.category.CategoryEventType;
import com.seekon.system.common.model.Category;

/**
 * 此处主要向Page界面广播
 * 
 * @author mdrtwell
 * 
 */
public class RoleTreeSelectionBroadcaster implements TreeSelectionListener {

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

      if (userObject instanceof Category) {
        broadcastRoleCategorySelected(userObject);
        return;
      }

      if (userObject instanceof Role) {
        broadcastRoleSelected(userObject);
        return;
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
      if (clazz == Role.class) {
        broadcastRolesSelected(userObjects);
        return;
      }
    }

  }

  private void broadcastRolesSelected(List userObjects) {
    ObserverUtil.notifyEvent(
      new Event(userObjects, AuthorityEventType.ROLES_SELECTED), tree);
  }

  private void broadcastRoleSelected(Object userObject) {
    ObserverUtil.notifyEvent(
      new Event(userObject, AuthorityEventType.ROLE_SELECTED), tree);
  }

  private void broadcastRoleCategorySelected(Object userObject) {
    ObserverUtil.notifyEvent(
      new Event(userObject, CategoryEventType.CATEGORY_SELECTED), tree);
  }

  private void broadcastRootSelected(Object userObject) {
    ObserverUtil.notifyEvent(
      new Event(userObject, AuthorityEventType.ROLE_ROOT_SELECTED), tree);
  }

  private void broadcaseNoneSelected() {
    ObserverUtil.notifyEvent(
      new Event("", AuthorityEventType.NONE_ROLE_SELECTIED), tree);
  }

}
