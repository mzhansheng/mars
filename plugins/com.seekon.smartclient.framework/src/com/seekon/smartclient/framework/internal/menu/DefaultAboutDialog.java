package com.seekon.smartclient.framework.internal.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import com.seekon.smartclient.framework.component.JSimpleDialog;
import com.seekon.smartclient.framework.util.ResourceProvide;

final class DefaultAboutDialog extends JSimpleDialog {

	DefaultAboutDialog(Window owner){
		super(owner);
	}
	
	DefaultAboutDialog(JFrame owner) {
		super(owner);
	}

	@Override
	protected void initComponent(Container container) {
		// Create and init Components
		JLabel logoLabel = new JLabel(
				//ResourceProvide.getIcon("/resources/aboutdialog/logo.png")
				);

		JLabel version = new JLabel();

		JLabel copyrightLabel = new JLabel();

		JSeparator separator = new JSeparator();

		JLabel pluginsLabel = new JLabel(
				ResourceProvide.getResourceString("AboutDialog.pluginlabeltitle"));

		setTitle(ResourceProvide.getResourceString("AboutDialog.title"));
		closeButton.setText(ResourceProvide.getResourceString("Close"));
		version.setText(String.format("%s%s",
				ResourceProvide.getResourceString("Version"),
				ResourceProvide.getResourceString("Colon")));
		version.setBounds(new Rectangle(40, 0, 325, 15));
		copyrightLabel.setText("<html><u><font color='blue' name='宋体'>"
				+ ResourceProvide.getResourceString("AboutDialog.copyright")
				+ "</font></u></html>");
		copyrightLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("http://www.seekon.com/"));
				} catch (URISyntaxException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		pluginsLabel.setForeground(Color.BLUE);

		copyrightLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		copyrightLabel.setBounds(new Rectangle(41, 33, 335, 15));
		separator.setBorder(BorderFactory.createEtchedBorder());
		separator.setBounds(new Rectangle(16, 96, 334, 3));

		pluginsLabel.setBounds(new Rectangle(0, 0, 334, 3));
		pluginsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pluginsLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					PluginManagerDialog dlg = new PluginManagerDialog(null);
					dlg.execute();

				}
				super.mouseClicked(e);
			}
		});

		// Add Components to the Constrain
		container.setLayout(new GridBagLayout());

		container.add(logoLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 12, 12,
						12), 0, 0));
		container.add(version, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 12,
						12), 0, 0));

		container.add(copyrightLabel, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 12,
						12), 0, 0));
		container.add(pluginsLabel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(8, 12, 12,
						12), 0, 0));

		separator.setOrientation(JSeparator.HORIZONTAL);

		container.add(separator, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5,
						5), 0, 0));
		this.getContentPane().add(logoLabel, BorderLayout.NORTH);
		setSize(360, 300);
	}

}
