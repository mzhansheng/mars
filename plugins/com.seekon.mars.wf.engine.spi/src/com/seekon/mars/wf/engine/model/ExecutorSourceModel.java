package com.seekon.mars.wf.engine.model;

import com.seekon.mars.wf.engine.spi.util.DateUtil;

/**
 * 
 * 节点的分类执行者来源，除通过组织上下级、业务上下级等设置外的执行者设置
 * 表wf_executor_source、wf_executor_order
 * 
 */
public class ExecutorSourceModel implements java.io.Serializable {

  private static final long serialVersionUID = 5608408383466751920L;

  private String nodeId;

	private String executor;
	
	private String executorName;

	private int source;//1：公司，2：组织，3：角色，4：职位，5：职员

	private Long responsibility = new Long(1);//1：主办，2：辅办

	private int executorOrder;//正整数，值越小表示优先级越高
	
	private String coCode;
	
	private String orgCode;
	
	private String posiCode;
	
	private int nd = DateUtil.getYear();
	
	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public Long getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Long responsibility) {
		this.responsibility = responsibility;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

  public int getExecutorOrder() {
    return executorOrder;
  }

  public void setExecutorOrder(int executorOrder) {
    this.executorOrder = executorOrder;
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

  public int getNd() {
    return nd;
  }

  public void setNd(int nd) {
    this.nd = nd;
  }

  public String getNodeId() {
    return nodeId;
  }

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }

  public String getPosiCode() {
    return posiCode;
  }

  public void setPosiCode(String posiCode) {
    this.posiCode = posiCode;
  }

  public String getExecutorName() {
    return executorName;
  }

  public void setExecutorName(String executorName) {
    this.executorName = executorName;
  }
}
