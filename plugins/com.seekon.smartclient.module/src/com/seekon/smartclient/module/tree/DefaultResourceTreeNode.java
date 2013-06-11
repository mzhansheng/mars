package com.seekon.smartclient.module.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public class DefaultResourceTreeNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 7831004139865812612L;
	
	
	public DefaultResourceTreeNode() {
		super();
	}


	public DefaultResourceTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}


	public DefaultResourceTreeNode(Object userObject) {
		super(userObject);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(obj instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode tmp = (DefaultMutableTreeNode)obj;
			if(this.getUserObject() != null){
				return this.getUserObject().equals(tmp.getUserObject());
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		if(this.getUserObject() != null){
			return this.getUserObject().hashCode();
		}
		return super.hashCode();
	}
}
