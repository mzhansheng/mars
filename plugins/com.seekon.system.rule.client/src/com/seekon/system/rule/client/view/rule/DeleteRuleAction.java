package com.seekon.system.rule.client.view.rule;

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
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.model.Rule;

public class DeleteRuleAction extends AbstractTreeAction {

  private static final long serialVersionUID = 2761050300073450322L;

  private List<Rule> rules = new ArrayList<Rule>();

  public DeleteRuleAction(JTree tree) {
    super(tree);
    this.putValue(Action.NAME, "删除规则");
    this.setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);

    rules.clear();
    boolean enable = paths != null && paths.size() > 0;
    for (TreePath path : paths) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path
        .getLastPathComponent();
      Object userObject = treeNode.getUserObject();
      if (userObject instanceof Rule) {
        enable = true;
        rules.add((Rule) userObject);
      } else {
        enable = false;
        rules.clear();
        break;
      }
    }
    setEnabled(enable);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (JOptionPane.showConfirmDialog(tree, "确认删除选中的规则？", "请确认",
      JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
      UIUtilities.asyncInvoke(new DefaultInvokeHandler<List<Rule>>() {

        @Override
        public List<Rule> execute() throws Exception {
          ServiceFacade.getRuleService().deleteRuleList(rules);
          return rules;
        }

        @Override
        public void success(List<Rule> result) {
          ObserverUtil.notifyEvent(new Event(rules, RuleEventType.RULES_DELETED),
            tree);
          JOptionPane.showMessageDialog(tree, "删除成功！");
        }
      });

    }
  }

}
