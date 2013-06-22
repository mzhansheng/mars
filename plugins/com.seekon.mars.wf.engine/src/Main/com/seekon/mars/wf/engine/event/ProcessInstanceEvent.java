package com.seekon.mars.wf.engine.event;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 流程实例操作改变时发送的事件
 *
 */
public class ProcessInstanceEvent extends WorkflowEvent{

  private static final long serialVersionUID = -4885480587638520042L;

  public static final String ACTIVATE = WFConst.ACTION_TYPE_ACTIVATE_INSTANCE;// 激活

  public static final String DEACTIVATE = WFConst.ACTION_TYPE_DEACTIVATE_INSTANCE;// 冻结

  public static final String RESTART = WFConst.ACTION_TYPE_RESTART_INSTANCE;// 重启

  public static final String REWORK = WFConst.ACTION_TYPE_REWORK_INSTANCE;// 重做
  
  public static final String SUSPEND = WFConst.ACTION_TYPE_SUSPEND_INSTANCE;// 挂起
  
  public static final String TERMINATED = WFConst.ACTION_TYPE_TERMINATED_INSTANCE;// 终止
  
  public static final String START = WFConst.ACTION_TYPE_START_INSTANCE;// 启动
  
  public ProcessInstanceEvent(Object source, String id, Environment env) {
    super(source, id, env);
  }

}
