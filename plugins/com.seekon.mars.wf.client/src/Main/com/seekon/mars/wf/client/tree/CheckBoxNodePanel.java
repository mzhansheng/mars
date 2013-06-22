package com.seekon.mars.wf.client.tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
* <p>CheckBoxNodePanel树节点绘制面板</p>
*/
public class CheckBoxNodePanel extends JPanel {
  protected JCheckBox check;

  protected TreeIcon imgIcon;

  public CheckBoxNodePanel() {
    setBorder(javax.swing.BorderFactory.createEmptyBorder());
    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    add(check = new JCheckBox());
    add(imgIcon = new TreeIcon());
    check.setBackground(UIManager.getColor("Tree.textBackground"));
    imgIcon.setForeground(UIManager.getColor("Tree.textForeground"));
  }

  public void setText(String text) {
    imgIcon.setText(text);
  }

  public void setIcon(ImageIcon icon) {
    imgIcon.setIcon(icon);
  }

  public void setSelected(boolean selected) {
    check.setSelected(selected);
  }
  public void setCheckEnable(boolean isChkEnable){
    check.setVisible(isChkEnable);
    repaint();
  }

  public void setPanelOpaque(boolean isOpaque) {
    check.setOpaque(isOpaque);
    imgIcon.setOpaque(isOpaque);
  }

  public JCheckBox getCheckBox() {
    return check;
  }

  public TreeIcon getTreeIcon() {
    return imgIcon;
  }
  
  public void hideCheckBox(boolean isHide){
    check.setVisible(isHide);
  }

  public Dimension getPreferredSize() {
    Dimension d_check = check.getPreferredSize();
    Dimension d_label = imgIcon.getPreferredSize();
    if(check.isVisible()){
      return new Dimension(d_check.width + d_label.width,
        (d_check.height < d_label.height ? d_label.height : d_check.height));
    }
    else{
      return new Dimension(d_label.width,d_label.height);
    }
  }

  public void doLayout() {
    if(check.isVisible()){
      Dimension d_check = check.getPreferredSize();
      Dimension d_label = imgIcon.getPreferredSize();
      int y_check = 0;
      int y_label = 0;
      if (d_check.height < d_label.height) {
        y_check = (d_label.height - d_check.height) / 2;
      } else {
        y_label = (d_check.height - d_label.height) / 2;
      }
      check.setLocation(0, y_check);
      check.setBounds(0, y_check, d_check.width, d_check.height);
      imgIcon.setLocation(d_check.width, y_label);
      imgIcon.setBounds(d_check.width, y_label, d_label.width, d_label.height);
    }
    else{
      Dimension d_label2 = imgIcon.getPreferredSize();
      imgIcon.setLocation(0, 0);
      imgIcon.setBounds(0,0,d_label2.width, d_label2.height);
    }
  }

  public void setBackground(Color color) {
    if (color instanceof ColorUIResource) {
      color = null;
    }
    super.setBackground(color);
  }
}

//定制的树节点图标
class TreeIcon extends JLabel {
  protected boolean isSelected = false;

  protected boolean hasFocus = false;

  public TreeIcon() {
    super();
  }

  public TreeIcon(ImageIcon icon, String text) {
    super(icon);
    super.setText(text);
  }

  public void setBackground(Color color) {
    if (color instanceof ColorUIResource) {
      color = null;
    }
    super.setBackground(color);
  }

  public void paint(Graphics g) {
    String str;
    if ((str = getText()) != null) {
      if (0 < str.length()) {
        if (isSelected) {
          g.setColor(UIManager.getColor("Tree.selectionBackground"));
        } else {
          g.setColor(UIManager.getColor("Tree.textBackground"));
        }
        Dimension d = getPreferredSize();
        int imageOffset = 0;
        Icon curIcon = getIcon();
        if (curIcon != null) {
          imageOffset = curIcon.getIconWidth() + Math.max(0, getIconTextGap() - 1);
        }
        g.fillRect(imageOffset, 0, d.width - 1 - imageOffset, d.height);
        if (hasFocus) {
          g.setColor(UIManager.getColor("Tree.selectionBorderColor"));
          g.drawRect(imageOffset, 0, d.width - 1 - imageOffset, d.height - 1);
        }
      }
    }
    super.paint(g);
  }

  public Dimension getPreferredSize() {
    Dimension retDimension = super.getPreferredSize();
    if (retDimension != null) {
      retDimension = new Dimension(retDimension.width + 3, retDimension.height);
    }
    return retDimension;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  public void setFocus(boolean hasFocus) {
    this.hasFocus = hasFocus;
  }
}
