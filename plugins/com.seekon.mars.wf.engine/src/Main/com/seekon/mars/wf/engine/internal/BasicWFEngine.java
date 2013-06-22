package com.seekon.mars.wf.engine.internal;

import java.util.Stack;

import com.google.inject.Inject;
import com.seekon.mars.wf.engine.Configuration;
import com.seekon.mars.wf.engine.ExecutionService;
import com.seekon.mars.wf.engine.ManagementService;
import com.seekon.mars.wf.engine.MonitorService;
import com.seekon.mars.wf.engine.RepositoryService;
import com.seekon.mars.wf.engine.WFEngine;
import com.seekon.mars.wf.engine.event.ProcessInstanceListener;
import com.seekon.mars.wf.engine.event.TaskEventListener;
import com.seekon.mars.wf.engine.event.WorkflowEventBroker;
import com.seekon.mars.wf.engine.internal.event.BasicProcessInstanceListener;
import com.seekon.mars.wf.engine.internal.event.BasicTaskEventListener;
import com.seekon.mars.wf.engine.internal.interceptor.AuthorizationInterceptor;
import com.seekon.mars.wf.engine.internal.interceptor.EnvironmentInterceptor;
import com.seekon.mars.wf.engine.internal.interceptor.InitCurrentNodeInterceptor;
import com.seekon.mars.wf.engine.internal.interceptor.WorkflowContextInterceptor;

/**
 * 
 * 引擎默认实现类
 * 
 */
public class BasicWFEngine implements WFEngine {

  @Inject
	private ExecutionService executionService;

  @Inject
	private MonitorService historyService;

  @Inject
	private ManagementService managementService;

  @Inject
	private RepositoryService repositoryService;
	
  @Inject
	private WorkflowEventBroker workflowEventBroker;
	
	public BasicWFEngine(){}
	
	public BasicWFEngine(ExecutionService executionService,
			MonitorService historyService, ManagementService managementService,
			RepositoryService repositoryService, WorkflowEventBroker workflowEventBroker) {
		this.executionService = executionService;
		this.historyService = historyService;
		this.managementService = managementService;
		this.repositoryService = repositoryService;
		this.workflowEventBroker = workflowEventBroker;
	}
	
	/**
	 * bean创建后的初始化回调方法
	 */
	public void init(){
	  this.workflowEventBroker.addProcessInstanceListener(new BasicProcessInstanceListener());
	  this.workflowEventBroker.addTaskEventListener(new BasicTaskEventListener());
	  
	  String proListenerList = Configuration.get(Configuration.PROCESS_EVENT_LISTENER_LIST);
	  if(proListenerList != null && proListenerList.length() > 0){
	    String[] tmpList = proListenerList.split("[,]");
	    for(int i = 0; i < tmpList.length; i++){
	      Object listener = null;
        try {
          listener = Class.forName(tmpList[i].trim()).newInstance();
        } catch (Exception e) {
          e.printStackTrace();
        } 
	      if(listener != null && listener instanceof ProcessInstanceListener){
	        this.workflowEventBroker.addProcessInstanceListener((ProcessInstanceListener)listener);
	      }
	    }
	  }
	  
	  String taskListenerList = Configuration.get(Configuration.TASK_EVENT_LISTENER_LIST);
	  if(taskListenerList != null && taskListenerList.length() > 0){
      String[] tmpList = taskListenerList.split("[,]");
      for(int i = 0; i < tmpList.length; i++){
        Object listener = null;
        try {
          listener = Class.forName(tmpList[i].trim()).newInstance();
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if(listener != null && listener instanceof TaskEventListener){
          this.workflowEventBroker.addTaskEventListener((TaskEventListener)listener);
        }
      }
    }
	}
	
	/**
	 * bean销毁时的析构回调方法
	 */
	public void destroy(){
	  workflowEventBroker.clear();
	}
	
	public ExecutionService getExecutionService() {
		Stack interceptorStack = new Stack();
		interceptorStack.push(new InitCurrentNodeInterceptor());
		interceptorStack.push(new EnvironmentInterceptor());
		interceptorStack.push(new AuthorizationInterceptor());
		interceptorStack.push(new WorkflowContextInterceptor());

		EngineServiceInterceptor interceptor = new EngineServiceInterceptor(
				executionService, interceptorStack);
		return (ExecutionService) interceptor.getProxy();
	}

	public MonitorService getMonitorService() {
		EngineServiceInterceptor interceptor = new EngineServiceInterceptor(
				historyService);
		return (MonitorService) interceptor.getProxy();
	}

	public ManagementService getManagementService() {
		Stack interceptorStack = new Stack();
		interceptorStack.push(new InitCurrentNodeInterceptor());
		interceptorStack.push(new EnvironmentInterceptor());
		interceptorStack.push(new AuthorizationInterceptor());
		interceptorStack.push(new WorkflowContextInterceptor());
		EngineServiceInterceptor interceptor = new EngineServiceInterceptor(
				managementService,interceptorStack);
		return (ManagementService) interceptor.getProxy();
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

  public WorkflowEventBroker getWorkflowEventBroker() {
    return workflowEventBroker;
  }

}
