package com.seekon.mars.wf.client.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.seekon.mars.wf.client.panel.TraceSumPanel;

public class TranceSumDlg extends WfDialog {
  private JPanel panel;
  public TranceSumDlg(Dialog owner, String title, boolean modal) {
    super(owner, title, modal);
    initGUI();
  }

  public TranceSumDlg(Dialog owner, String title, int treeType) {
    super(owner, title, treeType);
    initGUI();
  }

  public TranceSumDlg(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    initGUI();
  }

  public TranceSumDlg(Frame owner, String title, int treeType) {
    super(owner, title, treeType);
    initGUI();
  }

  public TranceSumDlg(Window owner, String title, ModalityType modalityType) {
    super(owner, title, modalityType);
    initGUI();
  }

  public TranceSumDlg(Window owner, String title) {
    super(owner, title);
    initGUI();
  }

  private void initGUI(){
    panel=new TraceSumPanel();
    this.setLayout(new BorderLayout());
    this.add(panel,BorderLayout.CENTER);
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
        TranceSumDlg dlg = new TranceSumDlg(frame, "流程跟踪", false);
        dlg.setVisible(true);
      }
    });
    
  }

}
