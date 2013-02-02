package com.seekon.mars.equinox.hooks.extend.patch.internal;

import java.text.Collator;

import org.osgi.framework.Bundle;

import com.seekon.mars.equinox.hooks.extend.patch.PatchComparator;

/**
 * 
 * 可安装补丁在安装过程中的比较器，版本越新的越后执行sql脚本
 *
 */
public class InstallPatchComparator implements PatchComparator{
  
  public int compare(Object patch0, Object patch1) {
    if (!(patch0 instanceof Bundle) || !(patch1 instanceof Bundle)) {
      throw new RuntimeException("compare方法不支持的参数类型：" + patch0.getClass().getName()
        + ", " + patch1.getClass().getName());
    }
    
    Bundle bundle0 = (Bundle)patch0;
    Bundle bundle1 = (Bundle)patch1;
    String bundleName0 = (String)bundle0.getHeaders().get(MANIFEST_KEY_COMPARE);
    String bundleName1 = (String)bundle1.getHeaders().get(MANIFEST_KEY_COMPARE);
    
    Collator collator = Collator.getInstance(java.util.Locale.CHINA);
    return collator.compare(bundleName0.substring(bundleName0.lastIndexOf(".")),
      bundleName1.substring(bundleName1.lastIndexOf(".")));
  }
}
