package com.seekon.smartclient.framework;

import java.awt.Component;
import java.awt.Container;

import com.javadocking.dock.BorderDock;
import com.javadocking.dock.Position;
import com.javadocking.dockable.Dockable;

public interface Perspective {

	public void init(Container container);
	
	public void addDockable(Dockable dockable, Position position);
	
	public void addComponent(String id, Component compo, String title, Position position);
	
	public BorderDock getToolBarBorderDock();
	
	public void changeLookAndFeel(String lookAndFeelClassName) throws Exception;
}
