package com.seekon.system.rule.client.view.rule.basicinfo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.AbstractValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.seekon.system.rule.model.Rule;

public class RuleModel extends PresentationModel<BindingRule> {

  private static final long serialVersionUID = -8743703498096215109L;

  private AbstractValidationResultModel validationResultModel;

  public RuleModel(Rule rule) {

    super(new BindingRule(rule));

    validationResultModel = new DefaultValidationResultModel();
    PropertyChangeListener listener = new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        updateValidationResult();
      }
    };
    addBeanPropertyChangeListener(listener);
    getBeanChannel().addValueChangeListener(listener);
    updateValidationResult();
  }

  public AbstractValidationResultModel getValidationResultModel() {
    return validationResultModel;
  }

  private void updateValidationResult() {
    Rule rule = getBean().getRule();
    ValidationResult result = new RuleValidator().validate(rule);
    validationResultModel.setResult(result);
  }
}
