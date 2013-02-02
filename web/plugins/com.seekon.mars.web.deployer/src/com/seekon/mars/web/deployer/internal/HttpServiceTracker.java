package com.seekon.mars.web.deployer.internal;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class HttpServiceTracker extends ServiceTracker {

  private BundleContext context = null;

  private ExtendedHttpService httpService = null;

  public HttpServiceTracker(BundleContext context) throws Exception {
    super(context, context
      .createFilter("(objectClass=org.osgi.service.http.HttpService)"), null);
    this.context = context;
  }

  @Override
  public Object addingService(ServiceReference reference) {
    httpService = (ExtendedHttpService) this.context.getService(reference);
    System.out.println("httpService:" + httpService);
    return httpService;
  }

  @Override
  public void modifiedService(ServiceReference reference, Object service) {
    super.modifiedService(reference, service);
  }

  @Override
  public void removedService(ServiceReference arg0, Object arg1) {

  }

  public ExtendedHttpService getHttpService() {
    return httpService;
  }

//  /**
//   * 当httpService可用的时候启动所有web插件
//   */
//  private void startWebBundles() {
//    Bundle[] bundles = context.getBundles();
//    for (Bundle bundle : bundles) {
//      if (bundle.getState() == Bundle.RESOLVED
//        && bundle.getHeaders().get("Context-Path") != null) {
//        try {
//          bundle.start();
//        } catch (BundleException e) {
//          e.printStackTrace();
//        }
//      }
//    }
//  }
}
