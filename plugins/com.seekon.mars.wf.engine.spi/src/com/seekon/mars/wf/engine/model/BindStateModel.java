package com.seekon.mars.wf.engine.model;

import java.io.Serializable;

/**
 * 
 * 模板绑定状态集，同时设置需要将状态进行同步的业务表及字段
 * 表as_wf_bind_state、wf_state
 * 
 */
public class BindStateModel implements Serializable {

	private static final long serialVersionUID = -2422343444490193695L;

	private Long templateId;
	
	private Long stateId;
	
	private String name;

  private String description;

	private String tableId;

	private String fieldId;

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

  public Long getStateId() {
    return stateId;
  }

  public void setStateId(Long stateId) {
    this.stateId = stateId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
