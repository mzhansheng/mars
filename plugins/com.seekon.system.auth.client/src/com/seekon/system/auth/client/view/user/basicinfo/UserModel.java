package com.seekon.system.auth.client.view.user.basicinfo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.AbstractValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.seekon.system.auth.model.User;

public class UserModel extends PresentationModel<BindingUser> {

  private static final long serialVersionUID = 313460773694002066L;

  private AbstractValidationResultModel validationResultModel;

  public UserModel(User user) {
    super(new BindingUser(user));
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
    User user = getBean().getUser();
    ValidationResult result = new UserValidator().validate(user);
    validationResultModel.setResult(result);
  }

}
