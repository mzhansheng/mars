package com.seekon.mars.web.proxy.internal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
public class HttpInvokeServlet extends AbstractHttpServlet{

  private static final long serialVersionUID = 2330057822219413700L;

  @Override
  protected void service(HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {
    
    String serviceId = this.getServiceId(request, ".action");
    Object service = this.getExportedService(serviceId);
  }
}
