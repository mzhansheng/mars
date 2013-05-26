package com.seekon.system.basedata.client.internal;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.gui.GuiEngine;
import com.seekon.smartclient.gui.model.ComboBoxMap;
import com.seekon.smartclient.gui.tags.MDataModel;
import com.seekon.smartclient.gui.tags.MDefaultDataModel;
import com.seekon.smartclient.gui.tags.MJSONComboBoxModel;

public class BDReferViewDialog extends GuiEngine {

  private JDialog editDialog;

  private JTextField codeField;

  private JTextField nameField;

  private JComboBox typeField;

  private JCheckBox usedField;

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
              ((ComboBoxMap) typeField.getSelectedItem()).get("code"));
            mDataModel.addParam("is_used", usedField.isSelected() ? "1" : "0");
            mDataModel.addParam("ele_id", data.get("ele_id"));
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
        ComboBoxMap item = (ComboBoxMap) model.getElementAt(i);
        if (type.equals(item.get("code"))) {
          model.setSelectedItem(item);
          break;
        }
      }
    }
  }

  public boolean getReturnValue() {
    return this.returnValue;
  }

}
