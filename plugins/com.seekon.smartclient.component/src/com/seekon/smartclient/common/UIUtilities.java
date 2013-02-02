package com.seekon.smartclient.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public final class UIUtilities {
  private static final class InvokeRunnable implements Runnable {
    private final InvokeHandler handler;

    private InvokeRunnable(InvokeHandler handler) {
      this.handler = handler;
    }

    @Override
    public void run() {
      try {
        final Object ret = handler.execute();
        if (ASYNC) {
          EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              handler.success(ret);
              handler.after();
            }
          });
        } else {
          handler.success(ret);
          handler.after();
        }

      } catch (final Exception e) {
        if (ASYNC) {
          EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              handler.failure(e);
              handler.after();
            }
          });
        } else {
          handler.failure(e);
          handler.after();
        }
      }
    }
  }

  /**
   * 是否使用异步加载，用于测试使用
   */
  private static boolean ASYNC = "true"
    .equals(System.getProperty("async", "true"));

  private UIUtilities() {
  }

  /**
   * 显示堆栈对话框
   * @param e 异常或其他Throwable实例对象
   * @param parentComponent 父组件，对话框父级件
   * @param title 对话框标题
   * @param message 显示消息
   * @deprecated 方法名存在错别字，请使用 {@link UIUtilities#showStackTraceDialog(Throwable, Component, String, String)}
   */
  @Deprecated
  public static void showStaickTraceDialog(Throwable e, Component parentComponent,
    String title, String message) {
    showStackTraceDialog(e, parentComponent, title, message);
  }

  /**
   * 显示堆栈对话框
   * @param e 异常或其他Throwable实例对象
   * @param parentComponent 父组件，对话框父级件
   * @param title 对话框标题
   * @param message 显示消息
   */
  public static void showStackTraceDialog(Throwable e, Component parentComponent,
    String title, String message) {

    JTextArea textArea = new JTextArea();
    textArea.setForeground(Color.RED);
    TextAreaOutputStream outputStream = new TextAreaOutputStream(textArea);
    e.printStackTrace(new PrintStream(outputStream, true));
    final JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(360, 240));

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

    JPanel messagePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.gridwidth = gbc.gridheight = 1;
    gbc.anchor = GridBagConstraints.WEST;

    messagePanel.add(new JLabel("执行操作时系统出现异常"), gbc);

    gbc.gridy++;
    gbc.anchor = GridBagConstraints.EAST;
    messagePanel.add(panel, gbc);

    gbc.gridy++;
    gbc.anchor = GridBagConstraints.EAST;
    gbc.fill = GridBagConstraints.BOTH;
    messagePanel.add(scrollPane, gbc);
    scrollPane.setVisible(false);

    JLabel detailLabel = new JLabel("详细>>");
    panel.add(detailLabel);

    detailLabel.setForeground(Color.BLUE);
    detailLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    detailLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        label.setToolTipText("展开显示堆栈信息");
        if (label.getText().equals("详细>>")) {
          label.setText("<<详细");
          scrollPane.setVisible(true);
        } else {
          label.setText("详细>>");
          scrollPane.setVisible(false);
        }

        SwingUtilities.getWindowAncestor(scrollPane).pack();
      }
    });

    JOptionPane optionPane = new JOptionPane(messagePanel,
      JOptionPane.ERROR_MESSAGE);
    optionPane.createDialog(
      parentComponent == null ? DefaultKeyboardFocusManager
        .getCurrentKeyboardFocusManager().getActiveWindow() : parentComponent,
      title).setVisible(true);
  }

  /**
   * 执行异步调用
   * @param handler
   * @see InvokeHandler
   */
  public static Thread asyncInvoke(final InvokeHandler handler) {
    if (ASYNC) {
      EventQueue.invokeLater(new Runnable() {

        @Override
        public void run() {
          handler.before();
        }
      });
    } else {
      handler.before();
    }

    Runnable runnable = new InvokeRunnable(handler);
    Thread thread;
    if (ASYNC) {
      class InvokeHandlerThread extends Thread {

        public InvokeHandlerThread(Runnable target, String name) {
          super(target, name);
        }
      }
      thread = new InvokeHandlerThread(runnable, "InvokeHandler-Thread");
      thread.start();
    } else {
      runnable.run();
      thread = Thread.currentThread();
    }
    return thread;
  }
}
