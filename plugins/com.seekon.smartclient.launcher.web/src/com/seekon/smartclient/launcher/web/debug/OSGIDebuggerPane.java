package com.seekon.smartclient.launcher.web.debug;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Utilities;


public class OSGIDebuggerPane extends JComponent {

  private class ContentTextArea extends JTextArea {

    /**
     * 命令行历史
     */
    private List<String> commandHistory = new ArrayList<String>();

    private Set<String> keywords = new HashSet<String>();

    /**
     * 当前的命令行索引号
     */
    private int currentHistoryIndex = -1;

    @Override
    public void updateUI() {
      //      super.updateUI();
      setUI(new BasicTextAreaUI());
      modiyfKeyBoardActions();
      setFont(new Font("Monospaced", Font.BOLD, 13));
      setBackground(Color.BLACK);
      setForeground(Color.WHITE);
      setCaretColor(Color.WHITE);
      setSelectionColor(SystemColor.textHighlight);
      setSelectedTextColor(Color.CYAN);
      registerShortCuts();
    }

    public ContentTextArea() {
      //支持补齐的关键字
      String[] keywordArray = { "refresh", "status", "start", "stop", "update",
        "install", "uninstall", "getprop", "bundles", "headers", "services", "ss",
        "packages", "bundle", "INSTALLED", "RESOLVED", "ACTIVE", "file://" };
      for (String word : keywordArray) {
        keywords.add(word);
      }
    }

