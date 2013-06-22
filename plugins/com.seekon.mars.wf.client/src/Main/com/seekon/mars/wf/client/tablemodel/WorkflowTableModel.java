package com.seekon.mars.wf.client.tablemodel;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.seekon.mars.wf.engine.model.runtime.ActionHistoryModel;

public class WorkflowTableModel extends DefaultTableModel{
  public WorkflowTableModel(List<ActionHistoryModel> dataList, List<String> headerName){
    this.dataVector = new Vector<ActionHistoryModel>();
    dataVector.addAll(dataList);
    this.columnIdentifiers = (Vector<String>) headerName;
  }
  
  @Override
  public int getColumnCount() {
    if (columnIdentifiers != null) {
      return columnIdentifiers.size();
    } else {
      return 0;
    }
  }

  @Override
  public int getRowCount() {
    if (dataVector != null) {
      return dataVector.size();
    } else {
      return 0;
    }
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Object biPayQuery = dataVector.get(rowIndex);
    String colName = columnIdentifiers.get(columnIndex).toString();
    Class beanClass = biPayQuery.getClass();
    Method method = null;
    Object result = null;
    try {
      method = beanClass.getMethod(getMethodName(colName, "get"), new Class[] {});
      result = method.invoke(biPayQuery, new Object[] {});
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  @Override
  public Class getColumnClass(int column) {
    if ((column >= 0) && (column < getColumnCount()) && this.getRowCount() > 0) {
      if (getValueAt(0, column) != null) {
        return getValueAt(0, column).getClass();
      }
    }
    return Object.class;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public Object getRowObject(int rowIndex) {
    return dataVector.get(rowIndex);
  }

  private String getMethodName(String property, String prefix) {
    String prop = Character.toUpperCase(property.charAt(0)) + property.substring(1);
    String methodName = prefix + prop;
    return methodName;
  }
}
