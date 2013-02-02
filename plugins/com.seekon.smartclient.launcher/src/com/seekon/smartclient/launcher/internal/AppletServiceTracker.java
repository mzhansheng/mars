package com.seekon.smartclient.launcher.internal;

import javax.swing.JApplet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

import com.seekon.smartclient.framework.internal.AbstractApplication;

public class AppletServiceTracker extends AbstractServiceTracker {

	public AppletServiceTracker(BundleContext context)
			throws InvalidSyntaxException {
		super(
				context,
				context
						.createFilter("(&(objectClass=javax.swing.JApplet)(type=com.seekon.smartclient.launcher.web.internal.JOSGiApplet))"),
				null);
	}

  @Override
  AbstractApplication getApplication(Object service) {
    return new AppletApplication((JApplet) service);
  }
}
