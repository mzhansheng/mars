package com.seekon.smartclient.layout.split;

import java.awt.Component;
import java.awt.Container;

import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.Dockable;
import com.seekon.smartclient.framework.AbstractPerspective;
import com.seekon.smartclient.framework.nav.NavigationView;

public class TwoColumnsPerspective extends AbstractPerspective {

	TabDock leftTabbedDock;

	TabDock rightTabbedDock;

	public TwoColumnsPerspective() {
		super();
	}

	@Override
	public void init(Container container) {

		super.init(container);

		leftTabbedDock = new TabDock();
	  rightTabbedDock = new TabDock();
	  
		SplitDock leftSplitDock = new SplitDock();
		leftSplitDock.addChildDock(leftTabbedDock, new Position(Position.CENTER));

		SplitDock rightSplitDock = new SplitDock();
		rightSplitDock.addChildDock(rightTabbedDock, new Position(Position.CENTER));

		SplitDock totalSplitDock = new SplitDock();
		totalSplitDock.addChildDock(leftSplitDock, new Position(Position.LEFT));
		totalSplitDock.addChildDock(rightSplitDock, new Position(Position.RIGHT));
		totalSplitDock.setDividerLocation(300);
		dockModel.addRootDock("totalDock", totalSplitDock, container);

		maximizer.setContent(totalSplitDock);
	}

	@Override
	public void addDockable(Dockable dockable, Position position) {
		Component compo = dockable.getContent();
		if (compo instanceof NavigationView) {// 导航组件放在左侧
			this.addDockable(leftTabbedDock, dockable, new Position());
		} else {
			this.addDockable(rightTabbedDock, dockable, new Position());
		}
	}

}
