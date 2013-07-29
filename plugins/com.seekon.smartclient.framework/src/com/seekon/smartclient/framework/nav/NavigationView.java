package com.seekon.smartclient.framework.nav;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.seekon.smartclient.module.AbstractResourceTree;

public class NavigationView extends JPanel {

	private static final long serialVersionUID = 7877574582479993478L;

	public NavigationView() {
		super();
		init();
	}

	protected void init() {
		this.setLayout(new BorderLayout());
		
		AbstractResourceTree resourceTree = new JNavigationTree();
		resourceTree.addMouseListener(new NavigationItemDoubleClickedListener(resourceTree));
		resourceTree.setRootVisible(false);
		
		this.add(new JScrollPane(resourceTree));
	}

}
