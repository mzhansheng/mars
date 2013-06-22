package com.seekon.mars.wf.engine.model;

import java.io.Serializable;

/**
 * 
 * 部件模型，工作流中会使用到的部件模型
 * 表as_compo
 * 
 */
public class CompoModel implements Serializable {

  private static final long serialVersionUID = -2814230297816822763L;

  private String compoId;

  private String compoName;

  private Long tempolateId;

  private String masterTabId;

  private String briefFields;
  
  private String titleField;
  
  private String listType;
  
  public CompoModel() {
  }

  public String getCompoId() {
    return compoId;
  }

  public void setCompoId(String compoId) {
    this.compoId = compoId;
  }

  public String getCompoName() {
    return compoName;
  }

  public void setCompoName(String compoName) {
    this.compoName = compoName;
  }

  public String getMasterTabId() {
    return masterTabId;
  }

  public void setMasterTabId(String masterTabId) {
    this.masterTabId = masterTabId;
  }

  public Long getTempolateId() {
    return tempolateId;
  }

  public void setTempolateId(Long tempolateId) {
    this.tempolateId = tempolateId;
  }

  public String getBriefFields() {
    return briefFields;
  }

  public void setBriefFields(String briefFields) {
    this.briefFields = briefFields;
  }

  public String getTitleField() {
    return titleField;
  }

  public void setTitleField(String titleField) {
    this.titleField = titleField;
  }

  public String getListType() {
    return listType;
  }

  public void setListType(String listType) {
    this.listType = listType;
  }

}
