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
import com.seekon.mars.wf.client.panel.WorkflowTemplateTracePanel;

//流程跟踪对话框
public class WorkflowTemplateTranceDlg extends WfDialog{
  private Long templateId;
  private AbstractDisplayPanel templateTracePanel;
  public WorkflowTemplateTranceDlg(Window owner, String title, Long templateId) {
    super(owner, title);
    this.templateId=templateId;
    initGUI();
  }

  public WorkflowTemplateTranceDlg(Window owner, String title, ModalityType modalityType, Long templateId) {
    super(owner, title, modalityType);
    this.templateId=templateId;
    initGUI();
  }

  public WorkflowTemplateTranceDlg(Frame owner, String title, Long templateId) {
    super(owner, title);
    this.templateId=templateId;
    initGUI();
  }

  public WorkflowTemplateTranceDlg(Frame owner, String title, boolean modal, Long templateId) {
    super(owner, title, modal);
    this.templateId=templateId;
    initGUI();
  }

  public WorkflowTemplateTranceDlg(Dialog owner, String title, Long templateId) {
    super(owner, title);
    this.templateId=templateId;
    initGUI();
  }

  private void initGUI(){
    templateTracePanel=new WorkflowTemplateTracePanel(this.templateId);
    this.setLayout(new BorderLayout());
    this.add(templateTracePanel,BorderLayout.CENTER);
    setSize(1000, 720);
    moveToScreenCenter();
  }
  
  public static void main(String arg[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame frame = new JFrame("frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(280, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        WorkflowTemplateTranceDlg dlg = new WorkflowTemplateTranceDlg(frame, "流程跟踪", false, new Long(2023));
        dlg.setVisible(true);
      }
    });
    
  }
}
