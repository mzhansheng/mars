package com.seekon.mars.wf.client.component;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.seekon.mars.wf.client.dialog.WfDialog;
import com.seekon.mars.wf.client.panel.CompanyTreePanel;
import com.seekon.mars.wf.client.tree.TreeType;
import com.seekon.mars.wf.model.Company;
import com.seekon.mars.wf.model.Staff;

public class StaffSelectDlg extends WfDialog{
  protected JButtonTextField triggerField;
  private CompanyTreePanel treePanel;
  private JButton okBtn;
  private JButton clearBtn;
  private JButton cancelBtn;
  
  public StaffSelectDlg(Dialog owner, boolean modal,
    JButtonTextField triggerField) {
    super(owner,"人员选择", modal);
    this.triggerField=triggerField;
    this.setSize(600,500);
    SwingUtilities.invokeLater(new Runnable(){

      @Override
      public void run() {
        initGUI();
      }
      
    });
    this.moveToScreenCenter();
  }
  
  private void initGUI(){
    Company root = new Company();
    root.setCode(null);
    root.setName("公司选择");
//    root.setNd(2009);
    add(treePanel=new CompanyTreePanel(TreeType.ALL_LEVEL,root),BorderLayout.CENTER);
    JPanel cmdPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
    cmdPanel.add(okBtn=new JButton("确定"));
    okBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        List<Staff> selList=treePanel.getSelectBusinessList();
        Staff staff=(Staff)selList.get(0);
        triggerField.setValue(staff);
        triggerField.setText(staff.toString());
        close();
      }
    });
    cmdPanel.add(clearBtn=new JButton("清除"));
    clearBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        triggerField.setText(null);
        triggerField.setValue(null);
        close();
      }
      
    });
    cmdPanel.add(cancelBtn=new JButton("取消"));
    cancelBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        close();
      }
      
    });
    add(cmdPanel,BorderLayout.SOUTH);
  }
}
