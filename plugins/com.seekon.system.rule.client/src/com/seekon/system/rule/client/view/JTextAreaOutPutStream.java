package com.seekon.system.rule.client.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class JTextAreaOutPutStream extends OutputStream {

  private JTextArea ta;

  public JTextAreaOutPutStream(JTextArea textArea) {
    this.ta = textArea;
  }

  public void write(int b) throws IOException {
    ta.append(Character.toString((char) b));
    ta.setCaretPosition(ta.getText().length());
  }

  public void write(byte[] b, int off, int len) throws IOException {
    String s = new String(b, off, len);
    ta.append(s);
    ta.setCaretPosition(ta.getText().length());
  }

}
