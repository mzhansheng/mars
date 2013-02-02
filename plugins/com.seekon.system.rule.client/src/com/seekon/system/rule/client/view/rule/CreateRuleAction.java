package com.seekon.system.rule.client.view.rule;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.tree.AbstractTreeAction;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.client.view.RuleMainPage;
import com.seekon.system.rule.client.view.rule.wizard.CreateRuleWizard;
import com.seekon.system.rule.model.Rule;

public class CreateRuleAction extends AbstractTreeAction {

  private static final long serialVersionUID = -8631059617479376797L;

  private Category category;

  public CreateRuleAction(JTree tree) {
    super(tree);
    this.putValue(Action.NAME, "创建规则...");
    this.setEnabled(false);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    super.valueChanged(e);

    setEnabled(false);

    if (paths.isEmpty()) {
      setEnabled(false);
    } else {
      TreePath tp = paths.get(0);
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tp
        .getLastPathComponent();
      Object userObject = ((DefaultMutableTreeNode) tp.getLastPathComponent())
        .getUserObject();
      if (userObject instanceof Category) {
        if ((treeNode.getChildCount() == 0)
          || (treeNode.getChildCount() > 0 && ((DefaultMutableTreeNode) treeNode
            .getChildAt(0)).getUserObject() instanceof Rule)) {
          this.category = (Category) userObject;
          this.setEnabled(true);
        }
      } else {
        this.setEnabled(false);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    RuleMainPage ruleMainPage = (RuleMainPage) SwingUtilities.getAncestorOfClass(
      RuleMainPage.class, tree);
    ruleMainPage.getRuleSettingContainer().callback(
      new CreateRuleWizard(this.category));
  }

}
