package com.seekon.smartclient.launcher.web.internal;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.seekon.smartclient.launcher.web.JBaseApplet;
import com.seekon.smartclient.launcher.web.WebLauncher;
import com.seekon.smartclient.launcher.web.debug.DebugConstants;
import com.seekon.smartclient.launcher.web.debug.DebugFrameFactory;

public class JOSGiApplet extends JBaseApplet {

	private static final long serialVersionUID = 182485785403337970L;

	private WebLauncher webLauncher;

	private Properties serviceProperties = new Properties();

	private ServiceRegistration serviceRegistration;

	@Override
	public void init() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(WebLauncher.KEY_PARAM_SERVER_ROOT_CODE, this.getCodeBase()
				.toString());
		params.put(WebLauncher.KEY_PARAM_SYSTEM_BUNLE_NAMES,
				this.getParameter("systemBundles"));
		webLauncher = new OSGiWebLauncher(params);

		String marsDebug = System.getProperty(DebugConstants.DEBUG_KEY);
		int port = 9922;
		boolean debug = "true".equalsIgnoreCase(marsDebug);
		if (debug) {
			String p = System.getProperty(DebugConstants.OSGI_DEBUG_PORT);
			if (p != null) {
				try {
					port = Integer.parseInt(p);
				} catch (Throwable e) {
				}
			}
			System.setProperty(DebugConstants.OSGI_CONSOLE, Integer.toString(port));
		}
		super.init();
		if (debug) {
			final int fport = port;
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					try {
						showDebugWindow("127.0.0.1", fport);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	@Override
	protected void createComponent(Container c) {
		try {
			webLauncher.initOSGiFramework();

			BundleContext bundleContext = webLauncher.getSystemBundleContext();
			serviceProperties.put("type", JOSGiApplet.class.getName());
			serviceRegistration = bundleContext.registerService(
					JApplet.class.getName(), this, serviceProperties);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		//serviceRegistration.unregister();
		try {
			webLauncher.shutdown();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void showDebugWindow(String host, int port) throws Exception {
		JFrame frame = DebugFrameFactory.createDebugFrame(host, port);
		frame.setVisible(true);
	}

}
