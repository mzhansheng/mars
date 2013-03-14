package com.seekon.smartclient.framework.internal.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.osgi.framework.ServiceReference;

import com.seekon.smartclient.framework.Application;
import com.seekon.smartclient.framework.component.JDescriptionableMenuItem;
import com.seekon.smartclient.framework.internal.Activator;
import com.seekon.smartclient.framework.util.LAF;
import com.seekon.smartclient.framework.util.ResourceProvide;

public class JOptionMenu extends JMenu {

  private static final long serialVersionUID = 5483027665936131520L;

  public static String OPTION_MENU_KEY = "option";

  public static LAF[] LAFS;

  public JOptionMenu() {
    super();

    LAFS = new LAF[16];
    LAFS[0] = new LAF("Metal", "javax.swing.plaf.metal.MetalLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[1] = new LAF("Windows",
      "com.sun.java.swing.plaf.windows.WindowsLookAndFeel", LAF.THEME_DEAULT);
    LAFS[2] = new LAF("Nimbus", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[3] = new LAF("Acryl", "com.jtattoo.plaf.acryl.AcrylLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[4] = new LAF("Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[5] = new LAF("Aluminium", "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[6] = new LAF("Bernstein", "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[7] = new LAF("Fast", "com.jtattoo.plaf.fast.FastLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[8] = new LAF("Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[9] = new LAF("HiFi", "com.jtattoo.plaf.hifi.HiFiLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[10] = new LAF("Luna", "com.jtattoo.plaf.luna.LunaLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[11] = new LAF("McWin", "com.jtattoo.plaf.mcwin.McWinLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[12] = new LAF("Mint", "com.jtattoo.plaf.mint.MintLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[13] = new LAF("Noire", "com.jtattoo.plaf.noire.NoireLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[14] = new LAF("Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel",
      LAF.THEME_DEAULT);
    LAFS[15] = new LAF("Texture", "com.jtattoo.plaf.texture.TextureLookAndFeel",
      LAF.THEME_DEAULT);
    
    this.setText(ResourceProvide.getResourceString(OPTION_MENU_KEY + "menu.text"));
    this.setMnemonic(ResourceProvide.getMnemonic(OPTION_MENU_KEY + "menu.mnemonic"));

    createLookAndFeelMenuItem();
    createPluginManagerMenuItem();
  }

  private void createLookAndFeelMenuItem() {
    JMenu lookAndFeelMenu = new JMenu(
      ResourceProvide.getResourceString("optionmenu_lookandfeel.text"));
    lookAndFeelMenu.setMnemonic(ResourceProvide
      .getMnemonic("optionmenu_lookandfeel.mnemonic"));
    this.add(lookAndFeelMenu);

    String lookAndFeelName = UIManager.getLookAndFeel().getClass().getName();
    ButtonGroup group = new ButtonGroup();
    for (int index = 0; index < LAFS.length; index++) {
      LAF laf = LAFS[index];
      if (lookAndFeelName.equals(laf.getClassName())) {
        laf.setSelected(true);
      }
      LafMenuItem lafMenuItem = new LafMenuItem(laf);
      lookAndFeelMenu.add(lafMenuItem);
      group.add(lafMenuItem);
    }
  }

  private void createPluginManagerMenuItem() {
    final JDescriptionableMenuItem item = new JDescriptionableMenuItem(
      ResourceProvide.getResourceString("optionmenu_pluginmanager.text"));
    item.setDescription(ResourceProvide
      .getResourceString("optionmenu_pluginmanager.description"));
    item.setMnemonic(ResourceProvide
      .getMnemonic("optionmenu_pluginmanager.mnemonic"));
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        PluginManagerDialog dlg = new PluginManagerDialog(SwingUtilities
          .getWindowAncestor(item));
        dlg.execute();
      }
    });

    this.add(item);
  }

  private class LafMenuItem extends JRadioButtonMenuItem {

    public LafMenuItem(LAF laf) {
      super(laf.getTitle());

      // Is this look and feel supported?
      if (laf.isSupported()) {
        LafListener lafItemListener = new LafListener(laf);
        addActionListener(lafItemListener);
      } else {
        setEnabled(false);
      }

      if (laf.isSelected()) {
        setSelected(true);
      }
    }
  }

  private class LafListener implements ActionListener {

    private LAF laf;

    public LafListener(LAF laf) {
      this.laf = laf;
    }

    public void actionPerformed(ActionEvent arg0) {
      for (int index = 0; index < LAFS.length; index++) {
        LAFS[index].setSelected(false);
      }
      //setLookAndFeel(laf);
      ServiceReference sr = Activator.getBundleContext().getServiceReference(
        Application.class.getName());
      Application application = (Application) Activator.getBundleContext()
        .getService(sr);
      try {
        UIManager.put("ClassLoader", laf.getLaf().getClass().getClassLoader());
        application.changeLookAndFeel(laf.getClassName());
      } catch (Exception e) {
        e.printStackTrace();
      }
      laf.setSelected(true);
    }

  }
}
