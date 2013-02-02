package com.seekon.smartclient.loader.internal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.seekon.smartclient.launcher.web.WebLauncher;
import com.seekon.smartclient.loader.JarFile;
import com.seekon.smartclient.loader.JarFileLoader;

public class LauncherServiceTracker extends ServiceTracker {

  private BundleContext context = null;

  private WebLauncher webLauncher = null;

  public LauncherServiceTracker(BundleContext context) throws InvalidSyntaxException {
    super(
      context,
      context
        .createFilter("(objectClass=com.seekon.smartclient.launcher.web.WebLauncher)"
          //+ "(||(type=com.seekon.smartclient.launcher.web.internal.OSGiWebLauncher)"
          //+ "(type=com.seekon.smartclient.launcher.desktop.internal.DesktopLauncher)))"
          ),
      null);
    this.context = context;
  }

  @Override
  public Object addingService(ServiceReference reference) {
    webLauncher = (WebLauncher) context.getService(reference);

    loadSystemBundles(context,
      webLauncher.getParameter(WebLauncher.KEY_PARAM_SYSTEM_BUNLE_NAMES),
      webLauncher.getParameter(WebLauncher.KEY_PARAM_SERVER_ROOT_CODE));
    return webLauncher;
  }

  @Override
  public void modifiedService(ServiceReference reference, Object service) {
    super.modifiedService(reference, service);
  }

  @Override
  public void removedService(ServiceReference reference, Object service) {
    super.removedService(reference, service);
  }

  private void loadSystemBundles(BundleContext context, String systemBundles,
    String codeBase) {
    if (systemBundles == null || systemBundles.trim().length() == 0) {
      return;
    }
    String[] systemBundleNames = systemBundles.split(",");
    List<JarFile> jarFileList = new ArrayList<JarFile>();
    for (String bundleEntry : systemBundleNames) {
      String[] bundle = bundleEntry.split(":");
      jarFileList
        .add(new JarFile(bundle[0].trim(), Long.parseLong(bundle[1].trim())));
    }
    
    JarFileLoader loader = null;
    if ("com.seekon.smartclient.launcher.web.internal.OSGiWebLauncher"
      .equals(webLauncher.getClass().getName())) {
      loader = new JavaCacheFileLoader(context, codeBase);
    } else {
      loader = new LocalCacheFileLoader(context, codeBase);
    }
    
    context.registerService(JarFileLoader.class.getName(), loader, null);
    loader.load(jarFileList);
  }
}
