package com.seekon.smartclient.framework.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.seekon.smartclient.framework.Perspective;

public class PerspectiveRegistry {

	private static final String EXTENSION_POINT_ID = "com.seekon.smartclient.framework.perspectives";
	private static final String ATT_DEFAULT = "default";
	private static final String ATT_CLASS = "class";

	private List<Perspective> perspectives = new ArrayList<Perspective>();

	private Perspective defaultPerspective;

	public PerspectiveRegistry() {
		super();

		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		IConfigurationElement[] extensions = extensionRegistry
				.getConfigurationElementsFor(EXTENSION_POINT_ID);

		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			Perspective perspective = null;
			try {
				perspective = (Perspective) element
						.createExecutableExtension(ATT_CLASS);
				perspectives.add(perspective);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (element.getAttribute(ATT_DEFAULT) != null) {
				try {
					boolean def = Boolean.valueOf(element.getAttribute(ATT_DEFAULT));
					if (def) {
						defaultPerspective = perspective;
					}
				} catch (Exception e) {

				}
			}
		}
	}

	public Perspective getDefaultPerspective() {
		Perspective perspective = defaultPerspective;
		if (perspective != null) {
			return perspective;
		}
		if (perspectives != null && !perspectives.isEmpty()) {
			return perspectives.get(0);
		}
		return null;
	}

	public List<Perspective> getPerspectives() {
		return this.perspectives;
	}
}
