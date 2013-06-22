package com.seekon.mars.wf.client.trace.unit;

import java.io.Serializable;

/**
 * 连接单元
 * @author Administrator
 *
 */
public class LinkUnit implements Serializable{
  protected Object model; //图形单元的描述模式
  protected GraphUnit previousUnit;//前驱单元
  protected GraphUnit nextUnit;//后置单元
  
  public LinkUnit(GraphUnit startUnit,GraphUnit endUnit){
    this.previousUnit=startUnit;
    this.nextUnit=endUnit;
  }

  public GraphUnit getPreviousUnit() {
    return previousUnit;
  }

  public void setPreviousUnit(GraphUnit previousUnit) {
    this.previousUnit = previousUnit;
  }

  public GraphUnit getNextUnit() {
    return nextUnit;
  }

  public void setNextUnit(GraphUnit nextUnit) {
    this.nextUnit = nextUnit;
  }

  public Object getModel() {
    return model;
  }

  public void setModel(Object model) {
    this.model = model;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((nextUnit == null) ? 1 : nextUnit.hashCode());
    result = prime * result + ((previousUnit == null) ? 1 : previousUnit.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
//    if (this == obj)
//      return true;
//    if (obj == null)
//      return false;
//    if (getClass() != obj.getClass())
//      return false;
//    LinkUnit other = (LinkUnit) obj;
//    if (nextUnit == null) {
//      if (other.nextUnit != null)
//        return false;
//    } else if (!nextUnit.equals(other.nextUnit))
//      return false;
//    if (previousUnit == null) {
//      if (other.previousUnit != null)
//        return false;
//    } else if (!previousUnit.equals(other.previousUnit))
//      return false;
    LinkUnit other = (LinkUnit) obj;
//    System.out.println("====>"+this.hashCode()+"::::"+other.hashCode());
    return this.hashCode()==other.hashCode();
  }
  
}
