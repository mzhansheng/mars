package com.seekon.mars.wf.engine.internal.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;
import com.seekon.mars.wf.engine.spi.util.DateUtil;
import com.seekon.mars.wf.engine.spi.util.InstanceXmlUtil;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 处理实例重做
 * @author Administrator
 *
 */
public class ProcessReworkInstanceCmd extends AbstractCommand{

  private static final long serialVersionUID = -5897822014768335594L;

  public void doExecute(Environment env) throws CommandException {
    Long instanceId = env.getContext().getInstanceId();
    TemplateModel template=env.getTemplate();
    NodeModel startNode=template.getStartNode();
    WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
    InstanceHistoryModel instanceHistory = runtimeDao.getInstanceHistoryById(instanceId);
    
    if(instanceHistory.getStatus().intValue()==WFConst.INSTANCE_STATUS_FINISH){
      InstanceModel instance=new InstanceModel();
      instance.setInstanceId(instanceHistory.getInstanceId());
      instance.setDescription(instanceHistory.getDescription());
      instance.setName(instanceHistory.getName());
      instance.setTemplateId(instanceHistory.getTemplateId());
      instance.setOwner(instanceHistory.getOwner());
      instance.setStartTime(instanceHistory.getStartTime());
      instance.setEndTime(WFConst.INITIAL_END_TIME);
      instance.setStatus(new Long(WFConst.INSTANCE_STATUS_ACTIVE));
      instance.setParentInstanceId(instanceHistory.getParentInstanceId());
      runtimeDao.createInstanceFromHistory(instance);//重新创建实例
      
      String xmlStr=instanceHistory.getInstanceTrace();
      Long targetNodeId=env.getCurrentNode().getNodeId();
      List actionList=new ArrayList();
      LinkedList nodeList=InstanceXmlUtil.instanceTransList(actionList,template, xmlStr, startNode.getNodeId(), targetNodeId);
      while(!nodeList.isEmpty()){
        //初始化pass信息
        NodeModel node=(NodeModel)nodeList.removeFirst();
        if(node.getNodeId().equals(targetNodeId))break;
        Iterator linkItr=node.getToLinkList().iterator();
        while(linkItr.hasNext()){
          //恢复实例的pass信息
          LinkModel link = (LinkModel)linkItr.next();
          PassModel pass = new PassModel();
          pass.setCurrentNodeId(link.getCurrentNodeId());
          pass.setInstanceId(env.getInstance().getInstanceId());
          pass.setNextNodeId(link.getNextNodeId());
          pass.setNodeLinkId(link.getNodeLinkId());
          runtimeDao.createPass(pass);
        }
      }
      
      Iterator itr=actionList.iterator();
      while(itr.hasNext()){
        //初始化action信息
        ActionModel action=(ActionModel)itr.next();
        if(action.getNodeId().equals(targetNodeId)){
          //为当前节点创建代办任务
          CurrentTaskModel task=new CurrentTaskModel();
          task.setInstanceId(instanceId);
          task.setNodeId(action.getNodeId());
          task.setCreateTime(DateUtil.getTime());
          task.setCreator(env.getCurrentExecutor().getCode());
          task.setOwner(action.getOwner());
          task.setResponsibility(env.getContext().getResponsibility());
          task.setExecutor(action.getExecutor());
          task.setCoCode(action.getCoCode());
          task.setOrgCode(action.getOrgCode());
          task.setPosiCode(action.getPosiCode());
          task.setNd(DateUtil.getYear());
          runtimeDao.createCurrentTask(task);
        }
        else{
          //恢复实例的action信息
          runtimeDao.createAction(action);
        }
      }
      runtimeDao.removeInstanceHistoryById(instanceId);
    }else{
      throw new CommandException("实例没有结束，不能重做！");
    }
  }

}
