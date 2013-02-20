package com.seekon.smartclient.framework.util;

import java.util.List;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Action;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.model.ResourceWrapper;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.model.User;

public class AuthUtil {

  private AuthUtil() {
  }

  public static Resource getUserAuthorizedResource(String resourceId) {
    if (resourceId == null || resourceId.trim().length() == 0) {
      return null;
    }

    Resource resource = new Resource();
    resource.setId(resourceId);
    User user = (User) SessionContext.getCurrentHttpSession().get(
      SessionContext.KEY_LOGINED_CURRENT_USER);
    List<Role> roleList = user.getRoleList();
    for (Role role : roleList) {
      ResourceWrapper resourceWrapper = role.getResource();
      if (resourceWrapper != null) {
        AbstractResource userResource = resourceWrapper.getResource();
        if (userResource != null && userResource instanceof Resource) {
          Resource result = (Resource) ((Resource) userResource)
            .getContainsResource(resource);
          if (result != null) {
            return result;
          }
        }
      }
    }
    return null;
  }

  public static boolean isActionAuthorizedToUser(String resourceId, String actionId){
    Action action = new Action(actionId, null);
    return isActionAuthorizedToUser(resourceId, action);
  }
  
  public static boolean isActionAuthorizedToUser(String resourceId, Action action) {
    Resource resource = getUserAuthorizedResource(resourceId);
    return isActionAuthorizedToUser(resource, action);
  }

  public static boolean isActionAuthorizedToUser(Resource resource, Action action) {
    if (resource == null || action == null) {
      return false;
    }
    List<AbstractResource> children = resource.getChildren();
    for (AbstractResource child : children) {
      if (child instanceof Action && child.getId().equals(action.getId())) {
        return true;
      }
    }
    return false;
  }
}
