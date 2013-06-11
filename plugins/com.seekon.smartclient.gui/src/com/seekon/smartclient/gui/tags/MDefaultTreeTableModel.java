package com.seekon.smartclient.gui.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import com.seekon.smartclient.gui.model.TableColumn;

public class MDefaultTreeTableModel extends AbstractTreeTableModel{

  private MDataModel mDataModel = new MDefaultDataModel();

  private List<TableColumn> columnList = new ArrayList<TableColumn>();

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

  public Object refresh() {
    return mDataModel.refresh();
  }

  public int insert() {
    return mDataModel.insert();
  }

  public int delete() {
    return mDataModel.delete();
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
  public Class<?> getColumnClass(int columnIndex) {
    return columnList.get(columnIndex).getType();
  }

  @Override
  public String getColumnName(int column) {
    return columnList.get(column).getName();
  }
  
  @Override
  public int getColumnCount() {
    return columnList.size();
  }

  @Override
  public Object getValueAt(Object node, int column) {
    return null;
  }

  @Override
  public Object getChild(Object parent, int index) {
    return null;
  }

  @Override
  public int getChildCount(Object parent) {
    return 0;
  }

  @Override
  public int getIndexOfChild(Object parent, Object child) {
    return 0;
  }

}
