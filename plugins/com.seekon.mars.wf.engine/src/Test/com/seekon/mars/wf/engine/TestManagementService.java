package com.seekon.mars.wf.engine;

import java.util.HashMap;
import java.util.Map;

import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.engine.util.WFConst;

public class TestManagementService extends TestCase {
  private ManagementService manageService = null;

  private Map sessionMap = new HashMap();

  private WorkflowContext ctx = new WorkflowContext();

  public void setUp() {
    manageService = WFClient.getWFEngine().getManagementService();
//    sessionMap.put("svUserID", "hening");
//    sessionMap.put("svUserName", "hening");
//    sessionMap.put("svCoCode", "999");
//    sessionMap.put("svOrgCode", "03");
//    sessionMap.put("svPoCode", "004");
//    sessionMap.put("nd", "2009");

    sessionMap.put("svUserID", "jiangxl");
    sessionMap.put("svUserName", "jiangxl");
    sessionMap.put("svCoCode", "999");
    sessionMap.put("svOrgCode", "06");
    sessionMap.put("svPoCode", "031");
    sessionMap.put("nd", "2009");
    
//    String draftId = "-9784";
    ctx = new WorkflowContext();
    ctx.setSessionMap(sessionMap);
    ctx.setInstanceId(new Long(9829));
    ctx.setTemplateId(new Long(2023));
    ctx.setCurrentNodeId(new Long(2030));
    

    TableData data = new TableData();
    data.setId("AM_APPLY");
    data.setTitle("AM_APPLY");
    ctx.setTableData(data);
  }

//  	public void testActivate() {
//  	  ctx.setVariable("instanceDesc", "激活工作流流程实例！");
//  	  ctx.setAction(WFConst.ACTION_TYPE_ACTIVATE_INSTANCE);
//  		manageService.activate(ctx);
//  	}

//  	public void testDeactivate() {
//  	  ctx.setVariable("instanceDesc", "冻结工作流流程实例！");
//  	  ctx.setAction(WFConst.ACTION_TYPE_DEACTIVATE_INSTANCE);
//  		manageService.deactivate(ctx);
//  	}
  //
//  	public void testSuspend() {
//  	  ctx.setVariable("instanceDesc", "挂起工作流流程实例！");
//  	  ctx.setAction(WFConst.ACTION_TYPE_SUSPEND_INSTANCE);
//  		manageService.suspend(ctx);
//  	}
  //
  	public void testRestart() {
  	  ctx.setVariable("instanceDesc", "重新启动工作流流程实例！");
  	  ctx.setAction(WFConst.ACTION_TYPE_RESTART_INSTANCE);
  		manageService.restart(ctx);
  	}
  //
//  public void testTerminated() {
//    ctx.setVariable("instanceDesc", "终止工作流流程实例！");
//    ctx.setAction(WFConst.ACTION_TYPE_TERMINATED_INSTANCE);
//    manageService.terminated(ctx);
//  }
}
