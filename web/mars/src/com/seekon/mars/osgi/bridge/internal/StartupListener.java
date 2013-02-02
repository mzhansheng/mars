package com.seekon.mars.osgi.bridge.internal;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.seekon.mars.osgi.bridge.GlobalWebContextHelper;

public final class StartupListener implements ServletContextListener {
	//private FrameworkService service;

	public void contextInitialized(ServletContextEvent event) {
		GlobalWebContextHelper.setGlobalServletContext(event.getServletContext());
		System.out.println("StartupListener is Initializing.");
		//this.service = new FrameworkService(event.getServletContext());
		//this.service.start();
	}

	public void contextDestroyed(ServletContextEvent event) {
		//this.service.stop();
	}
}
