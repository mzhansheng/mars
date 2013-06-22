package com.seekon.mars.wf.client.trace.unit;

import javax.swing.ImageIcon;

/**
 * 任务单元
 * @author Administrator
 *
 */
public class TaskUnit extends GraphUnit{
  public TaskUnit(){
    super();
    super.setIcon(new ImageIcon(TaskUnit.class.getResource("userTask.jpg")));
  }
  
  public void setCommitTaskIcon(){
    super.setIcon(new ImageIcon(TaskUnit.class.getResource("commitTask.jpg")));
  }
  
  public void setUntreadTaskIcon(){
    super.setIcon(new ImageIcon(TaskUnit.class.getResource("untreadTask.jpg")));
  }
  
  public void setCallbackIcon(){
    super.setIcon(new ImageIcon(TaskUnit.class.getResource("callbackTask.jpg")));
  }
}
