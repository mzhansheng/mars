package com.seekon.smartclient.framework;

import javax.swing.JMenu;

import com.seekon.smartclient.framework.util.OrderableMap;

public interface MenuManager {

	public OrderableMap<String, JMenu> getMenuMap();
	
	public void update();
}
