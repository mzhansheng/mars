package com.seekon.mars.wf.client.panel;

import java.awt.Container;
import java.util.Map;

import com.seekon.smartclient.gui.GuiEngine;

public class TemplateEditPage extends GuiEngine{

  private Map data = null;
  
  public Container getTemplateEditContainer(Map data) {
    this.data = data;
    Container bdReferContainer = null;
    try {
      bdReferContainer = render(TemplateManager.class
        .getResource("TemplateEditPage.xml"));
      bdReferContainer.setVisible(true);
      initListeners();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bdReferContainer;
  }
  
  private void initListeners(){
    
  }
}
