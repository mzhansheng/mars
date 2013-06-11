package com.seekon.system.rule.client.view.rule.element;

import java.awt.BorderLayout;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.model.RuleElement;

public class RuleElementDetail extends AbstractDetail<RuleElement> {

  private static final long serialVersionUID = -70530530362116965L;

  private RuleElement ruleElement;

  public RuleElementDetail() {
    super();
  }

  public void initPage(RuleElement ruleElement) {
    this.ruleElement = ruleElement;
    this.setLayout(new BorderLayout());
    this.add(new RuleContentPanel(ruleElement, false));
  }

  @Override
  public RuleElement edit() {
    return ruleElement;
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == RuleEventType.RULE_ELEMENT_UPDATED) {
      RuleElement ruleElement = (RuleElement) event.getSource();
      if (ruleElement.getElement().equals(this.ruleElement.getElement())) {
        this.removeAll();
        this.add(new RuleContentPanel(ruleElement, false));
        this.invalidate();
      }
    }
  }

}
