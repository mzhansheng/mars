package com.seekon.system.auth.service.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.seekon.system.auth.model.User;

public interface UserMapper {

  public List<User> getUserList();

  public User getUser(@Param("userId")
  String userId);

  public void addUserBasicInfo(@Param("user")
  User user);

  public void addUserRole(@Param("userRole")
  Map<String, String> userRole);

  public void addUserRule(@Param("userRule")
  Map<String, String> userRule);

  public void updateUserBasicInfo(@Param("user")
  User user);

  public void deleteUserBasicInfo(@Param("user")
  User user);

  public void deleteUserRoleInfo(@Param("user")
  User user);

  public void deleteUserRuleInfo(@Param("user")
  User user);

  public void updatePassword(@Param("user")
  User user);
}
