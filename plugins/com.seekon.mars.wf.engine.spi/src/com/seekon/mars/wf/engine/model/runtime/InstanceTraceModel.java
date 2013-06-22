package com.seekon.mars.wf.engine.model.runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.seekon.mars.wf.engine.spi.util.InstanceXmlUtil;

public class InstanceTraceModel implements java.io.Serializable {

  private static final long serialVersionUID = -1653472646870172989L;

  private Long instanceId;//流程实例id

  private List passList;//流程经过路径列表

  private List actionList;//所有动作列表，包含回退、收回、重做、挂起、重启、提交等工作

  public Long getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(Long instanceId) {
    this.instanceId = instanceId;
  }

  public List getPassList() {
    return passList;
  }

  public void setPassList(List passList) {
    this.passList = passList;
  }

  public List getActionList() {
    return actionList;
  }

  public void setActionList(List actionList) {
    this.actionList = actionList;
  }
  
  public List getPassIdList(){
    if(passList == null){
      return new ArrayList();
    }
    
    List passIdList = new ArrayList();
    Iterator iterator = passList.iterator();
    while(iterator.hasNext()){
      passIdList.add(((PassModel)iterator.next()).getPassCountId());
    }
    
    return passIdList;
  }
  
  public List getActionIdList(){
    if(actionList == null){
      return new ArrayList();
    }
    
    List actionIdList = new ArrayList();
    Iterator iterator = actionList.iterator();
    while(iterator.hasNext()){
      actionIdList.add(((ActionHistoryModel)iterator.next()).getActionId());
    }
    
    return actionIdList;
  }
  
  public String toXml(){
    return InstanceXmlUtil.instanceTrace2Xml(this);
  }
}
