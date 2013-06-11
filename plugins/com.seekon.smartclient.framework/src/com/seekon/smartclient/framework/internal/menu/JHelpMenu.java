package com.seekon.smartclient.framework.internal.menu;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import com.seekon.smartclient.framework.component.JDescriptionableMenuItem;
import com.seekon.smartclient.framework.util.ResourceProvide;

public class JHelpMenu extends JMenu {

	private static final long serialVersionUID = 9006825205103789288L;

	public static String HELP_MENU_KEY = "help";

	public JHelpMenu() {
		super();

		this.setText(ResourceProvide.getResourceString(HELP_MENU_KEY
				+ "menu.text"));
		this.setMnemonic(ResourceProvide.getMnemonic(HELP_MENU_KEY
				+ "menu.mnemonic"));
		createHomePageMenuItem();
		createFeedbackMenuItem();
		createAboutMenuItem();
	}

	/**
	 * 帮助->关于菜单
	 */

	private void createAboutMenuItem() {
		final JDescriptionableMenuItem item = new JDescriptionableMenuItem(
				ResourceProvide.getResourceString("help_aboutmenuitem.text"));
		item.setMnemonic(ResourceProvide
				.getMnemonic("help_aboutmenuitem.mnemonic"));
		item.setDescription(ResourceProvide
				.getResourceString("help_aboutmenuitem.description"));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DefaultAboutDialog(SwingUtilities.getWindowAncestor(item)).execute();
			}
		});
		this.add(item);
	}

	/**
	 * 帮助->用友政务主页
	 */
	private void createHomePageMenuItem() {
		JDescriptionableMenuItem item = new JDescriptionableMenuItem(
				ResourceProvide
						.getResourceString("helpmenu_homepagemenuitem.text"));
		item.setMnemonic(ResourceProvide
				.getMnemonic("helpmenu_homepagemenuitem.mnemonic"));
		item.setDescription(ResourceProvide
				.getResourceString("helpmenu_homepagemenuitem.description"));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(
							new URI("http://www.seekon.com/"));
				} catch (URISyntaxException ex) {
					// application.processException(ex);
				} catch (IOException ex) {
					// application.processException(ex);
				}
			}
		});
		this.add(item);
	}

	/**
	 * 帮助->意见反馈
	 */
	private void createFeedbackMenuItem() {
		JDescriptionableMenuItem item = new JDescriptionableMenuItem(
				ResourceProvide
						.getResourceString("helpmenu_feedbackmenuitem.text"));
		item.setMnemonic(ResourceProvide
				.getMnemonic("filemenu_feedbackmenuitem.mnemonic"));
		item.setDescription(ResourceProvide
				.getResourceString("filemenu_feedbackmenuitem.description"));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().mail(
							new URI(
									"mailto:udnyliu@126.com?subject=A6框架意见反馈("
											+ new SimpleDateFormat(
													"yyyy年MM月dd日HH点mm分")
													.format(new Date()) + ")"));
				} catch (URISyntaxException ex) {
					// application.processException(ex);
				} catch (IOException ex) {
					// application.processException(ex);
				}
			}
		});

		this.add(item);
	}
}
