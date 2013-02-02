package com.seekon.system.auth.client.view.user.role;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
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
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.client.util.TreeUtil;
import com.seekon.system.auth.client.view.role.RoleTreePage;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.model.User;

public class UserRolesForm extends AbstractForm<User> {

  private static final long serialVersionUID = 6281119252241720910L;

  private JCheckTree roleTree;

  private User user;

  public UserRolesForm() {
    reload();
  }

  public void reload(){
    this.removeAll();
    
    roleTree = new JCheckTree();
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<TreeModel>() {

      @Override
      public TreeModel execute() throws Exception {
        return RoleTreePage.newRoleTreeModel();
      }

      @Override
      public void success(TreeModel result) {
        roleTree.setModel(result);
        TreeUtil.expandAll(roleTree, true);
      }
    });

    JScrollPane jsp = new JScrollPane(roleTree);
    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);

    setComplete(true);
    this.revalidate();
  }
  
  @Override
  public void update(Event event) {
    if(event.getEventType().equals(AuthorityEventType.ROLE_ADDED)){
      reload();
    }
  }
  
  @Override
  public void edit(User user) {
    TreePath[] checkedPath = roleTree.getCheckedPaths();
    for (TreePath path : checkedPath) {
      roleTree.setCheckeState(path, CheckState.UNCHECKED);
    }
    roleTree.setCheckeState(roleTree.getPathForRow(0), CheckState.UNCHECKED);

    this.user = user;
    if (user == null || user.getRoleList() == null || user.getRoleList().isEmpty()) {
      return;
    }

    DefaultTreeModel treeModel = (DefaultTreeModel) roleTree.getModel();
    List<TreeNode> nodePath = new ArrayList<TreeNode>();
    setRoleTreeStateByUser(treeModel, nodePath,
      (DefaultMutableTreeNode) treeModel.getRoot());
  }

  @Override
  public User save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<User>() {

      @Override
      public User execute() throws Exception {
        List<Role> selectecRoleList = getUserSelectedRoleList();
        user.setRoleList(selectecRoleList);
        ServiceFacade.getUserService().updateUserRole(user);
        return user;
      }

      @Override
      public void success(User result) {
        ObserverUtil.notifyEvent(
          new Event(result, AuthorityEventType.USER_ROLE_UPDATED), UserRolesForm.this);
      }
    });

    return user;
  }

  public List<Role> getUserSelectedRoleList() {
    List<Role> selectecRoleList = new ArrayList<Role>();
    Object[] nodes = roleTree.getCheckedNodes();
    for (Object node : nodes) {
      Object userObject = ((DefaultMutableTreeNode) node).getUserObject();
      if (userObject instanceof Role) {
        selectecRoleList.add((Role) userObject);
      }
    }
    return selectecRoleList;
  }

  private void setRoleTreeStateByUser(DefaultTreeModel treeModel,
    List<TreeNode> nodePath, DefaultMutableTreeNode node) {

    nodePath.add(node);

    Object nodeObject = node.getUserObject();
    if (nodeObject instanceof Role) {
      if (this.user.getRoleList().contains(nodeObject)) {
        this.roleTree.setCheckeState(new TreePath(nodePath.toArray()),
          CheckState.CHECKED);
      }
    }
    int count = treeModel.getChildCount(node);
    for (int i = 0; i < count; i++) {
      List<TreeNode> nodePath_1 = new ArrayList<TreeNode>();
      nodePath_1.addAll(nodePath);
      setRoleTreeStateByUser(treeModel, nodePath_1,
        (DefaultMutableTreeNode) treeModel.getChild(node, i));
    }
  }
}
