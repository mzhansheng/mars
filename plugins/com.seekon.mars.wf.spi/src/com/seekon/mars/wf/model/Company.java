package com.seekon.mars.wf.model;

/**
 * 公司
 * @author Administrator
 *
 */
public class Company extends BusinessType {

  private static final long serialVersionUID = -6752212682300687451L;

  private String parentId;

  public String toString() {
    String str=getName();
    str+=code!=null?"[" + code + "]":"";
    return str;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  
  public boolean equals(Object obj) {
    if(obj instanceof Company){
      return super.equals(obj);
    }else{
      return false;
    }
  }

  public String getBusinessKey() {
    return "Cmp_"+getCode()+"_"+getNd();
  }

}
