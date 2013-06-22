package com.seekon.mars.wf.client.trace;

import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import com.seekon.mars.wf.client.trace.unit.GraphUnit;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.participant.NodeStaffModel;

public class TraceGraphCell extends DefaultGraphCell {
  public static final int ICON_WIDTH = 60;

  public static final int ICON_HEIGHT = 50;
  
  public static final int COMMIT_ICON_HEIGHT = 68;

  private Map nodeAttrMap = new Hashtable();
  
  private List<NodeStaffModel> curTaskAllExecutorList=new ArrayList<NodeStaffModel>();

  private GraphUnit unit;

  public TraceGraphCell(GraphUnit unit, Map attrGraphMap) {
    this.unit = unit;
    Object obj = unit.getModel();
    NodeModel nodeModel = null;
    if (obj != null) {
      nodeModel = (NodeModel) unit.getModel();
    }
    attrGraphMap.put(this, nodeAttrMap);
    Point point = unit.getLocation();

    Rectangle2D targetBounds = targetBounds = new Rectangle2D.Double(point.getX(), point.getY(), ICON_WIDTH,
      ICON_HEIGHT);//node位置
    GraphConstants.setBounds(this.getAttributes(), targetBounds);

    GraphConstants.setIcon(this.getAttributes(), unit.getIcon());
    GraphConstants.setBorder(nodeAttrMap, BorderFactory.createCompoundBorder());
    GraphConstants.setEditable(nodeAttrMap, false);
    GraphConstants.setOpaque(this.getAttributes(), true);
    GraphConstants.setValue(nodeAttrMap, nodeModel == null ? "" : nodeModel.getName()+"["+nodeModel.getNodeId()+"]");
    GraphConstants.setFont(nodeAttrMap, new Font("宋体", Font.PLAIN, 12));
  }

  public void setIcon(ImageIcon icon) {
    GraphConstants.setIcon(nodeAttrMap, icon);
  }
  
  public void refreshIcon(ImageIcon icon){
    GraphConstants.setIcon(this.getAttributes(), icon);
  }

  public void setBounds(Rectangle2D targetBounds) {
    GraphConstants.setBounds(this.nodeAttrMap, targetBounds);
  }
  
  public void refreshBounds(Rectangle2D targetBounds) {
    GraphConstants.setBounds(this.getAttributes(), targetBounds);
  }

  public void setNodeInfo(String msg) {
    GraphConstants.setValue(this.nodeAttrMap, msg);
  }
  
  public void refreshNodeInfo(String msg){
    GraphConstants.setValue(this.getAttributes(), msg);
  }

  public GraphUnit getUserGraphUnit() {
    return unit;
  }
  
  public void addCurTaskExecutor(List<NodeStaffModel> staffList){
    if(staffList!=null)this.curTaskAllExecutorList.addAll(staffList);
  }
  
  public void addCurTaskExecutor(NodeStaffModel staff){
    this.curTaskAllExecutorList.add(staff);
  }
  
  public List<NodeStaffModel> getCurTaskAllExecutorList() {
    return curTaskAllExecutorList;
  }

  public boolean equals(Object obj) {
    GraphUnit otherUnit = (GraphUnit) ((TraceGraphCell) obj).getUserGraphUnit();
    return this.unit.equals(otherUnit);
  }

  public int hashCode() {
    return unit.hashCode();
  }
}
