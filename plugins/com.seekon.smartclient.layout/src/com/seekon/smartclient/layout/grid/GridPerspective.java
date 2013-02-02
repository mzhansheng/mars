package com.seekon.smartclient.layout.grid;

import java.awt.Container;

import com.javadocking.dock.GridDock;
import com.javadocking.dock.Position;
import com.javadocking.dockable.Dockable;
import com.seekon.smartclient.framework.AbstractPerspective;

public class GridPerspective extends AbstractPerspective{

	GridDock gridDock = new GridDock();
	
	@Override
	public void init(Container container) {
		super.init(container);
		
		gridDock.setFillMode(GridDock.FILL_FLOW_VERTICAL);
		dockModel.addRootDock("gridDock", gridDock, container);
		
		maximizer.setContent(gridDock);
	}

	@Override
	public void addDockable(Dockable dockable, Position position) {
		gridDock.addDockable(dockable, position);
	}

}
