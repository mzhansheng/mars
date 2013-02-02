package com.seekon.system.auth.client.view.user.role;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.client.util.TreeUtil;
import com.seekon.system.auth.client.view.role.RoleTreeModel;
import com.seekon.system.auth.model.User;
import com.seekon.system.auth.spi.RoleService;

public class UserRolesDetail extends AbstractDetail<User> {
  private static final long serialVersionUID = -5785889942562542866L;

  private JTree userRolesTree;

  private User user;

  public UserRolesDetail() {
    userRolesTree = new JTree();
    JScrollPane jsp = new JScrollPane(userRolesTree);
    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);
  }

  private void setUser(final User user) {
    this.user = user;
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<TreeModel>() {

      @Override
      public TreeModel execute() throws Exception {
        RoleService roleService = ServiceFacade.getRoleService();
        return new RoleTreeModel(roleService.getCategory(), user.getRoleList());
      }

      @Override
      public void success(TreeModel result) {
        userRolesTree.setModel(result);

        TreeUtil.expandAll(userRolesTree, true);
      }

    });

  }

  @Override
  public User edit() {
    return user;
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.NEW_USER_SELECTED
      || event.getEventType() == AuthorityEventType.USER_ROLE_UPDATED) {
      setUser((User) event.getSource());
    }
  }

}
