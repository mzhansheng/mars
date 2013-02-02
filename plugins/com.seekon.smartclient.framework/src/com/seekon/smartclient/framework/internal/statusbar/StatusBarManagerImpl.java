package com.seekon.smartclient.framework.internal.statusbar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Set;

import com.seekon.smartclient.framework.StatusBarManager;
import com.seekon.smartclient.framework.component.JStatusBar;
import com.seekon.smartclient.framework.component.JStatusBarPanel;
import com.seekon.smartclient.framework.internal.MainFrameImpl;

public class StatusBarManagerImpl implements StatusBarManager {

	private JStatusBar statusBar;

	private StatusbarPaneRegistry statusbarPaneRegistry;
	
	private MainFrameImpl mainFrame;
	
	public StatusBarManagerImpl(MainFrameImpl mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	public void update(){
	  this.statusbarPaneRegistry = StatusbarPaneRegistry.getInstance(true);
	  this.statusBar = new JStatusBar();
		Set<Component> statusbarPanes = statusbarPaneRegistry.getStatusbarPanes();
		for(Component compo: statusbarPanes){
			this.addPanel(compo);
		}
		this.mainFrame.getMainPane().add(statusBar, BorderLayout.SOUTH);
	}
	
	private JStatusBarPanel insertPanel(Component comp, int index) {
		JStatusBarPanel panel = new JStatusBarPanel();
		panel.getContentPane().setLayout(new BorderLayout());
		panel.getContentPane().add(BorderLayout.CENTER, comp);
		statusBar.insertPanel(index, panel);
		return panel;
	}

	private JStatusBarPanel addPanel(Component comp) {
		return insertPanel(comp, statusBar.getPanelCount());
	}
}
