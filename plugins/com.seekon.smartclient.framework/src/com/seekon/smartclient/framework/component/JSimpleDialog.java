package com.seekon.smartclient.framework.component;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public abstract class JSimpleDialog<V> extends JBaseDialog {
	private static final long serialVersionUID = -2109976048432306784L;

	/**
	 * 关闭按钮
	 */
	protected JButton closeButton = new JButton("Close");

	/**
	 * 底部面板
	 */
	protected JPanel buttonPanel = new JPanel();

	/**
	 * 客户区面板
	 */
	protected JPanel clientPanel = new JPanel();

	/**
	 * 组件是否已初始化
	 */
	private boolean componentInited = false;

	public JSimpleDialog() {
		super();
		init();
	}

	public JSimpleDialog(Frame owner) {
		super(owner);
		init();
	}

	public JSimpleDialog(Frame owner, boolean modal) {
		super(owner, modal);
		init();
	}

	public JSimpleDialog(Frame owner, String title) {
		super(owner, title);
		init();
	}

	public JSimpleDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		init();
	}

	public JSimpleDialog(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		init();
	}

	public JSimpleDialog(Dialog owner) {
		super(owner);
	}

	public JSimpleDialog(Dialog owner, boolean modal) {
		super(owner, modal);
	}

	public JSimpleDialog(Dialog owner, String title) {
		super(owner, title);
	}

	public JSimpleDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public JSimpleDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		init();
	}

	public JSimpleDialog(Window owner) {
		super(owner);
		init();
	}

	public JSimpleDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		init();
	}

	public JSimpleDialog(Window owner, String title) {
		super(owner, title);
		init();
	}

	public JSimpleDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		init();
	}

	public JSimpleDialog(Window owner, String title, ModalityType modalityType,
			GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		init();
	}

	/**
	 * 执行初始化
	 */
	protected void init() {

		// 模式对话框
		setModal(true);

		// 不能改变大小
		setResizable(false);

		setSize(320, 240);
		Container c = getContentPane();
		// 设置布局管理器
		c.setLayout(new BorderLayout());

		// 放到底部
		c.add(buttonPanel, BorderLayout.SOUTH);

		// 占满整个客户区
		c.add(clientPanel, BorderLayout.CENTER);

		// 添加按钮到buttonPanel
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.add(closeButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 12,
						12, 12), 0, 0));

		// 为关闭按钮添加关闭代码

		Action closeAction = new AbstractAction() {

			private static final long serialVersionUID = -105376916563199771L;

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};
		closeButton.setAction(closeAction);

		// 添加按下ESC执行关闭快捷键
		closeButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), "actionClose");
		closeButton.getActionMap().put("actionClose", closeAction);
	}

	/**
	 * 初始化组件，方法由子类实现，用于添加相应的初始化代码
	 * 
	 * @param c
	 *          Container 容器，用于摆放用户创建的组件
	 */
	protected abstract void initComponent(Container c);

	/**
	 * 执行
	 * 
	 * @return boolean
	 */
	public void execute() {
		if (!componentInited) {
			componentInited = true;
			initComponent(clientPanel);
		}
		moveToScreenCenter();
		setVisible(true);
	}
}
