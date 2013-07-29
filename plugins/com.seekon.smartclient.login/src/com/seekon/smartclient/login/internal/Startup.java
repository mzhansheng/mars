package com.seekon.smartclient.login.internal;

import org.osgi.framework.BundleContext;

import com.seekon.mars.context.http.SessionContext;
import com.seekon.smartclient.login.LoginValidateor;
import com.seekon.smartclient.splash.Splash;
import com.seekon.system.auth.model.LoginInfo;

public class Startup implements com.seekon.smartclient.launcher.Startup {

  @Override
  public void run(BundleContext context) {
    Splash splash = com.seekon.smartclient.splash.SplashPlugin.getDefault()
      .getSplash();
    splash.getProgressBar().setValue(splash.getProgressBar().getMaximum());
    splash.getProgressBar().setVisible(false);

    splash.displayMessage("正在登录");
    LoginValidateor loginValidateor = new DefaultLoginValidateor();
    LoginInfo user = loginValidateor.login();
    if (user == null) {
      System.exit(1);
    }
    
    initApplicationContextSession(user);
  }

  protected static void initApplicationContextSession(LoginInfo loginInfo){    
    SessionContext.setCurrentHttpSession(loginInfo.getSessionMap());
  }
  
}
