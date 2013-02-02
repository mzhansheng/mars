package com.seekon.system.auth.client.view.role.resource;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.util.TreeUtil;
import com.seekon.system.auth.model.Role;

public class RoleResourceDetail extends AbstractDetail<Role> {

  private static final long serialVersionUID = 3006056655444127115L;

  private JTree resourceTree;

  private Role role;

  public RoleResourceDetail() {
    super();
    this.resourceTree = new JTree();
    this.initCompoments();
  }

  protected void initCompoments() {
    this.resourceTree.setModel(new AuthResourceTreeModel(null));
    JScrollPane jsp = new JScrollPane(this.resourceTree);
    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);

    TreeUtil.expandAll(resourceTree, true);
  }

  @Override
  public Role edit() {
    return role;
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == AuthorityEventType.ROLE_SELECTED
      || event.getEventType() == AuthorityEventType.ROLE_RESOURCE_UPDATED) {
      role = ((Role) event.getSource());
      // this.resourceTree.setModel(new
      // AuthResourceTreeModel(role.getResource()));
      AuthResourceTreeModel treeModel = (AuthResourceTreeModel) this.resourceTree
        .getModel();
      treeModel.setRoot(new AuthResourceTreeNode(role.getResource()));
      treeModel.reload();

      TreeUtil.expandAll(resourceTree, true);
    }
  }

}
