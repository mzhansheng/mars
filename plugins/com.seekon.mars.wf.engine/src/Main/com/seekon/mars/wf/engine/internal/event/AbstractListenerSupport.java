package com.seekon.mars.wf.engine.internal.event;

import java.util.EventListener;
import java.util.Iterator;
import java.util.Vector;

import com.seekon.mars.wf.engine.event.WorkflowEvent;
import com.seekon.mars.wf.engine.event.WorkflowEventListener;
import com.seekon.mars.wf.engine.internal.Environment;

public abstract class AbstractListenerSupport {

  protected Vector listeners = new Vector();
  
  public final void addListener(EventListener listener){
    if(listener instanceof WorkflowEventListener){
      listeners.add(listener);
    }
  }
  
  public final void removeListener(EventListener listener){
    if(listener instanceof WorkflowEventListener){
      listeners.remove(listener);
    }
  }
  
  public final void clear(){
    listeners.clear();
  }
  
  protected final void fire(Object source, String id, Environment env){
    Iterator iterator = listeners.iterator();
    while(iterator.hasNext()){
      ((WorkflowEventListener)iterator.next()).onEvent(new WorkflowEvent(source, id, env));
    }
  }
}
