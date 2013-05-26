package com.seekon.smartclient.gui.tags;

import java.util.HashMap;
import java.util.Map;

import com.seekon.smartclient.gui.internal.ServiceFacade;

public class MDefaultDataModel implements MDataModel {

  private String sqlid;

  private Map<String, Object> params = new HashMap<String, Object>();

  @Override
  public void setSqlid(String sqlid) {
    this.sqlid = sqlid;
  }

  @Override
  public String getSqlid() {
    return sqlid;
  }

  @Override
  public void setParams(String paramString) {
    processParameter(paramString);
  }

  @Override
  public Map<String, Object> getParams() {
    return params;
  }

  @Override
  public Object refresh() {
    if (sqlid == null || sqlid.trim().length() == 0) {
      throw new RuntimeException("sqlid must not be null.");
    }
    return ServiceFacade.getModelService().getModelData(sqlid, params);
  }

  @Override
  public int insert() {
    if (sqlid == null || sqlid.trim().length() == 0) {
      throw new RuntimeException("sqlid must not be null.");
    }
    return ServiceFacade.getModelService().insert(sqlid, params);
  }
  
  @Override
  public int delete() {
    if (sqlid == null || sqlid.trim().length() == 0) {
      throw new RuntimeException("sqlid must not be null.");
    }
    return ServiceFacade.getModelService().delete(sqlid, params);
  }
  
  @Override
  public int update() {
    if (sqlid == null || sqlid.trim().length() == 0) {
      throw new RuntimeException("sqlid must not be null.");
    }
    return ServiceFacade.getModelService().update(sqlid, params);
  }
  
  @Override
  public void addParam(String name, Object value) {
    if(this.params.keySet().contains(name)){
      this.params.remove(name);
    }
    this.params.put(name, value);
  }

  private void processParameter(String parameter) {
    if (parameter == null || parameter.trim().length() == 0) {
      return;
    }

    String[] params = parameter.split(";");
    for (String param : params) {
      String[] entry = param.split("=");
      if (entry.length > 1) {
        String[] value = entry[1].split("[|]");
        this.params
          .put(entry[0].trim(), value.length == 1 ? entry[1].trim() : value);
      }
    }
  }

}
