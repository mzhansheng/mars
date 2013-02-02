package com.seekon.system.org.client.view.ent;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.VESPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.framework.util.AuthUtil;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.system.org.client.module.OrgResourceRegistry;
import com.seekon.system.org.client.view.OrgEventType;
import com.seekon.system.org.model.Enterprise;

public class EnterprisePage extends VESPage<Enterprise> {

  private static final long serialVersionUID = 1090272091632498060L;

  private EnterpriseDetail enterpriseDetail;

  @Override
  protected AbstractForm<Enterprise> getForm() {
    return new EnterpriseForm();
  }

  @Override
  protected AbstractDetail<Enterprise> getDetail() {
    enterpriseDetail = new EnterpriseDetail();
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
