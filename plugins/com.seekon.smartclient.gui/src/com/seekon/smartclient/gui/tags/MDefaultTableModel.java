package com.seekon.smartclient.gui.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.gui.model.TableColumn;
import com.seekon.smartclient.gui.util.TypeConverter;

public class MDefaultTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1269269934330510520L;

  private MDataModel mDataModel = new MDefaultDataModel();

  private Vector<Map> dataList = null;

  private List<TableColumn> columnList = new ArrayList<TableColumn>();
  
  private boolean editable = true;
  
  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public void setSqlid(String sqlid) {
    mDataModel.setSqlid(sqlid);
  }

  public String getSqlid() {
    return mDataModel.getSqlid();
  }

  public void setParams(String paramString) {
    mDataModel.setParams(paramString);
  }

  public Map<String, Object> getParams() {
    return mDataModel.getParams();
  }

  public void addParam(String name, Object value) {
    mDataModel.addParam(name, value);
  }

  public void refresh() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<List<Map>>() {

      @Override
      public List<Map> execute() throws Exception {
        return (List<Map>) mDataModel.refresh();
      }

      @Override
      public void success(List<Map> result) {
        setDataList(result);
      }
    });
  }

  public void setDataList(List<Map> dataList) {
    this.dataList = new Vector<Map>(dataList);
    this.fireTableDataChanged();
  }

  public Map getRowData(int rowIndex){
    return this.dataList.get(rowIndex);
  }
  
  public void setColumns(String columnString) {
    if (columnString != null) {
      String[] columns = columnString.trim().split(";");
      for (String col : columns) {
        columnList.add(new TableColumn(col));
      }
    }
  }

  @Override
  public int getColumnCount() {
    return columnList.size();
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return columnList.get(columnIndex).getType();
  }

  @Override
  public String getColumnName(int column) {
    return columnList.get(column).getName();
  }

  @Override
  public int getRowCount() {
    return dataList == null ? 0 : dataList.size();
  }

  @Override
  public Object getValueAt(int row, int column) {
    if (dataList == null) {
      return null;
    }
    Map<String, Object> data = dataList.get(row);
    return TypeConverter.convert(getColumnClass(column),
      data.get(columnList.get(column).getKey()));
  }

  @Override
  public void setValueAt(Object aValue, int row, int column) {
    Map<String, Object> data = dataList.get(row);
    data.put(columnList.get(column).getKey(), aValue);
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return editable;
  }

  public void addRow(Map<String, Object> rowData) {
    insertRow(getRowCount(), rowData);
  }

  public void insertRow(int row, Map<String, Object> rowData) {
    this.dataList.insertElementAt(rowData, row);
    fireTableRowsInserted(row, row);
  }

  public void removeRow(int row) {
    this.dataList.removeElementAt(row);
    fireTableRowsDeleted(row, row);
  }

}
