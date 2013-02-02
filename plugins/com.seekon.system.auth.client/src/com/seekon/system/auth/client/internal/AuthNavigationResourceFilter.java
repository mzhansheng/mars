package com.seekon.system.auth.client.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.seekon.smartclient.framework.NavigationResourceFilter;
import com.seekon.mars.context.http.SessionContext;
import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.model.User;

public class AuthNavigationResourceFilter implements NavigationResourceFilter {

  @Override
  public AbstractResource doFilter(AbstractResource root) {
    User user = (User) SessionContext.getCurrentHttpSession().get(
      SessionContext.KEY_LOGINED_CURRENT_USER);
    if (user == null) {
      return null;
    }

    List<Role> roleList = user.getRoleList();
    //checkResourceByRoleList(root, roleList);
    return root;
  }

  private boolean checkResourceByRoleList(AbstractResource resource,
    List<Role> roleList) {
    boolean contain = false;
    for (Role role : roleList) {
      if(role.getResource() == null){
        continue;
      }
      contain = role.getResource().getResource().contains(resource);
      if (contain) {
        break;
      }
    }

    if (!contain && resource.getParent() == null) {//为根
      if (resource instanceof Resource) {
        ((Resource) resource).setChildren(new ArrayList<AbstractResource>());
      }
    }

    if (contain && resource instanceof Resource) {
      Iterator<AbstractResource> iterator = ((Resource) resource).getChildren()
        .iterator();
      while (iterator.hasNext()) {
        AbstractResource child = iterator.next();
        boolean childContain = checkResourceByRoleList(child, roleList);
        if (!childContain) {
          iterator.remove();
          child.setParent(null);
        }
      }
    }
    return contain;
  }
}
