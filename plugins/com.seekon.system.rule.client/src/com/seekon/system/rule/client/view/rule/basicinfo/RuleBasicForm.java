package com.seekon.system.rule.client.view.rule.basicinfo;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.AbstractForm;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.model.Rule;

public class RuleBasicForm extends AbstractForm<Rule> {

  private static final long serialVersionUID = -6941282289937324914L;

  private JTextField ruleNameField;

  private RuleModel model;

  public RuleBasicForm() {
    super();
    model = new RuleModel(new Rule());
    ruleNameField = BasicComponentFactory.createTextField(
      model.getModel(BindingRule.PROPERTYNAME_NAME), false);

    initComponentAnnotations();
    this.initComponents();

    updateComponentTree(model.getValidationResultModel().getResult());
    model.getValidationResultModel().addPropertyChangeListener(
      ValidationResultModel.PROPERTY_RESULT, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          updateComponentTree((ValidationResult) evt.getNewValue());
        }
      });

    setComplete(!model.getValidationResultModel().hasErrors());
    model.getValidationResultModel().addPropertyChangeListener(
      ValidationResultModel.PROPERTY_ERRORS, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          setComplete(!(Boolean) evt.getNewValue());
        }
      });
  }

  protected void initComponents() {
    JPanel pane = new JPanel(new MigLayout("align center"));
    pane.add(new JLabel("规则名称"));
    pane.add(new JLabel("<html><font color=blue>：</font></html>"));
    pane.add(ruleNameField, "w 4cm:6cm:,wrap");

    this.setLayout(new BorderLayout());
    this.add(pane, BorderLayout.CENTER);
  }

  private void initComponentAnnotations() {
    ValidationComponentUtils.setMandatory(ruleNameField, true);
  }

  private void updateComponentTree(ValidationResult result) {
    ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(this);
    ValidationComponentUtils.updateComponentTreeSeverityBackground(this, result);
  }

  @Override
  public void edit(Rule t) {
    this.model.setBean(new BindingRule(t));
  }

  public RuleModel getRuleModel() {
    return this.model;
  }

  public Rule getRule() {
    return this.model.getBean().getRule();
  }

  @Override
  public Rule save() {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Rule>() {

      @Override
      public Rule execute() throws Exception {
        Rule rule = getRule();
        ServiceFacade.getRuleService().updateRule(rule);
        return rule;
      }

      @Override
      public void success(Rule rule) {
        JOptionPane.showMessageDialog(RuleBasicForm.this, "更新成功！");
        ObserverUtil.notifyEvent(
          new Event(rule, RuleEventType.RULE_BASIC_INFO_UPDATED), RuleBasicForm.this);

      }
    });

    return getRule();
  }

}
