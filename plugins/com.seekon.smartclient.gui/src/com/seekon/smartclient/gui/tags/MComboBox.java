package com.seekon.smartclient.gui.tags;

import javax.swing.JComboBox;

public class MComboBox extends JComboBox{

  private static final long serialVersionUID = -8716856545182377734L;
  
  private MComponent mComponent = new MDefaultComponent();

  public void setTableName(String tableName) {
    mComponent.setTableName(tableName);
  }

  public String getTableName() {
    return mComponent.getTableName();
  }

  public void setFieldName(String fieldName) {
    mComponent.setFieldName(fieldName);
  }

  public String getFieldName() {
    return mComponent.getFieldName();
  }
  
}
