package com.seekon.mars.web.proxy.internal;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;
import com.seekon.mars.web.proxy.util.WebParameterUtils;

@Singleton
public class WebstarMainServlet extends HttpServlet {

	private static final long serialVersionUID = -4110608541282735626L;

	private static final String CONTENT_TYPE = "application/x-java-jnlp-file";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		
		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				response.getOutputStream(), "UTF-8"));

		boolean packEnabled = false;
		ServletContext sc = request.getSession().getServletContext();
		String webRoot = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + "/mars/client/plugins/";

		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.write("<jnlp spec=\"1.0+\" codebase=\"" + webRoot + "\">");
		out.write("  <information>");
		out.write("    <title>smartclient</title>");
		out.write("    <vendor>smartclient</vendor>");
    out.write("    <description>smartclient</description>");
		out.write("    <offline-allowed />");
		out.write("  </information>");
		out.write("  <security>");
		out.write("  <all-permissions/>");
		out.write("  </security>");
		out.write("  <resources>");
		out.write("    <j2se version=\"1.6+\" href=\"http://java.sun.com/products/autodl/j2se\"/> ");
		out.write("    <jar href=\"org.eclipse.osgi_3.6.2.R36x_v20110210.jar\" />");
		out.write("    <jar href=\"com.seekon.mars.equinox.extend_6.0.2.jar\" />");
		out.write("    <jar href=\"com.seekon.smartclient.launcher.web_1.0.0.jar\" />");
		out.write("    <jar href=\"com.seekon.smartclient.loader_1.0.0.jar\" />");
		out.write("  </resources>");
		out.write("  <application-desc main-class=\"com.seekon.smartclient.launcher.web.internal.WebstarMain\">");
		out.write("    <argument>" + webRoot + "</argument>");
		out.write("    <argument>" + WebParameterUtils.getSystemBundleNames(packEnabled, sc) + "</argument>");
		out.write("    <argument>" + request.getSession().getId() + "</argument>");
		out.write("  </application-desc>");
		out.write("</jnlp>");

		out.flush();
		out.close();
	
	}

}
