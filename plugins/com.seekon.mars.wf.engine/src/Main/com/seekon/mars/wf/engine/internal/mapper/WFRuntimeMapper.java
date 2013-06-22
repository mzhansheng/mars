package com.seekon.mars.wf.engine.internal.mapper;

import java.util.List;

import com.seekon.mars.wf.engine.model.BindVariableModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.CurrentTaskModel;
import com.seekon.mars.wf.engine.model.runtime.DraftModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;

public interface WFRuntimeMapper {

  public DraftModel createDraft(DraftModel draft);

  public DraftModel getDraftById(Long draftId);

  public void removeDraftById(Long draftId);

  public InstanceModel createInscance(InstanceModel instance);

  public InstanceModel getInstanceById(Long instanceId);

  public List getInstanceList(Long instanceId);
  
  public void removeInstanceById(Long id);

  public void updateInstance(InstanceModel instance);

  public void updateBusinessInstanceId(Long draftId, Long instanceId, String tableName);

  public void updateBusinessState(String tableName, String fieldName, String value, Long instanceId);

  public CurrentTaskModel createCurrentTask(CurrentTaskModel task);

  public List getCurrentTask(CurrentTaskModel task);

  public int getCurrentTaskCount(Long instanceId, Long nodeId, String executor);

  public void removeCurrentTask(CurrentTaskModel task);

  public ActionModel createAction(ActionModel action);

  public void removeAction(ActionModel action);
    
  public List getAction(ActionModel action);

  public int getActionCountByNodeId(Long instanceId, Long nodeId);

  public ActionHistoryModel createActionHistory(ActionHistoryModel actionHistory);

  public List getActionHistory(ActionHistoryModel actionHistory);

  public PassModel createPass(PassModel pass);

  public List getBusinessJuniorListByStaff(StaffModel staff);

  public List getOrgJuniorListByStaff(StaffModel staff);

  public List getStaffListByUserCodeList(List userCodeList, int nd);

  public Object getBindVariableValue(BindVariableModel bindVariable, Long instanceId);

  public List getInstancePass(Long instanceId);//得到所有实例的执行路径

  public List getInstancePass(PassModel pass);

  public List getInstanceActionHistoryList(Long instanceId, List nodeidList,String executor);

  public void removeInstancePass(PassModel pass);

  public void removeInstancePassByIdList(List passIdList);

  public void createInstanceHistory(InstanceHistoryModel instanceHistory);
  
  public void createInstanceFromHistory(InstanceModel instance);

  public void removeInstanceHistoryById(Long instanceHistoryId);

  public InstanceHistoryModel getInstanceHistoryById(Long instanceHistoryId);
  
  public List getActionFromNodeList(Long instanceId,List nodeIdList);
  
  public String getSql(String id, Object parameterObject);
  
  public List getTemplateActionHistory(Long templateId,Long nodeId);
  
  public List sumTemplateAction(QueryElement elementDto);
  
  public List getCurTaskAllExecutor(Long instanceId);
}
