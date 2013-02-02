package com.seekon.smartclient.launcher.internal;

import javax.swing.SwingUtilities;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.seekon.smartclient.framework.Application;
import com.seekon.smartclient.framework.internal.AbstractApplication;
import com.seekon.smartclient.launcher.Startup;

public class DesktopProduct implements IApplication {

	private AbstractApplication application;

	@Override
	public Object start(IApplicationContext applicationContext) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {		
			@Override
			public void run() {
				StartupRegistry startupRegistry = new StartupRegistry();
				for (Startup startup : startupRegistry.getStartup()) {
					startup.run(Activator.getContext());
				}

				application = new DesktopAppliction();
				application.setBundleContext(Activator.getContext());
				Activator.getContext().registerService(Application.class.getName(), application,
						null);

				application.init();
				application.run();

			}
		});
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		if (application != null) {
			application.terminate();
			application = null;
		}
	}
}
