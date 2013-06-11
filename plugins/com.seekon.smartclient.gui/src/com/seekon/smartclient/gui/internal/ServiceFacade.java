package com.seekon.smartclient.gui.internal;

import com.seekon.mars.gui.service.ModelService;

public class ServiceFacade {

  private ServiceFacade() {
  }

  public static ModelService getModelService() {
    return GuiPlugin.injector.getInstance(ModelService.class);
  }
}
