package com.seekon.mars.equinox.hooks.extend.patch;

import java.security.ProtectionDomain;
import java.util.ArrayList;

import org.eclipse.osgi.baseadaptor.BaseData;
import org.eclipse.osgi.baseadaptor.HookConfigurator;
import org.eclipse.osgi.baseadaptor.HookRegistry;
import org.eclipse.osgi.baseadaptor.bundlefile.BundleEntry;
import org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook;
import org.eclipse.osgi.baseadaptor.loader.BaseClassLoader;
import org.eclipse.osgi.baseadaptor.loader.ClasspathEntry;
import org.eclipse.osgi.baseadaptor.loader.ClasspathManager;
import org.eclipse.osgi.baseadaptor.loader.FragmentClasspath;
import org.eclipse.osgi.framework.adaptor.BundleProtectionDomain;
import org.eclipse.osgi.framework.adaptor.ClassLoaderDelegate;

public class PatchClassLoadingHook implements ClassLoadingHook, HookConfigurator {

  public PatchClassLoadingHook() {
		super();
	}

	public byte[] processClass(String name, byte[] classbytes,
    ClasspathEntry classpathEntry, BundleEntry entry, ClasspathManager manager) {
    return null;
  }

  /**
   * 将补丁的FragmentClasspath排序后插入到Entries最前面
   * 
   */
  public boolean addClassPathEntry(ArrayList cpEntries, String cp,
    ClasspathManager hostmanager, BaseData sourcedata, ProtectionDomain sourcedomain) {
//    System.out.println("##addClassPathEntry:"
//      + hostmanager.getBaseClassLoader().getBundle());
    boolean result = false;
    if (!hostmanager.getBaseData().equals(sourcedata)) {//添加fragement时不处理
      return result;
    }

    FragmentClasspath[] fragments = hostmanager.getFragmentClasspaths();
    if (fragments == null || fragments.length == 0) {
      return result;
    }

    java.util.Arrays.sort(fragments, PatchComparatorFactory
      .getPatchComparator(PatchComparatorFactory.PATCH_TYPE_SOURCE));
    for (int i = fragments.length - 1; i >= 0; i--) {
      if (!PatchUtils.isPatch(fragments[i])) {
        continue;
      }

      ClasspathEntry[] entries = fragments[i].getEntries();
      if (entries != null && entries.length > 0) {
        cpEntries.addAll(0, java.util.Arrays.asList(entries));
        result = true;
      }
    }
    return result;
  }

  public String findLibrary(BaseData data, String libName) {
    return null;
  }

  public ClassLoader getBundleClassLoaderParent() {
    return null;
  }

  public BaseClassLoader createClassLoader(ClassLoader parent,
    ClassLoaderDelegate delegate, BundleProtectionDomain domain, BaseData data,
    String[] bundleclasspath) {
    //    return new PatchClassLoader(parent, delegate, domain, data, bundleclasspath);
    return null;
  }

  public void initializedClassLoader(BaseClassLoader baseClassLoader, BaseData data) {

  }

  public void addHooks(HookRegistry hookRegistry) {
    hookRegistry.addClassLoadingHook(this);
  }

}
