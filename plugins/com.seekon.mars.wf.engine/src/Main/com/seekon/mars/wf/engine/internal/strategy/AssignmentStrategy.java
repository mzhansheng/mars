package com.seekon.mars.wf.engine.internal.strategy;

import java.util.Set;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;

/**
 * 
 * 任务分配策略，通过此策略来决定代办任务将由谁来执行。当前支持三种方式：
 * 1、人工选择的方式
 * 2、根据定义的组织上下级来分配
 * 3、根据定义的业务上下级来分配
 *
 */
public interface AssignmentStrategy {
  
  /**
   * 分配任务，返回所分配任务的列表
   * @param ctx
   * @return
   * @throws WorkflowException
   */
  public Set apply(Environment env) throws WorkflowException;
  
}
