package com.seekon.mars.wf.engine.internal.strategy;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;

/**
 * 节点退回的执行策略
 * 1.强制回退
 * 2.条件判断回退
 * @author Administrator
 */
public interface UntreadStrategy {
  public void apply(Environment env) throws WorkflowException;
}
