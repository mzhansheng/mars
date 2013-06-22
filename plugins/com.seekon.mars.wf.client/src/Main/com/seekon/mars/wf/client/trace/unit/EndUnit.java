package com.seekon.mars.wf.client.trace.unit;

import javax.swing.ImageIcon;

public class EndUnit extends GraphUnit{
  public EndUnit(){
    super();
    super.setIcon(new ImageIcon(TaskUnit.class.getResource("end.jpg")));
  }
}
