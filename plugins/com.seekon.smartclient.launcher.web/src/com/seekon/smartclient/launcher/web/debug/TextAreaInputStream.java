package com.seekon.smartclient.launcher.web.debug;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextArea;

public class TextAreaInputStream extends InputStream {

  private JTextArea textArea = new JTextArea();

  private Integer value = null;

  private static Object lock = new Object();

  public TextAreaInputStream() {
    installListeners();
  }

  public JTextArea getTextArea() {
    return textArea;
  }

  private void installListeners() {
    textArea.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        synchronized (lock) {
          value = e.getKeyCode();
          lock.notifyAll();
        }
      }
    });
  }

  @Override
  public int read() throws IOException {
    System.out
      .println("read===============================================================================");
    synchronized (lock) {
      if (value == null) {
        try {
          System.out.println("wait...");
          lock.wait();
          System.out.println("end wait");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    int val = value.intValue();
    System.out.println(val);
    value = null;
    return val;
  }

}
