package com.seekon.smartclient.component.viewedit.event;

import java.awt.Component;

import javax.swing.SwingUtilities;

import com.seekon.smartclient.component.viewedit.ObserverPage;

public class ObserverUtil {

  private ObserverUtil() {
  }

  public static void notifyEvent(Event event, Component sourceComponent) {
    ObserverPage observerPage = (ObserverPage) SwingUtilities.getAncestorOfClass(
      ObserverPage.class, sourceComponent);
    observerPage.notifyEvent(event);
  }
}
