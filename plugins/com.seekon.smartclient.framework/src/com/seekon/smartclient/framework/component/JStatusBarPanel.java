package com.seekon.smartclient.framework.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;


public class JStatusBarPanel extends JPanel {

	private static final long serialVersionUID = -909178403559782255L;

	private static Border innerPanelBorder = BorderFactory.createBevelBorder(
			BevelBorder.LOWERED, Color.white, Color.white, new Color(114, 114, 115),
			new Color(164, 163, 165));

	private JLabel label = new JLabel();
	private JPanel client = new JPanel();
	private JLabel imageIcon = new JLabel();

	/**
	 * 返回内容面板，可以在该面板上存置其他组件
	 * 
	 * @return Container
	 */
	public Container getContentPane() {
		return client;
	}

	/**
	 * 设置是否显示text
	 * 
	 * @param b
	 *          boolean
	 */
	public void setShowText(boolean b) {
		label.setVisible(b);
	}

	/**
	 * 设置要显示的text，注意，在setShowText(false)时该方法设置的文本将不可见
	 * 
	 * @param text
	 *          String
	 */
	public void setText(String text) {
		label.setText(text);
		revalidate();
	}

	public String getText() {
		return label.getText();
	}

	public JStatusBarPanel() {
		imageIcon.setIcon(new ResizeWindowIcon());
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		this.setLayout(new GridBagLayout());
		setBorder(innerPanelBorder);
		add(client, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,
						0, 0, 0), 0, 0));
		client.setLayout(new GridBagLayout());
		client.add(label, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,
						5, 0, 5), 0, 0));
	}

	/**
	 * @param text
	 *          String - 显示标题
	 */
	public JStatusBarPanel(String text) {
		this();
		setText(text);
	}

	/**
	 * 设置是否显示resize图标
	 * 
	 * @param visible
	 *          boolean
	 */
	void setShowResizeWindowIcon(boolean visible) {
		imageIcon.setVisible(visible);
		if (visible) {
			add(imageIcon, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0,
							0), 0, 0));

		} else {
			remove(imageIcon);
		}
		revalidate();
	}


	private static class ResizeWindowIcon implements Icon {
		private static final Color WHITE_LINE_COLOR = new Color(255, 255, 255);

		private static final Color GRAY_LINE_COLOR = new Color(172, 168, 153);
		private static final int WIDTH = 13;

		private static final int HEIGHT = 13;

		public int getIconHeight() {
			return WIDTH;
		}

		public int getIconWidth() {
			return HEIGHT;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(WHITE_LINE_COLOR);
			g.drawLine(0, 12, 12, 0);
			g.drawLine(5, 12, 12, 5);
			g.drawLine(10, 12, 12, 10);

			g.setColor(GRAY_LINE_COLOR);
			g.drawLine(1, 12, 12, 1);
			g.drawLine(2, 12, 12, 2);
			g.drawLine(3, 12, 12, 3);

			g.drawLine(6, 12, 12, 6);
			g.drawLine(7, 12, 12, 7);
			g.drawLine(8, 12, 12, 8);

			g.drawLine(11, 12, 12, 11);
			g.drawLine(12, 12, 12, 12);
		}
	}

}
