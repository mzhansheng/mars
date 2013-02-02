package com.seekon.system.rule.client.view.rule.wizard;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.seekon.system.common.model.Element;
import com.seekon.system.common.model.ElementType;
import com.seekon.system.common.util.ElementRegistryUtils;
import com.seekon.system.rule.client.view.rule.element.RuleElementForm;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.model.RuleElement;

public class RuleSettingWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = 8923005904816565546L;

  private CreateRuleWizardModel model;

  private List<RuleElementForm> formList = new ArrayList<RuleElementForm>();

  public RuleSettingWizardStep() {
    super("规则要素配置", "依规则要素定义规则.");
  }

  @Override
  public void init(WizardModel model) {
    super.init(model);
    this.model = (CreateRuleWizardModel) model;

    Rule rule = this.model.getRule();
    JTabbedPane ruleElementPane = new JTabbedPane();
    List<Element> elementList = ElementRegistryUtils.getInstance()
      .getAllRegistriedElementsByType(ElementType.ELE_TYPE_RULE);
    for (Element element : elementList) {
      RuleElement ruleElement = rule.getRuleElementByElementCode(element.getCode());
      if (ruleElement == null) {
        ruleElement = new RuleElement();
        ruleElement.setElementCode(element.getCode());
      }
      ruleElement.setElement(element);
      ruleElement.setRule(rule);

      RuleElementForm form = new RuleElementForm();
      form.edit(ruleElement);
      formList.add(form);

      ruleElementPane.addTab(element.getName(), form);
    }

    setLayout(new BorderLayout());
    add(ruleElementPane, BorderLayout.CENTER);

    setComplete(true);
  }

  @Override
  public void applyState() throws InvalidStateException {
    Rule rule = this.model.getRule();
    for (RuleElementForm form : formList) {
      RuleElement re = form.getUpdatedRuleElement();
      if (re.getRuleSpecification() != null) {
        rule.addRuleElement(re, true);
      }
    }
  }
}
