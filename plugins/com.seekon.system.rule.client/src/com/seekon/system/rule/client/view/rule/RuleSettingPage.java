package com.seekon.system.rule.client.view.rule;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JTabbedPane;

import com.seekon.smartclient.component.viewedit.AbstractPage;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.EventType;
import com.seekon.system.common.model.Element;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.client.view.rule.basicinfo.RuleBasicPage;
import com.seekon.system.rule.client.view.rule.element.RuleElementPage;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.model.RuleElement;

public class RuleSettingPage extends AbstractPage {

  private static final long serialVersionUID = -7268754474192064116L;

  private List<Element> elementList;

  public RuleSettingPage(List<Element> elementList) {
    super();
    this.elementList = elementList;
  }

  protected void initComponents(Rule rule) {
    this.removeAll();

    this.setLayout(new BorderLayout());
    JTabbedPane mainPane = new JTabbedPane();
    this.add(mainPane, BorderLayout.CENTER);

    mainPane.addTab("基本信息", new RuleBasicPage(rule));

    JTabbedPane ruleElementPane = new JTabbedPane();
    mainPane.addTab("配置信息", ruleElementPane);

    for (Element element : elementList) {
      RuleElement ruleElement = rule.getRuleElementByElementCode(element.getCode());
      if (ruleElement == null) {
        ruleElement = new RuleElement();
        ruleElement.setElementCode(element.getCode());
      }
      ruleElement.setElement(element);
      ruleElement.setRule(rule);

      ruleElementPane.addTab(element.getName(), new RuleElementPage(ruleElement));
    }
    this.invalidate();
  }

  @Override
  public void update(Event event) {
    EventType type = event.getEventType();
    if (type == RuleEventType.RULE_SELECTED) {
      initComponents((Rule) event.getSource());
    }
  }

}
