/*******************************************************************************
 * Copyright (c) 2005, 2007 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *******************************************************************************/

package org.eclipse.equinox.http.servlet.internal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.seekon.mars.osgi.bridge.BridgeServlet;
import com.seekon.mars.osgi.bridge.GlobalWebContextHelper;

public class Activator implements BundleActivator {

  //private BundleContext context;

  private ServiceRegistration serviceRegistration;

  private HttpServlet proxyServlet;

  public void start(BundleContext bundleContext) throws Exception {
    //this.context = bundleContext;
    proxyServlet = new ProxyServlet(new HttpServiceController(bundleContext));
    //		Hashtable props = new Hashtable();
    //		props.put("http.felix.dispatcher", proxyServlet.getClass().getName());
    //		props.put("service.description", "Dispatcher for bridged request handling");
    //		props.put("service.vendor", "The Apache Software Foundation");
    //		serviceRegistration = bundleContext.registerService(
    //				HttpServlet.class.getName(), proxyServlet, props);
    BridgeServlet.registerServletDelegate(proxyServlet);
    serviceRegistration = bundleContext.registerService(
      ServletContext.class.getName(),
      GlobalWebContextHelper.getGlobalServletContext(), null);
    
    System.out.println("Started bridged http service");
  }

  public void stop(BundleContext bundleContext) throws Exception {
    BridgeServlet.unregisterServletDelegate(proxyServlet);
    serviceRegistration.unregister();
    //this.context = null;
  }

}
