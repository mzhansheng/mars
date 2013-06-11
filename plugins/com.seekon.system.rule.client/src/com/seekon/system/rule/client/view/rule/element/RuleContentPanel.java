package com.seekon.system.rule.client.view.rule.element;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.mars.rule.engine.specification.AndRuleSpecification;
import com.seekon.mars.rule.engine.specification.CompositeRuleSpecification;
import com.seekon.mars.rule.engine.specification.IRuleSpecification;
import com.seekon.mars.rule.engine.specification.Visitor;
import com.seekon.mars.rule.engine.specification.ext.AbstractCustomRuleSpecification;
import com.seekon.mars.rule.engine.specification.ext.TrueSpecification;
import com.seekon.mars.rule.engine.specification.visitor.FindSpecificationVistor;
import com.seekon.system.common.model.Element;
import com.seekon.system.rule.model.RuleElement;

public class RuleContentPanel extends JPanel {

  private static final long serialVersionUID = 6031322774758607770L;

  private RuleElement ruleElement;

  private boolean edit;

  private List<RuleItemPanel> ruleItemPaneList = new ArrayList<RuleItemPanel>();

  public RuleContentPanel(RuleElement ruleElement, boolean edit) {
    super();
    this.ruleElement = ruleElement;
    this.edit = edit;

    initComponents();
  }

  private void initComponents() {
    this.setLayout(new MigLayout("align center", "[right]"));

    Element element = ruleElement.getElement();
    List<FieldMeta> fieldList = element.getFieldList();
    for (FieldMeta field : fieldList) {
      RuleItemPanel ruleItemPane = new RuleItemPanel(field,
        findRuleSpecification(field), edit);
      ruleItemPaneList.add(ruleItemPane);
      this.add(ruleItemPane, "wrap");
    }
  }

  private AbstractCustomRuleSpecification findRuleSpecification(FieldMeta fieldMeta) {
    IRuleSpecification specification = ruleElement.getRuleSpecification();
    if (specification != null) {
      Visitor findsVisitor = new FindSpecificationVistor(fieldMeta);
      Object result = specification.accept(findsVisitor);
      if (result instanceof AbstractCustomRuleSpecification) {
        return (AbstractCustomRuleSpecification) result;
      }
    }
    return null;
  }

  public RuleElement getUpdatedRuleElement() {
    ruleElement.setRuleSpecification(getSettedRuleSpecification());
    return ruleElement;
  }

  private CompositeRuleSpecification getSettedRuleSpecification() {
    CompositeRuleSpecification result = new TrueSpecification("true");
    for (RuleItemPanel ruleItemPane : ruleItemPaneList) {
      IRuleSpecification other = ruleItemPane.getSelectedRuleSpecification();
      if (other != null) {
        AndRuleSpecification specification = new AndRuleSpecification(result, other);
        result = specification;
      }
    }
    if (result instanceof AndRuleSpecification) {
      return result;
    } else {
      return null;
    }
  }

}
