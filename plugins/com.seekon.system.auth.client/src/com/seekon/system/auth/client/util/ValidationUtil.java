package com.seekon.system.auth.client.util;

public class ValidationUtil {

  public static boolean isBlank(String value) {
    return value == null || value.trim().length() == 0;
  }
}
