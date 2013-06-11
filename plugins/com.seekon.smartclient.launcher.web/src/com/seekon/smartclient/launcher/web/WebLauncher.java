package com.seekon.smartclient.launcher.web;

import org.osgi.framework.BundleContext;

public interface WebLauncher {

	public static final String KEY_PARAM_SERVER_ROOT_CODE = "serverRootCode";

	public static final String KEY_PARAM_SYSTEM_BUNLE_NAMES = "systemBundleNames";

	void initOSGiFramework() throws Exception;

	BundleContext getSystemBundleContext();

	void shutdown();

	String getParameter(String key);
}
