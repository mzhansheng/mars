package com.seekon.smartclient.component.viewedit;

import com.seekon.smartclient.component.viewedit.event.Event;

public abstract class AbstractPage extends Page {

  private static final long serialVersionUID = -3591751364572496483L;

  public AbstractPage() {
    ObserverPage observerPage = ObserverPage.getCurrentObserverPage();
    if(observerPage != null){
      observerPage.addListener(this);
    }
//    this.addHierarchyListener(new HierarchyListener() {
//
//      @Override
//      public void hierarchyChanged(HierarchyEvent e) {
//        Component compo = e.getComponent();
//        if (compo instanceof Listener) {
//          ObserverPage observerPage = (ObserverPage) SwingUtilities
//            .getAncestorOfClass(ObserverPage.class, compo);
//          if (observerPage != null) {
//            observerPage.addListener((Listener) compo);
//            System.out.println(compo);
//          }
//        }
//      }
//    });
  }

  @Override
  public void disposePage() {
    //Observer.getInstance().removeListener(this);
  }

  @Override
  public String getPageId() {
    return getClass().getName();
  }

  @Override
  public String getPageName() {
    return getClass().getSimpleName();
  }

  @Override
  public void update(Event event) {
    // if you want to listen to the system event,you need override this
    // method
  }

}
