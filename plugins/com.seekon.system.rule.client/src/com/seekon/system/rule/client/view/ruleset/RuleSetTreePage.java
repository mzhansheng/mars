package com.seekon.system.rule.client.view.ruleset;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.tree.TreePopupUtil;
import com.seekon.smartclient.component.viewedit.AbstractPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.common.client.category.CategoryEventType;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.client.view.RuleMainPage;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.spi.RuleService;

public class RuleSetTreePage extends AbstractPage {

  private static final long serialVersionUID = -791611703566669076L;

  protected JTree tree;

  @Override
  public Dimension getMinimumSize() {
    return new Dimension(240, 0);
  }

  public RuleSetTreePage(final Category ruleCategory, RuleMainPage ruleMainPage) {

    setLayout(new BorderLayout(0, 0));

    JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);

    tree = new JTree();

    UIUtilities.asyncInvoke(new DefaultInvokeHandler<TreeModel>() {

      @Override
      public TreeModel execute() throws Exception {
        RuleService service = ServiceFacade.getRuleService();
        return new RuleTreeModel(ruleCategory, service.getRuleList());
      }

      @Override
      public void success(TreeModel result) {
        tree.setModel(result);
      }
    });

    scrollPane.setViewportView(tree);

    TreePopupUtil.addPopup(tree, getTreePopupMenu(ruleMainPage));
    tree.addTreeSelectionListener(new RuleTreeSelectionBroadcaster());
  }

  protected JPopupMenu getTreePopupMenu(RuleMainPage container) {
    return new RuleTreePopupMenu(tree, container);
  }

  @Override
  public void update(Event event) {
    RuleTreeModel ruleTreeModel = (RuleTreeModel) tree.getModel();
    EventType type = event.getEventType();
    if (type == RuleEventType.RULE_BASIC_INFO_UPDATED
      || type == CategoryEventType.CATEGORY_UPDATED) {
      updateTreeNode(event.getSource(), ruleTreeModel);
    } else if (type == RuleEventType.RULE_ADDED
      || type == CategoryEventType.CATEGORY_ADDED) {
      addRuleTreeNode(event.getSource(), ruleTreeModel);
    } else if (type == RuleEventType.RULES_DELETED
      || type == CategoryEventType.CATEGORY_DELETED) {
      deleteRuleTreeNode(ruleTreeModel);
    }
  }

  protected void updateTreeNode(Object userObj, DefaultTreeModel treeModel) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath()
      .getLastPathComponent();
    node.setUserObject(userObj);
    treeModel.reload(node);
  }

  protected void addRuleTreeNode(Object userObj, RuleTreeModel treeModel) {
    TreePath selectionPath = tree.getSelectionPath();
    if (selectionPath == null) {
      return;
    }

    DefaultMutableTreeNode newer = new DefaultMutableTreeNode(userObj);
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) tree
      .getSelectionPath().getLastPathComponent();
    Category parent = (Category) parentNode.getUserObject();
    if (userObj instanceof Category) {
      parent.addChild((Category) userObj);
    } else {
      treeModel.addRule((Rule) userObj);
    }

    treeModel.insertNodeInto(newer, parentNode, parentNode.getChildCount());
    tree.setSelectionPath(tree.getSelectionPath().pathByAddingChild(newer));
  }

  protected void deleteRuleTreeNode(RuleTreeModel ruleTreeModel) {
    TreePath[] selectionPaths = tree.getSelectionPaths();
    if (selectionPaths == null || selectionPaths.length == 0) {
      return;
    }

    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) selectionPaths[0]
      .getLastPathComponent()).getParent();
    Category parent = (Category) parentNode.getUserObject();
    for (TreePath treePath : selectionPaths) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
        .getLastPathComponent();
      Object userObj = node.getUserObject();
      if (userObj instanceof Category) {
        parent.removeChild((Category) userObj);
      } else {
        ruleTreeModel.deleteRule((Rule) userObj);
      }
      ruleTreeModel.removeNodeFromParent(node);
    }
    tree.setSelectionPath(new TreePath(parentNode.getPath()));
  }
}
