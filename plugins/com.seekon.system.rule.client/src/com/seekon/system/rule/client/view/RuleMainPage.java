package com.seekon.system.rule.client.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JSplitPane;

import com.seekon.smartclient.component.viewedit.ObserverPage;
import com.seekon.smartclient.module.Resource;
import com.seekon.system.common.model.Element;
import com.seekon.system.common.model.ElementType;
import com.seekon.system.common.util.ElementRegistryUtils;
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.client.view.rule.RuleSettingContainer;
import com.seekon.system.rule.client.view.ruleset.RuleSetTreePage;
import com.seekon.system.rule.spi.RuleService;

public class RuleMainPage extends ObserverPage {

  private static final long serialVersionUID = 5464756100344680527L;

  protected RuleSetTreePage ruleSetTree;

  protected RuleSettingContainer ruleSettingContainer;
  
  private Resource resource;
  
  public RuleMainPage(Resource resource) {
    this.resource = resource;
    setLayout(new BorderLayout(0, 0));

    JSplitPane splitPane = new JSplitPane();
    add(splitPane, BorderLayout.CENTER);

    ruleSetTree = getRuleSetTree();
    splitPane.setLeftComponent(ruleSetTree);

    ruleSettingContainer = getRuleSettingContainer();
    splitPane.setRightComponent(ruleSettingContainer);

  }

  public RuleSetTreePage getRuleSetTree() {
    if (ruleSetTree == null) {
      RuleService service = ServiceFacade.getRuleService();
      ruleSetTree = new RuleSetTreePage(service.getCategory(),
        this);
    }
    return ruleSetTree;
  }

  public RuleSettingContainer getRuleSettingContainer() {
    if (ruleSettingContainer == null) {
      List<Element> elementList = ElementRegistryUtils.getInstance()
        .getAllRegistriedElementsByType(ElementType.ELE_TYPE_RULE);
      ruleSettingContainer = new RuleSettingContainer(elementList);
    }
    return ruleSettingContainer;
  }

  public Resource getResource() {
    return resource;
  }
  
}
