package com.seekon.mars.wf.engine.internal.interceptor;

import java.util.List;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.DelegationModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 初始化当前运行环境中的当前节点信息
 * 1、如果context中存在currentNodeId，则直接从模板中获取
 * 2、如果context中不存在currentNodeId，则查询数据库中的代办任务列表，并从中获取一条作为当前节点
 * 3、如果当前任务为代理授权而来的任务，则将Environment中的currentNode改为被授权人的信息
 * 
 */
public class InitCurrentNodeInterceptor implements Interceptor{

  private static final long serialVersionUID = 6561467137627039410L;

  public Object invoke(Invocation invocation) throws Throwable {
    Environment env = Environment.getEnvironment();
    if(env == null){
      return invocation.invoke();
    }
    if(WFConst.ACTION_TYPE_START_INSTANCE.equals(env.getContext().getAction())){
      env.setCurrentNode(env.getTemplate().getStartNode());
      env.getContext().setCurrentNodeId(env.getCurrentNode().getNodeId());
      return invocation.invoke();
    }
    
    NodeModel currentNode = env.getCurrentNode();// currentNode检测及设置
    if (currentNode == null) {
      Long nodeId = env.getContext().getCurrentNodeId();
      if (nodeId == null) {
        List taskList = env.getCurrentTaskList();
        if (taskList == null) {
          StaffModel executor = env.getCurrentExecutor();
          CurrentTaskModel task = new CurrentTaskModel();
          task.setInstanceId(env.getInstance().getInstanceId());
          task.setExecutor(executor.getCode());
          //task.setNodeId(env.getContext().getCurrentNodeId());
          task.setCoCode(executor.getStaffPosition().getOrg()
              .getCompany().getCode());
          task.setOrgCode(executor.getStaffPosition().getOrg()
              .getCode());
          task.setPosiCode(executor.getStaffPosition().getPosition()
              .getCode());
          task.setNd(executor.getStaffPosition().getOrg().getNd());

          taskList = WFDaoFactory.getWFRuntimeDao().getCurrentTask(
              task);
//          System.out.println("coCode:" + task.getCoCode() + ";orgCode:" + task.getOrgCode()
//        		  + ";posiCode:" + task.getPosiCode() + ";nd:" + task.getNd()
//        		  + ";executor:" + task.getExecutor() + ";instance:" + task.getInstanceId());
        }
        if (taskList != null && taskList.size() > 0) {
          CurrentTaskModel task = (CurrentTaskModel) taskList.get(0);
          currentNode = env.getTemplate().getNode(task.getNodeId());
          env.setCurrentTaskList(taskList);
          
          DelegationModel delegation = currentNode.getDelegation(task.getDelegationId());//是否存在委派代理
          if(delegation != null){
            env.setDelegateExecutor(env.getCurrentExecutor());
            env.setCurrentExecutor(delegation.getSenderStaff());
          }
        }
      }else{
        currentNode = env.getTemplate().getNode(nodeId);
      }
      
//      System.out.println("env.getContext():" + env.getContext());
//      System.out.println("WorkflowContext.getWorkflowContext():" + WorkflowContext.getWorkflowContext());
//      System.out.println("currentNode:" + currentNode);
      env.setCurrentNode(currentNode);
      env.getContext().setCurrentNodeId(currentNode.getNodeId());
    }
    
    return invocation.invoke();
  }

}
