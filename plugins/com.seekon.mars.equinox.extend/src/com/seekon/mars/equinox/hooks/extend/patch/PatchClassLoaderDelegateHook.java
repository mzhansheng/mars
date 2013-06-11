package com.seekon.mars.equinox.hooks.extend.patch;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.osgi.baseadaptor.HookConfigurator;
import org.eclipse.osgi.baseadaptor.HookRegistry;
import org.eclipse.osgi.baseadaptor.bundlefile.BundleFile;
import org.eclipse.osgi.baseadaptor.hooks.ClassLoadingStatsHook;
import org.eclipse.osgi.baseadaptor.loader.ClasspathEntry;
import org.eclipse.osgi.baseadaptor.loader.ClasspathManager;
import org.eclipse.osgi.baseadaptor.loader.FragmentClasspath;
import org.eclipse.osgi.framework.adaptor.BundleClassLoader;
import org.eclipse.osgi.framework.adaptor.BundleData;
import org.eclipse.osgi.framework.adaptor.ClassLoaderDelegateHook;
import org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader;

public class PatchClassLoaderDelegateHook implements ClassLoaderDelegateHook,
		HookConfigurator {

	public PatchClassLoaderDelegateHook() {
		super();
	}

	public Class postFindClass(String name, BundleClassLoader classLoader,
			BundleData data) throws ClassNotFoundException {
		return null;
	}

	public String postFindLibrary(String name, BundleClassLoader classLoader,
			BundleData data) {
		return null;
	}

	public URL postFindResource(String name, BundleClassLoader classLoader,
			BundleData data) throws FileNotFoundException {
		return null;
	}

	public Enumeration postFindResources(String name,
			BundleClassLoader classLoader, BundleData data)
			throws FileNotFoundException {
		return null;
	}

	public Class preFindClass(String name, BundleClassLoader classLoader,
			BundleData data) throws ClassNotFoundException {
		Class result = null;
		SecurityManager securityManager = System.getSecurityManager();
		if (classLoader instanceof DefaultClassLoader) {
			DefaultClassLoader cl = (DefaultClassLoader) classLoader;
			FragmentClasspath[] fragments = cl.getClasspathManager()
					.getFragmentClasspaths();
			if (fragments.length == 0) {
				return null;
			}

			Method method = null;
			try {
				method = ClasspathManager.class.getDeclaredMethod("findClassImpl",
						new Class[] { String.class, ClasspathEntry.class,
								ClassLoadingStatsHook[].class });
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (method == null) {
				return null;
			}

			java.util.Arrays.sort(fragments, PatchComparatorFactory
					.getPatchComparator(PatchComparatorFactory.PATCH_TYPE_SOURCE));
			method.setAccessible(true);
			ClassLoadingStatsHook[] hooks = new ClassLoadingStatsHook[0];

			for (int i = 0; i < fragments.length; i++) {
				if (!PatchUtils.isPatch(fragments[i])) {
					continue;
				}

				ClasspathEntry[] fragEntries = fragments[i].getEntries();
				for (int j = 0; j < fragEntries.length; j++) {
					try {
						result = (Class) method.invoke(cl.getClasspathManager(),
								new Object[] { name, fragEntries[j], hooks });
					} catch (Throwable e) {
					}
					if (result != null)
						break;
				}
			}
		}
		return result;
	}

	public String preFindLibrary(String name, BundleClassLoader classLoader,
			BundleData data) throws FileNotFoundException {
		return null;
	}

	public URL preFindResource(String name, BundleClassLoader classLoader,
			BundleData data) throws FileNotFoundException {
		URL result = null;
		if (classLoader instanceof DefaultClassLoader) {
			DefaultClassLoader cl = (DefaultClassLoader) classLoader;
			FragmentClasspath[] fragments = cl.getClasspathManager()
					.getFragmentClasspaths();
			java.util.Arrays.sort(fragments, PatchComparatorFactory
					.getPatchComparator(PatchComparatorFactory.PATCH_TYPE_SOURCE));

			for (int i = 0; i < fragments.length; i++) {
				FragmentClasspath fragmentClasspath = fragments[i];
				if (!PatchUtils.isPatch(fragmentClasspath)) {
					continue;
				}

				ClasspathEntry[] entries = fragmentClasspath.getEntries();
				for (int j = 0; j < entries.length; j++) {
					ClasspathEntry classpathEntry = entries[j];
					result = classpathEntry.getBundleFile().getResourceURL(name,
							fragmentClasspath.getBundleData(), j);
					if (result != null) {
						return result;
					}

				}
				BundleFile bundleFile = fragmentClasspath.getBundleData()
						.getBundleFile();

				result = bundleFile.getResourceURL(name,
						fragmentClasspath.getBundleData(), i);
				if (result != null) {
					break;
				}
			}
		}
		return result;
	}

	public Enumeration preFindResources(String name,
			BundleClassLoader classLoader, BundleData data)
			throws FileNotFoundException {
		return null;
	}

	public void addHooks(HookRegistry registry) {
		registry.addClassLoaderDelegateHook(this);
	}

}
