package com.seekon.smartclient.framework.internal.menu;

import java.awt.Container;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.log4j.Logger;

import com.seekon.smartclient.framework.MenuManager;
import com.seekon.smartclient.framework.internal.MainFrameImpl;
import com.seekon.smartclient.framework.util.OrderableMap;

public class MenuManagerImpl implements MenuManager {

	private MainFrameImpl mainFrame;

	private Logger log = Logger.getLogger(MenuManagerImpl.class);

	private MenuRegistry menuRegistry;

	public MenuManagerImpl(MainFrameImpl mainFrame) {
		this.mainFrame = mainFrame;
	}

	public OrderableMap<String, JMenu> getMenuMap() {
		return menuRegistry.getMenuMap();
	}

	/**
	 * 更新主菜单
	 */
	public void update() {
	  menuRegistry = MenuRegistry.getInstance(true);
		JMenuBar menuBar = new JMenuBar();
		for (JMenu menu : menuRegistry.getMenuMap().values()) {
			log.debug("Add Menu " + menu.getText() + " to MenuBar");
			menuBar.add(menu);
		}
		
		Container owner = mainFrame.getOwner();
		if (owner != null) {
			if (owner instanceof JFrame) {
				((JFrame) owner).setJMenuBar(menuBar);
			} else if (owner instanceof JApplet) {
				((JApplet) owner).setJMenuBar(menuBar);
			}
		}

	}
}
