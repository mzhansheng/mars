package com.seekon.system.auth.spi;

import com.seekon.system.auth.model.User;

public interface LoginService {

  public User login(String userId, String passwd);

}
