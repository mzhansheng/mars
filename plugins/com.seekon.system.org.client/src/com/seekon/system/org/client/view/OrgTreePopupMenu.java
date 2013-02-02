package com.seekon.system.org.client.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;

import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.org.client.module.OrgResourceRegistry;
import com.seekon.system.org.client.view.dept.CreateDepartmentAction;
import com.seekon.system.org.client.view.dept.DeleteDepartmentAction;
import com.seekon.system.org.client.view.ent.CreateEnterpriseAction;
import com.seekon.system.org.client.view.ent.DeleteEnterpriseAction;

public class OrgTreePopupMenu extends JPopupMenu {

  private static final long serialVersionUID = 3694434974347256544L;

  private JTree tree;

  public OrgTreePopupMenu(JTree tree) {
    super();
    this.tree = tree;

    initPopupMenu();
  }

  private void initPopupMenu() {
    Resource resource = AuthUtil
      .getUserAuthorizedResource(OrgResourceRegistry.KEY_ORG_RESOURCE);
    boolean add = AuthUtil.isActionAuthorizedToUser(resource,
      ActionHelper.getAddAction());
    boolean delete = AuthUtil.isActionAuthorizedToUser(resource,
      ActionHelper.getDeleteAction());
    int count = 0;
    if (add) {
      CreateEnterpriseAction createEntAction = new CreateEnterpriseAction(tree);
      JMenuItem item = new JMenuItem(createEntAction);
      add(item);
      count++;
    }
    if (delete) {
      DeleteEnterpriseAction deleteEntAction = new DeleteEnterpriseAction(tree);
      JMenuItem item = new JMenuItem(deleteEntAction);
      add(item);
      count++;
    }

    if (count > 0) {
      this.add(new JSeparator());
    }
    if (add) {
      CreateDepartmentAction createDepartmentAction = new CreateDepartmentAction(
        tree);
      JMenuItem item = new JMenuItem(createDepartmentAction);
      add(item);
    }
    if (delete) {
      DeleteDepartmentAction deleteDepartmentAction = new DeleteDepartmentAction(
        tree);
      JMenuItem item = new JMenuItem(deleteDepartmentAction);
      add(item);
    }
  }
}
