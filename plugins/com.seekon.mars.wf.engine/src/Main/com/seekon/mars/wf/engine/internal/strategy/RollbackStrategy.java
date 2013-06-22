package com.seekon.mars.wf.engine.internal.strategy;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;

/**
 * 
 * 收回或回退到某一节点前的执行策略
 *
 */
public interface RollbackStrategy {

  public void apply(Environment env) throws WorkflowException;
  
}
