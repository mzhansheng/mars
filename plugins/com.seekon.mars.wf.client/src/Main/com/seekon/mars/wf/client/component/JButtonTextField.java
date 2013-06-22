package com.seekon.mars.wf.client.component;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.Document;

import com.seekon.mars.wf.client.dialog.WfDialog;
import com.seekon.mars.wf.client.panel.event.ValueChangeEvent;
import com.seekon.mars.wf.client.panel.event.ValueChangeListener;

public abstract class JButtonTextField extends JTextField {
  protected JButtonTextField self = this;

  public Object value;

  public Map dataMap = new HashMap();

  public List filteredDataList = new ArrayList();

  public String compoId;

  public String prefix = "";

  public boolean randomEdit = true;

  public boolean levelCtrl = false;

  public String elementCode;

  private boolean isInput = false;

  protected WfDialog selectDialog;

  protected JButton chooseButton = new JButton(" ") {
    private static final long serialVersionUID = -7456400999981871987L;
    {
      this.setCursor(Cursor.getDefaultCursor());
      this.setFocusable(false);
    }

    @Override
    public Insets getInsets() {
      return new Insets(0, 0, 0, 0);
    }

    @Override
    public Dimension getPreferredSize() {
      Dimension size = JButtonTextField.super.getPreferredSize();
      size.height -= 6;
      size.width = size.height;
      return size;
    }

    @Override
    public void paint(Graphics g) {
      super.paint(g);
      int width = 3;
      int height = 3;

      Rectangle rect = g.getClipBounds();
      int x = rect.width / 4;
      int y = rect.height / 2;

      for (int i = 0; i < 3;) {
        g.fillOval(++i * x, y, width, height);
      }
    }
  };

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public boolean isRandomEdit() {
    return randomEdit;
  }

  public void setRandomEdit(boolean randomEdit) {
    this.randomEdit = randomEdit;
  }

  public Map getDataMap() {
    return dataMap;
  }

  public void setDataMap(Map dataMap) {
    this.dataMap = dataMap;
  }

  public void setValueByCode(String valueCode) {
    Object value = dataMap.get(valueCode);
    this.setValue(value);
  }

  public void setValue(Object value) {
    this.value = value;
    if (value != null) {
      this.setText(value.toString());
      this.setToolTipText(value.toString());
    } else {
      this.setText("");
      this.setToolTipText(null);
    }
//    fireValueChanged();
  }

  public Object getValue() {
    return this.value;
  }

  public JButtonTextField(Document doc, String text, int columns) {
    super(doc, text, columns);
    initButtonTextField();
  }

  public JButtonTextField(int columns) {
    super(columns);
    initButtonTextField();
  }

  @Override
  public void setEnabled(boolean enabled) {
    chooseButton.setEnabled(enabled);
    super.setEnabled(enabled);
    chooseButton.setVisible(enabled);
  }

  public JButtonTextField(String text, int columns) {
    super(text, columns);
    initButtonTextField();
  }

  public JButtonTextField(String text) {
    super(text);
    initButtonTextField();
  }

  public JButtonTextField() {
    super();
    initButtonTextField();
  }

  protected void initButtonTextField() {
    chooseButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JButtonTextField.this.handleClick(JButtonTextField.this);
      }
    });
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          chooseButton.doClick();
        }
      }
    });

    this.addFocusListener(new FocusAdapter() {
      public void focusLost(FocusEvent e) {
        validateInput();
      }
    });
    this.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
          return;
        }
        self.isInput = true;
      }

      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          validateInput();
        }
      }

    });
  }

  @Override
  public void addNotify() {
    super.addNotify();
    if (chooseButton != null)
      add(chooseButton);
  }

  public void addValueChangeListener(ValueChangeListener l) {
    this.listenerList.add(ValueChangeListener.class, l);
  }

  public void removeValueChangeListener(ValueChangeListener l) {
    this.listenerList.remove(ValueChangeListener.class, l);
  }

  protected void fireValueChanged() {
    ValueChangeEvent e = null;
    ValueChangeListener[] listeners = listenerList.getListeners(ValueChangeListener.class);
    for (ValueChangeListener l : listeners) {
      if (e == null) {
        e = new ValueChangeEvent(this);
      }
      l.valueChanged(e);
    }
  }

  private void validateInput() {
    String input = getText();
    if (isInput) {
      if (self.dataMap.get(input) == null) {
        self.setValue(null);
        self.isInput = false;
        if (!input.equals("")) {
          self.selectDialog.setVisible(true);
        }
      } else {
        self.isInput = false;
        if (this.filteredDataList.contains(self.dataMap.get(input))) {
          self.setValue(self.dataMap.get(input));
        } else {
          self.setValue(null);
          self.selectDialog.setVisible(true);
        }
      }
    }
  }

  public abstract void handleClick(JButtonTextField jButtonTextField);

  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
    IllegalAccessException, UnsupportedLookAndFeelException {

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    JFrame f = new JFrame();
    JButtonTextField textField = new JButtonTextField(15) {
      @Override
      public void handleClick(JButtonTextField jButtonTextField) {
        JOptionPane.showMessageDialog(null, "test");
        jButtonTextField.setText("test!");
      }
    };

    textField.setEditable(false);
    textField.setEnabled(false);
    textField.setEnabled(true);
    JPanel panel = new JPanel();
    panel.add(textField);
    f.getContentPane().add(panel, BorderLayout.NORTH);
    f.setSize(400, 300);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
    f.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}