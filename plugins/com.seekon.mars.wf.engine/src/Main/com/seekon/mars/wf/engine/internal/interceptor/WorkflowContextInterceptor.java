package com.seekon.mars.wf.engine.internal.interceptor;

import com.seekon.mars.wf.engine.WorkflowContext;

/**
 * 
 * 对工作流上下文进行处理的拦截器
 *
 */
public class WorkflowContextInterceptor implements Interceptor{

  private static final long serialVersionUID = -4332503094524777772L;

  public Object invoke(Invocation invocation) throws Throwable{
    try{
      openWorkflowContext(invocation.getArgs());
      
      return invocation.invoke();
    }finally{
      closeWorkflowContext();
    }
  }

  private void initWorkflowContext(){
    WorkflowContext ctx = WorkflowContext.getWorkflowContext();
    ////Assert.notNull(ctx.getInstanceId(), "工作流上下文中的流程实例id不能为空!");
    ////Assert.notEmpty(ctx.getSessionMap(), "工作流上下文中的环境信息(sessionMap)不能为空!");

  }
  
  private void openWorkflowContext(Object[] args){
    if(args == null){
      return;
    }
    
    for(int i = 0; i < args.length; i++){
      Object argObject = args[i];
      if(argObject instanceof WorkflowContext){
        WorkflowContext.setWorkflowContext((WorkflowContext)argObject);
        break;
      }
    }
    
    initWorkflowContext();
  }

  private void closeWorkflowContext(){
    WorkflowContext.setWorkflowContext(null);
  }
}
