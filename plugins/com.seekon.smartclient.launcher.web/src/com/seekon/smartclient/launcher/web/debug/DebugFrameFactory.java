package com.seekon.smartclient.launcher.web.debug;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

public class DebugFrameFactory {
  private DebugFrameFactory() {
  }

  public static JFrame createDebugFrame(String host, int port) throws Exception {
    File logFile = new File(System.getProperty("user.home"), ".seekon/debug/logs/"
      + generateLogFileName());
    OSGIDebuggerPane debugPane = new OSGIDebuggerPane(host, port, logFile);
    JFrame frame = new JFrame("A++ OSGI 调试器");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    Preferences pref = createPref();

    int width = pref.getInt("debug.window.width", 640);
    int height = pref.getInt("debug.window.height", 480);
    frame.setSize(width, height);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(pref.getInt("debug.window.x", (screenSize.width - width) / 2),
      pref.getInt("debug.window.y", (screenSize.height - height) / 2));
    frame.getContentPane().add(debugPane);
    frame.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentMoved(ComponentEvent e) {
        JFrame frame = (JFrame) e.getSource();
        Preferences pref = createPref();
        pref.putInt("debug.window.x", frame.getLocation().x);
        pref.putInt("debug.window.y", frame.getLocation().y);
      }

      @Override
      public void componentResized(ComponentEvent e) {
        JFrame frame = (JFrame) e.getSource();
        Preferences pref = createPref();
        pref.putInt("debug.window.width", frame.getSize().width);
        pref.putInt("debug.window.height", frame.getSize().height);
      }
    });
    return frame;
  }

  private static Preferences createPref() {
    Preferences pref = Preferences.userNodeForPackage(DebugFrameFactory.class);
    return pref;
  }

  private static String generateLogFileName() {
    return new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".log";
  }
}
