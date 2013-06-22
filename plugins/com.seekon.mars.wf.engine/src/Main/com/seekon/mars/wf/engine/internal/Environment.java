package com.seekon.mars.wf.engine.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.event.WorkflowEventBroker;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.NodeModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;
import com.seekon.mars.wf.engine.model.runtime.LinkStaffModel;

/**
 * 
 * 工作流运行期环境，此环境包含应用程序所设置的工作流上下文、引擎内部所使用的数据
 * 
 */
public class Environment implements Serializable {

	private static final long serialVersionUID = -341108853800403687L;

	private Map variableMap = new HashMap();

	// private Map returnValueMap = new HashMap();//command方法所执行后可设置的返回值信息

	private InstanceModel instance;// 当前流程实例

	private TemplateModel template;// 工作流模板

	private StaffModel currentExecutor;// 当前执行者
	
	private StaffModel delegateExecutor;//授权执行人
	
	private NodeModel currentNode;// 当前执行节点，由工作流引擎查询计算获取

	private Set nextLinkStaffSet = new HashSet();// 下一执行节点及人员信息

	private List currentTaskList;// 当前代办任务

	private List passPathList; //存储计算回退、收回时路径
	
	private List previousNodeList;// 存储计算回退节点列表（可能有多个节点）

	private WorkflowContext context;

	private boolean manual = false;// 是否人工选择执行节点
	
	private WorkflowEventBroker workflowEventBroker;
	
	private String taskArrivalTime;//任务到达时间
	
	private static final ThreadLocal currentEnvironment = new ThreadLocal();

	public List getPreviousNodeList() {
		return previousNodeList;
	}

	public void setPreviousNodeList(List previousNodeList) {
		this.previousNodeList = previousNodeList;
	}
	
	public List getPassPathList() {
		return passPathList;
	}

	public void setPassPathList(List passPathList) {
		this.passPathList = passPathList;
	}

	public StaffModel getCurrentExecutor() {
		return currentExecutor;
	}

	public void setCurrentExecutor(StaffModel currentExecutor) {
		this.currentExecutor = currentExecutor;
	}

	public StaffModel getDelegateExecutor() {
    return delegateExecutor;
  }

  public void setDelegateExecutor(StaffModel delegateExecutor) {
    this.delegateExecutor = delegateExecutor;
  }

  public InstanceModel getInstance() {
		if (instance == null) {
			WFRuntimeMapper runtimeDao = WFDaoFactory.getWFRuntimeDao();
			instance = runtimeDao.getInstanceById(context.getInstanceId());
			if(instance == null){
			  instance = runtimeDao.getInstanceHistoryById(context.getInstanceId());
			}
		}
		return instance;
	}

	public void setInstance(InstanceModel instance) {
		this.instance = instance;
	}

	public TemplateModel getTemplate() {
		return template;
	}

	public void setTemplate(TemplateModel template) {
		this.template = template;
	}

	public NodeModel getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(NodeModel currentNode) {
		this.currentNode = currentNode;
	}

	public Set getNextLinkStaffSet() {
		return nextLinkStaffSet;
	}

	public void addNextLinkStaff(LinkStaffModel linkStaff) {
		this.nextLinkStaffSet.add(linkStaff);
	}
	
	public synchronized void resetNextLinkStaffSet(){
	  this.nextLinkStaffSet.clear();
	}
	
	public Map getVariableMap() {
		return variableMap;
	}

	public Map getAllVariableMap() {// 返回Environment中的变量、context中的变量、Dabledata中的变量
		Map tmpMap = new HashMap();
		tmpMap.putAll(context.getVariableMap());
		tmpMap.putAll(variableMap);
		return tmpMap;
	}

	public void setVariableMap(Map variableMap) {
		this.variableMap = variableMap;
	}

	public List getCurrentTaskList() {
		return currentTaskList;
	}

	public void setCurrentTaskList(List currentTaskList) {
		this.currentTaskList = currentTaskList;
	}

	public WorkflowContext getContext() {
		return context;
	}

	public void setContext(WorkflowContext context) {
		this.context = context;
	}

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	public void setVariable(Object key, Object value) {
		variableMap.put(key, value);
	}

	public Object getVariable(Object key) {
		Object value = variableMap.get(key);
		if (value == null) {
			value = getContext().getVariableMap().get(key);
		}
		return value;
	}

	public WorkflowEventBroker getWorkflowEventBroker() {
    return workflowEventBroker;
  }

  public void setWorkflowEventBroker(WorkflowEventBroker workflowEventBroker) {
    this.workflowEventBroker = workflowEventBroker;
  }

  public static void setEnvironment(Environment env) {
		currentEnvironment.set(env);
	}

	public static Environment getEnvironment() {
		return (Environment) currentEnvironment.get();
	}
	
	public String getTaskArrivalTime() {
    return taskArrivalTime;
  }

  public void setTaskArrivalTime(String taskArrivalTime) {
    this.taskArrivalTime = taskArrivalTime;
  }
}
