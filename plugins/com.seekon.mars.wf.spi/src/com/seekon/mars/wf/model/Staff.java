package com.seekon.mars.wf.model;

/**
 * 人员
 * @author Administrator
 *
 */
public class Staff extends BusinessType {

  private static final long serialVersionUID = -6500279998748431456L;

  private String pwd;

  private String email;

  private String status;

  private String coCode;

  private String orgCode;

  private String posiCode;

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

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public String getPosiCode() {
    return posiCode;
  }

  public void setPosiCode(String posiCode) {
    this.posiCode = posiCode;
  }

  public boolean equals(Object obj) {
    if (obj instanceof Staff) {
      Staff other = (Staff) obj;
      return super.equals(obj) && this.coCode.equals(other.coCode) && this.orgCode.equals(other.orgCode)
        && this.posiCode.equals(other.posiCode);
    } else {
      return false;
    }
  }

  public String getBusinessKey() {
    return "Stf_" + getCode() + "_" + getNd();
  }
}
