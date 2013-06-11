package com.seekon.system.org.client.view.dept.wizard;

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
import com.seekon.system.org.client.internal.ServiceFacade;
import com.seekon.system.org.client.view.OrgEventType;
import com.seekon.system.org.model.Department;

public class DeptSummaryWizardStep extends AbstractWizardStep {

  private CreateDeptWizardModel model;

  private JTextPane summaryOutput;

  public DeptSummaryWizardStep() {
    super("概况", "对照如下信息是否和期望的一样");
  }

  @Override
  public void applyState() throws InvalidStateException {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Department>() {

      @Override
      public Department execute() throws Exception {
        //setBusy(true);
        setComplete(false);
        JProgressBar pgb = new JProgressBar();
        pgb.setIndeterminate(true);
        pgb.setString("Please wait..");
        pgb.setStringPainted(true);
        setView(pgb);

        Department enterprise = model.getDepartment();
        enterprise = ServiceFacade.getOrgService()
          .addDepartment(enterprise);
        return enterprise;
      }

      @Override
      public void success(Department enterprise) {
        //setBusy(false);
        setSummary("创建成功");
        setView(new JLabel("Done.."));
        setComplete(true);

        ObserverUtil.notifyEvent(
          new Event(enterprise, OrgEventType.DEPT_ADDED), getView());
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

    summaryOutputAppend("基本信息~~~~~~~~~~~~~~~~\n");

  }

  @Override
  public void init(WizardModel model) {
    this.model = (CreateDeptWizardModel) model;

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
