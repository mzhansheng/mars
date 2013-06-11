package com.seekon.smartclient.framework.internal.toolbar;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.seekon.smartclient.framework.util.ResourceProvide;
import com.seekon.smartclient.framework.util.ToolBarButton;

public class JHelpButton extends ToolBarButton {

  private static final long serialVersionUID = 1697447813622659059L;

  public JHelpButton() {
    this(new AbstractAction() {
      private static final long serialVersionUID = -6980952343542609366L;

      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });

  }

  public JHelpButton(Action action) {
    super(action);

    this.setText("帮助");
    this.setToolTipText("帮助");
    this.setIcon(ResourceProvide.getIcon("/resources/global/help.png"));
  }

}
