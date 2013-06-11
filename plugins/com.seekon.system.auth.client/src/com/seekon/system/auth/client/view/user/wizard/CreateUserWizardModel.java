package com.seekon.system.auth.client.view.user.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.models.StaticModel;

import com.seekon.system.auth.model.User;
import com.seekon.system.org.model.AbstractOrgItem;

public class CreateUserWizardModel extends StaticModel {

  private User user;

  public CreateUserWizardModel(AbstractOrgItem orgItem) {
    user = new User();
    user.setBelongedOrgItem(orgItem);

    initGUI();
  }

  private void initGUI() {
    UserBasicInfoWizardStep basicInfoStep = new UserBasicInfoWizardStep();
    add(basicInfoStep);
    basicInfoStep.addPropertyChangeListener("complete",
      new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          boolean complete = (Boolean) evt.getNewValue();
          setLastAvailable(complete);
        }
      });

    add(new AssignRoleWizardStep());
    add(new AssignDatascopeWizardStep());
    add(new SummaryWizardStep());
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

}
