package com.seekon.mars.equinox.hooks.extend.patch;

import java.util.Comparator;

/**
 * 
 * 补丁的比较器，用于对补丁版本等的比较
 *
 */
public interface PatchComparator extends Comparator {
  static final String MANIFEST_KEY_COMPARE = "Bundle-SymbolicName";
}
