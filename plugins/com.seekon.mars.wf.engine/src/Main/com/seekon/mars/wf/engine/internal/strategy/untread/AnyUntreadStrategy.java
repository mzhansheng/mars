package com.seekon.mars.wf.engine.internal.strategy.untread;

import java.util.List;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.internal.strategy.UntreadStrategy;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;

//条件退回策略
public class AnyUntreadStrategy implements UntreadStrategy{

  public void apply(Environment env) throws WorkflowException {
    Long instanceId = env.getContext().getInstanceId();
    Long currentNodeId = env.getCurrentNode().getNodeId();
    String executor=env.getCurrentExecutor().getCode();
    ActionModel action=new ActionModel();
    action.setInstanceId(instanceId);
    action.setNodeId(currentNodeId);
    action.setExecutor(executor);
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    List actionList=runtimeDao.getAction(action);
    if(actionList!=null && actionList.size()>0){
      throw new WorkflowException("其他代办人的任务已经提交，任务不能收回！");
    }
  }
}
