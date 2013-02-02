/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seekon.mars.osgi.bridge;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.http.proxy.DispatcherTracker;

import com.seekon.mars.osgi.bridge.internal.FrameworkLauncher;

public final class BridgeServlet extends HttpServlet {

  private static final long serialVersionUID = 4653802091324863072L;

  private DispatcherTracker tracker = new DispatcherTracker();

  private Servlet jspServlet = null;

  private FrameworkLauncher framework;
  
  private static BridgeServlet instance;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    setInstance(this);

    if (System.getProperty("catalina.base") != null) {
      try {
        jspServlet = (Servlet) Thread.currentThread().getContextClassLoader()
          .loadClass("org.apache.jasper.servlet.JspServlet").newInstance();
        jspServlet.init(getServletConfig());
      } catch (Exception e) {
        throw new ServletException(e);
      }
    }

    try {
      doInit();
    } catch (ServletException e) {
      throw e;
    } catch (Exception e) {
      throw new ServletException(e);
    }

  }

  private void doInit() throws Exception {
    //		this.tracker = new DispatcherTracker(getBundleContext(), null,
    //				getServletConfig());
    //		this.tracker.open();
    
//    String enableFrameworkControlsParameter = getServletConfig().getInitParameter(
//      "enableFrameworkControls");
//    boolean enableFrameworkControls = (enableFrameworkControlsParameter != null && enableFrameworkControlsParameter
//      .equals("true"));
    try {
      String frameworkLauncherClassParameter = getServletConfig().getInitParameter(
        "frameworkLauncherClass");
      if (frameworkLauncherClassParameter != null) {
        Class frameworkLauncherClass = this.getClass().getClassLoader()
          .loadClass(frameworkLauncherClassParameter);
        framework = (FrameworkLauncher) frameworkLauncherClass.newInstance();
      } else {
        framework = new FrameworkLauncher();
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }
    framework.init(getServletConfig());
    framework.deploy();

    try {
      startOSGIFramework();
    } catch (Exception e) {
      System.out.println("OSGI框架启动失败");
      throw new ServletException(e);
    }
  }

  public void startOSGIFramework() throws Exception {
    boolean frameworkStarted = false;
    try {
      framework.start();
      frameworkStarted = true;
    } finally {
      if (!frameworkStarted)
        setInstance(null);
    }
  }
  
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

    String pathInfo = req.getPathInfo();
    if ("/".equals(pathInfo) || "/index.htm".equalsIgnoreCase(pathInfo)
      || "/index.html".equalsIgnoreCase(pathInfo)) {
      resp.sendRedirect("index.jsp");
      return;
    }

    // //String uri = req.getRequestURI();
    if (jspServlet != null && pathInfo != null && pathInfo.endsWith(".jsp")
      && getServletContext().getResource(pathInfo) != null) {
      jspServlet.service(req, resp);
      return;
    }

    HttpServlet dispatcher = this.tracker.getDispatcher();
    if (dispatcher != null) {
      dispatcher.service(req, resp);
    } else {
      resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }
  }

  @Override
  public void destroy() {
    ///this.tracker.close();
    framework.stop();
    framework.destroy();
    
    super.destroy();
  }

  //	private BundleContext getBundleContext() throws ServletException {
  //		Object context = getServletContext().getAttribute(
  //				BundleContext.class.getName());
  //		if (context instanceof BundleContext) {
  //			return (BundleContext) context;
  //		}
  //
  //		throw new ServletException("Bundle context attribute ["
  //				+ BundleContext.class.getName() + "] not set in servlet context");
  //	}

  private static synchronized void setInstance(BridgeServlet servlet) {
    if ((instance != null) && (servlet != null))
      throw new IllegalStateException("instance already set");
    instance = servlet;
  }

  public static synchronized void registerServletDelegate(HttpServlet servletDelegate) {
    if (servletDelegate == null)
      throw new NullPointerException("cannot register a null servlet delegate");

    if (instance.tracker.getDispatcher() != null)
      throw new IllegalStateException("A Servlet Proxy is already registered");

    try {
      servletDelegate.init(instance.getServletConfig());
    } catch (ServletException e) {
      instance.getServletContext().log("Error initializing servlet delegate", e);
      return;
    }
    instance.tracker.setDispatcher(servletDelegate);
  }

  public static synchronized void unregisterServletDelegate(
    HttpServlet servletDelegate) {
    if (instance == null || instance.tracker == null) {
      return;
    }

    synchronized (instance) {
      HttpServlet delegate = instance.tracker.getDispatcher();
      if (delegate == null){
        throw new IllegalStateException("No servlet delegate is registered");
      }
      
      if (delegate != servletDelegate){
        throw new IllegalStateException(
          "Servlet delegate does not match registered servlet delegate");
      }
      delegate.destroy();
    }
  }
}
