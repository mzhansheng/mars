package com.seekon.mars.wf.engine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 工作流链接(流向)模型
 * 表wf_link
 *
 */
public class LinkModel implements java.io.Serializable {

  private static final long serialVersionUID = -2387727816754004971L;

  private Long nodeLinkId;

	private String name;

	private String description;

	private String linkType;//0:普通,1:开始和结束,2:提交后汇总
	
	private Long currentNodeId;
	
	private Long nextNodeId;
	
	private String executorRelation;//0:无(可任意选择),1:组织上下级,3:业务上下级,2:自己

	private String executorsMethod;//执行方式?

	private String numberOrPercent;//0:数字,1:比例

	private Double passValue;//通过的值

	private String expression ;//defaultPath为0时的条件表达式,表达式中可使用定义的变量

	private String defaultPath;//0:条件,1:默认路径

	private String actionName;//当linkType为2时，对应的汇总动作
	
	private List linkStateList = new ArrayList();//流向上挂接的状态集
	
	private TemplateModel template;

	private NodeModel currentNode;
	  
	private NodeModel nextNode;
	  
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getDefaultPath() {
		return defaultPath;
	}

	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExecutorRelation() {
		return executorRelation;
	}

	public void setExecutorRelation(String executorRelation) {
		this.executorRelation = executorRelation;
	}

	public String getExecutorsMethod() {
		return executorsMethod;
	}

	public void setExecutorsMethod(String executorsMethod) {
		this.executorsMethod = executorsMethod;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNodeLinkId() {
		return nodeLinkId;
	}

	public void setNodeLinkId(Long nodeLinkId) {
		this.nodeLinkId = nodeLinkId;
	}

	public String getNumberOrPercent() {
		return numberOrPercent;
	}

	public void setNumberOrPercent(String numberOrPercent) {
		this.numberOrPercent = numberOrPercent;
	}

	public Double getPassValue() {
		return passValue;
	}

	public void setPassValue(Double passValue) {
		this.passValue = passValue;
	}
	
	public void setCurrentNode(NodeModel node) {
		this.currentNode = node;
	}

	public NodeModel getCurrentNode() {
		return this.currentNode;
	}
	
	public void setNextNode(NodeModel node) {
		this.nextNode = node;
	}
	
	public NodeModel getNextNode() {
		return this.nextNode;
	}

	public List getLinkStateList() {
		return linkStateList;
	}

	public void setLinkStateList(List linkState) {
		this.linkStateList = linkState;
	}
	
	public TemplateModel getTemplate() {
		return template;
	}

	public void setTemplate(TemplateModel template) {
		this.template = template;
	}

  public Long getCurrentNodeId() {
    return currentNodeId;
  }

  public void setCurrentNodeId(Long currentNodeId) {
    this.currentNodeId = currentNodeId;
  }

  public Long getNextNodeId() {
    return nextNodeId;
  }

  public void setNextNodeId(Long nextNodeId) {
    this.nextNodeId = nextNodeId;
  }
	
  public boolean equals(Object obj){
    if(obj == null || !(obj instanceof LinkModel)){
      return false;
    }
    
    LinkModel tmp = (LinkModel)obj;
    
    return ((this.nodeLinkId == null && tmp.nextNodeId == null) 
      || (this.nodeLinkId != null && this.nodeLinkId.equals(tmp.nodeLinkId)));
  }
}
