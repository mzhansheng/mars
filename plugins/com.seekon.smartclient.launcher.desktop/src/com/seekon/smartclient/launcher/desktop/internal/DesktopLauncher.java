package com.seekon.smartclient.launcher.desktop.internal;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.seekon.smartclient.launcher.web.WebLauncher;

public class DesktopLauncher implements WebLauncher {

  private Map<String, String> params = new HashMap<String, String>();

  private ServiceRegistration serviceRegistration;
  
  @Override
  public void initOSGiFramework() throws Exception {
    String serverRootUrl = System.getProperties().getProperty("server.root.url");
    if (serverRootUrl != null) {
      if (serverRootUrl.endsWith("/")) {
        serverRootUrl = serverRootUrl.substring(0, serverRootUrl.length() - 1);
      }

      params.put(KEY_PARAM_SERVER_ROOT_CODE, serverRootUrl + "/client/plugins/");
      try {
        URL getSystemBundlesURL = new URL(serverRootUrl
          + "/proxy/getSystemBundleInfoServlet");
        InputStream is = getSystemBundlesURL.openStream();
        String systemBundles = "";
        int size = 0;
        byte[] buffer = new byte[1024];
        while ((size = is.read(buffer)) > 0) {
          systemBundles += new String(buffer, 0, size);
        }
        ;
        params.put(KEY_PARAM_SYSTEM_BUNLE_NAMES, systemBundles);
      } catch (Throwable e) {
        e.printStackTrace();
      }

      Properties serviceProperties = new Properties();
      serviceProperties.put("type", DesktopLauncher.class.getName());
      serviceRegistration = Activator.bundleContext
        .registerService(WebLauncher.class.getName(), this, serviceProperties);
    }
  }

  @Override
  public BundleContext getSystemBundleContext() {
    return Activator.bundleContext;
  }

  @Override
  public void shutdown() {
    serviceRegistration.unregister();
  }

  @Override
  public String getParameter(String key) {
    return params.get(key);
  }

}
