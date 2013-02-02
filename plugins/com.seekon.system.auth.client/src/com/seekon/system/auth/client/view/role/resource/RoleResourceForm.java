package com.seekon.system.auth.client.view.role.resource;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.tree.JCheckTree;
import com.seekon.smartclient.component.tree.JCheckTree.CheckState;
import com.seekon.smartclient.component.tree.TreeCheckEvent;
import com.seekon.smartclient.component.tree.TreeCheckListener;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.smartclient.module.ResourceFacade;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.model.ResourceWrapper;
import com.seekon.system.auth.model.Role;

public class RoleResourceForm extends AbstractForm<Role> {

  private static final long serialVersionUID = -2506213759091929886L;

  private JCheckTree resourceTree;

  private Role role;

  public RoleResourceForm() {
    super();
    this.initComponents();
  }

  protected void initComponents() {
    this.resourceTree = new JCheckTree();
    this.resourceTree.setModel(new AuthResourceTreeModel(new ResourceWrapper(
      ResourceFacade.getRegistriedResource())));
    this.resourceTree.addTreeCheckListener(new ResourceTreeCheckListener());

    JScrollPane jsp = new JScrollPane(this.resourceTree);
    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);

    setComplete(true);
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.ROLE_SELECTED) {

    }
  }

  @Override
  public void edit(Role t) {
    TreePath[] checkedPath = resourceTree.getCheckedPaths();
    for (TreePath path : checkedPath) {
      resourceTree.setCheckeState(path, CheckState.UNCHECKED);
    }
    resourceTree.setCheckeState(resourceTree.getPathForRow(0), CheckState.UNCHECKED);

    this.role = t;

    if (this.role == null || this.role.getResource() == null) {
      return;
    }

    AuthResourceTreeModel treeModel = (AuthResourceTreeModel) this.resourceTree
      .getModel();
    List<TreeNode> nodePath = new ArrayList<TreeNode>();
    setTreeStateByAssignedResource(nodePath, null,
      (DefaultMutableTreeNode) treeModel.getRoot());
  }

  @Override
  public Role save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Role>() {

      @Override
      public Role execute() throws Exception {
        boolean update = role.getResource() != null;

        role.setResource(getAssignedResource());

        if (update) {
          ServiceFacade.getRoleService().updateRoleResource(role);
        } else {
          role = ServiceFacade.getRoleService().addRoleResource(role);
        }
        return role;
      }

      @Override
      public void success(Role role) {
        JOptionPane.showMessageDialog(RoleResourceForm.this, "保存成功！");
        ObserverUtil.notifyEvent(
          new Event(role, AuthorityEventType.ROLE_RESOURCE_UPDATED), RoleResourceForm.this);
      }
    });

    return role;
  }

  public ResourceWrapper getAssignedResource() {
    ResourceWrapper root = (ResourceWrapper) ((DefaultMutableTreeNode) resourceTree
      .getModel().getRoot()).getUserObject();
    try {
      ResourceWrapper newResource = (ResourceWrapper) root.clone();
      newResource.cleanUnAssignedResource();
      return newResource;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void setTreeStateByAssignedResource(List<TreeNode> nodePath,
    DefaultMutableTreeNode parent, DefaultMutableTreeNode node) {
    nodePath.add(node);

    ResourceWrapper nodeObject = (ResourceWrapper) node.getUserObject();
    if (!this.role.getResource().contains(nodeObject)) {//直接返回
      return;
    }

    if (node.getChildCount() == 0) {//包含而且为末级节点，设置勾选状态
      if (parent != null) {
        ResourceWrapper parentObj = (ResourceWrapper) parent.getUserObject();
        if (!nodeObject.getResource().getParent().equals(parentObj.getResource())) {
          return;
        }
      }
      this.resourceTree.setCheckeState(new TreePath(nodePath.toArray()),
        CheckState.CHECKED);
      return;
    }

    int count = node.getChildCount();
    for (int i = 0; i < count; i++) {
      List<TreeNode> nodePath_1 = new ArrayList<TreeNode>();
      nodePath_1.addAll(nodePath);
      setTreeStateByAssignedResource(nodePath_1, node,
        (DefaultMutableTreeNode) node.getChildAt(i));
    }

  }

  class ResourceTreeCheckListener implements TreeCheckListener {

    @Override
    public void checkStateChanged(TreeCheckEvent e) {
      ResourceWrapper nodeResource = (ResourceWrapper) ((DefaultMutableTreeNode) e
        .getNode()).getUserObject();

      CheckState newState = e.getNewState();
      if (CheckState.UNCHECKED != newState) {
        nodeResource.setAssigned(true);
      } else {
        nodeResource.setAssigned(false);
      }
    }
  }
}
