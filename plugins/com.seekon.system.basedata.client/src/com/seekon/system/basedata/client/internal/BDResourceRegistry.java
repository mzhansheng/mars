package com.seekon.system.basedata.client.internal;

import java.awt.Component;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.smartclient.module.Resource;
import com.seekon.smartclient.module.ResourceRegistry;

public class BDResourceRegistry implements ResourceRegistry {

  private String KEY_GENERAL_BD_MANAGER = "bd.general.manager";

  private String KEY_BD_ELEMENT = "bd.element.manager";

  private String KEY_BD_REFERENCE = "bd.reference";
  
  private String KEY_BD_RELATION = "bd.relation";
  
  @Override
  public AbstractResource getRootResouce() {
    Resource root = new Resource("app.basedata", "基础数据");
    root.addChild(getGeneralBDManager());
    root.addChild(getBDElement());
    root.addChild(new Resource(KEY_BD_REFERENCE, "参照视图", true));
    root.addChild(new Resource(KEY_BD_RELATION, "关联关系", true));
    return root;
  }

  @Override
  public Component getComponent(AbstractResource item) {
    if (KEY_GENERAL_BD_MANAGER.equals(item.getId())) {
      return new BDGeneralManager().getDbGeneralContainer();
    } else if (KEY_BD_ELEMENT.equals(item.getId())) {
      return new BDElementManager().getbdElementContainer();
    } else if (KEY_BD_REFERENCE.equals(item.getId())) {
      return null;
    }
    return null;
  }
  
  private Resource getGeneralBDManager(){
    Resource res = new Resource(KEY_GENERAL_BD_MANAGER, "通用基础数据", true);
    res.addChild(ActionHelper.getImportAction());
    res.addChild(ActionHelper.getExportAction());
    return res;
  }
  
  private Resource getBDElement(){
    Resource res = new Resource(KEY_BD_ELEMENT, "基础数据注册");
    res.addChild(ActionHelper.getWatchAction());
    res.addChild(ActionHelper.getHelpAction());
    res.addChild(ActionHelper.getSettingAction());
    return res;
  }
}
