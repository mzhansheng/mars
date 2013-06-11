package com.seekon.smartclient.gui.tags;

import javax.swing.JTextField;

public class MTextField extends JTextField{

  private static final long serialVersionUID = 7571151537031947120L;

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
