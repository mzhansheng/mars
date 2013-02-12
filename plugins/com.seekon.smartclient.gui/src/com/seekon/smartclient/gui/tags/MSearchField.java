package com.seekon.smartclient.gui.tags;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MSearchField extends MTextField {

  private static final long serialVersionUID = -2749201431010204094L;

  private String emptyString = null;

  @Override
  public String getText() {
    String text = super.getText();
    if(emptyString.equals(text)){
      return "";
    }
    return text;
  }
  
  public void setEmptyString(String initString) {
    this.emptyString = initString;
    if (this.emptyString != null && this.emptyString.trim().length() > 0) {
      this.setText(emptyString);
      addListener();
    }
  }

  private void addListener() {
    this.addFocusListener(new FocusListener() {

      @Override
      public void focusLost(FocusEvent e) {
        String text = MSearchField.super.getText();
        if (text == null || text.trim().length() == 0) {
          MSearchField.this.setText(emptyString);
        }
      }

      @Override
      public void focusGained(FocusEvent e) {
        String text = MSearchField.super.getText();
        if (emptyString.equals(text)) {
          MSearchField.this.setText("");
        }
      }
    });
  }
}
