package com.seekon.system.auth.client.view.role.wizard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
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
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.model.Role;

public class RoleSummaryWizardStep extends AbstractWizardStep {

  private CreateRoleWizardModel model;

  private JTextPane summaryOutput;

  public RoleSummaryWizardStep() {
    super("概况", "对照如下信息是否和期望的一样");
  }

  @Override
  public void init(WizardModel model) {
    this.model = (CreateRoleWizardModel) model;

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

  @Override
  public void prepare() {
    summaryOutput.setText(null);

    summaryOutputAppend("角色基本信息~~~~~~~~~~~~~~~~\n");
    summaryOutputAppend(model.getRole() + "\n");

    summaryOutputAppend("角色‘s资源~~~~~~~~~~~~~~~~~~~~~\n");
    summaryOutputAppend(" " + model.getRole().getResource() + "\n");

  }

  @Override
  public void applyState() throws InvalidStateException {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<Role>() {

      @Override
      public Role execute() throws Exception {
        setComplete(false);
        Role role = model.getRole();
        role = ServiceFacade.getRoleService().addRole(role);
        return role;
      }

      @Override
      public void success(Role role) {
        setSummary("创建成功");
        setView(new JLabel("Done.."));
        setComplete(true);

        ObserverUtil.notifyEvent(
          new Event(role, AuthorityEventType.ROLE_ADDED), getView());
      }
    });
  }

  @Override
  public void applyState(ActionEvent e) throws InvalidStateException {
    applyState();
  }

  @Override
  public Dimension getPreferredSize() {
    return new JLabel("Done..").getPreferredSize();
  }

}
