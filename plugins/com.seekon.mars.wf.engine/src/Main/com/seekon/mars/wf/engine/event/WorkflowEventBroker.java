package com.seekon.mars.wf.engine.event;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;

public interface WorkflowEventBroker {

  public void addProcessInstanceListener(ProcessInstanceListener listener);
  
  public void removeProcessInstanceListener(ProcessInstanceListener listener);
  
  public void fireProcessInstanceRestart(InstanceModel instance, Environment env);
  
  public void fireProcessInstanceStart(InstanceModel instance, Environment env);
  
  public void fireProcessInstanceTerminate(InstanceModel instance, Environment env);
  
  public void fireProcessInstanceSuspend(InstanceModel instance, Environment env);
  
  public void fireProcessInstanceRework(InstanceModel instance, Environment env);
  
  public void fireProcessInstanceActivate(InstanceModel instance, Environment env);
  
  public void fireProcessInstanceDeactivate(InstanceModel instance, Environment env);
  
  public void addTaskEventListener(TaskEventListener listener);
  
  public void removeTaskEventListener(TaskEventListener listener);
  
  public void fireTaskCommit(CurrentTaskModel task, Environment env);
  
  public void fireTaskUntread(CurrentTaskModel task, Environment env);
  
  public void fireTaskCallback(CurrentTaskModel task, Environment env);
  
  public void fireTaskHandover(CurrentTaskModel task, Environment env);
  
  public void clear();
}
