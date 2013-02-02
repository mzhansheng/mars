package com.seekon.system.org.client.view.ent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.AbstractValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.seekon.system.org.model.Enterprise;

public class EnterpriseModel extends PresentationModel<BindingEnterprise> {

  private static final long serialVersionUID = 313460773694002066L;

  private AbstractValidationResultModel validationResultModel;

  public EnterpriseModel(Enterprise enterprise) {
    super(new BindingEnterprise(enterprise));
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
    Enterprise user = getBean().getEnterprise();
    ValidationResult result = new EnterpriseValidator().validate(user);
    validationResultModel.setResult(result);
  }

}
