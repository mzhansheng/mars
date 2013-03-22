package com.seekon.system.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.model.User;
import com.seekon.system.auth.service.mapper.UserMapper;
import com.seekon.system.auth.spi.UserService;
import com.seekon.system.rule.model.Rule;

public class UserServiceImpl implements UserService {

  @Inject
  private UserMapper userMapper;

  @Override
  public List<User> getUserList() {
    return userMapper.getUserList();
  }

  @Override
  @Transactional
  public User addUser(User user) {
    user.setId(UUID.randomUUID().toString());
    userMapper.addUserBasicInfo(user);
    this.addUserRole(user);
    this.addUserRule(user);
    return user;
  }

  @Override
  @Transactional
  public void updateUserBasicInfo(User user) {
    userMapper.updateUserBasicInfo(user);
  }

  @Override
  @Transactional
  public void updateUserRole(User user) {
    userMapper.deleteUserRoleInfo(user);
    addUserRole(user);
  }

  @Override
  @Transactional
  public void updateUserRule(User user) {
    userMapper.deleteUserRuleInfo(user);
    addUserRule(user);
  }

  @Transactional
  private void addUserRole(User user) {
    List<Role> roleList = user.getRoleList();
    if (roleList != null && !roleList.isEmpty()) {
      for (Role role : roleList) {
        Map<String, String> userRole = new HashMap<String, String>();
        userRole.put("id", UUID.randomUUID().toString());
        userRole.put("userId", user.getId());
        userRole.put("roleId", role.getId());
        userMapper.addUserRole(userRole);
      }
    }
  }

  @Transactional
  private void addUserRule(User user) {
    List<Rule> ruleList = user.getDatascopeRuleList();
    for (Rule rule : ruleList) {
      Map<String, String> userRule = new HashMap<String, String>();
      userRule.put("id", UUID.randomUUID().toString());
      userRule.put("userId", user.getId());
      userRule.put("ruleId", rule.getId());
      userMapper.addUserRule(userRule);
    }

  }

  @Override
  @Transactional
  public void deleteUsers(List<User> userList) {
    for (User user : userList) {
      userMapper.deleteUserBasicInfo(user);
      userMapper.deleteUserRoleInfo(user);
      userMapper.deleteUserRuleInfo(user);
    }
  }

  @Override
  public User getUser(String userId) {
    return userMapper.getUser(userId);
  }
  
  @Override
  public void updatePassword(String userId, String pwd) {
    User user = new User();
    user.setId(userId);
    user.setPassword(pwd);
    userMapper.updatePassword(user);
  }
}
