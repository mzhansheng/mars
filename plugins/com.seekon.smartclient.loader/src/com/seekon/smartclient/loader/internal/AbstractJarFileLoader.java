package com.seekon.smartclient.loader.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;

import com.seekon.smartclient.loader.JarFile;
import com.seekon.smartclient.loader.JarFileLoader;

public abstract class AbstractJarFileLoader implements JarFileLoader {

	static final String BUNDLE_SYMBOLICNAME = "Bundle-SymbolicName";

	static final String BUNDLE_VERSION = "Bundle-Version";

	static final String BUNDLE_ACTIVATOR = "Bundle-Activator";

	static final String FRAGMENT_HOST = "Fragment-Host";

	BundleContext bundleContext;

	String serverRootUrl;

	public AbstractJarFileLoader(BundleContext bundleContext, String serverRootUrl) {
		super();
		this.bundleContext = bundleContext;
		this.serverRootUrl = serverRootUrl;
	}

	@Override
	final public void load(List<JarFile> jarFileList) {
		ResolveAndStartBundles(installJarFiles(checkUpdatedJarFiles(jarFileList)));
	}

	protected abstract List<JarFile> checkUpdatedJarFiles(
			List<JarFile> jarFileList);

	protected Bundle[] installJarFiles(List<JarFile> jarFileList) {
		List<Bundle> installedBundleList = new ArrayList<Bundle>();
		for (JarFile jarFile : jarFileList) {
			Attributes manifest = jarFile.getManifest();
			String bundleSymbolicName = manifest.getValue(BUNDLE_SYMBOLICNAME);
			if (bundleSymbolicName == null) {
				continue;
			}
			if (bundleSymbolicName.indexOf(";") > 0) {
				bundleSymbolicName = bundleSymbolicName.substring(0,
						bundleSymbolicName.indexOf(";"));
			}
			String version = manifest.getValue(BUNDLE_VERSION);
			if (bundleInstalled(bundleContext, bundleSymbolicName, version)) {
				continue;
			}
			try {
				String bundleURL = "reference:"
						+ new File(jarFile.getLocation()).toURI().toURL().toString();
				Bundle bundle = bundleContext.installBundle(bundleURL);
				installedBundleList.add(bundle);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return installedBundleList.toArray(new Bundle[installedBundleList.size()]);
	}


  protected void ResolveAndStartBundles(Bundle[] installedBundles) {
    PackageAdmin admin = (PackageAdmin) bundleContext.getService(bundleContext
        .getServiceReference(PackageAdmin.class.getName()));
    admin.resolveBundles(installedBundles);

    for (Bundle bundle : installedBundles) {
      try {
        if (bundle.getHeaders().get(FRAGMENT_HOST) == null
            && bundle.getHeaders().get(BUNDLE_ACTIVATOR) != null
            && !"server".equals(bundle.getHeaders().get("Bundle-Type"))
            && bundle.getState() != Bundle.STARTING
            && bundle.getState() != Bundle.ACTIVE) {
          bundle.start();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

	private static boolean bundleInstalled(BundleContext context,
			String symbolicName, String version) {
		for (Bundle bundle : context.getBundles()) {
			String bundleSymbolicName = bundle.getSymbolicName();
			String bundleVer = bundle.getVersion().toString();
			if (bundleSymbolicName.equals(symbolicName) && bundleVer.equals(version)) {
				return true;
			}
		}
		return false;
	}
}
