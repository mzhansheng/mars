package com.seekon.mars.wf.client.panel;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.seekon.smartclient.gui.GuiEngine;

public class TemplateManager extends GuiEngine {

  private JButton add;
  
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
    add.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent arg0) {
        TemplateEditPage page = new TemplateEditPage();
        Container container = page.getTemplateEditContainer(null);
      }
    });
  }
}
