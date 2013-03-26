package com.seekon.mars.dictionary;

import java.io.Serializable;

public class TableMeta implements Serializable, Cloneable {

  private static final long serialVersionUID = -1841792047694612545L;

  private String tableCode;

  private String tableName;

  private TableMeta masterTable;

  private boolean isTable;
    
  public String getTableCode() {
    return tableCode;
  }

  public void setTableCode(String tableCode) {
    this.tableCode = tableCode;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public TableMeta getMasterTable() {
    return masterTable;
  }

  public void setMasterTable(TableMeta masterTable) {
    this.masterTable = masterTable;
  }

  public boolean isTable() {
    return isTable;
  }

  public void setTable(boolean isTable) {
    this.isTable = isTable;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((tableCode == null) ? 0 : tableCode.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TableMeta other = (TableMeta) obj;
    if (tableCode == null) {
      if (other.tableCode != null)
        return false;
    } else if (!tableCode.equals(other.tableCode))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return this.tableName;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
