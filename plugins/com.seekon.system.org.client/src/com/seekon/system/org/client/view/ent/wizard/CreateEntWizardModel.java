package com.seekon.system.org.client.view.ent.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.models.StaticModel;

import com.seekon.system.org.model.Enterprise;

public class CreateEntWizardModel extends StaticModel {

  private Enterprise enterprise;

  public CreateEntWizardModel(Enterprise parent) {
    enterprise = new Enterprise();
    enterprise.setParent(parent);

    initGUI();
  }

  private void initGUI() {
    EntInfoWizardStep basicInfoStep = new EntInfoWizardStep();
    add(basicInfoStep);
    basicInfoStep.addPropertyChangeListener("complete",
      new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          boolean complete = (Boolean) evt.getNewValue();
          setLastAvailable(complete);
        }
      });

    add(new EntSummaryWizardStep());
  }

  public Enterprise getEnterprise() {
    return enterprise;
  }

  public void setEnterprise(Enterprise enterprise) {
    this.enterprise = enterprise;
  }

}
