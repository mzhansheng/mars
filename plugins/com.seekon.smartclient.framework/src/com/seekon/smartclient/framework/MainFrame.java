package com.seekon.smartclient.framework;

import java.awt.Container;

import javax.swing.JPanel;

public interface MainFrame {

	public ToolBarManager getToolBarManager();

	public Application getApplication();

	public MenuManager getMenuManager();

	public StatusBarManager getStatusBarManager();

	public Perspective getCurrentPerspective();
	
	public JPanel getMainPane();
	
	public Container getOwner();
	
	public void setOwner(Container owner);
	
	public void init();
	
	public void update();
	
	public void destroy();

}
