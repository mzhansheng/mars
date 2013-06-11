package com.seekon.mars.dictionary;

import java.io.Serializable;

public class FieldMeta implements Serializable {

  private static final long serialVersionUID = 7445640492600335592L;

  private String colCode;

  private String colName;

  private String colType;//text/number/reference/list

  private TableMeta belongedTable;

  public String getColCode() {
    return colCode;
  }

  public void setColCode(String colCode) {
    this.colCode = colCode;
  }

  public String getColName() {
    return colName;
  }

  public void setColName(String colName) {
    this.colName = colName;
  }

  public String getColType() {
    return colType;
  }

  public void setColType(String colType) {
    this.colType = colType;
  }

  public TableMeta getBelongedTable() {
    return belongedTable;
  }

  public void setBelongedTable(TableMeta belongedTable) {
    this.belongedTable = belongedTable;
  }

  public String getFullColCode() {
    if (this.belongedTable != null) {
      return this.belongedTable.getTableCode() + "." + this.colCode;
    }
    return this.colCode;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
      + ((belongedTable == null) ? 0 : belongedTable.hashCode());
    result = prime * result + ((colCode == null) ? 0 : colCode.hashCode());
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
    FieldMeta other = (FieldMeta) obj;
    if (belongedTable == null) {
      if (other.belongedTable != null)
        return false;
    } else if (!belongedTable.equals(other.belongedTable))
      return false;
    if (colCode == null) {
      if (other.colCode != null)
        return false;
    } else if (!colCode.equals(other.colCode))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return this.colName;
  }
}
