package com.seekon.smartclient.component.viewedit.callback;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.seekon.smartclient.component.viewedit.Page;

public abstract class ToolBarCallBackPage extends CallbackPage {

  private class FinishAction extends AbstractAction {
    public FinishAction() {
      super("完成");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      gobackAtion.actionPerformed(e);
    }
  }

  public static final Color CALLBACKCOLOR = Color.BLUE;

  private FinishAction finishAction = new FinishAction();

  private Action gobackAtion;

  private Page previousPage;

  public ToolBarCallBackPage() {
    BorderLayout borderLayout = new BorderLayout();
    setLayout(borderLayout);

    Component southComponent = getSouthComponent();
    if (southComponent != null)
      add(southComponent, BorderLayout.SOUTH);

    Component center = getCenterComponent();
    if (center != null)
      add(center, BorderLayout.CENTER);

    Component northComponent = getNorthComponent();
    if (northComponent != null)
      add(northComponent, BorderLayout.NORTH);
  }

  protected abstract Component getCenterComponent();

  protected Component getNorthComponent() {
    JPanel panel = new JPanel();
    panel.setBackground(CALLBACKCOLOR);
    GridBagLayout gbl_panel = new GridBagLayout();
    gbl_panel.columnWidths = new int[] { 0, 0, 0 };
    gbl_panel.rowHeights = new int[] { 0, 0 };
    gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
    gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
    panel.setLayout(gbl_panel);

    JButton btnW = new JButton(finishAction);
    GridBagConstraints gbc_btnW = new GridBagConstraints();
    gbc_btnW.gridx = 0;
    gbc_btnW.gridy = 0;
    panel.add(btnW, gbc_btnW);

    return panel;
  }

  protected Component getSouthComponent() {
    JPanel panel = new JPanel();
    panel.setBackground(CALLBACKCOLOR);
    GridBagLayout gbl_panel = new GridBagLayout();
    gbl_panel.columnWidths = new int[] { 0, 0, 0 };
    gbl_panel.rowHeights = new int[] { 0, 0 };
    gbl_panel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
    gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
    panel.setLayout(gbl_panel);

    JButton btnW = new JButton(finishAction);
    GridBagConstraints gbc_btnW = new GridBagConstraints();
    gbc_btnW.gridx = 1;
    gbc_btnW.gridy = 0;
    panel.add(btnW, gbc_btnW);

    return panel;
  }

  @Override
  public void setGoBackAction(Action action) {
    this.gobackAtion = action;
  }

  @Override
  public void setPreviousPage(Page page) {
    this.previousPage = page;
    finishAction.putValue(Action.NAME, finishAction.getValue(Action.NAME) + "，返回>>>"
      + page.getPageName());
  }

}
