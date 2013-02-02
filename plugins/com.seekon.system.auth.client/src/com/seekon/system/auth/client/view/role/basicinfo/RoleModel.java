package com.seekon.system.auth.client.view.role.basicinfo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.AbstractValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.seekon.system.auth.model.Role;

public class RoleModel extends PresentationModel<BindingRole> {

  private static final long serialVersionUID = -6269096916091230204L;
  
  private AbstractValidationResultModel validationResultModel;

  public RoleModel(Role role) {

    super(new BindingRole(role));

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
    Role role = getBean().getRole();
    ValidationResult result = new RoleValidator().validate(role);
    validationResultModel.setResult(result);
  }

}
