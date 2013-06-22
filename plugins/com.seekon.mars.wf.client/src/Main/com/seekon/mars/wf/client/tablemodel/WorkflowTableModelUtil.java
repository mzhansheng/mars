package com.seekon.mars.wf.client.tablemodel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import com.seekon.mars.wf.client.access.MetaAccessUtil;
import com.seekon.mars.wf.client.access.TraceAccessUtil;
import com.seekon.mars.wf.engine.model.runtime.ActionModel;
import com.seekon.mars.wf.engine.model.runtime.QueryElement;
import com.seekon.mars.wf.engine.model.runtime.SumResultRow;

public class WorkflowTableModelUtil {
  public static List<String> getObjectField(Class cls){
    List<String> list=new Vector<String>();
    Field heads[]=cls.getDeclaredFields();
    for(Field fd:heads){
      String key=fd.getName();
      if(key.equals("serialVersionUID")){
        continue;
      }
      else{
        list.add(fd.getName());
      }
    }
    return list;
  }
  
  private static List queryTemplateActionHistoryModel(Long templateId,Long nodeId){
    return TraceAccessUtil.getTemplateActionHistory(templateId,nodeId);
  }
  
  private static List queryInstanceActionHistoryModel(Long instanceId,Long nodeId){
    return TraceAccessUtil.getInstanceActionHistory(instanceId,nodeId);
  }
  
  public static WorkflowTableModel getTemplateHistoryTableModel(Long templateId,Long nodeId){
    return new WorkflowTableModel(queryTemplateActionHistoryModel(templateId,nodeId),
      getObjectField(ActionModel.class));
  }
  
  public static WorkflowTableModel getInstanceHistoryTableModel(Long instanceId,Long nodeId){
    return new WorkflowTableModel(queryInstanceActionHistoryModel(instanceId,nodeId),
      getObjectField(ActionModel.class));
  }
  
  private static List sumTemplateActionList(QueryElement queryElement){
    return TraceAccessUtil.sumTemplateAction(queryElement);
  }
  
  public static WorkflowTableModel getSumTemplateModel(QueryElement queryElement){
    return new WorkflowTableModel(sumTemplateActionList(queryElement),
      getObjectField(SumResultRow.class));
  }
  
  public static WorkflowTableModel getTemplateListModel(Long templateId,Vector fieldList){
    return new WorkflowTableModel(MetaAccessUtil.getTemplate(templateId), fieldList);
  }
  
  public static WorkflowTableModel getInstanceListModel(Long instanceId,Vector fieldList){
    return new WorkflowTableModel(TraceAccessUtil.getInstanceList(instanceId), fieldList);
  }
  
  public static WorkflowTableModel getNodeListModel(Long tempalateId,Vector fieldList){
    return new WorkflowTableModel(MetaAccessUtil.getNodeList(tempalateId), fieldList);
  }
}
