package com.seekon.mars.wf.engine.internal.event;

import com.seekon.mars.wf.engine.event.ProcessInstanceEvent;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;

public class ProcessInstanceListenerSupport extends AbstractListenerSupport {

  public void fireProcessInstanceRestart(InstanceModel instance, Environment env){
    fire(instance, ProcessInstanceEvent.RESTART, env);
  }
  
  public void fireProcessInstanceStart(InstanceModel instance, Environment env){
    fire(instance, ProcessInstanceEvent.START, env);
  }
  
  public void fireProcessInstanceTerminate(InstanceModel instance, Environment env){
    fire(instance, ProcessInstanceEvent.SUSPEND, env);
  }
  
  public void fireProcessInstanceSuspend(InstanceModel instance, Environment env){
    fire(instance, ProcessInstanceEvent.TERMINATED, env);
  }

  public void fireProcessInstanceRework(InstanceModel instance, Environment env){
    fire(instance, ProcessInstanceEvent.REWORK, env);
  }
  
  public void fireProcessInstanceActivate(InstanceModel instance, Environment env){
    fire(instance, ProcessInstanceEvent.ACTIVATE, env);
  }
  
  public void fireProcessInstanceDeactivate(InstanceModel instance, Environment env){
    fire(instance, ProcessInstanceEvent.DEACTIVATE, env);
  }

}
