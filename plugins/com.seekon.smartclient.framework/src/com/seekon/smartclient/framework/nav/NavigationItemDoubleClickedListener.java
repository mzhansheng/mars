package com.seekon.smartclient.framework.nav;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.javadocking.dock.Position;
import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
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
				application
				.getMainFrame()
				.getCurrentPerspective()
				.addComponent(item.getId(), new JPanelWrapper(path), item.getName(), new Position());
			}
		}

	}
	
	class JPanelWrapper extends JPanel{
    private static final long serialVersionUID = -2986321563725084028L;

    public JPanelWrapper(final TreePath path) {
      super();
      this.setLayout(new BorderLayout());
      
      UIUtilities.asyncInvoke( new DefaultInvokeHandler<Component>() {

        @Override
        public Component execute() throws Exception {
          return navigation.getComponent(path);
        }

        @Override
        public void success(Component result) {
          if (result == null) {
            result = new JLabel("组件正在开发中");
          }
          JPanelWrapper.this.add(result);
        }
      });
    }
	  
	}
}
