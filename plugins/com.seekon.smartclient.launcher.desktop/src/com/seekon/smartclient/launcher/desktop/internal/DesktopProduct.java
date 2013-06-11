package com.seekon.smartclient.launcher.desktop.internal;

import java.util.Properties;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.ServiceRegistration;

import com.seekon.smartclient.launcher.web.WebLauncher;

public class DesktopProduct implements IApplication {

  ServiceRegistration serviceRegistration = null;
  WebLauncher launcher;
  
  @Override
  public Object start(IApplicationContext applicationContext) throws Exception {
    Properties serviceProperties = new Properties();
    serviceProperties.put("type", DesktopProduct.class.getName());
    serviceRegistration = Activator.bundleContext.registerService(
      IApplication.class.getName(), this, serviceProperties);

    launcher = new DesktopLauncher();
    launcher.initOSGiFramework();
    
    return IApplication.EXIT_OK;
  }

  @Override
  public void stop() {
    launcher.shutdown();
    serviceRegistration.unregister();
  }
}
