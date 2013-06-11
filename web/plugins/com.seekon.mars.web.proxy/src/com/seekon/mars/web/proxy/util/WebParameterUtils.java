package com.seekon.mars.web.proxy.util;

import javax.servlet.ServletContext;

public class WebParameterUtils {

	public static String getSystemBundleNames(boolean packEnabled, ServletContext sc) {
		String systemBundles = "";
		String[] systemBundleNames = new String[] {
				"com.seekon.smartclient.splash_1.0.0.jar",
				"org.eclipse.equinox.common_3.6.0.v20100503.jar",
				"org.eclipse.update.configurator_3.3.100.v20100512.jar",
				"org.eclipse.equinox.registry_3.5.0.v20100503.jar",
				"org.eclipse.osgi.services_3.2.100.v20100503.jar",
				"org.eclipse.core.runtime_3.6.0.v20100505.jar",
				"org.eclipse.equinox.app_1.3.1.R36x_v20100803.jar",
				"org.eclipse.core.jobs_3.5.1.R36x_v20100824.jar",
				"org.eclipse.equinox.preferences_3.3.0.v20100503.jar",
				"org.eclipse.core.contenttype_3.4.100.v20100505-1235.jar",
				"com.springsource.org.apache.log4j_1.2.16.jar",
				"com.caucho.hessian_3.2.1.jar",
				"com.seekon.smartclient.hessiansupport_1.0.0.jar",
				"com.seekon.mars.update.service_1.0.0.jar",
				"com.seekon.smartclient.module_1.0.0.jar",
				"com.seekon.smartclient.updater_1.0.0.jar",
				"com.seekon.smartclient.launcher_1.0.0.jar",
				"com.seekon.smartclient.laf_1.6.7.jar",
				"com.seekon.smartclient.component_1.0.0.jar",
				"com.seekon.smartclient.framework_1.0.0.jar",
				"com.seekon.smartclient.javadocking_1.5.0.jar",
				"com.seekon.smartclient.login_1.0.0.jar",
				"com.seekon.system.common_1.0.0.jar",
				"com.seekon.system.org_1.0.0.jar",
				"com.seekon.system.auth_1.0.0.jar",
				"com.seekon.system.rule_1.0.0.jar",
				"com.seekon.mars.rule.engine_1.0.0.jar",
				"com.seekon.smartclient.layout_1.0.0.jar",
				"com.seekon.mars.context_1.0.0.jar",
				"com.seekon.mars.osgi.util_1.0.0.jar",
				"com.google.inject_3.0.0.jar",
				"org.ops4j.peaberry_1.3.0.jar",
				"com.springsource.javax.inject_1.0.0.jar",
				"com.springsource.org.aopalliance_1.0.0.jar",
				"org.ops4j.peaberry.patch_1.0.0.jar",
				"com.seekon.mars.dictionary_1.0.0.jar",
				"com.springsource.org.json-1.0.0.jar"
		};

		for (int i = 0; i < systemBundleNames.length; i++) {
			String fileName = systemBundleNames[i].trim();

			long fileLastModified = 0;
			if (packEnabled) {
				fileLastModified = FileUtils.getFileLastModifiedTime(fileName
						+ ".pack.gz", sc);
			}
			if (fileLastModified <= 0) {
				fileLastModified = FileUtils.getFileLastModifiedTime(fileName, sc);
			}

			systemBundles += fileName + ":" + fileLastModified + ",";
		}
		systemBundles = systemBundles.substring(0, systemBundles.length() - 1);
		return systemBundles;
	}
}
