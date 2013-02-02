package com.seekon.mars.web.proxy.internal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;
import com.seekon.mars.web.proxy.util.WebParameterUtils;

@Singleton
public class GetSystemBundleInfoServlet extends HttpServlet {

  private static final long serialVersionUID = -6904443517029034031L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    this.doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    String systemBundles = WebParameterUtils.getSystemBundleNames(false, req
      .getSession().getServletContext());
    resp.getWriter().write(systemBundles);
    resp.getWriter().flush();
  }
}
