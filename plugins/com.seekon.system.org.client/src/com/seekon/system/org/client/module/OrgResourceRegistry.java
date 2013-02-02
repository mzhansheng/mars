package com.seekon.system.org.client.module;

import java.awt.Component;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.smartclient.module.Resource;
import com.seekon.smartclient.module.ResourceRegistry;
import com.seekon.system.org.client.view.OrganizationPage;

public class OrgResourceRegistry implements ResourceRegistry {

  public static final String KEY_ORG_RESOURCE = "system.org";

  @Override
  public Component getComponent(AbstractResource item) {
    if (KEY_ORG_RESOURCE.equals(item.getId())) {
      return new OrganizationPage();
    }
    return null;
  }

  @Override
  public AbstractResource getRootResouce() {
    Resource org = new Resource(KEY_ORG_RESOURCE, "组织机构");

    org.addChild(ActionHelper.getAddAction());
    org.addChild(ActionHelper.getDeleteAction());
    org.addChild(ActionHelper.getEditAction());
    org.addChild(ActionHelper.getWatchAction());
    org.addChild(ActionHelper.getHelpAction());

    Resource root = new Resource("top.platform.manager", "应用平台");
    root.addChild(org);
    return root;
  }

}
