package com.seekon.mars.wf.service;

import java.util.List;
import java.util.Map;

import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;
import com.seekon.mars.wf.engine.model.runtime.TableData;

/**
 * 
 * 工作流运行期服务接口，提供推动工作流引擎的方法接口
 *
 */
public interface RuntimeService {

  /**
   * 创建工作流草稿
   * @param draftName
   * @param compoId
   * @param masterTableId
   * @param ctx
   */
  public void createDraft(String draftName, String compoId, String masterTableId);

  /**
   * 挂起工作流实例
   * @param ctx
   */
  public void suspend(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data);

  /**
   * 重启工作流实例
   * @param ctx
   */
  public void restart(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, Long currentNodeId, TableData data);

  /**
   * 终止工作流实例
   * @param ctx
   */
  public void terminated(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data);

  /**
   * 激活工作流实例
   * @param ctx
   */
  public void activate(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data);

  /**
   * 冻结工作流实例
   * @param ctx
   */
  public void deactivate(Map sessionMap, Long instanceId, String compoId,
    String instanceDesc, TableData data);

  /**
   * 启动工作流实例
   * @param ctx
   */
  public void startProcessInstance(Map sessionMap, Long draftId,
    String instanceDesc, String compoId, Long templateId, TableData data, Map variableMap);

  /**
   * 送审
   */
  public void commit(Map sessionMap, Long instanceId, TableData data, Map variableMap);

  /**
   * 手工选择人或分支提交
   * @param sessionMap
   * @param instanceId
   */
  public void commit(Map sessionMap, Long instanceId, Long selectedNextLinkId,
    List selectedNextExecutorList, TableData data, Map variableMap);

  /**
   * 收回(流程实例未完成，由本人收回)
   */
  public void callback(Map sessionMap, Long instanceId, Long currentNodeId, TableData data,
    Map variableMap);

  /**
   * 移交
   */
  public void handover(Map sessionMap, Long instanceId,
    List selectedNextExecutorList, TableData data, Map variableMap);

  /**
   * 重做(流程实例已经完成的回退)
   */
  public void rework(Map sessionMap, Long instanceId, Long currentNodeId, TableData data,
    Map variableMap);

  /**
   * 回退（流程实例未完成的回退，其他人（也可能本人）的强制回退）
   */
  public void untread(Map sessionMap, Long instanceId, Long selectedRebackLinkId, TableData data,
    Map variableMap);

  /**
   * 运行期增加执行者
   * @param ctx
   */
  public void addRuntimeExecutor(Map sessionMap, Long instanceId,
    Long currentNodeId, List selectedNextExecutorList);

  public List getAction(ActionModel action);

  public List getActionHistory(ActionHistoryModel actionHistory);

  public List getTemplateActionHistory(Long templateId, Long nodeId);

  public List sumTemplateAction(QueryElement queryElement);

  public List getInstanceList(Long instanceId);

  public List getCurrentTask(CurrentTaskModel task);
  
  public List getCurTaskAllExecutor(Long instanceId);
}
