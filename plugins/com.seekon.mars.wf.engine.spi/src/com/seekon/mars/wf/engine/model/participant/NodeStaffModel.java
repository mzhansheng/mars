package com.seekon.mars.wf.engine.model.participant;

/**
 * 节点执行者
 * @author Administrator
 *
 */
public class NodeStaffModel extends StaffModel{
  private Long nodeId;
  private String actionDescription;

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public String getActionDescription() {
    return actionDescription;
  }

  public void setActionDescription(String actionDescription) {
    this.actionDescription = actionDescription;
  }

}
