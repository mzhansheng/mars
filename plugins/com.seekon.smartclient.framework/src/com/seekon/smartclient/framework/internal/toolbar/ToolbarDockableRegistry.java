package com.seekon.smartclient.framework.internal.toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.javadocking.DockingManager;
import com.javadocking.dockable.ButtonDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.drag.DragListener;

public class ToolbarDockableRegistry {
 
  private static final String EXTENSION_POINT_ID = "com.seekon.smartclient.framework.toolbarDocks";

  public static final String GLOBAL_GROUP_ID = "com.seekon.smartclient.framework.toolbar.globarGroupId";

  private static final String ATT_ID = "id";

  private static final String ATT_CLASS = "class";

  private static final String ATT_GROUP_ID = "groupId";

  private Map<String, List<Dockable>> toolbarDockables = new HashMap<String, List<Dockable>>();

  private static ToolbarDockableRegistry instance;

  private ToolbarDockableRegistry() {
    super();

    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    if (extensionRegistry == null) {
      return;
    }
    IConfigurationElement[] extensions = extensionRegistry
      .getConfigurationElementsFor(EXTENSION_POINT_ID);
    for (IConfigurationElement element : extensions) {
      String groupId = element.getAttribute(ATT_GROUP_ID);
      if (groupId == null || groupId.trim().length() == 0) {
        groupId = GLOBAL_GROUP_ID;
      }
      List<Dockable> compos = toolbarDockables.get(groupId);
      if (compos == null) {
        compos = new ArrayList<Dockable>();
      }

      try {
        JComponent compo = (JComponent) element.createExecutableExtension(ATT_CLASS);       
        Dockable dockable = new ButtonDockable(element.getAttribute(ATT_ID), compo);
        createDockableDragger(dockable);
        compos.add(dockable);
      } catch (Exception e) {
        e.printStackTrace();
      }
      toolbarDockables.put(groupId, compos);
    }
  }

  private void createDockableDragger(Dockable dockable) {
    DragListener dragListener = DockingManager.getDockableDragListenerFactory()
      .createDragListener(dockable);
    dockable.getContent().addMouseListener(dragListener);
    dockable.getContent().addMouseMotionListener(dragListener);
  }

  public static ToolbarDockableRegistry getInstance(boolean reload) {
    if (reload || instance == null) {
      instance = new ToolbarDockableRegistry();
    }
    return instance;
  }

  public Map<String, List<Dockable>> getToolbarDockables() {
    return toolbarDockables;
  }

}
