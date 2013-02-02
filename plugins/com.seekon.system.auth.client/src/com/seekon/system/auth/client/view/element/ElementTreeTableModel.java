package com.seekon.system.auth.client.view.element;

import java.util.List;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import com.seekon.mars.dictionary.model.FieldMeta;
import com.seekon.system.common.model.Element;

public class ElementTreeTableModel extends AbstractTreeTableModel {

  private String[] columnNames = new String[] { "要素代码", "要素名称", "是否启用" };

  private List<Element> elementList;
  
  public ElementTreeTableModel(List<Element> elementList) {
    super("root");
    this.elementList = elementList;
  }

  public List<Element> getElementList() {
    return elementList;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }
  
  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(Object node, int column) {
    if (node instanceof FieldMeta) {
      FieldMeta field = (FieldMeta) node;
      switch (column) {
      case 0:
        return field.getColCode();
      case 1:
        return field.getColName();
      case 2:
        return false;
      }
    }else if(node instanceof Element){
      Element element = (Element) node;
      switch (column) {
      case 0:
        return element.getCode();
      case 1:
        return element.getName();
      case 2:
        return element.isEnable();
      }
    }
    return null;
  }

  @Override
  public void setValueAt(Object value, Object node, int column) {
    if(node instanceof Element){
      ((Element)node).setEnable(Boolean.valueOf(value.toString()));
    }
    super.setValueAt(value, node, column);
  }
  
  @Override
  public Object getChild(Object parent, int index) {
    if(root.equals(parent)){
      return elementList.get(index);
    }
    
    if (parent instanceof Element) {
      return ((Element)parent).getFieldList().get(index);
    }
    return null;
  }

  @Override
  public int getChildCount(Object parent) {
    if(root.equals(parent)){
      return elementList.size();
    }
    
    if (parent instanceof Element) {
      return ((Element)parent).getFieldList().size();
    } else {
      return 0;
    }
  }

  @Override
  public int getIndexOfChild(Object parent, Object child) {
    if(parent instanceof Element && child instanceof FieldMeta){
      List<FieldMeta> fieldList = ((Element)parent).getFieldList();
      for(int i = 0; i < fieldList.size(); i++){
        FieldMeta field = fieldList.get(i);
        if(field.equals(child)){
          return i;
        }
      }
    }
    return 0;
  }

  @Override
  public boolean isCellEditable(Object node, int column) {
    if(node instanceof Element && column == 2){
      return true;
    }
    return false;
  }
  
  @Override
  public Class<?> getColumnClass(int column) {
    return column == 2 ? Boolean.class : String.class;
  }
}
