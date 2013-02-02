package com.seekon.system.auth.client.view.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
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
import com.seekon.system.auth.model.User;
import com.seekon.system.org.client.tree.JOrgTree;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;

public class UserTreePage extends AbstractPage {

  private static final long serialVersionUID = -2322320325673257193L;

  private JOrgTree userTree = null;

  private OrgUserTreeModel userTreeModel = null;

  public UserTreePage() {
    initUserTree();

    JScrollPane jsp = new JScrollPane();
    jsp.setViewportView(userTree);

    setLayout(new BorderLayout());
    add(jsp, BorderLayout.CENTER);
  }

  private void initUserTree() {
    userTree = new JOrgTree();
    initUserTreeModel();

    TreeCellRenderer myRender = new UserTreeCellRenderer();
    userTree.setCellRenderer(myRender);

    userTree.addTreeSelectionListener(new UserTreeSelectionBroadcaster());

    userTree.setRootVisible(false);

    configPopup(userTree);
    TreeUtil.expandAll(userTree, true);
  }

  private void initUserTreeModel() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<List<User>>() {

      @Override
      public List<User> execute() throws Exception {
        return ServiceFacade.getUserService().getUserList();
      }

      @Override
      public void success(List<User> result) {
        Enterprise root = new Enterprise();
        Department other = new Department();
        other.setId("-999");
        other.setType("008");
        other.setCode("other");
        other.setName("组织外人员");

        Enterprise ent = new Enterprise();
        ent.setName("组织机构");

        List<AbstractOrgItem> rootChildOrgItems = new ArrayList<AbstractOrgItem>();
        rootChildOrgItems.add(ent);
        rootChildOrgItems.add(other);

        root.setChildOrgItemList(rootChildOrgItems);

        userTreeModel = new OrgUserTreeModel(root, result);
        userTree.setModel(userTreeModel);
      }
    });
  }

  private void configPopup(JTree userTree) {
    UserTreePopupMenu popupMenu = new UserTreePopupMenu(userTree);
    TreePopupUtil.addPopup(userTree, popupMenu);
  }

  @Override
  public Dimension getMinimumSize() {
    return Constants.SPLITPANE_LEFT_MINISIZE;
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == AuthorityEventType.NEW_USER_ADDED) {
      User userAdded = (User) event.getSource();

      DefaultMutableTreeNode parentNode = null;
      DefaultMutableTreeNode newer = new DefaultMutableTreeNode(userAdded);
      AbstractOrgItem category = userAdded.getBelongedOrgItem();
      if (category == null) {
        parentNode = (DefaultMutableTreeNode) userTreeModel.getRoot();
      } else {
        parentNode = (DefaultMutableTreeNode) userTree.getSelectionPath()
          .getLastPathComponent();
      }

      userTreeModel.addUser(userAdded);
      userTreeModel.insertNodeInto(newer, parentNode, parentNode.getChildCount());
      userTree.setSelectionPath(new TreePath(newer.getPath()));

    } else if (type == AuthorityEventType.USERS_DELETED) {
      TreePath[] selectionPaths = userTree.getSelectionPaths();
      DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) selectionPaths[0]
        .getLastPathComponent()).getParent();
      for (TreePath treePath : selectionPaths) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
          .getLastPathComponent();
        User userObj = (User) node.getUserObject();
        userTreeModel.deleteUser(userObj);
        userTreeModel.removeNodeFromParent(node);
      }
      userTree.setSelectionPath(new TreePath(parentNode.getPath()));
    } else if (type == AuthorityEventType.USER_BASIC_INFO_UPDATED
      || type == AuthorityEventType.USER_ROLE_UPDATED) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) userTree
        .getSelectionPath().getLastPathComponent();
      node.setUserObject((User) event.getSource());
      userTreeModel.reload(node);
    }
  }
}
