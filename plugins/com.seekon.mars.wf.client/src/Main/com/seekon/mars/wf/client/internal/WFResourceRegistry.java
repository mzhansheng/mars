package com.seekon.mars.wf.client.internal;

import java.awt.Component;

import com.seekon.mars.wf.client.panel.TemplateManager;
import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Action;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.smartclient.module.Resource;
import com.seekon.smartclient.module.ResourceRegistry;

public class WFResourceRegistry implements ResourceRegistry{

  public static final String KEY_TEMPLATE_RESOURCE = "system.wf.template";
  
  @Override
  public AbstractResource getRootResouce() {
    Resource root = new Resource("system.workflow", "工作流");
    root.addChild(getTemplateResource());

    Resource top = new Resource("top.platform.manager", "应用平台");
    top.addChild(root);
    return top;
  }

  private Resource getTemplateResource(){
    Resource item = new Resource(KEY_TEMPLATE_RESOURCE, "模板管理", true);
    item.addChild(ActionHelper.getImportAction());
    item.addChild(ActionHelper.getExportAction());
    item.addChild(new Action("func.wf.template.designer", "模板设计"));
    return item;
  }
  
  @Override
  public Component getComponent(AbstractResource item) {
    if(item.getId().equals(KEY_TEMPLATE_RESOURCE)){
      return new TemplateManager().getTemplateContainer();
    }
    return null;
  }

}
