package com.seekon.system.rule.client.view.rule.element;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.model.RuleElement;
import com.seekon.system.rule.spi.RuleService;

public class RuleElementForm extends AbstractForm<RuleElement> {

  private static final long serialVersionUID = -4145398732202556811L;

  private RuleElement ruleElement;

  private RuleContentPanel ruleContentPane;

  @Override
  public void edit(RuleElement t) {
    this.ruleElement = t;
    if (ruleContentPane == null) {
      this.setLayout(new BorderLayout());
      ruleContentPane = new RuleContentPanel(ruleElement, true);
      this.add(ruleContentPane, BorderLayout.CENTER);
    }
    this.setComplete(true);
  }

  @Override
  public RuleElement save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<RuleElement>() {

      @Override
      public RuleElement execute() throws Exception {
        RuleService service = ServiceFacade.getRuleService();
        RuleElement old = ruleElement.getRule().getRuleElementByElementCode(
          ruleElement.getElementCode());
        ruleElement = getUpdatedRuleElement();
        if (old == null) {
          ruleElement = service.addRuleElement(ruleElement);
        } else {
          service.updateRuleElement(ruleElement);
        }

        return ruleElement;
      }

      @Override
      public void success(RuleElement ruleElement) {
        ruleElement.getRule().addRuleElement(ruleElement, true);
        JOptionPane.showMessageDialog(RuleElementForm.this, "保存成功！");
        ObserverUtil.notifyEvent(
          new Event(ruleElement, RuleEventType.RULE_ELEMENT_UPDATED), RuleElementForm.this);
      }
    });

    return ruleElement;
  }

  public RuleElement getUpdatedRuleElement() {
    return this.ruleContentPane.getUpdatedRuleElement();
  }
}
