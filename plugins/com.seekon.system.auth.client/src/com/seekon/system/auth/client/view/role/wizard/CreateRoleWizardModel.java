package com.seekon.system.auth.client.view.role.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.models.StaticModel;

import com.seekon.system.auth.model.Role;
import com.seekon.system.common.model.Category;

public class CreateRoleWizardModel extends StaticModel {

  private Role role;

  public CreateRoleWizardModel(Category category) {
    role = new Role();
    role.setCategory(category);

    initGUI();
  }

  private void initGUI() {
    if (role.getCategory() == null) {
      add(new SelectRoleCategoryWizardStep());
    }

    RoleBasicInfoWizardStep basicInfoStep = new RoleBasicInfoWizardStep();
    add(basicInfoStep);
    basicInfoStep.addPropertyChangeListener("complete",
      new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          boolean complete = (Boolean) evt.getNewValue();
          setLastAvailable(complete);
        }
      });

    add(new AssignResourceWizardStep());
    add(new AssignDatascopeWizardStep());
    add(new RoleSummaryWizardStep());
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Role getRole() {
    return role;
  }

}
