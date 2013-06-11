package com.seekon.smartclient.component.tree;

import java.util.EventListener;

public interface TreeCheckListener extends EventListener {

  /**
   * 构选状态已改变
   * @param e
   */
  public void checkStateChanged(TreeCheckEvent e);
}
