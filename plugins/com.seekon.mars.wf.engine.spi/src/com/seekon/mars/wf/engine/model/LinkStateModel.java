package com.seekon.mars.wf.engine.model;

/**
 * 
 * 路径与状态的关联，当业务单据经过此路径时将同步业务单据的对应字段
 * 表wf_link_state
 * 
 */
public class LinkStateModel implements java.io.Serializable {

  private static final long serialVersionUID = -8365252729917751572L;

  private Long nodeLinkStateId;

	private Long nodeLinkId;

	private Long stateId;
	
	private String stateValue;
	
	private BindStateModel bindState;
	
	private LinkModel link;
	
	public Long getNodeLinkId() {
		return nodeLinkId;
	}

	public void setNodeLinkId(Long nodeLinkId) {
		this.nodeLinkId = nodeLinkId;
	}

	public Long getNodeLinkStateId() {
		return nodeLinkStateId;
	}

	public void setNodeLinkStateId(Long nodeLinkStateId) {
		this.nodeLinkStateId = nodeLinkStateId;
	}

	public String getStateValue() {
		return stateValue;
	}

	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}

  public Long getStateId() {
    return stateId;
  }

  public void setStateId(Long stateId) {
    this.stateId = stateId;
  }

  public BindStateModel getBindState() {
    return bindState;
  }

  public void setBindState(BindStateModel bindState) {
    this.bindState = bindState;
  }

  public LinkModel getLink() {
    return link;
  }

  public void setLink(LinkModel link) {
    this.link = link;
  }
	
}
