package com.seekon.mars.wf.engine.model.runtime;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 工作流业务数据对象
 *
 */
public class TableData implements java.io.Serializable{

  private Long instanceId;

  private String id;//tableId
  
  private String compoName;//compoName
  
  private String title;

  private Map fields = new HashMap();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  public String getCompoName() {
    return compoName;
  }

  public void setCompoName(String compoName) {
    this.compoName = compoName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(Long instanceId) {
    this.instanceId = instanceId;
  }
  
  public Map getFieldMap(){
    return fields;
  }
  
  public void setFieldValue(String fName, Object value) {
    fields.put(fName, value);
  }

  public Object getFieldValue(String fName) {
    return fields.get(fName);
  }
}
