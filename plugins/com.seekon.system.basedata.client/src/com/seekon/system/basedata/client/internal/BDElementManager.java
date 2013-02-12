package com.seekon.system.basedata.client.internal;

import java.awt.Container;

import com.seekon.smartclient.gui.GuiEngine;

public class BDElementManager extends GuiEngine{

  static BDElementManager bdElementManager = new BDElementManager();
  
  private Container bdElementContainer = null;
  
  private BDElementManager() {
    super();
    try {
      bdElementContainer = render(BDGeneralManager.class
        .getResource("BDElementPage.xml"));
      initListeners();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Container getbdElementContainer() {
    bdElementContainer.setVisible(true);
    return bdElementContainer;
  }
  
  public void initListeners(){
    
  }
}
