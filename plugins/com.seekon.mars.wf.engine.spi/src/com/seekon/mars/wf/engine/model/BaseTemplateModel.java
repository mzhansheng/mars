package com.seekon.mars.wf.engine.model;

public class BaseTemplateModel implements java.io.Serializable{
  private Long templateId = null;

  private String name = null;

  private String description = null;

  private String templateType = null;

  private String version = null;

  private String startTime = null;

  private String expireTime = null;

  private String createTime = null;

  private String createStaffId = null;

  private String active;

  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }

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

  public String getTemplateType() {
    return templateType;
  }


  public void setTemplateType(String templateType) {
    this.templateType = templateType;
  }


  public String getVersion() {
    return version;
  }


  public void setVersion(String version) {
    this.version = version;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = expireTime;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getCreateStaffId() {
    return createStaffId;
  }

  public void setCreateStaffId(String createStaffId) {
    this.createStaffId = createStaffId;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  public String toString(){
    return name+"["+templateId+"]";
  }
}
