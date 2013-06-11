package com.seekon.system.auth.client.view.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.seekon.system.auth.model.Role;
import com.seekon.system.common.model.Category;

public class RoleTreeModel extends DefaultTreeModel {

  private static final long serialVersionUID = -5949178557735099302L;

  private Map<Category, List<Role>> roleMap = new HashMap<Category, List<Role>>();

  public RoleTreeModel(Category roleCategory, List<Role> roleList) {
    super(new DefaultMutableTreeNode(roleCategory));
    buildRoleMap(roleList);
  }

  public void addRole(Role role) {
    List<Role> roleList = roleMap.get(role.getCategory());
    if (roleList == null) {
      roleList = new ArrayList<Role>();
    }
    if (!roleList.contains(role)) {
      roleList.add(role);
    }
    roleMap.put(role.getCategory(), roleList);
  }

  public void deleteRole(Role role) {
    List<Role> roleList = roleMap.get(role.getCategory());
    if (roleList == null || !roleList.contains(role)) {
      return;
    }
    roleList.remove(role);
    roleMap.put(role.getCategory(), roleList);
  }

  @Override
  public Object getChild(Object parent, int index) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parent;
    DefaultMutableTreeNode node = null;
    try {
      node = (DefaultMutableTreeNode) parentNode.getChildAt(index);
    } catch (Exception e) {
    }
    if (node == null) {
      Object userObj = parentNode.getUserObject();
      if (userObj instanceof Category) {
        Object child = null;
        if (((Category) userObj).getChildCount() > 0) {
          child = ((Category) userObj).getChild(index);
        } else {
          List<Role> roleList = roleMap.get(userObj);
          child = roleList.get(index);
        }
        node = new DefaultMutableTreeNode(child);
        parentNode.add(node);
      }
    }
    return node;
  }

  @Override
  public int getChildCount(Object parent) {
    int count = 0;
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parent;
    Object userObj = parentNode.getUserObject();
    if (userObj instanceof Category) {
      Category category = (Category) userObj;
      count = category.getChildCount();
      if (count == 0) {
        List<Role> roleList = roleMap.get(category);
        if (roleList != null) {
          count = roleList.size();
        }
      }
    }
    return count;
  }

  @Override
  public boolean isLeaf(Object node) {
    return getChildCount(node) == 0;
  }

  private void buildRoleMap(List<Role> roleList) {
    if (roleList == null || roleList.isEmpty()) {
      return;
    }
    for (Role role : roleList) {
      Category category = role.getCategory();
      List<Role> cateRoleList = roleMap.get(category);
      if (cateRoleList == null) {
        cateRoleList = new ArrayList<Role>();
      }
      if (!cateRoleList.contains(role)) {
        cateRoleList.add(role);
      }
      roleMap.put(category, cateRoleList);
    }
  }
}
