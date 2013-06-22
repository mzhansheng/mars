package com.seekon.mars.wf.engine;

import java.util.List;

import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;

/**
 * 工作流数据
 * 
 * @author Administrator
 * 
 */
public interface MonitorService {
	public void queryHistory(); // 添加历史
	
	public List getAction(ActionModel action);

  public List getActionHistory(ActionHistoryModel actionHistory);

  public List getTemplateActionHistory(Long templateId, Long nodeId);

  public List sumTemplateAction(QueryElement queryElement);

  public List getInstanceList(Long instanceId);

  public List getCurrentTask(CurrentTaskModel task);
  
  public List getCurTaskAllExecutor(Long instanceId);

}
