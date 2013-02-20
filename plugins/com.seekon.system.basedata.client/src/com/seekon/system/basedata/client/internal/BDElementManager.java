package com.seekon.system.basedata.client.internal;

import java.awt.Container;

import com.seekon.smartclient.gui.GuiEngine;

public class BDElementManager extends GuiEngine{
 
  private Container bdElementContainer = null;

  public Container getbdElementContainer() {
    initComponents();
    bdElementContainer.setVisible(true);
    return bdElementContainer;
  }
  
  private void initComponents(){
    try {
      bdElementContainer = render(BDGeneralManager.class
        .getResource("BDElementPage.xml"));
      initListeners();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void initListeners(){
    
  }
}
