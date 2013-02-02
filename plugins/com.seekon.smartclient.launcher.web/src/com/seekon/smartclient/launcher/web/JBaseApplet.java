package com.seekon.smartclient.launcher.web;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public abstract class JBaseApplet extends JApplet{
	
	private static final long serialVersionUID = 1572853657607815569L;

	static {
    System.setSecurityManager(new AllPermissionSecurityManager());
  }

  protected abstract void createComponent(Container c);
  
  @Override
  public void init() {
    super.init();

    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        SwingUtilities.updateComponentTreeUI(JBaseApplet.this);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        createComponent(contentPane);
      }
    });
  }

  @Override
  public void destroy() {
    super.destroy();
  }
}
