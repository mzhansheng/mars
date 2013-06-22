package com.seekon.mars.wf.engine.model.runtime;

public class QueryElement implements java.io.Serializable{
  private String startTime;
  private String endTime;
  private Long templateId;
  private Long instanceId;
  private Long nodeId;
  private String staffId;
  
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

  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }

  public Long getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(Long instanceId) {
    this.instanceId = instanceId;
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public String getStaffId() {
    return staffId;
  }

  public void setStaffId(String staffId) {
    this.staffId = staffId;
  }
}
