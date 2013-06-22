package com.seekon.mars.wf.engine.internal.strategy.completion;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.strategy.CompletionStrategy;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;
import com.seekon.mars.wf.engine.util.ActivityInstanceState;

/**
 * 
 * 根据执行通过的人数来判断当前节点是否可以结束
 * 若通过则将link放入Environment的nextLinkStaffSet中以便任务分配时使用
 * 
 */
public class NumericalCompletionStrategy implements CompletionStrategy{
  
  protected LinkModel link;
  
  public NumericalCompletionStrategy(LinkModel link){
    this.link = link;
  }
  
  public ActivityInstanceState apply(Environment env) throws WorkflowException {
    int count = WFDaoFactory.getWFRuntimeDao().getActionCountByNodeId(env.getInstance().getInstanceId()
      , env.getCurrentNode().getNodeId());
    if(count >= link.getPassValue().doubleValue()){
      return ActivityInstanceState.CLOSED_COMPLETED;
    }
    
    if(link.getNodeLinkId() != null){
      env.addNextLinkStaff(new LinkStaffModel(link));
    }
    
    return ActivityInstanceState.OPEN_RUNNING;
  }

}
