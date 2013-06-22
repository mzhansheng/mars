package com.seekon.mars.wf.model;

import java.io.Serializable;

public class AsCompoModel implements Serializable {

  private static final long serialVersionUID = 5750975950015747189L;

  private String compoId;

  private String compoName;

  private String masterTabId;

  private String noField;

  private String compType;

  private String defaultWftemplate;

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

  public String getNoField() {
    return noField;
  }

  public void setNoField(String noField) {
    this.noField = noField;
  }

  public String getCompType() {
    return compType;
  }

  public void setCompType(String compType) {
    this.compType = compType;
  }

  public String getDefaultWftemplate() {
    return defaultWftemplate;
  }

  public void setDefaultWftemplate(String defaultWftemplate) {
    this.defaultWftemplate = defaultWftemplate;
  }
}
