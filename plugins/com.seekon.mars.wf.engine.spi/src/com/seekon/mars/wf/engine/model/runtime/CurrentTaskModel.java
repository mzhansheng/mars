package com.seekon.mars.wf.engine.model.runtime;


/**
 * 
 * 工作流代办工作
 * 表wf_current_task
 *
 */
public class CurrentTaskModel implements java.io.Serializable {

	private static final long serialVersionUID = -2027339582973511263L;

	private Long currentTaskId;
	
	private Long instanceId;
	
	private Long nodeId;
	
	private Long responsibility = new Long(1);

	private Long parentTaskId = new Long(-1);

	private String executor;
	
	private Long delegationId = new Long(1);//委托代理id

	private String owner;

	private String creator;

	private String createTime;

	private String limitExecuteTime;
	
	private String coCode;
	
	private String orgCode;
	
	private String posiCode;
	
	private int nd;
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getCurrentTaskId() {
		return currentTaskId;
	}

	public void setCurrentTaskId(Long currentTaskId) {
		this.currentTaskId = currentTaskId;
	}

	public Long getDelegationId() {
		return delegationId;
	}

	public void setDelegationId(Long delegationId) {
		this.delegationId = delegationId;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public Long getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(Long instanceId) {
    this.instanceId = instanceId;
  }

  public String getLimitExecuteTime() {
		return limitExecuteTime;
	}

	public void setLimitExecuteTime(String limitExecuteTime) {
		this.limitExecuteTime = limitExecuteTime==null?"":limitExecuteTime;
	}

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public Long getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Long responsibility) {
		this.responsibility = responsibility;
	}

  public String getCoCode() {
    return coCode;
  }

  public void setCoCode(String coCode) {
    this.coCode = coCode;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getPosiCode() {
    return posiCode;
  }

  public void setPosiCode(String posiCode) {
    this.posiCode = posiCode;
  }

  public int getNd() {
    return nd;
  }

  public void setNd(int nd) {
    this.nd = nd;
  }
	
}
