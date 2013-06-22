package com.seekon.mars.wf.engine.internal.strategy.completion;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.strategy.CompletionStrategy;
import com.seekon.mars.wf.engine.util.ActivityInstanceState;

/**
 * 
 * 任意人员在此节点提交即表示此节点关闭完成
 *
 */
public class AnyiCompletionStrategy implements CompletionStrategy{

  public ActivityInstanceState apply(Environment env) throws WorkflowException {
    
    return ActivityInstanceState.CLOSED_COMPLETED;
  }

}
