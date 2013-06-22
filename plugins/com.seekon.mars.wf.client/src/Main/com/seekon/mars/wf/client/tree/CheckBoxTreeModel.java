package com.seekon.mars.wf.client.tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.seekon.mars.wf.model.Company;
import com.seekon.mars.wf.model.Org;
import com.seekon.mars.wf.model.Position;
import com.seekon.mars.wf.model.Staff;

public abstract class CheckBoxTreeModel implements TreeModel {
  protected final Map<CheckBoxTreeNode, CheckBoxTreeNode[]> buffer = new HashMap<CheckBoxTreeNode, CheckBoxTreeNode[]>();

  private List<Staff> selectBusinessList;
  
  protected CheckBoxTreeNode root;

  protected CheckBoxTreeNode curNode;

  public CheckBoxTreeModel(CheckBoxTreeNode root) {
    this.root = root;
  }

  public CheckBoxTreeModel() {
  }

  public CheckBoxTreeNode getCurNode() {
    return curNode;
  }

  public void setCurNode(CheckBoxTreeNode curNode) {
    this.curNode = curNode;
  }

  public void processCheckedNode(CheckBoxTreeNode parent, CheckBoxTreeNode[] ts) {
    ts = buffer.get(parent);
    if (ts == null || (ts != null && ts.length == 0)) {
      ts = (CheckBoxTreeNode[]) gets(parent);
      if (ts != null && ts.length > 0) {
        buffer.put(parent, ts);
        for (CheckBoxTreeNode node : ts) {
          parent.add(node);
        }
        parent.isLeaf = false;
        if (parent.isLeaf == false && parent.isChecked == true) {
          for (int i = 0; i < ts.length; i++) {
            CheckBoxTreeNode tmpChileNode = ts[i];
            processCheckedNode(tmpChileNode, ts);
          }
        }
      } else {
        parent.isLeaf = true;
      }
    } else {
      if (parent.isChecked) {
        for (int i = 0; i < ts.length; i++) {
          CheckBoxTreeNode tmpChileNode = ts[i];
          tmpChileNode.setChecked(true);
          processCheckedNode(tmpChileNode, ts);
        }
      }
    }
  }

  public void processCheckedNode(List<Staff> selectBusinessList, CheckBoxTreeNode parent,
    CheckBoxTreeNode[] ts) {
    if(this.selectBusinessList==null)this.selectBusinessList=selectBusinessList;
    ts = buffer.get(parent);
    if (ts == null || (ts != null && ts.length == 0)) {
      ts = (CheckBoxTreeNode[]) getTArray(parent);
      if (ts != null && ts.length > 0) {
        buffer.put(parent, ts);
        for (CheckBoxTreeNode node : ts) {
          parent.add(node);
        }
        parent.setLeaf(false);
        if (parent.isLeaf == false && parent.isChecked == true) {
          for (int i = 0; i < ts.length; i++) {
            CheckBoxTreeNode tmpChileNode = ts[i];
            tmpChileNode.setChecked(selectBusinessList, true, true);
            if(!tmpChileNode.isLeaf)processCheckedNode(selectBusinessList, tmpChileNode, null);
          }
        }
      } else {
//        parent.setLeaf(true);
        parent.setChecked(selectBusinessList, true, true);
      }
    } else {
      if (parent.isLeaf == false && parent.isChecked) {
        for (int i = 0; i < ts.length; i++) {
          CheckBoxTreeNode tmpChileNode = ts[i];
          tmpChileNode.setChecked(selectBusinessList, true, true);
          processCheckedNode(selectBusinessList,tmpChileNode, null);
        }
      }
    }
  }

  private void processTArray(CheckBoxTreeNode parent, CheckBoxTreeNode curNode, CheckBoxTreeNode[] ts) {
    ts = buffer.get(parent);
    if (ts == null || (ts != null && ts.length == 0)) {
      ts = (CheckBoxTreeNode[]) gets(curNode);
      
      if(ts!=null && ts.length>0){
        parent.setLeaf(false);
        buffer.put(parent, ts);
      }
    }
    if (ts != null && ts.length > 0) {
      CheckBoxTreeNode[] tmp=ts.clone();
      processCheckedNode(parent, tmp);
    } else {
      parent.isLeaf = true;
    }
  }
  
