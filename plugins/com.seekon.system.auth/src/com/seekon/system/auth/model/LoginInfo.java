package com.seekon.system.auth.model;

import java.io.Serializable;
import java.util.Map;

public class LoginInfo implements Serializable{

  private static final long serialVersionUID = 7486888721109126288L;
  
  private User user;
  
  private Map<String, Object> sessionMap;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Map<String, Object> getSessionMap() {
    return sessionMap;
  }

  public void setSessionMap(Map<String, Object> sessionMap) {
    this.sessionMap = sessionMap;
  }
  
}
