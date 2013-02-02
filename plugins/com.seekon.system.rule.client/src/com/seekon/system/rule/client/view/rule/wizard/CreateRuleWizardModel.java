package com.seekon.system.rule.client.view.rule.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pietschy.wizard.models.StaticModel;

import com.seekon.system.common.client.category.AbstractSelectCategoryWizardStep;
import com.seekon.system.common.model.Category;
import com.seekon.system.rule.model.Rule;

public class CreateRuleWizardModel extends StaticModel {

  private Rule rule;

  public CreateRuleWizardModel(Category category) {
    rule = new Rule();
    rule.setCategory(category);

    initGUI();
  }

  protected void initGUI() {
    if(rule.getCategory() == null){
      add(getSelectCategoryWizardStep());
    }
    
    RuleBasicInfoWizardStep basicInfoStep = new RuleBasicInfoWizardStep();
    add(basicInfoStep);
    basicInfoStep.addPropertyChangeListener("complete",
      new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          boolean complete = (Boolean) evt.getNewValue();
          setLastAvailable(complete);
        }
      });

    add(new RuleSettingWizardStep());
    add(new RuleSummaryWizardStep());
  }

  protected AbstractSelectCategoryWizardStep getSelectCategoryWizardStep(){
    return new SelectRuleCategoryWizardStep();
  }
  
  public Rule getRule() {
    return rule;
  }

  public void setRule(Rule rule) {
    this.rule = rule;
  }

}
