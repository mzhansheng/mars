package com.seekon.smartclient.gui.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.gui.model.TreeNodeMap;

public class MDefaultTreeModel extends DefaultTreeModel {

  private static final long serialVersionUID = 6058448768278247735L;

  private MDataModel mDataModel = new MDefaultDataModel();

  private boolean checkAndInitTreeNode = false;
  
  public MDefaultTreeModel() {
    super(new DefaultMutableTreeNode(new TreeNodeMap(new HashMap())));
  }

  public void setSqlid(String sqlid) {
    mDataModel.setSqlid(sqlid);
  }

  public String getSqlid() {
    return mDataModel.getSqlid();
  }

  public void setParams(String paramString) {
    mDataModel.setParams(paramString);
  }

  public void addParam(String name, Object value) {
    mDataModel.addParam(name, value);
  }

  @Override
  public void setRoot(TreeNode root) {
    this.root = root;
    this.refresh();
  }
  
  public void refresh() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<List<Map>>() {

      @Override
      public List<Map> execute() throws Exception {
        return (List<Map>) mDataModel.refresh();
      }

      @Override
      public void success(List<Map> result) {
        List<Map> rootChildren = new ArrayList<Map>();
        for (Map map : result) {
          rootChildren.add(new TreeNodeMap(map));
        }
        TreeNodeMap rootObj = (TreeNodeMap) ((DefaultMutableTreeNode) MDefaultTreeModel.this
          .getRoot()).getUserObject();
        rootObj.setChildren(rootChildren);
        
        MDefaultTreeModel.this.reload();
      }
    });

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
      TreeNodeMap userObj = (TreeNodeMap) parentNode.getUserObject();
      checkAndInitTreeNodeChildren(userObj);
      node = new DefaultMutableTreeNode(userObj.getChild(index));
      parentNode.add(node);
    }
    return node;
  }

  @Override
  public int getChildCount(Object parent) {   
    TreeNodeMap userObj = (TreeNodeMap) ((DefaultMutableTreeNode) parent)
      .getUserObject();
    if(root.equals(parent) &&
      (userObj == null || userObj.get("id") == null)){
      return 0;
    }
    
    if(checkAndInitTreeNode){
      checkAndInitTreeNodeChildren(userObj);
    }
    
    if(userObj == null){
      return 0;
    }
    return userObj.getChildCount();
  }

  @Override
  public boolean isLeaf(Object node) {
    return getChildCount(node) == 0;
  }

  private void checkAndInitTreeNodeChildren(TreeNodeMap userObj) {
    if (userObj.getChildren() == null) {
      this.addParam("parent_id", userObj.get("id"));
      List<Map> result = (List<Map>) MDefaultTreeModel.this.mDataModel.refresh();
      userObj.setChildren(new ArrayList<Map>());
      for (Map map : result) {
        userObj.addChild(new TreeNodeMap(map));
      }
    }
  }

}
