package com.seekon.mars.wf.client.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.seekon.mars.wf.client.internal.ServiceFacade;
import com.seekon.mars.wf.engine.model.participant.NodeStaffModel;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;
import com.seekon.mars.wf.engine.spi.util.DateUtil;
import com.seekon.mars.wf.service.BusinessService;
import com.seekon.mars.wf.service.RuntimeService;

public class TraceAccessUtil {
  public static List getInstanceAction(Long instanceId){
    ActionModel action=new ActionModel();
    action.setInstanceId(instanceId);
    return getRuntimeService().getAction(action);
  }
  
  public static List getInstanceActionHistory(Long instanceId,Long nodeId){
    ActionHistoryModel actionHistory=new ActionHistoryModel();
    actionHistory.setInstanceId(instanceId);
    actionHistory.setNodeId(nodeId);
    return getRuntimeService().getActionHistory(actionHistory);
  }
  
  public static List getTemplateActionHistory(Long templateId,Long nodeId){
    return getRuntimeService().getTemplateActionHistory(templateId,nodeId);
  }
  
  public static Map<Long,ActionHistoryModel> instanceActionMap(Long instanceId){
    Map<Long,ActionHistoryModel> map=new HashMap<Long, ActionHistoryModel>();
    Iterator<?> itr=getInstanceActionHistory(instanceId,null).iterator();
    while(itr.hasNext()){
      ActionHistoryModel action=(ActionHistoryModel)itr.next();
      map.put(action.getNodeId(), action);
    }
    return map;
  }
  
  public static Map<Long,List<CurrentTaskModel>> instanceCurTaskMap(Long instanceId){
    Map<Long,List<CurrentTaskModel>> map =new HashMap<Long, List<CurrentTaskModel>>();
    List<?> tmpList=getInstanceCurtaskList(instanceId);
    if(tmpList.size()>0){
      Long curNodeId=((CurrentTaskModel)tmpList.get(0)).getNodeId();
      List<CurrentTaskModel> storList=new ArrayList<CurrentTaskModel>();
      Iterator<?> itr=tmpList.iterator();
      while(itr.hasNext()){
        CurrentTaskModel model=(CurrentTaskModel)itr.next();
        if(model.getNodeId().equals(curNodeId)){
          storList.add(model);
        }
        else{
          map.put(curNodeId, storList);
          curNodeId=model.getNodeId();
          storList=new ArrayList<CurrentTaskModel>();
        }
      }
      map.put(curNodeId, storList);
    }
    return map;
  }
  
  public static List sumTemplateAction(QueryElement queryElement){
    return getRuntimeService().sumTemplateAction(queryElement);
  }
  
  public static List getInstanceList(Long instanceId){
    return getRuntimeService().getInstanceList(instanceId);
  }
  
  public static List getInstanceCurtaskList(Long instanceId){
    CurrentTaskModel task=new CurrentTaskModel();
    task.setInstanceId(instanceId);
    task.setNd(DateUtil.getYear());
    return getRuntimeService().getCurrentTask(task);
  }
  
  private static RuntimeService getRuntimeService(){
    return ServiceFacade.getRuntimeService();
  }
  
  public static Map<Long,List<NodeStaffModel>> getCurTaskAllExecutorMap(Long instanceId){
    Map<Long, List<NodeStaffModel>> nodeExecutorMap=new HashMap<Long, List<NodeStaffModel>>();
    List<NodeStaffModel> list= getRuntimeService().getCurTaskAllExecutor(instanceId);
    Long curNodeId=null;
    List<NodeStaffModel> curNodeList=null;
    Iterator itr=list.iterator();
    while(itr.hasNext()){
      NodeStaffModel tmpModel=(NodeStaffModel)itr.next();
      if(curNodeId==null){
        curNodeId=tmpModel.getNodeId();
        curNodeList=new ArrayList<NodeStaffModel>();
        curNodeList.add(tmpModel);
      }
      else{
        Long tmpNodeId= tmpModel.getNodeId();
        if(curNodeId.equals(tmpNodeId)){
          curNodeList.add(tmpModel);
        }
        else{
          nodeExecutorMap.put(curNodeId, curNodeList);
          curNodeId=tmpNodeId;
          curNodeList=new ArrayList<NodeStaffModel>();
          curNodeList.add(tmpModel);
        }
      }
    }
    if(curNodeId!=null && curNodeList!=null){
      nodeExecutorMap.put(curNodeId, curNodeList);
    }
    return nodeExecutorMap;
  }
  
  public static boolean isInstanceFinish(Long instanceId){
    return getBusinessService().isInstanceFinish(instanceId);
  }
  
  private static BusinessService getBusinessService(){
    return ServiceFacade.getBusinessService(); 
  }
}

