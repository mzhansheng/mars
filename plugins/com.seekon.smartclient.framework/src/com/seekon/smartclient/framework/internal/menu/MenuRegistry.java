package com.seekon.smartclient.framework.internal.menu;

import java.util.Comparator;

import javax.swing.JMenu;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.seekon.smartclient.framework.util.OrderableMap;

final class MenuRegistry {

	private static final String MENUS_EXTENSION_POINT_ID = "com.seekon.smartclient.framework.menus";
	private static final String ATT_KEY = "key";
	private static final String ATT_CLASS = "class";
	private static final String ATT_REF_KEY = "refkey";
	private static final String ATT_BEFORE = "before";

	private OrderableMap<String, JMenu> menuMap = new OrderableMap<String, JMenu>();

	private static MenuRegistry instance;
	
	private MenuRegistry() {
		super();

		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		IConfigurationElement[] extensions = extensionRegistry
				.getConfigurationElementsFor(MENUS_EXTENSION_POINT_ID);
		if (extensions != null && extensions.length > 0) {
			java.util.Arrays.sort(extensions,
					new MenuConfigurationElementComparator());
		}
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			boolean before = Boolean.parseBoolean(element
					.getAttribute(ATT_BEFORE));
			String refkey = element.getAttribute(ATT_REF_KEY);
			if (refkey == null) {
				refkey = JHelpMenu.HELP_MENU_KEY;
				before = true;
			}
			try {
				menuMap.put(element.getAttribute(ATT_KEY),
						(JMenu) element.createExecutableExtension(ATT_CLASS),
						refkey, before);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static MenuRegistry getInstance(boolean reload) {
	  if(reload || instance == null){
	    instance = new MenuRegistry();
	  }
    return instance;
  }

  public OrderableMap<String, JMenu> getMenuMap() {
		return menuMap;
	}

	class MenuConfigurationElementComparator implements
			Comparator<IConfigurationElement> {

		@Override
		public int compare(IConfigurationElement o1, IConfigurationElement o2) {
			return o1.getAttribute(ATT_REF_KEY) == null ? -1 : 0;
		}

	}
}
