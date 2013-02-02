package com.seekon.smartclient.component.viewedit;

import javax.swing.JComponent;

import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.Listener;
import com.seekon.smartclient.component.viewedit.event.Observer;

public class ObserverPage extends JComponent {

  private static final long serialVersionUID = 5579269074062842632L;

  private Observer observer = new Observer();

  private static final ThreadLocal<ObserverPage> currentObserverPage = new ThreadLocal<ObserverPage>();

  public ObserverPage() {
    super();
    currentObserverPage.set(this);
  }

  public static ObserverPage getCurrentObserverPage(){//TODO
    return currentObserverPage.get();
  }
  
  public void notifyEvent(Event event) {
    observer.notifyEvent(event);
  }

  public void addListener(Listener listener) {
    observer.addListener(listener);
  }

  public void removeListener(Listener listener) {
    observer.removeListener(listener);
  }

}
