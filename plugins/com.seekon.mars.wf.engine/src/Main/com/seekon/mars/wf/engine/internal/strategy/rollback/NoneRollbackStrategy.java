package com.seekon.mars.wf.engine.internal.strategy.rollback;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.strategy.RollbackStrategy;

/**
 * 
 * 1、当前节点没有任何人提交送审才允许收回(发起人)
 * 2、当前节点没有任何人提交送审才允许退回（退回发起人）
 *
 */
public class NoneRollbackStrategy implements RollbackStrategy{

  public void apply(Environment env) throws WorkflowException {
    
  }

}
