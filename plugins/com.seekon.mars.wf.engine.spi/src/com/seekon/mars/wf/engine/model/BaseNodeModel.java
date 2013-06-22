package com.seekon.mars.wf.engine.model;

public class BaseNodeModel implements java.io.Serializable{
  private Long templateId;
  
  private Long nodeId;

  private String name;

  private String description;

  private String type;// 节点类型,2:任务节点,3:分支节点

  private String businessType;// 节点业务类型?

  private String executorsMethod;// 节点执行方式,0:独签,1:并签,2:顺签

  private String taskListener;// 任务监听器

  private Long limitExecuteTerm;// 执行期限(小时)

  private Long remindExecuteTerm;// 提醒期限(小时)

  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
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

  public String getBusinessType() {
    return businessType;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public String getExecutorsMethod() {
    return executorsMethod;
  }

  public void setExecutorsMethod(String executorsMethod) {
    this.executorsMethod = executorsMethod;
  }

  public String getTaskListener() {
    return taskListener;
  }

  public void setTaskListener(String taskListener) {
    this.taskListener = taskListener;
  }

  public Long getLimitExecuteTerm() {
    return limitExecuteTerm;
  }

  public void setLimitExecuteTerm(Long limitExecuteTerm) {
    this.limitExecuteTerm = limitExecuteTerm;
  }

  public Long getRemindExecuteTerm() {
    return remindExecuteTerm;
  }

  public void setRemindExecuteTerm(Long remindExecuteTerm) {
    this.remindExecuteTerm = remindExecuteTerm;
  }
  
  public boolean equals(Object node) {
    if (node == null) {
      return false;
    } else {
      if (!(node instanceof NodeModel)) {
        return false;
      } else {
        BaseNodeModel model = (BaseNodeModel) node;
        return (this.getNodeId().longValue() == model.getNodeId()
            .longValue());
      }
    }
  }
  
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
    return result;
  }
  
  public String toString(){
    return name+"["+nodeId+"]";
  }
}
