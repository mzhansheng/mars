package com.seekon.system.auth.client.view.role;

import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Action;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.spi.RoleService;
import com.seekon.system.common.client.category.CreateCategoryAction;
import com.seekon.system.common.client.category.DeleteCategoryAction;

class RoleTreePopupMenu extends JPopupMenu {

  private static final long serialVersionUID = -1806357381372226189L;

  private JTree tree;

  public RoleTreePopupMenu(JTree tree, RolePage rolePage) {
    this.tree = tree;
    initPopupMenu(rolePage);
  }

  private void initPopupMenu(RolePage rolePage) {
    List<AbstractResource> children = rolePage.getResource().getChildren();
    boolean enableAdd = false;
    boolean enableDelete = false;
    Action addAction = ActionHelper.getAddAction();
    Action delAction = ActionHelper.getDeleteAction();
    for (AbstractResource child : children) {
      if (child instanceof Action) {
        if (child.getId().equals(addAction.getId())) {
          enableAdd = true;
        } else if (child.getId().contains(delAction.getId())) {
          enableDelete = true;
        }
      }
    }

    JMenuItem item = null;
    RoleService service = ServiceFacade.getRoleService();
    if (enableAdd) {
      CreateCategoryAction createCategoryAction = new CreateCategoryAction(tree,
        rolePage.getRoleDisplayer(), service);
      item = new JMenuItem(createCategoryAction);
      add(item);
    }

    if (enableDelete) {
      item = new JMenuItem(new DeleteCategoryAction(tree, service));
      add(item);
    }

    addASeparator();

    if (enableAdd) {
      CreateRoleAction createRoleAction = new CreateRoleAction(tree);
      item = new JMenuItem(createRoleAction);
      add(item);
    }

    if (enableDelete) {
      DeleteRoleAction deleteRoleAction = new DeleteRoleAction(tree);
      item = new JMenuItem(deleteRoleAction);
      add(item);
    }
  }

  private void addASeparator() {
    JSeparator separator = new JSeparator();
    add(separator);
  }

}
