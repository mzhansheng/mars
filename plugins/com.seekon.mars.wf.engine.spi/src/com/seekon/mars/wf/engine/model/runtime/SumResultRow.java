package com.seekon.mars.wf.engine.model.runtime;

import java.io.Serializable;

public class SumResultRow implements Serializable{
  private Integer nd;
  private String coCode;
  private String compId;
  private Long templateId;
  private String templateName;
  private Long instanceId;
  private String instanceName;
  private Long nodeId;
  private String nodeName;
  private String executor;
  private String executorName;
  private String arrivalTime;
  private String executeTime;
//  private String orgCode;
//  private String posiCode;
  private Double dureTime;//消耗时间
  private Double averageTime;//平均业务时间
 
  public String getCompId() {
    return compId;
  }
 
  public void setCompId(String compId) {
    this.compId = compId;
  }
 
  public Long getTemplateId() {
    return templateId;
  }
  
  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }
 
  public String getTemplateName() {
    return templateName;
  }
 
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }
 
  public Long getInstanceId() {
    return instanceId;
  }
 
  public void setInstanceId(Long instanceId) {
    this.instanceId = instanceId;
  }
 
  public String getInstanceName() {
    return instanceName;
  }
 
  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }
  
  public Long getNodeId() {
    return nodeId;
  }
 
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }
 
  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getExecutor() {
    return executor;
  }

  public void setExecutor(String executor) {
    this.executor = executor;
  }

  public String getExecutorName() {
    return executorName;
  }
  
  public void setExecutorName(String executorName) {
    this.executorName = executorName;
  }
 
  public String getArrivalTime() {
    return arrivalTime;
  }
  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public String getExecuteTime() {
    return executeTime;
  }

  public void setExecuteTime(String executeTime) {
    this.executeTime = executeTime;
  }

  public String getCoCode() {
    return coCode;
  }
 
  public void setCoCode(String coCode) {
    this.coCode = coCode;
  }
 
//  public String getOrgCode() {
//    return orgCode;
//  }
//  
//  public void setOrgCode(String orgCode) {
//    this.orgCode = orgCode;
//  }
//  
//  public String getPosiCode() {
//    return posiCode;
//  }
//  
//  public void setPosiCode(String posiCode) {
//    this.posiCode = posiCode;
//  }
 
  public Integer getNd() {
    return nd;
  }
 
  public void setNd(Integer nd) {
    this.nd = nd;
  }

  public Double getDureTime() {
    return dureTime;
  }

  public void setDureTime(Double dureTime) {
    this.dureTime = dureTime;
  }

  public Double getAverageTime() {
    return averageTime;
  }

  public void setAverageTime(Double averageTime) {
    this.averageTime = averageTime;
  }
  
}
