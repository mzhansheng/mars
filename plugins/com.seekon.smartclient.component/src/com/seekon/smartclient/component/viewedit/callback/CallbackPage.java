package com.seekon.smartclient.component.viewedit.callback;

import javax.swing.Action;

import com.seekon.smartclient.component.viewedit.AbstractPage;
import com.seekon.smartclient.component.viewedit.Page;

public abstract class CallbackPage extends AbstractPage {

  private static final long serialVersionUID = 4094731631969491814L;

  public abstract void setGoBackAction(Action action);

  public abstract void setPreviousPage(Page page);

}
