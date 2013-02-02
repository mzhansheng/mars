package com.seekon.mars.web.proxy.internal;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.framework.ServiceReference;

import com.seekon.mars.context.http.SessionContext;

public abstract class AbstractHttpServlet extends HttpServlet{

  private static final long serialVersionUID = 1411004191781107138L;

  protected synchronized Object getExportedService(String serviceId) {
    ServiceReference sr = Activator.getContext()
        .getServiceReference(serviceId);
    return Activator.getContext().getService(sr);
  }
  
  protected String getServiceId(HttpServletRequest request, String suffix){
    String serviceId = request.getRequestURI();
    int pos = serviceId.lastIndexOf("/");
    if (pos > 0) {
      serviceId = serviceId.substring(pos + 1);
    }
    
    if (serviceId != null) {
      serviceId = serviceId.substring(0,
          serviceId.length() - suffix.length());
    }
    return serviceId;
  }
  
  protected void initApplicationContextSession(HttpServletRequest request){
    Map<String, Object> session = new HashMap<String, Object>();
    HttpSession httpSession = request.getSession();
    Enumeration<String> enumeration = httpSession.getAttributeNames();
    while(enumeration.hasMoreElements()){
      String key = enumeration.nextElement();
      session.put(key, httpSession.getAttribute(key));
    }
    
    SessionContext.setCurrentHttpSession(session);
  }
  
  protected void initHttpSession(HttpServletRequest request){
    Map<String, Object> session = SessionContext.getCurrentHttpSession();
    if(session == null){
      return;
    }
    
    HttpSession httpSession = request.getSession();
    for(String key : session.keySet()){
      httpSession.setAttribute(key, session.get(key));
    }
    
  }
}
