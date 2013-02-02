package com.seekon.smartclient.framework;

import java.util.List;
import java.util.Map;

import com.javadocking.dockable.Dockable;

public interface ToolBarManager {

	public Map<String, List<Dockable>> getToolbarDockables();
	
	public void update();
}
