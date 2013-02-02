package com.seekon.system.rule.client.internal;

import com.seekon.system.rule.spi.RuleService;

public class ServiceFacade {

  public static RuleService getRuleService() {
    return RulePlugin.injector.getInstance(RuleService.class);
  }
}
