package com.seekon.smartclient.splash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class Splash {

  private AffineTransform affineTransform = new java.awt.geom.AffineTransform();

  private JProgressBar progressBar = new JProgressBar();

  /**
   * 字符串位置
   */
  private Point messageLocation;

  private JWindow splashWindow = new JWindow() {

    {
      this.setAlwaysOnTop(false);
      this.setLayout(new BorderLayout());
    }

    private static final long serialVersionUID = -8201531760700233487L;

    @Override
    public void paint(Graphics g) {
      Splash.this.paint(g);
    }

    /**
     * Calls <code>paint(g)</code>.  This method was overridden to
     * prevent an unnecessary call to clear the background.
     *
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void update(Graphics g) {
      paint(g);
    }
  };
  /**
   * 显示图象
   */
  private Image image;

  /**
   * 提示消息
   */
  protected String message = "";

  /**
   * 闪屏是否处于活动状态，如果为false将不显示闪屏
   */
  private boolean active = false;

  /**
   * 消息字体颜色
   */
  private Color messageFontColor = Color.WHITE;

  /**
   * 闪屏显示的图象
   * @param image Image
   */
  public Splash(Image image, JProgressBar progressBar, boolean active) {
    super();
    //affineTransform.rotate(180);

    this.image = image;
    this.active = active;
    this.progressBar = progressBar;

    //设置大小与图象相同
    splashWindow.setSize(this.image.getWidth(splashWindow), this.image
      .getHeight(splashWindow));

    //移动位置到屏幕中央
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension size = splashWindow.getSize();
    //    Dimension size = new Dimension(320, 240);
    //    setSize(size);

    if (size.height > screenSize.height) {
      size.height = screenSize.height;
    }
    if (size.width > screenSize.width) {
      size.width = screenSize.width;
    }
    splashWindow.setLocation((screenSize.width - size.width) / 2,
      (screenSize.height - size.height) / 2);
    setMessageLocation(new Point(20, splashWindow.getHeight() - 40));
    setActive(active);
  }

  public void paint(Graphics g) {
    synchronized (this) {
      Graphics2D g2d = (Graphics2D) g;
      //affineTransform.setToRotation(Math.toRadians(180));//旋转 

      //affineTransform.setToShear(0.5, 0.1);
      //    affineTransform.scale(0.8, 0.6);

      //affineTransform.scale(0.5, 0.5);
      //affineTransform.rotate(45);
      g2d.setTransform(affineTransform);
      //    affineTransform.translate(0, 0);
      //    affineTransform.translate(5, 0);

      //g2d.translate(-splashWindow.getWidth(),-splashWindow.getHeight());
      //g2d.translate(0,-splashWindow.getHeight());
      //画图片
      //g2d.setFont(new Font("宋体", Font.PLAIN, 12));
      g2d.setFont(progressBar.getFont());
      g2d.drawImage(image, 0, 0, splashWindow.getWidth(), splashWindow.getHeight(),
        splashWindow);

      //画字符串
      g2d.setColor(messageFontColor);
      g2d.drawString(message, messageLocation.x, messageLocation.y);

      //    AffineTransform affineTransform1 = new java.awt.geom.AffineTransform();
      //    affineTransform1.shear(Math.toRadians(5), 0);
      //    g2d.setTransform(affineTransform1);
      if (progressBar != null && progressBar.isVisible())

        SwingUtilities.paintComponent(g, progressBar, splashWindow, 20, splashWindow
          .getHeight() - 34, Math.max(splashWindow.getWidth() - 150, 20), 18);
      g2d.setTransform(affineTransform);
    }
  }

  /**
   * 显示消息字符串，该方法默认暂停100毫秒
   * @param message String - 要显示的消息字符
   */
  public void displayMessage(String message) {
    displayMessage(message, 0, null);
  }

  /**
   * 显示消息字符串，该方法默认暂停500毫秒
   * @param message String - 要显示的消息字符
   * @param messageColor Color 消息颜色
   */
  public void displayMessage(String message, Color messageColor) {
    displayMessage(message, 500, messageColor);
  }

  /**
   * 显示消息字符串
   * @param message String - 要显示的消息字符
   * @param millil long - 暂停时间，单位为毫秒
   */
  public void displayMessage(String message, long millil) {
    displayMessage(message, millil, null);
  }

  /**
   * 显示消息字符串
   * @param message String - 要显示的消息字符
   * @param millil long - 暂停时间，单位为毫秒
   * @param messageColor Color 消息颜色
   */
  public void displayMessage(String message, long millil, Color messageColor) {
    if (!active)
      return;
    splashWindow.setAlwaysOnTop(true);

    if (messageColor != null)
      setMessageFontColor(messageColor);
    this.message = message;
    splashWindow.update(splashWindow.getGraphics());
    splashWindow.repaint();
    try {
      Thread.sleep(millil);
    } catch (InterruptedException ex) {
    }
    splashWindow.setAlwaysOnTop(false);
  }

  public void setVisible(boolean b) {
    if (b && active) {
      splashWindow.setVisible(true);
    } else {
      splashWindow.setVisible(false);
    }
  }

  public void setActive(boolean active) {
    this.active = active;
    splashWindow.setVisible(active);
    if (!active)
      splashWindow.dispose();
  }

  public void setMessageFontColor(Color messageFontColor) {
    this.messageFontColor = messageFontColor;
  }

  public void setMessageLocation(Point messageLocation) {
    this.messageLocation = messageLocation;
  }

  public boolean getActive() {
    return active;
  }

  public Color getMessageFontColor() {
    return messageFontColor;
  }

  public Point getMessageLocation() {
    return messageLocation;

  }

  /**
   * 返回闪屏的大小
   * @return Dimension
   */
  public Dimension getSize() {
    return splashWindow.getSize();
  }

  /**
   * 设置大小
   * @param d Dimension
   */
  public void setSize(Dimension d) {
    splashWindow.setSize(d);
  }

  /**
   * 返回位置
   * @return Point
   */
  public Point getLocation() {
    return splashWindow.getLocation();
  }

  /**
   * 设置位置
   * @param p Point
   */
  public void setLocation(Point p) {
    splashWindow.setLocation(p);
  }

  public JWindow toWindow() {
    return splashWindow;
  }

  public JProgressBar getProgressBar() {
    return progressBar;
  }

}
