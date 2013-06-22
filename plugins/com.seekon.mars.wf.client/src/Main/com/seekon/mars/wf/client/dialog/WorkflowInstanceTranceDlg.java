package com.seekon.mars.wf.client.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.seekon.mars.wf.client.panel.AbstractDisplayPanel;
import com.seekon.mars.wf.client.panel.WorkflowInstanceTracePanel;
import com.seekon.mars.wf.model.Org;

public class WorkflowInstanceTranceDlg extends WfDialog {
  private Long templateId;
  private Long instanceId;
  private String curUserId;
  private AbstractDisplayPanel instanceTracePanel;

  public WorkflowInstanceTranceDlg(Window owner, String title,Long templateId,Long instanceId,String userId) {
    super(owner, title);
    this.templateId=templateId;
    this.instanceId=instanceId;
    this.curUserId=userId;
    initGUI();
  }

  public WorkflowInstanceTranceDlg(Window owner, String title, ModalityType modalityType,Long templateId,Long instanceId,String userId) {
    super(owner, title, modalityType);
    this.templateId=templateId;
    this.instanceId=instanceId;
    this.curUserId=userId;
    initGUI();
  }

  public WorkflowInstanceTranceDlg(Frame owner, String title, int treeType,Long templateId,Long instanceId,String userId) {
    super(owner, title);
    this.templateId=templateId;
    this.instanceId=instanceId;
    this.curUserId=userId;
    initGUI();
  }

  public WorkflowInstanceTranceDlg(Frame owner, String title, boolean modal,Long templateId,Long instanceId,String userId) {
    super(owner, title, modal);
    this.templateId=templateId;
    this.instanceId=instanceId;
    this.curUserId=userId;
    initGUI();
  }

  public WorkflowInstanceTranceDlg(Dialog owner, String title,Long templateId,Long instanceId,String userId) {
    super(owner, title);
    this.templateId=templateId;
    this.instanceId=instanceId;
    this.curUserId=userId;
    initGUI();
  }

  public WorkflowInstanceTranceDlg(Dialog owner, String title, boolean modal,Long templateId,Long instanceId,String userId) {
    super(owner, title, modal);
    this.templateId=templateId;
    this.instanceId=instanceId;
    this.curUserId=userId;
    initGUI();
  }

  public void initGUI() {
    instanceTracePanel=new WorkflowInstanceTracePanel(this.templateId,this.instanceId,this.curUserId);
    this.setLayout(new BorderLayout());
    this.add(instanceTracePanel,BorderLayout.CENTER);
    this.setSize(instanceTracePanel.getPreferredSize());
    setSize(1000, 720);
    moveToScreenCenter();
  }
  
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
    IllegalAccessException, UnsupportedLookAndFeelException {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame frame = new JFrame("frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(280, 400);
        frame.setLocationRelativeTo(null);
        Org org = new Org();
        org.setCode("03");
        org.setCoCode("999");
        org.setName("预算科");
        org.setNd(2009);
        frame.setVisible(true);
        WorkflowInstanceTranceDlg dlg = new WorkflowInstanceTranceDlg(frame, "流程跟踪", false, new Long(2023),
          new Long(9013),null);
        dlg.setVisible(true);
      }
    });
  }
}
