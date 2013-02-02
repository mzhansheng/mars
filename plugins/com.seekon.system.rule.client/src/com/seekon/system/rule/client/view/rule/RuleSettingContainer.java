package com.seekon.system.rule.client.view.rule;

import java.util.List;

import com.seekon.smartclient.component.viewedit.SwitcherPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.common.client.category.CategoryEventType;
import com.seekon.system.common.client.category.CategoryPage;
import com.seekon.system.common.model.Element;
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.client.view.RuleEventType;

public class RuleSettingContainer extends SwitcherPage {

  private static final long serialVersionUID = -5327022461453111739L;

  private RuleSettingPage ruleSettingPage;

  private CategoryPage categoryPage;
  
  public RuleSettingContainer(List<Element> elementList) {
    super();

    ruleSettingPage = new RuleSettingPage(elementList);
    categoryPage = new CategoryPage(ServiceFacade.getRuleService());
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == RuleEventType.RULE_SELECTED) {
      setPage(ruleSettingPage);
    } else if (type == CategoryEventType.CATEGORY_SELECTED) {
      setPage(categoryPage);
    }
  }

}
