package com.seekon.smartclient.component.viewedit.event;

import java.util.EventObject;

public class Event extends EventObject {

  private static final long serialVersionUID = -2105446305093559763L;

  private EventType eventType;

  public Event(Object source) {
    super(source);
  }

  public Event(Object source, EventType eventType) {
    super(source);
    this.eventType = eventType;
  }

  public EventType getEventType() {
    return eventType;
  }

}
