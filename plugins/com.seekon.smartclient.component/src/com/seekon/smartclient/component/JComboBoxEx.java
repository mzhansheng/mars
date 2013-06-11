package com.seekon.smartclient.component;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * <p>Title: com.ufgov.smartclient.component</p>
 * <p>Description: 可以指定item显示标题的JCombobox</p>
 * <p>Copyright: Copyright 2009 ufgov, Inc.</p>
 * <p>Company: ufgov</p>
 * <p>创建时间: 2009-5-16</p>
 * @author 刘永伟(manlge)
 * @version 1.0
 */
public class JComboBoxEx extends JComboBox {

  private static final String DEFAULT_PROTOTYPE_STRING = "xxxxxxxxxxxx";

  private final class ComboBoxExCellRenderer implements ListCellRenderer {
    private ListCellRenderer render;

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
      Object displayLabel = lableMap.get(value);
      if (displayLabel == null)
        displayLabel = value;
      list.setToolTipText(displayLabel == null ? "" : displayLabel.toString());
      return render.getListCellRendererComponent(list, displayLabel, index,
        isSelected, cellHasFocus);
    }

  }

  private ComboBoxExCellRenderer cellRender = new ComboBoxExCellRenderer();

  private Map<Object, String> lableMap = new HashMap<Object, String>();

  public JComboBoxEx(ComboBoxModel model) {
    super(model);
    setPrototypeDisplayValue(JComboBoxEx.DEFAULT_PROTOTYPE_STRING);
  }

  public JComboBoxEx(Object[] items) {
    super(items);
    setPrototypeDisplayValue(JComboBoxEx.DEFAULT_PROTOTYPE_STRING);
  }

  public JComboBoxEx(Object[] items, String[] displayLabels) {
    super(items);
    if (items.length != displayLabels.length) {
      throw new IllegalArgumentException("items条目数必须与displayLabels长度相同");
    }
    for (int i = 0; i < items.length; i++) {
      setItemDisplayLable(items[i], displayLabels[i]);
    }
    setPrototypeDisplayValue(JComboBoxEx.DEFAULT_PROTOTYPE_STRING);
  }

  public JComboBoxEx(Vector<?> items) {
    super(items);
    setPrototypeDisplayValue(JComboBoxEx.DEFAULT_PROTOTYPE_STRING);
  }

  public JComboBoxEx() {
    super();
    setPrototypeDisplayValue(JComboBoxEx.DEFAULT_PROTOTYPE_STRING);
  }

  @Override
  public ListCellRenderer getRenderer() {
    ListCellRenderer render = super.getRenderer();
    if (render == null)
      return null;
    if (cellRender == null)
      cellRender = new ComboBoxExCellRenderer();
    cellRender.render = render;
    return cellRender;
  }

  public void addItem(Object item, String displayLabel) {
    super.addItem(item);
    setItemDisplayLable(item, displayLabel);
  }

  /**
   * 为对象指定显示标题
   * @param value
   * @param displayLabel
   */
  public void setItemDisplayLable(Object value, String displayLabel) {
    lableMap.put(value, displayLabel);
  }

  /**
   * 添加value对应的显示标题
   * @param value
   * @param displayLabel
   * @deprecated 请使用{@link #setItemDisplayLable(Object, String)}
   */
  @Deprecated
  public void addItemDisplaLable(Object value, String displayLabel) {
    lableMap.put(value, displayLabel);
  }

  /**
   * 返回指定显示项的显示标题
   * @param itemValue
   * @return
   */
  public String getItemDisplayLabel(Object itemValue) {
    return lableMap.get(itemValue);
  }

}
