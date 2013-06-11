package com.seekon.smartclient.launcher.web.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class OSGIConsoleReadThread extends Thread {

  private JTextArea outTextArea;

  private BufferedReader bufferedReader;

  private PrintWriter logWriter;

  private boolean stoped = false;

  private Runnable closeOperation;

  public OSGIConsoleReadThread(InputStream in, JTextArea outTextArea,
    PrintWriter logWriter, Runnable closeOperation) throws IOException {
    this.outTextArea = outTextArea;
    this.bufferedReader = new BufferedReader(new InputStreamReader(in));
    this.logWriter = logWriter;
    this.closeOperation = closeOperation;
  }

  public void setStoped(boolean stoped) {
    this.stoped = stoped;
  }

  public void run() {
    try {
      for (String line = null; !stoped && (line = bufferedReader.readLine()) != null;) {
        logWriter.println(line);
        appendLine(line);
        Thread.sleep(1);
      }
    } catch (Exception e) {
    } finally {
      if (closeOperation != null) {
        closeOperation.run();
      }
    }
  }

  private void appendLine(final String line) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Document doc = outTextArea.getDocument();
        if (doc != null) {
          try {
            doc.insertString(doc.getLength(), line + "\n", null);
          } catch (BadLocationException e) {
          }
        }
      }
    });
  }
}