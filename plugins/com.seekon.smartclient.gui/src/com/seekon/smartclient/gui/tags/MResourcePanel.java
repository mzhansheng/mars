package com.seekon.smartclient.gui.tags;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MResourcePanel extends JPanel{

  private static final long serialVersionUID = 8265426898170606510L;
  
  private String id;

  public MResourcePanel() {
    super(new BorderLayout());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
}
