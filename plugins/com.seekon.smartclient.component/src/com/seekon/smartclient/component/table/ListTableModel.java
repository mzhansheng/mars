package com.seekon.smartclient.component.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

/**
 * <p>Title: Smart Client</p>
 * <p>Description: 基于List的TableModel实现类</p>
 * <p>Copyright: Copyright 2007 ufgov, Inc.</p>
 * <p>Company: ufgov</p>
 * <p>创建时间: 2007-10-26</p>
 * @author 刘永伟(manlge)
 * @version 1.0
 */
public class ListTableModel extends AbstractTableModel implements Serializable {

  private static final long serialVersionUID = -851990252630243286L;

  private static void changeListSize(List list, int newSize) {
    while (list.size() < newSize)
      list.add(null);
    while (list.size() > newSize)
      list.remove(list.size() - 1);
  }

  protected List<List> dataList;

  protected List<String> columnIdentifiers;

  public ListTableModel() {
    this(0, 0);
  }

  private static List<Object> newList(int size) {
    List<Object> v = new ArrayList<Object>(size);
    changeListSize(v, size);
    return v;
  }

  public ListTableModel(int rowCount, int columnCount) {
    this(newList(columnCount), rowCount);
  }

  public ListTableModel(List columnNames, int rowCount) {
    setDataList(newList(rowCount), columnNames);
  }

  public ListTableModel(Object[] columnNames, int rowCount) {
    this(convertToList(columnNames), rowCount);
  }

  public ListTableModel(List data, List columnNames) {
    setDataList(data, columnNames);
  }

  public ListTableModel(Object[][] data, Object[] columnNames) {
    setDataList(data, columnNames);
  }

  public List<List> getDataList() {
    return dataList;
  }

  private static List nonNullList(List v) {
    return (v != null) ? v : new ArrayList();
  }

  public void setDataList(List dataList, List columnIdentifiers) {
    this.dataList = nonNullList(dataList);
    this.columnIdentifiers = nonNullList(columnIdentifiers);
    justifyRows(0, getRowCount());
    fireTableStructureChanged();
  }

  public void setDataList(Object[][] dataList, Object[] columnIdentifiers) {
    setDataList(convertToList(dataList), convertToList(columnIdentifiers));
  }

  public void newDataAvailable(TableModelEvent event) {
    fireTableChanged(event);
  }

  private void justifyRows(int from, int to) {
    changeListSize(dataList, getRowCount());

    for (int i = from; i < to; i++) {
      if (dataList.get(i) == null) {
        dataList.set(i, new ArrayList<Object>());
      }
      changeListSize((dataList.get(i)), columnIdentifiers.size());
    }
  }

  public void newRowsAdded(TableModelEvent e) {
    justifyRows(e.getFirstRow(), e.getLastRow() + 1);
    fireTableChanged(e);
  }

  public void rowsRemoved(TableModelEvent event) {
    fireTableChanged(event);
  }

  public void setNumRows(int rowCount) {
    int old = getRowCount();
    if (old == rowCount) {
      return;
    }
    changeListSize(dataList, rowCount);
    if (rowCount <= old) {
      fireTableRowsDeleted(rowCount, old - 1);
    } else {
      justifyRows(old, rowCount);
      fireTableRowsInserted(old, rowCount - 1);
    }
  }

  public void setRowCount(int rowCount) {
    setNumRows(rowCount);
  }

  public void addRow(List rowData) {
    insertRow(getRowCount(), rowData);
  }

  public void addRow(Object[] rowData) {
    addRow(convertToList(rowData));
  }

  public void insertRow(int row, List rowData) {
    dataList.add(row, rowData);
    justifyRows(row, row + 1);
    fireTableRowsInserted(row, row);
  }

  public void insertRow(int row, Object[] rowData) {
    insertRow(row, convertToList(rowData));
  }

  private static int gcd(int i, int j) {
    return (j == 0) ? i : gcd(j, i % j);
  }

  private static void rotate(List v, int a, int b, int shift) {
    int size = b - a;
    int r = size - shift;
    int g = gcd(size, r);
    for (int i = 0; i < g; i++) {
      int to = i;
      Object tmp = v.get(a + to);
      for (int from = (to + r) % size; from != i; from = (to + r) % size) {
        v.add(a + to, v.get(a + from));
        to = from;
      }
      v.add(a + to, tmp);
    }
  }

  public void moveRow(int start, int end, int to) {
    int shift = to - start;
    int first, last;
    if (shift < 0) {
      first = to;
      last = end;
    } else {
      first = start;
      last = to + end - start;
    }
    rotate(dataList, first, last + 1, shift);

    fireTableRowsUpdated(first, last);
  }

  public void removeRow(int row) {
    dataList.remove(row);
    fireTableRowsDeleted(row, row);
  }

  public void setColumnIdentifiers(List columnIdentifiers) {
    setDataList(dataList, columnIdentifiers);
  }

  public void setColumnIdentifiers(Object[] newIdentifiers) {
    setColumnIdentifiers(convertToList(newIdentifiers));
  }

  public void setColumnCount(int columnCount) {
    changeListSize(columnIdentifiers, columnCount);
    justifyRows(0, getRowCount());
    fireTableStructureChanged();
  }

  public void addColumn(String columnName) {
    addColumn(columnName, (List) null);
  }

  public void addColumn(String columnName, List columnData) {
    columnIdentifiers.add(columnName);
    if (columnData != null) {
      int columnSize = columnData.size();
      if (columnSize > getRowCount()) {
        changeListSize(dataList, columnSize);
      }
      justifyRows(0, getRowCount());
      int newColumn = getColumnCount() - 1;
      for (int i = 0; i < columnSize; i++) {
        List row = dataList.get(i);
        row.add(newColumn, columnData.get(i));
      }
    } else {
      justifyRows(0, getRowCount());
    }

    fireTableStructureChanged();
  }

  public void addColumn(String columnName, Object[] columnData) {
    addColumn(columnName, convertToList(columnData));
  }

  public int getRowCount() {
    return dataList.size();
  }

  public int getColumnCount() {
    return columnIdentifiers.size();
  }

  @Override
  public String getColumnName(int column) {
    Object id = null;
    if (column < columnIdentifiers.size() && (column >= 0)) {
      id = columnIdentifiers.get(column);
    }
    return (id == null) ? super.getColumnName(column) : id.toString();
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return true;
  }

  public Object getValueAt(int row, int column) {
    List rowList = dataList.get(row);
    return rowList.get(column);
  }

  @Override
  public void setValueAt(Object aValue, int row, int column) {
    List rowList = dataList.get(row);
    rowList.set(column, aValue);
    fireTableCellUpdated(row, column);
  }

  protected static <T> List<T> convertToList(T[] anArray) {
    if (anArray == null) {
      return null;
    }
    List<T> v = new ArrayList<T>(anArray.length);
    for (int i = 0; i < anArray.length; i++) {
      v.add(anArray[i]);
    }
    return v;
  }

  protected static List convertToList(Object[][] anArray) {
    if (anArray == null) {
      return null;
    }
    List v = new ArrayList(anArray.length);
    for (int i = 0; i < anArray.length; i++) {
      v.add(convertToList(anArray[i]));
    }
    return v;
  }
}
