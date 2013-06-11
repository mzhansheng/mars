package com.seekon.smartclient.module.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Action;
import com.seekon.smartclient.module.Resource;

public class DefaultResourceTreeModel extends DefaultTreeModel {

	private static final long serialVersionUID = 7819638863504539760L;

	public DefaultResourceTreeModel(TreeNode root) {
		super(root);
	}

	@Override
	public Object getChild(Object parent, int index) {
		AbstractResource resource = (AbstractResource) ((DefaultMutableTreeNode) parent)
				.getUserObject();
		if (resource instanceof Action) {
			return null;
		} else if (resource instanceof Resource) {
			return new DefaultResourceTreeNode(((Resource) resource).getChild(index));
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		AbstractResource resource = (AbstractResource) ((DefaultMutableTreeNode) parent)
				.getUserObject();
		if (resource instanceof Action) {
			return 0;
		} else if (resource instanceof Resource) {
			return ((Resource) resource).getChildCount();
		}
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		return this.getChildCount(node) == 0;
	}
}