    private void registerShortCuts() {
      //      this.registerKeyboardAction(new ActionListener() {
      //
      //        @Override
      //        public void actionPerformed(ActionEvent e) {
      //          requestFocus();
      //          setCaretPosition(getDocument().getLength());
      //        }
      //      }, KeyStroke.getKeyStroke(KeyEvent.VK_SEMICOLON, InputEvent.SHIFT_MASK),
      //        JComponent.WHEN_IN_FOCUSED_WINDOW);

      this.registerKeyboardAction(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          requestFocus();
          setCaretPosition(getDocument().getLength());
        }
      }, KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK),
        JComponent.WHEN_IN_FOCUSED_WINDOW);
      this.registerKeyboardAction(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          clear();
        }
      }, KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK),
        JComponent.WHEN_IN_FOCUSED_WINDOW);

      this.registerKeyboardAction(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (getSelectedText() != null) {
            searchTextField.setText(getSelectedText());
          }
          searchTextField.requestFocus();
          searchTextField.selectAll();
        }
      }, KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK),
        JComponent.WHEN_IN_FOCUSED_WINDOW);

      ActionListener findNextAction = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          doSearch();
        }
      };
      this
        .registerKeyboardAction(findNextAction,
          KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
          JComponent.WHEN_IN_FOCUSED_WINDOW);
      this.registerKeyboardAction(findNextAction,
        KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK),
        JComponent.WHEN_IN_FOCUSED_WINDOW);

      this.registerKeyboardAction(findNextAction,
        KeyStroke.getKeyStroke(KeyEvent.VK_K, KeyEvent.CTRL_MASK),
        JComponent.WHEN_IN_FOCUSED_WINDOW);

      this.registerKeyboardAction(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          autoCorrect();
        }
      }, KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), JComponent.WHEN_FOCUSED);
    }

    /**
     * 根据字符串返回匹配的关键字数组
     * @param str
     * @return
     */
    private String[] findMatch(String str) {
      List<String> list = new ArrayList<String>();
      for (Iterator<String> it = keywords.iterator(); it.hasNext();) {
        String keyword = it.next();
        if (keyword.equals(str)) {
          continue;
        }
        if (keyword.startsWith(str)) {
          list.add(keyword);
        }
      }
      return list.toArray(new String[list.size()]);
    }

    /**
     * 修改键盘动作映射
     */
    private void modiyfKeyBoardActions() {
      //向上键，显示上一条命令历史
      getActionMap().put("caret-down", new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
          loadNextHistory();
        }
      });
      //向下键显示下一条命令历史
      getActionMap().put("caret-up", new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
          loadPreHistory();
        }
      });
    }

    public void append(String str) {
      Document doc = getDocument();
      if (doc != null) {
        try {
          doc.insertString(doc.getLength(), str, null);
        } catch (BadLocationException e) {
        }
      }
    }

    public void replaceSelection(String content) {
      /*
       * 如果输入的是回车，且光标不在行尾，会出现断行情况，即在光标处换行，使用很不方便，下面
       * 对该问题进行了处理：
       * 1. 如果输入的是回车，则移动光标到行尾，再执行换行动作
       * 2. 将当行内容加到命令行历史list
       */
      if ("\n".equals(content)) {
        int p = getCaretPosition();
        try {
          int end = Utilities.getRowEnd(this, p);
          if (p < end) {
            this.setCaretPosition(end);
          }

          //添加命令行历史
          int start = Utilities.getRowStart(this, p);
          String commandLine = this.getText(start, end - start);
          addCommandToHistory(commandLine);
          if (osgiConsoleReadThread != null && osgiConsoleReadThread.isAlive()) {
            getLogWriter().println(commandLine);
          }
        } catch (BadLocationException e) {
          e.printStackTrace();
        }
      }
      super.replaceSelection(content);
    }

    /**
     * 添加命令行到历史
     * @param commandLine
     */
    private void addCommandToHistory(String commandLine) {
      //空串不处理
      if (commandLine == null)
        return;
      commandLine = commandLine.trim();
      if (commandLine.length() == 0)
        return;

      //如果历史最后一条与commandLine内容相同，则不添加
      if (commandHistory.size() == 0
        || !commandLine.equals(commandHistory.get(commandHistory.size() - 1))) {
        commandHistory.add(commandLine);
      }
      //重置currentHistoryIndex到最后
      currentHistoryIndex = commandHistory.size();
    }

    /**
     * 显示前一个命令行
     */
    private void loadPreHistory() {
      if (currentHistoryIndex > 0) {
        String content = commandHistory.get(--currentHistoryIndex);
        loadCommandLineHistory(content);
      }
    }

    /**
     * 将命令行显示到当前光标处
     * @param content
     */
    private void loadCommandLineHistory(String content) {
      int p = this.getCaretPosition();
      try {
        p = Utilities.getRowEnd(this, p);
        int start = Utilities.getRowStart(this, p);
        this.getDocument().remove(start, p - start);
      } catch (BadLocationException e) {
      }
      replaceSelection(content);
    }

    /**
     * 加载下一条命令行
     */
    private void loadNextHistory() {
      if (currentHistoryIndex < commandHistory.size() - 1) {
        loadCommandLineHistory(commandHistory.get(++currentHistoryIndex));
      }
    }

    public void clear() {
      setText("");
      getHighlighter().removeAllHighlights();
      System.gc();
      if (osgiConsoleReadThread != null && osgiConsoleReadThread.isAlive()) {
        getLogWriter().flush();
      }
    }

    /**
     * 自动更正/补齐
     */
    private void autoCorrect() {
      int p = getCaretPosition();
      try {
        int start = Utilities.getRowStart(ContentTextArea.this, p);
        int length = p - start;
        Document doc = getDocument();
        String str = doc.getText(start, length);
        int spaceIndex = str.lastIndexOf(" "); //空格
        if (spaceIndex > -1) {
          str = str.substring(spaceIndex + 1);
          start += spaceIndex + 1; //+1保留空格
          length = str.length();
        }
        if (str == null || str.length() == 0)
          return;
        String[] keywords = findMatch(str);
        if (keywords.length == 1) {
          doc.remove(start, length);
          doc.insertString(start, keywords[0], null);
        } else if (keywords.length > 1) {
          start = Utilities.getRowStart(ContentTextArea.this, p) - 1;
          if (start < 0) {
            start = 0;
            doc.insertString(start, "\n", null);
          }

          for (String s : keywords) {
            doc.insertString(start, s + " ", null);
            start += s.length() + 1;
          }
          doc.insertString(start, "\n", null);
        }
      } catch (BadLocationException e1) {
      }
    }
  }

  private PrintWriter logWriter;

  /**
   * 文本框组件，用与输入命令和输出内容
   */
  private ContentTextArea contentTextArea = new ContentTextArea();

  /**
   * 连接调试器的socket
   */
  private Socket socket;

  /**
   * osgi命令输出流，用于发送命令
   */
  private OutputStream outputStream;

  /**
   * 日志文件，osgi的输出将写到该文件
   */
  private File logFile;

  /**
   * 清除按钮
   */
  private JButton clearButton = new JButton("清除");

  /**
   * 打开调试窗口日志按钮
   */
  private JButton openLogButton = new JButton("打开调试窗口日志");

  /**
   * 打开osgi框架日志按钮
   */
  private JButton openOSGILogButton = new JButton("打开osgi框架日志");

  /**
   * 总在前复选框
   */
  private JCheckBox alwaysCheckBox = new JCheckBox("调试窗口前端显示");

  /**
   * osgi框架的日志文件
   */
  private File osgiLogFile = null;

  /**
   * 端口输入框
   */
  private JTextField portTextField = new JTextField();

  /**
   * 主机输入框
   */
  private JTextField hostTextField = new JTextField();

  /**
   * 连接按钮
   */
  private JButton connectButton;

  private JTextField searchTextField = new JTextField();

  private JButton searchButton = new JButton("查找");

  /**
   * osgi console输出读取线程
   */
  private OSGIConsoleReadThread osgiConsoleReadThread;

  /**
   * 显示连接状态的label
   */
  private JLabel statusLabel = new JLabel();

  /**
   * 停止icon
   */
  private Icon stopedIcon = new ImageIcon(OSGIDebuggerPane.class.getClassLoader()
    .getResource("icon/stop.gif"));

  /**
   * 已经连接icon
   */
  private Icon connectedIcon = new ImageIcon(OSGIDebuggerPane.class.getClassLoader()
    .getResource("icon/connected.gif"));

  public OSGIDebuggerPane(String host, int port, File logFile) throws Exception {
    File logDir = logFile.getParentFile();
    if (!logDir.exists()) {
      logDir.mkdirs();
    }
    this.logFile = logFile;
    initComponent();
    hostTextField.setText(host);
    portTextField.setText(Integer.toString(port));
    connectByConfig();
  }

  private void initComponent() {
    addComponents();
    installListeners();
  }

  private void addComponents() {
    setLayout(new BorderLayout());
    add(new JScrollPane(contentTextArea));

    JToolBar toolBar = new JToolBar();
    add(toolBar, BorderLayout.NORTH);
    toolBar.add(clearButton);
    toolBar.add(openLogButton);
    toolBar.add(openOSGILogButton);

    connectButton = new JButton("连接");
    Dimension preferredSize = new Dimension(96, 25);
    hostTextField.setPreferredSize(preferredSize);
    hostTextField.setMaximumSize(preferredSize);
    hostTextField.setMinimumSize(preferredSize);

    preferredSize = new Dimension(40, 25);
    portTextField.setPreferredSize(preferredSize);
    portTextField.setMaximumSize(preferredSize);
    portTextField.setMinimumSize(preferredSize);
    portTextField.setHorizontalAlignment(JTextField.CENTER);

    toolBar.add(alwaysCheckBox);
    toolBar.addSeparator();
    toolBar.add(statusLabel);
    toolBar.add(new JLabel("主机："));
    toolBar.add(hostTextField);
    toolBar.add(new JLabel(" 端口："));
    toolBar.add(portTextField);
    toolBar.add(connectButton);

    toolBar.addSeparator();
    toolBar.add(new JLabel("查找："));
    preferredSize = new Dimension(125, 25);
    searchTextField.setPreferredSize(preferredSize);
    searchTextField.setMaximumSize(preferredSize);
    searchTextField.setMinimumSize(preferredSize);
    bindSearchShortCuts();

    toolBar.add(searchTextField);
    toolBar.add(searchButton);
  }

  private void bindSearchShortCuts() {
    searchTextField.registerKeyboardAction(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            doSearch();
          }
        });
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
  }

  public PrintWriter getLogWriter() {
    return logWriter;
  }

  @Override
  public void addNotify() {
    super.addNotify();
    contentTextArea.requestFocus();
  }

  /**
   * 连接到指定的主机和端口
   * @param host 主机，可以是机器名或ip
   * @param port 调试端口号
   * @throws UnknownHostException
   * @throws IOException
   */
  private void connect(final String host, final int port) {
    new Thread() {
      public void run() {
        try {
          OSGIDebuggerPane.this.logWriter = new PrintWriter(new BufferedWriter(
            new FileWriter(logFile), 1024 * 1024), false);
          //如果已经处理连接状态，关闭读线程和socket
          if (socket != null) {
            socket.close();
            outputStream.close();
          }
          if (osgiConsoleReadThread != null && osgiConsoleReadThread.isAlive()) {
            osgiConsoleReadThread.setStoped(true);
            osgiConsoleReadThread.join();
          }

          socket = new Socket(host, port);
          outputStream = socket.getOutputStream();
          InputStream inputStream = socket.getInputStream();
          osgiConsoleReadThread = new OSGIConsoleReadThread(inputStream,
            contentTextArea, getLogWriter(), new Runnable() {

              @Override
              public void run() {
                disconnect();
              }
            });
          connected(host, port);
          osgiConsoleReadThread.start();
          osgiLogFile = getOsgLogFile();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(OSGIDebuggerPane.this,
            "连接失败，原因：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
          disconnect();
        }
      };
    }.start();

  }

  /**
   * 返回osgi日志文件路径
   * @return 实际的osgi日志文件路径，不存在则返回null，注：如果在equinox不可能为null
   */
  private File getOsgLogFile() {
    Socket socket = null;
    BufferedReader br = null;
    OutputStream out = null;
    try {
      socket = new Socket(hostTextField.getText(), Integer.parseInt(
        portTextField.getText(), 10));
      out = socket.getOutputStream();
      br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      out.write(new byte[] { 'g', 'e', 't', 'p', 'r', 'o', 'p', '\n' });
      out.flush();

      for (String l = null; (l = br.readLine()) != null;) {
        if (l.startsWith("osgi.logfile=")) {
          return new File(l.substring("osgi.logfile=".length()));
        }
      }
    } catch (Exception e) {
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
        }
      }
      if (out != null)
        try {
          out.close();
        } catch (IOException e1) {
        }
      if (br != null)
        try {
          br.close();
        } catch (IOException e) {
        }
    }
    return null;
  }

  /**
   * 安装监听器
   */
  private void installListeners() {
    ActionListener buttonListener = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
          contentTextArea.clear();
          contentTextArea.requestFocus();
        } else if (e.getSource() == openLogButton) {
          try {
            getLogWriter().flush();
            Desktop.getDesktop().open(logFile);
          } catch (IOException e1) {
            JOptionPane.showMessageDialog(OSGIDebuggerPane.this, e1.getMessage());
          }
        } else if (e.getSource() == openOSGILogButton) {
          try {
            Desktop.getDesktop().open(osgiLogFile);
          } catch (IOException e1) {
            JOptionPane.showMessageDialog(OSGIDebuggerPane.this, e1.getMessage());
          }
        } else if (e.getSource() == alwaysCheckBox) {
          Window activeWindow = DefaultKeyboardFocusManager
            .getCurrentKeyboardFocusManager().getActiveWindow();
          activeWindow.setAlwaysOnTop(alwaysCheckBox.isSelected());
        } else if (e.getSource() == connectButton) {
          connectByConfig();
        } else if (e.getSource() == searchButton) {
          doSearch();
        }
      }
    };
    alwaysCheckBox.addActionListener(buttonListener);
    clearButton.addActionListener(buttonListener);
    openLogButton.addActionListener(buttonListener);
    openOSGILogButton.addActionListener(buttonListener);
    connectButton.addActionListener(buttonListener);
    searchButton.addActionListener(buttonListener);

    contentTextArea.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == '\n') {
          int p = contentTextArea.getCaretPosition() - 1;
          if (p < 0)
            return;
          try {
            int start = Utilities.getRowStart(contentTextArea, p);
            int end = Utilities.getRowEnd(contentTextArea, p);
            if (end - start < 1) {
              return;
            }
            String commandLine = contentTextArea.getText(start, end - start);
            if (commandLine == null) {
              return;
            }
            commandLine = commandLine.trim();
            if (commandLine.startsWith(":")) {
              commandLine = commandLine.substring(1).trim();
            }
            if (commandLine.length() == 0) {
              return;
            }

            if (execCustomCommand(commandLine))
              return;

            execOSGICommand(commandLine);
          } catch (BadLocationException e1) {
            e1.printStackTrace();
          }
        }
      }
    });
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        if (osgiConsoleReadThread != null && osgiConsoleReadThread.isAlive()) {
          osgiConsoleReadThread.setStoped(true);
        }
      }
    });
  }

  /**
   * 发送一个字符到osgi框架核心
   * @param c
   */
  private void sendChar2Osgi(char c) {
    try {
      outputStream.write(c);
      outputStream.flush();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  private void connectByConfig() {
    connect(hostTextField.getText(), Integer.parseInt(portTextField.getText(), 10));
  }

  private void disconnect() {
    logWriter.close();
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        contentTextArea.append("连接已断开\n");
        contentTextArea.setEnabled(false);
        openLogButton.setEnabled(false);
        openOSGILogButton.setEnabled(false);
        clearButton.setEnabled(false);
        statusLabel.setIcon(stopedIcon);
      }
    });
  }

  /**
   * 执行自定义命令
   * @param commandLine 命令行
   * @return 如果是自定义命令，并执行，返回true，否则返回false，由osgi来执行
   */
  private boolean execCustomCommand(String commandLine) {
    if (commandLine.equalsIgnoreCase("clean") || commandLine.equalsIgnoreCase("cls")
      || commandLine.equalsIgnoreCase("clear")) {
      contentTextArea.clear();
      return true;
    } else if ("la".equals(commandLine)) {
      execOSGICommand("ss -s ACTIVE");
      return true;
    } else if ("lr".equals(commandLine)) {
      execOSGICommand("ss -s RESOLVED");
      return true;
    } else if ("li".equals(commandLine)) {
      execOSGICommand("ss -s INSTALLED");
      return true;
    }
    return false;
  }

  private void execOSGICommand(String commandString) {
    for (int i = 0; i < commandString.length(); i++) {
      sendChar2Osgi(commandString.charAt(i));
    }
    if (!commandString.endsWith("\n")) {
      sendChar2Osgi('\n');
    }
  }

  private void doSearch() {
    contentTextArea.getHighlighter().removeAllHighlights();
    String searchText = searchTextField.getText();
    if (searchText == null || searchText.trim().length() == 0)
      return;

    int pos = contentTextArea.getCaretPosition();
    if (pos == contentTextArea.getDocument().getLength()) {
      pos = 0;
    }

    String contentText = contentTextArea.getText().toLowerCase();
    searchText = searchText.toLowerCase();
    int start = contentText.indexOf(searchText, pos);
    //选定下一个找到的String
    if (start > -1) {
      contentTextArea.requestFocus();
      contentTextArea.select(start, start + searchText.length());
    }

    //将所有的匹配String全部高亮
    for (int index = contentText.indexOf(searchText); index > -1; index = contentText
      .indexOf(searchText, pos)) {
      try {
        int end = index + searchText.length();
        //start 为-1说明start > -1未找到,选定第一个匹配
        if (start == -1) {
          contentTextArea.requestFocus();
          contentTextArea.select(index, end);
          start = index + end;
        }
        contentTextArea.getHighlighter().addHighlight(index, end,
          new DefaultHighlighter.DefaultHighlightPainter(null));
        pos = end;
      } catch (BadLocationException e) {
      }
    }
  }

  private void connected(final String host, final int port) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        contentTextArea.setEnabled(true);
        openLogButton.setEnabled(true);
        openOSGILogButton.setEnabled(true);
        clearButton.setEnabled(true);
        contentTextArea.clear();
        contentTextArea.append(String.format("已经连接到调试主机：%s 端口：%s\n", host, port));
        contentTextArea.append("快捷键：\n");
        contentTextArea.append(String.format("\t清除：%s\n",
          "CTRL+L (与linux shell快捷键相同)"));
        contentTextArea.append(String.format("\t快速输入：%s\n", "CTRL+I (注：i input)"));
        contentTextArea.append(String.format("\t查找：%s，支持查找选定文本\n", "CTRL+F"));
        contentTextArea.append(String
          .format("\t查找下一个：%s或CTRL+N (find next)\n", "F3"));
        contentTextArea.append("内建命令：\n");
        contentTextArea.append(String.format("\tclear或clean或cls  功能：%s\n", "清屏/清除"));
        contentTextArea.append(String.format("\tla                 功能：%s\n",
          "list active 显示状态为ACTIVE"));
        contentTextArea.append(String.format("\tli                 功能：%s\n",
          "list installed 显示状态为INSTALLED"));
        contentTextArea.append(String.format("\tlr                 功能：%s\n",
          "list resolved 显示状态为RESOLVED"));

        contentTextArea.append("命令补齐：\n");
        contentTextArea.append("\t补齐快捷键：tab 与linux shell和windows cmd窗口相同\n");
        contentTextArea.append("\t命用方法：输入部分命令，按tab键，如：serv 按tab将补齐为services\n");

        contentTextArea.append("在这里输入osgi控制台命令或内建命令，如：la, ss\n");
        contentTextArea.setCaretPosition(contentTextArea.getDocument().getLength());
        portTextField.setText(Integer.toString(port));
        hostTextField.setText(host);
        statusLabel.setIcon(connectedIcon);
      }
    });
  }
}
