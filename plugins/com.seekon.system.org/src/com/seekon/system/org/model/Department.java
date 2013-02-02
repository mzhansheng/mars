package com.seekon.system.org.model;

public class Department extends AbstractOrgItem {

  private static final long serialVersionUID = -1034391918090958634L;

  private Enterprise belongedEnt;

  public Department() {
    super();
    type = "002";
  }

  public Enterprise getBelongedEnt() {
    return belongedEnt;
  }

  public void setBelongedEnt(Enterprise belongedEnt) {
    this.belongedEnt = belongedEnt;
  }

}
