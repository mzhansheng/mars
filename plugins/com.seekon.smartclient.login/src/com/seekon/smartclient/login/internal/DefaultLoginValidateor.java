package com.seekon.smartclient.login.internal;

import java.awt.Dialog.ModalityType;

import javax.swing.SwingUtilities;

import com.seekon.smartclient.login.LoginValidateor;
import com.seekon.smartclient.splash.Splash;
import com.seekon.smartclient.splash.SplashPlugin;
import com.seekon.system.auth.model.User;

final class DefaultLoginValidateor implements LoginValidateor {

  @Override
  public User login() {
    LoginDialog loginDialog = new LoginDialog();
    loginDialog.setModalityType(ModalityType.APPLICATION_MODAL);

    if (SplashPlugin.getDefault() != null) {
      Splash splash = SplashPlugin.getDefault().getSplash();
      if (splash != null) {
        splash.displayMessage("登录");
      }
    }
   
    return loginDialog.execute();
  }
  
  public static void  main(String[] args){
	  SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
			  DefaultLoginValidateor validator = new DefaultLoginValidateor();
			  validator.login();
		}
	});

  }
}
