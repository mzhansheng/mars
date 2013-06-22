package com.seekon.mars.wf.client.trace;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;
import org.jgraph.util.ParallelEdgeRouter;

import com.seekon.mars.wf.client.trace.unit.LinkUnit;

public class TraceGraphEdge extends DefaultEdge{
  private LinkUnit linkUnit;
  private Object userModel;
  private Map edgeAttrMap = new Hashtable();
  public TraceGraphEdge(LinkUnit linkUnit,Map attrGraphMap){
    this.linkUnit=linkUnit;
    int arrow = GraphConstants.ARROW_SIMPLE;
    //    GraphConstants.setLineStyle(edgeAttrMap, GraphConstants.STYLE_BEZIER);
    GraphConstants.setRouting(edgeAttrMap, ParallelEdgeRouter.getSharedInstance());
    GraphConstants.setLineEnd(edgeAttrMap, arrow);
    GraphConstants.setEditable(edgeAttrMap, false);
    GraphConstants.setLabelAlongEdge(edgeAttrMap, true);
    GraphConstants.setEndFill(edgeAttrMap, true);
    GraphConstants.setDisconnectable(edgeAttrMap, false);
    GraphConstants.setFont(edgeAttrMap, new Font("宋体", Font.PLAIN, 14));
    GraphConstants.setLineColor(edgeAttrMap, Color.GRAY);
    GraphConstants.setLineWidth(edgeAttrMap, 0.8f);
    attrGraphMap.put(this, edgeAttrMap);
  }
  
  public LinkUnit getLinkUnit() {
    return linkUnit;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((edgeAttrMap == null) ? 0 : edgeAttrMap.hashCode());
    result = prime * result + ((linkUnit == null) ? 0 : linkUnit.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TraceGraphEdge other = (TraceGraphEdge) obj;
    if (edgeAttrMap == null) {
      if (other.edgeAttrMap != null)
        return false;
    } else if (!edgeAttrMap.equals(other.edgeAttrMap))
      return false;
    if (linkUnit == null) {
      if (other.linkUnit != null)
        return false;
    } else if (!linkUnit.equals(other.linkUnit))
      return false;
    return true;
  }

  /**
   * @param lineStyle 例如：GraphConstants.STYLE_BEZIER
   */
  public void setLineStyle(int lineStyle){
    GraphConstants.setLineStyle(edgeAttrMap, lineStyle);
  }
  
  public void refreshLineStyle(int lineStyle){
    GraphConstants.setLineStyle(this.getAttributes(), lineStyle);
  }
  
  public void refreshLineColor(Color color){
    GraphConstants.setLineColor(this.getAttributes(), color);
  }
  
  public void setLineColor(Color color){
    GraphConstants.setLineColor(edgeAttrMap, color);
  }
  
  public void refreshLineLabel(String msg){
    GraphConstants.setValue(this.getAttributes(),msg);
  }
  
  public void setLineLabel(String msg){
    GraphConstants.setValue(edgeAttrMap, msg);
  }

  public Object getUserModel() {
    return userModel;
  }

  public void setUserModel(Object userModel) {
    this.userModel = userModel;
  }
  
  
}
