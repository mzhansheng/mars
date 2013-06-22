package com.seekon.mars.wf.engine.model;

/**
 * 
 * 节点与状态的关联，当业务单据到达此节点时将同步业务单据的对应字段
 * 表wf_node_state
 * 
 */
public class NodeStateModel implements java.io.Serializable {

  private static final long serialVersionUID = 6386862429661148117L;

  private Long nodeStateId;//主键

	private Long nodeId;

	private Long stateId;

	private String stateValue;

	private BindStateModel bindState;//为便于程序操作添加
	
	private NodeModel node;//为便于程序操作添加
	
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Long getNodeStateId() {
		return nodeStateId;
	}

	public void setNodeStateId(Long nodeStateId) {
		this.nodeStateId = nodeStateId;
	}

	public String getStateValue() {
		return stateValue;
	}

	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}

  public BindStateModel getBindState() {
    return bindState;
  }

  public void setBindState(BindStateModel bindState) {
    this.bindState = bindState;
  }

  public NodeModel getNode() {
    return node;
  }

  public void setNode(NodeModel node) {
    this.node = node;
  }

  public Long getStateId() {
    return stateId;
  }

  public void setStateId(Long stateId) {
    this.stateId = stateId;
  }
	
}
