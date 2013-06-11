package com.seekon.system.auth.client.view.user.wizard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.pietschy.wizard.AbstractWizardStep;
import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.WizardModel;

import com.seekon.smartclient.common.DefaultInvokeHandler;
import com.seekon.smartclient.common.TextAreaOutputStream;
import com.seekon.smartclient.common.UIUtilities;
import com.seekon.smartclient.component.viewedit.event.Event;
import com.seekon.smartclient.component.viewedit.event.ObserverUtil;
import com.seekon.system.auth.client.AuthorityEventType;
import com.seekon.system.auth.client.internal.ServiceFacade;
import com.seekon.system.auth.model.User;

public class SummaryWizardStep extends AbstractWizardStep {

  public SummaryWizardStep() {
    super("Summary", "概要");
  }

  private CreateUserWizardModel wizardModel;

  private JTextArea ta;

  @Override
  public void init(WizardModel model) {
    this.wizardModel = (CreateUserWizardModel) model;
    ta = new JTextArea();
    OutputStream os = new TextAreaOutputStream(ta);
    System.setOut(new PrintStream(os));
    setView(ta);
    setComplete(true);
  }

  @Override
  public void prepare() {
    ta.setText(null);
    System.out.println("基础信息：");
    System.out.println("	" + wizardModel.getUser());
    System.out.println("\n角色：");
    System.out.println("	" + wizardModel.getUser().getRoleList());
    System.out.println("\n数据权限：");
    setComplete(true);
  }

  @Override
  public void applyState(ActionEvent e) throws InvalidStateException {
    applyState();
  }

  @Override
  public void applyState() throws InvalidStateException {
    UIUtilities.asyncInvoke(new DefaultInvokeHandler<User>() {

      @Override
      public User execute() throws Exception {
        setComplete(false);

        User user = wizardModel.getUser();
        user = ServiceFacade.getUserService().addUser(user);
        return user;
      }

      @Override
      public void success(User user) {
        setView(new JLabel("Done.."));
        setComplete(true);

        ObserverUtil.notifyEvent(
          new Event(user, AuthorityEventType.NEW_USER_ADDED), getView());
      }
    });

  }

  @Override
  public Dimension getPreferredSize() {
    return new JLabel("Done..").getPreferredSize();
  }

}
