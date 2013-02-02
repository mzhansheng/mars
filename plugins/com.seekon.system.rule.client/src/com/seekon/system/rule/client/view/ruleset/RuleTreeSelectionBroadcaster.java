package com.seekon.system.rule.client.view.ruleset;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.common.client.category.CategoryEventType;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.model.Rule;

public class RuleTreeSelectionBroadcaster implements TreeSelectionListener {

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    JTree tree = (JTree) e.getSource();
    TreePath[] tps = tree.getSelectionPaths();
    if (tps == null || tps.length == 0) {
      broadcastRuleEvent("", RuleEventType.NONE_SELECTED, tree);
    } else {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tps[tps.length - 1]
        .getLastPathComponent();//最后一个选择的节点
      Object userObj = treeNode.getUserObject();
      if (userObj instanceof Category) {
        broadcastRuleEvent(userObj, CategoryEventType.CATEGORY_SELECTED, tree);
      } else if (userObj instanceof Rule) {
        broadcastRuleEvent(userObj, RuleEventType.RULE_SELECTED, tree);
      } else {
        //nothing.
      }
    }
  }

  private void broadcastRuleEvent(Object source, EventType type, Component compo) {
    ObserverUtil.notifyEvent(new Event(source, type), compo);
  }
}
