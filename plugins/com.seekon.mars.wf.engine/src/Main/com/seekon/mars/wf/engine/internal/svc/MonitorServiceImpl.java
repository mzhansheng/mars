package com.seekon.mars.wf.engine.internal.svc;

import java.util.List;

import com.seekon.mars.wf.engine.MonitorService;
import com.seekon.mars.wf.engine.internal.mapper.WFRuntimeMapper;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;

public class MonitorServiceImpl extends AbstractServiceImpl implements
		MonitorService {
  private WFRuntimeMapper runtimeDao;

  public WFRuntimeMapper getRuntimeDao() {
    return runtimeDao;
  }

  public void setRuntimeDao(WFRuntimeMapper runtimeDao) {
    this.runtimeDao = runtimeDao;
  }

  public List getAction(ActionModel action) {
    return runtimeDao.getAction(action);
  }

  public List getActionHistory(ActionHistoryModel actionHistory) {
    return runtimeDao.getActionHistory(actionHistory);
  }

  public List getCurrentTask(CurrentTaskModel task) {
    return runtimeDao.getCurrentTask(task);
  }

  public List getInstanceList(Long instanceId) {
    return runtimeDao.getInstanceList(instanceId);
  }

  public List getTemplateActionHistory(Long templateId, Long nodeId) {
    return runtimeDao.getTemplateActionHistory(templateId, nodeId);
  }

  public List sumTemplateAction(QueryElement queryElement) {
    return runtimeDao.sumTemplateAction(queryElement);
  }

  public void queryHistory() {
    
  }

  public List getCurTaskAllExecutor(Long instanceId) {
    return runtimeDao.getCurTaskAllExecutor(instanceId);
  }

}
