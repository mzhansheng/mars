package com.seekon.system.org.client.view.dept.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.models.StaticModel;

import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;

public class CreateDeptWizardModel extends StaticModel {

  private Department department;

  public CreateDeptWizardModel(Enterprise parent) {
    department = new Department();
    department.setBelongedEnt(parent);

    initGUI();
  }

  private void initGUI() {
    DeptInfoWizardStep basicInfoStep = new DeptInfoWizardStep();
    add(basicInfoStep);
    basicInfoStep.addPropertyChangeListener("complete",
      new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          boolean complete = (Boolean) evt.getNewValue();
          setLastAvailable(complete);
        }
      });

    add(new DeptSummaryWizardStep());
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

}
