package com.seekon.mars.wf.engine.internal.interceptor;

import java.util.Iterator;

import com.seekon.mars.wf.engine.WFClient;
import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.BindVariableModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
/**
 * 
 * 设置工作流运行期环境的拦截器
 *
 */
public class EnvironmentInterceptor implements Interceptor{

  private static final long serialVersionUID = -604581738419107923L;

  public Object invoke(Invocation invocation) throws Throwable{
    try{
      initEnvironment();
      return invocation.invoke();
    }finally{
      Environment.setEnvironment(null);
    }
  }
  
  private void initEnvironment(){
    Environment env = new Environment();
    
    WorkflowContext ctx = WorkflowContext.getWorkflowContext();
    ////Assert.notNull(ctx, "工作流运行期环境中的workflowContext不能为空!");
    env.setContext(ctx);
    
    Long templateId = ctx.getTemplateId();
    if(templateId == null){
      if(ctx.getCompoId() != null){//根据部件代码获取模板
    	  
      }else if(ctx.getInstanceId() != null){//根据当前流程实例获取模板
        templateId = env.getInstance().getTemplateId();
        ctx.setTemplateId(templateId);
      }
    }
    ////Assert.notNull(templateId, "工作流运行期环境中的template不能为空!");
    env.setTemplate(WFClient.getWFEngine().getRepositoryService().getTemplate(templateId));
    
    env.setCurrentExecutor(new StaffModel(ctx.getSessionMap()));
    if(ctx.getSelectedNextLinkId() != null){
      env.setManual(true);
    }
    
    initBindVariableValue(env);//设置定义的变量
    
    env.setWorkflowEventBroker((WFClient.getWFEngine()).getWorkflowEventBroker());//设置监听处理器
    
    Environment.setEnvironment(env);
  }
  
  private void initBindVariableValue(Environment env){
    TemplateModel template = env.getTemplate();
    Iterator iterator = template.getBindVariableList().iterator();
    while(iterator.hasNext()){
      BindVariableModel variable = (BindVariableModel)iterator.next();
      Object result = WFDaoFactory.getWFRuntimeDao().getBindVariableValue(variable
             , env.getContext().getInstanceId());
      env.setVariable(variable.getName(), result);
    }
  }
}
