package com.seekon.mars.wf.engine.model;

/**
 * 
 * 模板绑定变量集，同时设置需要变量绑定的业务表、条件和表达式
 * 表as_wf_bind_variable与wf_variable
 *
 */
public class BindVariableModel implements java.io.Serializable {

	private static final long serialVersionUID = -8389220349443404888L;

  public static String STRING = "1";
  
  public static String NUMBER = "0";
  
	private Long templateId = null;
	
	private String bindExpression;
	
	private String tableId;
	
	private String condition;
	
	private String filterByKey = "N";
	
  private Long variableId = null;

  private String name = null;

  private String description = null;

  private String type = null;//0：数字，1：字符串

	public String getBindExpression() {
		return bindExpression;
	}

	public void setBindExpression(String bindExpression) {
		this.bindExpression = bindExpression;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getFilterByKey() {
		return filterByKey;
	}

	public void setFilterByKey(String filterByKey) {
		this.filterByKey = filterByKey;
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

  public Long getVariableId() {
    return variableId;
  }

  public void setVariableId(Long variableId) {
    this.variableId = variableId;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
	
}
