package com.seekon.mars.gui.service;

import java.util.List;
import java.util.Map;

public interface ModelService {

  public List<Map<String, Object>> getModelData(String sqlid, Map<String, Object> params);
  
  public int insert(String sqlid, Map<String, Object> params);
  
  public int update(String sqlid, Map<String, Object> params);
  
  public int delete(String sqlid, Map<String, Object> params);
}
