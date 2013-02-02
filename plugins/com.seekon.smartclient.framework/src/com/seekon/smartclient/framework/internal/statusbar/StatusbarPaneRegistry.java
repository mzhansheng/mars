package com.seekon.smartclient.framework.internal.statusbar;

import java.awt.Component;
import java.util.Comparator;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.seekon.smartclient.framework.util.OrderableSet;

public class StatusbarPaneRegistry {

	private static final String EXTENSION_POINT_ID = "com.seekon.smartclient.framework.statusbarPanes";
	private static final String ATT_INDEX = "index";
	private static final String ATT_CLASS = "class";

	private Set<Component> statusbarPanes = new OrderableSet<Component>();

	private static StatusbarPaneRegistry instance;
	
	private StatusbarPaneRegistry() {
		super();

		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		IConfigurationElement[] extensions = extensionRegistry
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		if (extensions != null && extensions.length > 0) {
			java.util.Arrays.sort(extensions, new ConfigurationElementComparator());
		}
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			try {
				statusbarPanes.add((Component) element
						.createExecutableExtension(ATT_CLASS));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Set<Component> getStatusbarPanes() {
		return statusbarPanes;
	}

	public static StatusbarPaneRegistry getInstance(boolean reload) {
	  if(reload || instance == null){
	    instance = new StatusbarPaneRegistry();
	  }
    return instance;
  }

  class ConfigurationElementComparator implements
			Comparator<IConfigurationElement> {

		@Override
		public int compare(IConfigurationElement o1, IConfigurationElement o2) {
			int index1 = 0;
			try {
				index1 = Integer.parseInt(o1.getAttribute(ATT_INDEX));
			} catch (Exception e) {
			}
			int index2 = 0;
			try {
				index2 = Integer.parseInt(o2.getAttribute(ATT_INDEX));
			} catch (Exception e) {
			}
			return index1 - index2;
		}
	}

}
