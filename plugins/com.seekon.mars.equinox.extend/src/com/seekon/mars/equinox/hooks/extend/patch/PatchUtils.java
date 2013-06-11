package com.seekon.mars.equinox.hooks.extend.patch;

import org.eclipse.osgi.baseadaptor.loader.FragmentClasspath;
import org.osgi.framework.Bundle;

public class PatchUtils {

  public static String MANIFEST_KEY_IS_PATCH = "Is-Patch";

  public static String MANIFEST_KEY_FRAGMENT_HOST = "Fragment-Host";

  public static boolean isPatch(Bundle bundle) {
    boolean isPatch = false;
    try {
      isPatch = Boolean.valueOf(
        (String) bundle.getHeaders().get(MANIFEST_KEY_IS_PATCH)).booleanValue()
        && (bundle.getHeaders().get(MANIFEST_KEY_FRAGMENT_HOST) != null);
    } catch (Exception e) {
    }
    return isPatch;
  }

  public static boolean isPatch(FragmentClasspath fragment) {
    boolean isPatch = false;
    try {
      isPatch = Boolean.valueOf(
        (String) fragment.getBundleData().getManifest().get(MANIFEST_KEY_IS_PATCH))
        .booleanValue();
    } catch (Exception e) {
    }
    return isPatch;
  }
  
}
