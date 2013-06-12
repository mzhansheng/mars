package com.seekon.smartclient.framework.internal;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class HomepageRegistry {

  private static final String HOMEPAGE_EXTENSION_POINT_ID = "com.seekon.smartclient.framework.homepage";
  private static final String ATT_CLASS = "class";
  
  private List<JComponent> homepages = new ArrayList<JComponent>();

  public HomepageRegistry() {
    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    if(extensionRegistry == null){
      return;
    }
    IConfigurationElement[] extensions = extensionRegistry
        .getConfigurationElementsFor(HOMEPAGE_EXTENSION_POINT_ID);
    
    for (int i = 0; i < extensions.length; i++) {
      IConfigurationElement element = extensions[i];
      String className = element.getAttribute(ATT_CLASS);
      if (className != null) {
        try {
          homepages.add((JComponent) element.createExecutableExtension(ATT_CLASS));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  public JComponent getDefaultHomepage(){
    if(homepages == null || homepages.isEmpty()){
      return new JLabel("this is the homepage.");
    }
    return homepages.get(0);
  }
}
