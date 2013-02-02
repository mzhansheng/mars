package com.seekon.smartclient.framework.nav;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.javadocking.dock.Position;
import com.seekon.smartclient.framework.Application;
import com.seekon.smartclient.framework.internal.Activator;
import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.AbstractResourceTree;

public class NavigationItemDoubleClickedListener extends MouseAdapter {

	private AbstractResourceTree navigation;

	private Application application;

	public NavigationItemDoubleClickedListener(AbstractResourceTree navigation) {
		super();
		this.navigation = navigation;
		this.application = (Application) Activator.getBundleContext().getService(
				Activator.getBundleContext().getServiceReference(
						Application.class.getName()));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {

			TreePath path = navigation.getPathForLocation(e.getX(), e.getY());
			if (path != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				if(!node.isLeaf()){
				  return;
				}
				AbstractResource item = (AbstractResource) node.getUserObject();
				Component compo = navigation.getComponent(path);
				if (compo == null) {
					compo = new JLabel(item.getName() + "正在开发中");
				}
				application
				.getMainFrame()
				.getCurrentPerspective()
				.addComponent(item.getId(), compo, item.getName(), new Position());
			}
		}

	}
}
