package com.seekon.system.org.client.view.dept;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.org.client.view.OrgEventType;
import com.seekon.system.org.model.Department;

public class DepartmentDetail extends AbstractDetail<Department> {

  private static final long serialVersionUID = 2811491441821125032L;

  private Department enterprise;

  public void initPage(Department enterprise) {
    this.getComponents();

    this.removeAll();

    this.setLayout(new MigLayout("align center"));

    this.add(new JLabel("内部机构代码:"));
    this.add(new JLabel(enterprise.getCode()), "w 4cm:6cm:,wrap");

    this.add(new JLabel("内部机构名称"));
    this.add(new JLabel(enterprise.getName()), "growx,wrap");

    this.revalidate();

    this.enterprise = enterprise;
  }

  @Override
  public Department edit() {
    return enterprise;
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == OrgEventType.DEPT_SELECTED || type == OrgEventType.DEPT_UPDATED) {
      initPage((Department) event.getSource());
    }

  }
}
