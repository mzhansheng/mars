package com.seekon.system.org.client.view;

import com.seekon.smartclient.component.viewedit.LabelPage;
import com.seekon.smartclient.component.viewedit.SwitcherPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.org.client.view.dept.DepartmentPage;
import com.seekon.system.org.client.view.ent.EnterprisePage;
import com.seekon.system.org.model.Enterprise;

public class OrgElementContainer extends SwitcherPage {

  private static final long serialVersionUID = 7289024261566589703L;

  private EnterprisePage enterprisePage = new EnterprisePage();

  private DepartmentPage departmentPage = new DepartmentPage();

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == OrgEventType.ENT_SELECTED) {
      this.setPage(enterprisePage);
    } else if (type == OrgEventType.DEPT_SELECTED) {
      this.setPage(departmentPage);
    } else if (type == OrgEventType.ROOT_SELECTED) {
      Enterprise root = (Enterprise) event.getSource();
      this.setPage(new LabelPage(root.getName()));
    }
  }
}
