package com.seekon.mars.wf.client.dialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JDialog;

public class WfDialog extends JDialog {
  private static final long serialVersionUID = 1469263927853350561L;
  protected boolean isSeccess=false;

  public void setSeccess(boolean isSeccess) {
    this.isSeccess = isSeccess;
  }

  public boolean isSeccess() {
    return isSeccess;
  }

  public WfDialog(Window owner, String title) {
    super(owner, title);
  }

  public WfDialog(Window owner, String title, ModalityType modalityType) {
    super(owner, title, modalityType);
  }

  public WfDialog(Frame owner, String title, int treeType) {
    super(owner, title);
  }

  public WfDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
  }

  public WfDialog(Dialog owner, String title, int treeType) {
    super(owner, title);
  }

  public WfDialog(Dialog owner, String title, boolean modal) {
    super(owner, title, modal);
  }
  
  public void moveToScreenCenter() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation((screenSize.width - frameSize.width) / 2,
      (screenSize.height - frameSize.height) / 2);
  }
  
  public void close(){
    this.setVisible(false);
    this.dispose();
  }
}
