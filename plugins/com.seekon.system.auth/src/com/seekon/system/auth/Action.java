package com.seekon.system.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
  String resourceId();

  String id();
}
