package com.seekon.smartclient.component.viewedit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractForm<T> extends AbstractPage {

  private boolean complete;

  private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  public void setComplete(boolean complete) {
    boolean oldValue = this.complete;
    this.complete = complete;
    pcs.firePropertyChange(new PropertyChangeEvent(this, "complete", oldValue,
      complete));
  }

  public void addCompleteListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener("complete", listener);
  }

  public void removeCompleteListener(PropertyChangeListener listener) {
    pcs.removePropertyChangeListener("complete", listener);
  }

  public boolean isComplete() {
    return complete;
  }

  public abstract void edit(T t);

  public abstract T save();

}
