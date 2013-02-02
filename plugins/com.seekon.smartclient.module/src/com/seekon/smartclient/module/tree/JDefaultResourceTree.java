package com.seekon.smartclient.module.tree;

import java.awt.Component;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.AbstractResourceTree;
import com.seekon.smartclient.module.ResourceFacade;
import com.seekon.smartclient.module.ResourceRegistry;
import com.seekon.smartclient.module.internal.ResourceRegistryUtils;

public class JDefaultResourceTree extends AbstractResourceTree {

	private static final long serialVersionUID = 6480667834142873394L;

	private List<ResourceRegistry> resourceRegistryList;

	public JDefaultResourceTree() {
		super();
		resourceRegistryList = ResourceRegistryUtils.getInstance()
				.getResourceRegistryList();
	}

	@Override
	public TreeModel getResourceTreeModel() {
	  return ResourceFacade.getResourceTreeModel();
	}
	
	@Override
	public Component getComponent(TreePath path) {
		if (path == null) {
			return null;
		}

		AbstractResource resource = (AbstractResource) ((DefaultMutableTreeNode) path
				.getLastPathComponent()).getUserObject();
		if (resource == null) {
			return null;
		}
		for (ResourceRegistry registry : resourceRegistryList) {
			if (registry.getRootResouce().contains(resource)) {
				return registry.getComponent(resource);
			}
		}
		return null;
	}

}
