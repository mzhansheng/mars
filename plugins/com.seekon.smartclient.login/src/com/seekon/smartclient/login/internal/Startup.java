package com.seekon.smartclient.login.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.smartclient.login.LoginValidateor;
import com.seekon.smartclient.splash.Splash;
import com.seekon.system.auth.model.User;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;

public class Startup implements com.seekon.smartclient.launcher.Startup {

  @Override
  public void run(BundleContext context) {
    Splash splash = com.seekon.smartclient.splash.SplashPlugin.getDefault()
      .getSplash();
    splash.getProgressBar().setValue(splash.getProgressBar().getMaximum());
    splash.getProgressBar().setVisible(false);

    splash.displayMessage("正在登录");
    LoginValidateor loginValidateor = new DefaultLoginValidateor();
    User user = loginValidateor.login();
    if (user == null) {
      System.exit(1);
    }
    
    initApplicationContextSession(user);
  }

  protected static void initApplicationContextSession(User user){
    Map<String, Object> session = new HashMap<String, Object>();
    
    session.put(SessionContext.KEY_USER_CODE, user.getUserCode());
    session.put(SessionContext.KEY_USER_NAME, user.getUserName());
    session.put(SessionContext.KEY_LOGINED_CURRENT_USER, user);
    
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
