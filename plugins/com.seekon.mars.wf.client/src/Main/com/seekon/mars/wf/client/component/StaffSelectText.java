package com.seekon.mars.wf.client.component;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StaffSelectText extends JButtonTextField{
  private StaffSelectText self=this;
  protected Dialog owner;
  
  public StaffSelectText(int col) {
    super(col);
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run() {
        initGUI();
      }
    });
  }
  
  private void initGUI(){
     selectDialog=new StaffSelectDlg(owner, true, self);
  }
  
  @Override
  public void handleClick(JButtonTextField buttonTextField) {
    selectDialog.setVisible(true);
  }

  public static void main(String []args){
    JFrame f = new JFrame();
    StaffSelectText textField = new StaffSelectText(20);

    JPanel panel = new JPanel();
    panel.add(textField);
    f.getContentPane().add(panel, BorderLayout.NORTH);
    f.setSize(400, 300);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
