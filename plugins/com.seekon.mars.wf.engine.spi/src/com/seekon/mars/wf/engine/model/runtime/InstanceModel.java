package com.seekon.mars.wf.engine.model.runtime;

/**
 * 
 * 流程实例
 * 表wf_instance
 * 
 */
public class InstanceModel implements java.io.Serializable {

	private static final long serialVersionUID = -9182382066488148948L;

	private Long instanceId;

	private String name;

	private String description;

	private Long templateId;

	private String owner;//发起者

	private String startTime;

	private String endTime;

	private Long status;//流程实例状态,1:活动的,-1:挂起的,9:结束的,-9:中止的

	private Long parentInstanceId = new Long(-1);//-1表示没有父流程

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getParentInstanceId() {
		return parentInstanceId;
	}

	public void setParentInstanceId(Long parentInstanceId) {
		this.parentInstanceId = parentInstanceId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }
	
  public String toString(){
    return name+"["+instanceId+"]";
  }
}
