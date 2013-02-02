package com.seekon.smartclient.launcher.internal;

import java.awt.BorderLayout;

import javax.swing.JApplet;

import com.seekon.smartclient.framework.internal.AbstractApplication;

public class AppletApplication extends AbstractApplication {
	private JApplet applet;
	public AppletApplication(JApplet applet) {
		super();
		this.applet = applet;
	}

	@Override
	public void init() {
		mainFrame.setOwner(this.applet);
		super.init();
	}
	
	@Override
	public void doStart() {	
	}

	@Override
	public void terminate() {
		super.terminate();
	}
}
