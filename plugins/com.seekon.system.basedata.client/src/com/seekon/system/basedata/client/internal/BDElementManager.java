package com.seekon.system.basedata.client.internal;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTable;

import com.seekon.smartclient.gui.GuiEngine;
import com.seekon.smartclient.gui.tags.MDefaultTableModel;

public class BDElementManager extends GuiEngine{
 
  private Container bdElementContainer = null;

  private JButton settingButton;
  
  private JTable bdElementTable;
  
  public Container getbdElementContainer() {
    initComponents();
    bdElementContainer.setVisible(true);
    return bdElementContainer;
  }
  
  private void initComponents(){
    try {
      bdElementContainer = render(BDGeneralManager.class
        .getResource("BDElementPage.xml"));
      initListeners();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void initListeners(){
    settingButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
       MDefaultTableModel model = (MDefaultTableModel) bdElementTable.getModel(); 
       Vector<Map> dataList = model.getDataList();
      }
    });
  }
  
  @Override
  protected void registerTags() {
    super.registerTags();
    this.registerTag(BDElementTableModel.class);
  }
}
