package com.seekon.system.rule.client.view.rule.basicinfo;

import com.jgoodies.binding.beans.Model;
import com.seekon.system.rule.model.Rule;

public class BindingRule extends Model {

  private static final long serialVersionUID = 4763684110386795871L;

  public static final String PROPERTYNAME_NAME = "ruleName";

  public static final String PROPERTYNAME_ID = "id";

  private Rule target;

  public BindingRule(Rule target) {
    super();
    this.target = target;
  }

  public String getId() {
    return target.getId();
  }

  public void setId(String id) {
    String old = this.target.getId();
    target.setId(id);
    firePropertyChange(PROPERTYNAME_ID, old, id);
  }

  public String getRuleName() {
    return target.getRuleName();
  }

  public void setRuleName(String ruleName) {
    String old = this.target.getRuleName();
    target.setRuleName(ruleName);
    firePropertyChange(PROPERTYNAME_NAME, old, ruleName);
  }

  public Rule getRule() {
    return this.target;
  }

  @Override
  public String toString() {
    return this.target.toString();
  }
}
