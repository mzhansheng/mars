package com.seekon.mars.wf.engine.model.participant;

import java.util.Map;

import com.seekon.mars.wf.engine.model.ExecutorSourceModel;

/**
 * 
 * 职员任职情况模型
 *
 */
public class StaffPositionModel implements java.io.Serializable {

  private static final long serialVersionUID = 8479814840932230186L;

  private String id;

  private OrgModel org;

  private PositionModel position;

  public StaffPositionModel() {

  }

  public StaffPositionModel(Map sessionMap) {
    this.position = new PositionModel(sessionMap);
    this.org = new OrgModel(sessionMap);
  }

  public StaffPositionModel(ExecutorSourceModel executorSource) {
    this.org = new OrgModel(executorSource);
    this.position = new PositionModel(executorSource);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public OrgModel getOrg() {
    return org;
  }

  public void setOrg(OrgModel org) {
    this.org = org;
  }

  public PositionModel getPosition() {
    return position;
  }

  public void setPosition(PositionModel position) {
    this.position = position;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((org == null) ? 0 : org.hashCode());
    result = prime * result + ((position == null) ? 0 : position.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof StaffPositionModel)) {
      return false;
    }

    StaffPositionModel tmp = (StaffPositionModel) obj;
    return ((this.org == null && tmp.org == null) || (this.org != null && this.org
      .equals(tmp.getOrg())))
      && ((this.position == null && tmp.position == null) || (this.position != null && this.position
        .equals(tmp.position)));
  }
}
