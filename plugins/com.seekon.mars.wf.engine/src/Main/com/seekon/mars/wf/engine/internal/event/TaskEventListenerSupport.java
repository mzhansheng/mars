package com.seekon.mars.wf.engine.internal.event;

import com.seekon.mars.wf.engine.event.TaskEvent;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;

public class TaskEventListenerSupport extends AbstractListenerSupport{

  public void fireTaskCommit(CurrentTaskModel task, Environment env){
    fire(task, TaskEvent.COMMIT, env);
  }
  
  public void fireTaskUntread(CurrentTaskModel task, Environment env){
    fire(task, TaskEvent.UNTREAD, env);
  }
  
  public void fireTaskCallback(CurrentTaskModel task, Environment env){
    fire(task, TaskEvent.CALLBACK, env);
  }
  
  public void fireTaskHandover(CurrentTaskModel task, Environment env){
    fire(task, TaskEvent.HANDOVER, env);
  }
}
