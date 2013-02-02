package com.seekon.smartclient.module.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.seekon.smartclient.module.ResourceRegistry;

public class ResourceRegistryUtils {

	private static final String EXTENSION_POINT_ID = "com.seekon.smartclient.module.resourceRegistry";
	private static final String ATT_CLASS = "class";
	
	List<ResourceRegistry> resourceRegistryList = new ArrayList<ResourceRegistry>();
	
	private static final ResourceRegistryUtils instance = new ResourceRegistryUtils();
	
	public static ResourceRegistryUtils getInstance(){
		return instance;
	}
	
	private ResourceRegistryUtils() {
		super();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		IConfigurationElement[] extensions = extensionRegistry
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (IConfigurationElement element : extensions) {
			try {
				resourceRegistryList.add((ResourceRegistry) element.createExecutableExtension(ATT_CLASS));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<ResourceRegistry> getResourceRegistryList() {
		return resourceRegistryList;
	}
	
}
