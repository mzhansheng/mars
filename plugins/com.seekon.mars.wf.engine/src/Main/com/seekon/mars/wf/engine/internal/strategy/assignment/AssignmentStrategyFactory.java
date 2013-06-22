package com.seekon.mars.wf.engine.internal.strategy.assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.strategy.AssignmentStrategy;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 任务分配策略的工厂类，根据模板定义中的节点获取不同的策略
 *
 */
public class AssignmentStrategyFactory {

  public static AssignmentStrategy[] getAssignmentStrategy(Environment env){
    if(env.isManual()){
      env.resetNextLinkStaffSet();//重置nextLinkStaffList
      Long linkId = env.getContext().getSelectedNextLinkId();
      return new AssignmentStrategy[]{new AnyiAssignmentStrategy(env.getTemplate().getLink(linkId))};
    }
    
    NodeModel node = env.getCurrentNode();
    ////Assert.notNull(node, "AssignmentStrategyFactory类中的参数node不能为空!");
    
    List assignmentStrategyList = new ArrayList();
    List linkList = null;
    if(env.getNextLinkStaffSet().isEmpty()){
      linkList = node.getToLinkList();
    }else{
      linkList = new ArrayList();
      Iterator iterator = env.getNextLinkStaffSet().iterator();
      while(iterator.hasNext()){
        linkList.add(((LinkStaffModel)iterator.next()).getLink());
      }
      env.resetNextLinkStaffSet();//重置nextLinkStaffList
    }
    
    
    Set resultLinkSet = new HashSet();//考虑此流向对应的节点为分支节点
    for(int i = 0; i < linkList.size(); i++){
      LinkModel link = (LinkModel)linkList.get(i);
      recursiveIterateLink(link, resultLinkSet);
    }
    
    Iterator iterator = resultLinkSet.iterator();
    while(iterator.hasNext()){
      LinkModel link = (LinkModel)iterator.next();
      if(WFConst.EXECUTOR_RELATION_BUSINESS_SUPPERIOR.equals(link.getExecutorRelation())){
        assignmentStrategyList.add(new BusinessJuniorAssignmentStrategy(link));
      }else if(WFConst.EXECUTOR_RELATION_MANAGER.equals(link.getExecutorRelation())){
        assignmentStrategyList.add(new OrgJuniorAssignmentStrategy(link));
      }else{
        assignmentStrategyList.add(new AnyiAssignmentStrategy(link));
      }
    }
    return (AssignmentStrategy[])assignmentStrategyList.toArray(new AssignmentStrategy[assignmentStrategyList.size()]);
  }
  
  /**
   * 跳过分支节点获取link列表
   * @param link
   * @param resultSet
   */
  public static void recursiveIterateLink(LinkModel link, Set resultSet){
    NodeModel node = link.getNextNode();
    if(node == null || node.equals(node.getTemplate().getEndNode())){
      resultSet.add(link);
      return;
    }
    
    String nodeType = node.getType();
    if(nodeType.equals(WFConst.TASK_NODE)){
      resultSet.add(link);
      return;
    }
    
    Iterator iterator = node.getToLinkList().iterator();
    while(iterator.hasNext()){
      LinkModel tmpLink = (LinkModel)iterator.next();
      recursiveIterateLink(tmpLink, resultSet);
    }
  }
  
}
