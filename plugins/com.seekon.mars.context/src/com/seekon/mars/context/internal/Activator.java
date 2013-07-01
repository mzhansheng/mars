package com.seekon.mars.context.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.context.ISharedModuleProvider;

public class Activator implements BundleActivator {

	public void start(BundleContext bundleContext) throws Exception {
	  try {
      org.apache.ibatis.io.VFS.addImplClass(BundleVFS.class);
    } catch (Throwable e) {
    }
		initGlobaleGuiceInjector();
	}

	public void stop(BundleContext bundleContext) throws Exception {

	}

	private void initGlobaleGuiceInjector() {
		List<Module> moduleList = new ArrayList<Module>();
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(ISharedModuleProvider.EXTENSION_POINT_ID);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			if (element.getAttribute("class") != null) {
				try {
					ISharedModuleProvider provider = (ISharedModuleProvider) element
							.createExecutableExtension("class");
					moduleList.add(provider.get());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		Injector injector = Guice.createInjector(moduleList);
		try {
			Field field = GuiceContext.class.getDeclaredField("globalSharedInjector");
			field.setAccessible(true);
			field.set(GuiceContext.class, injector);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
