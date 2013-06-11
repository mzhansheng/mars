package com.seekon.system.org.client.view.dept;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.org.client.module.OrgResourceRegistry;
import com.seekon.system.org.client.view.OrgEventType;
import com.seekon.system.org.model.Department;

public class DepartmentPage extends VESPage<Department> {

  private static final long serialVersionUID = 1090272091632498060L;

  private DepartmentDetail enterpriseDetail;

  @Override
  protected AbstractForm<Department> getForm() {
    return new DepartmentForm();
  }

  @Override
  protected AbstractDetail<Department> getDetail() {
    enterpriseDetail = new DepartmentDetail();
    return enterpriseDetail;
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == OrgEventType.ENT_SELECTED) {
      showViewPage();
    }
  }

  @Override
  protected boolean isEditableByAuth() {
    return AuthUtil.isActionAuthorizedToUser(OrgResourceRegistry.KEY_ORG_RESOURCE,
      ActionHelper.getEditAction());
  }
}
