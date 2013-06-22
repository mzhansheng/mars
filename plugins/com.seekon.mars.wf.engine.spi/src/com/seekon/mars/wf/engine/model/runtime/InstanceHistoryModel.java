package com.seekon.mars.wf.engine.model.runtime;

/**
 * 
 * 流程实例的历史记录，主要保存已经结束和终止的流程
 * 表wf_instance_history
 * 
 */
public class InstanceHistoryModel extends InstanceModel{

  private static final long serialVersionUID = 3278096742590585264L;
  
  private String instanceTrace;

  public String getInstanceTrace() {
    return instanceTrace;
  }

  public void setInstanceTrace(String instanceTrace) {
    this.instanceTrace = instanceTrace;
  }
}
