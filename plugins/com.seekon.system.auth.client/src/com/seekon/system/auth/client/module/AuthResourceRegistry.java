package com.seekon.system.auth.client.module;

import java.awt.Component;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.ActionHelper;
import com.seekon.smartclient.module.Resource;
import com.seekon.smartclient.module.ResourceRegistry;
import com.seekon.system.auth.client.view.datascope.DatascopeMainPage;
import com.seekon.system.auth.client.view.element.ElementManager;
import com.seekon.system.auth.client.view.role.RolePage;
import com.seekon.system.auth.client.view.user.UserPage;

public class AuthResourceRegistry implements ResourceRegistry {

  public static final String KEY_ROLE_RESOURCE = "system.auth.role";

  public static final String KEY_USER_RESOURCE = "system.auth.user";

  public static final String KEY_ELEMENT_RESOURCE = "system.auth.element";

  public static final String KEY_DATA_SCOPE_RESOURCE = "system.auth.data.scope";

  @Override
  public Component getComponent(AbstractResource item) {
    if (KEY_ROLE_RESOURCE.equals(item.getId())) {
      return new RolePage((Resource) item);
    } else if (KEY_USER_RESOURCE.equals(item.getId())) {
      return new UserPage((Resource) item);
    } else if (KEY_ELEMENT_RESOURCE.equals(item.getId())) {
      return new ElementManager((Resource) item);
    } else if (KEY_DATA_SCOPE_RESOURCE.equals(item.getId())) {
      return new DatascopeMainPage((Resource) item);
    }
    return null;
  }

  @Override
  public AbstractResource getRootResouce() {
    Resource root = new Resource("system.auth", "权限管理");
    root.addChild(getAuthElementResource());
    root.addChild(getDatasopeResource());
    root.addChild(getRoleResource());
    root.addChild(getUserResource());

    Resource top = new Resource("top.platform.manager", "应用平台");
    top.addChild(root);
    return top;
  }

  private Resource getUserResource() {
    Resource user = new Resource(KEY_USER_RESOURCE, "人员", true);
//    user.addChild(ActionHelper.getAddAction());
//    user.addChild(ActionHelper.getDeleteAction());
//    user.addChild(ActionHelper.getEditAction());
//    user.addChild(ActionHelper.getWatchAction());
//    user.addChild(ActionHelper.getHelpAction());
    return user;
  }

  private Resource getRoleResource() {
    Resource role = new Resource(KEY_ROLE_RESOURCE, "角色", true);
//    role.addChild(ActionHelper.getAddAction());
//    role.addChild(ActionHelper.getDeleteAction());
//    role.addChild(ActionHelper.getEditAction());
//    role.addChild(ActionHelper.getWatchAction());
//    role.addChild(ActionHelper.getHelpAction());
    return role;
  }

  private Resource getDatasopeResource() {
    Resource dataScope = new Resource(KEY_DATA_SCOPE_RESOURCE, "数据范围", true);
//    dataScope.addChild(ActionHelper.getAddAction());
//    dataScope.addChild(ActionHelper.getDeleteAction());
//    dataScope.addChild(ActionHelper.getEditAction());
//    dataScope.addChild(ActionHelper.getWatchAction());
//    dataScope.addChild(ActionHelper.getHelpAction());
    return dataScope;
  }

  private Resource getAuthElementResource() {
    Resource element = new Resource(KEY_ELEMENT_RESOURCE, "权限要素");
    element.addChild(ActionHelper.getSettingAction());
    element.addChild(ActionHelper.getWatchAction());
    element.addChild(ActionHelper.getHelpAction());
    return element;
  }

}
