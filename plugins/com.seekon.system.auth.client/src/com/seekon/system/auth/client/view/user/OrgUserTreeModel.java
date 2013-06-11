package com.seekon.system.auth.client.view.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.seekon.system.auth.model.User;
import com.seekon.system.org.client.tree.OrgTreeModel;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Enterprise;

public class OrgUserTreeModel extends OrgTreeModel {

  private static final long serialVersionUID = 8896547956115723626L;

  private Map<AbstractOrgItem, List<User>> userMap = new HashMap<AbstractOrgItem, List<User>>();

  public OrgUserTreeModel(Enterprise orgItem, List<User> userList) {
    super(orgItem);

    if (userList != null) {
      this.buildUserMap(userList);
    }
  }

  public void addUser(User user) {
    List<User> userList = userMap.get(user.getBelongedOrgItem());
    if (userList == null) {
      userList = new ArrayList<User>();
    }
    if (!userList.contains(user)) {
      userList.add(user);
    }
    userMap.put(user.getBelongedOrgItem(), userList);
  }

  public void deleteUser(User user) {
    List<User> userList = userMap.get(user.getBelongedOrgItem());
    if (userList == null || !userList.contains(user)) {
      return;
    }
    userList.remove(user);
    userMap.put(user.getBelongedOrgItem(), userList);
  }

  private void buildUserMap(List<User> userList) {
    userMap.clear();
    for (User user : userList) {
      AbstractOrgItem orgItem = user.getBelongedOrgItem();
      List<User> entryUserList = userMap.get(orgItem);
      if (entryUserList == null) {
        entryUserList = new ArrayList<User>();
      }
      if (!entryUserList.contains(user)) {
        entryUserList.add(user);
      }
      userMap.put(orgItem, entryUserList);
    }
  }

  @Override
  public Object getChild(Object parent, int index) {
    Object obj = super.getChild(parent, index);
    if (obj == null) {
      try {
        obj = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) parent)
          .getChildAt(index);
      } catch (Exception e) {
      }
      if (obj == null) {
        Object orgItem = ((DefaultMutableTreeNode) parent).getUserObject();
        if (orgItem instanceof AbstractOrgItem) {
          List<User> userList = userMap.get(orgItem);
          if (userList != null) {
            obj = new DefaultMutableTreeNode(userList.get(index));
            ((DefaultMutableTreeNode) parent).add((MutableTreeNode) obj);
          }
        }
      }
    }
    return obj;
  }

  @Override
  public int getChildCount(Object parent) {
    int count = super.getChildCount(parent);
    if (count == 0) {
      Object orgItem = ((DefaultMutableTreeNode) parent).getUserObject();
      if (orgItem instanceof AbstractOrgItem) {
        List<User> userList = userMap.get(orgItem);
        if (userList != null) {
          count = userList.size();
        }
      }
    }
    return count;
  }

  @Override
  public boolean isLeaf(Object node) {
    return super.isLeaf(node);
  }
}
