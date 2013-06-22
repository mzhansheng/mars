package com.seekon.mars.wf.engine.model.runtime;


/**
 * 
 * 流程运行期有效的执行动作，当进行回退、收回等操作时将删除对应表的记录
 * 表wf_action
 *
 */
public class ActionModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 6295961453367162007L;

	private Long actionId;
		
	private Long instanceId;
		
	private Long nodeId;
			
	private String actionName;
		
	private String executor;
	
	private String arrivalTime;
		
	private String executeTime;
		
	private String description;
		
	private String owner;
		
	private String limitExecuteTime;
	
	private String coCode;
	
	private String orgCode;
	
	private String posiCode;
	
	private int nd;
	
	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getLimitExecuteTime() {
		return limitExecuteTime;
	}

	public void setLimitExecuteTime(String limitExecuteTime) {
		this.limitExecuteTime = limitExecuteTime;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

  public Long getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(Long instanceId) {
    this.instanceId = instanceId;
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
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

  public String getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }
	
}
