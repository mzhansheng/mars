package com.seekon.mars.wf.engine;

import java.util.HashMap;
import java.util.Map;

import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.engine.util.WFConst;

public class TestStartProcessInstance extends TestCase {

  ExecutionService executionService = null;

  Map sessionMap = new HashMap();

  public void setUp() {
    executionService = WFClient.getWFEngine().getExecutionService();
    sessionMap.put("svUserID", "hening");
    sessionMap.put("svUserName", "hening");
    sessionMap.put("svCoCode", "999");
    sessionMap.put("svOrgCode", "03");
    sessionMap.put("svPoCode", "004");
    sessionMap.put("nd", "2009");
  }

  public void testStartProcessInstance() {
    String draftId = "-9804";

    WorkflowContext ctx = new WorkflowContext();
    ctx.setAction("startProcessInstance");
    ctx.setSessionMap(sessionMap);
    ctx.setInstanceId(Long.valueOf(draftId));
    ctx.setVariable("instanceDesc", "启动流程实例测试");
    ctx.setTemplateId(Long.valueOf("2023"));
    ctx.setAction(WFConst.ACTION_TYPE_START_INSTANCE);

    TableData data = new TableData();
    data.setId("AM_APPLY");
    data.setTitle("AM_APPLY");
    ctx.setTableData(data);

    executionService.startProcessInstance(ctx);

    try {
      Thread.sleep(90L * 1000L);
    } catch (Exception e) {
    }
  }
}
