package com.seekon.mars.wf.engine;

import java.util.HashMap;
import java.util.Map;

public class TestCommitTask extends TestCase{

ExecutionService executionService = null;
  
  Map sessionMap = new HashMap();
  
  public void setUp(){
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
    
//    sessionMap.put("svUserID", "maojy");
//    sessionMap.put("svUserName", "maojy");
//    sessionMap.put("svCoCode", "999");
//    sessionMap.put("svOrgCode", "06");
//    sessionMap.put("svPoCode", "019");
//    sessionMap.put("nd", "2009");
    
//    sessionMap.put("svUserID", "jiangxl");
//    sessionMap.put("svUserName", "jiangxl");
//    sessionMap.put("svCoCode", "999");
//    sessionMap.put("svOrgCode", "06");
//    sessionMap.put("svPoCode", "031");
//    sessionMap.put("nd", "2009");
  }
  
  public void testCommitTask(){
    String instanceId = "9843";
    WorkflowContext ctx = new WorkflowContext();
    ctx.setAction("commit");
    ctx.setSessionMap(sessionMap);
    ctx.setInstanceId(Long.valueOf(instanceId));
    
    executionService.commit(ctx);
  }
  
}
