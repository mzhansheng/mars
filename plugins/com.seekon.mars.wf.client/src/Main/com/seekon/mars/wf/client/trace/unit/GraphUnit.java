package com.seekon.mars.wf.client.trace.unit;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.seekon.mars.wf.engine.model.NodeModel;

/**
 * 图形单元
 * @author Administrator
 *
 */
public abstract class GraphUnit implements Serializable{
  public static final int UNITTYPE_START=0;//开始
  public static final int UNITTYPE_END=1;//结束
  public static final int UNITTYPE_TASK=2;//任务
  public static final int UNITTYPE_NAVIGATION=3;//导航
  
  protected Object model; //图形单元的描述模式
  protected Point location;//图形单元的位置
  protected ImageIcon icon;//图形单元图标
  protected boolean isEnable=false;//是否置灰
  
  protected List<LinkUnit> previousUnitList;//前驱单元
  protected List<LinkUnit> nextUnitList;//后置单元
  
  public GraphUnit(){
    previousUnitList=new ArrayList<LinkUnit>();
    nextUnitList=new ArrayList<LinkUnit>();
  }
  /**
   * @return 用户业务对象
   */
  public Object getModel() {
    return model;
  }
  
  public void setModel(Object model) {
    this.model = model;
    if(this.model instanceof NodeModel ){
      NodeModel nodeModel=(NodeModel)this.model;
      List staffList=nodeModel.getExecutorSourceList();
      if(staffList!=null && staffList.size()>0){
        setIcon(new ImageIcon(GraphUnit.class.getResource("userTask.jpg")));
      }
    }
  }
 
  public Point getLocation() {
    return location;
  }
  
  public void setLocation(Point location) {
    this.location = location;
  }
  
  public ImageIcon getIcon() {
    return icon;
  }
 
  public void setIcon(ImageIcon icon) {
    this.icon = icon;
  }
  
  public boolean isEnable() {
    return isEnable;
  }
  
  public void setEnable(boolean isEnable) {
    this.isEnable = isEnable;
  }
  
  public List<LinkUnit> getPreviousUnitList() {
    return previousUnitList;
  }
  
  public void setPreviousUnitList(List<LinkUnit> previousUnit) {
    this.previousUnitList = previousUnit;
  }
  
  public List<LinkUnit> getNextUnitList() {
    return nextUnitList;
  }
  
  public void setNextUnitList(List<LinkUnit> nextUnit) {
    this.nextUnitList = nextUnit;
  }
  
  /**
   * 添加前驱单元
   * @param privousUnit
   */
  public void addPrivousUnit(GraphUnit privousUnit){
    LinkUnit link=new LinkUnit(privousUnit,this);
    if(!previousUnitList.contains(link))previousUnitList.add(link);
  }
  
  /**
   * 添加后续单元
   * @param nextUnit 分支节点
   * @param nextLinked 分支节点连接序列
   */
  public void addNextUnit(GraphUnit nextUnit){
    LinkUnit link=new LinkUnit(this,nextUnit);
    if(!nextUnitList.contains(link))nextUnitList.add(link);
  }
  
  public boolean equals(Object obj) {
    if(obj==null)return false;
    NodeModel node=(NodeModel)this.getModel();
    NodeModel otherNode=(NodeModel)(((GraphUnit)obj).getModel());
    if(node!=null && otherNode!=null &&
        node.getNodeId().equals(otherNode.getNodeId())){
      return true;
    }
    else{
      return false;
    }
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((model == null) ? 0 : ((NodeModel)model).getNodeId().intValue());
    return result;
  }  
  
  public String toString(){
    Object node=this.getModel();
    return node==null?"":((NodeModel)node).getName();
  }
}
