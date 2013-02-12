package com.seekon.smartclient.gui.tags;

public class MDefaultComponent implements MComponent {

  private String tableName;

  private String fieldName;

  @Override
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @Override
  public String getTableName() {
    return tableName;
  }

  @Override
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  @Override
  public String getFieldName() {
    return fieldName;
  }

}
