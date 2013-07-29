package com.seekon.system.auth.spi;

import com.seekon.system.auth.model.LoginInfo;

public interface LoginService {

  public LoginInfo login(String userId, String passwd);

}
