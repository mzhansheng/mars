package com.seekon.mars.wf.engine.event;

import java.util.EventListener;

/**
 * 
 * 工作流运行时事件的监听器，运行时的事件监听器都继承于此类
 *
 */
public interface WorkflowEventListener extends EventListener{

  void onEvent(WorkflowEvent event);
}
