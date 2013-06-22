package com.seekon.mars.wf.engine.model.runtime;

/**
 * 
 * 流程节点执行记录
 * 表wf_pass
 * 
 */
public class PassModel implements java.io.Serializable {

  private static final long serialVersionUID = 5263736044745144318L;

  private Long passCountId;

  private Long instanceId;

  private Long nodeLinkId;

  private Long currentNodeId;

  private Long nextNodeId;
  
  private String executor;
  
  private String coCode;

  private String orgCode;

  private String posiCode;

  private int nd;

  public Long getPassCountId() {
    return passCountId;
  }

  public void setPassCountId(Long passCountId) {
    this.passCountId = passCountId;
  }

  public Long getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(Long instanceId) {
    this.instanceId = instanceId;
  }

  public Long getNodeLinkId() {
    return nodeLinkId;
  }

  public void setNodeLinkId(Long nodeLinkId) {
    this.nodeLinkId = nodeLinkId;
  }

  public Long getCurrentNodeId() {
    return currentNodeId;
  }

  public void setCurrentNodeId(Long currentNodeId) {
    this.currentNodeId = currentNodeId;
  }

  public Long getNextNodeId() {
    return nextNodeId;
  }

  public void setNextNodeId(Long nextNodeId) {
    this.nextNodeId = nextNodeId;
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

  public int getNd() {
    return nd;
  }

  public void setNd(int nd) {
    this.nd = nd;
  }

  public String getExecutor() {
    return executor;
  }

  public void setExecutor(String executor) {
    this.executor = executor;
  }

}
