package com.seekon.smartclient.gui.model;

import java.io.Serializable;

public class TableColumn implements Serializable {
  private static final long serialVersionUID = -6812408517976287249L;

  private String key;

  private String name;

  private Class<?> type;

  public TableColumn(String col) {
    super();

    String[] values = col.trim().split(",");
    if (values.length > 2) {
      this.key = values[0].trim();
      this.name = values[1].trim();
      try {
        this.type = Class.forName(values[2].trim());
      } catch (Throwable e) {
        this.type = String.class;
      }
    } else if (values.length == 2) {
      this.key = values[0].trim();
      this.name = values[1].trim();
      this.type = String.class;
    } else if (values.length == 1) {
      this.key = values[0].trim();
      this.name = values[0].trim();
      this.type = String.class;
    }
  }

  public TableColumn(String key, String name, String type) {
    super();
    this.key = key;
    this.name = name;
    try {
      this.type = Class.forName(type);
    } catch (Throwable e) {
      this.type = String.class;
    }
  }

  public TableColumn(String key, String name, Class<?> type) {
    super();
    this.key = key;
    this.name = name;
    this.type = type;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Class<?> getType() {
    return type;
  }

  public void setType(Class<?> type) {
    this.type = type;
  }

}
