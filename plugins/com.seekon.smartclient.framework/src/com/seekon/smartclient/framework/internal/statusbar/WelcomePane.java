package com.seekon.smartclient.framework.internal.statusbar;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePane extends JPanel{

	private static final long serialVersionUID = -4047513752183351733L;

	public WelcomePane() {
		super();
		init();
	}

	protected void init(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(new JLabel("欢迎使用..."));
	}
}
