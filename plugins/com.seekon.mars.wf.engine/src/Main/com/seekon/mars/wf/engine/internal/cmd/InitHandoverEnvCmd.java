package com.seekon.mars.wf.engine.internal.cmd;

import java.util.List;

import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;

/**
 * 
 * 移交任务
 * 1、设置env为人工选择模式
 * 2、设置context中的selectedNextLinkId为当前节点所对应的已经执行过的linkId
 * 
 */
public class InitHandoverEnvCmd extends AbstractCommand{

  private static final long serialVersionUID = -2061447917305163764L;

  public void doExecute(Environment env) throws CommandException {
    ////Assert.notEmpty(env.getContext().getSelectedNextExecutorList(), "移交任务时被移交人员不能为空!");
    
    WorkflowContext context = env.getContext();
    Long selectedNextLinkId = null;
    
    PassModel pass = new PassModel();
    pass.setInstanceId(context.getInstanceId());
    pass.setNextNodeId(env.getCurrentNode().getNodeId());//曾经提交过的link所对应的nextNode为当前Node
    List passList = WFDaoFactory.getWFRuntimeDao().getInstancePass(pass);
    if(!passList.isEmpty()){
      selectedNextLinkId = ((PassModel)passList.get(0)).getNodeLinkId();
    }else{
      selectedNextLinkId = ((LinkModel)env.getCurrentNode().getToLinkList().get(0)).getNodeLinkId();
    }
    
    context.setSelectedNextLinkId(selectedNextLinkId);
    env.setManual(true);
  }

}
