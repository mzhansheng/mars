package com.seekon.mars.wf.client.panel;

import java.awt.Container;

import com.seekon.smartclient.gui.GuiEngine;

public class TemplateManager extends GuiEngine {

  public Container getTemplateContainer() {
    Container bdReferContainer = null;
    try {
      bdReferContainer = render(TemplateManager.class
        .getResource("TemplateManager.xml"));
      bdReferContainer.setVisible(true);
      initListeners();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bdReferContainer;
  }

  private void initListeners() {

  }
}
