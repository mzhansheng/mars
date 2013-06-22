package com.seekon.mars.wf.client.panel;

import java.util.Map;

import javax.swing.JOptionPane;

import com.seekon.mars.wf.client.dialog.WfDialog;
import com.seekon.mars.wf.client.internal.ServiceFacade;
import com.seekon.mars.wf.engine.model.LinkModel;
import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.model.BusinessType;
import com.seekon.mars.wf.service.MetaService;
import com.seekon.mars.wf.service.RuntimeService;

/**
 * 流程回退
 * @author Administrator
 *
 */
public class TraceUntreadPanel extends AbstractTaskActionPanel{
  private WfDialog dialog;
  
  public TraceUntreadPanel(WfDialog self,Map<String, String> sessionMap, 
    TableData tableData,String compId,Long templateId,Long instanceId,
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType, BusinessType businesstype){
    super(sessionMap, tableData,compId,templateId, instanceId, userId,vMap,vDesc,treeType, businesstype);
    this.dialog=self;
    setActionButtonText("退回");
  }
  
  @Override
  public void addGraphListener() {
    
  }

  @Override
  public void cancelActionEvent() {
    
  }

  @Override
  public void taskActionEvent() {
    
    try{
      MetaService metaService=ServiceFacade.getMetaService();
      RuntimeService runtimeService=ServiceFacade.getRuntimeService();
      vMap.put("actionDescription",contentDemoTxt.getText());
/////////////////////////////////////////////////////////////////////////////////////////////////
//选中当前节点前置任意LINKID回退
//      if (selectedRebackLinkId!=null) {
//        runtimeService.untread(sessionMap, instanceId,selectedRebackLinkId,data,vMap);
//        this.dialog.close();
//        dialog.setSeccess(true);
//        JOptionPane.showMessageDialog(this.dialog, "流程退回成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
//      }
//      else{
//        JOptionPane.showMessageDialog(this.dialog, "请选择要退回到的目标连接！", "提示", JOptionPane.INFORMATION_MESSAGE);
//      }
////////////////////////////////////////////////////////////////////////////////////////////////
      //----------------------------向前回退一步--------------------------------//
      LinkModel link=metaService.getPreLinkOfCurNode(instanceId);
      if(link.getCurrentNodeId().intValue()==-1){
        JOptionPane.showMessageDialog(this.dialog, "不能退回到开始结点！", "提示", JOptionPane.INFORMATION_MESSAGE);
      }
      else{
        runtimeService.untread(sessionMap, instanceId,link.getNodeLinkId(),tableData,vMap);
        this.dialog.close();
        dialog.setSeccess(true);
        JOptionPane.showMessageDialog(this.dialog, "流程退回成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    catch(Exception e){
      dialog.setSeccess(false);
      e.printStackTrace();
      JOptionPane.showMessageDialog(this.dialog, "流程退回失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
