package com.seekon.system.auth.spi;

import java.util.List;

import com.seekon.system.auth.model.User;

public interface UserService {

  public List<User> getUserList();

  public User addUser(User user);

  public void updateUserBasicInfo(User user);

  public void updateUserRole(User user);

  public void updateUserRule(User user);

  public void deleteUsers(List<User> userList);

}
