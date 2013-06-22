package com.seekon.mars.wf.engine;

import java.util.HashMap;
import java.util.Map;

public class TestHandoverTask extends TestCase{
  ExecutionService executionService = null;
  Map sessionMap = new HashMap();
  
  public void setUp(){
    executionService = WFClient.getWFEngine().getExecutionService();
    sessionMap.put("svUserID", "liufm");
    sessionMap.put("svUserName", "liufm");
    sessionMap.put("svCoCode", "999");
    sessionMap.put("svOrgCode", "03");
    sessionMap.put("svPoCode", "003");
    sessionMap.put("nd", "2009");
  }
  
  public void testHandover(){
    Map session01 = new HashMap();
    session01.put("svUserID", "yanxp");
    session01.put("svUserName", "yanxp");
    session01.put("svCoCode", "999");
    session01.put("svOrgCode", "03");
    session01.put("svPoCode", "004");
    session01.put("nd", "2009");
    
    Map session02 = new HashMap();
    session02.put("svUserID", "zhanggw");
    session02.put("svUserName", "zhanggw");
    session02.put("svCoCode", "999");
    session02.put("svOrgCode", "03");
    session02.put("svPoCode", "004");
    session02.put("nd", "2009");
    
    Long instanceId = Long.valueOf("9624");
    
    WorkflowContext context = new WorkflowContext();
    context.addSelectedNextExecutor(session01);
    context.addSelectedNextExecutor(session02);
    context.setInstanceId(instanceId);
    context.setSessionMap(sessionMap);
    context.setVariable("actionDescription", "请酌情办理");
    
    executionService.handover(context);
  }
}
