package com.seekon.mars.wf.engine.internal.strategy.assignment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seekon.mars.wf.engine.exception.WorkflowException;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.expression.ExpressionEvaluator;
import com.seekon.mars.wf.engine.internal.strategy.AssignmentStrategy;
import com.seekon.mars.wf.engine.model.DelegationModel;
import com.seekon.mars.wf.engine.model.ExecutorSourceModel;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;

/**
 * 
 * 对当前节点上的所有路径进行处理，分配代办的下一节点任务
 *
 */
public abstract class AbstractAssignmentStrategy implements AssignmentStrategy{
  
  protected LinkModel link;
  
  protected Set nextExecutorSet = new HashSet();
  
  protected LinkStaffModel linkStaff = null;
  
  public Set apply(Environment env) throws WorkflowException{
    before(env);
    if(linkStaff == null){
      return new HashSet();
    }else if(link.getNextNode().equals(env.getTemplate().getEndNode())){//结束节点
      env.addNextLinkStaff(linkStaff);
      return new HashSet();
    }
    
    doApply(env);
    return after(env);
  }
  
  public void before(Environment env) throws WorkflowException{
    if(env.isManual()){//人工选择方式，不需要设置执行路�?
      linkStaff = new LinkStaffModel(link);
      return;
    }
    
    ////Assert.notNull(link, "代办任务分配策略中的link成员不能为空!");
    
    boolean match = ExpressionEvaluator.matchExpr(link.getExpression(), env.getAllVariableMap());
    if(match){
      linkStaff = new LinkStaffModel(link);
    }
  }
  
  public Set after(Environment env) throws WorkflowException{
    List executorSourceList = link.getNextNode().getExecutorSourceList();
    if(executorSourceList != null && executorSourceList.size() > 0){//模板中设置的执行者，当前仅支持模版中设置职位和职员的情况
      for(int j = 0; j < executorSourceList.size(); j++){
        ExecutorSourceModel executorSource = (ExecutorSourceModel)executorSourceList.get(j);
        if(!String.valueOf(executorSource.getNd()).equals( env.getContext().getSessionMap().get("svNd"))) continue;
        nextExecutorSet.add(new StaffModel(executorSource));
      }
    }
    
    Set dNextExecutorSet = new HashSet();
    Iterator dIterator = link.getNextNode().getDelegationMap().entrySet().iterator();
    while(dIterator.hasNext()){
      DelegationModel delegation = (DelegationModel)((Map.Entry)dIterator.next()).getValue();
      Iterator iterator = nextExecutorSet.iterator();
      while(iterator.hasNext()){
        if(delegation.senderContains((StaffModel)iterator.next())){
          dNextExecutorSet.add(delegation.getReceiverStaff());
        }
      }
    }
    nextExecutorSet.addAll(dNextExecutorSet);
    
    Set taskList = new HashSet();
    linkStaff.setStaffList(nextExecutorSet);
    env.addNextLinkStaff(linkStaff);
    
    StaffModel currentExecutor = env.getCurrentExecutor();
    Iterator iterator = nextExecutorSet.iterator();
    while(iterator.hasNext()){
      StaffModel staff = (StaffModel)iterator.next();
      
      CurrentTaskModel task = new CurrentTaskModel();
      task.setNodeId(linkStaff.getLink().getNextNodeId());
      task.setCreateTime(DateUtil.getTime());
      task.setCreator(currentExecutor.getCode());
      task.setInstanceId(env.getInstance().getInstanceId());
      task.setOwner(staff.getCode());
      task.setResponsibility(env.getContext().getResponsibility());
      task.setExecutor(staff.getCode());
      task.setCoCode(staff.getStaffPosition().getOrg().getCompany().getCode());
      task.setOrgCode(staff.getStaffPosition().getOrg().getCode());
      task.setPosiCode(staff.getStaffPosition().getPosition().getCode());
      task.setNd(staff.getStaffPosition().getOrg().getNd());
      task.setDelegationId(staff.getDelegationId());
      
      taskList.add(task);
    }
    
    return taskList;
  }
  
  public abstract void doApply(Environment env)throws WorkflowException;
}
