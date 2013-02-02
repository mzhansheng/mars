package com.javadocking.model;

import java.awt.Container;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.javadocking.dock.Dock;
import com.javadocking.dock.HidableFloatDock;

/**
 * This class contains a collection of static utility methods for dock models.
 * 
 * @author Heidi Rakels.
 */
public class DockModelUtil 
{

	public static Set getVisibleFloatDocks(DockModel dockModel, Container ownerWindow) 
	{
		Set keys = dockModel.getFloatDockKeys(ownerWindow);
		Set visibleDocks = new HashSet();
		Iterator iterator = keys.iterator();
		while(iterator.hasNext()) 
		{
			String key = (String)iterator.next();
			Dock dock = dockModel.getRootDock(key);
			if (dock instanceof HidableFloatDock) 
			{
				if (!((HidableFloatDock)dock).isHidden())
				{
					visibleDocks.add(dock);
				}
			}
			else 
			{
				visibleDocks.add(dock);
			}
		}
		return visibleDocks;
	}
	
}
