package com.seekon.mars.wf.engine.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seekon.mars.wf.engine.WFClient;
import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.BindVariableModel;
import com.seekon.mars.wf.engine.model.TemplateModel;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;
import com.seekon.mars.wf.engine.model.runtime.BusinessJuniorModel;
import com.seekon.mars.wf.engine.model.runtime.InstanceModel;
import com.seekon.mars.wf.engine.model.runtime.PassModel;
import com.seekon.mars.wf.engine.model.runtime.TableData;

public class TestWFRuntimeDao extends TestCase{

  public void testGetOrgJuniorListByStaff(){
    Map sessionMap = new HashMap();
    sessionMap.put("svUserID", "hening");
    sessionMap.put("svCoCode", "999");
    sessionMap.put("svOrgCode", "03");
    sessionMap.put("svPoCode", "004");
    sessionMap.put("nd", "2009");
    
    List result = WFDaoFactory.getWFRuntimeDao().getOrgJuniorListByStaff(new StaffModel(sessionMap));
    
    assertNotNull(result);
    assertNotNull(result.get(0));
    assertNotNull(((Map)result.get(0)).get("svUserID"));
    assertNotNull(((Map)result.get(0)).get("svCoCode"));
  }
  
  public void testGetCurrentTaskCount(){
    Long instanceId = Long.valueOf("9523");
    Long nodeId = Long.valueOf("40255");
    String executor = "";
    
    int count = WFDaoFactory.getWFRuntimeDao().getCurrentTaskCount(instanceId, nodeId, executor);
    assertTrue(count >= 1);
  }
  
  public void testGetBindVariableValue(){
    Long instanceId = Long.valueOf("9568");
    InstanceModel instance = WFDaoFactory.getWFRuntimeDao().getInstanceById(instanceId);
    TemplateModel template = WFClient.getWFEngine().getRepositoryService().getTemplate(instance.getTemplateId());
    
    Iterator iterator = template.getBindVariableList().iterator();
    while(iterator.hasNext()){
      BindVariableModel variable = (BindVariableModel)iterator.next();
      Object result = WFDaoFactory.getWFRuntimeDao().getBindVariableValue(variable, instanceId);
      assertNotNull(result);
    }
  }
  
  public void testGetInstancePass(){
    Long instanceId = Long.valueOf("9606");
    List result = WFDaoFactory.getWFRuntimeDao().getInstancePass(instanceId);
    assertNotNull(result);
    
    PassModel pass = new PassModel();
    pass.setInstanceId(Long.valueOf("9606"));
    pass.setCurrentNodeId(Long.valueOf("10749"));
    pass.setNextNodeId(Long.valueOf("10750"));
    pass.setNodeLinkId(Long.valueOf("10754"));
    
    result = WFDaoFactory.getWFRuntimeDao().getInstancePass(pass);
    assertNotNull(result);
  }
  
  public void testGetBusinessJuniorListByStaff(){
    Map sessionMap = new HashMap();
    sessionMap.put("svUserID", "004001luping");
    sessionMap.put("svCoCode", "004001");
    sessionMap.put("svOrgCode", "51");
    sessionMap.put("svPoCode", "024");
    sessionMap.put("nd", "2009");
    TableData tableData = new TableData();
    
    Set nextExecutorSet = new HashSet();
    StaffModel currentExecutor = new StaffModel(sessionMap);
    
    List businessJuniorList = WFDaoFactory.getWFRuntimeDao().getBusinessJuniorListByStaff(currentExecutor);
    Iterator iterator = businessJuniorList.iterator();
    while(iterator.hasNext()){
      BusinessJuniorModel businessJunior = (BusinessJuniorModel)iterator.next();
      if(!businessJunior.containJuniorStaff(currentExecutor) || !businessJunior.isBelow(tableData)){
        continue;
      }
      
      nextExecutorSet.addAll(businessJunior.transSuperStaffList());
    }
    
    assertNotNull(nextExecutorSet);
    
  }
  
  public void testGetStaffListByEmpCodeList(){
    int nd = 2009;
    List empCodeList = new ArrayList();
    empCodeList.add("liwz");
    empCodeList.add("wangqx");
    empCodeList.add("wumd");
    empCodeList.add("zhanggh");
    
    List result = WFDaoFactory.getWFRuntimeDao().getStaffListByUserCodeList(empCodeList, nd);
    assertNotNull(result);
    assertNotNull(result.get(0));
  }
  
  public void testGetActionHistoryList(){
    ActionHistoryModel action = new ActionHistoryModel();
    action.setInstanceId(new Long(9741));
    List result = WFDaoFactory.getWFRuntimeDao().getActionHistory(action);
    assertNotNull(result);
    assertNotNull(result.get(0));
  }
}
