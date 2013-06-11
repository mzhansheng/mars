package com.seekon.smartclient.splash;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.eclipse.osgi.framework.internal.core.Constants;
import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;

import com.seekon.smartclient.splash.internal.Messages;

/**
 * The activator class controls the plug-in life cycle
 */
public class SplashPlugin implements BundleActivator{

  public static boolean debug = System.getProperty("osgi.debug") != null;; //$NON-NLS-1$

  // The plug-in ID
  public static final String PLUGIN_ID = "com.seekon.smartclient.splash"; //$NON-NLS-1$

  // The shared instance
  private static SplashPlugin plugin;
  
  private BundleContext context;
  
  private final class StartBundleListener implements BundleListener {
    @Override
    public void bundleChanged(BundleEvent event) {
      if (splash == null)
        return;
      int eventType = event.getType();
      if (eventType != BundleEvent.STARTED)
        return;
      Bundle bundle2 = event.getBundle();
      progressBar.setValue(progressBar.getValue() + 1);
      splash.displayMessage(String.format(Messages.SplashPlugin_loading + "%s",
        bundle2.getSymbolicName(), bundle2.getHeaders().get("Bundle-Name"))); //$NON-NLS-1$
    }
  }

  static void changeUIFont(Font font) {
    if (font == null)
      return;
    FontUIResource f = new FontUIResource(font);
    Enumeration keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value instanceof javax.swing.plaf.FontUIResource) {
        UIManager.put(key, f);
      }
    }
  }

  static {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      if (debug) {
        System.out.println("Warning:\n    Change LookAndFeel to System Style Fail"); //$NON-NLS-1$
      }
    }
    changeUIFont(new Font("宋体", Font.PLAIN, 12)); //$NON-NLS-1$
  }

  private Splash splash;

  private JProgressBar progressBar = new JProgressBar();

  private StartBundleListener startBundleListener = new StartBundleListener();

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    plugin = this;
    this.context = context;
    
    progressBar.setStringPainted(false);
    String splashLocation = System.getProperty("osgi.splashLocation");
    InputStream imageStream = null;
    if (splashLocation != null) {
      File file = new File(splashLocation);
      if (file.exists() && file.isFile()) {
        imageStream = new FileInputStream(file);
      } else {
        imageStream = SplashPlugin.class.getClassLoader().getResourceAsStream(
          "splash.jpg");
      }
    } else {
      imageStream = SplashPlugin.class.getClassLoader().getResourceAsStream(
        "splash.jpg");
    }
    Image icon = ImageIO.read(imageStream);
    ImageIcon imageIcon = new ImageIcon(icon);
    splash = new Splash(imageIcon.getImage(), progressBar, true);

    splash.setMessageFontColor(Color.blue);
    splash.displayMessage(Messages.SplashPlugin_initialization);
    splash.setActive(true);
    int bundleCount = context.getBundles().length;

    if (debug)
      System.out.println("bundle count: " + bundleCount); //$NON-NLS-1$
    progressBar.setMinimum(0);

    int max = 0;
    int value = 0;

    for (Bundle bundle : context.getBundles()) {
      if (hasLazyActivationPolicy(bundle)) {
        max++;
        if (bundle.getState() == BundleEvent.STARTED)
          progressBar.setValue(value++);
      }
    }
    progressBar.setMaximum(max);
    progressBar.setValue(value);

    context.addBundleListener(startBundleListener);
    if (debug) {
      context.addBundleListener(new BundleListener() {

        @Override
        public void bundleChanged(BundleEvent event) {
          int eventType = event.getType();
          if (eventType != BundleEvent.STARTED)
            return;
          Bundle bundle2 = event.getBundle();
          if (debug)
            System.out
              .println("BundleEvent: " + bundle2.getSymbolicName() + " Started"); //$NON-NLS-1$
        }
      });
    }
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
  }

  /**
   * Returns the shared instance
   *
   * @return the shared instance
   */
  public static SplashPlugin getDefault() {
    return plugin;
  }

  public void freeSplash() {
    if (splash != null) {
      splash.setActive(false);
      splash.setVisible(false);
      splash = null;
    }
    context.removeBundleListener(startBundleListener);
    try {
      stop(context);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Splash getSplash() {
    return splash;
  }

  private static boolean hasLazyActivationPolicy(Bundle target) {
    // check the bundle manifest to see if it defines a lazy activation policy
    Dictionary<String, String> headers = target.getHeaders(""); //$NON-NLS-1$
    // first check to see if this is a fragment bundle
    String fragmentHost = headers.get(Constants.FRAGMENT_HOST);
    if (fragmentHost != null)
      return false; // do not activate fragment bundles
    // look for the OSGi defined Bundle-ActivationPolicy header
    String activationPolicy = headers.get(Constants.BUNDLE_ACTIVATIONPOLICY);
    try {
      if (activationPolicy != null) {
        ManifestElement[] elements = ManifestElement.parseHeader(
          Constants.BUNDLE_ACTIVATIONPOLICY, activationPolicy);
        if (elements != null && elements.length > 0) {
          // if the value is "lazy" then it has a lazy activation poliyc
          if (Constants.ACTIVATION_LAZY.equals(elements[0].getValue()))
            return true;
        }
      } else {
        // check for Eclipse specific lazy start headers "Eclipse-LazyStart" and "Eclipse-AutoStart"
        String eclipseLazyStart = headers.get(Constants.ECLIPSE_LAZYSTART);
        if (eclipseLazyStart == null)
          eclipseLazyStart = headers.get(Constants.ECLIPSE_AUTOSTART);
        ManifestElement[] elements = ManifestElement.parseHeader(
          Constants.ECLIPSE_LAZYSTART, eclipseLazyStart);
        if (elements != null && elements.length > 0) {
          // if the value is true then it is lazy activated
          if ("true".equals(elements[0].getValue())) //$NON-NLS-1$
            return true;
          // otherwise it is only lazy activated if it defines an exceptions directive.
          else if (elements[0].getDirective("exceptions") != null) //$NON-NLS-1$
            return true;
        }
      }
    } catch (BundleException be) {
      // ignore this
    }
    return false;
  }

}
