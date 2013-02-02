package com.seekon.system.rule.client.view.rule.wizard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.pietschy.wizard.AbstractWizardStep;
import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.WizardModel;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.rule.client.internal.ServiceFacade;
import com.seekon.system.rule.client.view.RuleEventType;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.spi.RuleService;

public class RuleSummaryWizardStep extends AbstractWizardStep {

  private CreateRuleWizardModel model;

  private JTextPane summaryOutput;

  public RuleSummaryWizardStep() {
    super("概况", "对照如下信息是否和期望的一样");
  }

  @Override
  public void applyState() throws InvalidStateException {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Rule>() {

      @Override
      public Rule execute() throws Exception {
        //setBusy(true);
        setComplete(false);
        JProgressBar pgb = new JProgressBar();
        pgb.setIndeterminate(true);
        pgb.setString("Please wait..");
        pgb.setStringPainted(true);
        setView(pgb);

        Rule rule = model.getRule();
        RuleService service = ServiceFacade.getRuleService();
        rule = service.addRule(rule);
        return rule;
      }

      @Override
      public void success(Rule rule) {
        //setBusy(false);
        setSummary("创建成功");
        setView(new JLabel("Done.."));
        setComplete(true);

        ObserverUtil
          .notifyEvent(new Event(rule, RuleEventType.RULE_ADDED), getView());
      }
    });

  }

  @Override
  public void applyState(ActionEvent arg0) throws InvalidStateException {
    applyState();
  }

  @Override
  public Dimension getPreferredSize() {
    return new JLabel("Done..").getPreferredSize();
  }

  @Override
  public void prepare() {
    summaryOutput.setText(null);

    summaryOutputAppend("规则基本信息~~~~~~~~~~~~~~~~\n");
    summaryOutputAppend(model.getRule() + "\n");

    summaryOutputAppend("规则要素信息~~~~~~~~~~~~~~~~~~~~~\n");
    summaryOutputAppend(" " + model.getRule().getRuleElementList() + "\n");
  }

  @Override
  public void init(WizardModel model) {
    this.model = (CreateRuleWizardModel) model;

    summaryOutput = new JTextPane();
    JScrollPane jsp = new JScrollPane(summaryOutput);
    setView(jsp);

    setComplete(true);
  }

  private void summaryOutputAppend(String str) {
    Document document = summaryOutput.getDocument();
    try {
      document.insertString(document.getLength(), str, null);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }
}
