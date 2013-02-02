package com.seekon.mars.web.proxy.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.servlet.ServletModule;

public class Activator implements BundleActivator {

  private static BundleContext context;

  static BundleContext getContext() {
    return context;
  }

  /*
   * (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext bundleContext) throws Exception {
    Activator.context = bundleContext;
    Guice.createInjector(new ServletModule() {
      @Override
      protected void configureServlets() {
        serve("*.hessian").with(HessianInvokeServlet.class);
        serve("*.action").with(HttpInvokeServlet.class);
        serve("/proxy/webstarMainServlet").with(WebstarMainServlet.class);
        serve("/proxy/getSystemBundleInfoServlet").with(
          GetSystemBundleInfoServlet.class);
      }
    });

  }

  /*
   * (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext bundleContext) throws Exception {
    Activator.context = null;
  }

}
