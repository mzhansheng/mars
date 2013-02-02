package com.seekon.smartclient.launcher.desktop.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{

  static BundleContext bundleContext;
  
  @Override
  public void start(BundleContext context) throws Exception {
    Activator.bundleContext = context;
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    Activator.bundleContext = null;
  }

}
