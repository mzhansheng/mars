package com.seekon.mars.wf.client.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.text.Position;
import javax.swing.tree.TreeNode;

import com.seekon.mars.wf.model.Company;
import com.seekon.mars.wf.model.Org;
import com.seekon.mars.wf.model.Staff;

public class CheckBoxTreeNode implements Cloneable,Serializable {

  private static final long serialVersionUID = 1L;

  public final static int SINGLE_SELECTION = 0;//单选

  public final static int DIG_IN_SELECTION = 4;//多选

  protected int selectionMode; //选择模式

  protected boolean isChecked = false; //是否选择

  protected boolean isCheckEnable = false; //复选框是否可用
  
  protected boolean isCheckVisible = true;//复选框是否可见

  protected boolean isLeaf = false;

  protected boolean expanded; //是否展开

  protected Object userObject; //树节点绑定对象

  protected Vector children=new Vector();
  
  private List<Staff> selectBusinessList=new ArrayList<Staff>();//选择的业务对象列表

  protected TreeNode parent;

  public CheckBoxTreeNode() {
    this(null);
  }

  public CheckBoxTreeNode(Object userObject) {
    this(userObject, true, false);
    this.userObject = userObject;
  }

  public CheckBoxTreeNode(Object userObject, boolean allowsChildren, boolean isChecked) {
    this.isChecked = isChecked;
    setSelectionMode(DIG_IN_SELECTION);
  }

  public void setSelectionMode(int mode) {
    selectionMode = mode;
  }

  public int getSelectionMode() {
    return selectionMode;
  }
  
  public List getSelectBusinessList(){
    return selectBusinessList;
  }

  /**
   * 设置本节点及其子节点的选中状态
   */
  public void setChecked(List<Staff> selectBusinessList,boolean isChecked,boolean isChkEnable) {
    this.selectBusinessList=selectBusinessList;
    this.isChecked=isChecked;
    this.isCheckEnable = isChkEnable;
    setChildrenChecked(this,isChecked,isChkEnable);
  }

  public void setCheckEnable(boolean isChkEnable) {
    this.isCheckEnable = isChkEnable;
  }
  
  protected void setChildrenChecked(CheckBoxTreeNode curNode,boolean isChecked,boolean isChkEnable) {
    curNode.isChecked = isChecked;
    curNode.isCheckEnable = isChkEnable;
    if(curNode.isLeaf){
      Object busType=curNode.getUserObject(); 
      if(busType instanceof Staff){
        Staff staff=(Staff)busType;
        if(isChecked){
          if(!selectBusinessList.contains(staff))selectBusinessList.add(staff);
        }
        else{
          if(selectBusinessList.contains(staff))selectBusinessList.remove(staff);
        }
      }
    }
    else{
      if ((selectionMode == DIG_IN_SELECTION)) {
        Enumeration e = curNode.children();
        while (e.hasMoreElements()) {
          CheckBoxTreeNode node = (CheckBoxTreeNode) e.nextElement();
          node.isChecked=isChecked;
          node.isCheckEnable=isChkEnable;
          setChildrenChecked(node,isChecked,isChkEnable);
        }
      }
    }
    
  }

  public void setExpanded(boolean isExpanded) {
    this.expanded = isExpanded;

    if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
      Enumeration e = children.elements();
      while (e.hasMoreElements()) {
        CheckBoxTreeNode node = (CheckBoxTreeNode) e.nextElement();
        node.setExpanded(isExpanded);
      }
    }
  }

  public boolean isExpanded() {
    return expanded;
  }

  public boolean isChecked() {
    return isChecked;
  }
  
  public void setChecked(boolean checking) {
    isChecked=checking;
  }

  public boolean isCheckEnable() {
    return isCheckEnable;
  }

  public Object getUserObject() {
    return userObject;
  }
  
  public void setUserObject(Object obj){
    userObject=obj;
  }

  public boolean isLeaf() {
    return isLeaf;
  }

  public void setLeaf(boolean isLeaf) {
    this.isLeaf = isLeaf;
  }
  
  public boolean isCheckVisible() {
    return isCheckVisible;
  }

  
  public void setCheckVisible(boolean isCheckVisible) {
    this.isCheckVisible = isCheckVisible;
  }

  public String toString() {
    return userObject == null ? "" : userObject.toString();
  }

//  public boolean equals(Object obj) {
//    CheckBoxTreeNode other = (CheckBoxTreeNode) obj;
//    if (userObject != null && userObject.equals(other.getUserObject()))
//      return true;
//    else
//      return false;
//  }
  
  public Enumeration<CheckBoxTreeNode> children(){
    return children.elements();
  }
  
  public Vector getChildren(){
    return children;
  }
  
  public void add(CheckBoxTreeNode node){
    if(!children.contains(node))children.add(node);
  }
  
  public boolean equals(CheckBoxTreeNode obj) {
    Object userObj=this.getUserObject();
    Object curObj=obj.getUserObject();
    if (userObj instanceof Company && curObj instanceof Company) {
      return ((Company)userObj).equals(((Company)curObj));
    } else if (userObj instanceof Org && curObj instanceof Org) {
      return ((Org)userObj).equals(((Org)curObj));
    } else if (userObj instanceof Position && curObj instanceof Position) {
      return ((Position)userObj).equals(((Position)curObj));
    } else if(userObj instanceof Staff && curObj instanceof Staff){
      return ((Staff)userObj).equals(((Staff)curObj));
    }
    else{
      return true;
    }
  }
}
