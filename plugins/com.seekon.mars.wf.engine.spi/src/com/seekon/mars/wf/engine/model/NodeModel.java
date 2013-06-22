package com.seekon.mars.wf.engine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 工作流节点模型 表wf_node
 * 
 */
public class NodeModel extends BaseNodeModel{

	private static final long serialVersionUID = 2015093932194203279L;

	private List nodeStateList;// 挂接到节点上的状态集

	private List executorSourceList;// 额外设置的执行者

	private List fromLinkList = new ArrayList();// 流入的链接

	private List toLinkList = new ArrayList();// 流出的链接

	private Map delegationMap = new HashMap();//定影的授权代理列表
	
	private TemplateModel template;

	public TemplateModel getTemplate() {
		return template;
	}

	public void setTemplate(TemplateModel template) {
		this.template = template;
	}

	public List getFromLinkList() {
		return fromLinkList;
	}

	public void setFromLinkList(List fromLinkList) {
		this.fromLinkList = fromLinkList;
	}

	public List getNodeStateList() {
		return nodeStateList;
	}

	public void setNodeStateList(List nodeState) {
		this.nodeStateList = nodeState;
	}

	public List getToLinkList() {
		return toLinkList;
	}

	public void setToLinkList(List toLinkList) {
		this.toLinkList = toLinkList;
	}

	public List getExecutorSourceList() {
		return executorSourceList;
	}

	public void setExecutorSourceList(List executorSourceList) {
		this.executorSourceList = executorSourceList;
	}

	public void addFromLink(LinkModel link) {
		if (link != null) {
			this.fromLinkList.add(link);
			link.setNextNode(this);
		}
	}

	public void addToLink(LinkModel link) {
		if (link != null) {
			this.toLinkList.add(link);
			link.setCurrentNode(this);
		}
	}

	public Map getDelegationMap() {
    return delegationMap;
  }

  public void setDelegationMap(Map delegationMap) {
    this.delegationMap = delegationMap;
  }

  public void addDelegation(Long delegationId, DelegationModel delegation){
    this.delegationMap.put(delegationId, delegation);
  }
  
  public DelegationModel getDelegation(Long delegationId){
    return (DelegationModel)this.delegationMap.get(delegationId);
  }
}
