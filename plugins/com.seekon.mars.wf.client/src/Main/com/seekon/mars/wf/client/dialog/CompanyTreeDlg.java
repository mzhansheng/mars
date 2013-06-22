package com.seekon.mars.wf.client.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.seekon.mars.wf.client.panel.CompanyTreePanel;
import com.seekon.mars.wf.client.tree.TreeType;
import com.seekon.mars.wf.model.BusinessType;
import com.seekon.mars.wf.model.Org;

//显示单位树对话框
public class CompanyTreeDlg extends WfDialog{
  private CompanyTreePanel treePanel;
  /**
   * @param treeType 业务树类型
   * @param businessObj 当前业务对象
   */
  public CompanyTreeDlg(Window owner, String title,int treeType
                        ,BusinessType businessObj) {
    super(owner, title);
    initGUI(treeType,businessObj);
  }
  public CompanyTreeDlg(Window owner, String title, 
                          ModalityType modalityType,
                          int treeType,BusinessType businessObj) {
    super(owner, title, modalityType);
    initGUI(treeType,businessObj);
  }
  
  public CompanyTreeDlg(Frame owner, String title
                        ,int treeType,BusinessType businessObj) {
    super(owner, title);
    initGUI(treeType,businessObj);
  }
  public CompanyTreeDlg(Frame owner, String title, boolean modal
                          ,int treeType,BusinessType businessObj) {
    super(owner, title, modal);
    initGUI(treeType,businessObj);
  }
  
  public CompanyTreeDlg(Dialog owner, String title,int treeType
                        ,BusinessType businessObj) {
    super(owner, title);
    initGUI(treeType,businessObj);
  }
  public CompanyTreeDlg(Dialog owner, String title, boolean modal,
                          int treeType,BusinessType businessObj) {
    super(owner, title, modal);
    initGUI(treeType,businessObj);
  }
  
  public void initGUI(int treeType,BusinessType businessObj){
    treePanel=new CompanyTreePanel(treeType,businessObj);
    this.setLayout(new BorderLayout());
    this.add(treePanel,BorderLayout.CENTER);
    this.setSize(new Dimension(280,360));
    this.moveToScreenCenter();
    this.setVisible(true);
  }
  
  public static void main(String []args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
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
          CompanyTreeDlg dlg=new CompanyTreeDlg(frame,"公司" ,false,TreeType.ORG_DOWN_LEVEL, org);
          dlg.setVisible(true);
        }
      });
  }
}
