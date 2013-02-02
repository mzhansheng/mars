package com.seekon.system.org.client.view.ent;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.org.client.view.OrgEventType;
import com.seekon.system.org.model.Enterprise;

public class EnterpriseDetail extends AbstractDetail<Enterprise> {

  private static final long serialVersionUID = 2811491441821125032L;

  private Enterprise enterprise;

  public void initPage(Enterprise enterprise) {
    this.getComponents();

    this.removeAll();

    this.setLayout(new MigLayout("align center"));

    this.add(new JLabel("单位代码:"));
    this.add(new JLabel(enterprise.getCode()), "w 4cm:6cm:,wrap");

    this.add(new JLabel("单位名称"));
    this.add(new JLabel(enterprise.getName()), "growx,wrap");

    this.revalidate();

    this.enterprise = enterprise;
  }

  @Override
  public Enterprise edit() {
    return enterprise;
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == OrgEventType.ENT_SELECTED || type == OrgEventType.ENT_UPDATED) {
      initPage((Enterprise) event.getSource());
    }

  }
}
