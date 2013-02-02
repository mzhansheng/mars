package com.seekon.system.org.client.view;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;

public class OrgTreeSelectionBroadcaster implements TreeSelectionListener {

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    JTree tree = (JTree) e.getSource();
    TreePath[] tps = tree.getSelectionPaths();
    if (tps == null || tps.length == 0) {
      broadcastRuleEvent("", OrgEventType.NONE_SELECTED, tree);
    } else {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tps[tps.length - 1]
        .getLastPathComponent();//最后一个选择的节点
      Object userObj = treeNode.getUserObject();
      if (userObj instanceof Enterprise) {
        if (((Enterprise) userObj).getId() == null) {
          broadcastRuleEvent(userObj, OrgEventType.ROOT_SELECTED, tree);
        } else {
          broadcastRuleEvent(userObj, OrgEventType.ENT_SELECTED, tree);
        }
      } else if (userObj instanceof Department) {
        broadcastRuleEvent(userObj, OrgEventType.DEPT_SELECTED, tree);
      } else {
        //nothing.
      }
    }
  }

  private void broadcastRuleEvent(Object source, EventType type, JComponent component) {
    ObserverUtil.notifyEvent(new Event(source, type), component);
  }
}
