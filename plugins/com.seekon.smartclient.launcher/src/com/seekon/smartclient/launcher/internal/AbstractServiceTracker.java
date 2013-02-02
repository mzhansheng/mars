package com.seekon.smartclient.launcher.internal;

import javax.swing.SwingUtilities;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.seekon.smartclient.framework.Application;
import com.seekon.smartclient.framework.internal.AbstractApplication;
import com.seekon.smartclient.launcher.Startup;

public abstract class AbstractServiceTracker extends ServiceTracker{

  AbstractApplication application = null;
  
  BundleContext context = null;
  
  public AbstractServiceTracker(BundleContext context, Filter filter,
    ServiceTrackerCustomizer customizer) {
    super(context, filter, customizer);
    this.context = context;
  }
  
  @Override
  public Object addingService(ServiceReference reference) {
    final Object service = context.getService(reference);
    if (application == null) {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          StartupRegistry startupRegistry = new StartupRegistry();
          for (Startup startup : startupRegistry.getStartup()) {
            startup.run(context);
          }

          application = getApplication(service);
          application.setBundleContext(context);
          context.registerService(Application.class.getName(), application,
              null);

          application.init();
          application.run();
          
        }
      });
    }
    return service;
  }
  
  @Override
  public void removedService(ServiceReference reference, Object service) {
    super.removedService(reference, service);
    if(application != null){
      application.destroy();
      application = null;
    }
  }
  
  abstract AbstractApplication getApplication(Object service);
}
