package com.seekon.smartclient.framework.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;


public class JDescriptionableMenuItem extends JMenuItem {

	private static final long serialVersionUID = -591651693267762592L;
	/**
	 * 菜单说明信息，如：打开新文件
	 */
	private String description;

	public JDescriptionableMenuItem() {
		super();
	}

	public JDescriptionableMenuItem(Icon icon) {
		super(icon);
	}

	public JDescriptionableMenuItem(String text) {
		super(text);
	}

	public JDescriptionableMenuItem(Action a) {
		super(a);
	}

	public JDescriptionableMenuItem(String text, Icon icon) {
		super(text, icon);
	}

	public JDescriptionableMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
