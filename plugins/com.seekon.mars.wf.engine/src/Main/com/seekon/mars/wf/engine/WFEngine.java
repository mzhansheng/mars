package com.seekon.mars.wf.engine;

import com.seekon.mars.wf.engine.event.WorkflowEventBroker;

public interface WFEngine {
  
  public ExecutionService getExecutionService();
  
  public ManagementService getManagementService();
  
  public MonitorService getMonitorService();
  
  public RepositoryService getRepositoryService();
  
  public WorkflowEventBroker getWorkflowEventBroker();
}
