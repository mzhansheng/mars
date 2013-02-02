package com.seekon.system.auth.client.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.tree.JCheckTree;
import com.seekon.smartclient.component.tree.JCheckTree.CheckState;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.model.DatascopeOwner;
import com.seekon.system.auth.spi.DataScopeService;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.client.view.ruleset.RuleTreeModel;
import com.seekon.system.rule.model.Rule;

public abstract class AbstractDatascopeForm extends AbstractForm<DatascopeOwner> {

  private static final long serialVersionUID = 2698650715862820136L;

  private JCheckTree datascopeTree;

  protected DatascopeOwner owner;

  public AbstractDatascopeForm() {
    super();
    initComponents();
  }

  protected void initComponents() {
    this.removeAll();
    this.setLayout(new BorderLayout());

    datascopeTree = new JCheckTree();
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<TreeModel>() {

      @Override
      public TreeModel execute() throws Exception {
        DataScopeService service = ServiceFacade.getDataScopeService();
        return new RuleTreeModel(service.getDateScopeCategory());
      }

      @Override
      public void success(TreeModel result) {
        datascopeTree.setModel(result);
      }
    });
    
    this.add(datascopeTree);
    this.setComplete(true);
    this.revalidate();
  }

  @Override
  public void update(Event event) {
    if(event.getEventType() == RuleEventType.RULE_ADDED){
      this.initComponents();
    }
  }
  
  @Override
  public void edit(DatascopeOwner owner) {
    TreePath[] checkedPath = datascopeTree.getCheckedPaths();
    for (TreePath path : checkedPath) {
      datascopeTree.setCheckeState(path, CheckState.UNCHECKED);
    }
    datascopeTree.setCheckeState(datascopeTree.getPathForRow(0),
      CheckState.UNCHECKED);

    this.owner = owner;
    if (owner == null || owner.getDatascopeRuleList() == null
      || owner.getDatascopeRuleList().isEmpty()) {
      return;
    }

    DefaultTreeModel treeModel = (DefaultTreeModel) datascopeTree.getModel();
    List<TreeNode> nodePath = new ArrayList<TreeNode>();
    setDatascopeTreeStateByUser(treeModel, nodePath,
      (DefaultMutableTreeNode) treeModel.getRoot());
  }

  public List<Rule> getSelectedDatascopeRuleList() {
    List<Rule> selectecRuleList = new ArrayList<Rule>();
    Object[] nodes = datascopeTree.getCheckedNodes();
    for (Object node : nodes) {
      Object userObject = ((DefaultMutableTreeNode) node).getUserObject();
      if (userObject instanceof Rule) {
        selectecRuleList.add((Rule) userObject);
      }
    }
    return selectecRuleList;
  }

  private void setDatascopeTreeStateByUser(DefaultTreeModel treeModel,
    List<TreeNode> nodePath, DefaultMutableTreeNode node) {

    nodePath.add(node);

    Object nodeObject = node.getUserObject();
    if (nodeObject instanceof Rule) {
      if (this.owner.getDatascopeRuleList().contains(nodeObject)) {
        this.datascopeTree.setCheckeState(new TreePath(nodePath.toArray()),
          CheckState.CHECKED);
      }
    }
    int count = treeModel.getChildCount(node);
    for (int i = 0; i < count; i++) {
      List<TreeNode> nodePath_1 = new ArrayList<TreeNode>();
      nodePath_1.addAll(nodePath);
      setDatascopeTreeStateByUser(treeModel, nodePath_1,
        (DefaultMutableTreeNode) treeModel.getChild(node, i));
    }
  }
}