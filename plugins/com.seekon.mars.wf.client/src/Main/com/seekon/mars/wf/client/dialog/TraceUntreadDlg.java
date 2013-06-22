package com.seekon.mars.wf.client.dialog;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.Map;

import com.seekon.mars.wf.client.panel.TraceUntreadPanel;
import com.seekon.mars.wf.engine.model.runtime.TableData;
import com.seekon.mars.wf.model.BusinessType;

public class TraceUntreadDlg extends AbstractTraceActionDlg {

  public TraceUntreadDlg(Dialog owner, String title, boolean modal, 
    Map<String, String> sessionMap,TableData tableData,String compId,Long templateId, Long instanceId,
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, modal,  sessionMap, tableData, compId,templateId, instanceId, userId, vMap, vDesc, treeType0, businessObj);
  }

  public TraceUntreadDlg(Dialog owner, String title, int treeType, 
    Map<String, String> sessionMap,TableData tableData,String compId,Long templateId, Long instanceId,
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, treeType,  sessionMap, tableData, compId,templateId, instanceId, userId, vMap, vDesc, treeType0, businessObj);
  }

  public TraceUntreadDlg(Frame owner, String title, boolean modal, 
    Map<String, String> sessionMap,TableData tableData,String compId,Long templateId, Long instanceId,
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, modal,  sessionMap, tableData, compId,templateId, instanceId, userId, vMap, vDesc, treeType0, businessObj);
  }

  public TraceUntreadDlg(Frame owner, String title, int treeType, 
                  Map<String, String> sessionMap,TableData tableData,String compId,Long templateId, Long instanceId,
                  String userId, Map<String,String> vMap,String  vDesc,
                  int treeType0, BusinessType businessObj) {
    super(owner, title, treeType, sessionMap, tableData, compId,templateId, instanceId, userId, vMap, vDesc, treeType0, businessObj);
  }

  public TraceUntreadDlg(Window owner, String title, 
    Map<String, String> sessionMap,TableData tableData,String compId,Long templateId, Long instanceId, 
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType0,BusinessType businessObj) {
    super(owner, title,  sessionMap, tableData, compId,templateId, instanceId, userId, vMap, vDesc, treeType0, businessObj);
  }

  public TraceUntreadDlg(Window owner, String title, 
    ModalityType modalityType, Map<String, String> sessionMap,
    TableData tableData,String compId,Long templateId,Long instanceId, 
    String userId, Map<String,String> vMap,String  vDesc,
    int treeType0, BusinessType businessObj) {
    super(owner, title, modalityType,  sessionMap, tableData, compId,templateId, instanceId, userId, vMap, vDesc, treeType0, businessObj);
  }

  @Override
  public void initOperationPanel() {
    this.operationPanel = new TraceUntreadPanel(self,sessionMap,tableData,compId,templateId, instanceId,curUserId,vMap,vDesc,treeType,
      this.businessType);
  }

//  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
//    IllegalAccessException, UnsupportedLookAndFeelException {
//    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//    SwingUtilities.invokeLater(new Runnable() {
//      @Override
//      public void run() {
//        JFrame frame = new JFrame("提交流程");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        //      frame.setSize(280, 400);
//        frame.setLocationRelativeTo(null);
//        Org org = new Org();
//        org.setCode("03");
//        org.setCoCode("999");
//        org.setName("预算科");
//        org.setNd(2009);
//        frame.setVisible(true);
//        TraceUntreadDlg dlg = new TraceUntreadDlg(frame, "流程跟踪", false, new Long(2023), new Long(9013),
//          TreeType.COMPANY_DOWN_LEVEL, org);
//        dlg.setVisible(true);
//      }
//    });
//  }
}
