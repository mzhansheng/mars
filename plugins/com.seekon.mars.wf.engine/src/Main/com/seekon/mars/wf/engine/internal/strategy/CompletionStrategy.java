package com.seekon.mars.wf.engine.internal.strategy;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.util.ActivityInstanceState;

/**
 * 
 * 节点提交送审后的执行策略，目前支持三种方式：
 * 1、任意人员在此节点提交即表示此节点关闭完成
 * 2、根据此节点上提交人员的比例来判断，若达到此比例则此节点关闭完成
 * 3、根据此节点上提交人员的数目来判断，若达到此数目则此节点关闭完成
 * 
 */
public interface CompletionStrategy {
  
  public ActivityInstanceState apply(Environment env) throws WorkflowException;
}
