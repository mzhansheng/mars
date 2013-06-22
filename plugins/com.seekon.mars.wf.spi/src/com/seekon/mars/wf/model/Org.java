package com.seekon.mars.wf.model;

/**
 * 组织
 * @author Administrator
 *
 */
public class Org extends BusinessType {

  private static final long serialVersionUID = -5989257473339672085L;

  private String parentId;

  private String coCode;

  public String toString() {
    return getName() + "[" + code + "]";
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getCoCode() {
    return coCode;
  }

  public void setCoCode(String coCode) {
    this.coCode = coCode;
  }

  public int hashCode() {
    return coCode.hashCode() + code.hashCode() + nd.hashCode();
  }
  
  public boolean equals(Object obj) {
    if(obj instanceof Org){
      Org other = (Org)obj;
      return super.equals(obj) && this.coCode.equals(other.coCode);
    }else{
      return false;
    }
  }
  
  public String getBusinessKey() {
    return "Org_"+getCode()+"_"+getNd();
  }

}
