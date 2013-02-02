package com.seekon.smartclient.launcher.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

import com.seekon.smartclient.framework.internal.AbstractApplication;

public class DesktopServiceTracker extends AbstractServiceTracker {

  public DesktopServiceTracker(BundleContext context) throws InvalidSyntaxException {
    super(context, context
      .createFilter("(&(objectClass=org.eclipse.equinox.app.IApplication)"
        + "(type=com.seekon.smartclient.launcher.desktop.internal.DesktopProduct))"),
      null);
  }

  @Override
  AbstractApplication getApplication(Object service) {
    return new DesktopAppliction();
  }

}
