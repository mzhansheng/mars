package com.seekon.system.auth.client.view.role;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.tree.TreePopupUtil;
import com.seekon.smartclient.component.viewedit.AbstractPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.Constants;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.client.util.TreeUtil;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.spi.RoleService;
import com.seekon.system.common.client.category.CategoryEventType;
import com.seekon.system.common.model.Category;

public class RoleTreePage extends AbstractPage {

  private static final long serialVersionUID = 4639555875201388368L;

  private RoleTreeModel roleTreeModel;

  private JTree roleTree;

  private RolePage rolePage;

  public RoleTreePage(RolePage rolePage) {

    this.rolePage = rolePage;

    roleTree = new JTree();
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<TreeModel>() {

      @Override
      public TreeModel execute() throws Exception {
        return newRoleTreeModel();
      }

      @Override
      public void success(TreeModel result) {
        roleTreeModel = (RoleTreeModel) result;
        roleTree.setModel(roleTreeModel);
      }
    });

    TreeCellRenderer myRender = new RoleTreeCellRenderer();
    roleTree.setCellRenderer(myRender);
    roleTree.addTreeSelectionListener(new RoleTreeSelectionBroadcaster());

    configPopup(roleTree);

    TreeUtil.expandAll(roleTree, true);

    JScrollPane jsp = new JScrollPane(roleTree);

    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);
  }

  private void configPopup(JTree tree) {
    RoleTreePopupMenu popupMenu = new RoleTreePopupMenu(tree, rolePage);
    TreePopupUtil.addPopup(tree, popupMenu);
  }

  public static DefaultTreeModel newRoleTreeModel() {
    RoleService roleService = ServiceFacade.getRoleService();
    return new RoleTreeModel(roleService.getCategory(), roleService.getRoleList());
  }

  @Override
  public Dimension getMinimumSize() {
    return Constants.SPLITPANE_LEFT_MINISIZE;
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == AuthorityEventType.ROLE_ADDED
      || type == CategoryEventType.CATEGORY_ADDED) {
      Object userObj = event.getSource();

      DefaultMutableTreeNode newer = new DefaultMutableTreeNode(userObj);

      DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) roleTree
        .getSelectionPath().getLastPathComponent();
      Category parent = (Category) parentNode.getUserObject();
      if (userObj instanceof Category) {
        parent.addChild((Category) userObj);
      } else {
        roleTreeModel.addRole((Role) userObj);
      }

      roleTreeModel.insertNodeInto(newer, parentNode, parentNode.getChildCount());
      roleTree
        .setSelectionPath(roleTree.getSelectionPath().pathByAddingChild(newer));
    } else if (type == AuthorityEventType.ROLE_BASIC_INFO_UPDATED) {
      Role source = (Role) event.getSource();
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) roleTree
        .getSelectionPath().getLastPathComponent();
      node.setUserObject(source);
      roleTreeModel.reload(node);
    } else if (type == AuthorityEventType.ROLES_DELETED
      || type == CategoryEventType.CATEGORY_DELETED) {
      TreePath[] selectionPaths = roleTree.getSelectionPaths();
      DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) ((MutableTreeNode) selectionPaths[0]
        .getLastPathComponent()).getParent();
      Category parent = (Category) parentNode.getUserObject();
      for (TreePath treePath : selectionPaths) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
          .getLastPathComponent();
        Object userObj = node.getUserObject();
        if (userObj instanceof Category) {
          parent.removeChild((Category) userObj);
        } else {
          roleTreeModel.deleteRole((Role) userObj);
        }
        roleTreeModel.removeNodeFromParent(node);
      }
      roleTree.setSelectionPath(new TreePath(parentNode.getPath()));
    } else if (type == CategoryEventType.CATEGORY_UPDATED) {
      Category source = (Category) event.getSource();
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) roleTree
        .getSelectionPath().getLastPathComponent();
      node.setUserObject(source);
      roleTreeModel.reload(node);
    }
  }
}
