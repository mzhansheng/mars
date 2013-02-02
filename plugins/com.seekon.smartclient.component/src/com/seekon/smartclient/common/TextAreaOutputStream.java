package com.seekon.smartclient.common;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream {
  /**
   * 缓冲区大块小1k
   */
  private static final int BLOCK_SIZE = 1024;

  /**
   * 文本框
   */
  private JTextArea textArea = null;

  /**
   * 是否限制行数
   */
  boolean limitRow = false;

  /**
   * 最大行数
   */
  int maxRow = 120;

  /**
   * 缓冲区
   */
  private byte[] buffer = new byte[BLOCK_SIZE];

  /**
   * 位置指针
   */
  private int pos = 0;

  /**
   * @param textArea JTextArea
   * @param maxRow int -  限制显示行数
   */
  public TextAreaOutputStream(JTextArea textArea, int maxRow) {
    this.textArea = textArea;
    this.maxRow = maxRow;
    this.limitRow = true;
  }

  /**
   * 实列化 TextAreaOutputStream，不限制行数
   * @param textArea JTextArea
   */
  public TextAreaOutputStream(JTextArea textArea) {
    this.textArea = textArea;
    limitRow = false;
  }

  @Override
  public void write(int b) throws IOException {
    if (buffer.length < pos + 1) {
      byte[] newBuffer = new byte[buffer.length + BLOCK_SIZE];
      System.arraycopy(buffer, 0, newBuffer, 0, pos);
      buffer = newBuffer;
    }
    buffer[pos] = (byte) b;
    pos++; //移动指针
  }

  @Override
  public void flush() throws IOException {
    textArea.append(new String(buffer, 0, pos));
    if (limitRow) {
      while (textArea.getLineCount() > maxRow + 1) {
        //System.err.println(textArea.getLineCount());
        textArea.replaceRange("", 0, textArea.getText().indexOf("\n") + 1);
      }
    }
    buffer = new byte[BLOCK_SIZE];
    pos = 0;
  }

  @Override
  public void close() throws IOException {
    buffer = null;
    textArea = null;
  }
}
