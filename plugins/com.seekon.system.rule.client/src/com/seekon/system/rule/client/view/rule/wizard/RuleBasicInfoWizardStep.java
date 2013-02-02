package com.seekon.system.rule.client.view.rule.wizard;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;

import com.jgoodies.validation.ValidationResultModel;
import com.seekon.system.rule.client.view.rule.basicinfo.RuleBasicForm;
import com.seekon.system.rule.model.Rule;

public class RuleBasicInfoWizardStep extends PanelWizardStep {

  private static final long serialVersionUID = 3557825828057223898L;

  private CreateRuleWizardModel wizardModel;

  private RuleBasicForm form;

  public RuleBasicInfoWizardStep() {
    super("规则基本信息", "请输入规则名称等信息");
  }

  @Override
  public void init(WizardModel model) {
    super.init(model);
    this.wizardModel = (CreateRuleWizardModel) model;

    form = new RuleBasicForm();
    setLayout(new BorderLayout());
    add(form, BorderLayout.CENTER);

    form
      .getRuleModel()
      .getValidationResultModel()
      .addPropertyChangeListener(ValidationResultModel.PROPERTY_ERRORS,
        new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent evt) {
            setComplete(!(Boolean) evt.getNewValue());
          }
        });

    setComplete(false);
  }

  @Override
  public void applyState() throws InvalidStateException {
    Rule rule = form.getRule();
    rule.setCategory(this.wizardModel.getRule().getCategory());
    this.wizardModel.setRule(rule);
  }
}
