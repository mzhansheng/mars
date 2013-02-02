package com.seekon.system.auth.client.view.datascope;

import java.util.List;

import com.seekon.smartclient.module.Resource;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.client.util.ElementUtil;
import com.seekon.system.auth.spi.DataScopeService;
import com.seekon.system.common.model.Element;
import com.seekon.system.rule.client.view.RuleMainPage;
import com.seekon.system.rule.client.view.rule.RuleSettingContainer;
import com.seekon.system.rule.client.view.ruleset.RuleSetTreePage;

public class DatascopeMainPage extends RuleMainPage {

  private static final long serialVersionUID = 1578641286884760433L;

  public DatascopeMainPage(Resource resource) {
    super(resource);
  }

  @Override
  public RuleSetTreePage getRuleSetTree() {
    if (ruleSetTree == null) {
      DataScopeService service = ServiceFacade.getDataScopeService();
      ruleSetTree = new RuleSetTreePage(service.getDateScopeCategory(), this);
    }
    return ruleSetTree;
  }

  @Override
  public RuleSettingContainer getRuleSettingContainer() {
    if (ruleSettingContainer == null) {
      List<Element> elementList = (List<Element>) ElementUtil.getAuthElementOption().getOptValue();
      ruleSettingContainer = new RuleSettingContainer(elementList);
    }
    return ruleSettingContainer;
  }
}
