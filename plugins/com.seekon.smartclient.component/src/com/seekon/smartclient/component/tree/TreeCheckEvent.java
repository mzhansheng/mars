package com.seekon.smartclient.component.tree;

import java.util.EventObject;

import com.seekon.smartclient.component.tree.JCheckTree.CheckState;

public class TreeCheckEvent extends EventObject {

  private Object node;

  private CheckState newState;

  private CheckState oldState;

  public TreeCheckEvent(Object source, Object node, CheckState oldState,
    CheckState state) {
    super(source);
    this.node = node;
    this.oldState = oldState;
    this.newState = state;
  }

  public Object getNode() {
    return node;
  }

  public CheckState getOldState() {
    return oldState;
  }

  public CheckState getNewState() {
    return newState;
  }

  public JCheckTree getTree() {
    return (JCheckTree) source;
  }

}
