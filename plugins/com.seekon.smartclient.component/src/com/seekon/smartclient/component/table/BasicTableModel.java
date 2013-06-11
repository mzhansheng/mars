package com.seekon.smartclient.component.table;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: Smart Client</p>
 * <p>Description: 可以隐藏/显示指定列的TableMode</p>
 * <p>Copyright: Copyright 2007 ufgov, Inc.</p>
 * <p>Company: ufgov</p>
 * <p>创建时间: 2007-10-19</p>
 * @author 刘永伟(manlge)
 * @version 1.0
 */
@Deprecated
public class BasicTableModel extends ListTableModel {

  public BasicTableModel() {
    super();
    visibleColumns = columnIdentifiers;
  }

  public BasicTableModel(int rowCount, int columnCount) {
    super(rowCount, columnCount);
    visibleColumns = columnIdentifiers;
  }

  public BasicTableModel(List columnNames, int rowCount) {
    super(columnNames, rowCount);
    visibleColumns = columnIdentifiers;
  }

  public BasicTableModel(List data, List columnNames) {
    super(data, columnNames);
    visibleColumns = columnIdentifiers;
  }

  public BasicTableModel(Object[] columnNames, int rowCount) {
    super(columnNames, rowCount);
    visibleColumns = columnIdentifiers;
  }

  public BasicTableModel(Object[][] data, Object[] columnNames) {
    super(data, columnNames);
    visibleColumns = columnIdentifiers;
  }

  /**
   * 返回实际的(未经过隐藏的)列名列表
   * @return List
   */
  public List<String> getFactColumnNameList() {
    return this.columnIdentifiers;
  }

  /**
   * 隐藏列名列表
   */
  private List<String> hideColumns = new ArrayList<String>();

  /**
   * 显示列名
   */
  private List<String> visibleColumns = new ArrayList<String>();

  /**
   * 是否显示求和、汇总行
   */
  private boolean showSumRow = false;

  @Override
  public int getColumnCount() {
    return visibleColumns.size();
  }

  /**
   * 转换column为实际字段对应的索引号
   * @param column int
   * @return int
   */
  public int convertColumnIndex2VerityIndex(int column) {
    Object o = visibleColumns.get(column);
    return columnIdentifiers.indexOf(o);
  }

  @Override
  public String getColumnName(int column) {
    return visibleColumns.get(column);
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return super.getColumnClass(convertColumnIndex2VerityIndex(columnIndex));
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    if (showSumRow && row == this.getRowCount() - 1)
      return false; //汇总行不能编辑
    return super.isCellEditable(row, convertColumnIndex2VerityIndex(column));
  }

  @Override
  public void setValueAt(Object aValue, int row, int column) {
    List rowList = dataList.get(row);
    rowList.set(convertColumnIndex2VerityIndex(column), aValue);
    fireTableCellUpdated(row, column);
  }

  @Override
  public Object getValueAt(int row, int column) {
    return super.getValueAt(row, convertColumnIndex2VerityIndex(column));
  }

  @Override
  public void setDataList(List dataList, List columnIdentifiers) {
    super.setDataList(dataList, columnIdentifiers);
    updateColumns();
  }

  public void addHideColumn(String columnName) {
    this.hideColumns.add(columnName);
    updateColumns();
  }

  public void removeHideColumn(String columnName) {
    this.hideColumns.remove(columnName);
    updateColumns();
  }

  public void clearHideColumns() {
    hideColumns.clear();
    updateColumns();
  }

  public void updateColumns() {
    if (hideColumns == null)
      hideColumns = new ArrayList<String>();
    //hideColumns.clear();
    if (columnIdentifiers != null) {
      this.visibleColumns = new ArrayList<String>(columnIdentifiers);
      this.visibleColumns.removeAll(hideColumns);
      this.fireTableStructureChanged();
    }
  }

  public void setShowSumRow(boolean b) {
    if (showSumRow == b)
      return; //如果没有改变直接返回

    //showSumRow == true b == false
    if (showSumRow) {
      removeRow(getRowCount() - 1);
    } else { //showSumRow == false b == true;
      List<Object> list = new java.util.ArrayList<Object>();
      while (list.size() < this.getFactColumnNameList().size()) {
        list.add(null);
      }
      this.insertRow(getRowCount(), list);
      this.showSumRow = b;
      updateSumRow();
    }
  }

  private void updateSumRow(int column) {
    class Total {
      public Double sum(String column) throws Exception {
        int index = getFactColumnNameList().indexOf(column);
        if (index == -1)
          throw new Exception("列" + column + "不存在");
        Double total = 0.0;
        for (int j = 0; j < getRowCount() - 1; j++) {
          Object obj = getDataList().get(j).get(index);
          if (obj == null)
            continue;
          double v = 0.0;
          try {
            v = Double.parseDouble(obj.toString());
            total += v;
          } catch (NumberFormatException ex) {
          }
        }
        return total;
      }

      public int count() {
        return getRowCount() - 1;
      }

      public Double avg(String column) throws Exception {
        return sum(column) / count();
      }
    }

    Object o = null;
    getDataList().get(getRowCount() - 1).set(column, o);
  }

  private void updateSumRow() {
    if (!isShowSumRow()) {
      return;
    }
    for (int i = 0; i < this.columnIdentifiers.size(); i++) {
      updateSumRow(i);
    }
  }

  @Override
  public void fireTableRowsInserted(int firstRow, int lastRow) {
    super.fireTableRowsInserted(firstRow, lastRow);
    if (isShowSumRow())
      updateSumRow();
  }

  @Override
  public void fireTableRowsDeleted(int firstRow, int lastRow) {
    super.fireTableRowsDeleted(firstRow, lastRow);
    if (isShowSumRow())
      updateSumRow();
  }

  @Override
  public void fireTableCellUpdated(int row, int column) {
    super.fireTableCellUpdated(row, column);
    if (isShowSumRow())
      updateSumRow();
  }

  public boolean isShowSumRow() {
    return showSumRow;
  }

}
