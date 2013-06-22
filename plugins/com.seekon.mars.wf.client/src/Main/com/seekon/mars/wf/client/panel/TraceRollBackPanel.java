package com.seekon.mars.wf.client.panel;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.seekon.mars.wf.client.dialog.WfDialog;
import com.seekon.mars.wf.client.internal.ServiceFacade;
import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.model.BusinessType;
import com.seekon.mars.wf.service.BusinessService;
import com.seekon.mars.wf.service.RuntimeService;

/**
 * 流程收回
 * @author Administrator
 *
 */
public class TraceRollBackPanel extends AbstractTaskActionPanel{
  private WfDialog dialog;
  
  public TraceRollBackPanel(WfDialog self,Map<String, String> sessionMap, TableData tableData, 
                            String compId,Long templateId,Long instanceId,
                            String userId, Map<String,String> vMap,String  vDesc,
                            int treeType, BusinessType businesstype){
    super(sessionMap, tableData, compId,templateId, instanceId, userId,vMap,vDesc,treeType, businesstype);
    this.dialog=self;
    setActionButtonText("收回");
  }
  
  @Override
  public void addGraphListener() {
    
  }

  @Override
  public void cancelActionEvent() {
    
  }

  @Override
  public void taskActionEvent() {
      
      BusinessService businessService=ServiceFacade.getBusinessService();
      //Long nodeId = businessService.getCurNodeIdFromNoCommitTask(this.instanceId,svOrgCode, svPoCode, svNd);
      Long nodeId = null;
      List result =  businessService.getCurNodeIdFromNoCommitTask(vMap);
      if(result.size() == 0) {
          JOptionPane.showMessageDialog(this.dialog, "未找到节点，流程收回失败！", "错误信息", JOptionPane.ERROR_MESSAGE);
          return;
      }
      try{
          for(int i=0; i<result.size(); i++){
              Object v = result.get(i);
              nodeId =  Long.valueOf(v == null ? "0" : v.toString());
              vMap.put("actionDescription",contentDemoTxt.getText());
              RuntimeService runtimeService=ServiceFacade.getRuntimeService();
              runtimeService.callback(sessionMap,instanceId,nodeId,tableData,vMap);
              this.dialog.close();
              dialog.setSeccess(true);
              JOptionPane.showMessageDialog(this.dialog, "流程收回成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
          } 
      }
      catch(Exception e){
          dialog.setSeccess(false);
          e.printStackTrace();
          JOptionPane.showMessageDialog(this.dialog, "流程收回失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
      }
  }
}
