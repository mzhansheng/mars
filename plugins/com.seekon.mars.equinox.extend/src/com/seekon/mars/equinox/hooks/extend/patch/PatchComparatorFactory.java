package com.seekon.mars.equinox.hooks.extend.patch;

import com.seekon.mars.equinox.hooks.extend.patch.internal.InstallPatchComparator;
import com.seekon.mars.equinox.hooks.extend.patch.internal.ResourcePatchComparator;

public class PatchComparatorFactory {

  public static final String PATCH_TYPE_INSTALL = "patch.type.install";

  public static final String PATCH_TYPE_SOURCE = "patch.type.source";

  public static PatchComparator getPatchComparator(String patchType) {
    if(PATCH_TYPE_INSTALL.equals(patchType)){
      return new InstallPatchComparator();
    }else if(PATCH_TYPE_SOURCE.equals(patchType)){
      return new ResourcePatchComparator();
    }
    return null;
  }
}
