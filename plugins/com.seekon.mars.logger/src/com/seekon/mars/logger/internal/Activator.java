package com.seekon.mars.logger.internal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.seekon.mars.logger.log4j.BundleLogManager;

public class Activator implements BundleActivator, BundleListener {

  private List<Bundle> checkedBundleList = new ArrayList<Bundle>();

  public void start(BundleContext bundleContext) throws Exception {
    bundleContext.addBundleListener(this);
    Bundle[] bundles = bundleContext.getBundles();
    for (Bundle bundle : bundles) {
      int state = bundle.getState();
      if (!checkedBundleList.contains(bundle)
        && (state == Bundle.RESOLVED || state == Bundle.STARTING || state == Bundle.ACTIVE)) {
        BundleLogManager.loadBundleLog4jConfigure(bundle);
        checkedBundleList.add(bundle);
      }
    }
  }

  public void stop(BundleContext bundleContext) throws Exception {
    bundleContext.removeBundleListener(this);
  }

  @Override
  public void bundleChanged(BundleEvent event) {
    Bundle bundle = event.getBundle();
    switch (event.getType()) {
    case BundleEvent.RESOLVED: {
      if (!checkedBundleList.contains(bundle)) {
        BundleLogManager.loadBundleLog4jConfigure(bundle);
        checkedBundleList.add(bundle);
      }
      break;
    }
    case BundleEvent.UNRESOLVED: {
      if (checkedBundleList.contains(bundle)) {
        checkedBundleList.remove(bundle);
      }
    }
    }
  }

}
