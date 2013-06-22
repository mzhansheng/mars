package com.seekon.mars.wf.engine.internal.cmd;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.strategy.CompletionStrategy;
import com.seekon.mars.wf.engine.internal.strategy.completion.CompletionStrategyFactory;
import com.seekon.mars.wf.engine.util.ActivityInstanceState;

public class CompleteTaskCmd extends CreateTaskCmd{

  private static final long serialVersionUID = 548183945713257827L;

  public void doExecute(Environment env) throws CommandException {
    boolean canCreateTask = false;
    CompletionStrategy[] completeStrategyList = CompletionStrategyFactory.getCompletionStrategy(env);
    for (int i = 0; i < completeStrategyList.length; i++) {
      ActivityInstanceState state = completeStrategyList[i].apply(env);
      if (ActivityInstanceState.CLOSED_COMPLETED.equals(state)) {// 1、当前节点已经结束，可创建下一节点任务
        canCreateTask = true;
      }
    }
    
    if(canCreateTask){
      super.doExecute(env);
    }
  }
}
