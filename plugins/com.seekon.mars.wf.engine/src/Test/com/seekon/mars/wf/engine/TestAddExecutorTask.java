package com.seekon.mars.wf.engine;

import java.util.HashMap;
import java.util.Map;

public class TestAddExecutorTask extends TestCase {
  ExecutionService executionService = null;
  Map sessionMap = new HashMap();

  public void setUp() {
    executionService = WFClient.getWFEngine().getExecutionService();
//    sessionMap.put("svUserID", "hening");
//    sessionMap.put("svUserName", "hening");
//    sessionMap.put("svCoCode", "999");
//    sessionMap.put("svOrgCode", "03");
//    sessionMap.put("svPoCode", "004");
//    sessionMap.put("nd", "2009");
    
    sessionMap.put("svUserID", "liufm");
    sessionMap.put("svUserName", "liufm");
    sessionMap.put("svCoCode", "999");
    sessionMap.put("svOrgCode", "03");
    sessionMap.put("svPoCode", "003");
    sessionMap.put("nd", "2009");
  }

  public void testAddRuntimeExecutor(){
    Long instanceId = Long.valueOf("9830");
    WorkflowContext context = new WorkflowContext();
    context.setInstanceId(instanceId);
    context.setSessionMap(sessionMap);
    context.setAction("rework");
    context.setCurrentNodeId(new Long(40182));
    
    Map map=new HashMap();
    map.put("svUserID", "hening");
    map.put("svUserName", "hening");
    map.put("svCoCode", "999");
    map.put("svOrgCode", "03");
    map.put("svPoCode", "004");
    map.put("nd", "2009");
    context.addSelectedNextExecutor(map);
    executionService.addRuntimeExecutor(context);
  }
}
