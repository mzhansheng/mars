package com.seekon.smartclient.framework.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.seekon.smartclient.framework.UIStartup;

final class StartupRegistry {

	private static final String ATT_CLASS = "class";
	private static final String ATT_INDEX = "index";
	
	private List<UIStartup> startups = new ArrayList<UIStartup>();

	public StartupRegistry() {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if(extensionRegistry == null){
			return;
		}
		IConfigurationElement[] extensions = extensionRegistry
				.getConfigurationElementsFor(UIStartup.STARTUP_EXTENSION_POINT_ID);
		
		if(extensions != null && extensions.length > 0){
			java.util.Arrays.sort(extensions, new ConfigurationElementComparator());
		}
		
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			String className = element.getAttribute(ATT_CLASS);
			if (className != null) {
				try {
					startups.add((UIStartup) element.createExecutableExtension(ATT_CLASS));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public UIStartup[] getStartup() {
		return startups.toArray(new UIStartup[startups.size()]);
	}
	
	class ConfigurationElementComparator implements Comparator<IConfigurationElement>{

		@Override
		public int compare(IConfigurationElement o1, IConfigurationElement o2) {			
			int indexNo1 = Integer.MAX_VALUE;
			int indexNo2 = Integer.MAX_VALUE;
			try{
				indexNo1 = Integer.parseInt(o1.getAttribute(ATT_INDEX));
			}catch(Throwable e){
			}
			try{
				indexNo2 = Integer.parseInt(o2.getAttribute(ATT_INDEX));
			}catch(Throwable e){
			}
			
			return indexNo1 - indexNo2;
		}
		
	}
}
