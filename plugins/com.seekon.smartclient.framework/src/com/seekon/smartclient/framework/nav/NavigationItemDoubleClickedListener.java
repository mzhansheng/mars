package com.seekon.smartclient.framework.nav;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

  private Map<String, Window> openedWindows = new HashMap<String, Window>();
  
  public NavigationItemDoubleClickedListener(AbstractResourceTree navigation) {
    super();
    this.navigation = navigation;
    this.application = (Application) Activator.getBundleContext().getService(
      Activator.getBundleContext().getServiceReference(Application.class.getName()));
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {

      final TreePath path = navigation.getPathForLocation(e.getX(), e.getY());
      if (path != null) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
          .getLastPathComponent();
        if (!node.isLeaf()) {
          return;
        }
        final AbstractResource item = (AbstractResource) node.getUserObject();
        if (item.isOpenNewWindow()) {
          SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
              Window window = (Window) openedWindows.get(item.getId());
              if(window != null){
                window.setVisible(true);
                //window.setAlwaysOnTop(true);
                window.toFront();
                return;
              }
              
              window = new JFrame(item.getName());
              window.setLayout(new BorderLayout());              
              window.add(new JPanelWrapper(path), BorderLayout.CENTER);
              
              Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
              window.setSize(d.width, d.height - 25);
              window.setLocationRelativeTo(null);
              
              window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                  super.windowOpened(e);
                  NavigationItemDoubleClickedListener.this.openedWindows.put(item.getId(), e.getWindow());
                }
                
                @Override
                public void windowClosing(WindowEvent e) {
                  super.windowClosing(e);
                  NavigationItemDoubleClickedListener.this.openedWindows.remove(e.getWindow());
                }
              });
              
              window.setVisible(true);
            }
          });
        } else {
          application
            .getMainFrame()
            .getCurrentPerspective()
            .addComponent(item.getId(), new JPanelWrapper(path), item.getName(),
              new Position());
        }
      }
    }

  }

  class JPanelWrapper extends JPanel {
    private static final long serialVersionUID = -2986321563725084028L;

    public JPanelWrapper(final TreePath path) {
      super();
      this.setLayout(new BorderLayout());
      Component compo = navigation.getComponent(path);
      if(compo == null){
        compo = new JLabel("组件开发中");
      }
      this.add(compo);
    }

  }
}
