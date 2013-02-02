package com.seekon.smartclient.launcher.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

import com.seekon.smartclient.framework.internal.AbstractApplication;

public class WebstarServiceTracker extends AbstractServiceTracker {

	public WebstarServiceTracker(BundleContext context)
			throws InvalidSyntaxException {
		super(
				context,
				context
						.createFilter("(objectClass=com.seekon.smartclient.launcher.web.internal.WebstarMain)"),
				null);
	}

  @Override
  AbstractApplication getApplication(Object service) {
    return new WebStartApplication();
  }
}
