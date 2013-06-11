package com.seekon.smartclient.module;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public abstract class AbstractResourceTree extends JTree implements Cloneable {

	private static final long serialVersionUID = 1534475845907470154L;

	public AbstractResourceTree() {
		super();

		this.setModel(getResourceTreeModel());
	}

	public abstract TreeModel getResourceTreeModel();
	
	public abstract Component getComponent(TreePath path);

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
