package com.seekon.smartclient.framework.component;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class JImageArea extends JComponent {

  private static final long serialVersionUID = -3718116452260187306L;

  private Image image;

  private int oldWidth = 0;

  private int oldHeight = 0;

  private Image drawImage;

  public JImageArea(Image image) {
    super();
    this.image = image;
  }

  @Override
  public void paint(Graphics g) {
    //检查是否要重新创建drawImage
    if (drawImage == null || oldWidth != this.getWidth()
      || oldHeight != this.getHeight()) {
      oldWidth = this.getWidth();
      oldHeight = this.getHeight();
      drawImage = image.getScaledInstance(g.getClipBounds().width,
        g.getClipBounds().height, 0);
      drawImage.flush();
    }
    g.drawImage(drawImage, 0, 0, this);
    this.paintBorder(g);
  }
}
