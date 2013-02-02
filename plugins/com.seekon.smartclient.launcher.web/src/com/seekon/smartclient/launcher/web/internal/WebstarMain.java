package com.seekon.smartclient.launcher.web.internal;

import java.util.HashMap;
import java.util.Map;

import com.seekon.smartclient.launcher.web.AllPermissionSecurityManager;
import com.seekon.smartclient.launcher.web.WebLauncher;

public class WebstarMain {

	static {
    System.setSecurityManager(new AllPermissionSecurityManager());
  }
	
	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			throw new RuntimeException("启动参数小于两个.");
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put(WebLauncher.KEY_PARAM_SERVER_ROOT_CODE, args[0]);
		params.put(WebLauncher.KEY_PARAM_SYSTEM_BUNLE_NAMES, args[1]);

		WebLauncher webLauncher = new OSGiWebLauncher(params);
		try {
			webLauncher.initOSGiFramework();
			webLauncher.getSystemBundleContext().registerService(
					WebstarMain.class.getName(), new WebstarMain(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
