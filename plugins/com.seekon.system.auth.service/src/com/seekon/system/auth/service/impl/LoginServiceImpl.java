package com.seekon.system.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.seekon.mars.context.http.SessionContext;
import com.seekon.system.auth.model.User;
import com.seekon.system.auth.service.mapper.UserMapper;
import com.seekon.system.auth.spi.LoginService;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;
import com.seekon.system.org.spi.OrgService;

public class LoginServiceImpl implements LoginService {

  @Inject
  private UserMapper userMapper;

  @Inject
  private OrgService orgService;
  
  @Override
  public User login(String userId, String passwd) {
    User user = userMapper.getUser(userId);
    AbstractOrgItem orgItem = user.getBelongedOrgItem();
    if("002".equals(orgItem.getType())){//department
      user.setBelongedOrgItem(orgService.getDepartmentById(orgItem.getId()));
    }
    initSessionContext(user);
    return user;
  }

  private void initSessionContext(User user){
    Map<String, Object> session = new HashMap<String, Object>();
    session.put(SessionContext.KEY_LOGINED_CURRENT_USER, user);
    session.put(SessionContext.KEY_USER_CODE, user.getUserCode());
    session.put(SessionContext.KEY_USER_NAME, user.getUserName());
    
    Enterprise belongedEnt = null;
    AbstractOrgItem orgItem = user.getBelongedOrgItem();
    if(orgItem instanceof Enterprise){
      belongedEnt = (Enterprise) orgItem;
    }else if(orgItem instanceof Department){
      session.put(SessionContext.KEY_DEPT_CODE, orgItem.getCode());
      session.put(SessionContext.KEY_DEPT_NAME, orgItem.getName());
      
      belongedEnt = ((Department)orgItem).getBelongedEnt();
    }
    
    if(belongedEnt != null){
      session.put(SessionContext.KEY_ENT_CODE, belongedEnt.getCode());
      session.put(SessionContext.KEY_ENT_NAME, belongedEnt.getName());
    }
    SessionContext.setCurrentHttpSession(session);
  }
}
