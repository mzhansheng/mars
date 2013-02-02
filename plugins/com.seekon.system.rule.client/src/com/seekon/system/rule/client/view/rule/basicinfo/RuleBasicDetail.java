package com.seekon.system.rule.client.view.rule.basicinfo;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.seekon.smartclient.component.viewedit.AbstractDetail;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.model.Rule;

public class RuleBasicDetail extends AbstractDetail<Rule> {

  private static final long serialVersionUID = 1017372384836816106L;

  private Rule rule;

  public void initPage(Rule rule) {
    this.removeAll();
    this.setLayout(new BorderLayout());

    JPanel pane = new JPanel(new MigLayout("align center"));
    pane.add(new JLabel("规则名称"));
    pane.add(new JLabel("<html><font color=blue>：</font></html>"));
    pane.add(new JLabel(rule.getRuleName()), "w 4cm:6cm:,wrap");

    this.add(pane, BorderLayout.CENTER);
    this.invalidate();

    this.rule = rule;
  }

  @Override
  public Rule edit() {
    return rule;
  }

  @Override
  public void update(Event event) {
    if (event.getEventType() == RuleEventType.RULE_BASIC_INFO_UPDATED) {
      this.initPage((Rule) event.getSource());
    }
  }
}
