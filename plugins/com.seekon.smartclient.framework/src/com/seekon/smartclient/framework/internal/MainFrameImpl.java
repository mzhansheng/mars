package com.seekon.smartclient.framework.internal;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.javadocking.dock.Position;
import com.seekon.smartclient.framework.Application;
import com.seekon.smartclient.framework.MainFrame;
import com.seekon.smartclient.framework.MenuManager;
import com.seekon.smartclient.framework.Perspective;
import com.seekon.smartclient.framework.StatusBarManager;
import com.seekon.smartclient.framework.ToolBarManager;
import com.seekon.smartclient.framework.internal.menu.MenuManagerImpl;
import com.seekon.smartclient.framework.internal.statusbar.StatusBarManagerImpl;
import com.seekon.smartclient.framework.internal.toolbar.ToolBarManagerImpl;
import com.seekon.smartclient.framework.nav.NavigationView;

public class MainFrameImpl implements MainFrame {

  AbstractApplication application = null;

  protected Container owner = null;

  private MenuManagerImpl menuManager = null;

  private ToolBarManagerImpl toolBarManager = null;

  private StatusBarManagerImpl statusBarManager = null;

  private PerspectiveRegistry perspectiveRegistry = null;

  protected Perspective perspective;

  private JPanel mainPane;

  MainFrameImpl(AbstractApplication application) {
    super();
    this.application = application;
    application.mainFrame = this;
    this.menuManager = new MenuManagerImpl(this);
    this.toolBarManager = new ToolBarManagerImpl(this);
    this.statusBarManager = new StatusBarManagerImpl(this);
    this.perspectiveRegistry = new PerspectiveRegistry();
    this.mainPane = new JPanel(new BorderLayout());
  }

  @Override
  public ToolBarManager getToolBarManager() {
    return this.toolBarManager;
  }

  @Override
  public Application getApplication() {
    return this.application;
  }

  @Override
  public MenuManager getMenuManager() {
    return this.menuManager;
  }

  @Override
  public StatusBarManager getStatusBarManager() {
    return this.statusBarManager;
  }

  @Override
  public Perspective getCurrentPerspective() {
    return this.perspective;
  }

  @Override
  public JPanel getMainPane() {
    return this.mainPane;
  }

  @Override
  public Container getOwner() {
    return this.owner;
  }

  public void setOwner(Container owner) {
    this.owner = owner;
  }

  @Override
  public void init() {
    this.perspective = perspectiveRegistry.getDefaultPerspective();
    if (this.perspective == null) {
      throw new RuntimeException(" perspective is null.");
    }
    if (owner instanceof JFrame) {
      ((JFrame) owner).getContentPane().setLayout(new BorderLayout());
      ((JFrame) owner).getContentPane().add(mainPane, BorderLayout.CENTER);
    } else if (owner instanceof JApplet) {
      ((JApplet) owner).getContentPane().setLayout(new BorderLayout());
      ((JApplet) owner).getContentPane().add(mainPane, BorderLayout.CENTER);
    }
  }

  @Override
  public void update() {
    this.mainPane.removeAll();

    this.perspective.init(this.owner);
    this.perspective.addComponent("NavigationView", new NavigationView(), "导航菜单",
      new Position());
    this.perspective.addComponent("homepage", new JLabel("this is the homepage."), "首页",
      new Position());

    this.menuManager.update();
    this.toolBarManager.update();
    this.statusBarManager.update();

    this.mainPane.revalidate();
  }

  @Override
  public void destroy() {

  }

}