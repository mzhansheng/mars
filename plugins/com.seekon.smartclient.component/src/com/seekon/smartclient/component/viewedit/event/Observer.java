package com.seekon.smartclient.component.viewedit.event;

import java.util.Enumeration;
import java.util.Vector;

public class Observer {

  //  private static Observer instance;
  //
  //  public static Observer getInstance() {
  //    if (instance == null)
  //      instance = new Observer();
  //    return instance;
  //  }

  private Vector<Listener> listeners = new Vector<Listener>();

  public void addListener(Listener listener) {
    if (!listeners.contains(listener)) {
      listeners.addElement(listener);
    }
  }

  public void removeListener(Listener listener) {
    listeners.removeElement(listener);
  }

  @SuppressWarnings("unchecked")
  public void notifyEvent(Event event) {
    Vector<Listener> temp = null;

    synchronized (this) {
      temp = (Vector<Listener>) listeners.clone();
      Enumeration<Listener> enumeration = temp.elements();
      while (enumeration.hasMoreElements()) {
        Listener lis = (Listener) enumeration.nextElement();
        lis.update(event);
      }
    }
  }
}
