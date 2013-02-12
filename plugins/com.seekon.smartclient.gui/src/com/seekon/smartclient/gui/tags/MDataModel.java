package com.seekon.smartclient.gui.tags;

import java.util.Map;

public interface MDataModel {

  void setSqlid(String sqlid);

  String getSqlid();

  void setParams(String paramString);

  Map<String, Object> getParams();
  
  void addParam(String name, Object value);
   
  Object refresh();
  
  int insert();
  
  int delete();
}
