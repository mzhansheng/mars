package com.seekon.system.auth.client.view;

import com.seekon.smartclient.component.viewedit.ObserverPage;
import com.seekon.smartclient.module.Resource;

public class AuthResourcePage extends ObserverPage{

  private static final long serialVersionUID = 1627138763589402939L;

  private Resource resource;

  public AuthResourcePage(Resource resource) {
    super();
    this.resource = resource;
  }

  public Resource getResource() {
    return resource;
  }
  
}
