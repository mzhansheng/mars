package com.seekon.smartclient.component.viewedit;

import java.io.Serializable;

import javax.swing.JComponent;

import com.seekon.smartclient.component.viewedit.event.Listener;

public abstract class Page extends JComponent implements Serializable, Listener {

  private static final long serialVersionUID = 7837168340457865710L;

  public abstract void disposePage();

  public abstract String getPageId();

  public abstract String getPageName();

}
