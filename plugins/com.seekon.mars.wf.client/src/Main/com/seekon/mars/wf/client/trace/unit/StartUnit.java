package com.seekon.mars.wf.client.trace.unit;

import javax.swing.ImageIcon;

/**
 * 开始单元
 * @author Administrator
 *
 */
public class StartUnit extends GraphUnit{
  public StartUnit(){
    super();
    super.setIcon(new ImageIcon(TaskUnit.class.getResource("start.jpg")));
  }
}
