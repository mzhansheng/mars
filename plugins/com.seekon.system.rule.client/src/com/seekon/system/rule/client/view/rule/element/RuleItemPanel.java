package com.seekon.system.rule.client.view.rule.element;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.seekon.mars.dictionary.model.FieldMeta;
import com.seekon.mars.rule.engine.specification.IRuleSpecification;
import com.seekon.mars.rule.engine.specification.ext.AbstractCustomRuleSpecification;
import com.seekon.mars.rule.engine.specification.ext.EqualsRuleSpecification;

public class RuleItemPanel extends JPanel {

  private static final long serialVersionUID = -3473826985753856480L;

  private JComboBox operator;

  private JTextField valueField;

  private FieldMeta fieldMeta;

  private AbstractCustomRuleSpecification ruleSpecification;

  private boolean edit;

  public RuleItemPanel(FieldMeta fieldMeta,
    AbstractCustomRuleSpecification ruleSpecification, boolean edit) {
    super();
    this.fieldMeta = fieldMeta;
    this.edit = edit;
    this.ruleSpecification = ruleSpecification;
    initComponents();
  }

  private void initComponents() {
    this.setLayout(new MigLayout());
    this.add(new JLabel(fieldMeta.getColName()));

    operator = this.getOperateComponent(fieldMeta, edit);
    this.add(operator);

    valueField = getValueField(edit);
    this.add(valueField, "w 4cm:6cm:");
  }

  protected JTextField getValueField(boolean edit) {
    JTextField valueField = new JTextField();
    valueField.setEnabled(edit);
    valueField.setEditable(edit);
    if (ruleSpecification != null) {
      valueField.setText(ruleSpecification.getTarget().toString());
    }
    return valueField;
  }

  protected JComboBox getOperateComponent(FieldMeta field, boolean edit) {
    DefaultComboBoxModel model = new DefaultComboBoxModel();
    JComboBox comboBox = new JComboBox(model);
    String colType = field.getColType();
    if ("text".equalsIgnoreCase(colType)) {
      model.addElement(new OperateItem(new EqualsRuleSpecification(field, ""), "等于"));
    }

    if (ruleSpecification != null) {
      int size = model.getSize();
      for (int i = 0; i < size; i++) {
        OperateItem item = (OperateItem) model.getElementAt(i);
        if (item.ruleSpecification.getClass() == ruleSpecification.getClass()) {
          comboBox.setSelectedItem(item);
          continue;
        }
      }
    }

    comboBox.setEditable(edit);
    comboBox.setEnabled(edit);
    return comboBox;
  }

  public IRuleSpecification getSelectedRuleSpecification() {
    String value = valueField.getText();
    if (value == null || value.trim().length() == 0) {
      return null;
    }
    OperateItem selectedOperater = (OperateItem) operator.getSelectedItem();
    AbstractCustomRuleSpecification ruleSpecification = selectedOperater.ruleSpecification;
    ruleSpecification.setTarget(value);
    return ruleSpecification;
  }

  class OperateItem {

    AbstractCustomRuleSpecification ruleSpecification;

    String name;

    public OperateItem(AbstractCustomRuleSpecification ruleSpecification, String name) {
      super();
      this.ruleSpecification = ruleSpecification;
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }
}
