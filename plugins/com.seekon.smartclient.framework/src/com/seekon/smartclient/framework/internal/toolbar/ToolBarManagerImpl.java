package com.seekon.smartclient.framework.internal.toolbar;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.javadocking.dock.BorderDock;
import com.javadocking.dock.CompositeLineDock;
import com.javadocking.dock.LineDock;
import com.javadocking.dock.Position;
import com.javadocking.dock.factory.ToolBarDockFactory;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockingMode;
import com.seekon.smartclient.framework.ToolBarManager;
import com.seekon.smartclient.framework.internal.MainFrameImpl;

public class ToolBarManagerImpl implements ToolBarManager {

	private ToolbarDockableRegistry toolbarDockableRegistry;
	private MainFrameImpl mainFrame;

	public ToolBarManagerImpl(MainFrameImpl mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	private BorderDock initToolBarBorderDock() {
		BorderDock toolbarDock = mainFrame.getCurrentPerspective().getToolBarBorderDock();
		CompositeLineDock compositeToolBarDock = new CompositeLineDock(
				CompositeLineDock.ORIENTATION_HORIZONTAL, false,
				new ToolBarDockFactory(), DockingMode.HORIZONTAL_TOOLBAR,
				DockingMode.VERTICAL_TOOLBAR);
		toolbarDock.setDock(compositeToolBarDock, Position.TOP);
		int position = 0;
		Map<String, List<Dockable>> toolbarDockables = toolbarDockableRegistry
				.getToolbarDockables();
		for (Entry<String, List<Dockable>> entry : toolbarDockables.entrySet()) {
			LineDock toolBarDock = new LineDock(
					LineDock.ORIENTATION_HORIZONTAL, false,
					DockingMode.HORIZONTAL_TOOLBAR,
					DockingMode.VERTICAL_TOOLBAR);
			int index = 0;
			List<Dockable> dockables = entry.getValue();
			for (Dockable dockable : dockables) {
				toolBarDock.addDockable(dockable, new Position(index));
				index++;
			}
			compositeToolBarDock.addChildDock(toolBarDock, new Position(
					position));
		}
		return toolbarDock;
	}

	public Map<String, List<Dockable>> getToolbarDockables(){
		return toolbarDockableRegistry.getToolbarDockables();
	}
	
	public void update() {
	  toolbarDockableRegistry = ToolbarDockableRegistry.getInstance(true);
		mainFrame.getMainPane().add(initToolBarBorderDock(), BorderLayout.CENTER);
	}

}
