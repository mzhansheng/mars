package com.seekon.system.basedata.client.internal;

import java.awt.Component;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Resource;
import com.seekon.smartclient.module.ResourceRegistry;

public class BDResourceRegistry implements ResourceRegistry {

  private String KEY_GENERAL_BD_MANAGER = "bd.general.manager";

  private String KEY_BD_ELEMENT = "db.element.manager";

  private String KEY_BD_REFERENCE = "db.reference";

  @Override
  public AbstractResource getRootResouce() {
    Resource root = new Resource("app.basedata", "基础数据");
    root.addChild(new Resource(KEY_GENERAL_BD_MANAGER, "通用基础数据"));
    root.addChild(new Resource(KEY_BD_ELEMENT, "基础数据注册"));
    root.addChild(new Resource(KEY_BD_REFERENCE, "参照视图"));
    return root;
  }

  @Override
  public Component getComponent(AbstractResource item) {
    if (KEY_GENERAL_BD_MANAGER.equals(item.getId())) {
      return BDGeneralManager.dbGeneralManager.getDbGeneralContainer();
    } else if (KEY_BD_ELEMENT.equals(item.getId())) {
      return BDElementManager.bdElementManager.getbdElementContainer();
    } else if (KEY_BD_REFERENCE.equals(item.getId())) {
      return null;
    }
    return null;
  }

}
