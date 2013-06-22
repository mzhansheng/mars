package com.seekon.mars.wf.adaptor.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.seekon.mars.wf.engine.WFClient;
import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;
import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.engine.util.WFConst;
import com.seekon.mars.wf.service.RuntimeService;

public class RuntimeServiceImpl implements RuntimeService{
  private WorkflowContext ctx;
  
  private void initWfContext(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data,Map variableMap){
    ctx = new WorkflowContext();
    ctx.setSessionMap(sessionMap);
    ctx.setInstanceId(instanceId);
    if(compoId!=null)ctx.setCompoId(compoId);
    if(data!=null)ctx.setTableData(data);
    if(variableMap!=null)ctx.setVariableMap(variableMap);
    if(instanceDesc!=null)ctx.setVariable("instanceDesc", instanceDesc);
  }
  
  public void activate(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data) {
    initWfContext(sessionMap,instanceId,compoId,instanceDesc,data,null);
    ctx.setAction(WFConst.ACTION_TYPE_ACTIVATE_INSTANCE);
    WFClient.getWFEngine().getManagementService().activate(ctx);
  }

  public void addRuntimeExecutor(Map sessionMap, Long instanceId,
    Long currentNodeId, List selectedNextExecutorList) {
    initWfContext(sessionMap,instanceId,null,null,null,null);
    ctx.setInstanceId(instanceId);
    ctx.setCurrentNodeId(currentNodeId);
    ctx.setAction(WFConst.ACTION_TYPE_ADD_EXECUTOR_FLOW);
    Iterator itr=selectedNextExecutorList.iterator();
    while(itr.hasNext()){
    ctx.addSelectedNextExecutor((Map)itr.next());
    }
    WFClient.getWFEngine().getExecutionService().addRuntimeExecutor(ctx);
  }

  public void callback(Map sessionMap, Long instanceId, Long currentNodeId, TableData data,
    Map variableMap) {
    initWfContext(sessionMap,instanceId,null,null,data,variableMap);
    ctx.setCurrentNodeId(currentNodeId);
    ctx.setAction(WFConst.ACTION_TYPE_CALLBACK_FLOW);
    WFClient.getWFEngine().getExecutionService().callback(ctx);
  }

  public void commit(Map sessionMap, Long instanceId, TableData data, Map variableMap) {
    initWfContext(sessionMap,instanceId,null,null,data,variableMap);
    ctx.setAction(WFConst.ACTION_TYPE_COMMIT_TASK);
    WFClient.getWFEngine().getExecutionService().commit(ctx);
  }

  public void commit(Map sessionMap, Long instanceId, Long selectedNextLinkId,
    List selectedNextExecutorList, TableData data, Map variableMap) {
    initWfContext(sessionMap,instanceId,null,null,data,variableMap);
    ctx.setAction(WFConst.ACTION_TYPE_COMMIT_TASK);
    ctx.setSelectedNextLinkId(selectedNextLinkId);
    Iterator itr=selectedNextExecutorList.iterator();
    while(itr.hasNext()){
    ctx.addSelectedNextExecutor((Map)itr.next());
    }
    WFClient.getWFEngine().getExecutionService().commit(ctx);
  }

  public void createDraft(String draftName, String compoId, String masterTableId) {
    ctx = new WorkflowContext();
    WFClient.getWFEngine().getManagementService().createDraft(draftName, compoId, masterTableId, ctx);
  }

  public void deactivate(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data) {
    initWfContext(sessionMap,instanceId,compoId,instanceDesc,data,null);
    ctx.setAction(WFConst.ACTION_TYPE_DEACTIVATE_INSTANCE);
    WFClient.getWFEngine().getManagementService().deactivate(ctx);
  }

  public void handover(Map sessionMap, Long instanceId,
    List selectedNextExecutorList, TableData data, Map variableMap) {
    initWfContext(sessionMap,instanceId,null,null,data,variableMap);
    Iterator itr=selectedNextExecutorList.iterator();
    while(itr.hasNext()){
      ctx.addSelectedNextExecutor((Map)itr.next());
    }
    ctx.setAction(WFConst.ACTION_TYPE_HANDOVER_TASK);
    WFClient.getWFEngine().getExecutionService().handover(ctx);
  }

  public void restart(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, Long currentNodeId, TableData data) {
    initWfContext(sessionMap,instanceId,compoId,instanceDesc,data,null);
    ctx.setAction(WFConst.ACTION_TYPE_RESTART_INSTANCE);
    ctx.setCurrentNodeId(currentNodeId);
    WFClient.getWFEngine().getManagementService().restart(ctx);
  }

  public void rework(Map sessionMap, Long instanceId, Long currentNodeId, TableData data,
    Map variableMap) {
    initWfContext(sessionMap,instanceId,null,null,data,variableMap);
    ctx.setAction(WFConst.ACTION_TYPE_RESTART_INSTANCE);
    ctx.setCurrentNodeId(currentNodeId);
    WFClient.getWFEngine().getExecutionService().rework(ctx);
  }

  public void startProcessInstance(Map sessionMap, Long draftId,
    String instanceDesc, String compoId, Long templateId, TableData data, Map variableMap) {
    initWfContext(sessionMap,draftId,compoId,instanceDesc,data,variableMap);
    ctx.setAction(WFConst.ACTION_TYPE_START_INSTANCE);
    ctx.setTemplateId(templateId);
    WFClient.getWFEngine().getExecutionService().startProcessInstance(ctx);
  }

  public void suspend(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data) {
    initWfContext(sessionMap,instanceId,compoId,instanceDesc,data,null);
    ctx.setAction(WFConst.ACTION_TYPE_SUSPEND_INSTANCE);
    WFClient.getWFEngine().getManagementService().suspend(ctx);
  }

  public void terminated(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data) {
    initWfContext(sessionMap,instanceId,compoId,instanceDesc,data,null);
    ctx.setAction(WFConst.ACTION_TYPE_TERMINATED_INSTANCE);
    WFClient.getWFEngine().getManagementService().terminated(ctx);
  }

  public void untread(Map sessionMap, Long instanceId, Long selectedRebackLinkId, TableData data,
    Map variableMap) {
    initWfContext(sessionMap,instanceId,null,null,data,variableMap);
    ctx.setAction(WFConst.ACTION_TYPE_UNTREAD_FLOW);
    ctx.setSelectedRebackLinkId(selectedRebackLinkId);
    WFClient.getWFEngine().getExecutionService().untread(ctx);
  }

  public List getAction(ActionModel action) {
    return WFClient.getWFEngine().getMonitorService().getAction(action);
  }

  public List getActionHistory(ActionHistoryModel actionHistory) {
    return WFClient.getWFEngine().getMonitorService().getActionHistory(actionHistory);
  }

  public List getCurrentTask(CurrentTaskModel task) {
    return WFClient.getWFEngine().getMonitorService().getCurrentTask(task);
  }

  public List getInstanceList(Long instanceId) {
    return WFClient.getWFEngine().getMonitorService().getInstanceList(instanceId);
  }

  public List getTemplateActionHistory(Long templateId, Long nodeId) {
    return WFClient.getWFEngine().getMonitorService().getTemplateActionHistory(templateId, nodeId);
  }

  public List sumTemplateAction(QueryElement queryElement) {
    return WFClient.getWFEngine().getMonitorService().sumTemplateAction(queryElement);
  }

  public List getCurTaskAllExecutor(Long instanceId) {
    return WFClient.getWFEngine().getMonitorService().getCurTaskAllExecutor(instanceId);
  }
  
}
