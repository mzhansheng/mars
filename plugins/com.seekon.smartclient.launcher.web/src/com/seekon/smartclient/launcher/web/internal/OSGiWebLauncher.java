package com.seekon.smartclient.launcher.web.internal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.eclipse.equinox.launcher.WebStartMain;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.seekon.smartclient.launcher.web.WebLauncher;

public class OSGiWebLauncher implements WebLauncher {

	private Map<String, String> params = new HashMap<String, String>();

	private LauncherMain main = null;

	private Properties serviceProperties = new Properties();

	private ServiceRegistration serviceRegistration;

	public OSGiWebLauncher(Map<String, String> params) {
		super();
		this.params = params;
	}

	public void initOSGiFramework() throws Exception {
		URL baseUrl = new URL(this.getParameter(KEY_PARAM_SERVER_ROOT_CODE));
		String installPath = System.getProperty("user.home") + "/.seekon/client/"
				+ baseUrl.getHost() + "_" + baseUrl.getPort();

		Properties configProperties = this.getConfigProperties(installPath);
		this.configOSGIBundlesProperty(configProperties);
		writeConfigFile(installPath, configProperties);

		String[] args = new String[] { "-nosplash", /* "-debug", */"-clean",
				"-noExit", "-install", installPath };

		// System.setSecurityManager(null);
		main = new LauncherMain();
		int result = main.run(args);
		System.out.println("result:" + result);

		BundleContext bundleContext = getSystemBundleContext();
		serviceProperties.put("type", OSGiWebLauncher.class.getName());
		serviceRegistration = bundleContext.registerService(
				WebLauncher.class.getName(), this, serviceProperties);
	}

	public BundleContext getSystemBundleContext() {
		return main.getSystemBundleContext();
	}

	public void shutdown() {
		//serviceRegistration.unregister();
		main.shutdown();
	}

	@Override
	public String getParameter(String key) {
		return params.get(key);
	}

	private Properties getConfigProperties(String installPath)
			throws MalformedURLException {
		Properties props = new Properties();
		URL baseUrl = new URL(this.getParameter(KEY_PARAM_SERVER_ROOT_CODE));
		String configUrl = baseUrl.getProtocol() + "://" + baseUrl.getHost() + ":"
				+ baseUrl.getPort() + "/mars/client/config.ini";
		try {
			props.load(new URL(configUrl).openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}

	private void configOSGIBundlesProperty(Properties configProperties) {
		String bundleList = (String) configProperties
				.get(LauncherMain.PROP_OSGI_BUNDLES);
		if (bundleList == null || bundleList.trim().length() == 0) {
			System.getProperties().remove(LauncherMain.PROP_OSGI_BUNDLES);
		} else {
			System.getProperties().put(LauncherMain.PROP_OSGI_BUNDLES, bundleList);
		}
		System.getProperties().put("osgi.framework.extensions",
				"com.seekon.mars.equinox.extend");
		System.getProperties().put("remote.service", "true");
	}

	private void writeConfigFile(String installPath, Properties configProperties)
			throws IOException {
		File dir = new File(installPath + "/configuration");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(dir.getAbsoluteFile() + "/config.ini");
		file.setWritable(true);
		FileWriter writer = new FileWriter(file, false);
		configProperties.store(writer, null);
		writer.close();
	}

	class LauncherMain extends WebStartMain {

		static final String PROP_OSGI_BUNDLES = "osgi.bundles";

		private BundleContext context = null;

		protected void invokeFramework(String[] passThruArgs, URL[] bootPath) {
			try {
				if (org.eclipse.core.runtime.adaptor.EclipseStarter.isRunning()) {
					this.context = org.eclipse.core.runtime.adaptor.EclipseStarter
							.getSystemBundleContext();
				} else {
					String extenstionBundles = null;
					if (this.extensionPaths != null) {
						extenstionBundles = "";
						for (String extension : this.extensionPaths) {
							extenstionBundles += ",reference:" + extension;
						}
					}

					Map<String, String> initialPropertyMap = new HashMap<String, String>();
					if (extenstionBundles != null) {
						initialPropertyMap.put("osgi.framework.extensions",
								extenstionBundles.substring(1));
					}
					EclipseStarter.setInitialProperties(initialPropertyMap);

					this.context = org.eclipse.core.runtime.adaptor.EclipseStarter
							.startup(passThruArgs, null);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		public void shutdown() {
			try {
				org.eclipse.core.runtime.adaptor.EclipseStarter.shutdown();
				context = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public BundleContext getSystemBundleContext() {
			if (!org.eclipse.core.runtime.adaptor.EclipseStarter.isRunning()) {
				throw new IllegalStateException("osgi framework is not in running");
			}
			return context;
		}
	}
}
