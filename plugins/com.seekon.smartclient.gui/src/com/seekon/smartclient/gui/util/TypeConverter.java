package com.seekon.smartclient.gui.util;

public class TypeConverter {

  public static Object convert(Class<?> clazz, Object value) {
    if (value == null || clazz == null) {
      return value;
    }

    if (clazz.isAssignableFrom(Boolean.class)) {
      return "1".equals(value) || "Y".equalsIgnoreCase(value.toString()) ? true
        : Boolean.valueOf(value.toString());
    }

    return value;
  }
}
