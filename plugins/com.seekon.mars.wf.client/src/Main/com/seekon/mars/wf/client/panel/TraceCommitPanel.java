package com.seekon.mars.wf.client.panel;

import java.util.Map;

import javax.swing.JOptionPane;

import com.seekon.mars.wf.client.dialog.WfDialog;
import com.seekon.mars.wf.client.internal.ServiceFacade;
import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.model.BusinessType;
import com.seekon.mars.wf.service.BusinessService;
import com.seekon.mars.wf.service.RuntimeService;

/**
 * 流程提交
 * @author Administrator
 *
 */
public class TraceCommitPanel extends AbstractTaskActionPanel {
  private WfDialog dialog;
  
  public TraceCommitPanel(WfDialog self, Map<String, String> sessionMap,TableData tableData,String compId, Long templateId, Long instanceId, 
    String userId,Map<String,String> vMap,String  vDesc,
    int treeType,
    BusinessType businessObj) {
    super(sessionMap, tableData, compId, templateId, instanceId,userId, vMap, vDesc, treeType, businessObj);
    this.dialog = self;
    setActionButtonText("提交");
  }

  @Override
  public void addGraphListener() {

  }

  @Override
  public void cancelActionEvent() {

  }

  @Override
  public void taskActionEvent() {
   
    BusinessService businessService= ServiceFacade.getBusinessService();
    vMap.put("actionDescription",contentDemoTxt.getText());
    try{
      RuntimeService runtimeService = ServiceFacade.getRuntimeService();
      if (!businessService.isStartedTrance(instanceId.intValue())) {
        //草稿实例未启动，启动草稿实例
        runtimeService.startProcessInstance(sessionMap, instanceId, vDesc, compId,templateId, tableData, vMap);
      }
      else{
        runtimeService.commit(sessionMap, instanceId,tableData,vMap);
      }
      
      this.dialog.close();
      dialog.setSeccess(true);
      JOptionPane.showMessageDialog(this.dialog, "流程提交成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
    }
    catch(Exception e){
      dialog.setSeccess(false);
      e.printStackTrace();
      JOptionPane.showMessageDialog(this.dialog, "流程提交失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  //  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
  //    IllegalAccessException, UnsupportedLookAndFeelException {
  //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  //    SwingUtilities.invokeLater(new Runnable() {
  //      @Override
  //      public void run() {
  //        JFrame frame = new JFrame("实例流程");
  //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
  ////        frame.setSize(1000, 720);
  //        frame.setLocationRelativeTo(null);
  //        Company com = new Company();
  ////        com.setCode(null);
  //        com.setName("丹阳财政");
  //        com.setParentId("029");
  //        com.setNd(2009);
  ////        Org org = new Org();
  ////        org.setCode("03");
  ////        org.setCoCode("999");
  ////        org.setName("预算科");
  ////        org.setNd(2009);
  //        frame.getContentPane().add(new TraceCommitPanel(new Long(2023), new Long(9013),TreeType.COMPANY_DOWN_LEVEL, com));
  //        frame.setVisible(true);
  //      }
  //    });
  //  }
}
