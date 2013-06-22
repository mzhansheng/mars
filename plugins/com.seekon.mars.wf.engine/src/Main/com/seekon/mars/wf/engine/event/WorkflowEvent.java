package com.seekon.mars.wf.engine.event;

import java.util.EventObject;

import com.seekon.mars.wf.engine.internal.Environment;

/**
 * 
 * 工作流运行时的事件类，其他所有运行时的事件都继承于此类
 *
 */
public class WorkflowEvent extends EventObject{

  private static final long serialVersionUID = 948869185727852264L;
  
  protected final String _id;
  
  protected final Environment env;
  
  public WorkflowEvent(Object source, String id, Environment env) {
    super(source);
    this._id = id;
    this.env = env;
  }

  public String getId(){
    return this._id;
  }
  
  public Environment getEnvironment(){
    return this.env;
  }
}
