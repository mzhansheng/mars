package com.seekon.system.basedata.client.internal;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.gui.GuiEngine;
import com.seekon.smartclient.gui.tags.MDataModel;
import com.seekon.smartclient.gui.tags.MDefaultDataModel;

public class BDGeneralEditPage extends GuiEngine{

  private JDialog editDialog;
  
  private JTextField codeField;
  
  private JTextField nameField;
  
  private JTextField tableField;
  
  private JButton saveButton;
  
  private MDataModel mDataModel = new MDefaultDataModel();
  
  private boolean returnValue = false;
  
  public BDGeneralEditPage() {
    super();
    try {
      Container container = render(BDGeneralEditPage.class.getResource("BDGeneralEditpage.xml"));
      mDataModel.setSqlid("insertBDElement");
      initListeners();
      container.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void initListeners(){
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final String code = codeField.getText();
        if(code == null || code.trim().length() == 0){
          return;
        }
        
        UIUtilities.asyncInvoke(new DefaultInvokeHandler<Integer>() {

          @Override
          public Integer execute() throws Exception {
            mDataModel.addParam("id", UUID.randomUUID().toString());
            mDataModel.addParam("code", code);
            mDataModel.addParam("name", nameField.getText());
            mDataModel.addParam("type", "1");
            mDataModel.addParam("physical_table", tableField.getText());
            mDataModel.addParam("is_used", 1);
            return mDataModel.insert();
          }

          @Override
          public void success(Integer result) {
            JOptionPane.showMessageDialog(saveButton, "保存成功！");
            returnValue = true;
            editDialog.setVisible(false);
            editDialog.dispose();
          }
        });
      }
    });
  }
  
  public boolean getReturnValue(){
    return this.returnValue;
  }
}
