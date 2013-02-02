package com.seekon.system.auth.client.view.user.basicinfo;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GenderChooser extends JPanel {

  private static final long serialVersionUID = 3784549832034569820L;

  private static final String FEMALE = "女";

  private static final String MALE = "男";

  private final ButtonGroup buttonGroup = new ButtonGroup();

  public GenderChooser() {
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    JRadioButton radioButton = new JRadioButton(MALE);
    radioButton.setActionCommand(MALE);
    buttonGroup.add(radioButton);
    radioButton.setSelected(true);
    add(radioButton);

    JRadioButton radioButton_1 = new JRadioButton(FEMALE);
    radioButton.setActionCommand(FEMALE);
    buttonGroup.add(radioButton_1);
    add(radioButton_1);

  }

  public int getValue() {
    return buttonGroup.getSelection().getActionCommand().equals(MALE) ? 1 : 0;
  }

}
