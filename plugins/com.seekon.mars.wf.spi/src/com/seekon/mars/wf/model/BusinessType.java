package com.seekon.mars.wf.model;

import java.io.Serializable;

public abstract class BusinessType implements Serializable {

  private static final long serialVersionUID = -553790733166263341L;
  
  protected String name;

  protected String description;

  protected Integer nd;

  protected String code;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getNd() {
    return nd;
  }

  public void setNd(Integer nd) {
    this.nd = nd;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
  
  public boolean equals(Object obj) {
    if(obj!=null && (obj instanceof BusinessType)){
      BusinessType other = (BusinessType)obj;
      return other.code==null ||(this.code.equals(other.code) && this.nd.intValue() == other.nd.intValue());
    }else{
      return false;
    }
  }
  
  public abstract String getBusinessKey();
  
  public String toString(){
    return name+"["+code+"]";
  }
}
