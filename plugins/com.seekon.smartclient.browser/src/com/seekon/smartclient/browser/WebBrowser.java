package com.seekon.smartclient.browser;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class WebBrowser extends JComponent{

  private static final long serialVersionUID = -8118290979798665765L;

  private String url = "http://localhost:7001/mars/index.jsp";

  private JPanel webBrowserPanel;

  private JWebBrowser webBrowser;

  public WebBrowser(String url) {
    this.url = url;
    webBrowserPanel = new JPanel(new BorderLayout());
    webBrowser = new JWebBrowser();

    init();
  }

  protected void init() {
    NativeInterface.open();
    webBrowser.navigate(url);
    webBrowser.setButtonBarVisible(false);
    webBrowser.setMenuBarVisible(false);
    webBrowser.setBarsVisible(false);
    webBrowser.setStatusBarVisible(false);

//    String sessionId = (String) SessionContext.getCurrentHttpSession().get(
//      SessionContext.KEY_SESSION_ID);
//    if (sessionId != null) {
//      JWebBrowser.setCookie(url, SessionContext.KEY_SESSION_ID + "=" + sessionId);
//    }
    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);

    this.setLayout(new BorderLayout());
    add(webBrowserPanel, BorderLayout.CENTER);

  }
}
