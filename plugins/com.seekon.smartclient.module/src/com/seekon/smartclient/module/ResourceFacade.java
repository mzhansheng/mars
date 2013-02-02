package com.seekon.smartclient.module;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.seekon.smartclient.module.internal.ResourceRegistryUtils;
import com.seekon.smartclient.module.tree.DefaultResourceTreeModel;
import com.seekon.smartclient.module.tree.JDefaultResourceTree;

public class ResourceFacade {

	private ResourceFacade() {
		super();
	}

	public static AbstractResourceTree getResourceTree() {
		return new JDefaultResourceTree();
	}

	public static TreeModel getResourceTreeModel() {
		return new DefaultResourceTreeModel(new DefaultMutableTreeNode(
				getRegistriedResource()));
	}

	public static AbstractResource getRegistriedResource() {
		Resource root = new Resource("resource.root", "资源树根");
		List<ResourceRegistry> resourceRegistryList = ResourceRegistryUtils
				.getInstance().getResourceRegistryList();
		for (ResourceRegistry registry : resourceRegistryList) {
			try {
				root.addChild((AbstractResource) registry.getRootResouce().clone(), true);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return root;
	}
}
