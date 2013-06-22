package com.seekon.mars.wf.engine.internal.strategy.rollback;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.strategy.RollbackStrategy;

/**
 * 
 * 1、不管当前节点有没有其他人提交送审都可退回
 * 2、只要当前节点有人提交送审则不可收回
 *
 */
public class AnyRollbackStrategy implements RollbackStrategy{

  public void apply(Environment env) throws WorkflowException {
    // TODO Auto-generated method stub
    
  }

}
