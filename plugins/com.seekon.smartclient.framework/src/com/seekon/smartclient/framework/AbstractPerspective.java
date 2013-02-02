package com.seekon.smartclient.framework;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.javadocking.DockingManager;
import com.javadocking.component.DefaultSwComponentFactory;
import com.javadocking.component.DockHeader;
import com.javadocking.component.PointDockHeader;
import com.javadocking.dock.BorderDock;
import com.javadocking.dock.FloatDock;
import com.javadocking.dock.LeafDock;
import com.javadocking.dock.Position;
import com.javadocking.dock.TabDock;
import com.javadocking.dock.docker.BorderDocker;
import com.javadocking.dock.factory.CompositeToolBarDockFactory;
import com.javadocking.dock.factory.ToolBarDockFactory;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockableState;
import com.javadocking.dockable.DockingMode;
import com.javadocking.dockable.StateActionDockable;
import com.javadocking.dockable.action.DefaultDockableStateAction;
import com.javadocking.dockable.action.DefaultDockableStateActionFactory;
import com.javadocking.model.FloatDockModel;
import com.javadocking.util.DockingUtil;
import com.javadocking.visualizer.DockingMinimizer;
import com.javadocking.visualizer.Externalizer;
import com.javadocking.visualizer.FloatExternalizer;
import com.javadocking.visualizer.SingleMaximizer;
import com.seekon.smartclient.framework.nav.NavigationView;

public abstract class AbstractPerspective implements Perspective {

  protected FloatDockModel dockModel;

  protected Externalizer externalizer;

  protected DockingMinimizer minimizer;

  protected SingleMaximizer maximizer;

  protected BorderDock toolBarBorderDock;

  protected BorderDock minimizerBorderDock;

  protected List<Dockable> dockableList = new ArrayList<Dockable>();
  
  @Override
  public void init(Container container) {
    DockingManager.setComponentFactory(new DefaultSwComponentFactory() {

      public static final int DEFAULT_DIVIDER_SIZE = 2;

      private int dividerSize = DEFAULT_DIVIDER_SIZE;

      public JSplitPane createJSplitPane() {
        JSplitPane splitPane = super.createJSplitPane();
        splitPane.setDividerSize(dividerSize);
        return splitPane;
      }

      public DockHeader createDockHeader(LeafDock dock, int orientation) {
        return new PointDockHeader(dock, orientation);
      }
    });

    maximizer = new SingleMaximizer();

    // Create a minimizer.
    BorderDocker borderDocker = new BorderDocker();
    minimizer = new DockingMinimizer(borderDocker);

    externalizer = new FloatExternalizer(container);

    dockModel = new FloatDockModel("workspace_1_5.dck");
    dockModel.addOwner("frame", container);

    // Give the dock model to the docking manager.
    DockingManager.setDockModel(dockModel);

    minimizerBorderDock = new BorderDock(new ToolBarDockFactory());
    minimizerBorderDock.setMode(BorderDock.MODE_MINIMIZE_BAR);
    minimizerBorderDock.setCenterComponent(maximizer);
    borderDocker.setBorderDock(minimizerBorderDock);

    dockModel.addVisualizer("minimizer", minimizer, container);
    dockModel.addVisualizer("maximizer", maximizer, container);
    dockModel.addVisualizer("externalizer", externalizer, container);

    toolBarBorderDock = new BorderDock(new CompositeToolBarDockFactory(),
      minimizerBorderDock);
    toolBarBorderDock.setMode(BorderDock.MODE_TOOL_BAR);
    dockModel.addRootDock("toolBarBorderDock", toolBarBorderDock, container);
  }

  @Override
  public BorderDock getToolBarBorderDock() {
    return toolBarBorderDock;
  }

  @Override
  public void addComponent(String id, Component compo, String title,
    Position position) {
    Dockable dockable = new DefaultDockable(id, compo, title, null, DockingMode.ALL);
    if (!(compo instanceof NavigationView) && !id.endsWith("homepage")) {
      dockable = addAllActions(dockable);
    }
    this.addDockable(dockable, position);
    dockableList.add(dockable);
  }

  protected void addDockable(LeafDock dock, Dockable dockable, Position position) {
    int count = dock.getDockableCount();
    for (int i = 0; i < count; i++) {
      Dockable tmp = dock.getDockable(i);
      if (tmp.getID().equals(dockable.getID())) {
        if (dock instanceof TabDock) {
          ((TabDock) dock).setSelectedDockable(tmp);
        }
        return;
      }
    }

    count = minimizer.getVisualizedDockableCount();
    for (int i = 0; i < count; i++) {
      Dockable tmp = minimizer.getVisualizedDockable(i);
      if (tmp.getID().equals(dockable.getID())) {
        restoreDockable(tmp);
        return;
      }
    }

    count = externalizer.getVisualizedDockableCount();
    for (int i = 0; i < count; i++) {
      Dockable tmp = externalizer.getVisualizedDockable(i);
      if (tmp.getID().equals(dockable.getID())) {
        restoreDockable(tmp);
        return;
      }
    }

    dock.addDockable(dockable, new Position());
  }

  protected Dockable addAllActions(Dockable dockable) {
    Dockable wrapper = new StateActionDockable(dockable,
      new DefaultDockableStateActionFactory(), DockableState.statesClosed());
    wrapper = new StateActionDockable(wrapper,
      new DefaultDockableStateActionFactory(), DockableState.statesAllExceptClosed());
    return wrapper;
  }

  protected void restoreDockable(Dockable dockable) {
    Action restoreAction = new DefaultDockableStateAction(
      DockingUtil.retrieveDockableOfDockModel(dockable.getID()),
      DockableState.NORMAL);
    restoreAction.actionPerformed(new ActionEvent(this,
      ActionEvent.ACTION_PERFORMED, "Restore"));
  }

  public void changeLookAndFeel(String lookAndFeelClassName) throws Exception {
    for (int index = 0; index < dockModel.getOwnerCount(); index++) {

      // Set the LaF on the owner.
      Container owner = dockModel.getOwner(index);
      SwingUtilities.updateComponentTreeUI(owner);

      // Set the Laf on the floating windows.
      FloatDock floatDock = dockModel.getFloatDock(owner);
      if (floatDock != null) {
        for (int childIndex = 0; childIndex < floatDock.getChildDockCount(); childIndex++) {
          Component floatingComponent = (Component) floatDock
            .getChildDock(childIndex);
          SwingUtilities.updateComponentTreeUI(SwingUtilities
            .getWindowAncestor(floatingComponent));
        }
      }
      for(Dockable dockable : dockableList){
        SwingUtilities.updateComponentTreeUI(dockable.getContent());
      }
      //      // Set the LaF on all the dockable components.
      //      for (int dockableIndex = 0; dockableIndex < dockables.length; dockableIndex++)
      //      {
      //        SwingUtilities.updateComponentTreeUI(dockables[dockableIndex].getContent());
      //      }
      //      for (int dockableIndex = 0; dockableIndex < buttonDockables.length; dockableIndex++)
      //      {
      //        SwingUtilities.updateComponentTreeUI(buttonDockables[dockableIndex].getContent());
      //      }

    }
  }
}
