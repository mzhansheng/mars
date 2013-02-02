package com.seekon.system.auth.client.view.user;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.client.module.AuthResourceRegistry;

class UserTreePopupMenu extends JPopupMenu {

  private static final long serialVersionUID = 8130212857707125961L;

  private JTree tree;

  public UserTreePopupMenu(JTree tree) {
    this.tree = tree;
    initPopupMenu();
  }

  private void initPopupMenu() {
    Resource resource = AuthUtil
      .getUserAuthorizedResource(AuthResourceRegistry.KEY_USER_RESOURCE);
    if (AuthUtil.isActionAuthorizedToUser(resource, ActionHelper.getAddAction())) {
      CreateUserAction createUserAction = new CreateUserAction(tree);
      JMenuItem item = new JMenuItem(createUserAction);
      add(item);
    }
    if (AuthUtil.isActionAuthorizedToUser(resource, ActionHelper.getDeleteAction())) {
      DeleteUserAction deleteUserAction = new DeleteUserAction(tree);
      JMenuItem item = new JMenuItem(deleteUserAction);
      add(item);
    }
  }

}
