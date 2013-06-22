package com.seekon.mars.wf.engine.internal.cmd;

import java.util.Iterator;

import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 创建运行期的执行动作
 * 1、创建有效的执行动作
 * 2、创建执行动作的历史
 * 
 */
public class CreateActionCmd extends AbstractCommand{

  private static final long serialVersionUID = 4405120456621949182L;

  public void doExecute(Environment env) throws CommandException {
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    
    NodeModel currentNode = env.getCurrentNode();
    WorkflowContext context = env.getContext();
    
    Iterator iterator = env.getNextLinkStaffSet().iterator();
    while(iterator.hasNext()){
      LinkStaffModel linkStaff = (LinkStaffModel)iterator.next();
      String actionName = null;
      
      StaffModel staff = env.getDelegateExecutor();//授权代理action
      if(staff != null){
        actionName = WFConst.ACTION_TYPE_AUTHORIZE_TASK + "_" + staff.getDelegationId();
      }
      else{
        staff = env.getCurrentExecutor();//执行的action
        actionName = linkStaff.getLink().getActionName();
      }
     
      ActionHistoryModel action = assembleActionModel(currentNode, context, staff, actionName,env.getTaskArrivalTime());
      runtimeDao.createAction(action);
      runtimeDao.createActionHistory(action);
    }
  }
  
  private ActionHistoryModel assembleActionModel(NodeModel currentNode, WorkflowContext context
    ,StaffModel staff, String actionName,String taskArrivalTime){
    
    ActionHistoryModel action = new ActionHistoryModel();
    action.setInstanceId(context.getInstanceId());
    action.setNodeId(currentNode.getNodeId());
    action.setNodeId(currentNode.getNodeId());
    action.setDescription((String)context.getVariable("actionDescription"));
    if(taskArrivalTime!=null){
      action.setArrivalTime(taskArrivalTime);
    }
    action.setExecuteTime(DateUtil.getTime());
    action.setActionName(actionName);
    action.setExecutor(staff.getCode());
    action.setOwner(staff.getCode());
    action.setCoCode(staff.getStaffPosition().getOrg().getCompany().getCode());
    action.setOrgCode(staff.getStaffPosition().getOrg().getCode());
    action.setPosiCode(staff.getStaffPosition().getPosition().getCode());
    action.setNd(staff.getStaffPosition().getOrg().getNd());
    
    return action;
  }

}
