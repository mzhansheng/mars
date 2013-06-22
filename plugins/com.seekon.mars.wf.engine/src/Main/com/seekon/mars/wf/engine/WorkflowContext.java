package com.seekon.mars.wf.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.TableData;

/**
 * 
 * 工作流上下文，提供给应用程序调用的接口方法，应用程序可通过此类对上下文进行相关设置
 * 
 */
public class WorkflowContext implements Serializable {

	private static final long serialVersionUID = -4423533282554601471L;

	private Long instanceId;// 流程实例id

	private Long templateId;// 模板id

	private String compoId;// 部件代码
	
	private Map sessionMap;// 工作环境信息

	private List selectedNextExecutorList = new ArrayList();// 人工选择设置的下一执行者列表；可放置Map(包含任职信息)或StaffModel

	private Long selectedRebackLinkId; // 回退Link的Id
	
	private Long selectedNextLinkId;// 人工选择的下一执行分支

	private Long currentNodeId;// 用户程序定位的当前执行节点id，重做时作为将要重做的目标节点

	private Map variableMap = new HashMap();// 变量列表，可通过应用程序设置相关变量

	private Long responsibility = new Long(1);// 主办或辅办
	
	private String action = null;// 当前操作名称

	private TableData tableData = null;//业务单据数据对象

	private static final ThreadLocal currentConext = new ThreadLocal();

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getCompoId() {
    return compoId;
  }

  public void setCompoId(String compoId) {
    this.compoId = compoId;
  }

  public Map getSessionMap() {
		return sessionMap == null ? this.sessionMap = new HashMap()
				: sessionMap;
	}

	public void setSessionMap(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	public List getSelectedNextExecutorList() {
		return selectedNextExecutorList;
	}

	public void addSelectedNextExecutor(Map staffInfo) {
		selectedNextExecutorList.add(staffInfo);
	}

	public void addSelectedNextExecutor(StaffModel staffInfo) {
		selectedNextExecutorList.add(staffInfo);
	}

	public Long getSelectedNextLinkId() {
		return selectedNextLinkId;
	}

	public void setSelectedNextLinkId(Long selectedNextLinkId) {
		this.selectedNextLinkId = selectedNextLinkId;
	}

	public Map getVariableMap() {
		return variableMap == null ? new HashMap() : variableMap;
	}

	public void setVariableMap(Map variableMap) {
		this.variableMap = variableMap;
	}

	public void setVariable(Object name, Object value) {
		variableMap.put(name, value);
	}

	public Object getVariable(Object name) {
		return variableMap.get(name);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Long responsibility) {
		this.responsibility = responsibility;
	}

	public Long getCurrentNodeId() {
		return currentNodeId;
	}

	public void setCurrentNodeId(Long currentNodeId) {
		this.currentNodeId = currentNodeId;
	}

	public TableData getTableData() {
		return tableData == null ? this.tableData = new TableData() : tableData;
	}

	public void setTableData(TableData tableData) {
		this.tableData = tableData;
	}

	public static void setWorkflowContext(WorkflowContext ctx) {
		currentConext.set(ctx);
	}

	public static WorkflowContext getWorkflowContext() {
		return (WorkflowContext) currentConext.get();
	}

	public Long getSelectedRebackLinkId() {
		return selectedRebackLinkId;
	}

	public void setSelectedRebackLinkId(Long selectedRebackLinkId) {
		this.selectedRebackLinkId = selectedRebackLinkId;
	}
}
