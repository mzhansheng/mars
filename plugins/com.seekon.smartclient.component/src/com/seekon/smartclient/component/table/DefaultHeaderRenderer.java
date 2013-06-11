package com.seekon.smartclient.component.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class DefaultHeaderRenderer extends DefaultTableCellRenderer {
  private boolean horizontalTextPositionSet;

  public DefaultHeaderRenderer() {
    setHorizontalAlignment(JLabel.CENTER);
  }

  @Override
  public void setHorizontalTextPosition(int textPosition) {
    horizontalTextPositionSet = true;
    super.setHorizontalTextPosition(textPosition);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Icon sortIcon = null;

    boolean isPaintingForPrint = false;

    if (table != null) {
      JTableHeader header = table.getTableHeader();
      if (header != null) {
        Color fgColor = null;
        Color bgColor = null;
        if (hasFocus) {
          fgColor = UIManager.getColor("TableHeader.focusCellForeground");
          bgColor = UIManager.getColor("TableHeader.focusCellBackground");
        }
        if (fgColor == null) {
          fgColor = header.getForeground();
        }
        if (bgColor == null) {
          bgColor = header.getBackground();
        }
        setForeground(fgColor);
        setBackground(bgColor);

        setFont(header.getFont());

        isPaintingForPrint = header.isPaintingForPrint();
      }

      if (!isPaintingForPrint && table.getRowSorter() != null) {
        if (!horizontalTextPositionSet) {
          // There is a row sorter, and the developer hasn't
          // set a text position, change to leading.
          setHorizontalTextPosition(JLabel.LEADING);
        }
        SortOrder sortOrder = getColumnSortOrder(table, column);
        if (sortOrder != null) {
          switch (sortOrder) {
          case ASCENDING:
            sortIcon = UIManager.getIcon("Table.ascendingSortIcon");
            break;
          case DESCENDING:
            sortIcon = UIManager.getIcon("Table.descendingSortIcon");
            break;
          case UNSORTED:
            sortIcon = UIManager.getIcon("Table.naturalSortIcon");
            break;
          }
        }
      }
    }

    setText(value == null ? "" : value.toString());
    setIcon(sortIcon);

    Border border = null;
    if (hasFocus) {
      border = UIManager.getBorder("TableHeader.focusCellBorder");
    }
    if (border == null) {
      border = UIManager.getBorder("TableHeader.cellBorder");
    }
    setBorder(border);
    //      if (JGroupableTableHeader.this.getUI().getRolloverColumn() == column) {
    //        setBackground(Color.gray);
    //      }
    return this;
  }

  public SortOrder getColumnSortOrder(JTable table, int column) {
    SortOrder rv = null;
    if (table.getRowSorter() == null) {
      return rv;
    }
    java.util.List<? extends RowSorter.SortKey> sortKeys = table.getRowSorter().getSortKeys();
    if (sortKeys.size() > 0 && sortKeys.get(0).getColumn() == table.convertColumnIndexToModel(column)) {
      rv = sortKeys.get(0).getSortOrder();
    }
    return rv;
  }
}