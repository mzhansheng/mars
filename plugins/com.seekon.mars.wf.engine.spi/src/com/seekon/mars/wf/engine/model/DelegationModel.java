package com.seekon.mars.wf.engine.model;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;

/**
 * 
 * 工作流委託代理
 * 表wf_delegation
 * 
 */
public class DelegationModel implements java.io.Serializable {

  private static final long serialVersionUID = -2689868095720272439L;

  private Long delegationId;

  private String description;

  private Long templateId;

  private Long nodeId;

  private String sender;

  private String senderCoCode;

  private String senderOrgCode;

  private String senderPosiCode;

  private String owner;

  private String receiver;

  private String receiverCoCode;

  private String receiverOrgCode;

  private String receiverPosiCode;

  private Long parentId;

  private String delegationTime;

  private String startTime;

  private String endTime;

  private String active;
  
  public Long getDelegationId() {
    return delegationId;
  }

  public void setDelegationId(Long delegationId) {
    this.delegationId = delegationId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getDelegationTime() {
    return delegationTime;
  }

  public void setDelegationTime(String delegationTime) {
    this.delegationTime = delegationTime;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getSenderCoCode() {
    return senderCoCode;
  }

  public void setSenderCoCode(String senderCoCode) {
    this.senderCoCode = senderCoCode;
  }

  public String getSenderOrgCode() {
    return senderOrgCode;
  }

  public void setSenderOrgCode(String senderOrgCode) {
    this.senderOrgCode = senderOrgCode;
  }

  public String getSenderPosiCode() {
    return senderPosiCode;
  }

  public void setSenderPosiCode(String senderPosiCode) {
    this.senderPosiCode = senderPosiCode;
  }

  public String getReceiverCoCode() {
    return receiverCoCode;
  }

  public void setReceiverCoCode(String receiverCoCode) {
    this.receiverCoCode = receiverCoCode;
  }

  public String getReceiverOrgCode() {
    return receiverOrgCode;
  }

  public void setReceiverOrgCode(String receiverOrgCode) {
    this.receiverOrgCode = receiverOrgCode;
  }

  public String getReceiverPosiCode() {
    return receiverPosiCode;
  }

  public void setReceiverPosiCode(String receiverPosiCode) {
    this.receiverPosiCode = receiverPosiCode;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  /**
   * 检查此代理记录是否可用
   * 1、active为1
   * 2、当前时间在startTime与endTime之间
   * @return
   */
  public boolean isActive(){
    long currentTime = System.currentTimeMillis();
    try {
      return "1".equals(active) && ((startTime == null || startTime.length() == 0 
        || DateUtil.parseTime(startTime).getTime() <= currentTime))
        && (endTime == null || endTime.length() == 0
        || DateUtil.parseTime(endTime).getTime() >= currentTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return false;
  }
  
  /**
   * 被授权人中是否包含有sender
   * @param staffCode
   * @param posiCode
   * @param orgCode
   * @param coCode
   * @return
   */
  public boolean senderContains(String staffCode, String posiCode, String orgCode, String coCode){
    return isActive() && (this.sender.equals(staffCode) || "*".equals(this.sender) || "*".equals(staffCode))
      && (this.senderCoCode.equals(coCode) || "*".equals(this.senderCoCode) || "*".equals(coCode))
      && (this.senderOrgCode.equals(orgCode) || "*".equals(this.senderOrgCode) || "*".equals(orgCode))
      && (this.senderPosiCode.equals(posiCode) || "*".equals(this.senderPosiCode) || "*".equals(posiCode));
  }
  
  /**
   * 被授权人中是否包含有sender
   * @param sender
   * @return
   */
  public boolean senderContains(StaffModel sender){
    return senderContains(sender.getCode(), sender.getStaffPosition().getPosition().getCode()
      , sender.getStaffPosition().getOrg().getCode(), sender.getStaffPosition().getOrg().getCompany().getCode());
  }
  
  /**
   * 赋予权限的人中是否包含有receiver
   * @param receiver
   * @return
   */
  public boolean receiverContains(StaffModel receiver){
    return receiverContains(receiver.getCode(), receiver.getStaffPosition().getPosition().getCode()
      , receiver.getStaffPosition().getOrg().getCode(), 
      receiver.getStaffPosition().getOrg().getCompany().getCode());
  }
  
  /**
   * 赋予权限的人中是否包含有receiver
   * @param staffCode
   * @param posiCode
   * @param orgCode
   * @param coCode
   * @return
   */
  public boolean receiverContains(String staffCode, String posiCode, String orgCode, String coCode){
    return isActive() && (this.receiver.equals(staffCode) || "*".equals(this.receiver) || "*".equals(staffCode))
      && (this.receiverCoCode.equals(coCode) || "*".equals(this.receiverCoCode) || "*".equals(coCode))
      && (this.receiverOrgCode.equals(orgCode) || "*".equals(this.receiverOrgCode) || "*".equals(orgCode))
      && (this.receiverPosiCode.equals(posiCode) || "*".equals(this.receiverPosiCode) || "*".equals(posiCode));
  }
  
  /**
   * 
   * @return
   */
  public StaffModel getReceiverStaff(){
    Map sessionMap = new HashMap();
    sessionMap.put("svUserID", this.receiver);
    sessionMap.put("svCoCode", this.receiverCoCode);
    sessionMap.put("svOrgCode", this.receiverOrgCode);
    sessionMap.put("svPoCode", this.receiverPosiCode);

    StaffModel staff = new StaffModel(sessionMap);
    staff.setDelegationId(delegationId);
    return staff;
  }
  
  /**
   * 
   * @return
   */
  public StaffModel getSenderStaff(){
    Map sessionMap = new HashMap();
    sessionMap.put("svUserID", this.sender);
    sessionMap.put("svCoCode", this.senderCoCode);
    sessionMap.put("svOrgCode", this.senderOrgCode);
    sessionMap.put("svPoCode", this.senderPosiCode);

    StaffModel staff = new StaffModel(sessionMap);
    staff.setDelegationId(delegationId);
    return staff;
  }
}
