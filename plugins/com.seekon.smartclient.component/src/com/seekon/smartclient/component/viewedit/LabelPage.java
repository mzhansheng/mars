package com.seekon.smartclient.component.viewedit;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabelPage extends AbstractPage {

  private JLabel comp;

  public LabelPage() {
    this("Empty Page");
  }

  public LabelPage(String text) {
    setLayout(new BorderLayout());
    comp = new JLabel();
    comp.setHorizontalAlignment(SwingConstants.CENTER);
    add(comp, BorderLayout.CENTER);

    setText(text);
  }

  public void setText(String text) {
    comp.setText("<html><font size=+5 color=green>" + text + "</font></html>");
  }

  @Override
  public String getPageId() {
    return String.valueOf(System.currentTimeMillis());
  }
}
