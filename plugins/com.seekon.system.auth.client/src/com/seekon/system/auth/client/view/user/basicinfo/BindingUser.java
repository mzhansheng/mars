package com.seekon.system.auth.client.view.user.basicinfo;

import java.beans.PropertyChangeEvent;
import java.util.List;

import com.jgoodies.binding.beans.Model;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.model.User;

public class BindingUser extends Model {

  private static final long serialVersionUID = 881683390226917933L;

  public static final String PROPERTYNAME_USERCODE = "userCode";

  public static final String PROPERTYNAME_USERNAME = "userName";

  public static final String PROPERTYNAME_PASSWORD = "password";

  private User target;

  public BindingUser(User user) {
    this.target = user;
  }

  public String getUserCode() {
    return target.getUserCode();
  }

  public void setUserCode(String userCode) {
    String old = target.getUserCode();
    target.setUserCode(userCode);
    firePropertyChange(PROPERTYNAME_USERCODE, old, userCode);
  }

  public String getUserName() {
    return target.getUserName();
  }

  public void setUserName(String userName) {
    String old = target.getUserName();
    target.setUserName(userName);
    firePropertyChange(PROPERTYNAME_USERNAME, old, userName);
  }

  public String getPassword() {
    return target.getPassword();
  }

  public void setPassword(String password) {
    String old = target.getPassword();
    target.setPassword(password);
    firePropertyChange(new PropertyChangeEvent(this, PROPERTYNAME_PASSWORD, old,
      password));
  }

  public List<Role> getRoleList() {
    return target.getRoleList();
  }

  public void setRoleList(List<Role> roleList) {
    target.setRoleList(roleList);
  }

  public User getUser() {
    return this.target;
  }

  public String toString() {
    return getUserName();
  }

  public Object clone() throws CloneNotSupportedException {
    BindingUser clone = (BindingUser) super.clone();
    //User t = (User) target.clone();
    //clone.target = t;
    return clone;
  }

}
