package com.seekon.system.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.mars.dictionary.TableMeta;

public class Element implements Serializable, Cloneable {

  private static final long serialVersionUID = -4567093179654120245L;

  private String id;
  
  private String code;

  private String name;
  
  private boolean enable;
  
  private ElementType eleType;

  private TableMeta table;

  private List<FieldMeta> fieldList = new ArrayList<FieldMeta>();

  public Element() {
    super();
  }

  public Element(String code, String name) {
    super();
    this.code = code;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public ElementType getEleType() {
    return eleType;
  }

  public void setEleType(ElementType eleType) {
    this.eleType = eleType;
  }

  public TableMeta getTable() {
    return table;
  }

  public void setTable(TableMeta table) {
    this.table = table;
  }

  public List<FieldMeta> getFieldList() {
    return fieldList;
  }

  public void setFieldList(List<FieldMeta> fieldList) {
    this.fieldList = fieldList;
  }

  public void addField(FieldMeta field) {
    this.fieldList.add(field);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
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
    Element other = (Element) obj;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    return true;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
