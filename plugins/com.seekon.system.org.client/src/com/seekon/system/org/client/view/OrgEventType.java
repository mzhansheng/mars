package com.seekon.system.org.client.view;

import com.seekon.smartclient.component.viewedit.event.EventType;

public enum OrgEventType implements EventType {

  NONE_SELECTED, ROOT_SELECTED,

  ENT_SELECTED, ENT_UPDATED, ENT_DELETE, ENT_ADDED,

  DEPT_SELECTED, DEPT_UPDATED, DEPT_DELETE, DEPT_ADDED
}
