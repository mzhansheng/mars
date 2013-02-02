package com.seekon.mars.equinox.hooks.extend.patch.internal;

import java.text.Collator;

import org.eclipse.osgi.baseadaptor.loader.FragmentClasspath;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.ExportPackageDescription;
import org.osgi.framework.BundleException;

import com.seekon.mars.equinox.hooks.extend.patch.PatchComparator;

/**
 * 
 * 补丁加载资源的比较器，越新的补丁优先加载
 * 
 */
public class ResourcePatchComparator implements PatchComparator {

  public int compare(Object patch0, Object patch1) {
    if ((patch0 instanceof FragmentClasspath)
      && (patch1 instanceof FragmentClasspath)) {
      return this.compareWithFragmentClasspath((FragmentClasspath) patch0,
        (FragmentClasspath) patch1);
    } else if ((patch0 instanceof BundleDescription)
      && (patch1 instanceof BundleDescription)) {
      return this.compareWithBundleDescription((BundleDescription) patch0,
        (BundleDescription) patch1);
    } else if ((patch0 instanceof ExportPackageDescription)
      && (patch1 instanceof ExportPackageDescription)) {
      return this.compareWithExportPackageDescription(
        (ExportPackageDescription) patch0, (ExportPackageDescription) patch1);
    } else {
      throw new RuntimeException("compare方法不支持的参数类型：" + patch0.getClass().getName()
        + ", " + patch1.getClass().getName());
    }
  }

  private int compareWithExportPackageDescription(ExportPackageDescription pack0,
    ExportPackageDescription pack1) {
    return compareWithBundleDescription(pack0.getExporter(), pack1.getExporter());
  }

  private int compareWithBundleDescription(BundleDescription desc0,
    BundleDescription desc1) {
    return compareWithBundleName(desc0.getSymbolicName(), desc1.getSymbolicName());
  }

  private int compareWithFragmentClasspath(FragmentClasspath fragment0,
    FragmentClasspath fragment1) {
    try {
      String bundleName0 = (String) fragment0.getBundleData().getManifest()
        .get(MANIFEST_KEY_COMPARE);
      String bundleName1 = (String) fragment1.getBundleData().getManifest()
        .get(MANIFEST_KEY_COMPARE);
      return compareWithBundleName(bundleName0, bundleName1);
    } catch (BundleException e) {
      e.printStackTrace();
    }
    return 0;
  }

  protected int compareWithBundleName(String bundleName0, String bundleName1) {
    Collator collator = Collator.getInstance(java.util.Locale.CHINA);
    return collator.compare(bundleName1.substring(bundleName1.lastIndexOf(".")),
      bundleName0.substring(bundleName0.lastIndexOf(".")));// 使用反向排序
  }
}
