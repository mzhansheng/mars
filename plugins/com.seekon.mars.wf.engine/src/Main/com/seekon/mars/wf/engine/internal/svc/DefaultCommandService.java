package com.seekon.mars.wf.engine.internal.svc;

import com.seekon.mars.wf.engine.WorkflowContext;
import com.seekon.mars.wf.engine.event.TaskListener;
import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.internal.cmd.Command;
import com.seekon.mars.wf.engine.internal.cmd.CommandChain;
import com.seekon.mars.wf.engine.internal.cmd.CommandService;
import com.seekon.mars.wf.engine.model.NodeModel;

public class DefaultCommandService implements CommandService{

  public void execute(Command command) {
    command.execute(Environment.getEnvironment());
  }
  
  public void execute(CommandChain chain) {
    Environment env = Environment.getEnvironment();
    WorkflowContext ctx = env.getContext();
    NodeModel currentNode = env.getCurrentNode();
    
    //Assert.notNull(currentNode, "上下文中的当前节点不能为空！");
    TaskListener listener = null;
    if(currentNode != null){
      String listenerName = currentNode.getTaskListener();
      try {
        if (listenerName != null && listenerName.length() > 0) {
          Object tmp = Class.forName(listenerName).newInstance();
          if (tmp instanceof TaskListener) {
            listener = (TaskListener) tmp;
          }
        }

        if (listener != null) {
          listener.beforeExecute(ctx);
        }
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    
    chain.execute(env);
    
    if(listener != null){
      listener.afterExecute(ctx);
    }
  }

}
