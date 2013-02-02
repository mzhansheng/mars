package com.seekon.smartclient.launcher.internal;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.seekon.smartclient.framework.internal.AbstractApplication;
import com.seekon.smartclient.framework.util.ResourceProvide;

public class WebStartApplication extends AbstractApplication {
  private JFrame frame = null;

  public WebStartApplication() {
    super();
  }

  @Override
  public void init() {
    frame = new JFrame();
    mainFrame.setOwner(frame);
    super.init();
  }

  @Override
  public void doStart() {
    frame.setIconImage(ResourceProvide.getImage("/resources/A6Icon.gif"));
    frame.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit
      .getDefaultToolkit().getScreenSize().height - 30);

    frame.addWindowListener(new WindowAdapter() {

      @Override
      public void windowClosing(WindowEvent e) {
        terminate();
      }
    });
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle(mainFrame.getApplication().getTitle());
    frame.setVisible(true);

    log.info("主框架启动完成");
  }

  @Override
  public void terminate() {
    if (frame != null) {
      frame.setVisible(false);
      frame.dispose();
    }
    super.terminate();
  }

}
