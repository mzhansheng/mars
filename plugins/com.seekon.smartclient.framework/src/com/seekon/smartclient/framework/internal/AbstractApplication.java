package com.seekon.smartclient.framework.internal;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleContext;

import com.javadocking.util.LookAndFeelUtil;
import com.seekon.smartclient.framework.Application;
import com.seekon.smartclient.framework.MainFrame;
import com.seekon.smartclient.framework.UIStartup;
import com.seekon.smartclient.framework.util.ResourceProvide;
import com.seekon.smartclient.splash.Splash;
import com.seekon.smartclient.splash.SplashPlugin;

public abstract class AbstractApplication implements Application {

  protected Logger log = Logger.getLogger(AbstractApplication.class);

  protected String applicationName = "smartclient";

  protected BundleContext bundleContext;

  protected MainFrameImpl mainFrame;

  protected String title = null;

  private String currentLookAndFeelClassName = UIManager
    .getCrossPlatformLookAndFeelClassName();

  public AbstractApplication() {
    LookAndFeelUtil.removeAllSplitPaneBorders();
    
    setTitle(ResourceProvide.getResourceString("application.title"));
    log.debug("正在创建主窗口");
    mainFrame = new MainFrameImpl(this);
  }

  public String getApplicationName() {
    return applicationName;
  }

  public String getVersion() {
    if (bundleContext != null) {
      if (bundleContext.getBundle() != null) {
        return (String) bundleContext.getBundle().getHeaders().get("Bundle-Version");
      }
    }
    return null;
  }

  public MainFrame getMainFrame() {
    return mainFrame;
  }

  public void setTitle(String title) {
    this.title = title;
    if (mainFrame != null && mainFrame.owner != null
      && (mainFrame.owner instanceof JFrame)) {
      ((JFrame) mainFrame.owner).setTitle(title);
    }
  }

  public String getTitle() {
    return title;
  }

  public boolean isOnLine() {
    String pluginsdirURL = System.getProperty("pluginsdir.url");
    if (pluginsdirURL == null)
      return false;
    try {
      URL url = new URL(pluginsdirURL);
      try {
        url.openConnection().connect();
        return true;
      } catch (IOException ex1) {
        return false;
      }
    } catch (MalformedURLException ex) {
      return false;
    }
  }

  public void init() {   
    Splash splash = com.seekon.smartclient.splash.SplashPlugin.getDefault()
    		.getSplash();

    splash.displayMessage("系统启动...");

    splash.displayMessage("正在初始化主窗口...");
    mainFrame.init();
    splash.displayMessage("初始化完毕");
  }

  public void destroy() {
    mainFrame.destroy();
  }

  public void terminate() {
    destroy();
    System.exit(0);
  }

  public void changeLookAndFeel(String lookAndFeelClassName) throws Exception {
    if (lookAndFeelClassName == null)
      throw new IllegalArgumentException("lookAndFeelClassName 参数不能为空");
    this.currentLookAndFeelClassName = lookAndFeelClassName;

    UIManager.setLookAndFeel(lookAndFeelClassName);
    if (lookAndFeelClassName.equals(UIManager.getSystemLookAndFeelClassName())) {
      UIManager.getLookAndFeelDefaults().put("TabbedPane.background",
        new Color(192, 192, 192));
    }

    if (mainFrame == null)
      return;
    if (mainFrame.owner != null) {
      SwingUtilities.updateComponentTreeUI(mainFrame.owner);
    }
    //else {
    //	SwingUtilities.updateComponentTreeUI(mainFrame);
    //}
    mainFrame.getCurrentPerspective().changeLookAndFeel(lookAndFeelClassName);
    
    LookAndFeelUtil.removeAllSplitPaneBorders();
  }

  public void changeLookAndFeel(LookAndFeel newLookAndFeel) throws Exception {
    changeLookAndFeel(newLookAndFeel.getClass().getName());
  }

  final public void run() {
    doStart();

    SplashPlugin.getDefault().freeSplash();

    mainFrame.update();

    StartupRegistry startupRegistry = new StartupRegistry();
    for (UIStartup startup : startupRegistry.getStartup()) {
      startup.run(bundleContext);
    }
  }

  public abstract void doStart();

  public String getCurrentLookAndFeelClassName() {
    return currentLookAndFeelClassName;
  }

  public BundleContext getBundleContext() {
    return bundleContext;
  }

  public void setBundleContext(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Override
  public void reload() {
    mainFrame.update();
  }
}
