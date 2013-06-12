package com.seekon.smartclient.gui;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTreeTable;
import org.swixml.LayoutConverterLibrary;
import org.swixml.SwingEngine;
import org.swixml.TagLibrary;

import com.seekon.smartclient.gui.internal.GuiParser;
import com.seekon.smartclient.gui.layoutconverter.MigLayoutConverter;
import com.seekon.smartclient.gui.tags.MDefaultComboBoxModel;
import com.seekon.smartclient.gui.tags.MDefaultDataModel;
import com.seekon.smartclient.gui.tags.MDefaultListModel;
import com.seekon.smartclient.gui.tags.MDefaultTableModel;
import com.seekon.smartclient.gui.tags.MDefaultTreeModel;
import com.seekon.smartclient.gui.tags.MDefaultTreeTableModel;
import com.seekon.smartclient.gui.tags.MJSONComboBoxModel;
import com.seekon.smartclient.gui.tags.MResourcePanel;
import com.seekon.smartclient.gui.tags.MSearchField;
import com.seekon.smartclient.gui.tags.MTextField;

public class GuiEngine extends SwingEngine {

  public GuiEngine() {
    super();
    this.parser = new GuiParser(this);
    initCustomProcessors();
  }

  public GuiEngine(Object client) {
    super(client);
    initCustomProcessors();
  }

  public GuiEngine(String resource) {
    super(resource);
    initCustomProcessors();
  }

  private void initCustomProcessors() {
    registerTags();
    registerLayoutConverters();
  }

  protected void registerTags() {
    registerTag(MTextField.class);
    registerTag(MSearchField.class);
    registerTag(JXTreeTable.class);
    registerTag(MResourcePanel.class);
    
    registerTag(MDefaultComboBoxModel.class);
    registerTag(MJSONComboBoxModel.class);
    registerTag(MDefaultListModel.class);
    registerTag(MDefaultTreeModel.class);
    registerTag(MDefaultTableModel.class);
    registerTag(MDefaultTreeTableModel.class);
    registerTag("MDataModel", MDefaultDataModel.class);
  }

  protected void registerTag(Class<?> clazz) {
    registerTag(clazz.getSimpleName(), clazz);
  }

  protected void registerTag(String name, Class<?> clazz) {
    TagLibrary taglib = this.getTaglib();
    taglib.registerTag(name, clazz);
  }

  protected void registerLayoutConverters() {
    LayoutConverterLibrary lib = LayoutConverterLibrary.getInstance();
    lib.register(MigLayout.class, new MigLayoutConverter());
  }
}
