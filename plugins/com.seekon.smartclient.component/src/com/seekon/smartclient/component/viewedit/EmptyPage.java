package com.seekon.smartclient.component.viewedit;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public final class EmptyPage extends AbstractPage {

  public EmptyPage() {
    setLayout(new BorderLayout());
    JLabel comp = new JLabel(
      "<html><font size=+5 color=green>Empty Page</font></html>");
    comp.setHorizontalAlignment(SwingConstants.CENTER);
    add(comp, BorderLayout.CENTER);
  }
}
