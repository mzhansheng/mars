package com.seekon.mars.wf.client.panel;

import java.util.Map;

import com.seekon.mars.wf.client.dialog.WfDialog;
import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.model.BusinessType;

/**
 * 流程重做
 * @author Administrator
 *
 */
public class TraceReworkPanel extends AbstractTaskActionPanel{
  private WfDialog dialog;
  
  public TraceReworkPanel(WfDialog self,Map<String, String> sessionMap,TableData tableData, String compId,Long templateId,Long instanceId,
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType, BusinessType businesstype){
    super(sessionMap, tableData,compId, templateId, instanceId, userId, vMap,vDesc, treeType, businesstype);
    this.dialog=self;
    setActionButtonText("重做");
  }
  
  @Override
  public void addGraphListener() {
  }

  @Override
  public void cancelActionEvent() {
    
  }

  @Override
  public void taskActionEvent() {
    
  }
}
