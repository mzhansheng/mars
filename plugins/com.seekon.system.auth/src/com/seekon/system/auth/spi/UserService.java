package com.seekon.system.auth.spi;

import java.util.List;
import java.util.Map;

import com.seekon.system.auth.model.User;

public interface UserService {

  public List<User> getUserList();

  public User addUser(User user);

  public void updateUserBasicInfo(User user);

  public void updateUserRole(User user);

  public void updateUserRule(User user);

  public void deleteUsers(List<User> userList);

  public User getUser(String userId);
  
  public void updatePassword(String userId, String pwd);
  
  public void changeSession(Map<String, Object> session);
}
