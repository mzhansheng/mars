package com.seekon.mars.wf.engine.event;

import com.seekon.mars.wf.engine.internal.Environment;
import com.seekon.mars.wf.engine.util.WFConst;

/**
 * 
 * 对当前任务操作时发送的相关事件
 *
 */
public class TaskEvent extends WorkflowEvent{

  private static final long serialVersionUID = -4925766298157208817L;
  
  public static final String AUTHORIZE = WFConst.ACTION_TYPE_AUTHORIZE_TASK;// 授权

  public static final String COMMIT = WFConst.ACTION_TYPE_COMMIT_TASK;// 提交

  public static final String HANDOVER = WFConst.ACTION_TYPE_HANDOVER_TASK;// 移交
  
  public static final String CALLBACK = WFConst.ACTION_TYPE_CALLBACK_FLOW;// 回收

  public static final String GIVEBACK = WFConst.ACTION_TYPE_GIVEBACK_FLOW;// 回退一步

  public static final String TRANSFER = WFConst.ACTION_TYPE_TRANSFER_FLOW;// 流程跳转

  public static final String UNTREAD = WFConst.ACTION_TYPE_UNTREAD_FLOW;// 回退多步
  
  public TaskEvent(Object source, String id, Environment env) {
    super(source, id, env);
  }

}
