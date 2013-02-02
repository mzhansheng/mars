package com.seekon.system.org.client.view.dept;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.AbstractValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.seekon.system.org.model.Department;

public class DepartmentModel extends PresentationModel<BindingDepartment> {

  private static final long serialVersionUID = 313460773694002066L;

  private AbstractValidationResultModel validationResultModel;

  public DepartmentModel(Department enterprise) {
    super(new BindingDepartment(enterprise));
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
    Department user = getBean().getDepartment();
    ValidationResult result = new DepartmentValidator().validate(user);
    validationResultModel.setResult(result);
  }

}
