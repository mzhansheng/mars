package com.seekon.mars.wf.engine.internal.event;

import javax.inject.Inject;

import com.seekon.mars.wf.engine.event.ProcessInstanceListener;
import com.seekon.mars.wf.engine.event.TaskEventListener;
import com.seekon.mars.wf.engine.event.WorkflowEventBroker;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;

public class BasicWorkflowEventBroker implements WorkflowEventBroker{
  
  @Inject
  private ProcessInstanceListenerSupport processInstanceListener;
  
  @Inject
  private TaskEventListenerSupport taskEventListener;
  
  public BasicWorkflowEventBroker() {
    super();
    this.processInstanceListener = new ProcessInstanceListenerSupport();
    this.taskEventListener = new TaskEventListenerSupport();
  }

  public BasicWorkflowEventBroker(ProcessInstanceListenerSupport processInstanceListener
    , TaskEventListenerSupport taskEventListener){
    this.processInstanceListener = processInstanceListener;
    this.taskEventListener = taskEventListener;
  }
  
  public void addProcessInstanceListener(ProcessInstanceListener listener) {
    processInstanceListener.addListener(listener);
  }

  public void removeProcessInstanceListener(ProcessInstanceListener listener) {
    processInstanceListener.removeListener(listener);
  }

  public void fireProcessInstanceRestart(InstanceModel instance, Environment env) {
    processInstanceListener.fireProcessInstanceRestart(instance, env);
  }

  public void fireProcessInstanceStart(InstanceModel instance, Environment env) {
    processInstanceListener.fireProcessInstanceStart(instance, env);
  }

  public void fireProcessInstanceSuspend(InstanceModel instance, Environment env) {
    processInstanceListener.fireProcessInstanceSuspend(instance, env);
  }

  public void fireProcessInstanceTerminate(InstanceModel instance, Environment env) {
    processInstanceListener.fireProcessInstanceTerminate(instance, env);
  }
  
  public void fireProcessInstanceRework(InstanceModel instance, Environment env) {
    processInstanceListener.fireProcessInstanceRework(instance, env);
  }
  
  public void fireProcessInstanceActivate(InstanceModel instance, Environment env) {
    processInstanceListener.fireProcessInstanceActivate(instance, env);
  }

  public void fireProcessInstanceDeactivate(InstanceModel instance, Environment env) {
    processInstanceListener.fireProcessInstanceDeactivate(instance, env);
  }

  public void addTaskEventListener(TaskEventListener listener) {
    taskEventListener.addListener(listener);
  }

  public void removeTaskEventListener(TaskEventListener listener) {
    taskEventListener.removeListener(listener);
  }

  public void fireTaskCallback(CurrentTaskModel task, Environment env) {
    taskEventListener.fireTaskCallback(task, env);
  }

  public void fireTaskCommit(CurrentTaskModel task, Environment env) {
    taskEventListener.fireTaskCommit(task, env);
  }

  public void fireTaskHandover(CurrentTaskModel task, Environment env) {
    taskEventListener.fireTaskHandover(task, env);
  }

  public void fireTaskUntread(CurrentTaskModel task, Environment env) {
    taskEventListener.fireTaskUntread(task, env);
  }

  public void clear(){
    processInstanceListener.clear();
    taskEventListener.clear();
  }
}