  private void processCheckedTArray(List<Staff> selectBusinessList,CheckBoxTreeNode parent, CheckBoxTreeNode curNode, CheckBoxTreeNode[] ts) {
    ts = buffer.get(parent);
    if (ts == null || (ts != null && ts.length == 0)) {
      ts = (CheckBoxTreeNode[]) gets(curNode);
      
      if(ts!=null && ts.length>0){
        parent.setLeaf(false);
        buffer.put(parent, ts);
      }
    }
    if (ts != null && ts.length > 0) {
      CheckBoxTreeNode[] tmp=ts.clone();
      processCheckedNode(selectBusinessList,parent, tmp);
    } else {
      parent.isLeaf = true;
    }
  }

  private CheckBoxTreeNode[] getTArray(CheckBoxTreeNode parent) {
    CheckBoxTreeNode[] ts = null;
    Object userObj = parent.getUserObject();
    if (userObj == null) {
      ts = (CheckBoxTreeNode[]) gets(parent);
      buffer.put(parent, ts);
    } else {
      ts = buffer.get(parent);
      if (ts == null) {
        ts = (CheckBoxTreeNode[]) gets(parent);
        if(ts!=null && ts.length>0){
          parent.setLeaf(false);
          buffer.put(parent, ts);
        }
      }
    }
    if (ts != null && ts.length > 0) {
      parent.isLeaf = false;
      for (CheckBoxTreeNode node : ts)
        parent.add(node);
    } else {
      if (userObj instanceof Company) {
        Company company = (Company) userObj;
        String comParentId = company.getCode();
        Org org = new Org();
        org.setCoCode(comParentId);
        CheckBoxTreeNode orgNode = new CheckBoxTreeNode();
        orgNode.setUserObject(org);
        if(parent.isChecked){
          processCheckedTArray(selectBusinessList,parent, orgNode, ts);
        }
        else{
          processTArray(parent, orgNode, ts);
        }
      } else if (userObj instanceof Org) {
        Org org = (Org) userObj;
        Position pos = new Position();
        pos.setCoCode(org.getCoCode());
        pos.setOrgCode(org.getCode());
        CheckBoxTreeNode posNode = new CheckBoxTreeNode();
        posNode.setUserObject(pos);
        if(parent.isChecked){
          processCheckedTArray(selectBusinessList,parent, posNode, ts);
        }
        else{
          processTArray(parent, posNode, ts);
        }
      } else if (userObj instanceof Position) {
        Position pos = (Position) userObj;
        Staff staff = new Staff();
        staff.setCoCode(pos.getCoCode());
        staff.setOrgCode(pos.getOrgCode());
        staff.setPosiCode(pos.getCode());
        CheckBoxTreeNode staffNode = new CheckBoxTreeNode();
        staffNode.setUserObject(staff);
        if(parent.isChecked){
          processCheckedTArray(selectBusinessList,parent, staffNode, ts);
        }
        else{
          processTArray(parent, staffNode, ts);
        }
        //        parent.add(staffNode);
      } else {
        parent.isLeaf = true;
      }
    }
    return ts;
  };

  public Object getChild(Object parent, int index) {
    return getTArray((CheckBoxTreeNode) parent)[index];
  }

  public int getChildCount(Object parent) {
    return getTArray((CheckBoxTreeNode) parent).length;
  }

  public int getIndexOfChild(Object parent, Object child) {
    return Arrays.asList(getTArray((CheckBoxTreeNode) parent)).indexOf(child);
  }

  public Object getRoot() {
    return root;
  }

  public boolean isLeaf(Object node) {
    return getChildCount(node) < 1;
  }

  public void clearBuffer() {
    buffer.clear();
  }

  public void removeTreeModelListener(TreeModelListener l) {
  }

  public void valueForPathChanged(TreePath path, Object newValue) {
  }

  public void addTreeModelListener(TreeModelListener l) {
  }

  /**
   * 查询该节点的数据
   * @param parent
   * @return
   */
  protected abstract Object[] gets(CheckBoxTreeNode parent);

}
