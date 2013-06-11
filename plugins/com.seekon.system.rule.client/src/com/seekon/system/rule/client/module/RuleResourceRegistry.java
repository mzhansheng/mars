package com.seekon.system.rule.client.module;

import java.awt.Component;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.smartclient.module.Resource;
import com.seekon.smartclient.module.ResourceRegistry;
import com.seekon.system.rule.client.view.RuleMainPage;

public class RuleResourceRegistry implements ResourceRegistry {

  public static final String KEY_RULE_RESOURCE = "system.rule";
  
  @Override
  public AbstractResource getRootResouce() {
    Resource top = new Resource("top.platform.manager", "应用平台");

    Resource root = new Resource(KEY_RULE_RESOURCE, "规则设置");

    root.addChild(ActionHelper.getAddAction());
    root.addChild(ActionHelper.getDeleteAction());
    root.addChild(ActionHelper.getEditAction());
    root.addChild(ActionHelper.getWatchAction());
    root.addChild(ActionHelper.getHelpAction());
    
    top.addChild(root);
    return top;
  }

  @Override
  public Component getComponent(AbstractResource item) {
    if (KEY_RULE_RESOURCE.equals(item.getId())) {
      return new RuleMainPage((Resource) item);
    }
    return null;
  }

}
