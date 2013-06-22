package com.seekon.mars.wf.model;

/**
 * 职位
 * @author Administrator
 *
 */
public class Position extends BusinessType {

  private static final long serialVersionUID = -914677767597608153L;

  private String coCode;

  private String orgCode;
  
  private String onOff;

  /**
   * @return the onOff
   */
  public String getOnOff() {
    return onOff;
  }

  /**
   * @param onOff the onOff to set
   */
  public void setOnOff(String onOff) {
    this.onOff = onOff;
  }

  public String toString() {
    return getName() + "[" + code + "]";
  }

  public String getCoCode() {
    return coCode;
  }

  public void setCoCode(String coCode) {
    this.coCode = coCode;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public boolean equals(Object obj) {
    if(obj instanceof Position){
      Position other = (Position)obj;
      return (super.equals(obj) && (other.coCode!=null && this.coCode.equals(other.coCode))
        && (other.orgCode!=null && this.orgCode.equals(other.orgCode)));
    }else{
      return false;
    }
  }

  public String getBusinessKey() {
    return "Pos_"+getCode()+"_"+getNd();
  }
}
