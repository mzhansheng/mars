package com.seekon.system.org.client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.component.tree.TreePopupUtil;
import com.seekon.smartclient.component.viewedit.AbstractPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.org.client.tree.JOrgTree;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Enterprise;

public class OrgTreePage extends AbstractPage {

  private static final long serialVersionUID = -6744518516398770339L;

  private JOrgTree orgTree;

  public OrgTreePage() {
    super();

    orgTree = new JOrgTree();
    JScrollPane jsp = new JScrollPane(orgTree);

    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);

    TreePopupUtil.addPopup(orgTree, new OrgTreePopupMenu(orgTree));
    orgTree.addTreeSelectionListener(new OrgTreeSelectionBroadcaster());
  }

  @Override
  public Dimension getMinimumSize() {
    return new Dimension(240, 0);
  }

  @Override
  public void update(Event event) {
    DefaultTreeModel treeModel = (DefaultTreeModel) orgTree.getModel();
    EventType type = event.getEventType();
    if (type == OrgEventType.ENT_UPDATED || type == OrgEventType.DEPT_UPDATED) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) orgTree
        .getSelectionPath().getLastPathComponent();
      node.setUserObject(event.getSource());
      treeModel.reload(node);
    } else if (type == OrgEventType.ENT_ADDED || type == OrgEventType.DEPT_ADDED) {
      AbstractOrgItem orgItem = (AbstractOrgItem) event.getSource();
      DefaultMutableTreeNode newer = new DefaultMutableTreeNode(orgItem);

      DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) orgTree
        .getSelectionPath().getLastPathComponent();
      Enterprise parentEnt = (Enterprise) parentNode.getUserObject();
      parentEnt.addChildOrgItem(orgItem);

      treeModel.insertNodeInto(newer, parentNode, parentNode.getChildCount());
      orgTree.setSelectionPath(orgTree.getSelectionPath().pathByAddingChild(newer));
    } else if (type == OrgEventType.ENT_DELETE || type == OrgEventType.DEPT_DELETE) {
      TreePath[] selectionPaths = orgTree.getSelectionPaths();
      DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) selectionPaths[0]
        .getLastPathComponent()).getParent();
      Enterprise parent = (Enterprise) parentNode.getUserObject();
      for (TreePath treePath : selectionPaths) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
          .getLastPathComponent();
        parent.removeChildOrgItem((AbstractOrgItem) node.getUserObject());
        treeModel.removeNodeFromParent(node);
      }
      orgTree.setSelectionPath(new TreePath(parentNode.getPath()));
    }

  }
}
