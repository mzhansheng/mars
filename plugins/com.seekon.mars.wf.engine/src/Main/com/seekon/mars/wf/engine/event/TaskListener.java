package com.seekon.mars.wf.engine.event;

import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.exception.WorkflowException;

/**
 * 
 * 任务监听接口，业务应用程序可以实现此接口来在各项操作前进行相关的处理
 *
 */
public interface TaskListener {

  public void beforeExecute(WorkflowContext ctx) throws WorkflowException;
  
  public void afterExecute(WorkflowContext ctx) throws WorkflowException;
}
