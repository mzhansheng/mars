package com.seekon.mars.wf.engine;

import java.util.HashMap;
import java.util.Map;

public class TestUntreadTask extends TestCase {
  ExecutionService executionService = null;

  Map sessionMap = new HashMap();

  public void setUp() {
    executionService = WFClient.getWFEngine().getExecutionService();
//  sessionMap.put("svUserID", "hening");
//  sessionMap.put("svUserName", "hening");
//  sessionMap.put("svCoCode", "999");
//  sessionMap.put("svOrgCode", "03");
//  sessionMap.put("svPoCode", "004");
//  sessionMap.put("nd", "2009");
  
//    sessionMap.put("svUserID", "liufm");
//    sessionMap.put("svUserName", "liufm");
//    sessionMap.put("svCoCode", "999");
//    sessionMap.put("svOrgCode", "03");
//    sessionMap.put("svPoCode", "003");
//    sessionMap.put("nd", "2009");
  
  sessionMap.put("svUserID", "maojy");
  sessionMap.put("svUserName", "maojy");
  sessionMap.put("svCoCode", "999");
  sessionMap.put("svOrgCode", "06");
  sessionMap.put("svPoCode", "019");
  sessionMap.put("nd", "2009");
    
  }

  public void testUntread() {

    Long instanceId = Long.valueOf("9796");

    WorkflowContext context = new WorkflowContext();
    context.setInstanceId(instanceId);
    context.setSessionMap(sessionMap);
    context.setAction("untread");
    context.setSelectedRebackLinkId(new Long(40194));
    executionService.untread(context);
  }
}
