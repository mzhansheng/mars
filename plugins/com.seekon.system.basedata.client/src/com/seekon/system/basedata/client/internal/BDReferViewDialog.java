package com.seekon.system.basedata.client.internal;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.gui.GuiEngine;
import com.seekon.smartclient.gui.model.CodeNameMap;
import com.seekon.smartclient.gui.tags.MDataModel;
import com.seekon.smartclient.gui.tags.MDefaultDataModel;
import com.seekon.smartclient.gui.tags.MJSONComboBoxModel;
import com.seekon.system.common.client.element.ElementStorage;
import com.seekon.system.common.model.Element;

public class BDReferViewDialog extends GuiEngine {

  private JDialog editDialog;

  private JTextField codeField;

  private JTextField nameField;

  private JComboBox typeField;

  private JCheckBox usedField;

  private JList leftColumnListField;

  private JList rightColumnListField;

  private JButton moveToLeftButton;

  private JButton moveToRightButton;

  private JButton moveToUpButton;

  private JButton moveToDownButton;

  private boolean returnValue = false;

  private Map data = null;

  private JButton saveButton;

  private MDataModel mDataModel = new MDefaultDataModel();

  public BDReferViewDialog(Map data) {
    super();

    Container bdReferContainer = null;
    try {
      bdReferContainer = render(BDGeneralManager.class
        .getResource("BDReferViewDialog.xml"));

      this.data = data;
      initListeners();
      initData();

      bdReferContainer.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initListeners() {
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final String code = codeField.getText();
        if (code == null || code.trim().length() == 0) {
          return;
        }

        UIUtilities.asyncInvoke(new DefaultInvokeHandler<Integer>() {

          @Override
          public Integer execute() throws Exception {
            mDataModel.addParam("code", code);
            mDataModel.addParam("name", nameField.getText());
            mDataModel.addParam("type",
              ((CodeNameMap) typeField.getSelectedItem()).get("code"));
            mDataModel.addParam("is_used", usedField.isSelected() ? "1" : "0");
            mDataModel.addParam("ele_id", data.get("ele_id"));
            
            String content = "";
            DefaultListModel rightModel = (DefaultListModel) rightColumnListField.getModel();
            for(int i = 0; i < rightModel.getSize(); i++){
              FieldMeta field = (FieldMeta) rightModel.get(i);
              content += field.getColCode() + ",";
            }
            if(content.length() > 0){
              content = content.substring(0, content.length() - 1);
            }
            mDataModel.addParam("content", content);
            
            Object id = data.get("id");
            if (id == null) {
              mDataModel.addParam("id", UUID.randomUUID().toString());
              mDataModel.setSqlid("insertElementReferView");
              return mDataModel.insert();
            } else {
              mDataModel.addParam("id", id);
              mDataModel.setSqlid("updateElementReferView");
              return mDataModel.update();
            }
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

    moveToLeftButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object[] values = rightColumnListField.getSelectedValues();
        for (Object obj : values) {
          ((DefaultListModel) leftColumnListField.getModel()).addElement(obj);
          ((DefaultListModel) rightColumnListField.getModel()).removeElement(obj);
        }
      }
    });

    moveToRightButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object[] values = leftColumnListField.getSelectedValues();
        for (Object obj : values) {
          ((DefaultListModel) rightColumnListField.getModel()).addElement(obj);
          ((DefaultListModel) leftColumnListField.getModel()).removeElement(obj);
        }
      }
    });
    
    moveToUpButton.addActionListener(new ActionListener() {     
      @Override
      public void actionPerformed(ActionEvent e) {
       
      }
    });
    
    moveToDownButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
      }
    });
  }

  private void initData() {
    if (data == null) {
      return;
    }
    codeField.setText((String) data.get("code"));
    nameField.setText((String) data.get("name"));
    Object used = data.get("is_used");
    if ("1".equals(used)) {
      usedField.setSelected(true);
    } else {
      usedField.setSelected(false);
    }

    Object type = data.get("type");
    if (type != null) {
      MJSONComboBoxModel model = (MJSONComboBoxModel) typeField.getModel();
      for (int i = 0; i < model.getSize(); i++) {
        CodeNameMap item = (CodeNameMap) model.getElementAt(i);
        if (type.equals(item.get("code"))) {
          model.setSelectedItem(item);
          break;
        }
      }
    }

    String content = (String)data.get("content");
    String eleId = (String) data.get("ele_id");
    Element element = ElementStorage.getElementById(eleId);
    if (element != null) {
      DefaultListModel model = (DefaultListModel) leftColumnListField.getModel();
      model.removeAllElements();
      
      DefaultListModel rightModel = (DefaultListModel) rightColumnListField.getModel();
      String[] contents = null;
      if(content != null){
        contents = content.split(",");
      }
      
      List<FieldMeta> fieldMetaList = element.getFieldList();
      for (FieldMeta field : fieldMetaList) {
        boolean addToRight = false;
        if(contents != null){
          for(String code : contents){
            if(code.equals(field.getColCode())){
              rightModel.addElement(field);
              addToRight = true;
              break;
            }
          }
        }
        
        if(!addToRight){
          model.addElement(field);
        }
      }
    }

  }

  public boolean getReturnValue() {
    return this.returnValue;
  }

}
