package com.seekon.mars.wf.engine.model.participant;

import java.util.Map;

import com.seekon.mars.wf.engine.model.ExecutorSourceModel;

/**
 * 
 * 职员模型，包含其任职情况
 *
 */
public class StaffModel implements java.io.Serializable {

  private static final long serialVersionUID = -1853269083676769538L;

  private String code = null;

  private String name = null;

  private String pwd = null;

  private String description = null;

  private String email = null;

  private String status = null;

  private String passDesc; //动作描述

  private String actionTime;//动作执行时间

  private Long responsibility = new Long(1);

  private Long delegationId = new Long(1);

  private StaffPositionModel staffPosition;

  public StaffModel() {

  }

  public StaffModel(Map sessionMap) {
    staffPosition = new StaffPositionModel(sessionMap);
    this.code = (String) sessionMap.get("svUserID");
    this.name = (String) sessionMap.get("svUserName");
    this.email = (String) sessionMap.get("email");
  }

  public StaffModel(ExecutorSourceModel executorSource) {
    this.code = executorSource.getExecutor();
    this.responsibility = executorSource.getResponsibility();
    this.staffPosition = new StaffPositionModel(executorSource);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getPassDesc() {
    return passDesc;
  }

  public void setPassDesc(String passDesc) {
    this.passDesc = passDesc;
  }

  public String getActionTime() {
    return actionTime;
  }

  public void setActionTime(String actionTime) {
    this.actionTime = actionTime;
  }

  public StaffPositionModel getStaffPosition() {
    return staffPosition;
  }

  public void setStaffPosition(StaffPositionModel staffPosition) {
    this.staffPosition = staffPosition;
  }

  public Long getResponsibility() {
    return responsibility;
  }

  public void setResponsibility(Long responsibility) {
    this.responsibility = responsibility;
  }

  public Long getDelegationId() {
    return delegationId;
  }

  public void setDelegationId(Long delegationId) {
    this.delegationId = delegationId;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    result = prime * result
      + ((staffPosition == null) ? 0 : staffPosition.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof StaffModel)) {
      return false;
    }

    StaffModel tmp = (StaffModel) obj;
    return (this.code.equals(tmp.getCode()))
      && ((this.staffPosition == null && tmp.getStaffPosition() == null) || (this.staffPosition != null && this.staffPosition
        .equals(tmp.getStaffPosition())));
  }
}
