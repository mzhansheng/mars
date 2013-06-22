package com.seekon.mars.wf.engine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 
 * 工作流模板
 * 表wf_template
 * 
 */
public class TemplateModel extends BaseTemplateModel {

	private static final long serialVersionUID = 7978089612196700405L;

	private List nodeList = new ArrayList();

	private List linkList = new ArrayList();
	
	private List bindStateList = new ArrayList();
	
	private List bindVariableList = new ArrayList();

	private Vector delegationList = new Vector();//临时存放的代理授权信息，实际运行时直接从NodeModel中获取
	
	private NodeModel startNode;

	private NodeModel endNode;

	private Map nodeMap = new HashMap();
	
	private Map linkMap = new HashMap();
	
	private Map bindStateMap = new HashMap();
	

	public List getLinkList() {
		return linkList;
	}

	public void setLinkList(List linkList) {
		this.linkList = linkList;
	}

	public List getNodeList() {
		return nodeList;
	}

	public void setNodeList(List nodeList) {
		this.nodeList = nodeList;
	}

	public NodeModel getStartNode() {
		return startNode;
	}

	public void setStartNode(NodeModel startNode) {
		this.startNode = startNode;
	}

	public NodeModel getEndNode() {
    return endNode;
  }

  public void setEndNode(NodeModel endNode) {
    this.endNode = endNode;
  }

  public List getBindStateList() {
		return bindStateList;
	}

	public void setBindStateList(List bindStateList) {
		this.bindStateList = bindStateList;
	}

	public List getBindVariableList() {
		return bindVariableList;
	}

	public void setBindVariableList(List bindVariable) {
		this.bindVariableList = bindVariable;
	}

  public void setDelegationList(List delegationList) {
    this.delegationList.addAll(delegationList);
  }

  public NodeModel getNode(Long nodeId) {
	  return (NodeModel) nodeMap.get(nodeId);
	}
	
  public LinkModel getLink(Long nodeLinkId){
    return (LinkModel) linkMap.get(nodeLinkId);
  }
  
	/**
	 * 组装模板流程图
	 */
	public void assembleGraph() {
	  populateStateMap();
	  assembleTemplateNodes();
	  assembleTemplateLinks();
	  checkAndInitStartEndNode();
	}

	 /**
   * 设置状态集
   */
  private void populateStateMap() {
    for(int i = 0; i < bindStateList.size(); i++){
      BindStateModel model = (BindStateModel)bindStateList.get(i);
      bindStateMap.put(model.getStateId(), model);
    }
  }
  
	/**
	 * 组装模板中的节点，设置节点的状态集等
	 */
	private void assembleTemplateNodes(){
	  NodeModel node = null;
    for (int i = 0; i < nodeList.size(); i++) {
      node = (NodeModel) nodeList.get(i);
      Long nodeId = node.getNodeId();
      node.setTemplate(this);
      nodeMap.put(nodeId, node);
      
      if(!bindStateList.isEmpty()){
        List nodeStateList = node.getNodeStateList();
        if(nodeStateList != null && nodeStateList.size() > 0){
          for(int j = 0, stateSize = nodeStateList.size(); j < stateSize; j++){
            NodeStateModel stateModel = (NodeStateModel)nodeStateList.get(j);
            stateModel.setNode(node);
            stateModel.setBindState((BindStateModel)bindStateMap.get(stateModel.getStateId()));
          }
        }
      }
      
      if(!delegationList.isEmpty()){
        Iterator dIterator = delegationList.iterator();
        while(dIterator.hasNext()){
          DelegationModel delegation = (DelegationModel)dIterator.next();
          if(!delegation.isActive()){
            continue;
          }
          if(nodeId.equals(delegation.getNodeId()) || "*".equals(delegation.getNodeId())){
            node.addDelegation(delegation.getDelegationId(), delegation);
          }
          if(nodeId.equals(delegation.getNodeId())){
            dIterator.remove();//删除找到的代理授权信息，模板中不保留
          }
        }
      }
    }
	}
	
	/**
	 * 组装模板中的链接流向，设置链接的状态集
	 */
	private void assembleTemplateLinks(){
	  LinkModel link = null;
    NodeModel currentNode = null;
    NodeModel nextNode = null;
    Long currentNodeId = null;
    Long nextNodeId = null;
    
    for (int i = 0; i < linkList.size(); i++) {
      link = (LinkModel) linkList.get(i);
      link.setTemplate(this);
      linkMap.put(link.getNodeLinkId(), link);
      
      currentNodeId = link.getCurrentNodeId();
      currentNode = getNode(currentNodeId);
      nextNodeId = link.getNextNodeId();
      nextNode = getNode(nextNodeId);
      if (currentNode != null) {
        currentNode.addToLink(link);
        link.setCurrentNode(currentNode);        
      }
      if (nextNode != null) {
        nextNode.addFromLink(link);
        link.setNextNode(nextNode);
      }
      
      if (currentNodeId.intValue() == -1) {
        this.startNode = nextNode;
      }
      if (nextNodeId.intValue() == -2) {
        this.endNode = currentNode;
      }
      
      if(!bindStateList.isEmpty()){
        List linkStateList = link.getLinkStateList();
        if(linkStateList != null && linkStateList.size() > 0){
          for(int j = 0, stateSize = linkStateList.size(); j < stateSize; j++){
            LinkStateModel stateModel = (LinkStateModel)linkStateList.get(j);
            stateModel.setLink(link);
            stateModel.setBindState((BindStateModel)bindStateMap.get(stateModel.getStateId()));
          }
        }
      }
    }
	}

	private void checkAndInitStartEndNode(){
	  if(this.startNode != null && this.endNode != null){
	    return;
	  } 
	  
	  for(int i = 0; i < this.nodeList.size(); i++){
	    NodeModel node = (NodeModel)nodeList.get(i);
	    if(this.startNode == null && (
	      node.getFromLinkList() == null || node.getFromLinkList().isEmpty())){
	      this.startNode = node;
	    }
	    
	    if(this.endNode == null &&
	      (node.getToLinkList() == null || node.getToLinkList().isEmpty())){
	      this.endNode = node;
	    }
	    
	    if(this.startNode != null && this.endNode != null){
	      return;
	    } 
	  }
	}
}
