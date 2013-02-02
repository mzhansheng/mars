package com.seekon.smartclient.framework.component;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;


public class JBaseDialog extends JDialog {

	public JBaseDialog() {
		super();
	}

	public JBaseDialog(Frame owner) {
		super(owner);
	}

	public JBaseDialog(Frame owner, boolean modal) {
		super(owner, modal);
	}

	public JBaseDialog(Frame owner, String title) {
		super(owner, title);
	}

	public JBaseDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public JBaseDialog(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public JBaseDialog(Dialog owner) {
		super(owner);
	}

	public JBaseDialog(Dialog owner, boolean modal) {
		super(owner, modal);
	}

	public JBaseDialog(Dialog owner, String title) {
		super(owner, title);
	}

	public JBaseDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public JBaseDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public JBaseDialog(Window owner) {
		super(owner);
	}

	public JBaseDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
	}

	public JBaseDialog(Window owner, String title) {
		super(owner, title);
	}

	public JBaseDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
	}

	public JBaseDialog(Window owner, String title, ModalityType modalityType,
			GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
	}

	public void moveToScreenCenter() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
	}

	@Override
	public void setVisible(boolean b) {
		// 更新UI
		SwingUtilities.updateComponentTreeUI(this);
		super.setVisible(b); // 调用父类方法
	}
}
